package com.lottemart.epc.edi.order.service;
import java.util.List;
import java.util.Map;


public interface PEDMORD0020Service {
	
	public List selectOrdAble(Map<String,Object> map ) throws Exception;
	public List selectOrdSply(Map<String,Object> map ) throws Exception;
	public void updateOrdSplyTime(String[] tmpTime, String[] tmpordno ,String[] tmpprodno) throws Exception;
	public void updateOrdSply(Map<String,Object> map ) throws Exception;
	
}

