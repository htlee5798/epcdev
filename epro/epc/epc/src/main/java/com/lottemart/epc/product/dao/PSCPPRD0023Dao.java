package com.lottemart.epc.product.dao;

import java.sql.SQLException;
import java.util.List;

import lcn.module.framework.base.AbstractDAO;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.product.model.PSCPPRD0020VO;

import org.springframework.stereotype.Repository;

@Repository("PSCPPRD0023Dao")
public class PSCPPRD0023Dao extends AbstractDAO{
	
	/**
	 * 추가구성품 조회
	 * @MethodName  : selectProdComponentList
	 * @param String
	 * @return List<DataMap>
	 * @throws Exception
	 */
	public List<DataMap> selectProdComponentList(DataMap paramMap) throws SQLException{
		return getSqlMapClientTemplate().queryForList("pscpprd0023.selectProdComponentList",paramMap);
	}
		
	/**
	 * 추가구성품 등록 여부 COUNT
	 * @MethodName  : selectProdComponentList
	 * @param String
	 * @return List<DataMap>
	 * @throws Exception
	 */
	public String selectProdCrossSellingCount(DataMap paramMap) throws SQLException{
		return (String)getSqlMapClientTemplate().queryForObject("pscpprd0023.selectProdCrossSellingCount",paramMap);
	}
	
	
	/**
	 * 추가구성품, 연관상품 삭제(저장전)
	 * @MethodName  : selectProdComponentList
	 * @param String
	 * @return List<DataMap>
	 * @throws Exception
	 */
	public int deleteProdComponent(DataMap paramMap) throws SQLException{
		return getSqlMapClientTemplate().update("pscpprd0023.deleteProdComponent", paramMap);
	}
	
	/**
	 * 본상품 CAT_CD를 추가구성품에 update
	 * @MethodName  : selectProdComponentList
	 * @param String
	 * @return List<DataMap>
	 * @throws Exception
	 */
	public int updateProductCatCd(DataMap paramMap) throws SQLException{
		return getSqlMapClientTemplate().update("pscpprd0023.updateProductCatCd", paramMap);
	}
	
	/**
	 * 추가구성품, 연관상품 등록
	 * @MethodName  : insertProdComponent
	 * @param String
	 * @return List<DataMap>
	 * @throws Exception
	 */
	public int insertProdComponent(DataMap paramMap) throws SQLException{
		return getSqlMapClientTemplate().update("pscpprd0023.insertProdComponent", paramMap);
	}
	
	/**
	 * 추가구성품여부 UPDATE
	 * @MethodName  : insertProdComponent
	 * @param String
	 * @return List<DataMap>
	 * @throws Exception
	 */
	public int updateCtpdDealYn(DataMap paramMap) throws SQLException{
		return getSqlMapClientTemplate().update("pscpprd0023.updateCtpdDealYn", paramMap);
	}
}
