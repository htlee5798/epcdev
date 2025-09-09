/**
 * @prjectName  : lottemart 프로젝트   
 * @since       : 2011. 10. 19. 오후 5:35:50
 * @author      : wcpark 
 * @Copyright(c) 2000 ~ 2011 lottemart
 *  All rights reserved.
 */

package com.lottemart.epc.board.dao;

import java.sql.SQLException;
import java.util.List;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.board.model.PSCMBRD0003SearchVO;

/**
 * @Class Name : PSCMBRD0003Dao
 * @Description : 1:1문의 목록 Dao 클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 19. 오후 5:37:01 wcpark
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
@Repository("PSCMBRD0003Dao")
public class PSCMBRD0003Dao extends AbstractDAO {
	
	/**
	 * Desc : 총처리건, 접수건, 완료건을 조회하는 메소드
	 * @Method Name : selectCounselCount
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@SuppressWarnings("unchecked")
	public List<DataMap> selectCounselCount(DataMap paramMap) throws SQLException{
		return (List<DataMap>)getSqlMapClientTemplate().queryForList("PSCMBRD0003.selectCounselCount", paramMap);
	}
	
	/**
	 * Desc : 1:1문의 목록을 조회하는 메소드
	 * @Method Name : selectinquireList
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@SuppressWarnings("unchecked")
	public List<DataMap> getinquireList(DataMap paramMap) throws SQLException{
		return (List<DataMap>)getSqlMapClientTemplate().queryForList("PSCMBRD0003.selectinquireList", paramMap);
	}
	
	/**
	 * Desc : 접수위치 select box
	 * @Method Name : agentLocation
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@SuppressWarnings("unchecked")
	public List<DataMap> agentLocation(PSCMBRD0003SearchVO searchVO) throws SQLException{
		return (List<DataMap>)getSqlMapClientTemplate().queryForList("PSCMBRD0003.agentLocation", searchVO);
	}
	
	/**
	 * Desc : 고객문의 구분 select box
	 * @Method Name : custQstDivnCd
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@SuppressWarnings("unchecked")
	public List<DataMap> custQstDivnCd(PSCMBRD0003SearchVO searchVO) throws SQLException{
		return (List<DataMap>)getSqlMapClientTemplate().queryForList("PSCMBRD0003.custQstDivnCd", searchVO);
	}
}
