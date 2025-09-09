/**
 * @prjectName  : lottemart 프로젝트   
 * @since       : 2011. 10. 18. 오후 2:33:50
 * @author      : yskim 
 * @Copyright(c) 2000 ~ 2011 lottemart
 *  All rights reserved.
 */

package com.lottemart.epc.statistics.dao;

import java.sql.SQLException;
import java.util.List;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.statistics.model.PSCMSTA0001SearchVO;

/**
 * @Class Name : PSCMSTA0001Dao
 * @Description : 네이버지식쇼핑/쇼핑캐스트 Dao 클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 18. 오후 5:25:37 yskim
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
@Repository("PSCMSTA0001Dao")
public class PSCMSTA0001Dao extends AbstractDAO {

	/**
	 * Desc : 매출유형 조회 메소드
	 * @Method Name : selectAffiliateLinkNoList
	 * @param searchVO
	 * @return
	 * @throws SQLException
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@SuppressWarnings("unchecked")
	public List<DataMap> selectAffiliateLinkNoList(PSCMSTA0001SearchVO searchVO) throws SQLException{
		return (List<DataMap>)getSqlMapClientTemplate().queryForList("PSCMSTA0001.selectAffiliateLinkNoList", searchVO);
	}
	
	/**
	 * Desc : 네이버지식쇼핑/쇼핑캐스트 통계 조회 메소드
	 * @Method Name : selectNaverEdmSummaryTotal
	 * @param searchVO
	 * @return
	 * @throws SQLException
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@SuppressWarnings("unchecked")
	public List<DataMap> selectNaverEdmSummaryTotal(PSCMSTA0001SearchVO searchVO) throws SQLException{
		return (List<DataMap>)getSqlMapClientTemplate().queryForList("PSCMSTA0001.selectNaverEdmSummaryTotal", searchVO);
	}
	
	/**
	 * Desc : 네이버지식쇼핑/쇼핑캐스트 목록 조회 메소드
	 * @Method Name : selectNaverEdmSummaryList
	 * @param searchVO
	 * @return
	 * @throws SQLException
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@SuppressWarnings("unchecked")
	public List<DataMap> selectNaverEdmSummaryList(PSCMSTA0001SearchVO searchVO) throws SQLException{
		return (List<DataMap>)getSqlMapClientTemplate().queryForList("PSCMSTA0001.selectNaverEdmSummaryList", searchVO);
	}
	
	
	/**
	 * Desc : 네이버지식쇼핑/쇼핑캐스트 목록 엑셀조회 메소드
	 * @Method Name : selectNaverEdmSummaryListExcel
	 * @param searchVO
	 * @return
	 * @throws SQLException
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@SuppressWarnings("unchecked")
	public List<DataMap> selectNaverEdmSummaryListExcel(PSCMSTA0001SearchVO searchVO) throws SQLException{
		return (List<DataMap>)getSqlMapClientTemplate().queryForList("PSCMSTA0001.selectNaverEdmSummaryListExcel", searchVO);
	}
	
	
	
	
}
