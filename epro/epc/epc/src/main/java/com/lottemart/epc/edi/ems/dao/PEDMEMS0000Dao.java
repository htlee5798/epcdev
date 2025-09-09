package com.lottemart.epc.edi.ems.dao;

import java.util.HashMap;
import java.util.Map;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;


@Repository("pedmems0000Dao")
public class PEDMEMS0000Dao extends AbstractDAO {
	
	
	public HashMap selectEmsData(Map<String,Object> map) throws Exception{
		return (HashMap)getSqlMapClientTemplate().queryForObject("PEDMEMS0000.TSC_EMS_DATA-SELECT01",map);
	}
}
