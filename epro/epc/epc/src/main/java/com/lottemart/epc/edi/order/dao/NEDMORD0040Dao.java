package com.lottemart.epc.edi.order.dao;

import java.util.List;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.epc.edi.order.model.NEDMORD0040VO;


@Repository("nedmord0040Dao")
public class NEDMORD0040Dao extends AbstractDAO {
	
	
	/**
	 * 기간정보 - > 전표별 조회
	 * @param NEDMORD0040VO
	 * @return
	 * @throws Exception
	 */
	public List<NEDMORD0040VO> selectStoreInfo(NEDMORD0040VO map) throws Exception{
		return (List)getSqlMapClientTemplate().queryForList("NEDMORD0040.TSC_STORE-SELECT01",map);
	}
}
