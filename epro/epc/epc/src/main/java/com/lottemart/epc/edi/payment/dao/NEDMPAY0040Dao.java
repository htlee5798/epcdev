package com.lottemart.epc.edi.payment.dao;

import java.util.List;
import java.util.Map;

import lcn.module.common.util.HashBox;
import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.epc.edi.payment.model.NEDMPAY0040VO;


@Repository("nedmpay0040Dao")
public class NEDMPAY0040Dao extends AbstractDAO {
	
	
	public List<NEDMPAY0040VO> selectCredAggInfo(NEDMPAY0040VO map) throws Exception{
		return (List<NEDMPAY0040VO>)getSqlMapClientTemplate().queryForList("NEDMPAY0040.TSC_CRED_AGG-SELECT01",map);
	}
	
}


