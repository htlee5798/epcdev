package com.lottemart.epc.edi.consult.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import lcn.module.common.util.HashBox;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.lottemart.epc.edi.consult.dao.PEDMSCT0009Dao;
import com.lottemart.epc.edi.consult.service.PEDMSCT0009Service;

@Service("pedmsct0009Service")
public class PEDMSCT0009ServiceImpl implements PEDMSCT0009Service{
	
	@Autowired
	private PEDMSCT0009Dao pedmsct0009Dao;
	
	
	public List orderStopSelect(Map<String,Object> map) throws Exception{
		// TODO Auto-generated method stub
		return pedmsct0009Dao.orderStopSelect(map );
	}
	
	public void orderStopInsert(String val) throws Exception{
		// TODO Auto-generated method stub
		
		String[] tmp = val.split("@");
		
		HashMap<String, Object> hMap = new HashMap<String, Object>();
			
		for (int i = 0; i < tmp.length; i++) {
			
			String[] tmpVal = tmp[i].split(";");
			String tmpData = tmpVal[3].substring(0,7);
			String tmpDate = tmpVal[1].substring(0,6);
			
			hMap.put("regdy", tmpDate);
			hMap.put("prod_pre", tmpData);
			hMap.put("str", tmpVal[0]);
			hMap.put("reg_date", tmpVal[1]);
			hMap.put("ven", tmpVal[2]);
			hMap.put("prod", tmpVal[3]);
			
			pedmsct0009Dao.orderStopInsert(hMap);
	    	
		} 
		
	}
	
}
