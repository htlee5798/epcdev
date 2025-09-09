package com.lottemart.epc.edi.payment.dao;

import java.util.List;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.epc.edi.payment.model.NEDMPAY0060VO;
import com.lottemart.epc.edi.payment.model.NEDMPAY0061VO;


@Repository("nedmpay0060Dao")
public class NEDMPAY0060Dao extends AbstractDAO {
	
	
	public List<NEDMPAY0061VO> selectPromoNewSaleInfo(NEDMPAY0061VO map) throws Exception{
		return (List<NEDMPAY0061VO>)getSqlMapClientTemplate().queryForList("NEDMPAY0060.TSC_PROMO_SALE-SELECT02",map);
	}
	public List<NEDMPAY0060VO> selectPromoSaleInfo(NEDMPAY0060VO map) throws Exception{
		return (List<NEDMPAY0060VO>)getSqlMapClientTemplate().queryForList("NEDMPAY0060.TSC_PROMO_SALE-SELECT01",map);
	}
	
}


