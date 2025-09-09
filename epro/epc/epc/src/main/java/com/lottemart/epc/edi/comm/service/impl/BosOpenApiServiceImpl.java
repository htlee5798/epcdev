package com.lottemart.epc.edi.comm.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.epc.common.util.BosOpenApiCaller;
import com.lottemart.epc.edi.comm.dao.BosOpenApiDao;
import com.lottemart.epc.edi.comm.service.BosOpenApiService;

@Service(value="bosOpenApiService")
public class BosOpenApiServiceImpl implements BosOpenApiService {
	
	private static final Logger logger = LoggerFactory.getLogger(BosOpenApiServiceImpl.class);
	private static final String OK_CODE = "S";
	private static final String NO_CODE = "F";
	private static final int MAX_BULK_INS = 4000;			//한번에 인서트 가능한 data 개수 (ibatis 최대 파라미터 65535 개 / 컬럼수 하여 적절한 사이즈로 설정. 250602 기준 4000넘으면 안됨)
	
	@Autowired
	BosOpenApiDao bosOpenApiDao;
	
	@Autowired
	BosOpenApiCaller bosOpenApiCaller;
	
	/**
	 * [API] BOS 기준정보 데이터 SELECT From BOS
	 */
	private List<Map<String, Object>> selectBaseInfoDataFromBos(String ifCd, Map<String, Object> paramMap, String batchLogId) throws Exception{
		List<Map<String,Object>> datalist = null;
		
		//API 호출
		Map<String,Object> apiResult = bosOpenApiCaller.call(ifCd, paramMap, batchLogId);
		//api 커넥션 결과 없음
		if(apiResult == null) {
			logger.error("selectBaseInfoDataFromBos("+ifCd+") - apiResult is Null :");
			return null;
		}
		
		String apiRsltCd = StringUtils.defaultString(MapUtils.getString(apiResult, "msgCd"));		//API 호출 결과코드
		String apiRsltMsg = StringUtils.defaultString(MapUtils.getString(apiResult, "message"));	//API 호출 결과메세지
		
		//api call 실패
		if(!OK_CODE.equals(apiRsltCd)) {
			logger.error("selectBaseInfoDataFromBos("+ifCd+") - api call is Fail : "+apiRsltMsg);
			return null;
		}
		
		//데이터리스트 추출
		datalist = (List<Map<String, Object>>) apiResult.get("resultData");
		
		logger.info("API_RTN_CNT : "+datalist.size());
		
		if(datalist == null || datalist.isEmpty() || datalist.size() == 0) {
			logger.info("selectBaseInfoDataFromBos("+ifCd+") - no data found. :"+apiRsltMsg);
			return null;
		}
		
		logger.info("API_RTN_CODE : "+apiRsltCd);
		logger.info("API_RTN_MSG : "+ apiRsltMsg);
		return datalist;
	}

	/**
	 * BOS 수신 기준정보 임시테이블 데이터 등록 
	 */
	public Map<String,Object> insertBaseInfoTempData(String ifCd, Map<String,Object> paramMap, String batchLogId) throws Exception{
		Map<String, Object> result = new HashMap<String,Object>();
		result.put("msgCd", NO_CODE);
		result.put("message", "insertBaseInfoTempData("+ifCd+") - System error has occurred.");
		
		/* 1. 기준정보 데이터 SELECT API 호출 ---------------------- */
		List<Map<String,Object>> datalist = selectBaseInfoDataFromBos(ifCd, paramMap, batchLogId);
		
		/* 2. I/F 대상 데이터 존재 여부 확인 ------------------------ */
		int resCnt = (datalist == null)?0:datalist.size();
//		if(resCnt == 0) {
//			result.put("msgCd", "E01");
//			result.put("message", "insertBaseInfoTempData("+ifCd+") - No Data Found!");
//			return result;	
//		}
		
		/* 3. 임시 테이블 데이터 등록 ------------------------------ */
//		try {
			/*
			 * 인터페이스 유형 별 실행
			 * 3-1) TEMP TABLE TRUNCATE
			 * 3-2) TEMP TABLE BULK INSERT 
			 */
			switch(ifCd) {
				case "001" :  //전상법 템플릿 마스터 수신
					bosOpenApiDao.deleteTmpProdAddInfoMst();			//TEMP TRUNCATE
					break;
				case "002" :  //전상법 템플릿 상세 수신
					bosOpenApiDao.deleteTmpProdAddInfoDet();			//TEMP TRUNCATE
					break;
				case "003" :  //KC인증 품목 마스터 수신
					bosOpenApiDao.deleteTmpProdCertInfoMst();			//TEMP TRUNCATE
					break;
				case "004" :  //KC인증 품목 상세 수신
					bosOpenApiDao.deleteTmpProdCertInfoDet();			//TEMP TRUNCATE
					break;
				case "005" :  //EC 표준 카테고리 조회
					bosOpenApiDao.deleteTmpStdCategory();			//TEMP TRUNCATE
					break;
				case "006" :  //EC 전시 카테고리 조회
					bosOpenApiDao.deleteTmpDispCategory();			//TEMP TRUNCATE
					break;
				case "007" :  //EC 표준/전시 카테고리 매핑정보 조회
					bosOpenApiDao.deleteTmpStdDispCategoryMapping();			//TEMP TRUNCATE
					break;
				case "008" :  //EC 표준/마트 카테고리 매핑정보 조회
					bosOpenApiDao.deleteTmpStdCatCategoryMapping();			//TEMP TRUNCATE
					break;
				case "009" :  //EC 표준카테고리 / EC 상품속성 매핑정보 조회
					bosOpenApiDao.deleteTmpAttrCatCategoryMapping();			//TEMP TRUNCATE
					break;
				case "010" :  //EC 상품 속성코드(마스터) 조회
					bosOpenApiDao.deleteTmpAttrCd();			//TEMP TRUNCATE
					break;
				case "011" :  //EC 상품 속성값(아이템) 조회
					bosOpenApiDao.deleteTmpAttrVal();			//TEMP TRUNCATE
					break;
				case "012" :  //온라인 공통코드 조회
					bosOpenApiDao.deleteTmpTetCode();			//TEMP TRUNCATE
					break;
//				case "013" :  //온라인 전시 카테고리 조회
//					bosOpenApiDao.deleteTmpCategory();			//TEMP TRUNCATE
//					break;
//				case "014" :  //마트 기간계 카테고리 / 온라인 전시 카테고리 매핑정보 조회
//					bosOpenApiDao.deleteTmpCategoryMapping();			//TEMP TRUNCATE
//					break;
//				case "015" :  //팀 카테고리 매핑정보 조회
//					bosOpenApiDao.deleteTmpL1Cd();			//TEMP TRUNCATE
//					break;
				case "016" :  //신상품 - 오카도(롯데마트제타) 카테고리 입력 데이터 조회
					bosOpenApiDao.deleteTmpOspCatProdMapping();			//TEMP TRUNCATE
					break;
//				case "017" :  //오카도 전시 카테고리 조회
//					bosOpenApiDao.deleteTmpPdDcat();			//TEMP TRUNCATE
//					break;
//				case "018" :  //오카도 전시 카테고리 / 마트 소분류 코드 매핑정보 조회
//					bosOpenApiDao.deleteTmpPdDcatScatMpng();			//TEMP TRUNCATE
//					break;
				case "019" :  //브랜드 정보 조회
					bosOpenApiDao.deleteTmpTprBrand();			//TEMP TRUNCATE
					break;
				case "020" :  //EC 패션 속성값 페이지 전시 유무 전송 조회
					bosOpenApiDao.deleteTmpEcAttDisplay();			//TEMP TRUNCATE
					break;
				case "021" :  //EDI 공통코드 조회
					bosOpenApiDao.deleteTmpTpcCode();			//TEMP TRUNCATE
					break;
				case "022" :  //영양성분 마스터 조회
					bosOpenApiDao.deleteTmpTpcNutMst();			//TEMP TRUNCATE
					break;
				case "023" :  //영양성분 소분류 매핑 정보 조회
					bosOpenApiDao.deleteTmpTpcNutL3Cd();		//TEMP TRUNCATE
					break;
				case "025" :  //오프라인카테고리분류 조회
					bosOpenApiDao.deleteTmpTcaMdCategory();		//TEMP TRUNCATE
					break;
				default:
					break;
			}
			
			//Parameter Map For Insert DataList
			if(datalist != null && datalist.size() > 0) {
				Map<String,Object> insParam = new HashMap<String,Object>();
				insParam.put("DATA_LIST", datalist);
				
				List<Map<String,Object>> insList = null;
				int lstIdx = 0;
				for(int i = 0; i<datalist.size(); i += MAX_BULK_INS) {
					lstIdx = Math.min(i+MAX_BULK_INS, datalist.size());
					insList = datalist.subList(i, lstIdx);
					insParam.put("DATA_LIST", insList);
					
					switch(ifCd) {
						case "001" :  //전상법 템플릿 마스터 수신
							bosOpenApiDao.insertTmpProdAddInfoMst(insParam);	//TEMP INSERT
							break;
						case "002" :  //전상법 템플릿 상세 수신
							bosOpenApiDao.insertTmpProdAddInfoDet(insParam);	//TEMP INSERT
							break;
						case "003" :  //KC인증 품목 마스터 수신
							bosOpenApiDao.insertTmpProdCertInfoMst(insParam);	//TEMP INSERT
							break;
						case "004" :  //KC인증 품목 상세 수신
							bosOpenApiDao.insertTmpProdCertInfoDet(insParam);	//TEMP INSERT
							break;
						case "005" :  //EC 표준 카테고리 조회
							bosOpenApiDao.insertTmpStdCategory(insParam);	//TEMP INSERT
							break;
						case "006" :  //EC 전시 카테고리 조회
							bosOpenApiDao.insertTmpDispCategory(insParam);	//TEMP INSERT
							break;
						case "007" :  //EC 표준/전시 카테고리 매핑정보 조회
							bosOpenApiDao.insertTmpStdDispCategoryMapping(insParam);	//TEMP INSERT
							break;
						case "008" :  //EC 표준/마트 카테고리 매핑정보 조회
							bosOpenApiDao.insertTmpStdCatCategoryMapping(insParam);	//TEMP INSERT
							break;
						case "009" :  //EC 표준카테고리 / EC 상품속성 매핑정보 조회
							bosOpenApiDao.insertTmpAttrCatCategoryMapping(insParam);	//TEMP INSERT
							break;
						case "010" :  //EC 상품 속성코드(마스터) 조회
							bosOpenApiDao.insertTmpAttrCd(insParam);	//TEMP INSERT
							break;
						case "011" :  //EC 상품 속성값(아이템) 조회
							bosOpenApiDao.insertTmpAttrVal(insParam);	//TEMP INSERT
							break;
						case "012" :  //온라인 공통코드 조회
							bosOpenApiDao.insertTmpTetCode(insParam);	//TEMP INSERT
							break;
//						case "013" :  //온라인 전시 카테고리 조회
//							bosOpenApiDao.insertTmpCategory(insParam);	//TEMP INSERT
//							break;
//						case "014" :  //마트 기간계 카테고리 / 온라인 전시 카테고리 매핑정보 조회
//							bosOpenApiDao.insertTmpCategoryMapping(insParam);	//TEMP INSERT
//							break;
//						case "015" :  //팀 카테고리 매핑정보 조회
//							bosOpenApiDao.insertTmpL1Cd(insParam);	//TEMP INSERT
//							break;
						case "016" :  //신상품 - 오카도(롯데마트제타) 카테고리 입력 데이터 조회
							bosOpenApiDao.insertTmpOspCatProdMapping(insParam);	//TEMP INSERT
							break;
//						case "017" :  //오카도 전시 카테고리 조회
//							bosOpenApiDao.insertTmpPdDcat(insParam);	//TEMP INSERT
//							break;
//						case "018" :  //오카도 전시 카테고리 / 마트 소분류 코드 매핑정보 조회
//							bosOpenApiDao.insertTmpPdDcatScatMpng(insParam);	//TEMP INSERT
//							break;
						case "019" :  //브랜드 정보 조회
							bosOpenApiDao.insertTmpTprBrand(insParam);	//TEMP INSERT
							break;
						case "020" :  //EC 패션 속성값 페이지 전시 유무 전송 조회
							bosOpenApiDao.insertTmpEcAttDisplay(insParam);	//TEMP INSERT
							break;
						case "021" :  //EDI 공통코드 조회
							bosOpenApiDao.insertTmpTpcCode(insParam);	//TEMP INSERT
							break;
						case "022" :  //영양성분 마스터 조회
							bosOpenApiDao.insertTmpTpcNutMst(insParam);	//TEMP INSERT
							break;
						case "023" :  //영양성분 소분류 매핑 정보 조회
							bosOpenApiDao.insertTmpTpcNutL3Cd(insParam);//TEMP INSERT
							break;
						case "025" :  //오프라인카테고리분류 조회
							bosOpenApiDao.insertTmpTcaMdCategory(insParam); //TEMP INSERT
							break;
						default:
							break;
					}
				}
			}
			result.put("msgCd", OK_CODE);
			result.put("message", "SUCCESS");
			result.put("resCnt", resCnt);
//		}catch (Exception e) {
//			logger.error("insertBaseInfoTempData("+ifCd+") - TEMP INSERT ERROR : " + e.getMessage());
//			result.put("msgCd", "E98");
//			result.put("message", "TEMP INSERT ERROR : "+e.getMessage());
//			return result;
//		}finally {
//		}
		
		return result;
	}
	
	/**
	 * BOS 수신 기준정보 TEMP to REAL 이관
	 */
	public Map<String,Object> updateMigBaseInfoTmpToRealData(String ifCd, Map<String,Object> paramMap) throws Exception{
		Map<String, Object> result = new HashMap<String,Object>();
		result.put("msgCd", NO_CODE);
		result.put("message", "updateMigBaseInfoTmpToRealData("+ifCd+") - System error has occurred.");
		
		int reccnt = 0;
//		try {
			switch(ifCd) {
				case "001" :  //전상법 템플릿 마스터 수신
					reccnt += bosOpenApiDao.mergeTprProdAddInfoMst(paramMap);
					break;
				case "002" :  //전상법 템플릿 상세 수신
					reccnt += bosOpenApiDao.mergeTprProdAddInfoDet(paramMap);
					break;
				case "003" :  //KC인증 품목 마스터 수신
					reccnt += bosOpenApiDao.mergeTprProdCertInfoMst(paramMap);
					break;
				case "004" :  //KC인증 품목 상세 수신
					reccnt += bosOpenApiDao.mergeTprProdCertInfoDet(paramMap);
					break;
				case "005" :  //EC 표준 카테고리 조회
					reccnt += bosOpenApiDao.mergeTecStdCategory(paramMap);
					break;
				case "006" :  //EC 전시 카테고리 조회
					reccnt += bosOpenApiDao.mergeTecDispCategory(paramMap);
					break;
				case "007" :  //EC 표준/전시 카테고리 매핑정보 조회
					reccnt += bosOpenApiDao.mergeTecStdDispCategoryMapping(paramMap);
					break;
				case "008" :  //EC 표준/마트 카테고리 매핑정보 조회
					reccnt += bosOpenApiDao.mergeTecStdCatCategoryMapping(paramMap);
					break;
				case "009" :  //EC 표준카테고리 / EC 상품속성 매핑정보 조회
					reccnt += bosOpenApiDao.mergeTecAttrCatCategoryMapping(paramMap);
					break;
				case "010" :  //EC 상품 속성코드(마스터) 조회 
					reccnt += bosOpenApiDao.mergeTecAttrCd(paramMap);
					break;
				case "011" :  //EC 상품 속성값(아이템) 조회
					reccnt += bosOpenApiDao.mergeTecAttrVal(paramMap);
					break;
				case "012" :  //온라인 공통코드 조회
					reccnt += bosOpenApiDao.mergeTetCode(paramMap);
					break;
//				case "013" :  //온라인 전시 카테고리 조회
//					reccnt += bosOpenApiDao.mergeCategory(paramMap);
//					break;
//				case "014" :  //마트 기간계 카테고리 / 온라인 전시 카테고리 매핑정보 조회
//					reccnt += bosOpenApiDao.mergeCategoryMapping(paramMap);
//					break;
//				case "015" :  //팀 카테고리 매핑정보 조회
//					reccnt += bosOpenApiDao.mergeL1Cd(paramMap);
//					break;
				case "016" :  //신상품 - 오카도(롯데마트제타) 카테고리 입력 데이터 조회
					reccnt += bosOpenApiDao.mergeOspCatProdMapping(paramMap);
					break;
//				case "017" :  //오카도 전시 카테고리 조회
//					reccnt += bosOpenApiDao.mergePdDcat(paramMap);
//					break;
//				case "018" :  //오카도 전시 카테고리 / 마트 소분류 코드 매핑정보 조회
//					reccnt += bosOpenApiDao.mergePdDcatScatMpng(paramMap);
//					break;
				case "019" :  //브랜드 정보 조회
					reccnt += bosOpenApiDao.mergeTprBrand(paramMap);
					break;
				case "020" :  //EC 패션 속성값 페이지 전시 유무 전송 조회
					reccnt += bosOpenApiDao.mergeEcAttDisplay(paramMap);
					break;
				case "021" :  //EDI 공통코드 조회
					reccnt += bosOpenApiDao.mergeTpcCode(paramMap);
					break;
				case "022" :  //영양성분 마스터 조회
					reccnt += bosOpenApiDao.mergeTpcNutMst(paramMap);			
					break;
				case "023" :  //영양성분 소분류 매핑 정보 조회
					reccnt += bosOpenApiDao.mergeTpcNutL3Cd(paramMap);
					break;
				case "025" :  //오프라인카테고리분류 조회
					reccnt += bosOpenApiDao.mergeTcaMdCategory(paramMap);
					break;
				default:
					break;
			}
			result.put("msgCd", OK_CODE);
			result.put("message", "SUCCESS");
			result.put("reccnt", reccnt);
//		}catch(Exception e) {
//			logger.error("updateMigBaseInfoTmpToRealData("+ifCd+") - BASE INFO DATA MERGE ERROR : " + e.getMessage());
//			result.put("msgCd", "E98");
//			result.put("message", "TPR_PROD_ADD_INFO_MST MERGE ERROR : "+e.getMessage());
//			return result;
//		}finally {
//		}		
		
		return result;
	}
	
	/**
	 * BOS API 호출 URL 조회
	 */
	public String selectBosOpenApiUrl(String ifCd){
		String apiUrl = "";
		try {
			apiUrl = bosOpenApiDao.selectBosApiUrl(ifCd);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return apiUrl;
		}
		return apiUrl;
	}

	/**
	 * 온오프상품등록 Key(PGM_ID) 채번
	 */
	public String selectTpcNewProdRegKey() {
		String ifCd = "024";	//인터페이스코드 - 024: 온오프 상품등록 key 채번
		String pgmId = "";		//신상품코드
		
		Map<String,Object> resultDataMap = null;
		
		//API 호출
		Map<String,Object> apiResult = bosOpenApiCaller.call(ifCd);
		//api 커넥션 결과 없음
		if(apiResult == null) {
			logger.error("selectTpcNewProdRegKey("+ifCd+") - apiResult is Null :");
			return "";
		}
		
		String apiRsltCd = StringUtils.defaultString(MapUtils.getString(apiResult, "msgCd"));		//API 호출 결과코드
		String apiRsltMsg = StringUtils.defaultString(MapUtils.getString(apiResult, "message"));	//API 호출 결과메세지
		
		//api call 실패
		if(!OK_CODE.equals(apiRsltCd)) {
			logger.error("selectTpcNewProdRegKey("+ifCd+") - api call is Fail : "+apiRsltMsg);
			return "";
		}
		
		//데이터맵 추출
		resultDataMap = (Map<String, Object>) apiResult.get("resultData");
		
		//데이터맵 null이거나 PGM_ID 채번 안됐을 경우,
		if(resultDataMap == null || !resultDataMap.containsKey("PGM_ID")) {
			logger.error("selectTpcNewProdRegKey("+ifCd+") - no data found.(PGM_ID) :"+apiRsltMsg);
			return "";
		}
		
		//채번된 신상품코드
		pgmId = MapUtils.getString(resultDataMap, "PGM_ID", "");
		
		logger.info("API_RTN_CODE : "+apiRsltCd);
		logger.info("API_RTN_MSG : "+ apiRsltMsg);
		return pgmId;
	}

	/**
	 * 신상품등록 ERP 자동발송 성공 건 BOS 전송
	 */
	@Override
	public Map<String, Object> insertNewProdAutoSendListToBos(Map<String, Object> paramMap, String batchId) throws Exception {
		String ifCd = "102";	//인터페이스코드 - 102 : BOS 미승인 건 EPC 자동승인 결과 전송 (온라인 확정 처리)
		
		Map<String, Object> result = new HashMap<String,Object>();
		result.put("msgCd", NO_CODE);
//		result.put("message", "insertNewProdAutoSendListToBos("+ifCd+") - System error has occurred.");
		
		/*
		 * 1. 필수 파라미터 확인
		 */
		if(paramMap == null || paramMap.isEmpty()) {
			logger.error("insertNewProdAutoSendListToBos("+ifCd+") - paramMap is empty");
			result.put("message", "전송할 데이터가 없습니다.");
			return result;
		}
		
		if(!paramMap.containsKey("pgmIdList")) {
			logger.error("insertNewProdAutoSendListToBos("+ifCd+") - paramMap not contains pgmIdList");
			result.put("message", "전송할 자동발송 정보가 없습니다.");
			return result;
		}
		
		//자동발송 성공 pgmId list
		List<String> pgmIdList = (List<String>) MapUtils.getObject(paramMap, "pgmIdList");
		
		if(pgmIdList == null || pgmIdList.isEmpty() || pgmIdList.size() == 0) {
			logger.error("insertNewProdAutoSendListToBos("+ifCd+") - pgmIdList is empty");
			result.put("message", "전송할 자동발송 정보가 없습니다.");
			return result;
		}
		
		/*
		 * 2. API 호출
		 */
		Map<String,Object> apiResult = bosOpenApiCaller.call(ifCd, paramMap, batchId);
		
		//api 커넥션 결과 없음
		if(apiResult == null) {
			logger.error("insertNewProdAutoSendListToBos("+ifCd+") - apiResult is Null :");
			result.put("message", "전송 중 오류가 발생했습니다.");
			return result;
		}
		
		String apiRsltCd = StringUtils.defaultString(MapUtils.getString(apiResult, "msgCd"));		//API 호출 결과코드
		String apiRsltMsg = StringUtils.defaultString(MapUtils.getString(apiResult, "message"));	//API 호출 결과메세지
		
		//api call 실패
		if(!OK_CODE.equals(apiRsltCd)) {
			logger.error("insertNewProdAutoSendListToBos("+ifCd+") - api call is Fail : "+apiRsltMsg);
			result.put("message", "전송에 실패하였습니다. ("+apiRsltMsg+")");
			return result;
		}
		
		//BOS쪽 처리결과 확인
		Map<String,Object> resultData = MapUtils.getMap(apiResult, "resultData");
		String rsltCd = (resultData.containsKey("RESULT"))?MapUtils.getString(resultData, "RESULT", ""):"";	//API 반환결과 코드
		String rsltMsg = (resultData.containsKey("MSG"))?MapUtils.getString(resultData, "MSG", ""):"";		//API 반환결과 메세지
		
		//API 처리결과 반환 코드 확인
		if(!"S".equals(rsltCd)) {
			logger.error("insertNewProdAutoSendListToBos("+ifCd+") - api result Fail : "+rsltMsg);
			result.put("message", rsltMsg);
			return result;
		}
		
		result.put("msgCd", OK_CODE);
		result.put("message", "SUCCESS");
		return result;
	}
	
	/**
	 *  BOS 수신 기준정보 REAL DATA (DELETE > INSERT)
	 */
	public Map<String,Object> deleteInsertBaseInfoAll(String ifCd, Map<String,Object> paramMap, String batchLogId) throws Exception{
		Map<String, Object> result = new HashMap<String,Object>();
		result.put("msgCd", NO_CODE);
		result.put("message", "deleteInsertBaseInfoAll("+ifCd+") - System error has occurred.");
		
		/* 1. 기준정보 데이터 SELECT API 호출 ---------------------- */
		List<Map<String,Object>> datalist = selectBaseInfoDataFromBos(ifCd, paramMap, batchLogId);
		
		/* 2. I/F 대상 데이터 존재 여부 확인 ------------------------ */
		int resCnt = (datalist == null)?0:datalist.size();
//		if(resCnt == 0) {
//			result.put("msgCd", "E01");
//			result.put("message", "insertBaseInfoTempData("+ifCd+") - No Data Found!");
//			return result;	
//		}
		
		/* 3. 원본테이블 전체삭제 ------------------------------ */
		//Parameter Map For Insert DataList
		if(datalist != null && datalist.size() > 0) {
			/*
			 * 인터페이스 유형 별 실행
			 * 3-1) REAL TABLE DELETE (실 운영 테이블은 TRUNCATE 하면 안됨..!!!!)
			 * 3-2) REAL TABLE BULK INSERT 
			 */
			switch(ifCd) {
				case "013" :  //온라인 전시 카테고리 조회
					bosOpenApiDao.deleteRealTcaCategory();			//REAL DELETE
					break;
				case "014" :  //마트 기간계 카테고리 / 온라인 전시 카테고리 매핑정보 조회
					bosOpenApiDao.deleteRealTcaCategoryMapping();	//REAL DELETE
					break;
				case "015" :  //팀 카테고리 매핑정보 조회
					bosOpenApiDao.deleteRealTpcL1Cd();				//REAL DELETE
					break;
				case "017" :  //오카도 전시 카테고리 조회
					bosOpenApiDao.deleteRealPdDcat();				//REAL DELETE
					break;
				case "018" :  //오카도 전시 카테고리 / 마트 소분류 코드 매핑정보 조회
					bosOpenApiDao.deleteRealPdDcatScatMpng();		//REAL DELETE
					break;
				default:
					break;
			}
			
			Map<String,Object> insParam = new HashMap<String,Object>();
			insParam.put("DATA_LIST", datalist);
			
			List<Map<String,Object>> insList = null;
			int lstIdx = 0;
			for(int i = 0; i<datalist.size(); i += MAX_BULK_INS) {
				lstIdx = Math.min(i+MAX_BULK_INS, datalist.size());
				insList = datalist.subList(i, lstIdx);
				insParam.put("DATA_LIST", insList);
				
				switch(ifCd) {
					case "013" :  //온라인 전시 카테고리 조회
						bosOpenApiDao.insertRealTcaCategory(insParam);			//REAL INSERT
						break;
					case "014" :  //마트 기간계 카테고리 / 온라인 전시 카테고리 매핑정보 조회
						bosOpenApiDao.insertRealTcaCategoryMapping(insParam);	//REAL INSERT
						break;
					case "015" :  //팀 카테고리 매핑정보 조회
						bosOpenApiDao.insertRealTpcL1Cd(insParam);				//REAL INSERT
						break;
					case "017" :  //오카도 전시 카테고리 조회
						bosOpenApiDao.insertRealPdDcat(insParam);				//REAL INSERT
						break;
					case "018" :  //오카도 전시 카테고리 / 마트 소분류 코드 매핑정보 조회
						bosOpenApiDao.insertRealPdDcatScatMpng(insParam);		//REAL INSERT
						break;
					default:
						break;
				}
			}
		}
		result.put("msgCd", OK_CODE);
		result.put("message", "SUCCESS");
		result.put("resCnt", resCnt);
		return result;
	}
}
