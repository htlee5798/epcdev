package com.lottemart.epc.edi.consult.service.impl;

import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.epc.edi.consult.dao.PEDMSCT0012Dao;
import com.lottemart.epc.edi.consult.service.PEDMSCT0012Service;


@Service("pedmsct0012Service")
public class PEDMSCT0012ServiceImpl implements PEDMSCT0012Service{

	@Autowired
	private PEDMSCT0012Dao pedmsct0012Dao;
	
	public List alertPageUpdatePageSelect(Map<String,Object> map) throws Exception{
		// TODO Auto-generated method stub
	//	return pedmsct0012Dao.alertPageInsertPageSelect(map );
	
		return pedmsct0012Dao.alertPageUpdatePageSelect(map );

	}
	
	
	public List alertPageUpdatePageSelectDetail(Map<String,Object> map) throws Exception{
		// TODO Auto-generated method stub
	//	return pedmsct0012Dao.alertPageInsertPageSelect(map );
	
		return pedmsct0012Dao.alertPageUpdatePageSelectDetail(map );

	}
	
	
	
}
