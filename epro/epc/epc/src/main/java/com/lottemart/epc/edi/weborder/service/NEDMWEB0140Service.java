package com.lottemart.epc.edi.weborder.service;

import java.sql.SQLException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.lottemart.epc.edi.weborder.model.NEDMWEB0140VO;




public interface NEDMWEB0140Service {


	
	/**
	 * 반품 등록 내역 조회
	 * @param SearchWebOrder
	 * @return List<EdiRtnProdVO>
	 * @throws SQLException
	 */
	public Map<String,Object> selectDayRtnList(NEDMWEB0140VO vo,HttpServletRequest request)  throws Exception ;
	
}
