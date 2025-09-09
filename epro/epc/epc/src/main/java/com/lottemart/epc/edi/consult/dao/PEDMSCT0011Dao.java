package com.lottemart.epc.edi.consult.dao;

import java.util.List;
import java.util.Map;

import lcn.module.common.util.HashBox;
import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;


@Repository("pedmsct0011Dao")
public class PEDMSCT0011Dao extends AbstractDAO {
	
	public List orderStopResultSelect(Map<String,Object> map) throws Exception{
		return (List<HashBox>)getSqlMapClientTemplate().queryForList("PEDMCST0011.TSC_ORDER_STOP_RESULT-SELECT01aaa",map);
	}
	
	
}
