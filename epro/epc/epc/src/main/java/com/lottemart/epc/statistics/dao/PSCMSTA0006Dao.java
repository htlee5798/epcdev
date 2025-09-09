package com.lottemart.epc.statistics.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.statistics.model.PSCMSTA0006VO;

@Repository("PSCMSTA0006Dao")
public class PSCMSTA0006Dao extends AbstractDAO {

	@SuppressWarnings("unchecked")
	public List<DataMap> selectToDate() throws SQLException{
		Map<String, Object> paramMap = new HashMap<String, Object>();
		return (List<DataMap>)getSqlMapClientTemplate().queryForList("PSCMSTA0006.selectToDate", paramMap);
	}

	@SuppressWarnings("unchecked")
	public List<DataMap> selectAffiliateLinkNoList(PSCMSTA0006VO searchVO) throws SQLException{
		return (List<DataMap>)getSqlMapClientTemplate().queryForList("PSCMSTA0006.selectAffiliateLinkNoList", searchVO);
	}
	
	@SuppressWarnings("unchecked")
	public List<DataMap> selectNaverEdmSummaryTotal(PSCMSTA0006VO searchVO) throws SQLException{
		return (List<DataMap>)getSqlMapClientTemplate().queryForList("PSCMSTA0006.selectNaverEdmSummaryTotal", searchVO);
	}
	
	@SuppressWarnings("unchecked")
	public List<DataMap> selectNaverEdmSummaryList(PSCMSTA0006VO searchVO) throws SQLException{
		return (List<DataMap>)getSqlMapClientTemplate().queryForList("PSCMSTA0006.selectNaverEdmSummaryList", searchVO);
	}
	
	@SuppressWarnings("unchecked")
	public List<DataMap> selectNaverEdmSummaryListExcel(PSCMSTA0006VO searchVO) throws SQLException{
		return (List<DataMap>)getSqlMapClientTemplate().queryForList("PSCMSTA0006.selectNaverEdmSummaryListExcel", searchVO);
	}
}
