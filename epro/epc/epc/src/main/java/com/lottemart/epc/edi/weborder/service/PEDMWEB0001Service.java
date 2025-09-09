package com.lottemart.epc.edi.weborder.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.lottemart.epc.edi.weborder.model.SearchWebOrder;
import com.lottemart.epc.edi.weborder.model.TedOrdProcess001VO;



public interface PEDMWEB0001Service {


	Map<String,Object> selectStoreOrdList(SearchWebOrder vo);
	
	Map<String,Object> selectVendorException(SearchWebOrder vo);
	
	/**
	 * 점포 정보 조회 [점포코드 필수]
	 * @param SearchWebOrder
	 * @return void
	 * @throws Exception
	 */
	public Map<String,Object> selectStoreOrdListFixedStr(SearchWebOrder vo) throws Exception;
	
	Map<String,Object> selectStoreOrdDetList(SearchWebOrder vo);
	
	public String insertStoreOrdTemp(TedOrdProcess001VO vo, HttpServletRequest request) throws Exception;
	
	public String deleteStoreOrdInfo (TedOrdProcess001VO vo) throws Exception;
	
	public String upadteApprReqInfo (SearchWebOrder vo, HttpServletRequest request) throws Exception;
	
}
