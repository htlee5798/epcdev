package com.lottemart.epc.edi.payment.dao;

import java.util.List;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.epc.edi.payment.model.NEDMPAY0120VO;


@Repository("nedmpay0120Dao")
public class NEDMPAY0120Dao extends AbstractDAO {
	
	
	
	public List<NEDMPAY0120VO> selectCredLedStoreDetail(NEDMPAY0120VO map) throws Exception{
		return (List<NEDMPAY0120VO>)getSqlMapClientTemplate().queryForList("NEDMPAY0120.TSC_CRED_LED_STORE_DETAIL-SELECT01",map);
	}
	
}
