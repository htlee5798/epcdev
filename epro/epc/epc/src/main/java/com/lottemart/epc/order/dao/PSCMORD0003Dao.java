
package com.lottemart.epc.order.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.order.model.PSCMORD0003VO;

@Repository("PSCMORD0003Dao")
public class PSCMORD0003Dao extends AbstractDAO{

	@SuppressWarnings("unchecked")
	public List<DataMap> selectAllOnlineStore() throws SQLException{
		return (List<DataMap>)getSqlMapClientTemplate().queryForList("PSCMORD0003.selectAllOnlineStore");
	}	
	
	public DataMap selectHodevMallStrYn(PSCMORD0003VO searchVO) throws SQLException{
		return (DataMap)getSqlMapClientTemplate().queryForObject("PSCMORD0003.selectHodevMallStrYn", searchVO);
	}		

	@SuppressWarnings("unchecked")
	public List<DataMap> selectCodeList(PSCMORD0003VO searchVO) throws SQLException{
		return (List<DataMap>)getSqlMapClientTemplate().queryForList("PSCMORD0003.selectCodeList", searchVO);
	}	
	
	@SuppressWarnings("unchecked")
	public List<DataMap> selectOrderList(DataMap paramMap) throws SQLException{
		return (List<DataMap>)getSqlMapClientTemplate().queryForList("PSCMORD0003.selectOrderList", paramMap);
	}		
	
	@SuppressWarnings("unchecked")
	public List<DataMap> selectOrderListExcel(Map<String, String> paramMap) throws SQLException{
		return (List<DataMap>)getSqlMapClientTemplate().queryForList("PSCMORD0003.selectOrderListExcel", paramMap);
	}		

	@SuppressWarnings("unchecked")
	public String selectCustInfo(Map<String, Object> paramMap) throws SQLException{
		return (String)getSqlMapClientTemplate().queryForObject("PSCMORD0003.selectCustInfo", paramMap);
	}		

	
}
