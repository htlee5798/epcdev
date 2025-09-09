package com.lottemart.epc.edi.payment.dao;

import java.util.List;
import java.util.Map;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.epc.edi.payment.model.NEDMPAY0030VO;


@Repository("nedmpay0030Dao")
public class NEDMPAY0030Dao extends AbstractDAO {
	
	
	public List<NEDMPAY0030VO> selectPaymentStoreInfo(NEDMPAY0030VO map) throws Exception{
		return (List<NEDMPAY0030VO>)getSqlMapClientTemplate().queryForList("NEDMPAY0030.TSC_PAYMENT_STORE-SELECT01",map);
	}
	
}


