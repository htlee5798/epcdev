package com.lottemart.epc.edi.batch.service.impl;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.edi.batch.dao.PEDMBAT0002Dao;
import com.lottemart.epc.edi.batch.model.FuelCalc001VO;
import com.lottemart.epc.edi.batch.service.PEDMBAT0002Service;

@Service("pEDMBAT0002Service")
public class PEDMBAT0002ServiceImpl implements PEDMBAT0002Service {
	 
	@Autowired
	private PEDMBAT0002Dao pEDMBAT0002Dao;


	
	public List<DataMap> selectFuesSale002SendDistinctDate(Map<String,Object> map) {
		// TODO Auto-generated method stub		
		return pEDMBAT0002Dao.selectFuesSale002SendDistinctDate(map);
	}
	
	
//	public FuelCalc001VO selectFuesSale002Send(String dateYYYYMMDD) {
//		// TODO Auto-generated method stub
//		return pEDMBAT0002Dao.selectFuesSale002Send(dateYYYYMMDD);
//	}
	public FuelCalc001VO selectFuesSale002Send(Map<String,Object> map) {
		// TODO Auto-generated method stub
		return pEDMBAT0002Dao.selectFuesSale002Send(map);
	}
	
		
	public void insertFuesSale002log(FuelCalc001VO tmpFuelCalc001VO)  {
		pEDMBAT0002Dao.insertFuesSale002log(tmpFuelCalc001VO);
		
	}
}
