package com.lottemart.epc.edi.product.dao;


import java.util.List;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.common.util.DataMap;


/**
 * @Class Name : NEDMPRO0050Dao
 * @Description : 온라인신상품등록(딜) Dao 
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>O
 *   
 *   수정일      			수정자           		수정내용
 *  -------    		--------    	---------------------------
 * 	2016.04.22		projectBOS32 	최초생성
 * </pre>
 */

@Repository("nEDMPRO0050Dao")
public class NEDMPRO0050Dao extends AbstractDAO {
	
	/**
	 * 묶음상품 삭제 (저장전)
	 * @param paramMap
	 * @throws Exception
	 */
	public void deleteDealProduct(DataMap paramMap) throws Exception {
		getSqlMapClientTemplate().delete("NEDMPRO0050.deleteDealProduct", paramMap);
	}
		
	/**
	 * 묶음상품 등록
	 * @param paramMap
	 * @throws Exception
	 */
	public void insertDealProduct(DataMap paramMap) throws Exception {
		getSqlMapClientTemplate().update("NEDMPRO0050.insertDealProduct", paramMap);
	}
	
	/**
	 * 묶음상품 조회
	 * @param paramMap
	 * @throws Exception
	 */
	public List<DataMap> selectDealProductList(DataMap paramMap) throws Exception {
		return getSqlMapClientTemplate().queryForList("NEDMPRO0050.selectDealProductList", paramMap);
	}

	/**
	 * 테마 목록 조회
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<DataMap> selectDealThemaList(DataMap paramMap) throws Exception {
		return getSqlMapClientTemplate().queryForList("NEDMPRO0050.selectDealThemaList", paramMap);
	}

	/**
	 * 테마 딜 상품목록 조회
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<DataMap> selectDealThemaProdList(DataMap paramMap) throws Exception {
		return getSqlMapClientTemplate().queryForList("NEDMPRO0050.selectDealThemaProdList", paramMap);
	}

	/**
	 * 테마 상품 등록 
	 * @param paramMap
	 * @throws Exception
	 */
	public void insertDealThema(DataMap paramMap) throws Exception {
		getSqlMapClientTemplate().update("NEDMPRO0050.insertDealThema", paramMap);
	}

	/**
	 * 테마 삭제
	 * @param paramMap
	 * @throws Exception
	 */
	public void deleteDealThema(DataMap paramMap) throws Exception {
		getSqlMapClientTemplate().delete("NEDMPRO0050.deleteDealThema", paramMap);
	}
}
