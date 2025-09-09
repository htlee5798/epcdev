package com.lottemart.epc.edi.usply.dao;

import java.util.List;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.epc.edi.usply.model.NEDMUSP0020VO;


@Repository("nedmusp0020Dao")
public class NEDMUSP0020Dao extends AbstractDAO {
	/**
	 * 미납정보 - > 기간정보  - > 점포별 조회
	 * @param NEDMUSP0020VO
	 * @return
	 */
	public List<NEDMUSP0020VO> selectStoreInfo(NEDMUSP0020VO map) throws Exception{
		return (List<NEDMUSP0020VO>)getSqlMapClientTemplate().queryForList("NEDMUSP0020.TSC_STORE-SELECT01",map);
	}
	
	
	
	
}
