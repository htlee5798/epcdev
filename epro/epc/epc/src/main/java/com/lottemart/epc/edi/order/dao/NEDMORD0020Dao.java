package com.lottemart.epc.edi.order.dao;

import java.util.List;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.epc.edi.order.model.NEDMORD0020VO;


@Repository("nedmord0020Dao")
public class NEDMORD0020Dao extends AbstractDAO {
	
	/**
	 * 기간정보 - > 전표별 조회
	 * @param NEDMORD0020VO
	 * @return
	 * @throws Exception
	 */
	public List<NEDMORD0020VO> selectJunpyoInfo(NEDMORD0020VO map) throws Exception{
		return (List)getSqlMapClientTemplate().queryForList("NEDMORD0020.TSC_ORD-SELECT01",map);
	}
	
}
