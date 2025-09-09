package com.lottemart.epc.edi.order.dao;

import java.util.List;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.epc.edi.order.model.NEDMORD0110VO;

@Repository("nedmord0110Dao")
public class NEDMORD0110Dao extends AbstractDAO {
	
	/**
	 * 발주정보 - > 주문응답서 - > 납품가능정보 조회
	 * @param NEDMORD0110VO
	 * @return
	 * @throws Exception
	 */
	public List<NEDMORD0110VO> selectOrdAble(NEDMORD0110VO map) throws Exception{
		return (List)getSqlMapClientTemplate().queryForList("NEDMORD0110.TSC_ORD_ABLE-SELECT01",map);
	}
	
	/**
	 * 발주정보 - > 주문응답서 - > 납품가능정보 수정
	 * @param NEDMORD0110VO
	 * @return
	 * @throws Exception
	 */
	public void updateOrdSplyTime(NEDMORD0110VO map ) throws Exception{
		getSqlMapClientTemplate().update("NEDMORD0110.TSC_ORD_SPLY_TIME-UPDATE01", map);
	}
	
}
