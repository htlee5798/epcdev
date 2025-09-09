package com.lottemart.epc.edi.inventory.service.impl;

import java.util.HashMap;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.epc.edi.inventory.dao.PEDMINV0020Dao;
import com.lottemart.epc.edi.inventory.model.PEDMINV0020VO;
import com.lottemart.epc.edi.inventory.service.PEDMINV0020Service;

@Service("pedminv0020Service")
public class PEDMINV0020ServiceImpl implements PEDMINV0020Service{
	
	@Autowired
	private PEDMINV0020Dao pedminv0020Dao;
	
	public List<PEDMINV0020VO> selectBadProdInfo(Map<String,Object> map) throws Exception{
		// TODO Auto-generated method stub
		return pedminv0020Dao.selectBadProdInfo(map );
	}
	
	public PEDMINV0020VO selectBadProdPopupInfo(Map<String,Object> map) throws Exception{
		// TODO Auto-generated method stub
		return pedminv0020Dao.selectBadProdPopupInfo(map );
	}
	
	public void selectBadProdPopupUpdate(Map<String,Object> map) throws Exception{
		pedminv0020Dao.selectBadProdPopupUpdate(map );
	}

}
