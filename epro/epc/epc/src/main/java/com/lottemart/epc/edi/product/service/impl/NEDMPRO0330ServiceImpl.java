package com.lottemart.epc.edi.product.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.edi.comm.service.RFCCommonService;
import com.lottemart.epc.edi.product.controller.NEDMPRO0020Controller;
import com.lottemart.epc.edi.product.dao.NEDMPRO0330Dao;
import com.lottemart.epc.edi.product.model.NEDMPRO0330VO;
import com.lottemart.epc.edi.product.service.NEDMPRO0330Service;

import lcn.module.framework.property.ConfigurationService;

@Service("nedmpro0330Service")
public class NEDMPRO0330ServiceImpl implements NEDMPRO0330Service {
	
	private static final Logger logger = LoggerFactory.getLogger(NEDMPRO0020Controller.class);
	
	@Autowired
	private NEDMPRO0330Dao nedmpro0330Dao;
	
	@Autowired
	private RFCCommonService rfcCommonService;
	
	@Autowired
	private ConfigurationService config;
	
	/**
	 * 약정서 조회
	 */
	public Map<String, Object> getProdRtnDocList(NEDMPRO0330VO paramVo, HttpServletRequest request) throws Exception {
		
		Map<String, Object> returnMap = new HashMap<String, Object>();		//반환할 맵
		
		//작업자정보 setting (로그인세션에서 추출)
		EpcLoginVO epcLoginVO = this.getWorkSessionVo();
		String workId = epcLoginVO.getLoginWorkId();
		
		String rtnResult = "F";
		returnMap.put("result", true);
		
		try {
			JSONObject jo = new JSONObject();
			
			JSONObject inputJson = new JSONObject();
			inputJson.put("I_RGDAT_FROM", paramVo.getiRgdatFrom());
			inputJson.put("I_RGDAT_TO", paramVo.getiRgdatTo());
			inputJson.put("I_LIFNR", paramVo.getiLifnr());
			inputJson.put("I_DC_STAT", paramVo.getiDcStat());
			inputJson.put("I_DEP_CD", paramVo.getiDepCd());
			
			JSONArray jsonArr = new JSONArray();
			jsonArr.put(inputJson);
			jo.put("ITEM_INPUT", jsonArr);
			logger.info("sap send String ::::::::::: " + jo.toString() );
			
			returnMap = rfcCommonService.rfcCall("INV2150", jo.toString(), workId);
			logger.info("sap get String ::::::::::: " + returnMap.toString() );
			JSONObject mapObj        = new JSONObject(returnMap.toString());  
			JSONObject resultObj     = mapObj.getJSONObject("result");        
			rtnResult = StringUtils.trimToEmpty(resultObj.getString("MSGTYP"));
			logger.info("sap get String ::::::::::: " + mapObj );
			//------------------------------------------------------------------------------------------------------
			logger.info("return message ::::::::::::::: " + rtnResult);
			
			// 성공이 아니면..
			if (!rtnResult.equals("S")) {
				returnMap.put("result", false);
				returnMap.put("msg", "약정서 조회를 실패했습니다. \n잠시후 다시 시도해주세요.");
			}else {
				returnMap.put("result", true);
				returnMap.put("msg", "정상적으로 조회를 하였습니다.");
				
				List<Map<String, Object>> dataList = parseFlexibleList(resultObj, "T_DATA");
				List<Map<String, Object>> matnrList = parseFlexibleList(resultObj, "T_MATNR");
				List<Map<String, Object>> werksList = parseFlexibleList(resultObj, "T_WERKS");
				
				for(Map<String, Object> vo : dataList){
					vo.put("workId", workId); // 작업자 아이디
					nedmpro0330Dao.mergeProdRtnCntr(vo);
				}
				
				for(Map<String, Object> vo : matnrList){
					vo.put("workId", workId); // 작업자 아이디
					nedmpro0330Dao.mergeProdRtnCntrProd(vo);
				}
				
				for(Map<String, Object> vo : werksList){
					vo.put("workId", workId); // 작업자 아이디
					nedmpro0330Dao.mergeProdRtnCntrStrProd(vo);
				}
				
				// 약정서 목록 조회
				List<NEDMPRO0330VO> tDataList = nedmpro0330Dao.selectProdRtnCntrList(paramVo);
				returnMap.put("tDataList", tDataList );	//리스트 데이터 T_DATA
			}
		} catch (Exception e) {
			logger.error("error message --> " + e.getMessage());
			returnMap.put("result", false);
			returnMap.put("errMsg", e.getMessage());
		}
		
		return returnMap;
	}
	
	/**
	 * 반품 약정서 조회( 상품별, 점포 & 상품별 )
	 */
	public Map<String, Object> selectProdRtnCntrStrProdList(NEDMPRO0330VO paramVo, HttpServletRequest request) throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();		//반환할 맵
		try {
			List<NEDMPRO0330VO> tMatnrList = nedmpro0330Dao.selectProdRtnCntrProdList(paramVo);
			List<NEDMPRO0330VO> tWerksList = nedmpro0330Dao.selectProdRtnCntrStrProdList(paramVo);
			
			returnMap.put("result", true);
			returnMap.put("tMatnrList", tMatnrList );		//리스트 데이터 T_MATNR
			returnMap.put("tWerksList", tWerksList );		//리스트 데이터 T_WERKS
			
		} catch (Exception e) {
			logger.error("error message --> " + e.getMessage());
			returnMap.put("result", false);
			returnMap.put("msg", "조회에 실패했습니다.\\n다시 확인해주세요.");
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
	
	private List<Map<String, Object>> parseFlexibleList(JSONObject parent, String key){
	    List<Map<String, Object>> list = new ArrayList<>();

	    if (!parent.has(key) || parent.isNull(key)) return list;

	    Object obj;
		try {
			obj = parent.get(key);
			
			if (obj instanceof JSONArray) {
		        JSONArray arr = (JSONArray) obj;
		        for (int i = 0; i < arr.length(); i++) {
		            JSONObject item = arr.getJSONObject(i);
		            list.add(toMap(item));
		        }
		    } else if (obj instanceof JSONObject) {
		        list.add(toMap((JSONObject) obj));
		    } 
			
		} catch (JSONException e) {
			logger.error(e.getMessage());
		}
	    return list;
	}

	private Map<String, Object> toMap(JSONObject jsonObj){
	    Map<String, Object> map = new HashMap<>();
	    Iterator<String> keys = jsonObj.keys();
	    while (keys.hasNext()) {
	        String key = keys.next();
	        Object value;
	        try {
	        	value = jsonObj.get(key);
	        	
	        	if(value == null || JSONObject.NULL.equals(value)) {
	        		map.put(key, "");
	        	}else {
	        		map.put(key, String.valueOf(value));
	        	}
			} catch (JSONException e) {
				logger.error(e.getMessage());
			}
	    }
	    return map;
	}
	
}
