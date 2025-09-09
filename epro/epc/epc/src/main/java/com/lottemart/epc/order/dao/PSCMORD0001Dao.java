
package com.lottemart.epc.order.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapException;
import com.lottemart.common.util.DataMap;
import com.lottemart.epc.order.model.PSCMORD0001VO;

@Repository("PSCMORD0001Dao")
public class PSCMORD0001Dao extends AbstractDAO{

	@SuppressWarnings("unchecked")
	public List<DataMap> selectAllOnlineStore() throws SQLException{
		return (List<DataMap>)getSqlMapClientTemplate().queryForList("PSCMORD0001.selectAllOnlineStore");
	}	
	
	public DataMap selectHodevMallStrYn(PSCMORD0001VO searchVO) throws SQLException{
		return (DataMap)getSqlMapClientTemplate().queryForObject("PSCMORD0001.selectHodevMallStrYn", searchVO);
	}		

	@SuppressWarnings("unchecked")
	public List<DataMap> selectCodeList(PSCMORD0001VO searchVO) throws SQLException{
		return (List<DataMap>)getSqlMapClientTemplate().queryForList("PSCMORD0001.selectCodeList", searchVO);
	}	
	
	@SuppressWarnings("unchecked")
	public List<DataMap> selectOrderList(DataMap paramMap) throws SQLException{
		List<DataMap> dmList = new ArrayList<DataMap>();
			
		if(("2").equals(paramMap.getString("searchType"))){
			dmList = (List<DataMap>)getSqlMapClientTemplate().queryForList("PSCMORD0001.selectSearchSaleList", paramMap);
		}else{
			dmList = (List<DataMap>)getSqlMapClientTemplate().queryForList("PSCMORD0001.selectSearchOrderList", paramMap);
		}
		
		return dmList;
		
	}		
	
	@SuppressWarnings("unchecked")
	public List<DataMap> selectOrderListExcel(Map<String, String> paramMap) throws SQLException{
		List<DataMap> dmList = new ArrayList<DataMap>();
		
		if(("2").equals( paramMap.get("searchType") ) ){
			dmList = (List<DataMap>)getSqlMapClientTemplate().queryForList("PSCMORD0001.selectSearchSaleListExcel", paramMap);			
		}else{
			dmList = (List<DataMap>)getSqlMapClientTemplate().queryForList("PSCMORD0001.selectSearchOrderListExcel", paramMap);
		}
		return dmList;
	}		

	public String selectCustInfoList(Map<String, Object> paramMap) throws SqlMapException{
		if(("2").equals(paramMap.get("searchType"))){
			return (String)getSqlMapClientTemplate().queryForObject("PSCMORD0001.selectCustInfoListForSale",paramMap);
		}else{
			return (String)getSqlMapClientTemplate().queryForObject("PSCMORD0001.selectCustInfoList",paramMap);
		}
	}
}
