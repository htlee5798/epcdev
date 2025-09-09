package com.lottemart.epc.edi.consult.dao;

import java.util.List;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.epc.edi.consult.model.Estimation;
import com.lottemart.epc.edi.consult.model.EstimationSheet;


@Repository("nedmcst0220Dao")
public class NEDMCST0220Dao extends AbstractDAO {
	
	public List<Estimation> estimationMainSelect(Estimation map) throws Exception{
		return (List<Estimation>)getSqlMapClientTemplate().queryForList("NEDMCST0220.TSC_ESTIMATION-SELECT01",map);
	}
	
	public Estimation estimationMainSelectDetailTop(String pid) throws Exception{
		return (Estimation)getSqlMapClientTemplate().queryForObject("NEDMCST0220.TSC_ESTIMATION_TOP-SELECT01",pid);
	}
	
	public List<EstimationSheet> estimationMainSelectDetailBottom(String pid) throws Exception{
		return (List<EstimationSheet>)getSqlMapClientTemplate().queryForList("NEDMCST0220.TSC_ESTIMATION_BOTTOM-SELECT01",pid);
	}
	
	public void estimationMainDelete(String pid) throws Exception{
		getSqlMapClientTemplate().delete("NEDMCST0220.TSC_ESTIMATION-DELETE01",pid);
	}
	
	public void estimationMainDetailDelete(Estimation map) throws Exception{
		getSqlMapClientTemplate().delete("NEDMCST0220.TSC_ESTIMATION_SHEET-DELETE01",map);
	}
	public void estimationMainDetailDelete(String pid) throws Exception{
		getSqlMapClientTemplate().delete("NEDMCST0220.TSC_ESTIMATION_SHEET-DELETE02",pid);
	}
	
	public void estimationUpdate(Estimation map) throws Exception{
		getSqlMapClientTemplate().update("NEDMCST0220.TSC_ESTIMATION-UPDATE01",map);
	}
	
	public void estimationSheetInsert(Estimation map) throws Exception{
		String pid = map.getPid();
		int seq = 1;
		for(EstimationSheet e : map.getEstimationSheet()){
			e.setPid(pid);
			e.setSeq(""+seq);
			seq++;
			getSqlMapClientTemplate().insert("NEDMCST0210.TSC_ESTIMATION_SHEET-INSERT01",e);	
		}
	}
	
}


