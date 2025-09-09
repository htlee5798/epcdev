package com.lottemart.epc.edi.imgagong.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lcnjf.util.StringUtils;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.edi.comm.service.RFCCommonService;
import com.lottemart.epc.edi.imgagong.dao.NEDMIGG0010Dao;
import com.lottemart.epc.edi.imgagong.model.NEDMIGG0010VO;
import com.lottemart.epc.edi.imgagong.service.NEDMIGG0110Service;

import lcn.module.framework.property.ConfigurationService;

/**
 * @Class Name : NEDMIGG0110ServiceImpl
 * @Description : 임가공 입고 관리 ServiceImpl
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 * 수정일			수정자          		수정내용
 * ----------	-----------		---------------------------
 * 2018.11.22	SHIN SE JIN		최초생성
 * 
 * @Copyright (C) 2000 ~ 2015 lottemart All right reserved.
 * </pre>
 */

@Service("NEDMIGG0110Service")
public class NEDMIGG0110ServiceImpl implements NEDMIGG0110Service{
	
	@Autowired
	private NEDMIGG0010Dao NEDMIGG0010Dao;
	
	@Resource(name = "configurationService")
	private ConfigurationService config;
	
	@Resource(name="rfcCommonService")
	private RFCCommonService rfcCommonService;
	
	private static final Logger logger = LoggerFactory.getLogger(NEDMIGG0110ServiceImpl.class);
	
	/**
	 * 임가공 입고정보 삭제 처리
	 */
	public Map<String, Object> imgagongGrDataDelete(NEDMIGG0010VO paramVO, HttpServletRequest request) throws Exception {
		
		Map<String, Object>	resultMap	=	new HashMap<String, Object>();
		resultMap.put("resultCd", "fail");
		
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		
		// 입고정보 삭제 대상 List
		List<NEDMIGG0010VO> dataList = paramVO.getDataList();
		
		Map<String, Object> rfcMap;										// RFC 결과
		HashMap<String, Object> rfcIggLog;								// RFC 로그(입고,삭제)
		HashMap reqCommonMap = new HashMap();							// RFC 응답
		
		reqCommonMap.put("ZPOSOURCE", "");
		reqCommonMap.put("ZPOTARGET", "");
		reqCommonMap.put("ZPONUMS", "");
		reqCommonMap.put("ZPOROWS", "");
		reqCommonMap.put("ZPODATE", "");
		reqCommonMap.put("ZPOTIME", "");
		
		boolean error_flag = false;	
		ArrayList<String> errkeys = new ArrayList<String>();
		ArrayList<String> eMessages = new ArrayList<String>();
				
		if (dataList.size() > 0) {
			
			for(NEDMIGG0010VO dataInfo : dataList) {
				
				/*-- 입고정보 SET -------------------------------------------------------*/
				JSONObject obj = new JSONObject();
				
				obj.put("I_EBLNR", dataInfo.getEblnr());			// 관리번호
				obj.put("I_GI_SEQ", dataInfo.getGiSeq());			// 출고순번
				obj.put("I_GR_SEQ", dataInfo.getGrSeq());			// 입고순번
				/*--------------------------------------------------------------------*/
				
				obj.put("REQCOMMON", reqCommonMap);		// RFC 응답 HashMap JsonObject로....
				
				/*-- RFC 요청로그정보 SET --------------------------------------------------*/
				rfcIggLog = new HashMap<String, Object>();
				rfcIggLog.put("proxyNm", "INV1020");					// 프록시명
				rfcIggLog.put("params", obj.toString());				// 요청값
				rfcIggLog.put("regId", epcLoginVO.getAdminId());		// 요청자
				/*--------------------------------------------------------------------*/
				
				// 임가공 RFC호출 로그저장(입고,삭제)
				String pgmId = NEDMIGG0010Dao.insertTpcRfcCallIgglog(rfcIggLog);
				
				logger.debug("obj.toString----->" + obj.toString());
				
				String eMessage = "";
				String eMsgtyp = "";
				
				JSONObject mapObj = null;
				JSONObject resultObj = null;
				
				try {
					// RFC 호출(임가공 입고정보 삭제)
					rfcMap = rfcCommonService.rfcCall("INV1020", obj.toString(), epcLoginVO.getAdminId());
					
					// 응답정보 확인
					mapObj				= new JSONObject(rfcMap.toString());							// MAP에 담긴 응답메세지를 JSONObject로.................							
					resultObj			= mapObj.getJSONObject("result");								// JSONObject에 담긴 응답메세지의 키는 result로 넘어 오기 떄문에 result로 꺼낸다.
					eMsgtyp				= StringUtils.trimToEmpty(resultObj.getString("E_MSGTYP"));		// RFC 응답 성공 / 실패 여부를 담는 Key다
					eMessage			= StringUtils.trimToEmpty(resultObj.getString("E_MESSAGE"));	// RFC 응답메세지 (임가공 입고정보를 저장했습니다.)
					
					logger.debug("eMessage >>>" + eMessage);
					
					logger.debug("eMsgtyp >>>" + eMsgtyp);
					
				} catch (Exception e) {
					
					rfcIggLog.put("pgmId", pgmId);
					rfcIggLog.put("statCd", "E");						// 임가공 RFC호출 상태
					rfcIggLog.put("errMsg", e.toString());				// 임가공 RFC호출 오류메세지
					NEDMIGG0010Dao.updateTpcRfcCallIgglog(rfcIggLog);	// 임가공 RFC호출 로그상세정보 저장(입고,삭제)					
					logger.error(e.getMessage());
					
					return resultMap;
					
				} finally {
					
					// 성공일 경우 임가공 입고 정보 저장
					if (eMsgtyp.equals("S")) {
						
						rfcIggLog.put("statCd", "S");						// 임가공 RFC호출 상태
						
						dataInfo.setDiv("D");								// 작업구분(I/D)
						dataInfo.setWorkId(epcLoginVO.getAdminId());		// 작업자ID
						NEDMIGG0010Dao.insertGrDataInfo(dataInfo);			// 임가공 입고내역 정보 저장
						
						//resultMap.put("resultCd", "success");

					} else {
						rfcIggLog.put("statCd", "E");						// 임가공 RFC호출 상태
						rfcIggLog.put("errMsg", eMessage);					// 임가공 RFC호출 오류메세지
						// 에러건이 있으면 관리번호 + SEQ, 에러매세지를 별도의 변수에 저장한 후 보여준다. 
						
						// 에러 플레그를 넣어줌
						error_flag = true;
						errkeys.add(dataInfo.getEblnr() + '_' + dataInfo.getGiSeq()); // 관리번호 + SEQ
						eMessages.add(eMessage); // 에러매세지
					}
					rfcIggLog.put("pgmId", pgmId);
					rfcIggLog.put("rtnData", resultObj.toString());
					NEDMIGG0010Dao.updateTpcRfcCallIgglog(rfcIggLog);		// 임가공 RFC호출 로그상세정보 저장(입고,삭제)
				}
			}
		}
		resultMap.put("resultCd", "success");
		
		//error_flag 값을 확인 하여 관리번호와 에러매세지를 resultMap에 넣어준다.
		if(error_flag) {
			resultMap.put("keys", errkeys);
			resultMap.put("eMessages", eMessages);
		}
				
		return resultMap;
	}
}

