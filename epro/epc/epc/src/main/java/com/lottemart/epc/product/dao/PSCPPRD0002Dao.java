package com.lottemart.epc.product.dao;


import java.sql.SQLException;
import java.util.List;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.lottemart.common.util.DataMap;
import com.lottemart.epc.product.model.PSCPPRD0002VO;

/**
 * @Class Name : PSCPPRD0002Dao.java
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
@Repository("pscpprd0002Dao")
public class PSCPPRD0002Dao extends AbstractDAO {

	@Autowired
	private SqlMapClient sqlMapClient;

	/**
     * 공통코드 목록
     * @return DataMap
     * @throws SQLException  DB 관련 오류
     */
	@SuppressWarnings("unchecked")
	public List<DataMap> selectPrdCommonCodeList() throws SQLException {
		return (List<DataMap>) getSqlMapClientTemplate().queryForList("pscpprd0002.selectPrdCommonCodeList");
	}

	/**
     * 상품 증정품 COMBO내용
     * @param String
     * @return DataMap
     * @throws SQLException  DB 관련 오류
     */
	@SuppressWarnings("unchecked")
	public List<DataMap> selectPrdPresentList(String prodCd) throws SQLException {
		return (List<DataMap>) getSqlMapClientTemplate().queryForList("pscpprd0002.selectPrdPresentList", prodCd);
	}

	/**
     * 상품 상세정보
     * @param String
     * @return DataMap
     * @throws SQLException  DB 관련 오류
     */
	public DataMap selectPrdInfo(String prodCd) throws SQLException {
		return (DataMap) sqlMapClient.queryForObject("pscpprd0002.selectPrdInfo", prodCd);
	}

	/**
	 * 테마정보
	 * @param prodCd
	 * @return
	 * @throws SQLException
	 */
	public DataMap selectPrdExtThemaDealInfo(String prodCd) throws SQLException {
		return (DataMap) sqlMapClient.queryForObject("pscpprd0002.selectPrdExtThemaDealInfo", prodCd);
	}

	public int updatePrdExtThemaDealInfo(DataMap dataMap) throws SQLException {
		return getSqlMapClientTemplate().update("pscpprd0002.updatePrdExtThemaDealInfo", dataMap);
	}
	/**
	 * 옥션 연동 체크
	 * @param VO
	 * @return List<VO>
	 * @throws SQLException  DB 관련 오류
	 */
	@SuppressWarnings("unchecked")
	public List<PSCPPRD0002VO> selectChkAuctionPrdInfoUpdate(PSCPPRD0002VO bean) throws SQLException {
		return (List<PSCPPRD0002VO>) getSqlMapClientTemplate().queryForList("pscpprd0002.selectChkAuctionPrdInfoUpdate", bean);
	}

	/**
	 * 옥션 연동 처리
	 * 
	 * @param VO
	 * @return int
	 * @throws SQLException DB 관련 오류
	 */
	public int insertAuctionBatchLog(PSCPPRD0002VO bean) throws SQLException {
		return getSqlMapClientTemplate().update("pscpprd0002.insertAuctionBatchLog", bean);
	}
	
	/**
	 * 11번가 연동 체크
	 * @param VO
	 * @return int
	 * @throws SQLException  DB 관련 오류
	 */
	@SuppressWarnings("unchecked")
	public List<PSCPPRD0002VO> selectchk11StPrdInfoUpdate(PSCPPRD0002VO bean) throws SQLException {
		return (List<PSCPPRD0002VO>) getSqlMapClientTemplate().queryForList("pscpprd0002.selectchk11StPrdInfoUpdate", bean);
	}
	
	/**
	 * 11번가 연동 처리
	 * @param VO
	 * @return int
	 * @throws SQLException  DB 관련 오류
	 */
	public int insertSiteLinkInfo(PSCPPRD0002VO bean) throws SQLException {
		return getSqlMapClientTemplate().update("pscpprd0002.insertSiteLinkInfo", bean);
	}
	
	/**
	 * 상품정보 업데이트
	 * @param VO
	 * @return int
	 * @throws SQLException  DB 관련 오류
	 */
	public int updatePrdDetailInfo(PSCPPRD0002VO bean) throws SQLException {
		return getSqlMapClientTemplate().update("pscpprd0002.updatePrdDetailInfo", bean);
	}
	
	public DataMap selectProdCommerceCheck(DataMap dm) throws SQLException {
		return (DataMap) getSqlMapClientTemplate().queryForObject("pscpprd0002.selectProdCommerceCheck", dm);
	}

	public DataMap selectNewProdCommerceCheck(DataMap dm) throws SQLException {
		return (DataMap) getSqlMapClientTemplate().queryForObject("pscpprd0002.selectNewProdCommerceCheck", dm);
	}

	public int insertProdHist(PSCPPRD0002VO bean) throws SQLException {
		return getSqlMapClientTemplate().update("pscpprd0002.insertProdHist", bean);
	}

	public int countTprProdExInfo(String prodCd) throws SQLException {
		return (Integer) sqlMapClient.queryForObject("pscpprd0002.countTprProdExInfo", prodCd);
	}

	public int deleteTprProdExInfo(String prodCd) throws SQLException {
		return sqlMapClient.delete("pscpprd0002.deleteTprProdExInfo", prodCd);
	}

	public int mergeExprDetailInfo(DataMap exParamMap) throws SQLException {
		return sqlMapClient.update("pscpprd0002.updateExprDetailInfo", exParamMap);

	}

	public int selectTypeCdCheck(String prodCd) throws SQLException {
		return (Integer) sqlMapClient.queryForObject("pscpprd0002.selectTypeCdCheck", prodCd);
	}

	//20180911 상품키워드 입력 기능 추가 	
	public String selectKeywordYnChk(String prodCd) throws SQLException {
		return (String) sqlMapClient.queryForObject("pscpprd0002.selectKeywordYnChk", prodCd);
	}

	public String selectKeywordAprvYnChk(String prodCd) throws SQLException {
		return (String) sqlMapClient.queryForObject("pscpprd0002.selectKeywordAprvYnChk", prodCd);
	}
	//20180911 상품키워드 입력 기능 추가

}
