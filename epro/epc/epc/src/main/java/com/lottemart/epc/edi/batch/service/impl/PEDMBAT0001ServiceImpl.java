package com.lottemart.epc.edi.batch.service.impl;


import java.util.List;
import java.util.Map;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.edi.batch.dao.PEDMBAT0001Dao;
import com.lottemart.epc.edi.batch.model.FuelSale001VO;
import com.lottemart.epc.edi.batch.service.PEDMBAT0001Service;

@Service("pEDMBAT0001Service")
public class PEDMBAT0001ServiceImpl implements PEDMBAT0001Service {
	
	@Autowired
	private PEDMBAT0001Dao pEDMBAT0001Dao;
 
	public List<DataMap> selectFuesSale001SendDistinctDate(Map<String,Object> map) {
		// TODO Auto-generated method stub		
		return pEDMBAT0001Dao.selectFuesSale001SendDistinctDate(map);
	}
	
//	public List<DataMap> selectFuesSale001SendDistinctBill(String dateYYYYMMDD)  {
//		return pEDMBAT0001Dao.selectFuesSale001SendDistinctBill(dateYYYYMMDD);
//	}
	public List<DataMap> selectFuesSale001SendDistinctBill(Map<String,Object> map)  {
		return pEDMBAT0001Dao.selectFuesSale001SendDistinctBill(map);
	}
	
	
	public FuelSale001VO selectFuesSale001Send(Map<String,Object> map) {
		// TODO Auto-generated method stub		
		return pEDMBAT0001Dao.selectFuesSale001Send(map);
	}
		
	public void insertFuesSale001log(FuelSale001VO tmpFuelSale001VO)  {
		pEDMBAT0001Dao.insertFuesSale001log(tmpFuelSale001VO);		
	}
	

	

}
