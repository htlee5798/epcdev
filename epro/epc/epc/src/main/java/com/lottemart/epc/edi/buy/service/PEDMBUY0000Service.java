package com.lottemart.epc.edi.buy.service;
import java.util.List;
import java.util.Map;


public interface PEDMBUY0000Service {
	
	public List selectBuyInfo(Map<String,Object> map ) throws Exception;
	public List selectStoreInfo(Map<String,Object> map ) throws Exception;
	public List selectProductInfo(Map<String,Object> map ) throws Exception;
	public List selectJunpyoInfo(Map<String,Object> map ) throws Exception;
	public List selectJunpyoDetailInfo(Map<String,Object> map ) throws Exception;
	public List selectStoreProductInfo(Map<String,Object> map ) throws Exception;
	public List selectStorePurchaseInfo(Map<String,Object> map ) throws Exception;
	public List selectGiftInfo(Map<String,Object> map ) throws Exception;
	
	
	
	
}

