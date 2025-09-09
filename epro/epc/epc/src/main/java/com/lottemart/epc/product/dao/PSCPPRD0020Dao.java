package com.lottemart.epc.product.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import lcn.module.framework.base.AbstractDAO;



import com.lottemart.common.util.DataMap;

import org.springframework.stereotype.Repository;

import com.lottemart.epc.product.model.PSCPPRD0020VO;

@Repository("PSCPPRD0020Dao")
public class PSCPPRD0020Dao extends AbstractDAO{
	
	/**
	 * 전자상거래 목록
	 * Desc : 
	 * @Method Name : selectPrdCommerceList
	 * @param DataMap
	 * @return List
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<PSCPPRD0020VO> selectPrdCommerceList(Map<String, String> paramMap) throws SQLException{
		return (List<PSCPPRD0020VO>) getSqlMapClientTemplate().queryForList("pscpprd0020.selectPrdCommerceList",paramMap);
	}
	
	@SuppressWarnings("unchecked")
	public List<DataMap> selectPrdCommerceCatCdList(Map<String, String> paramMap) throws SQLException{
		return (List<DataMap>) getSqlMapClientTemplate().queryForList("pscpprd0020.selectPrdCommerceCatCdList",paramMap);
	}
	
	public DataMap selectPrdCommerceCnt(Map<String, String> paramMap) throws SQLException{
		return (DataMap)getSqlMapClientTemplate().queryForObject("pscpprd0020.selectPrdCommerceCnt",paramMap);	
	}
	
	/**
	 * 전자상거래 삭제
	 * Desc : 
	 * @Method Name : updatePrdCommerce
	 * @param VO
	 * @return int
	 * @throws SQLException
	 */	
	public int deletePrdCommerce(Map<String, String> paramMap) throws SQLException{
		return getSqlMapClientTemplate().update("pscpprd0020.deletePrdCommerce",paramMap);	
	}

	/**
	 * 전자상거래 입력
	 * Desc : 
	 * @Method Name : InsertPrdCommerce
	 * @param VO
	 * @return int
	 * @throws SQLException
	 */	
	public int insertPrdCommerce(PSCPPRD0020VO bean) throws SQLException{
		return getSqlMapClientTemplate().update("pscpprd0020.insertPrdCommerce", bean);
	}
	
	public DataMap selectPrdCommerceTempList(Map<String, String> paramMap) throws SQLException{
		return (DataMap)getSqlMapClientTemplate().queryForObject("pscpprd0020.selectPrdCommerceTempList",paramMap);	
	}
	
	public DataMap prodCommerceUpdateInfo(Map<String, String> paramMap) throws SQLException{
		return (DataMap)getSqlMapClientTemplate().queryForObject("pscpprd0020.prodCommerceUpdateInfo",paramMap);	
	}
	
	/**
	 * 전자상거래 삭제
	 * Desc : 
	 * @Method Name : updatePrdCommerce
	 * @param VO
	 * @return int
	 * @throws SQLException
	 */	
	public int deletePrdCommerceHist(Map<String, String> paramMap) throws SQLException{
		return getSqlMapClientTemplate().update("pscpprd0020.deletePrdCommerceHist",paramMap);	
	}

	/**
	 * 전자상거래 입력
	 * Desc : 
	 * @Method Name : InsertPrdCommerce
	 * @param VO
	 * @return int
	 * @throws SQLException
	 */	
	public int insertPrdCommerceHist(PSCPPRD0020VO bean) throws SQLException{
		return getSqlMapClientTemplate().update("pscpprd0020.insertPrdCommerceHist", bean);
	}	
}
