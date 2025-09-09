package com.lottemart.epc.product.dao;


import java.sql.SQLException;
import java.util.List;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.lottemart.common.util.DataMap;

/**
 * @Class Name : PSCPPRD0007Dao.java
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
@Repository("pscpprd0007Dao")
public class PSCPPRD0007Dao extends AbstractDAO
{
	@Autowired
	private SqlMapClient sqlMapClient;
	
	/**
	 * 원산지정보 목록
	 * @Method Name : selectLocationList
	 * @param 
	 * @return DataMap
	 * @throws SQLException
	 */	
	@SuppressWarnings("unchecked")
	public List<DataMap> selectLocationList() throws SQLException
	{
		return (List<DataMap>)sqlMapClient.queryForList("pscpprd0007.selectLocationList");
	}

}
