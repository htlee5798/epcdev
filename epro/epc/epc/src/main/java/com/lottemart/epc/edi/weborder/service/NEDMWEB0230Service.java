package com.lottemart.epc.edi.weborder.service;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.lottemart.epc.edi.weborder.model.NEDMWEB0230VO;
import com.lottemart.epc.edi.weborder.model.SearchWebOrder;



public interface NEDMWEB0230Service {

	
	/**
	 * 협력사코드별  반품전제현황 조회
	 * @Method Name : selectVenOrdAllInfo
	 * @param NEDMWEB0230VO
	 * @param HttpServletRequest
	 * @return  Map<String, String>
	 */
	public Map<String,Object> selectVenRtnInfo(NEDMWEB0230VO vo,HttpServletRequest request) throws Exception;

	

}

