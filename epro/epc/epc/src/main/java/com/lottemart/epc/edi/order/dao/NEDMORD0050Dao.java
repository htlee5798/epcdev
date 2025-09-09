package com.lottemart.epc.edi.order.dao;

import java.util.List;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.epc.edi.order.model.NEDMORD0050VO;


@Repository("nedmord0050Dao")
public class NEDMORD0050Dao extends AbstractDAO {
	
	
	/**
	 * 기간정보 - > PDC전표상세 조회
	 * @param NEDMORD0050VO
	 * @return
	 * @throws Exception
	 */
	public List<NEDMORD0050VO> selectJunpyoDetailInfoPDC(NEDMORD0050VO map) throws Exception{
		return (List)getSqlMapClientTemplate().queryForList("NEDMORD0050.TSC_JUNPYO_DETAIL_PDC-SELECT01",map);
	}
	
}
