package com.lottemart.epc.edi.product.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.epc.edi.product.dao.NEDMPRO0210Dao;
import com.lottemart.epc.edi.product.service.NEDMPRO0210Service;

@Service("NEDMPRO0210Service")
public class NEDMPRO0210ServiceImpl implements NEDMPRO0210Service {

	@Autowired
	private NEDMPRO0210Dao NEDMPRO0210Dao;

	
	/**
	 * 물류바코드 관리 - > 물류바코드 현황 조회
	 * @param map
	 * @return
	 */
	@Override
	public List selectBarcodeList(Map<String,Object> map) {
		// TODO Auto-generated method stub
		return NEDMPRO0210Dao.selectBarcodeList_2(map);	//2016.03.30 이후 버전
	}
	
}
