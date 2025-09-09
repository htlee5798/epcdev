/**
 * @prjectName  : lottemart 프로젝트   
 * @since       : 2011. 10. 18. 오후 2:33:50
 * @author      : yskim 
 * @Copyright(c) 2000 ~ 2011 lottemart
 *  All rights reserved.
 */

package com.lottemart.epc.product.dao;

import java.sql.SQLException;
import java.util.List;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.product.model.PSCPPRD0010VO;

/**
 * @Class Name : PSCPPRD0010Dao
 * @Description : 상품검색 조회 Dao 클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 18. 오후 5:09:03 yskim
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
@Repository("pscpprd0010Dao")
public class PSCPPRD0010Dao extends AbstractDAO {

//	@Autowired
//	private SqlMapClient sqlMapClient;
	
	
	/**
	 * Desc : 상품검색 조회 메소드
	 * @Method Name : selectProductPopupList
	 * @param paramMap
	 * @return
	 * @throws SQLException
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PSCPPRD0010VO> selectProductPopupList(DataMap paramMap) throws SQLException {
		return (List<PSCPPRD0010VO>)getSqlMapClientTemplate().queryForList("PSCPPRD0010.selectProductPopupList", paramMap);
	}

	@SuppressWarnings("unchecked")
	public List<PSCPPRD0010VO> selectProductArrayPopupList(DataMap paramMap) throws SQLException {
		return (List<PSCPPRD0010VO>)getSqlMapClientTemplate().queryForList("PSCPPRD0010.selectProductArrayPopupList", paramMap);
	}
	/**
	 * Desc : 상품검색 카운트 조회 메소드
	 * @Method Name : selectProductPopupCount
	 * @param paramMap
	 * @return
	 * @throws SQLException
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@SuppressWarnings("unchecked")
	public List<DataMap> selectProductPopupCount(DataMap paramMap) throws SQLException {
		return (List<DataMap>)getSqlMapClientTemplate().queryForList("PSCPPRD0010.selectProductPopupCount", paramMap);
	}

	/**
	 * Desc : 상품구분코드 조회 메소드
	 * @Method Name : selectProdDivnCdList
	 * @return
	 * @throws SQLException
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@SuppressWarnings("unchecked")
	public List<DataMap> selectProdDivnCdList() throws SQLException {
		return (List<DataMap>)getSqlMapClientTemplate().queryForList("PSCPPRD0010.selectProdDivnCdList");
	}

	/**
	 * Desc : 대분류 카테고리 조회 메소드
	 * @Method Name : selectCategoryList
	 * @return
	 * @throws SQLException
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@SuppressWarnings("unchecked")
	public List<DataMap> selectCategoryList() throws SQLException {
		return (List<DataMap>)getSqlMapClientTemplate().queryForList("PSCPPRD0010.selectCategoryList");
	}

	/**
	 * Desc : 중분류 카테고리 조회 메소드
	 * @Method Name : selectMiddleCategoryList
	 * @param paramMap
	 * @return
	 * @throws SQLException
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@SuppressWarnings("unchecked")
	public List<DataMap> selectMiddleCategoryList(DataMap paramMap) throws SQLException {
		return (List<DataMap>)getSqlMapClientTemplate().queryForList("PSCPPRD0010.selectMiddleCategoryList", paramMap);
	}

	/**
	 * Desc : 소분류 카테고리 조회 메소드
	 * @Method Name : selectSubCategoryList
	 * @param paramMap
	 * @return
	 * @throws SQLException
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@SuppressWarnings("unchecked")
	public List<DataMap> selectSubCategoryList(DataMap paramMap) throws SQLException {
		return (List<DataMap>)getSqlMapClientTemplate().queryForList("PSCPPRD0010.selectSubCategoryList", paramMap);
	}

	/**
	 * Desc : 세분류 카테고리 조회 메소드
	 * @Method Name : selectDetailCategoryList
	 * @param paramMap
	 * @return
	 * @throws SQLException
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	
	/* 2015.10.30 by kmlee 카테고리 체계 변경으로 사용하지 않는 함수임.
	@SuppressWarnings("unchecked")
	public List<DataMap> selectDetailCategoryList(DataMap paramMap) throws SQLException {
		return (List<DataMap>)getSqlMapClientTemplate().queryForList("PSCPPRD0010.selectDetailCategoryList", paramMap);
	}
	*/
}
