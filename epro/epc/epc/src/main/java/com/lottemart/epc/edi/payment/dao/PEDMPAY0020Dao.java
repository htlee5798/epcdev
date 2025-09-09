package com.lottemart.epc.edi.payment.dao;

import java.util.List;
import java.util.Map;

import lcn.module.common.util.HashBox;
import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;


@Repository("pedmpay0020Dao")
public class PEDMPAY0020Dao extends AbstractDAO {
	
	
	public List selectCredLedInfo(Map<String,Object> map) throws Exception{
		return (List<HashBox>)getSqlMapClientTemplate().queryForList("PEDMPAY0020.TSC_CRED_LED-SELECT01",map);
	}
	
	public List selectCredLedStoreDetail(Map<String,Object> map) throws Exception{
		return (List<HashBox>)getSqlMapClientTemplate().queryForList("PEDMPAY0020.TSC_CRED_LED_STORE_DETAIL-SELECT01",map);
	}
	
	public List selectCredLedStoreInfo(Map<String,Object> map) throws Exception{
		return (List<HashBox>)getSqlMapClientTemplate().queryForList("PEDMPAY0020.TSC_CRED_LED_STORE-SELECT01",map);
	}
	
	public List selectFamilyLoan(Map<String,Object> map) throws Exception{
		return (List<HashBox>)getSqlMapClientTemplate().queryForList("PEDMPAY0020.TSC_FAMILY_LOAN-SELECT01",map);
	}
	
}
