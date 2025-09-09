package com.lottemart.epc.edi.delivery.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lcn.module.common.util.HashBox;
import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;


@Repository("pedmdly0020Dao")
public class PEDMDLY0020Dao extends AbstractDAO {
	
	public List selectStatusInfo(Map<String,Object> map) throws Exception{
		return (List<HashBox>)getSqlMapClientTemplate().queryForList("PEDMDLY0020.TSC_STATUS_TOY-SELECT01",map);
	}
	
	public List selectDeliverAcceptInfo(Map<String,Object> map) throws Exception{
		return (List<HashBox>)getSqlMapClientTemplate().queryForList("PEDMDLY0020.TSC_DELIVERY_ACCEPT_TOY-SELECT01",map);
	}
	
	public List selectDeliverRegInfo(Map<String,Object> map) throws Exception{
		return (List<HashBox>)getSqlMapClientTemplate().queryForList("PEDMDLY0020.TSC_DELIVERY_REG_TOY-SELECT01",map);
	}
	
	public void updateDeliverAcceptInfo(HashMap<String,Object> map  ) throws Exception{
		getSqlMapClientTemplate().update("PEDMDLY0020.TSC_DELIVERY_ACCEPT_TOY-UPDATE01", map);
	}
	
	
	public void updateDeliverRegInfo(HashMap<String,Object> map ) throws Exception{
		getSqlMapClientTemplate().update("PEDMDLY0020.TSC_DELIVERY_REG_TOY-UPDATE01", map);
	}
	
	
}


