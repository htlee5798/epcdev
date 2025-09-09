package com.lottemart.epc.common.dao;


import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.common.model.PSCMCOM0004VO;

/**
 * @Class Name : PSCMCOM0004Dao.java
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
@Repository("pscmcom0004Dao")
public class PSCMCOM0004Dao extends AbstractDAO
{
	/**
	 * 대분류(카테고리id:01 depth:2) 카테고리 목록
	 * @MethodName  : selectCategoryListDepth2
	 * @param String
	 * @return List<DataMap>
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<DataMap> selectCategoryListDepth2(String categoryTypeCd) throws SQLException{
		return getSqlMapClientTemplate().queryForList("pscmcom0004.selectCategoryListDepth2",categoryTypeCd);
	}
	
	/**
	 * 기본카테고리 목록
	 * @Method Name : selectCategoryList
	 * @param Map<String, String>
	 * @return List<VO>
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<PSCMCOM0004VO> selectCategoryList(DataMap paramMap) throws SQLException
	{
		return getSqlMapClientTemplate().queryForList("pscmcom0004.selectCategoryList",paramMap);
	}
	
	/**
	 * 기본카테고리 목록(IN 조건)
	 * @MethodName  : selectCategoryInList
	 * @param String
	 * @return List<DataMap>
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<DataMap> selectCategoryInList(Map<String, Object> paramMap) throws SQLException{
		return getSqlMapClientTemplate().queryForList("pscmcom0004.selectCategoryInList",paramMap);
	}
	
	/**
	 * 업체 주소 정보
	 * @MethodName  : selectVendorAddrList
	 * @param String
	 * @return List<DataMap>
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<DataMap> selectVendorAddrList(Map<String, Object> paramMap) throws SQLException{
		return getSqlMapClientTemplate().queryForList("pscmcom0004.selectVendorAddrList",paramMap);
	}
}
