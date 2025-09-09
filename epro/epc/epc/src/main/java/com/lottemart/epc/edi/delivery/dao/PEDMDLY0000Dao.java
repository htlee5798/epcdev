package com.lottemart.epc.edi.delivery.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lcn.module.common.util.HashBox;
import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;


@Repository("pedmdly0000Dao")
public class PEDMDLY0000Dao extends AbstractDAO {
	
	public List selectStatusInfo(Map<String,Object> map) throws Exception{
		return (List<HashBox>)getSqlMapClientTemplate().queryForList("PEDMDLY0000.TSC_STATUS-SELECT01",map);
	}
	
	public List selectDeliverAcceptInfo(Map<String,Object> map) throws Exception{
		return (List<HashBox>)getSqlMapClientTemplate().queryForList("PEDMDLY0000.TSC_DELIVERY_ACCEPT-SELECT01",map);
	}
	
	public List selectDeliverRegInfo(Map<String,Object> map) throws Exception{
		return (List<HashBox>)getSqlMapClientTemplate().queryForList("PEDMDLY0000.TSC_DELIVERY_REG-SELECT01",map);
	}
	
	public void updateDeliverAcceptInfo(HashMap<String,Object> map ) throws Exception{
		getSqlMapClientTemplate().update("PEDMDLY0000.TSC_DELIVERY_ACCEPT-UPDATE01", map);
	}
	
	
	public void updateDeliverRegInfo(HashMap<String,Object> map ) throws Exception{
		getSqlMapClientTemplate().update("PEDMDLY0000.TSC_DELIVERY_REG-UPDATE01", map);
	}
	
	public HashMap cancelDeliverAccept(Map<String,Object> map) throws Exception{
		return (HashMap)getSqlMapClientTemplate().queryForObject("PEDMDLY0000.TSC_DELIVERY_CANCEL-SELECT01",map);
	}
}
