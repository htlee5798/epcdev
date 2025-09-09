package com.lottemart.epc.edi.comm.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @Class Name : EpcRestApiService.java
 * @Description : EPC REST API 공통  
 * @Modification Information
 * 
 *               <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      			수정자           		수정내용
 *  -------    		--------    	---------------------------
 * 	2025.06.05		yun				최초생성
 *               </pre>
 */
public interface EpcRestApiService {
	
	/**
	 * [RESTAPI > BOS] 신상품 승인상태 변경
	 * @param paramMap
	 * @param req
	 * @return Map<String,Object>
	 * @throws Exception
	 */
	public Map<String,Object> updateTpcNewProdStsCfrm(Map<String,Object> paramMap, HttpServletRequest req) throws Exception;
	
	/**
	 * [RESTAPI > 협력사로그인] 협력사 로그인 KEY발번 
	 * @param paramMap
	 * @param req
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	public Map<String,Object> updateMakeVendorLoginKey(Map<String,Object> paramMap, HttpServletRequest req) throws Exception;
	
}
