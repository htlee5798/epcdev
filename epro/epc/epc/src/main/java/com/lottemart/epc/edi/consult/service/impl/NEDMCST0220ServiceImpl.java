package com.lottemart.epc.edi.consult.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.epc.edi.consult.dao.NEDMCST0220Dao;
import com.lottemart.epc.edi.consult.model.Estimation;
import com.lottemart.epc.edi.consult.model.EstimationSheet;
import com.lottemart.epc.edi.consult.service.NEDMCST0220Service;

@Service("nedmcst0220Service")
public class NEDMCST0220ServiceImpl implements NEDMCST0220Service{
	
	@Autowired
	private NEDMCST0220Dao nedmcst0220Dao;
	
	
	public List<Estimation> estimationMainSelect(Estimation map) throws Exception{
		// TODO Auto-generated method stub
		return nedmcst0220Dao.estimationMainSelect(map );
	}
	
	public Estimation estimationMainSelectDetailTop(String pid) throws Exception{
		// TODO Auto-generated method stub
		return nedmcst0220Dao.estimationMainSelectDetailTop(pid );
	}
	
	public List<EstimationSheet> estimationMainSelectDetailBottom(String pid) throws Exception{
		// TODO Auto-generated method stub
		return nedmcst0220Dao.estimationMainSelectDetailBottom(pid );
	}
	
	public void estimationMainDelete(String pid) throws Exception{
		// TODO Auto-generated method stub
		nedmcst0220Dao.estimationMainDelete(pid );
	}
	
	public void estimationMainDetailDelete(Estimation map) throws Exception{
		// TODO Auto-generated method stub
		nedmcst0220Dao.estimationMainDetailDelete(map );
	}
	
	public void estimationMainDetailDelete(String pid) throws Exception{
		// TODO Auto-generated method stub
		nedmcst0220Dao.estimationMainDetailDelete(pid );
	}
	
	public void estimationUpdate(Estimation map) throws Exception{
		// TODO Auto-generated method stub
		nedmcst0220Dao.estimationUpdate(map);
	}
	public void estimationSheetInsert(Estimation map) throws Exception{
		// TODO Auto-generated method stub
		nedmcst0220Dao.estimationSheetInsert(map );
	}
	
}
