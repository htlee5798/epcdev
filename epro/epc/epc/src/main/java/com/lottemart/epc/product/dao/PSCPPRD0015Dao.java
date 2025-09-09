package com.lottemart.epc.product.dao;


import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.product.model.PSCPPRD0015VO;

/**
 * @Class Name : PSCPPRD0015Dao.java
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
@Repository("pscpprd0015Dao")
public class PSCPPRD0015Dao extends AbstractDAO
{
	/**
     * 점포 목록
     * @Method Name : selectPrdStoreList
     * @param String
     * @return List<DataMap>
     * @throws SQLException  DB 관련 오류
     */
	@SuppressWarnings("unchecked")
	public List<DataMap> selectPrdStoreList(String strGubun) throws SQLException
	{
		return (List<DataMap>)getSqlMapClientTemplate().queryForList("pscpprd0015.selectPrdStoreList", strGubun);
	}

	/**
	 * 상품 아이콘 목록
	 * Desc : 
	 * @Method Name : selectPrdIconList
	 * @param Map<String, String>
	 * @return List<VO>
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<PSCPPRD0015VO> selectPrdIconList(Map<String, String> paramMap) throws SQLException
	{
		return getSqlMapClientTemplate().queryForList("pscpprd0015.selectPrdIconList",paramMap);
	}

	/**
	 * 상품 아이콘 수정 처리
	 * Desc : 
	 * @Method Name : updatePrdIconList
	 * @param VO
	 * @return int
	 * @throws SQLException
	 */	
	public int updatePrdIconList(PSCPPRD0015VO bean) throws SQLException
	{
		return getSqlMapClientTemplate().update("pscpprd0015.updatePrdIconList",bean);	
	}

}
