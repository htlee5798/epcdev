package com.lottemart.epc.edi.payment.dao;

import java.util.List;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.epc.edi.order.model.NEDMORD0020VO;
import com.lottemart.epc.edi.payment.model.NEDMPAY0020VO;


@Repository("nedmpay0020Dao")
public class NEDMPAY0020Dao extends AbstractDAO {
	
	
	public List<NEDMPAY0020VO> selectPaymentDayInfo(NEDMPAY0020VO map) throws Exception{
		return (List<NEDMPAY0020VO>)getSqlMapClientTemplate().queryForList("NEDMPAY0020.TSC_PAYMENT_DAY-SELECT01",map);
	}
	
}


