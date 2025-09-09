package com.lottemart.epc.edi.usply.dao;

import java.util.List;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.epc.edi.usply.model.NEDMUSP0010VO;


@Repository("nedmusp0010Dao")
public class NEDMUSP0010Dao extends AbstractDAO {
	/**
	 * 미납정보 - > 기간정보  - > 일자별 조회
	 * @param NEDMUSP0010VO
	 * @return
	 */
	public List<NEDMUSP0010VO> selectDayInfo(NEDMUSP0010VO map) throws Exception{
		return (List<NEDMUSP0010VO>)getSqlMapClientTemplate().queryForList("NEDMUSP0010.TSC_DAY-SELECT01",map);
	}
	
	
	
	
	
}
