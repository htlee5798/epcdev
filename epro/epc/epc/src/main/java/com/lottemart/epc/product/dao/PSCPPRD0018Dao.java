package com.lottemart.epc.product.dao;


import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.lottemart.epc.product.model.PSCPPRD0018VO;
import com.lottemart.common.util.DataMap;

/**
 *  
 * @Class Name : PSCPPRD0018Dao
 * @Description :
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자	   수정내용
 *  -------    --------    ---------------------------
 * 2011. 12. 27. 오후 03:03:03 mjChoi
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
@Repository("pscpprd0018Dao")
public class PSCPPRD0018Dao extends AbstractDAO
{
	@Autowired
	private SqlMapClient sqlMapClient;
	
	/**
	 * 상품종류
	 * Desc : 
	 * @Method Name : selectPrdDivnType
	 * @param String
	 * @return String
	 * @throws SQLException
	 */
	public DataMap selectPrdDivnType(String prodCd) throws SQLException{
		return (DataMap)getSqlMapClientTemplate().queryForObject("pscpprd0018.selectPrdDivnType",prodCd);
	}
	
	/**
     * 점포 목록
     * @return DataMap
     * @throws SQLException  DB 관련 오류
     */
	@SuppressWarnings("unchecked")
	public List<DataMap> selectPrdStoreList(String strGubun) throws SQLException
	{
		return (List<DataMap>)getSqlMapClientTemplate().queryForList("pscpprd0018.selectPrdStoreList", strGubun);
	}

	/**
     * 공통코드 목록
     * @return DataMap
     * @throws SQLException  DB 관련 오류
     */
	@SuppressWarnings("unchecked")
	public List<DataMap> selectPrdCommonCodeList(String prodCd) throws SQLException{
		return (List<DataMap>)getSqlMapClientTemplate().queryForList("pscpprd0018.selectPrdCommonCodeList", prodCd);
	}
	
	/**
	 * 가격정보 목록
	 * Desc : 
	 * @Method Name : selectPrdPriceList
	 * @param DataMap
	 * @return List
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<PSCPPRD0018VO> selectPrdPriceList(DataMap paramMap) throws SQLException
	{
		return (List<PSCPPRD0018VO>) getSqlMapClientTemplate().queryForList("pscpprd0018.selectPrdPriceList",paramMap);
	}
	
	/**
	 * 가격정보 업데이트
	 * Desc : 
	 * @Method Name : updatePrdPrice
	 * @param VO
	 * @return int
	 * @throws SQLException
	 */	
	public int updatePrdPrice(PSCPPRD0018VO bean) throws SQLException
	{
		return getSqlMapClientTemplate().update("pscpprd0018.updatePrdPrice",bean);	
	}

	/**
	 * 상품 전시 채크
	 * Desc : 
	 * @Method Name : selectChkPrdVisible
	 * @param VO
	 * @return List
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<PSCPPRD0018VO> selectChkPrdVisible(PSCPPRD0018VO bean) throws SQLException
	{
		return (List<PSCPPRD0018VO>) getSqlMapClientTemplate().queryForList("pscpprd0018.selectChkPrdVisible",bean);
	}

	/**
	 * 카테고리 할당 수정
	 * Desc : 
	 * @Method Name : updatePrdCategoryAssign
	 * @param VO
	 * @return int
	 * @throws SQLException
	 */	
	public int updatePrdCategoryAssign(PSCPPRD0018VO bean) throws SQLException
	{
		return getSqlMapClientTemplate().update("pscpprd0018.updatePrdCategoryAssign",bean);	
	}

	/**
	 * 상품마스터 전시여부 수정
	 * Desc : 
	 * @Method Name : updatePrdVisible
	 * @param VO
	 * @return int
	 * @throws SQLException
	 */	
	public int updatePrdVisible(PSCPPRD0018VO bean) throws SQLException
	{
		return getSqlMapClientTemplate().update("pscpprd0018.updatePrdVisible",bean);	
	}

	/**
	 * 옥션연동대상 채크
	 * Desc : 
	 * @Method Name : selectChkAuctionPrdInsert
	 * @param VO
	 * @return List
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<PSCPPRD0018VO> selectChkAuctionPrdInsert(PSCPPRD0018VO bean) throws SQLException
	{
		return (List<PSCPPRD0018VO>) getSqlMapClientTemplate().queryForList("pscpprd0018.selectChkAuctionPrdInsert",bean);
	}
	
	/**
	 * 옥션연동대상 채크2
	 * Desc : 
	 * @Method Name : selectChkAuctionAbsenceVisible
	 * @param VO
	 * @return List
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<PSCPPRD0018VO> selectChkAuctionAbsenceVisible(PSCPPRD0018VO bean) throws SQLException
	{
		return (List<PSCPPRD0018VO>) getSqlMapClientTemplate().queryForList("pscpprd0018.selectChkAuctionAbsenceVisible",bean);
	}

	/**
	 * 옥션연동 정보 입력
	 * Desc : 
	 * @Method Name : InsertAuctionBatchLog
	 * @param VO
	 * @return int
	 * @throws SQLException
	 */	
	public int InsertAuctionBatchLog(PSCPPRD0018VO bean) throws SQLException
	{
		return getSqlMapClientTemplate().update("pscpprd0018.InsertAuctionBatchLog", bean);
	}

	/**
	 * 11번가 연동 채크 채크
	 * Desc : 
	 * @Method Name : selectChk11StPrdInfoUpdate
	 * @param VO
	 * @return List
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<PSCPPRD0018VO> selectChk11StPrdInfoUpdate(PSCPPRD0018VO bean) throws SQLException
	{
		return (List<PSCPPRD0018VO>) getSqlMapClientTemplate().queryForList("pscpprd0018.selectChk11StPrdInfoUpdate",bean);
	}
	
	/**
	 * 11번가 연동대상 채크2
	 * Desc : 
	 * @Method Name : selectChk11stAbsenceVisible
	 * @param VO
	 * @return List
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<PSCPPRD0018VO> selectChk11stAbsenceVisible(PSCPPRD0018VO bean) throws SQLException
	{
		return (List<PSCPPRD0018VO>) getSqlMapClientTemplate().queryForList("pscpprd0018.selectChk11stAbsenceVisible",bean);
	}

	/**
	 * 11번가 연동 처리
	 * Desc : 
	 * @Method Name : insertSiteLinkInfo
	 * @param VO
	 * @return int
	 * @throws SQLException
	 */	
	public int insertSiteLinkInfo(PSCPPRD0018VO bean) throws SQLException
	{
		return getSqlMapClientTemplate().update("pscpprd0018.insertSiteLinkInfo", bean);
	}
	
	/**
	 * 온라인품절 채크
	 * Desc : 
	 * @Method Name : selectPrdOutOfStockDay
	 * @param VO
	 * @return List
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<PSCPPRD0018VO> selectPrdOutOfStockDay(PSCPPRD0018VO bean) throws SQLException
	{
		return (List<PSCPPRD0018VO>) getSqlMapClientTemplate().queryForList("pscpprd0018.selectPrdOutOfStockDay",bean);
	}

	/**
	 * PR_ONL_SO_DTIME_MGMT 에 저장
	 * Desc : 
	 * @Method Name : insertPrdOutOfStockDay
	 * @param VO
	 * @return int
	 * @throws SQLException
	 */	
	public int insertPrdOutOfStockDay(PSCPPRD0018VO bean) throws SQLException
	{
		return getSqlMapClientTemplate().update("pscpprd0018.insertPrdOutOfStockDay", bean);
	}	

	/**
	 * PR_ONL_SO_DTIME_MGMT 삭제
	 * Desc : 
	 * @Method Name : deletePrdOutOfStockDay
	 * @param VO
	 * @return int
	 * @throws SQLException
	 */	
	public int deletePrdOutOfStockDay(PSCPPRD0018VO bean) throws SQLException
	{
		return getSqlMapClientTemplate().update("pscpprd0018.deletePrdOutOfStockDay", bean);
	}	

	/**
	 * 가격정보 상세 목록
	 * Desc : 
	 * @Method Name : selectPrdPriceDetailList
	 * @param DataMap
	 * @return List
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<PSCPPRD0018VO> selectPrdPriceDetailList(Map<String, String> paramMap) throws SQLException
	{
		return (List<PSCPPRD0018VO>) getSqlMapClientTemplate().queryForList("pscpprd0018.selectPrdPriceDetailList",paramMap);
	}

	/* 특정점 연동 프로시져 */
	public DataMap insertSpMprInsert(DataMap dm) throws SQLException {
		return (DataMap)sqlMapClient.queryForObject("pscpprd0018.SP_MPR_INSERT",dm);
	}
}
