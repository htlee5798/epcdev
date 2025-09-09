package com.lottemart.epc.edi.usply.service;
import java.util.List;
import java.util.Map;


public interface PEDMUSP0000Service {
	
	public List selectDayInfo(Map<String,Object> map ) throws Exception;
	public List selectStoreInfo(Map<String,Object> map ) throws Exception;
	public List selectProductInfo(Map<String,Object> map ) throws Exception;
	public List selectProductDetailInfo(Map<String,Object> map ) throws Exception;
	public List selectUsplyReasonInfo(Map<String,Object> map ) throws Exception;
	public List selectPenaltyInfo(Map<String,Object> map ) throws Exception;
	
	public void selectUsplyReasonUpdate(String[] tmpusp1, String[] tmpusp2, String[] tmpprod, String[] tmpordno) throws Exception;
	
	
	
	
}

