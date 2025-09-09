package com.lottemart.epc.edi.buy.service.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import lcn.module.common.util.HashBox;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.epc.edi.buy.dao.PEDMBUY0000Dao;
import com.lottemart.epc.edi.buy.service.PEDMBUY0000Service;

@Service("pedmbuy0000Service")
public class PEDMBUY0000ServiceImpl implements PEDMBUY0000Service{
	
	@Autowired
	private PEDMBUY0000Dao pedmbuy0000Dao;
	
	public List selectBuyInfo(Map<String,Object> map) throws Exception{
		// TODO Auto-generated method stub
		return pedmbuy0000Dao.selectBuyInfo(map );
	}
	
	public List selectStoreInfo(Map<String,Object> map) throws Exception{
		// TODO Auto-generated method stub
		return pedmbuy0000Dao.selectStoreInfo(map );
	}
	
	public List selectProductInfo(Map<String,Object> map) throws Exception{
		// TODO Auto-generated method stub
		return pedmbuy0000Dao.selectProductInfo(map );
	}
	
	public List selectJunpyoInfo(Map<String,Object> map) throws Exception{
		// TODO Auto-generated method stub
		return pedmbuy0000Dao.selectJunpyoInfo(map );
	}
	
	public List selectJunpyoDetailInfo(Map<String,Object> map) throws Exception{
		// TODO Auto-generated method stub
		return pedmbuy0000Dao.selectJunpyoDetailInfo(map );
	}
	
	public List selectStoreProductInfo(Map<String,Object> map) throws Exception{
		// TODO Auto-generated method stub
		return pedmbuy0000Dao.selectStoreProductInfo(map );
	}
	
	public List selectStorePurchaseInfo(Map<String,Object> map) throws Exception{
		// TODO Auto-generated method stub
		return pedmbuy0000Dao.selectStorePurchaseInfo(map );
	}
	
	public List selectGiftInfo(Map<String,Object> map) throws Exception{
		// TODO Auto-generated method stub
		return pedmbuy0000Dao.selectGiftInfo(map );
	}
		
	
}
