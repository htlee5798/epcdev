package com.lottemart.epc.edi.payment.dao;

import java.util.List;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.epc.edi.payment.model.NEDMPAY0110VO;


@Repository("nedmpay0110Dao")
public class NEDMPAY0110Dao extends AbstractDAO {
	
	
	public List<NEDMPAY0110VO> selectCredLedInfo(NEDMPAY0110VO map) throws Exception{
		return (List<NEDMPAY0110VO>)getSqlMapClientTemplate().queryForList("NEDMPAY0110.TSC_CRED_LED-SELECT01",map);
	}
	
	
}
