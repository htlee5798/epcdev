package com.lottemart.epc.product.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.product.model.PSCPPRD0005VO;
import com.lottemart.epc.product.model.PSCPPRD0017HistVO;
import com.lottemart.epc.product.model.PSCPPRD0017VO;

/**
 *  
 * @Class Name : PSCPPRD0017Dao
 * @Description :
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자	   수정내용
 *  -------    --------    ---------------------------
 * 2011. 12. 09. 오후 03:03:03 mjChoi
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
@Repository("pscpprd0017Dao")
public class PSCPPRD0017Dao extends AbstractDAO {

	/**
	 * 상품 키워드 목록
	 * Desc : 
	 * @Method Name : selectPrdKeywordList
	 * @param DataMap
	 * @return List
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<PSCPPRD0017VO> selectPrdKeywordList(Map<String, String> paramMap) throws SQLException {
		return (List<PSCPPRD0017VO>) getSqlMapClientTemplate().queryForList("pscpprd0017.selectPrdKeywordList", paramMap);
	}

	/**
	 * 상품 키워드 입력
	 * Desc : 
	 * @Method Name : insertPrdKeyword
	 * @param VO
	 * @return int
	 * @throws SQLException
	 */
	public int insertPrdKeyword(PSCPPRD0017VO bean) throws SQLException {
		return getSqlMapClientTemplate().update("pscpprd0017.insertPrdKeyword", bean);
	}

	/**
	 * 상품 키워드 업데이트
	 * Desc : 
	 * @Method Name : updatePrdKeyword
	 * @param VO
	 * @return int
	 * @throws SQLException
	 */
	public int updatePrdKeyword(PSCPPRD0017VO bean) throws SQLException {
		return getSqlMapClientTemplate().update("pscpprd0017.updatePrdKeyword", bean);
	}

	/**
	 * 상품 키워드 삭제
	 * Desc : 
	 * @Method Name : deletePrdKeyword
	 * @param VO
	 * @return int
	 * @throws SQLException
	 */
	public int deletePrdKeyword(PSCPPRD0017VO bean) throws SQLException {
		return getSqlMapClientTemplate().update("pscpprd0017.deletePrdKeyword", bean);
	}

	/**
	 * 상품 전체 키워드 채크
	 * Desc : 
	 * @Method Name : selectChkPrdTotalKeyword
	 * @param VO
	 * @return List
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<PSCPPRD0017VO> selectChkPrdTotalKeyword(PSCPPRD0017VO bean) throws SQLException {
		return (List<PSCPPRD0017VO>) getSqlMapClientTemplate().queryForList("pscpprd0017.selectChkPrdTotalKeyword", bean);
	}

	/**
	 * 상품 전체 키워드 업데이트
	 * Desc : 
	 * @Method Name : updatePrdTotalKeyword
	 * @param VO
	 * @return int
	 * @throws SQLException
	 */
	public int updatePrdTotalKeyword(PSCPPRD0017VO bean) throws SQLException {
		return getSqlMapClientTemplate().update("pscpprd0017.updatePrdTotalKeyword", bean);
	}

	/**
	 * 상품 전체 키워드 입력
	 * Desc : 
	 * @Method Name : insertPrdTotalKeyword
	 * @param VO
	 * @return int
	 * @throws SQLException
	 */
	public int insertPrdTotalKeyword(PSCPPRD0017VO bean) throws SQLException {
		return getSqlMapClientTemplate().update("pscpprd0017.insertPrdTotalKeyword", bean);
	}

	//20180911 상품키워드 입력 기능 추가
	/**
	 * 상품키워드 이력
	 * @Method Name : selectPrdDescription
	 * @param Map<String, String>
	 * @return VO
	 * @throws SQLException
	 */
	public PSCPPRD0005VO selectPrdMdAprvMst(Map<String, String> paramMap) throws SQLException {
		return (PSCPPRD0005VO) getSqlMapClientTemplate().queryForObject("pscpprd0017.selectPrdMdAprvMst", paramMap);
	}

	/**
	 * 상품키워드 이력 마스터
	 * @Method Name : selectPrdTotalCnt
	 * @param DataMap
	 * @return int
	 * @throws SQLException
	 */
	public int selectPrdMdAprvMstCnt(DataMap paramMap) throws SQLException {
		Integer iCnt = new Integer(0);
		iCnt = (Integer) getSqlMapClientTemplate().queryForObject("pscpprd0017.selectPrdMdAprvMstCnt", paramMap);

		return iCnt.intValue();
	}

	/**
	 * 상품키워드 이력 마스터 입력 처리
	 * @Method Name : insertPrdDescription
	 * @param VO
	 * @return int
	 * @throws SQLException
	 */
	public String insertPrdMdAprvMst(PSCPPRD0005VO bean) throws SQLException {
		return (String) getSqlMapClientTemplate().insert("pscpprd0017.insertPrdMdAprvMst", bean);
	}

	/**
	 * 상품키워드 이력 원본 삭제
	 * @Method deletePrdKeywordHist
	 * @param DataMap
	 * @return INT
	 * @throws SQLException
	 */
	public int deletePrdKeywordHistAll(DataMap paramMap) throws SQLException {
		return getSqlMapClientTemplate().update("pscpprd0017.deletePrdKeywordHistAll", paramMap);
	}

	/**
	 * 상품키워드 이력 원본 입력
	 * @Method insertPrdKeywordHist
	 * @param DataMap
	 * @return INT
	 * @throws SQLException
	 */
	public int insertPrdKeywordHistAll(DataMap paramMap) throws SQLException {
		return getSqlMapClientTemplate().update("pscpprd0017.insertPrdKeywordHistAll", paramMap);
	}

	/**
	 * 상품키워드 이력 입력
	 * @Method insertPrdKeyword
	 * @param VO
	 * @return INT
	 * @throws SQLException
	 */
	public int insertPrdKeywordHist(PSCPPRD0017HistVO bean) throws SQLException {
		return getSqlMapClientTemplate().update("pscpprd0017.insertPrdKeywordHist", bean);
	}

	/**
	 * 상품키워드 이력 업데이트
	 * Desc : 
	 * @Method Name : updatePrdKeyword
	 * @param VO
	 * @return int
	 * @throws SQLException
	 */
	public int updatePrdKeywordHist(PSCPPRD0017HistVO bean) throws SQLException {
		return getSqlMapClientTemplate().update("pscpprd0017.updatePrdKeywordHist", bean);
	}

	/**
	 * 상품키워드 이력 삭제
	 * Desc : 
	 * @Method Name : deletePrdKeyword
	 * @param VO
	 * @return int
	 * @throws SQLException
	 */
	public int deletePrdKeywordHist(PSCPPRD0017HistVO bean) throws SQLException {
		return getSqlMapClientTemplate().update("pscpprd0017.deletePrdKeywordHist", bean);
	}

	/**
	 * 상품키워드 전체 이력 확인
	 * @Method selectChkPrdTotalKeyword
	 * @param VO
	 * @return LIST
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<PSCPPRD0017HistVO> selectChkPrdTotalKeywordHist(PSCPPRD0017HistVO bean) throws SQLException {
		return (List<PSCPPRD0017HistVO>) getSqlMapClientTemplate().queryForList("pscpprd0017.selectChkPrdTotalKeywordHist", bean);
	}

	/**
	 * 상품키워드 전체 이력 업데이트
	 * @Method updatePrdTotalKeyword
	 * @param VO
	 * @return INT
	 * @throws SQLException
	 */
	public int updatePrdTotalKeywordHist(PSCPPRD0017HistVO bean) throws SQLException {
		return getSqlMapClientTemplate().update("pscpprd0017.updatePrdTotalKeywordHist", bean);
	}

	/**
	 * 상품키워드 전체 이력 입력
	 * @Method insertPrdTotalKeywordHist
	 * @param VO
	 * @return INT
	 * @throws SQLException
	 */
	public int insertPrdTotalKeywordHist(PSCPPRD0017HistVO bean) throws SQLException {
		return getSqlMapClientTemplate().update("pscpprd0017.insertPrdTotalKeywordHist", bean);
	}
	//20180911 상품키워드 입력 기능 추가

	/**
	 * 
	 * @param paramMap
	 * @return
	 * @throws SQLException
	 */
	public int mergePrdKeywordHistAll(DataMap paramMap) throws SQLException {
		return getSqlMapClientTemplate().update("pscpprd0017.mergePrdKeywordHistAll", paramMap);
	}

	/**
	 * 
	 * @param paramMap
	 * @return
	 * @throws SQLException
	 */
	public int masterKeywordHistUpdate(DataMap paramMap) throws SQLException {
		return getSqlMapClientTemplate().update("pscpprd0017.masterKeywordHistUpdate", paramMap);
	}
}