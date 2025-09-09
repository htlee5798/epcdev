package com.lottemart.epc.edi.payment.dao;

import java.util.List;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.epc.edi.payment.model.NEDMPAY0140VO;


@Repository("nedmpay0140Dao")
public class NEDMPAY0140Dao extends AbstractDAO {
	
	
	
	public List<NEDMPAY0140VO> selectFamilyLoan(NEDMPAY0140VO map) throws Exception{
		return (List<NEDMPAY0140VO>)getSqlMapClientTemplate().queryForList("NEDMPAY0140.TSC_FAMILY_LOAN-SELECT01",map);
	}
	
}
