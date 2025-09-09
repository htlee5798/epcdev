package com.lottemart.epc.edi.weborder.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.lottemart.epc.edi.weborder.model.SearchWebOrder;
import com.lottemart.epc.edi.weborder.model.TedPoOrdPackList001VO;
import com.lottemart.epc.edi.weborder.model.TedPoOrdPackVO;



public interface PEDMWEB0003Service {

	Map<String,Object> selectOrdPackInfo(SearchWebOrder vo);
	
	public String insertOrdPackInfo(TedPoOrdPackList001VO vo, HttpServletRequest request, String ordVenCd) throws Exception;
	
	public String deleteExcelOrdInfo (TedPoOrdPackVO vo) throws Exception;
	
	public String insertExcelOrdInfo(TedPoOrdPackVO vo, HttpServletRequest request) throws Exception;
}
