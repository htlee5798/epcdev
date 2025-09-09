package com.lottemart.epc.edi.buy.service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public interface PEDMBUY0020Service {
	
	public List selectRejectInfo(Map<String,Object> map ) throws Exception;
	public HashMap selectRejectPopupInfo(Map<String,Object> map ) throws Exception;
	public void updateRejectPopup(Map<String,Object> map ) throws Exception;
	
}

