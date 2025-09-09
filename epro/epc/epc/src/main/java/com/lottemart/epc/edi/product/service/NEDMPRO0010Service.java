package com.lottemart.epc.edi.product.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;


/**
 * @Class Name : NEDMPRO0010Service
 * @Description : 신상품현황조회 Service
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      			수정자           		수정내용
 *  -------    		--------    	---------------------------
 * 	2015.12.21		SONG MIN KYO	최초생성
 * </pre>
 */

public interface NEDMPRO0010Service {
	
	/**
	 * 신상품 등록 PGM_ID구해오기
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> selectNewProdFixList(Map<String, Object> paramMap) throws Exception;
	
	/**
	 * 신상품 현황조회 POG 이미지 저장
	 * @param paramMap
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String insertNewProdImgPop(Map<String, Object> paramMap, HttpServletRequest request) throws Exception;
}
