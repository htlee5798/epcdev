package com.lottemart.epc.board.dao;


import java.sql.SQLException;
import java.util.List;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.common.util.DataMap;

/**
 * @Class Name : PSCMBRD0008Dao.java
 * @Description :
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 12. 16. 오후 03:03:03 mjChoi
 * 
 * @Copyright (C) 2000 ~ 2011 롯데정보통신(주) All right reserved.
 * </pre>
 */
@Repository("pscpbrd0008Dao")
public class PSCMBRD0008Dao extends AbstractDAO 
{
	/**
	 * 콜센터 목록을 조회하는 메소드
	 * @Method Name : selectCallCenterList
	 * @param DataMap
	 * @return List<DataMap>
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<DataMap> selectCallCenterList(DataMap paramMap) throws SQLException
	{
		return (List<DataMap>)getSqlMapClientTemplate().queryForList("pscmbrd0008.selectCallCenterList", paramMap);
	}
	
}
