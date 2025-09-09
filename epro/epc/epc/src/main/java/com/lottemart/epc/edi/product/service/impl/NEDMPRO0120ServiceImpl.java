package com.lottemart.epc.edi.product.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lcnjf.util.DateUtil;
import com.lottemart.epc.edi.comm.service.RFCCommonService;
import com.lottemart.epc.edi.product.dao.NEDMPRO0120Dao;
import com.lottemart.epc.edi.product.model.NEDMPRO0120VO;
import com.lottemart.epc.edi.product.service.NEDMPRO0120Service;

@Service("nedmpro0120Service")
public class NEDMPRO0120ServiceImpl implements NEDMPRO0120Service {
	
	private static final Logger logger = LoggerFactory.getLogger(NEDMPRO0120Service.class);

	@Autowired
	private NEDMPRO0120Dao nedmpro0120Dao;
	
	@Resource(name="rfcCommonService")
	private RFCCommonService rfcCommonService;
	
	/**
	 * Count
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int selectWholeStoreProductCount(NEDMPRO0120VO vo) throws Exception {
		return nedmpro0120Dao.selectWholeStoreProductCount(vo);
	}
	
	/**
	 * List 조회
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<NEDMPRO0120VO> selectWholeStoreProductList(NEDMPRO0120VO vo) throws Exception {
		return nedmpro0120Dao.selectWholeStoreProductList(vo);
	}
	
	/**
	 * 점포별 상품 List 조회 (PROXY)
	 */
	public Map<String,Object> getSelectStrProductListProxy(NEDMPRO0120VO vo) throws Exception {
		//데이터리스트
		List<NEDMPRO0120VO> list = new ArrayList<NEDMPRO0120VO>();
		int totalCount = 0;
		
		//리스트 반환 맵셋팅
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("resultList", list);
		resultMap.put("totalCount", totalCount);		
		
		String proxyNm = "MST2320";	//프록시명
		String gbn = "EDI";			//ERP 전송 시스템구분 (EDI)
		
		/*
		 * 1. 필수검색 조건 체크
		 */
		//검색조건 확인
		String srchSellCode = StringUtils.defaultString(vo.getSrchSellCode());		//판매코드
		String srchEntpCode = StringUtils.defaultString(vo.getSrchEntpCode());		//협력업체코드
		
		//판매코드 누락
		if("".equals(srchSellCode)) {
			return resultMap;
		}
		
		//협력업체코드 누락
		if("".equals(srchEntpCode)) {
			return resultMap;
		}
		
		
		/*
		 * 2. 점포별 상품 조회 (PROXY 전송)
		 */
		// ----- 1.PARAMETER SETTING
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("ven_cd", srchEntpCode);		//협력업체코드
		paramMap.put("srcmk_cd", srchSellCode);		//판매코드
		
		JSONObject obj = new JSONObject(paramMap);
		String params = obj.toString();
		
		// ----- 2.RFC CALL("proxyNm", String, String);
		Map<String, Object> rfcMap = rfcCommonService.rfcCall(proxyNm, params, gbn);
		
		// ----- 3.RFC 응답 메세지의 성공 / 실패 여부에 따라 리턴메세지를 처리해준다.
		JSONObject mapObj = new JSONObject(rfcMap.toString()); //MAP에 담긴 응답메세지를 JSONObject로
		JSONObject resultObj = mapObj.getJSONObject("result");
		String rsltCd = resultObj.has("MSGTYP")?resultObj.getString("MSGTYP"):"";		//응답결과코드
		String rsltMsg = resultObj.has("MESSAGE")?resultObj.getString("MESSAGE"):"";	//응답결과메세지	- 응답결과코드 S일때는 반환되지 않음
		
		//수신 실패 시, 빈 리스트 반환
		if(!"S".equals(rsltCd)) {
			logger.error(rsltMsg);
			return resultMap;
		}

		JSONArray dataListObj = (resultObj.has("ET_TAB"))? resultObj.getJSONArray("ET_TAB"):null;
		if(dataListObj == null) {
			logger.error("EMPTY ET_TAB");
			return resultMap;
		}
		
		
		NEDMPRO0120VO dataVo = null;
		String strCd="";	//점포코드 
		String strNm="";	//점포명
		String prodCd="";	//상품코드
		String prodNm="";	//상품명
		String srcmkCd="";	//판매코드
		String venCd="";	//협력업체코드
		String prodStatus="";	//점포상품상태코드
		String codeStatus="";	//점포상품상태명
		String regDt="";		//등록일시
		
		JSONObject dataObj = null;
		for(int i=0; i < dataListObj.length(); i++) {
			dataObj = dataListObj.getJSONObject(i);
			
			dataVo = new NEDMPRO0120VO();
			
			strCd = dataObj.getString("STR_CD"); 			//점포코드 
			strNm = dataObj.getString("STR_NM"); 			//점포명
			prodCd = dataObj.getString("PROD_CD");			//상품코드
			prodNm = dataObj.getString("PROD_NM"); 			//상품명
			srcmkCd = dataObj.getString("SRCMK_CD");		//판매코드
			venCd = dataObj.getString("VEN_CD");			//협력업체코드
			prodStatus = dataObj.getString("PROD_STATUS");	//점포상품상태코드
			codeStatus = dataObj.getString("CODE_STATUS");	//점포상품상태명
			regDt = dataObj.getString("REG_DT");			//등록일시
			
			strCd = strCd.replaceAll("^0+", "");			//점포코드앞 0제거
			prodCd = prodCd.replaceAll("^0+", "");			//상품코드앞 0제거
			venCd = venCd.replaceAll("^0+", "");			//협력업체코드 앞 0제거
			
			//vo setting
			dataVo.setSnum(Integer.toString(i+1));		//행번호	
			dataVo.setStrCd(strCd);				//점포코드
			dataVo.setStrNm(strNm);				//점포명
			dataVo.setProdCd(prodCd);			//상품코드
			dataVo.setProdNm(prodNm);			//상품명
			dataVo.setSrcmkCd(srcmkCd);			//판매코드
			dataVo.setVenCd(venCd);				//파트너사코드
			dataVo.setCodeStaus(codeStatus);	//점포상품상태명
			dataVo.setRegDt(regDt);				//등록일시
			
			//list에 추가
			list.add(dataVo);
		}
		
		//datalist 결과 setting
		resultMap.put("resultList", list);
		resultMap.put("totalCount", totalCount);		
		
		return resultMap;
	}
	
	/**
	 * 점포별 상품 List ExcelDownload (PROXY)
	 */
	public Map<String,Object> getSelectStrProductListProxyExcelDownload(NEDMPRO0120VO vo) throws Exception {
		//데이터리스트
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		int totalCount = 0;
		
		//리스트 반환 맵셋팅
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("resultList", list);
		resultMap.put("totalCount", totalCount);		
		
		String proxyNm = "MST2320";	//프록시명
		String gbn = "EDI";			//ERP 전송 시스템구분 (EDI)
		
		/*
		 * 1. 필수검색 조건 체크
		 */
		//검색조건 확인
		String srchSellCode = StringUtils.defaultString(vo.getSrchSellCode());		//판매코드
		String srchEntpCode = StringUtils.defaultString(vo.getSrchEntpCode());		//협력업체코드
		
		//판매코드 누락
		if("".equals(srchSellCode)) {
			return resultMap;
		}
		
		//협력업체코드 누락
		if("".equals(srchEntpCode)) {
			return resultMap;
		}
		
		
		/*
		 * 2. 점포별 상품 조회 (PROXY 전송)
		 */
		// ----- 1.PARAMETER SETTING
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("ven_cd", srchEntpCode);		//협력업체코드
		paramMap.put("srcmk_cd", srchSellCode);		//판매코드
		
		JSONObject obj = new JSONObject(paramMap);
		String params = obj.toString();
		
		// ----- 2.RFC CALL("proxyNm", String, String);
		Map<String, Object> rfcMap = rfcCommonService.rfcCall(proxyNm, params, gbn);
		
		// ----- 3.RFC 응답 메세지의 성공 / 실패 여부에 따라 리턴메세지를 처리해준다.
		JSONObject mapObj = new JSONObject(rfcMap.toString()); //MAP에 담긴 응답메세지를 JSONObject로
		JSONObject resultObj = mapObj.getJSONObject("result");
		String rsltCd = resultObj.has("MSGTYP")?resultObj.getString("MSGTYP"):"";		//응답결과코드
		String rsltMsg = resultObj.has("MESSAGE")?resultObj.getString("MESSAGE"):"";	//응답결과메세지	- 응답결과코드 S일때는 반환되지 않음
		
		//수신 실패 시, 빈 리스트 반환
		if(!"S".equals(rsltCd)) {
			logger.error(rsltMsg);
			return resultMap;
		}
		
		JSONArray dataListObj = (resultObj.has("ET_TAB"))? resultObj.getJSONArray("ET_TAB"):null;
		if(dataListObj == null) {
			logger.error("EMPTY ET_TAB");
			return resultMap;
		}
		

		Map<String,String> dataMap = null;
		String strCd="";	//점포코드 
		String strNm="";	//점포명
		String prodCd="";	//상품코드
		String prodNm="";	//상품명
		String srcmkCd="";	//판매코드
		String venCd="";	//협력업체코드
		String prodStatus="";	//점포상품상태코드
		String codeStatus="";	//점포상품상태명
		String regDt="";		//등록일시
		String regDtFmt = "";	//등록일시포맷
		
		JSONObject dataObj = null;
		for(int i=0; i < dataListObj.length(); i++) {
			dataObj = dataListObj.getJSONObject(i);
			
			dataMap = new LinkedHashMap<String,String>();
			
			strCd = dataObj.getString("STR_CD"); 			//점포코드 
			strNm = dataObj.getString("STR_NM"); 			//점포명
			prodCd = dataObj.getString("PROD_CD");			//상품코드
			prodNm = dataObj.getString("PROD_NM"); 			//상품명
			srcmkCd = dataObj.getString("SRCMK_CD");		//판매코드
			venCd = dataObj.getString("VEN_CD");			//협력업체코드
			prodStatus = dataObj.getString("PROD_STATUS");	//점포상품상태코드
			codeStatus = dataObj.getString("CODE_STATUS");	//점포상품상태명
			regDt = dataObj.getString("REG_DT");			//등록일시
			
			strCd = strCd.replaceAll("^0+", "");			//점포코드앞 0제거
			prodCd = prodCd.replaceAll("^0+", "");			//상품코드앞 0제거
			venCd = venCd.replaceAll("^0+", "");			//협력업체코드 앞 0제거
			
			//dataMap setting
			dataMap.put("SNUM", Integer.toString(i+1));		//행번호
			dataMap.put("VEN_CD", venCd);					//협력업체코드
			dataMap.put("STR_CD", strCd);					//점포코드
			dataMap.put("STR_NM", strNm);					//점포명
			dataMap.put("SRCMK_CD", srcmkCd);				//판매코드
			dataMap.put("PROD_CD", prodCd);					//상품코드
			dataMap.put("PROD_NM", prodNm);					//상품명
			dataMap.put("CODE_STATUS", codeStatus);			//점포상품상태명
			
			//등록일자 yyyy-MM-dd
			if(regDt !=null && !"".equals(regDt) && DateUtil.isDate(regDt, "yyyyMMdd")) {
				regDtFmt = DateUtil.string2String(regDt, "yyyyMMdd", DateUtil.DATE_PATTERN);
			}
			dataMap.put("REG_DT", regDtFmt);				//등록일시
			
			//list에 추가
			list.add(dataMap);
		}
		
		//datalist 결과 setting
		resultMap.put("resultList", list);
		resultMap.put("totalCount", totalCount);		
		
		return resultMap;
	}
}
