package com.lottemart.epc.edi.product.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.common.exception.AlertException;
import com.lottemart.epc.edi.product.dao.NEDMPRO0190Dao;
import com.lottemart.epc.edi.product.model.NEDMPRO0190VO;
import com.lottemart.epc.edi.product.service.NEDMPRO0190Service;

@Service
public class NEDMPRO0190ServiceImpl implements NEDMPRO0190Service{

	@Autowired
	NEDMPRO0190Dao nedmpro0190dao = new NEDMPRO0190Dao();

	// 업체별 상품 및 영양성분 데이터 가져오기
	@Override
	public List<HashMap> selectNutAttrInfo(NEDMPRO0190VO vo) throws Exception {
		return nedmpro0190dao.selectNutAttrInfo(vo);
	}

	// 업체별 상품 및 영양성분 값 개수 카운트
	@Override
	public Integer countNutAttrInfo(NEDMPRO0190VO vo) throws Exception {
		return nedmpro0190dao.countNutAttrInfo(vo);
	}

	// 업체 특정 상품 영양성분 승인정보 가져오기
	@Override
	public List<HashMap> selectProdNutAprv(Map<String, Object> paramMap) throws Exception {

		List<HashMap> prodNutAprv = null;

		if ("APRVPOSSIBLE".equals(paramMap.get("aprvStat"))) {
			prodNutAprv = nedmpro0190dao.selectNutWithProdL3Cd(paramMap);
		}

		if ("APRVING".equals(paramMap.get("aprvStat")) || "APRVDONE".equals(paramMap.get("aprvStat"))) {
			prodNutAprv = nedmpro0190dao.selectProdNutAprv(paramMap);
		}

		if (prodNutAprv == null || prodNutAprv.isEmpty()) {
			throw new AlertException("상품속성이 없다.");
		}

		return prodNutAprv;
	}

}
