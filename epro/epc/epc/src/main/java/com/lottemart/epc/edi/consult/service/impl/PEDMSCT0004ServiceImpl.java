package com.lottemart.epc.edi.consult.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.epc.edi.consult.dao.PEDMSCT0004Dao;
import com.lottemart.epc.edi.consult.service.PEDMSCT0004Service;

@Service("pedmsct0004Service")
public class PEDMSCT0004ServiceImpl implements PEDMSCT0004Service{
	
	@Autowired
	private PEDMSCT0004Dao pedmsct0004Dao;
	
	
	public List estimationMainSelect(Map<String,Object> map) throws Exception{
		// TODO Auto-generated method stub
		return pedmsct0004Dao.estimationMainSelect(map );
	}
	
	public HashMap estimationMainSelectDetailTop(String pid) throws Exception{
		// TODO Auto-generated method stub
		return pedmsct0004Dao.estimationMainSelectDetailTop(pid );
	}
	
	public List estimationMainSelectDetailBottom(String pid) throws Exception{
		// TODO Auto-generated method stub
		return pedmsct0004Dao.estimationMainSelectDetailBottom(pid );
	}
	
	public void estimationMainDelete(String pid) throws Exception{
		// TODO Auto-generated method stub
		pedmsct0004Dao.estimationMainDelete(pid );
	}
	
	public void estimationMainDetailDelete(String pid) throws Exception{
		// TODO Auto-generated method stub
		pedmsct0004Dao.estimationMainDetailDelete(pid );
	}
	
	public void estimationUpdate(Map<String,Object> map, String pid) throws Exception{
		// TODO Auto-generated method stub
		pedmsct0004Dao.estimationUpdate(map, pid );
	}
}
