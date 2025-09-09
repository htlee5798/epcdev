/**
 * @prjectName  : lottemart 프로젝트
 * @since       : 2011. 05. 31. 오후 2:38:50
 * @author      : kslee
 * @Copyright(c) 2000 ~ 2011 lottemart
 *  All rights reserved.
 */

package com.lottemart.epc.board.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.board.model.PSCMBRD0013VO;

/**
 * @Class Name : PSCMBRD0013Dao
 * @Description : 상품평 목록 Dao 클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 05. 31. 오후 2:39:01 kslee
 *
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
@Repository("PSCMBRD0013Dao")
public class PSCMBRD0013Dao extends AbstractDAO {


	/**
	 * Desc : 상품평 목록을 조회하는 메소드
	 * @Method Name : selectRecommList
	 * @param searchVO
	 * @return
	 * @throws SQLException
	 * @param
	 * @return
	 * @exception Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PSCMBRD0013VO> selectProductSearch(Map<String, String> paramMap) throws SQLException{
		return getSqlMapClientTemplate().queryForList("pscmbrd0013.selectProductSearch", paramMap);
	}

	/**
	 * Desc : 상품평 게시판 총건수
	 * @Method Name : selectNoticeTotalCnt
	 * @param paramMap
	 * @throws SQLException
	 * @return iTotCnt
	 */
	public int selectProductTotalCnt(Map<String, String> paramMap) throws SQLException{
		Integer iTotCnt = new Integer(0);
		iTotCnt = (Integer)getSqlMapClientTemplate().queryForObject("pscmbrd0013.selectProductTotalCnt",paramMap);
		return iTotCnt.intValue();
	}

	/**
	 * Desc : 상품평 게시판 상세정보
	 * @Method Name : selectProductView
	 * @param recommSeq
	 * @throws SQLException
	 * @return PBOMBRD0003VO
	 */
	public PSCMBRD0013VO selectProductView(String recommSeq) throws SQLException{
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("recommSeq", recommSeq);
		return (PSCMBRD0013VO)getSqlMapClientTemplate().queryForObject("pscmbrd0013.selectProductView", paramMap);
	}

	/**
	 * Desc : 상품평 게시판 우수 상품평선정 및 해제 업데이트
	 * @Method Name : updateExlnSltYn
	 * @param PSCMBRD0013VO
	 * @throws SQLException
	 * @return 결과수
	 */
	public int updateExlnSltYn(PSCMBRD0013VO bean) throws SQLException{
		return getSqlMapClientTemplate().update("pscmbrd0013.updateExlnSltYn",bean);
	}

	/**
	 * Desc : 상품평 게시판 업데이트
	 * @Method Name : updateProduct
	 * @param PBOMBRD0003VO
	 * @throws SQLException
	 * @return 결과수
	 */
	public int updateProduct(PSCMBRD0013VO bean) throws SQLException{
		return getSqlMapClientTemplate().update("pscmbrd0013.updateProduct",bean);
	}

	/**
	 * Desc : 상품평 리스트 엑셀다운로드
	 * @Method Name : selectPscmbrd0013Export
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Map<Object, Object>> selectPscmbrd0013Export(Map<Object, Object> paramMap) throws Exception {
		return (List<Map<Object, Object>>)getSqlMapClientTemplate().queryForList("pscmbrd0013.selectPscmbrd0013Export",paramMap);
	}

	@SuppressWarnings("unchecked")
	public DataMap selectProdPhotoView(DataMap paramMap) throws SQLException {
		return (DataMap)getSqlMapClientTemplate().queryForObject("pscmbrd0013.selectProdPhotoView",paramMap);
	}

	/**
	 * Desc : 체험형 상품평 총건수
	 * @Method Name : selectPscmbrd0013Export
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	public int selectExprTotalCnt(Map paramMap) {
		return (Integer)getSqlMapClientTemplate().queryForObject("pscmbrd0013.selectExprTotalCnt",paramMap);
	}

	public List<PSCMBRD0013VO> selectExprSearch(Map paramMap) {
		return getSqlMapClientTemplate().queryForList("pscmbrd0013.selectExprSearch", paramMap);
	}

	public PSCMBRD0013VO selectExprView(String recommSeq) {
		return (PSCMBRD0013VO)getSqlMapClientTemplate().queryForObject("pscmbrd0013.selectExprView", recommSeq);
	}

	public int updateExprProd(PSCMBRD0013VO bean) {
		return (Integer) getSqlMapClientTemplate().update("pscmbrd0013.updateExprProd", bean);
	}

	public List<PSCMBRD0013VO> selectPscmbrd001302Export(Map paramMap) {
		return (List<PSCMBRD0013VO>)getSqlMapClientTemplate().queryForList("pscmbrd0013.selectPscmbrd001302Export",paramMap);
	}

}
