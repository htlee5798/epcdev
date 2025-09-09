package com.lottemart.epc.edi.weborder.service;

import java.sql.SQLException;
import java.util.List;

import com.lottemart.epc.edi.weborder.model.NEDMWEB0120VO;

public interface NEDMWEB0120Service {
	
	/**
	 *  N 당일 반품등록 내역 조회
	 * @param NEDMWEB0120VO
	 * @return List<NEDMWEB0120VO>
	 * @throws SQLException
	 */
	public List<NEDMWEB0120VO> NEDMWEB0120selectDayRtnProdList(NEDMWEB0120VO vo)  throws Exception;
	
	/**
	 * 수량등록
	 * @param NEDMWEB0120VO
	 * @throws SQLException
	 */
	public String updateReturnProdData(NEDMWEB0120VO vo) throws Exception;
	/**
	 * 삭제
	 * @param NEDMWEB0120VO
	 * @throws SQLException
	 */
	public String deleteReturnProdData(NEDMWEB0120VO vo) throws Exception;
	/**
	 * 반품요청
	 * @param NEDMWEB0120VO
	 * @throws SQLException
	 */
	public String sendReturn(NEDMWEB0120VO vo,String adminId) throws Exception;
	
}
