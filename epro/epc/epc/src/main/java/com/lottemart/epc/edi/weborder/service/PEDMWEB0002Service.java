package com.lottemart.epc.edi.weborder.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.lottemart.epc.edi.weborder.model.SearchWebOrder;
import com.lottemart.epc.edi.weborder.model.TedOrdProcess001VO;



public interface PEDMWEB0002Service {


	Map<String,Object> selectProdOrdList(SearchWebOrder vo);
	
	Map<String,Object> selectProdOrdDetList(SearchWebOrder vo);
	
	public String insertProdOrdTemp(TedOrdProcess001VO vo, HttpServletRequest request) throws Exception;
	
	public String deleteProdOrdInfo (TedOrdProcess001VO vo) throws Exception;
}
