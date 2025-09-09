package com.lottemart.epc.edi.consult.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.epc.edi.consult.dao.PEDMSCT0005Dao;
import com.lottemart.epc.edi.consult.service.PEDMSCT0005Service;

@Service("pedmsct0005Service")
public class PEDMSCT0005ServiceImpl implements PEDMSCT0005Service{
	
	@Autowired
	private PEDMSCT0005Dao pedmsct0005Dao;
	
	public List selectVenCd(Map<String,Object> map) throws Exception{
		// TODO Auto-generated method stub
		return pedmsct0005Dao.selectVenCd(map );
	}
	
	public List alertPageInsertPageSelect(Map<String,Object> map) throws Exception{
		// TODO Auto-generated method stub
		return pedmsct0005Dao.alertPageInsertPageSelect(map );
	}
	
	public Integer ajaxEmailCk(Map<String,Object> map) throws Exception{
		// TODO Auto-generated method stub
		return pedmsct0005Dao.ajaxEmailCk(map );
	}
	
	public Integer ajaxEmailCkUP(Map<String,Object> map) throws Exception{
		// TODO Auto-generated method stub
		return pedmsct0005Dao.ajaxEmailCkUP(map );
	}
	
	public Integer ajaxCellCk(Map<String,Object> map) throws Exception{
		// TODO Auto-generated method stub
		return pedmsct0005Dao.ajaxCellCk(map );
	}
	
	public Integer ajaxCellCkUP(Map<String,Object> map) throws Exception{
		// TODO Auto-generated method stub
		return pedmsct0005Dao.ajaxCellCkUP(map );
	}
	
	public List ajaxVendor(Map<String,Object> map) throws Exception{
		// TODO Auto-generated method stub
		return pedmsct0005Dao.ajaxVendor(map );
	}
	
	public List ajaxVendorCK(Map<String,Object> map) throws Exception{
		// TODO Auto-generated method stub
		return pedmsct0005Dao.ajaxVendorCK(map );
	}
	
	public String alertPageInsert(Map<String,Object> map) throws Exception{
		String[] ven_split=map.get("vens").toString().split(";");
		String seq="";
		seq = pedmsct0005Dao.alertPageInsertIDSeq(map);
		map.put("seq", seq);
		
		pedmsct0005Dao.alertPageInsertID(map );
		
		for(int j=0;j<ven_split.length;j++){
			map.put("ven", ven_split[j]);
			pedmsct0005Dao.alertPageInsertUser(map);
		}
		
		return seq;
	}
	
	public HashMap alertPageUpdatePage(Map<String,Object> map) throws Exception{
		// TODO Auto-generated method stub
		return pedmsct0005Dao.alertPageUpdatePage(map );
	}
	
	public void alertPageUpdate(Map<String,Object> map) throws Exception{
		
		String[] ven_split=map.get("vens").toString().split(";");
		
		pedmsct0005Dao.alertPageUpdateID(map );
		pedmsct0005Dao.alertPageDeleteUser(map);
		
		for(int j=0;j<ven_split.length;j++){
			map.put("ven", ven_split[j]);
			pedmsct0005Dao.alertPageInsertUser(map);
		}
		
	}
	
	public void alertPageDelete(Map<String,Object> map) throws Exception{
		// TODO Auto-generated method stub
		pedmsct0005Dao.alertPageDeleteID(map );
		pedmsct0005Dao.alertPageDeleteUser(map );
	}
	
}



