package com.lottemart.epc.edi.sample.dao;

import java.util.List;
import java.util.Map;

import lcn.module.common.util.HashBox;
import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

@Repository("pedmasm001Dao")
public class PEDMASM001Dao extends AbstractDAO {

//	@Autowired
//	public PEDMASM001Dao(SqlMapClient sqlMapClient) {
//		super();
//		setSqlMapClient(sqlMapClient);
//	}

	public List selectOrderInfo(Map<String,Object> map) throws Exception{
		return (List<HashBox>)getSqlMapClientTemplate().queryForList("PEDMSAM001.TSC_ORD_PROD-SELECT01",map);
	}
}
