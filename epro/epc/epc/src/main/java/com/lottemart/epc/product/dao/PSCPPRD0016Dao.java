package com.lottemart.epc.product.dao;


import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.product.model.PSCPPRD0016VO;

/**
 * @Class Name : PSCPPRD0016Dao.java
 * @Description :
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 20. 오후 02:02:02 mjChoi
 * 
 * @Copyright (C) 2000 ~ 2011 롯데정보통신(주) All right reserved.
 * </pre>
 */
@Repository("pscpprd0016Dao")
public class PSCPPRD0016Dao extends AbstractDAO
{
	/**
	 * 증정품 목록
	 * Desc : 
	 * @Method Name : selectPrdPresentList
	 * @param Map<String, String>
	 * @return List
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<DataMap> selectPrdPresentList(Map<String, String> paramMap) throws SQLException
	{
		return (List<DataMap>) getSqlMapClientTemplate().queryForList("pscpprd0016.selectPrdPresentList",paramMap);
	}
	
	/**
	 * 증정품 등록 목록 체크
	 * Desc : 
	 * @Method Name : selectPrdPresentChek
	 * @param Map<String, String>
	 * @return List
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public int selectPrdPresentChek(Map<String, String> paramMap) throws SQLException
	{
		return (Integer) getSqlMapClientTemplate().queryForObject("pscpprd0016.selectPrdPresentChek",paramMap);
	}
	
	/**
	 * 증정품 등록
	 * Desc : 
	 * @Method Name : updatePrdPresent
	 * @param VO
	 * @return int
	 * @throws SQLException
	 */	
	public int insertPrdPresent(PSCPPRD0016VO bean) throws SQLException
	{
		return getSqlMapClientTemplate().update("pscpprd0016.insertPrdPresent",bean);	
	}
	
	/**
	 * 증정품 업데이트
	 * Desc : 
	 * @Method Name : updatePrdPresent
	 * @param VO
	 * @return int
	 * @throws SQLException
	 */	
	public int updatePrdPresent(PSCPPRD0016VO bean) throws SQLException
	{
		return getSqlMapClientTemplate().update("pscpprd0016.updatePrdPresent",bean);	
	}

	/**
	 * 증정품 삭제(신규)
	 * Desc : 
	 * @Method Name : updatePrdPresentNull
	 * @param VO
	 * @return int
	 * @throws SQLException
	 */	
	public int deletePrdPresent(PSCPPRD0016VO bean) throws SQLException
	{
		return getSqlMapClientTemplate().delete("pscpprd0016.deletePrdPresent",bean);	
	}

	/**
	 * 증정품 삭제
	 * Desc : 
	 * @Method Name : updatePrdPresentNull
	 * @param VO
	 * @return int
	 * @throws SQLException
	 */	
	public int updatePrdPresentNull(PSCPPRD0016VO bean) throws SQLException
	{
		return getSqlMapClientTemplate().update("pscpprd0016.updatePrdPresentNull",bean);	
	}
	
	/**
	 * 증정품 아이콘 업데이트
	 * Desc : 
	 * @Method Name : updatePrdPresentIcon
	 * @param VO
	 * @return int
	 * @throws SQLException
	 */	
	public int updatePrdPresentIcon(PSCPPRD0016VO bean) throws SQLException
	{
		return getSqlMapClientTemplate().update("pscpprd0016.updatePrdPresentIcon",bean);	
	}

}
