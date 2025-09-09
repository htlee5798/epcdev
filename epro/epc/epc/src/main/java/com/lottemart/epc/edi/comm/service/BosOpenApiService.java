package com.lottemart.epc.edi.comm.service;

import java.util.List;
import java.util.Map;

/**
 * 
 * @Class Name : BosOpenApiService.java
 * @Description : EPC-BOS API 공통
 * @Modification Information
 * 
 *               <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      			수정자           		수정내용
 *  -------    		--------    	---------------------------
 * 	2025.06.04		yun				최초생성
 *               </pre>
 */
public interface BosOpenApiService {
	
	/**
	 * BOS 수신 기준정보 임시테이블 데이터 등록
	 * @param ifCd
	 * @param paramMap
	 * @param batchLogId
	 * @return Map<String,Object>
	 * @throws Exception
	 */
	public Map<String,Object> insertBaseInfoTempData(String ifCd, Map<String,Object> paramMap, String batchLogId) throws Exception;
	
	/**
	 * BOS 수신 기준정보 TEMP to REAL 이관
	 * @param ifCd
	 * @param paramMap
	 * @return Map<String,Object>
	 * @throws Exception
	 */
	public Map<String,Object> updateMigBaseInfoTmpToRealData(String ifCd, Map<String,Object> paramMap) throws Exception;
	
	/**
	 * BOS API 호출 URL 조회
	 * @param ifCd
	 * @return String
	 */
	public String selectBosOpenApiUrl(String ifCd);
	
	/**
	 * 온오프상품등록 Key(PGM_ID) 채번
	 * @return String
	 */
	public String selectTpcNewProdRegKey();

	/**
	 * 신상품등록 ERP 자동발송 성공 건 BOS 전송
	 * @param paramMap
	 * @param batchLogId
	 * @return Map<String,Object> 
	 * @throws Exception
	 */
	public Map<String,Object> insertNewProdAutoSendListToBos(Map<String,Object> paramMap, String batchLogId) throws Exception;
	
	/**
	 * BOS 수신 기준정보 REAL DATA (DELETE > INSERT)
	 * @param ifCd
	 * @param paramMap
	 * @param batchLogId
	 * @return Map<String,Object>
	 * @throws Exception
	 */
	public Map<String,Object> deleteInsertBaseInfoAll(String ifCd, Map<String,Object> paramMap, String batchLogId) throws Exception;
}
