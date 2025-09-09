package com.lottemart.epc.edi.consult.dao;

import java.util.List;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.lottemart.epc.edi.comm.model.SearchParam;
import com.lottemart.epc.edi.consult.model.NEDMCST0230VO;


@Repository("nedmcst0230Dao")
public class NEDMCST0230Dao extends AbstractDAO {
	
	public List<NEDMCST0230VO> asMainSelect(NEDMCST0230VO nEDMCST0230VO) throws Exception{
		return (List<NEDMCST0230VO>)getSqlMapClientTemplate().queryForList("NEDMCST0230.TSC_AS-SELECT",nEDMCST0230VO);
	}
	
	public int asMainSelectCount(NEDMCST0230VO nEDMCST0230VO) throws Exception{
		return (Integer)getSqlMapClientTemplate().queryForObject("NEDMCST0230.TSC_AS-SELECT-COUNT",nEDMCST0230VO);
	}
	
	public int asMainUpdate(NEDMCST0230VO nEDMCST0230VO) throws Exception{
		return getSqlMapClientTemplate().update("NEDMCST0230.TSC_AS-UPDATE",nEDMCST0230VO);
	}
	
}


