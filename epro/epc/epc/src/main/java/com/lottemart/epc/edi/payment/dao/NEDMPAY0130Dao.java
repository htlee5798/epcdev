package com.lottemart.epc.edi.payment.dao;

import java.util.List;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.epc.edi.payment.model.NEDMPAY0130VO;


@Repository("nedmpay0130Dao")
public class NEDMPAY0130Dao extends AbstractDAO {
	
	
	public List<NEDMPAY0130VO> selectCredLedStoreInfo(NEDMPAY0130VO map) throws Exception{
		return (List<NEDMPAY0130VO>)getSqlMapClientTemplate().queryForList("NEDMPAY0130.TSC_CRED_LED_STORE-SELECT01",map);
	}
	
}
