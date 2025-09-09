package com.lottemart.epc.statistics.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.statistics.model.PSCMSTA0003VO;

@Repository("asianaBalanceAccountDao")
public class PSCMSTA0003Dao extends AbstractDAO {

//	@Autowired
//	private SqlMapClient sqlMapClient;

	@SuppressWarnings("unchecked")
	public List<DataMap> selectToDate() throws SQLException{
		Map<String, Object> paramMap = new HashMap<String, Object>();
		return (List<DataMap>)getSqlMapClientTemplate().queryForList("PSCMSTA0003.selectToDate", paramMap);
	}	
	
	@SuppressWarnings("unchecked")
	public List<DataMap> getAsianaBalanceAccountList(PSCMSTA0003VO searchVO) throws SQLException{
		return (List<DataMap>)getSqlMapClientTemplate().queryForList("PSCMSTA0003.getAsianaBalanceAccountList", searchVO);
	}
	
	public DataMap getAsianaBalanceAccountSum(PSCMSTA0003VO searchVO) throws SQLException{
		return (DataMap)getSqlMapClientTemplate().queryForObject("PSCMSTA0003.getAsianaBalanceAccountSum", searchVO);
	}
		
	
	@SuppressWarnings("unchecked")
	public List<DataMap> getAsianaBalanceAccountListExcel(PSCMSTA0003VO searchVO) throws SQLException{
		return (List<DataMap>)getSqlMapClientTemplate().queryForList("PSCMSTA0003.getAsianaBalanceAccountListExcel", searchVO);
	}
}
