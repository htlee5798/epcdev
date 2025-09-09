package com.lottemart.epc.edi.order.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lcn.module.common.util.HashBox;
import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.epc.edi.order.model.NEDMORD0110VO;
import com.lottemart.epc.edi.order.model.NEDMORD0120VO;


@Repository("nedmord0120Dao")
public class NEDMORD0120Dao extends AbstractDAO {
	
	public List<NEDMORD0120VO> selectOrdSply(NEDMORD0120VO map) throws Exception{
		return (List)getSqlMapClientTemplate().queryForList("NEDMORD0120.TSC_ORD_SPLY-SELECT01",map);
	}
	public void updateOrdSply(NEDMORD0120VO map ) throws Exception{
		getSqlMapClientTemplate().update("NEDMORD0120.TSC_ORD_SPLY-UPDATE01", map);
	}
	
	
	
	
	
}





