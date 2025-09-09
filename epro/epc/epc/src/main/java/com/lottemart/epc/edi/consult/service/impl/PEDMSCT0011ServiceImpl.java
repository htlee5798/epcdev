package com.lottemart.epc.edi.consult.service.impl;

import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.epc.edi.consult.dao.PEDMSCT0011Dao;
import com.lottemart.epc.edi.consult.service.PEDMSCT0011Service;

@Service("pedmsct0011Service")
public class PEDMSCT0011ServiceImpl implements PEDMSCT0011Service{
	
	@Autowired
	private PEDMSCT0011Dao pedmsct0011Dao;
	
	
	public List orderStopResultSelect(Map<String,Object> map) throws Exception{
		// TODO Auto-generated method stub
		return pedmsct0011Dao.orderStopResultSelect(map );
	}
	
	
}
