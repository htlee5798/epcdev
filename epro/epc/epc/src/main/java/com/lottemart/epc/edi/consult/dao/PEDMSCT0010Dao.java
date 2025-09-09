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


@Repository("pedmsct0010Dao")
public class PEDMSCT0010Dao extends AbstractDAO {
	
	public List orderStopResultSelect(Map<String,Object> map) throws Exception{
		return (List<HashBox>)getSqlMapClientTemplate().queryForList("PEDMCST0010.TSC_ORDER_STOP_RESULT-SELECT01",map);
	}
	
	
}
