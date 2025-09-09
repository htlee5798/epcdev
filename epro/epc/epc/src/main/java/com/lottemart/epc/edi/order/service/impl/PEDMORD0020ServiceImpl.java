package com.lottemart.epc.edi.order.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.lottemart.epc.edi.order.dao.PEDMORD0020Dao;
import com.lottemart.epc.edi.order.service.PEDMORD0020Service;

@Service("pedmord0020Service")
public class PEDMORD0020ServiceImpl implements PEDMORD0020Service{
	
	@Autowired
	private PEDMORD0020Dao pedmord0020Dao;
	
	
	public List selectOrdAble(Map<String,Object> map) throws Exception{
		// TODO Auto-generated method stub
		return pedmord0020Dao.selectOrdAble(map );
	}
	
	public List selectOrdSply(Map<String,Object> map) throws Exception{
		// TODO Auto-generated method stub
		
		return pedmord0020Dao.selectOrdSply(map );
	}
	
	public void updateOrdSplyTime(String[] tmpTime,String[] tmpordno,String[] tmpprodno) throws Exception {
		
		
		HashMap<String, Object> mapTime = new HashMap<String, Object>();
    	
    		for (int i = 0; i < tmpTime.length; i++) { 
    			
    			mapTime.put("ord_slip_no", tmpordno[i]);
    			mapTime.put("sply_tm", tmpTime[i]);
    			mapTime.put("prodcd", tmpprodno[i]);
    	    	
    	    	
    	    	pedmord0020Dao.updateOrdSplyTime(mapTime);
    		} 
	}
	
	public void updateOrdSply(Map<String,Object> map) throws Exception {
		
		String[] tmpordno = map.get("forward_ordno").toString().split("-");
		String[] tmpprodno = map.get("forward_prod").toString().split("-");
		String[] tmpven = map.get("forward_ven").toString().split("-");
		String[] tmpnego = map.get("forward_nego").toString().split("-");
		String[] tmporigin = map.get("forward_origin").toString().split("-");
		
		for (int i = 0; i < tmpordno.length; i++) { 
			
			map.put("ord_slip_no", tmpordno[i]);
			map.put("prod", tmpprodno[i]);
			map.put("ven", tmpven[i]);
			map.put("nego", tmpnego[i]);
			
			if("none".equals(tmporigin[i])){
				map.put("origin", "");
			}else{
				map.put("origin", tmporigin[i]);
			}
			
			pedmord0020Dao.updateOrdSply(map);
		} 
		
    	
	}

}
