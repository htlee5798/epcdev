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


@Repository("pedmsct0004Dao")
public class PEDMSCT0004Dao extends AbstractDAO {
	
	public List estimationMainSelect(Map<String,Object> map) throws Exception{
		return (List<HashBox>)getSqlMapClientTemplate().queryForList("PEDMSCT0004.TSC_ESTIMATION-SELECT01",map);
	}
	
	public HashMap estimationMainSelectDetailTop(String pid) throws Exception{
		return (HashMap)getSqlMapClientTemplate().queryForObject("PEDMSCT0004.TSC_ESTIMATION_TOP-SELECT01",pid);
	}
	
	public List estimationMainSelectDetailBottom(String pid) throws Exception{
		return (List<HashBox>)getSqlMapClientTemplate().queryForList("PEDMSCT0004.TSC_ESTIMATION_BOTTOM-SELECT01",pid);
	}
	
	public void estimationMainDelete(String pid) throws Exception{
		getSqlMapClientTemplate().delete("PEDMSCT0004.TSC_ESTIMATION-DELETE01",pid);
	}
	
	public void estimationMainDetailDelete(String pid) throws Exception{
		getSqlMapClientTemplate().delete("PEDMSCT0004.TSC_ESTIMATION_SHEET-DELETE01",pid);
	}
	
	public void estimationUpdate(Map<String,Object> map, String pid) throws Exception{
		
		map.put("input_pid", pid);
		
		getSqlMapClientTemplate().update("PEDMSCT0004.TSC_ESTIMATION-UPDATE01",map);
	}
}


