package com.lottemart.epc.edi.weborder.service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.lottemart.epc.edi.weborder.model.SearchWebOrder;


public interface PEDMWEB0011Service {
	
	
	/**
	 * 협력사코드별  발주전제현황 조회
	 * @Method Name : selectVenOrdAllInfo
	 * @param SearchWebOrder
	 * @param HttpServletRequest
	 * @return  Map<String, String>
	 */
	public Map<String,Object> selectVenOrdAllInfo(SearchWebOrder vo ,HttpServletRequest request) throws Exception;

	

}

