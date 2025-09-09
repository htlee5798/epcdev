package com.lottemart.epc.edi.usply.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import lcn.module.common.util.HashBox;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.lottemart.epc.edi.usply.dao.PEDMUSP0000Dao;
import com.lottemart.epc.edi.usply.service.PEDMUSP0000Service;

@Service("pedmusp0000Service")
public class PEDMUSP0000ServiceImpl implements PEDMUSP0000Service{
	@Autowired
	private PEDMUSP0000Dao PEDMUSP0000Dao;
	
	public List selectDayInfo(Map<String,Object> map) throws Exception{
		// TODO Auto-generated method stub
		return PEDMUSP0000Dao.selectDayInfo(map );
	}
	
	public List selectStoreInfo(Map<String,Object> map) throws Exception{
		// TODO Auto-generated method stub
		return PEDMUSP0000Dao.selectStoreInfo(map );
	}
	
	public List selectProductInfo(Map<String,Object> map) throws Exception{
		// TODO Auto-generated method stub
		return PEDMUSP0000Dao.selectProductInfo(map );
	}
	
	public List selectProductDetailInfo(Map<String,Object> map) throws Exception{
		// TODO Auto-generated method stub
		return PEDMUSP0000Dao.selectProductDetailInfo(map );
	}
	
	public List selectUsplyReasonInfo(Map<String,Object> map) throws Exception{
		// TODO Auto-generated method stub
		return PEDMUSP0000Dao.selectUsplyReasonInfo(map );
	}
	
	public List selectPenaltyInfo(Map<String,Object> map) throws Exception{
		// TODO Auto-generated method stub
		return PEDMUSP0000Dao.selectPenaltyInfo(map );
	}
	
	public void selectUsplyReasonUpdate(String[] tmpusp1, String[] tmpusp2, String[] tmpprod, String[] tmpordno) throws Exception{
		// TODO Auto-generated method stub
		
		HashMap<String, Object> hMap = new HashMap<String, Object>();
		
		for (int i = 0; i < tmpusp1.length; i++) { 
			
			hMap.put("usp1UD", tmpusp1[i]);
			hMap.put("usp2UD", tmpusp2[i]);
			hMap.put("prodUD", tmpprod[i]);
			hMap.put("ordnoUD", tmpordno[i]);
			
			if(!"3".equals(tmpusp1[i])){
				
				PEDMUSP0000Dao.selectUsplyReasonUpdate(hMap);
			}
	    	
		} 
    	
	}
	
	
	
}
