package com.lottemart.epc.edi.consult.service.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import lcn.module.common.util.HashBox;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.epc.edi.comm.model.Constants;
import com.lottemart.epc.edi.comm.model.EdiCommonCode;
import com.lottemart.epc.edi.comm.model.SearchParam;
import com.lottemart.epc.edi.consult.dao.PEDMSCT0002Dao;
import com.lottemart.epc.edi.consult.service.PEDMSCT0002Service;

@Service("pedmsct0002Service")
public class PEDMSCT0002ServiceImpl implements PEDMSCT0002Service{
	
	@Autowired
	private PEDMSCT0002Dao pedmsct0002Dao;
	
	public List consultAdminSelect(Map<String,Object> map) throws Exception{
		// TODO Auto-generated method stub
		return pedmsct0002Dao.consultAdminSelect(map );
	}
	
	public void papeUpdate1(Map<String,Object> map) throws Exception{
		// TODO Auto-generated method stub
		pedmsct0002Dao.papeUpdate1(map );
	}
	
	public void papeUpdate2(Map<String,Object> map) throws Exception{
		// TODO Auto-generated method stub
		pedmsct0002Dao.papeUpdate2(map );
	}
	
	public void cnslUpdate1(Map<String,Object> map) throws Exception{
		// TODO Auto-generated method stub
		pedmsct0002Dao.cnslUpdate1(map );
	}
	
	public void cnslUpdate2(Map<String,Object> map) throws Exception{
		// TODO Auto-generated method stub
		pedmsct0002Dao.cnslUpdate2(map );
	}
	
	public void entshpUpdate1(Map<String,Object> map) throws Exception{
		// TODO Auto-generated method stub
		pedmsct0002Dao.entshpUpdate1(map );
	}
	
	public void entshpUpdate2(Map<String,Object> map) throws Exception{
		// TODO Auto-generated method stub
		pedmsct0002Dao.entshpUpdate2(map );
	}
	
	public List consultAdminSelectDetail(String str) throws Exception{
		// TODO Auto-generated method stub
		return pedmsct0002Dao.consultAdminSelectDetail(str );
	} 
	
	public List consultAdminSelectDetailPast(String str) throws Exception{
		// TODO Auto-generated method stub
		return pedmsct0002Dao.consultAdminSelectDetailPast(str );
	} 
	
	public void historyInsert(Map<String,Object> map) throws Exception{
		// TODO Auto-generated method stub
		pedmsct0002Dao.historyInsert(map );
	}
	
	public List<EdiCommonCode> selectL1List(SearchParam searchParam) {
		// TODO Auto-generated method stub
		
		return pedmsct0002Dao.selectL1List(searchParam);
	}
	public List<EdiCommonCode> selectL1ListApply(SearchParam searchParam) {
		// TODO Auto-generated method stub
		
		return pedmsct0002Dao.selectL1ListApply(searchParam);
	}
	
	
	public List<EdiCommonCode> selectDistinctTeamList() {
		// TODO Auto-generated method stub
		SearchParam searchParam = new SearchParam();
		searchParam.setDetailCode(Constants.DEFAULT_DETAIL_CD_CON);
		return pedmsct0002Dao.selectDistinctTeamList(searchParam);
	}
	
	
	
}
