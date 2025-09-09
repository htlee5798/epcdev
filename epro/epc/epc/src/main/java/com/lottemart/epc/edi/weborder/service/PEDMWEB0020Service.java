package com.lottemart.epc.edi.weborder.service;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.lottemart.epc.edi.weborder.model.EdiPoEmpPoolVO;
import com.lottemart.epc.edi.weborder.model.SearchWebOrder;



public interface PEDMWEB0020Service {
	
	
	
	/**
	 * 사원목록 조회  
	 * @param SearchWebOrder
	 * @return List<EdiPoEmPoolVO>
	 * @throws SQLException
	 */
	public List<EdiPoEmpPoolVO> selectEmpPoolList(SearchWebOrder vo) throws Exception;
	
	/**
	 * 사원별 협력사 매핑목록 조회  
	 * @param SearchWebOrder
	 * @return List<EdiPoEmPoolVO>
	 * @throws SQLException
	 */
	public List<EdiPoEmpPoolVO> selectEmpVenList(SearchWebOrder vo) throws Exception;
	
	/**
	 * 사원별 협력사 선택목록 삭제  
	 * @param SearchWebOrder
	 * @return void
	 * @throws SQLException
	 */
	public void deleteEmpVenList(SearchWebOrder vo ) throws Exception;

	/**
	 * 사원별 협력사 저장
	 * @param EdiPoEmpPoolVO
	 * @return void
	 * @throws SQLException
	 */
	public HashMap<String,String> insertEmpVenData(SearchWebOrder vo ) throws Exception;
	
	
	/**
	 * 사원별 협력사 복사
	 * @param EdiPoEmpPoolVO
	 * @return HashMap<String,String>
	 * @throws SQLException
	 */
	public HashMap<String,String> insertEmpVenCopAndMove(EdiPoEmpPoolVO vo) throws Exception;
	
	
}

