package com.lottemart.epc.edi.product.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.epc.edi.product.dao.NEDMPRO0080Dao;
import com.lottemart.epc.edi.product.model.NEDMPRO0080VO;
import com.lottemart.epc.edi.product.service.NEDMPRO0080Service;

@Service
public class NEDMPRO0080ServiceImpl implements NEDMPRO0080Service{

	@Autowired 
	NEDMPRO0080Dao nedmpro0080dao = new NEDMPRO0080Dao();
	
	// 대분류 데이터 가져오기
	@Override
	public List<HashMap> selectL1CdAll() throws Exception {
		return nedmpro0080dao.selectL1CdAll();
	}

	// 업체별 상품 및 분석속성 데이터 가져오기
	@Override
	public List<HashMap> selectGrpAttrInfo(NEDMPRO0080VO vo) throws Exception {
		return nedmpro0080dao.selectGrpAttrInfo(vo);
	}
	
	// 업체별 상품 및 분석속성 값 개수 카운트
	@Override
	public Integer countGrpAttrInfo(NEDMPRO0080VO vo) throws Exception {
		return nedmpro0080dao.countGrpAttrInfo(vo);
	}

	// 업체 특정 상품 분석속성 승인정보 가져오기
	@Override
	public List<HashMap> selectAttrAprvInfoAProd(Map<String, Object> paramMap) throws Exception {
		
		List<HashMap> attrAprvInfoAProd = null;
		
		if (paramMap.get("aprvStat").equals("APRVPOSSIBLE")) {
			attrAprvInfoAProd = nedmpro0080dao.selectAttrInfoAProd(paramMap);
		}
		
		if (paramMap.get("aprvStat").equals("APRVING") 
				|| paramMap.get("aprvStat").equals("APRVDONE")) {
			attrAprvInfoAProd = nedmpro0080dao.selectAttrAprvInfoAProd(paramMap);
		}
		
		/*
		if (attrAprvInfoAProd.isEmpty()) {
			throw new Exception("상품속성이 없다.");
		}
		*/
		
		return attrAprvInfoAProd;
	}

}
