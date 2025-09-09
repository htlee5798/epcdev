package com.lottemart.epc.edi.order.dao;

import java.util.List;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.epc.edi.order.model.NEDMORD0030VO;


@Repository("nedmord0030Dao")
public class NEDMORD0030Dao extends AbstractDAO {
	
	
	/**
	 * 기간정보 - > 전표상세 조회
	 * @param NEDMORD0030VO
	 * @return
	 * @throws Exception
	 */
	public List<NEDMORD0030VO> selectJunpyoDetailInfo(NEDMORD0030VO map) throws Exception{
		return (List)getSqlMapClientTemplate().queryForList("NEDMORD0030.TSC_JUNPYO_DETAIL-SELECT01",map);
	}
}
