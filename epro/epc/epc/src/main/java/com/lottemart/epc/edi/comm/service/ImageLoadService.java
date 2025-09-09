package com.lottemart.epc.edi.comm.service;

import java.util.Map;

/**
 * 
 * @Class Name : ImageLoadService.java
 * @Description : 이미지 공통 load
 * @Modification Information
 * 
 *               <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      			수정자           		수정내용
 *  -------    		--------    	---------------------------
 * 	2025.09.01		yun				최초생성
 *               </pre>
 */
public interface ImageLoadService {
	
	/**
	 * ESG 이미지 정보 조회
	 * @param paramMap
	 * @return Map<String,Object>
	 * @throws Exception
	 */
	public Map<String,Object> selectImageInfoProductEsg(Map<String,Object> paramMap) throws Exception; 
	
	/**
	 * 신상품 입점제안 이미지 정보 조회
	 * @param paramMap
	 * @return Map<String,Object>
	 * @throws Exception
	 */
	public Map<String,Object> selectImageInfoNewProdProp(Map<String,Object> paramMap) throws Exception; 

}
