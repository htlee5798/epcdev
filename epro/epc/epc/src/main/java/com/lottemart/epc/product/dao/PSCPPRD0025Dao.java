package com.lottemart.epc.product.dao;

import java.sql.SQLException;
import java.util.List;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.product.model.PSCPPRD0025HistVO;
import com.lottemart.epc.product.model.PSCPPRD0025VO;

/**
 * @Class Name : PSCPPRD0025Dao.java
 * @Description :
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2016. 11. 21. 오후 1:29:19 최성웅
 * 
 * @Copyright (C) 2000 ~ 2011 롯데정보통신(주) All right reserved.
 * </pre>
 */
@Repository("PSCPPRD0025Dao")
public class PSCPPRD0025Dao extends AbstractDAO{
	
	/**
	 * Desc : 키워드 검색
	 * @Method Name : selectKeywordSearch
	 * @param dataMap
	 * @return
	 * @throws SQLException
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@SuppressWarnings({ "deprecation", "unchecked" })
	public List<DataMap> selectKeywordSearch(DataMap dataMap) throws SQLException{
		return getSqlMapClientTemplate().queryForList("pscpprd0025.selectKeywordSearch",dataMap);
	}
	
	/**
	 * Desc : 키워드 수정
	 * @Method Name : keywordUpdate
	 * @param pscpprd00025Vo
	 * @throws SQLException
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@SuppressWarnings("deprecation")
	public void keywordUpdate(PSCPPRD0025VO pscpprd0025Vo) throws SQLException{
		getSqlMapClientTemplate().update("pscpprd0025.keywordUpdate", pscpprd0025Vo);
	}
	
	/**
	 * Desc : 키워드 마스터 수정
	 * @Method Name : masterKeywordUpdate
	 * @param pscpprd00025Vo
	 * @throws SQLException
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@SuppressWarnings("deprecation")
	public void masterKeywordUpdate(PSCPPRD0025VO pscpprd0025Vo) throws SQLException{
		getSqlMapClientTemplate().update("pscpprd0025.masterKeywordUpdate", pscpprd0025Vo);
	}
	
	/**
	 * Desc : 키워드 삭제
	 * @Method Name : keywordDelete
	 * @param pscpprd00025Vo
	 * @throws SQLException
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@SuppressWarnings("deprecation")
	public void keywordDelete(PSCPPRD0025VO pscpprd0025Vo) throws SQLException{
		getSqlMapClientTemplate().update("pscpprd0025.keywordDelete", pscpprd0025Vo);
	}
	
	//20180911 상품키워드 입력 기능 추가
	/**
	 * 상품키워드 이력 수정
	 * @Method keywordHistUpdate
	 * @param pscpprd00025Vo
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public void keywordHistUpdate(PSCPPRD0025HistVO pscpprd0025HistVo) throws SQLException{
		getSqlMapClientTemplate().update("pscpprd0025.keywordHistUpdate", pscpprd0025HistVo);
	}
	
	/**
	 * 상품키워드 마스터 이력 수정
	 * @Method masterKeywordHistUpdate
	 * @param pscpprd00025HistVo
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public void masterKeywordHistUpdate(PSCPPRD0025HistVO pscpprd0025HistVo) throws SQLException{
		getSqlMapClientTemplate().update("pscpprd0025.masterKeywordHistUpdate", pscpprd0025HistVo);
	}
	//20180911 상품키워드 입력 기능 추가
	
}
