package com.lottemart.epc.edi.ems.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.epc.edi.ems.dao.PEDMEMS0000Dao;
import com.lottemart.epc.edi.ems.service.PEDMEMS0000Service;

@Service("pedmems0000Service")
public class PEDMEMS0000ServiceImpl implements PEDMEMS0000Service{
	
	@Autowired
	private PEDMEMS0000Dao pedmems0000Dao;
	
	
	public HashMap selectEmsData(Map<String,Object> map) throws Exception{
		// TODO Auto-generated method stub
		return pedmems0000Dao.selectEmsData(map );
	}
	

}
