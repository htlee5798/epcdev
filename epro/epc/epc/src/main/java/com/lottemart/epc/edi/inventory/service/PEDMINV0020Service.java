package com.lottemart.epc.edi.inventory.service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lottemart.epc.edi.inventory.model.PEDMINV0020VO;

public interface PEDMINV0020Service {
	
	public List<PEDMINV0020VO> selectBadProdInfo(Map<String,Object> map ) throws Exception;
	public PEDMINV0020VO selectBadProdPopupInfo(Map<String,Object> map ) throws Exception;
	public void selectBadProdPopupUpdate(Map<String,Object> map ) throws Exception;
	
}

