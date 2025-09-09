package com.lottemart.epc.statistics.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.statistics.model.PSCMSTA0007VO;

@Repository("PSCMSTA0007Dao")
public class PSCMSTA0007Dao extends AbstractDAO  {

	@SuppressWarnings("unchecked")
	public List<DataMap> selectToDate() throws SQLException{
		Map<String, Object> paramMap = new HashMap<String, Object>();
		return (List<DataMap>)getSqlMapClientTemplate().queryForList("PSCMSTA0007.selectToDate", paramMap);
	}

	@SuppressWarnings("unchecked")
	public List<DataMap> selectAffiliateLinkNoList(PSCMSTA0007VO searchVO) throws SQLException{
		return (List<DataMap>)getSqlMapClientTemplate().queryForList("PSCMSTA0007.selectAffiliateLinkNoList", searchVO);
	}
	
	@SuppressWarnings("unchecked")
	public List<DataMap> selectDaumTotalOrder(PSCMSTA0007VO searchVO) throws SQLException{
		return (List<DataMap>)getSqlMapClientTemplate().queryForList("PSCMSTA0007.selectDaumTotalOrder", searchVO);
	}
	
	@SuppressWarnings("unchecked")
	public List<DataMap> selectDaumOrderList(PSCMSTA0007VO searchVO) throws SQLException{
		return (List<DataMap>)getSqlMapClientTemplate().queryForList("PSCMSTA0007.selectDaumOrderList", searchVO);
	}
	
	@SuppressWarnings("unchecked")
	public List<DataMap> selectDaumOrderListExcel(PSCMSTA0007VO searchVO) throws SQLException{
		return (List<DataMap>)getSqlMapClientTemplate().queryForList("PSCMSTA0007.selectDaumOrderListExcel", searchVO);
	}
	
}
