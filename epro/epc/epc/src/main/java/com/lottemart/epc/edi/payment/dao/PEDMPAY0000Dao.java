package com.lottemart.epc.edi.payment.dao;

import java.util.List;
import java.util.Map;

import lcn.module.common.util.HashBox;
import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;


@Repository("pedmpay0000Dao")
public class PEDMPAY0000Dao extends AbstractDAO {
	
	
	public List selectCominforInfo(Map<String,Object> map) throws Exception{
		return (List<HashBox>)getSqlMapClientTemplate().queryForList("PEDMPAY0000.TSC_COMINFOR-SELECT01",map);
	}
	
	public List selectPaymentDayInfo(Map<String,Object> map) throws Exception{
		return (List<HashBox>)getSqlMapClientTemplate().queryForList("PEDMPAY0000.TSC_PAYMENT_DAY-SELECT01",map);
	}
	
	public List selectPaymentStoreInfo(Map<String,Object> map) throws Exception{
		return (List<HashBox>)getSqlMapClientTemplate().queryForList("PEDMPAY0000.TSC_PAYMENT_STORE-SELECT01",map);
	}
	
	public List selectCredAggInfo(Map<String,Object> map) throws Exception{
		return (List<HashBox>)getSqlMapClientTemplate().queryForList("PEDMPAY0000.TSC_CRED_AGG-SELECT01",map);
	}
	
	public List selectLogiAmtInfo(Map<String,Object> map) throws Exception{
		return (List<HashBox>)getSqlMapClientTemplate().queryForList("PEDMPAY0000.TSC_LOGI_AMT-SELECT01",map);
	}
	
	public List selectPromoSaleInfo(Map<String,Object> map) throws Exception{
		return (List<HashBox>)getSqlMapClientTemplate().queryForList("PEDMPAY0000.TSC_PROMO_SALE-SELECT01",map);
	}
	public List selectPromoNewSaleInfo(Map<String,Object> map) throws Exception{
		return (List<HashBox>)getSqlMapClientTemplate().queryForList("PEDMPAY0000.TSC_PROMO_SALE-SELECT02",map);
	}
	
}


