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
import com.lottemart.epc.edi.product.model.PEDMPRO0006VO;


@Repository("pedmsct0006Dao")
public class PEDMSCT0006Dao extends AbstractDAO {
	
	
	public HashBox selectConsultVendor(Map<String,Object> map) {
		// TODO Auto-generated method stub
		return (HashBox)getSqlMapClientTemplate().queryForObject("PEDMSCT0006.TED_VENDOR_EDI-SELECT01",map);
	}
	
	public void updateConsultVendorPass(Map<String,Object> map) throws Exception{
		getSqlMapClientTemplate().update("PEDMSCT0006.TED_VENDOR_EDI-UPDATE01",map);
	}
	
	public void updateConsultVendorConsult(Map<String,Object> map) throws Exception{
		getSqlMapClientTemplate().update("PEDMSCT0006.TED_VENDOR_EDI-UPDATE02",map);
	}
	
	
	
}
