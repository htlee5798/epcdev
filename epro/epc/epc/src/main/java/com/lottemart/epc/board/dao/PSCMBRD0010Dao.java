package com.lottemart.epc.board.dao;


import java.sql.SQLException;
import java.util.List;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.common.util.DataMap;

/**
 * @Class Name : PSCMBRD0010Dao.java
 * @Description :
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2016. 01. 15. projectBOS32
 * 
 * @Copyright (C) 2000 ~ 2011 롯데정보통신(주) All right reserved.
 * </pre>
 */
@Repository("pscmbrd0010Dao")
public class PSCMBRD0010Dao extends AbstractDAO 
{
	/**
	 * 업체문의사항 목록
	 * @Method Name : selectList
	 * @param DataMap
	 * @return List
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<DataMap> selectList(DataMap paramMap) throws SQLException
	{
		return (List<DataMap>)getSqlMapClientTemplate().queryForList("pscmbrd0010.selectBoardList", paramMap);
	}
	
}
