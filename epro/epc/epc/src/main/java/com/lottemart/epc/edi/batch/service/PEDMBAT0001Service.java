package com.lottemart.epc.edi.batch.service;

import java.util.List;
import java.util.Map;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.edi.batch.model.FuelSale001VO;



public interface PEDMBAT0001Service {	
	
	
	//List<DataMap> selectFuesSale001SendDistinctBill(String dateYYYYMMDD);
	List<DataMap> selectFuesSale001SendDistinctBill(Map<String,Object> map);
	
	List<DataMap> selectFuesSale001SendDistinctDate(Map<String,Object> map);
	
	//FuelSale001VO selectFuesSale001Send(String dateYYYYMMDD,String billnumber);
	FuelSale001VO selectFuesSale001Send(Map<String,Object> map);
	
	
	void insertFuesSale001log(FuelSale001VO fuelsale001VO); //test
	

	
	

}
