package com.lottemart.epc.edi.weborder.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.lottemart.epc.edi.weborder.model.NEDMWEB0110VO;




public interface NEDMWEB0110Service {


	
	/**
     * 반품상품 저장
     * @param EdiRtnProdVO
     * @return HashMap<String, String> 
     * @throws Exception
    */
	public HashMap<String, String> insertReturnProdData(NEDMWEB0110VO vo, HttpServletRequest request) throws Exception, RuntimeException;
	
	
	
	/**
	 *  N 당일 반품등록 내역 조회
	 * @param SearchWebOrder
	 * @return List<EdiRtnProdVO>
	 * @throws SQLException
	 */
	public List<NEDMWEB0110VO> NEDMWEB0110selectDayRtnProdList(NEDMWEB0110VO vo)  throws Exception ;
	
	
	
	
	
}
