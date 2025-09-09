package com.lottemart.epc.edi.product.service;

import org.springframework.stereotype.Service;

import com.lottemart.epc.edi.product.model.NEDMPRO0080VO;

import java.util.List;
import java.util.Map;
import java.util.HashMap;


public interface NEDMPRO0080Service {
	/*
	 * 대분류 데이터 가져오기
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<HashMap> selectL1CdAll() throws Exception;
	
	/*
	 * 업체별 상품 및 분석속성 데이터 가져오기
	 * 
	 * @param NEDMPRO0080VO
	 * @return
	 * @throws Exception
	 */
	public List<HashMap> selectGrpAttrInfo(NEDMPRO0080VO vo) throws Exception;
	
	/*
	 * 업체별 상품 및 분석속성 값 개수 카운트
	 * 
	 * @param NEDMPRO0080VO
	 * @return
	 * @throws Exception
	 */
	public Integer countGrpAttrInfo(NEDMPRO0080VO vo) throws Exception;
	
	/*
	 * 업체 특정 상품 분석속성 승인정보 가져오기
	 * 
	 * @param Map<String, Object>
	 * @return
	 * @throws Exception
	 */
	public List<HashMap> selectAttrAprvInfoAProd(Map<String, Object> paramMap) throws Exception;
}