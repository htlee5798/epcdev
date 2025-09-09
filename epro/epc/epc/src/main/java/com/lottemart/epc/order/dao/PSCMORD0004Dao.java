
package com.lottemart.epc.order.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.order.model.PSCMORD0004VO;

@Repository("PSCMORD0004Dao")
public class PSCMORD0004Dao extends AbstractDAO{

	@SuppressWarnings("unchecked")
	public List<DataMap> selectAllOnlineStore() throws SQLException{
		return (List<DataMap>)getSqlMapClientTemplate().queryForList("PSCMORD0004.selectAllOnlineStore");
	}	
	
	public DataMap selectHodevMallStrYn(PSCMORD0004VO searchVO) throws SQLException{
		return (DataMap)getSqlMapClientTemplate().queryForObject("PSCMORD0004.selectHodevMallStrYn", searchVO);
	}		

	@SuppressWarnings("unchecked")
	public List<DataMap> selectCodeList(PSCMORD0004VO searchVO) throws SQLException{
		return (List<DataMap>)getSqlMapClientTemplate().queryForList("PSCMORD0004.selectCodeList", searchVO);
	}	
	
	@SuppressWarnings("unchecked")
	public List<DataMap> selectOrderItemList(DataMap paramMap) throws SQLException{
		List<DataMap> dmList = new ArrayList<DataMap>();
		
		if(("2").equals(paramMap.getString("searchType"))){
			dmList = (List<DataMap>)getSqlMapClientTemplate().queryForList("PSCMORD0004.selectSaleItemList", paramMap);
		}else{
			dmList = (List<DataMap>)getSqlMapClientTemplate().queryForList("PSCMORD0004.selectOrderItemList", paramMap);
		}
		
		return dmList;
	}		
	
	@SuppressWarnings("unchecked")
	public List<DataMap> selectOrderItemListExcel(Map<String, String> paramMap) throws SQLException{
		List<DataMap> dmList = new ArrayList<DataMap>();
		
		if(("2").equals( paramMap.get("searchType") ) ){
			dmList = getSqlMapClientTemplate().queryForList("PSCMORD0004.selectSaleItemListExcel", paramMap);
		}else{
			dmList = getSqlMapClientTemplate().queryForList("PSCMORD0004.selectOrderItemListExcel", paramMap);
		}
		return dmList;
	}		
	
	@SuppressWarnings("unchecked")
	public String selectCustInfoByGoods(Map<String, Object> paramMap) throws SQLException{
		
		if(("2").equals(paramMap.get("searchType"))){
			return (String)getSqlMapClientTemplate().queryForObject("PSCMORD0004.selectCustInfoByGoodsForSale", paramMap);
		}else{
			return (String)getSqlMapClientTemplate().queryForObject("PSCMORD0004.selectCustInfoByGoods", paramMap);
		}
	}		
	
}
