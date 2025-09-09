package com.lottemart.epc.edi.product.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.lottemart.common.util.ConfigUtils;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.edi.comm.service.RFCCommonService;
import com.lottemart.epc.edi.product.controller.NEDMPRO0020Controller;
import com.lottemart.epc.edi.product.dao.NEDMPRO0320Dao;
import com.lottemart.epc.edi.product.model.NEDMPRO0320VO;
import com.lottemart.epc.edi.product.service.NEDMPRO0320Service;

import lcn.module.common.util.HashBox;
import lcn.module.framework.property.ConfigurationService;

@Service("nedmpro0320Service")
public class NEDMPRO0320ServiceImpl implements NEDMPRO0320Service {
	
	private static final Logger logger = LoggerFactory.getLogger(NEDMPRO0020Controller.class);
	
	@Autowired
	private NEDMPRO0320Dao nedmpro0320Dao;
	
	@Autowired
	private RFCCommonService rfcCommonService;
	
	@Autowired
	private ConfigurationService config;
	
	/**
	 * 반품 제안 등록 조회
	 */
	public HashMap<String,Object> selectProdRtnItemList(NEDMPRO0320VO paramVo) throws Exception {
		
		HashMap<String, Object> returnMap = new HashMap<String, Object>();		//반환할 맵
		List<NEDMPRO0320VO> list = new ArrayList<NEDMPRO0320VO>();				//리스트 조회 결과 Vo
		EpcLoginVO epcLoginVO = this.getWorkSessionVo();
		
		if(epcLoginVO != null) {
			String[] ven_cd = epcLoginVO.getVendorId();
			paramVo.setVenCdArr(ven_cd);
		}
		
		int totalCount = 0;
		
		// 반품 제안 등록 카운터 조회
		totalCount = nedmpro0320Dao.selectProdRtnItemListCount(paramVo);

		if(totalCount > 0){	// 반품 제안 등록 리스트 조회
			list = nedmpro0320Dao.selectProdRtnItemList(paramVo);
		}
		
		returnMap.put("list", list);							//리스트 데이터
		returnMap.put("totalCount", totalCount);				//조회 결과 카운터
		
		return returnMap;
	}
	
	
	/**
	 * 반품 제안 등록
	 */
	public HashMap<String,Object> insertProdRtnItem(NEDMPRO0320VO paramVo, HttpServletRequest request) throws Exception {
		
		HashMap<String, Object> returnMap = new HashMap<String, Object>();		//반환할 맵
		
		//작업자정보 setting (로그인세션에서 추출)
		EpcLoginVO epcLoginVO = this.getWorkSessionVo();
		String workId = epcLoginVO.getLoginWorkId();
		
		String msg = "정상적으로 처리되었습니다.\n";
		int failCnt = 0;
		int cnt = 0;
		try {
			
			paramVo.setWorkId( workId ); // 작업자 아이디
			
			// 반품 정보 저장
			for(NEDMPRO0320VO vo : paramVo.getItemList()){
				vo.setWorkId( workId ); // 작업자 아이디
				vo.setDelYn( paramVo.getDelYn() );	// 삭제여부
				
				// 진행상태가 "파트너사등록"만 insert!!!
				if("0".equals( vo.getPrdtStatus() )) {
					if("".equals(vo.getProdRtnNo())) {
						vo.setProdRtnNo( nedmpro0320Dao.selectProdRtnNoSeq(vo) );  // 반품 요청 번호
					}
					
					NEDMPRO0320VO chkVo = nedmpro0320Dao.selectRtnItemProdCdChk(vo);
					if(chkVo != null && chkVo.getRtnClsDy() != null) {
						msg += "파트너사코드 : " + vo.getVenCd() + " 상품코드 : " + vo.getProdCd() + "의 반품마감일이 지나지않았습니다.";
						failCnt++;
						/*if(!"3".equals( chkVo.getPrdtStatus() )) {
							msg += "파트너사코드 : " + vo.getVenCd() + " 상품코드 : " + vo.getProdCd() + "의 반품마감일이 지나지않았습니다.";
							failCnt++;
						}else {
							nedmpro0320Dao.mergeProdRtnItem(vo); // 반품 item 저장(MERGE)
							cnt++;
						}*/
					}else {
						nedmpro0320Dao.mergeProdRtnItem(vo); // 반품 item 저장(MERGE)
						cnt++;
					}
				}
			}// end for
			returnMap.put("result", true);
			returnMap.put("msg", msg + "\n저장 성공 : "+ cnt + " 저장 실패 : + " + failCnt);
		} catch (Exception e) {
			logger.error("error message --> " + e.getMessage());
			returnMap.put("result", false);
			returnMap.put("errMsg", e.getMessage());
		}
		
		return returnMap;
	}
	
	
	/**
	 * 반품 제안 정보 item 삭제
	 */
	public HashMap<String,Object> deleteProdRtnItem(NEDMPRO0320VO paramVo, HttpServletRequest request) throws Exception {
		
		HashMap<String, Object> returnMap = new HashMap<String, Object>();		//반환할 맵
		
		//작업자정보 setting (로그인세션에서 추출)
		EpcLoginVO epcLoginVO = this.getWorkSessionVo();
		String workId = epcLoginVO.getLoginWorkId();
		
		try {
			paramVo.setWorkId( workId ); // 작업자 아이디
			paramVo.setDelYn("Y");
			nedmpro0320Dao.mergeProdRtnItem(paramVo);	// 반품 itme 정보 삭제 처리
			returnMap.put("result", true);
			
		} catch (Exception e) {
			logger.error("error message --> " + e.getMessage());
			returnMap.put("result", false);
			returnMap.put("errMsg", e.getMessage());
		}
		
		return returnMap;
	}
	
	/**
	 * 반품 제안 정보 ECO 전송
	 */
	public Map<String, Object> insertProdRtnItemRfcCall(NEDMPRO0320VO paramVo, HttpServletRequest request) throws Exception {
		
		Map<String, Object> returnMap = new HashMap<String, Object>();		//반환할 맵
		
		//작업자정보 setting (로그인세션에서 추출)
		EpcLoginVO epcLoginVO = this.getWorkSessionVo();
		String workId = epcLoginVO.getLoginWorkId();
		
		String rtnResult = "F";
		returnMap.put("result", true);
		
		try {
			JSONObject jo = new JSONObject();
			List<LinkedHashMap> itemData = new ArrayList<LinkedHashMap>();
			for(NEDMPRO0320VO vo : paramVo.getItemList()){
				List<LinkedHashMap> data = nedmpro0320Dao.selectProdRtnItemRfcCallData(vo);
				itemData.addAll(data);
			}// end for
			
			jo.put("ITEM", itemData);
			logger.info("sap send String ::::::::::: " + jo.toString() );
			returnMap = rfcCommonService.rfcCall("INV2040", jo.toString(), workId);
			JSONObject mapObj        = new JSONObject(returnMap.toString());  
			JSONObject resultObj     = mapObj.getJSONObject("result");        
			//JSONObject respCommonObj = resultObj.getJSONObject("MESSAGE");  
			//JSONObject respCommonObj = resultObj.getJSONObject("MSGTYP");  
			rtnResult = StringUtils.trimToEmpty(resultObj.getString("MSGTYP")); 
			logger.info("return message ::::::::::::::: " + resultObj);
			logger.info("return message ::::::::::::::: " + rtnResult);
			
			// 성공이 아니면..
			if (!rtnResult.equals("S")) {
				returnMap.put("result", false);
				returnMap.put("msg", "MD 협의 요청을 실패했습니다. \n잠시후 다시 시도해주세요.");
			}else {
				
				for(NEDMPRO0320VO vo : paramVo.getItemList()){
					vo.setWorkId( vo.getWorkId() ); 	// 작업자 아이디
					vo.setPrdtStatus(paramVo.getPrdtStatus()); 	// 진행상태
					nedmpro0320Dao.updateProdRtnItem(vo);
				}// end for
				
				returnMap.put("result", true);
				returnMap.put("msg", "정상적으로 MD 협의 요청을 하였습니다.");
			}
			
		} catch (Exception e) {
			logger.error("error message --> " + e.getMessage());
			returnMap.put("result", false);
			returnMap.put("errMsg", e.getMessage());
		}
		
		return returnMap;
	}
	
	
	/**
	 * 세션정보 추출
	 * @return EpcLoginVO
	 */
	private EpcLoginVO getWorkSessionVo() {
		EpcLoginVO epcLoginVO = null;
		String sessionKey = config.getString("lottemart.epc.session.key");
		
		try {
			ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
			HttpServletRequest request = attrs.getRequest();
			
			if(request != null) {
				epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);
			}
			
		}catch(Exception e) {
			logger.error(e.getMessage());
		}
		
		return epcLoginVO;
	}
	
	
}
