package com.lottemart.epc.edi.product.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lottemart.epc.edi.product.model.NEDMPRO0190VO;


public interface NEDMPRO0190Service {	
	/*
	 * 업체별 상품 및 영양성분 데이터 가져오기
	 * 
	 * @param NEDMPRO0080VO
	 * @return
	 * @throws Exception
	 */
	public List<HashMap> selectNutAttrInfo(NEDMPRO0190VO vo) throws Exception;
	
	/*
	 * 업체별 상품 및 영양성분 값 개수 카운트
	 * 
	 * @param NEDMPRO0080VO
	 * @return
	 * @throws Exception
	 */
	public Integer countNutAttrInfo(NEDMPRO0190VO vo) throws Exception;
	
	/*
	 * 업체 특정 상품 영양성분 승인정보 가져오기
	 * 
	 * @param Map<String, Object>
	 * @return
	 * @throws Exception
	 */
	public List<HashMap> selectProdNutAprv(Map<String, Object> paramMap) throws Exception;
}