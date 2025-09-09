package com.lottemart.epc.product.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import lcn.module.framework.base.AbstractDAO;



import com.lottemart.common.util.DataMap;

import org.springframework.stereotype.Repository;

import com.lottemart.epc.product.model.PSCPPRD0020VO;

@Repository("PSCPPRD0022Dao")
public class PSCPPRD0022Dao extends AbstractDAO{
	
	/**
	 * KC 인증 목록
	 * Desc : 
	 * @Method Name : selectPrdCertList
	 * @param DataMap
	 * @return List
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<PSCPPRD0020VO> selectPrdCertList(Map<String, String> paramMap) throws SQLException{
		return (List<PSCPPRD0020VO>) getSqlMapClientTemplate().queryForList("pscpprd0022.selectPrdCertList",paramMap);
	}
	
	@SuppressWarnings("unchecked")
	public List<DataMap> selectPrdCertCatCdList(Map<String, String> paramMap) throws SQLException{
		return (List<DataMap>) getSqlMapClientTemplate().queryForList("pscpprd0022.selectPrdCertCatCdList",paramMap);
	}
	
	public DataMap selectPrdCertCnt(Map<String, String> paramMap) throws SQLException{
		return (DataMap)getSqlMapClientTemplate().queryForObject("pscpprd0022.selectPrdCertCnt",paramMap);	
	}
	
	/**
	 * KC 인증 삭제
	 * Desc : 
	 * @Method Name : updatePrdCert
	 * @param VO
	 * @return int
	 * @throws SQLException
	 */	
	public int deletePrdCert(Map<String, String> paramMap) throws SQLException{
		return getSqlMapClientTemplate().update("pscpprd0022.deletePrdCert",paramMap);	
	}

	/**
	 * KC 인증 입력
	 * Desc : 
	 * @Method Name : InsertPrdCert
	 * @param VO
	 * @return int
	 * @throws SQLException
	 */	
	public int insertPrdCert(PSCPPRD0020VO bean) throws SQLException{
		return getSqlMapClientTemplate().update("pscpprd0022.insertPrdCert", bean);
	}
	
	public DataMap selectPrdCertTempList(Map<String, String> paramMap) throws SQLException{
		return (DataMap)getSqlMapClientTemplate().queryForObject("pscpprd0022.selectPrdCertTempList",paramMap);	
	}
	
	public DataMap prodCertUpdateInfo(Map<String, String> paramMap) throws SQLException{
		return (DataMap)getSqlMapClientTemplate().queryForObject("pscpprd0022.prodCertUpdateInfo",paramMap);	
	}
	
	/**
	 * KC 인증 삭제
	 * Desc : 
	 * @Method Name : updatePrdCert
	 * @param VO
	 * @return int
	 * @throws SQLException
	 */	
	public int deletePrdCertHist(Map<String, String> paramMap) throws SQLException{
		return getSqlMapClientTemplate().update("pscpprd0022.deletePrdCertHist",paramMap);	
	}

	/**
	 * KC 인증 입력
	 * Desc : 
	 * @Method Name : InsertPrdCert
	 * @param VO
	 * @return int
	 * @throws SQLException
	 */	
	public int insertPrdCertHist(PSCPPRD0020VO bean) throws SQLException{
		return getSqlMapClientTemplate().update("pscpprd0022.insertPrdCertHist", bean);
	}	
}
