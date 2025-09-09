package com.lottemart.epc.edi.usply.dao;

import java.util.List;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.epc.edi.usply.model.NEDMUSP0060VO;


@Repository("nedmusp0060Dao")
public class NEDMUSP0060Dao extends AbstractDAO {
	
	public List<NEDMUSP0060VO> selectPenaltyInfo(NEDMUSP0060VO map) throws Exception{
		return (List<NEDMUSP0060VO>)getSqlMapClientTemplate().queryForList("NEDMUSP0060.TSC_PENALTY-SELECT01",map);
	}
	
}
