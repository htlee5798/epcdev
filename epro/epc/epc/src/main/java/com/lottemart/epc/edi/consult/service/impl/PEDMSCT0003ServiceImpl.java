package com.lottemart.epc.edi.consult.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.lottemart.epc.edi.consult.dao.PEDMSCT0003Dao;
import com.lottemart.epc.edi.consult.service.PEDMSCT0003Service;

@Service("pedmsct0003Service")
public class PEDMSCT0003ServiceImpl implements PEDMSCT0003Service{
	
	@Autowired
	private PEDMSCT0003Dao pedmsct0003Dao;
	
	
	public void estimationInsert(Map<String,Object> map, String pid) throws Exception{
		// TODO Auto-generated method stub
		pedmsct0003Dao.estimationInsert(map, pid );
	}
	
	public void estimationSheetInsert(String pid, String obj) throws Exception{
		// TODO Auto-generated method stub
		
		String[] tmp = obj.split("@");
		String tmpCompare = "";
		
		HashMap<String, Object> hMap = new HashMap<String, Object>();
    		
		int seq = 1;
		
		for (int i = 0; i < tmp.length; i++) {
			String[] tmpVal = tmp[i].split(";");
			hMap.put("pid", pid);
			hMap.put("seq", seq + i);
			hMap.put("pc", tmpVal[0]);
			hMap.put("product_id", tmpVal[1]);
			hMap.put("product_name", tmpVal[2]);
			hMap.put("standard", tmpVal[3]);
			hMap.put("pack", tmpVal[4]);
			hMap.put("rating", tmpVal[5]);
			hMap.put("estimate_price", tmpVal[6]);
			hMap.put("origin", tmpVal[7]);
			
			if("empty".equals(tmpVal[8])){
				hMap.put("remark", "");
			}else{
				hMap.put("remark", tmpVal[8]);
			}
	    	
	    	pedmsct0003Dao.estimationSheetInsert(hMap );
		} 
		
	}
	
	
	public String  estimationSelectCount(String kkk) throws Exception{
		// TODO Auto-generated method stub
		return pedmsct0003Dao.estimationSelectCount(kkk );
	}
	
	
}
