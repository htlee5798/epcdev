package com.lottemart.epc.edi.ems.dao;

import java.util.Map;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.epc.edi.ems.model.NEDMEMS0010VO;

@Repository("nedmems0010Dao")
public class NEDMEMS0010Dao extends AbstractDAO {

	public NEDMEMS0010VO selectEmsData(Map<String,Object> map) throws Exception {
		return (NEDMEMS0010VO) getSqlMapClientTemplate().queryForObject("NEDMEMS0010VO.TSC_EMS_DATA-SELECT01", map);
	}
	
}
