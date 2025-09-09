package com.lottemart.epc.board.dao;


import java.sql.SQLException;
import java.util.List;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.lottemart.common.util.DataMap;
import com.lottemart.epc.board.model.PSCMBRD0005SearchVO;

/**
 * @Class Name : PSCMBRD0005Dao.java
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
@Repository("pscpbrd0005Dao")
public class PSCMBRD0005Dao extends AbstractDAO
{

	/**
	 * 업체문의사항 글상태 목록
	 * @Method Name : selectStatusList
	 * @param VO
	 * @return List
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<DataMap> selectStatusList(PSCMBRD0005SearchVO searchVO) throws SQLException
	{
		return (List<DataMap>)getSqlMapClientTemplate().queryForList("pscmbrd0005.selectStatusList", searchVO);
	}

	/**
	 * 업체문의사항 목록
	 * @Method Name : selectList
	 * @param DataMap
	 * @return List
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<PSCMBRD0005SearchVO> selectList(DataMap paramMap) throws SQLException
	{
		return (List<PSCMBRD0005SearchVO>)getSqlMapClientTemplate().queryForList("pscmbrd0005.selectBoardList", paramMap);
	}

}
