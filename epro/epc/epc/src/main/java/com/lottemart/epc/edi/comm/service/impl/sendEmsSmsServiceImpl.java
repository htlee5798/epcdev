package com.lottemart.epc.edi.comm.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.epc.edi.comm.dao.sendEmsSmsDao;
import com.lottemart.epc.edi.comm.service.sendEmsSmsService;

@Service("sendemssmsService")
public class sendEmsSmsServiceImpl implements sendEmsSmsService{
	
	@Autowired
	private sendEmsSmsDao sendemssmsDao;
	
	public HashMap sendEMS(Map<String,Object> map) throws Exception{
		// TODO Auto-generated method stub
		return sendemssmsDao.sendEMS(map );
	}
	
	public HashMap sendSMS(Map<String,Object> map) throws Exception{
		// TODO Auto-generated method stub
		return sendemssmsDao.sendSMS(map );
	}
	
	
}



