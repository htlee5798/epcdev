package com.lottemart.epc.edi.consult.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lcn.module.common.util.HashBox;
import lcn.module.framework.base.AbstractDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;


@Repository("pedmsct0009Dao")
public class PEDMSCT0009Dao extends AbstractDAO {
	
	public List orderStopSelect(Map<String,Object> map) throws Exception{
		return (List<HashBox>)getSqlMapClientTemplate().queryForList("PEDMCST0009.TSC_ORDER_STOP-SELECT01",map);
	}
	
	public void orderStopInsert(HashMap<String,Object> map) throws Exception{
		getSqlMapClientTemplate().insert("PEDMCST0009.TSC_ORDER_STOP-INSERT01", map);
	}
	
	
}

