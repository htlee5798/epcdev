package com.lottemart.epc.product.dao;


import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lottemart.epc.product.model.PSCPPRD00041VO;
import com.lottemart.epc.product.model.PSCPPRD0004VO;

import lcn.module.framework.base.AbstractDAO;

/**
 * @Class Name : PSCPPRD0004Dao.java
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
@Repository("pscpprd0004Dao")
public class PSCPPRD0004Dao extends AbstractDAO {

	/**
	 * 테마 목록 조회
	 * @param paramMap
	 * @return
	 * @throws SQLException
	 */
	public List<PSCPPRD00041VO> selectPrdThemaList(Map<String, String> paramMap) throws SQLException {
		return (List<PSCPPRD00041VO>) getSqlMapClientTemplate().queryForList("pscpprd0004.selectPrdThemaList", paramMap);
	}

	/**
	 * 묶음상품/테마상품 목록 조회
	 * Desc : 
	 * @Method Name : selectPrdRecommendList
	 * @param Map<String, String>
	 * @return List<VO>
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<PSCPPRD0004VO> selectPrdRecommendList(Map<String, String> paramMap) throws SQLException {
		return (List<PSCPPRD0004VO>) getSqlMapClientTemplate().queryForList("pscpprd0004.selectPrdRecommendList", paramMap);
	}

	/**
	 * 추천상품 수정
	 * Desc : 
	 * @Method Name : updatePrdRecommend
	 * @param VO
	 * @return int
	 * @throws SQLException
	 */	
	public int updatePrdRecommend(PSCPPRD0004VO bean) throws SQLException {
		return getSqlMapClientTemplate().update("pscpprd0004.updatePrdRecommend", bean);
	}

	/**
	 * 추천상품 삭제
	 * Desc : 
	 * @Method Name : deletePrdRecommend
	 * @param VO
	 * @return int
	 * @throws SQLException
	 */	
	public int deletePrdRecommend(PSCPPRD0004VO bean) throws SQLException {
		return getSqlMapClientTemplate().update("pscpprd0004.deletePrdRecommend", bean);
	}

	/**
	 * 추천할 상품 목록 총카운트
	 * Desc : 
	 * @Method Name : selectPrdRecommendCrossTotalCnt
	 * @param Map<String, String>
	 * @return int
	 * @throws SQLException
	 */
	public int selectPrdRecommendCrossTotalCnt(Map<String, String> paramMap) throws SQLException {
		Integer iTotCnt = new Integer(0);
		iTotCnt = (Integer) getSqlMapClientTemplate().queryForObject("pscpprd0004.selectPrdRecommendCrossTotalCnt", paramMap);

		return iTotCnt.intValue();
	}

	/**
	 * 추천할 상품 목록
	 * Desc : 
	 * @Method Name : selectPrdRecommendCrossList
	 * @param Map<String, String>
	 * @return List<VO>
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<PSCPPRD0004VO> selectPrdRecommendCrossList(Map<String, String> paramMap) throws SQLException {
		return (List<PSCPPRD0004VO>) getSqlMapClientTemplate().queryForList("pscpprd0004.selectPrdRecommendCrossList", paramMap);
	}

	/**
	 * 추천상품 입력 처리
	 * Desc : 
	 * @Method Name : insertPrdRecommend
	 * @param VO
	 * @return int
	 * @throws SQLException
	 */	
	public int insertPrdRecommend(PSCPPRD0004VO bean) throws SQLException {
		return getSqlMapClientTemplate().update("pscpprd0004.insertPrdRecommend", bean);
	}

	/**
	 * 테마 등록
	 * @param bean
	 * @return
	 * @throws SQLException
	 */
	public int insertPrdThema(PSCPPRD00041VO bean) throws SQLException {
		return getSqlMapClientTemplate().update("pscpprd0004.insertPrdThema", bean);
	}

	/**
	 * 테마 삭제
	 * @param bean
	 * @return
	 * @throws SQLException
	 */
	public int deletePrdThema(PSCPPRD00041VO bean) throws SQLException {
		return getSqlMapClientTemplate().update("pscpprd0004.deletePrdThema", bean);
	}
	/**
	 * 테마 수정
	 * @param bean
	 * @return
	 * @throws SQLException
	 */
	public int updatePrdThema(PSCPPRD00041VO bean) throws SQLException {
		return getSqlMapClientTemplate().update("pscpprd0004.updatePrdThema", bean);
	}

	/**
	 * 테마 상품 삭제
	 * @param bean
	 * @return
	 * @throws SQLException
	 */
	public int deletePrdThemaProd(PSCPPRD00041VO bean) throws SQLException {
		return getSqlMapClientTemplate().update("pscpprd0004.deletePrdThemaProd", bean);
	}

}
