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


@Repository("pedmsct0003Dao")
public class PEDMSCT0003Dao extends AbstractDAO {
	
	
	public void estimationInsert(Map<String,Object> map, String pid) throws Exception{
		map.put("pid", pid);
		getSqlMapClientTemplate().insert("PEDMSCT0003.TSC_ESTIMATION-INSERT01",map);
	}
	
	public void estimationSheetInsert(HashMap<String,Object> map) throws Exception{
		getSqlMapClientTemplate().insert("PEDMSCT0003.TSC_ESTIMATION_SHEET-INSERT01",map);
	}

	
	
	public String estimationSelectCount(String kkk) throws Exception{
		return (String)getSqlMapClientTemplate().queryForObject("PEDMSCT0003.TEST",kkk);
	}
	
}
