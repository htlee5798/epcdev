package com.lottemart.epc.statistics.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.statistics.model.PSCMSTA0010VO;

@Repository("PSCMSTA0010Dao")
public class PSCMSTA0010Dao extends AbstractDAO {

	@SuppressWarnings("unchecked")
	public List<DataMap> selectToDate() throws SQLException{
		Map<String, Object> paramMap = new HashMap<String, Object>();
		return (List<DataMap>)getSqlMapClientTemplate().queryForList("PSCMSTA0010.selectToDate", paramMap);
	}
	
	public DataMap selectRecipeConEdmSummaryTotal(Map<String,String> paramMap) throws SQLException{
		return (DataMap)getSqlMapClientTemplate().queryForObject("PSCMSTA0010.selectRecipeConEdmSummaryTotal", paramMap);
	}
	
	@SuppressWarnings("unchecked")
	public List<DataMap> selectRecipeConEdmSummaryList(PSCMSTA0010VO searchVO) throws SQLException{
		return (List<DataMap>)getSqlMapClientTemplate().queryForList("PSCMSTA0010.selectRecipeConEdmSummaryList", searchVO);
	}
	
	@SuppressWarnings("unchecked")
	public List<DataMap> selectRecipeConEdmSummaryListExcel(PSCMSTA0010VO searchVO) throws SQLException{
		return (List<DataMap>)getSqlMapClientTemplate().queryForList("PSCMSTA0010.selectRecipeConEdmSummaryListExcel", searchVO);
	}
}
