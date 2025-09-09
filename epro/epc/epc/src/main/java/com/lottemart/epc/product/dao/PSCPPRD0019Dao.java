package com.lottemart.epc.product.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.lottemart.common.util.DataMap;
import com.lottemart.epc.product.model.PSCPPRD0019VO;

/**
 *  
 * @Class Name : PSCPPRD0019Dao
 * @Description :
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자	   수정내용
 *  -------    --------    ---------------------------
 * 2011. 9. 7   jib
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
@Repository("PSCPPRD0019Dao")
public class PSCPPRD0019Dao extends AbstractDAO{
	
	@Autowired
	private SqlMapClient sqlMapClient;
	
	/**
	 * 추가속성 목록
	 * Desc : 
	 * @Method Name : selectPrdAttributeList
	 * @param DataMap
	 * @return List
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<PSCPPRD0019VO> selectPrdAttributeList(Map<String, String> paramMap) throws SQLException{
		return (List<PSCPPRD0019VO>) getSqlMapClientTemplate().queryForList("pscpprd0019.selectPrdAttributeList",paramMap);
	}
	
	/**
	 * 추가속성 입력
	 * Desc : 
	 * @Method Name : insertPrdAttributeList
	 * @param VO
	 * @return int
	 * @throws SQLException
	 */	
	public int insertPrdAttributeList(DataMap paramMap) throws SQLException{
		return getSqlMapClientTemplate().update("pscpprd0019.insertPrdAttributeList",paramMap);	
	}

	/**
	 * 추가속성 삭제
	 * Desc : 
	 * @Method Name : deletePrdAttribute
	 * @param VO
	 * @return int
	 * @throws SQLException
	 */	
	public int deletePrdAttribute(DataMap paramMap) throws SQLException{
		return getSqlMapClientTemplate().update("pscpprd0019.deletePrdAttribute",paramMap);	
	}

	/**
	 * 추가속성 카테고리
	 * Desc : 
	 * @Method Name : selectPrdAttributeCategory
	 * @param DataMap
	 * @return PSCPPRD0019VO
	 * @throws SQLException
	 */
	public PSCPPRD0019VO selectPrdAttributeCategory(Map<String, String> paramMap) throws SQLException{
		return (PSCPPRD0019VO) sqlMapClient.queryForObject("pscpprd0019.selectPrdAttributeCategory",paramMap);
	}

	/**
	 * 추가속성 항목값
	 * Desc : 
	 * @Method Name : selectPrdAttributeCategory
	 * @param DataMap
	 * @return PSCPPRD0019VO
	 * @throws SQLException
	 */
	public PSCPPRD0019VO selectPrdAttributeColVal(Map<String, String> paramMap) throws SQLException{
		return (PSCPPRD0019VO) sqlMapClient.queryForObject("pscpprd0019.selectPrdAttributeColVal",paramMap);
	}
	
	/**
	 * 추가속성 목록
	 * Desc : 
	 * @Method Name : selectPrdAttributeCrossList
	 * @param DataMap
	 * @return List
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<PSCPPRD0019VO> selectPrdAttributeCategoryList(Map<String, String> paramMap) throws SQLException{
		return (List<PSCPPRD0019VO>) getSqlMapClientTemplate().queryForList("pscpprd0019.selectPrdAttributeCategoryList",paramMap);
	}	

	/**
	 * 추가속성 N 업데이트
	 * Desc : 
	 * @Method Name : updatePrdAttribute
	 * @param VO
	 * @return int
	 * @throws SQLException
	 */	
	public int updatePrdAttribute(PSCPPRD0019VO bean) throws SQLException{
		return getSqlMapClientTemplate().update("pscpprd0019.updatePrdAttribute",bean);	
	}
	
	/**
	 * 추가속성 N 입력
	 * Desc : 
	 * @Method Name : InsertPrdAttribute
	 * @param VO
	 * @return int
	 * @throws SQLException
	 */	
	public int insertPrdAttribute(PSCPPRD0019VO bean) throws SQLException{
		return getSqlMapClientTemplate().update("pscpprd0019.insertPrdAttribute", bean);
	}
}