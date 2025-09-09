package com.lottemart.epc.edi.payment.dao;

import java.util.List;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.epc.edi.payment.model.NEDMPAY0010VO;


@Repository("nedmpay0010Dao")
public class NEDMPAY0010Dao extends AbstractDAO {
	
	
	public List<NEDMPAY0010VO> selectCominforInfo(NEDMPAY0010VO map) throws Exception{
		return (List)getSqlMapClientTemplate().queryForList("NEDMPAY0010.TSC_COMINFOR-SELECT01",map);
	}
	
}


