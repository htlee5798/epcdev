package com.lottemart.epc.edi.order.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lcn.module.common.util.HashBox;
import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;


@Repository("pedmord0020Dao")
public class PEDMORD0020Dao extends AbstractDAO {
	
	public List selectOrdAble(Map<String,Object> map) throws Exception{
		return (List<HashBox>)getSqlMapClientTemplate().queryForList("PEDMORD0020.TSC_ORD_ABLE-SELECT01",map);
	}
	
	public List selectOrdSply(Map<String,Object> map) throws Exception{
		return (List<HashBox>)getSqlMapClientTemplate().queryForList("PEDMORD0020.TSC_ORD_SPLY-SELECT01",map);
	}
	
	public void updateOrdSplyTime(HashMap<String,Object> map) throws Exception{
		getSqlMapClientTemplate().update("PEDMORD0020.TSC_ORD_SPLY_TIME-UPDATE01", map);
	}
	
	public void updateOrdSply(Map<String,Object> map ) throws Exception{
		getSqlMapClientTemplate().update("PEDMORD0020.TSC_ORD_SPLY-UPDATE01", map);
	}
	
	
}





