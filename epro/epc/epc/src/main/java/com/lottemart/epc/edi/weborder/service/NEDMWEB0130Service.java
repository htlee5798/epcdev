package com.lottemart.epc.edi.weborder.service;

import java.sql.SQLException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.lottemart.epc.edi.weborder.model.EdiRtnPackListVO;
import com.lottemart.epc.edi.weborder.model.EdiRtnPackVO;
import com.lottemart.epc.edi.weborder.model.SearchWebOrder;




public interface NEDMWEB0130Service {
	/**
	 * 반품 일괄 등록 저장
	 * @param EdiRtnPackListVO
	 * @return String
	 * @throws SQLException
	 */
	Map<String,Object> selectRtnPackInfo(SearchWebOrder vo);
	
	
	/**
	 * 반품 일괄등록 정보 삭제
	 * @param EdiRtnPackVO
	 * @return String
	 * @throws SQLException
	 */
	public String deleteExcelRtnInfo (EdiRtnPackVO vo) throws Exception;
	
	
	/**
	 * 반품 일괄등록 반품요청
	 * @Method Name : sendReturnProdData
	 * @param EdiRtnProdProcessVO
	 * @return HashMap<String, String>
	 */
	public Map<String, Object> insertSendReturnProdData(SearchWebOrder vo, HttpServletRequest request)  throws Exception;
	
	
	/**
	 * 반품 일괄 등록 저장
	 * @param EdiRtnPackListVO
	 * @return String
	 * @throws SQLException
	 */
	public String insertRtnPackInfo(EdiRtnPackListVO vo, HttpServletRequest request, String ordVenCd) throws Exception;
}
