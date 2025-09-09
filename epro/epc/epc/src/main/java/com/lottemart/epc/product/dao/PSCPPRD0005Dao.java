package com.lottemart.epc.product.dao;

import java.sql.SQLException;
import java.util.Map;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.lottemart.common.util.DataMap;
import com.lottemart.epc.product.model.PSCPPRD0005VO;

/**
 * @Class Name : PSCPPRD0005Dao.java
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
@Repository("pscpprd0005Dao")
public class PSCPPRD0005Dao extends AbstractDAO {

	@Autowired
	private SqlMapClient sqlMapClient;

	/**
	 * 추가설명 정보
	 * Desc : 
	 * @Method Name : selectPrdDescription
	 * @param Map<String, String>
	 * @return VO
	 * @throws SQLException
	 */
	public PSCPPRD0005VO selectPrdDescription(Map<String, String> paramMap) throws SQLException {
		return (PSCPPRD0005VO) sqlMapClient.queryForObject("pscpprd0005.selectPrdDescription", paramMap);
	}

	/**
	 * 추가설명 정보 입력
	 * Desc : 
	 * @Method Name : insertPrdDescription
	 * @param VO
	 * @return int
	 * @throws SQLException
	 */	
	public int insertPrdDescription(PSCPPRD0005VO bean) throws SQLException {
		return getSqlMapClientTemplate().update("pscpprd0005.insertPrdDescription", bean);
		
	}

	/**
	 * 추가설명 정보 업데이트
	 * Desc : 
	 * @Method Name : updatePrdDescription
	 * @param VO
	 * @return int
	 * @throws SQLException
	 */	
	public int updatePrdDescription(PSCPPRD0005VO bean) throws SQLException {
		return getSqlMapClientTemplate().update("pscpprd0005.updatePrdDescription", bean);
	}

	/**
	 * 추가설명 정보 로그
	 * Desc : 
	 * @Method Name : insertPrdDescrLog
	 * @param VO
	 * @return int
	 * @throws SQLException
	 */	
	public int insertPrdDescrLog(PSCPPRD0005VO bean) throws SQLException {
		return sqlMapClient.update("pscpprd0005.insertPrdDescrLog", bean);
	}

	/**
	 * 추가설명 정보
	 * Desc : 
	 * @Method Name : selectPrdDescription
	 * @param Map<String, String>
	 * @return VO
	 * @throws SQLException
	 */
	public PSCPPRD0005VO selectPrdMdAprvMst(Map<String, String> paramMap) throws SQLException {
		return (PSCPPRD0005VO) sqlMapClient.queryForObject("pscpprd0005.selectPrdMdAprvMst", paramMap);
	}

	/**
	 * 상품 MD승인 히스토리 마스터 카운트
	 * Desc : 
	 * @Method Name : selectPrdTotalCnt
	 * @param DataMap
	 * @return int
	 * @throws SQLException
	 */
	public int selectPrdMdAprvMstCnt(DataMap paramMap) throws SQLException {
		Integer iCnt = new Integer(0);
		iCnt = (Integer) getSqlMapClientTemplate().queryForObject("pscpprd0005.selectPrdMdAprvMstCnt", paramMap);

		return iCnt.intValue();
	}

	/**
	 * 추가설명 히스토리 마스터 정보 입력
	 * Desc : 
	 * @Method Name : insertPrdDescription
	 * @param VO
	 * @return int
	 * @throws SQLException
	 */	
	public String insertPrdMdAprvMst(PSCPPRD0005VO bean) throws SQLException {
		return (String) getSqlMapClientTemplate().insert("pscpprd0005.insertPrdMdAprvMst", bean);
	}

	/**
	 * 추가설명 히스토리 정보 입력
	 * Desc : 
	 * @Method Name : insertPrdDescription
	 * @param VO
	 * @return int
	 * @throws SQLException
	 */	
	public int insertPrdDescriptionHist(PSCPPRD0005VO bean) throws SQLException {

		return getSqlMapClientTemplate().update("pscpprd0005.insertPrdDescriptionHist", bean);
	}

	/**
	 * 추가설명 히스토리 정보 업데이트
	 * Desc : 
	 * @Method Name : updatePrdDescription
	 * @param VO
	 * @return int
	 * @throws SQLException
	 */	
	public int updatePrdDescriptionHist(PSCPPRD0005VO bean) throws SQLException {
		return getSqlMapClientTemplate().update("pscpprd0005.updatePrdDescriptionHist", bean);
	}

}
