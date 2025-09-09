package com.lottemart.epc.statistics.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import lcn.module.framework.base.AbstractDAO;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.statistics.model.PSCMSTA0013VO;

@Repository("PSCMSTA0013Dao")
public class PSCMSTA0013Dao  extends AbstractDAO  {

	
	@SuppressWarnings("unchecked")
	public List<DataMap> selectCrossPicUpList(PSCMSTA0013VO searchVO) throws SQLException{
		return getSqlMapClientTemplate().queryForList("PSCMSTA0013.selectCrossPicUpList", searchVO );
	}

	@SuppressWarnings("unchecked")
	public List<DataMap> selectCrossPicUpListExcel(PSCMSTA0013VO searchVO) throws SQLException{
		return getSqlMapClientTemplate().queryForList("PSCMSTA0013.selectCrossPicUpListExcel", searchVO );
	}
	
	@SuppressWarnings("unchecked")
	public List<DataMap> selectSuperStrList() throws SQLException{
		return getSqlMapClientTemplate().queryForList("PSCMSTA0013.selectSuperStrList");
	}
	
	@SuppressWarnings("unchecked")
	public List<DataMap> selectMartStrList() throws SQLException{
		return getSqlMapClientTemplate().queryForList("PSCMSTA0013.selectMartStrList");
	}
	
	@SuppressWarnings("unchecked")
	public List<DataMap> selectPickupStsList() throws SQLException{
		return getSqlMapClientTemplate().queryForList("PSCMSTA0013.selectPickupStsList");
	}
	
	@SuppressWarnings("unchecked")
	public List<DataMap> selectDeliStatusList() throws SQLException{
		return getSqlMapClientTemplate().queryForList("PSCMSTA0013.selectDeliStatusList");
	}
	
	@SuppressWarnings("unchecked")
	public void insertPickupStatus(Map<String, String> paramMap) throws SQLException{
		 getSqlMapClientTemplate().insert("PSCMSTA0013.insertPickupStatus", paramMap);
	}
}
