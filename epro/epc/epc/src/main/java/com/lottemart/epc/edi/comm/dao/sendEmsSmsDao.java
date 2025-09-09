package com.lottemart.epc.edi.comm.dao;

import java.util.HashMap;
import java.util.Map;
import lcn.module.framework.base.AbstractDAO;
import org.springframework.stereotype.Repository;

@Repository("sendemssmsDao")
public class sendEmsSmsDao extends AbstractDAO {
	
	public HashMap sendEMS(Map<String,Object> map) throws Exception{
		return (HashMap)getSqlMapClientTemplate().queryForObject("common.SEND_EMS-PROCEDURE01",map);
	}
	
	public HashMap sendSMS(Map<String,Object> map) throws Exception{
		return (HashMap)getSqlMapClientTemplate().queryForObject("common.SEND_SMS-PROCEDURE01",map);
	}
	
	
}




