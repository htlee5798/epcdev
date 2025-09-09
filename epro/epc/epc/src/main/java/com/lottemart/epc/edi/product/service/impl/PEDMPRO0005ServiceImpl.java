package com.lottemart.epc.edi.product.service.impl;

import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.epc.edi.product.dao.PEDMPRO0005Dao;

import com.lottemart.epc.edi.product.service.PEDMPRO0005Service;

@Service("pEDMPRO0005Service")
public class PEDMPRO0005ServiceImpl implements PEDMPRO0005Service {

	@Autowired
	private PEDMPRO0005Dao pEDMPRO0005Dao;

	

	@Override
	public List selectBarcodeList(Map<String,Object> map) {
		// TODO Auto-generated method stub
		return pEDMPRO0005Dao.selectBarcodeList(map);
	}
	
}
