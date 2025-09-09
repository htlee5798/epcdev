package com.lottemart.epc.system.dao;


import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.lottemart.common.util.DataMap;
import com.lottemart.epc.system.model.PSCMSYS0003VO;

/**
 * @Class Name : PSCMSYS0003Dao.java
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
@Repository("pscmsys0003Dao")
public class PSCMSYS0003Dao extends AbstractDAO
{
	@Autowired
	private SqlMapClient sqlMapClient;
	
	public int selectPartnerTotalCnt(DataMap dataMap) throws SQLException
	{
		Integer iTotCnt = new Integer(0);
		iTotCnt = (Integer)sqlMapClient.queryForObject("pscmsys0003.selectPartnerTotalCnt", dataMap);
	
		return iTotCnt.intValue();
	}
	
	@SuppressWarnings("unchecked")
	public List<PSCMSYS0003VO> selectPartnerList(DataMap dataMap) throws SQLException
	{
		return sqlMapClient.queryForList("pscmsys0003.selectPartnerList", dataMap);
	}
	
	public void insertPartnerPopup(PSCMSYS0003VO vo) throws SQLException{
		insert("pscmsys0003.insertPartnerPopup", vo);
	}
	
	public int updatePartnerList(PSCMSYS0003VO vo) throws SQLException {
		return getSqlMapClientTemplate().update("pscmsys0003.updatePartnerList", vo);
	}
	
	public int deletePartnerList(PSCMSYS0003VO vo) throws SQLException {
		return getSqlMapClientTemplate().update("pscmsys0003.deletePartnerList", vo);
	}
	

}
