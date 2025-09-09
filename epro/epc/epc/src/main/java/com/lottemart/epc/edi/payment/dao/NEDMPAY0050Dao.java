package com.lottemart.epc.edi.payment.dao;

import java.util.List;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.epc.edi.payment.model.NEDMPAY0050VO;


@Repository("nedmpay0050Dao")
public class NEDMPAY0050Dao extends AbstractDAO {
	
	
	public List<NEDMPAY0050VO> selectLogiAmtInfo(NEDMPAY0050VO map) throws Exception{
		return (List<NEDMPAY0050VO>)getSqlMapClientTemplate().queryForList("NEDMPAY0050.TSC_LOGI_AMT-SELECT01",map);
	}
	
}


