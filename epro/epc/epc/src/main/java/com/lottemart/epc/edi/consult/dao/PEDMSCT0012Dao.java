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


@Repository("pedmsct0012Dao")
public class PEDMSCT0012Dao extends AbstractDAO {
	
	public List alertPageUpdatePageSelect(Map<String,Object> map) throws Exception{
		return (List<HashBox>)getSqlMapClientTemplate().queryForList("PEDMSCT0012.TSC_ALERT_PAGE-SELECT01",map);
	}
	
	public List alertPageUpdatePageSelectDetail(Map<String,Object> map) throws Exception{
		return (List<HashBox>)getSqlMapClientTemplate().queryForList("PEDMSCT0012.TSC_ALERT_PAGE-SELECT02",map);
	}
	
	
	
	
	
	
}
