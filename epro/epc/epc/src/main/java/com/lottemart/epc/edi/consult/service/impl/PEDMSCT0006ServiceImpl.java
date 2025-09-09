package com.lottemart.epc.edi.consult.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import lcn.module.common.util.HashBox;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.epc.edi.consult.dao.PEDMSCT0006Dao;
import com.lottemart.epc.edi.consult.service.PEDMSCT0006Service;

@Service("pedmsct0006Service")
public class PEDMSCT0006ServiceImpl implements PEDMSCT0006Service{
	
	@Autowired
	private PEDMSCT0006Dao pedmsct0006Dao;
	
	public HashBox selectConsultVendor(Map<String,Object> map) throws Exception{
		// TODO Auto-generated method stub
		return pedmsct0006Dao.selectConsultVendor(map );
	}
	
	public void updateConsultVendorPass(Map<String,Object> map) throws Exception{
		// TODO Auto-generated method stub
		 pedmsct0006Dao.updateConsultVendorPass(map);
	}
	
	public void updateConsultVendorConsult(Map<String,Object> map) throws Exception{
		// TODO Auto-generated method stub
		 pedmsct0006Dao.updateConsultVendorConsult(map);
	}
	
	
	
	
	
}
