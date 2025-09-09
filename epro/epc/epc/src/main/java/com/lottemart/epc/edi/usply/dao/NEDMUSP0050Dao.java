package com.lottemart.epc.edi.usply.dao;

import java.util.List;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.epc.edi.usply.model.NEDMUSP0050VO;


@Repository("nedmusp0050Dao")
public class NEDMUSP0050Dao extends AbstractDAO {
	
	public List<NEDMUSP0050VO> selectUsplyReasonInfo(NEDMUSP0050VO map) throws Exception{
		return (List<NEDMUSP0050VO>)getSqlMapClientTemplate().queryForList("NEDMUSP0050.TSC_USPLY_REASON-SELECT01",map);
	}

	public List<NEDMUSP0050VO> selectUpdateUsplyReasonInfo(NEDMUSP0050VO map) throws Exception{
		return (List<NEDMUSP0050VO>)getSqlMapClientTemplate().queryForList("NEDMUSP0050.TSC_USPLY_UPDATEREASON-SELECT01",map);
	}
	
	
	public void selectUsplyReasonUpdate(NEDMUSP0050VO map) throws Exception{
		getSqlMapClientTemplate().update("NEDMUSP0050.TSC_USPLY_REASON-UPDATE01", map);
	}
	
	
	
	
}
