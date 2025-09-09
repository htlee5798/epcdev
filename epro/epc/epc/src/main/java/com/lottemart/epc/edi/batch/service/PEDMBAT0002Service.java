package com.lottemart.epc.edi.batch.service;

import java.util.List;
import java.util.Map;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.edi.batch.model.FuelCalc001VO;



public interface PEDMBAT0002Service {	
	
	List<DataMap> selectFuesSale002SendDistinctDate(Map<String,Object> map);
	
	//FuelCalc001VO selectFuesSale002Send(String dateYYYYMMDD);
	FuelCalc001VO selectFuesSale002Send(Map<String,Object> map);
	
	void insertFuesSale002log(FuelCalc001VO fuelsale001VO); //test

}
