package com.lottemart.epc.edi.comm.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.epc.edi.comm.dao.PEDPCOM0002Dao;
import com.lottemart.epc.edi.comm.service.PEDPCOM0002Service;

@Service("PEDPCOM0002Service")
public class PEDPCOM0002ServiceImpl implements PEDPCOM0002Service{
	
	@Autowired
	private PEDPCOM0002Dao pedpcom0002Dao;
	
	public List selectProduct(Map<String, Object> map) throws Exception{
		return pedpcom0002Dao.selectProduct(map );
	}
	
}
