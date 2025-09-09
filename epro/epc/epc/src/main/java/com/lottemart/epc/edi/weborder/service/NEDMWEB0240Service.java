package com.lottemart.epc.edi.weborder.service;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import com.lottemart.epc.edi.weborder.model.EdiPoEmpPoolVO;
import com.lottemart.epc.edi.weborder.model.NEDMWEB0240VO;
import com.lottemart.epc.edi.weborder.model.SearchWebOrder;

/**
 * @Class Name : NEDMWEB0240Service
 * @Description : 협력업체설정 Service Class
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      		수정자          		수정내용
 *  -------    --------    ---------------------------
 * 2015.12.09	O YEUN KWON	  최초생성
 * 
 * @Copyright (C) 2000 ~ 2015 lottemart All right reserved.
 * </pre>
 */

public interface NEDMWEB0240Service {
	
	
	
	/**
	 * 사원목록 조회  
	 * @param SearchWebOrder
	 * @return List<EdiPoEmPoolVO>
	 * @throws SQLException
	 */
	public List<NEDMWEB0240VO> selectEmpPoolList(NEDMWEB0240VO vo) throws Exception;
	
	/**
	 * 사원별 협력사 매핑목록 조회  
	 * @param SearchWebOrder
	 * @return List<EdiPoEmPoolVO>
	 * @throws SQLException
	 */
	public List<NEDMWEB0240VO> selectEmpVenList(NEDMWEB0240VO vo) throws Exception;
	
	/**
	 * 사원별 협력사 선택목록 삭제  
	 * @param SearchWebOrder
	 * @return void
	 * @throws SQLException
	 */
	public void deleteEmpVenList(NEDMWEB0240VO vo ) throws Exception;

	/**
	 * 사원별 협력사 저장
	 * @param EdiPoEmpPoolVO
	 * @return void
	 * @throws SQLException
	 */
	public HashMap<String,String> insertEmpVenData(NEDMWEB0240VO vo ) throws Exception;
	
	
	/**
	 * 사원별 협력사 복사
	 * @param EdiPoEmpPoolVO
	 * @return HashMap<String,String>
	 * @throws SQLException
	 */
	public HashMap<String,String> insertEmpVenCopAndMove(NEDMWEB0240VO vo) throws Exception;
	
	
}

