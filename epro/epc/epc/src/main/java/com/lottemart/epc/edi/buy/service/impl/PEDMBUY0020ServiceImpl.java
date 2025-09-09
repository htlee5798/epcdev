package com.lottemart.epc.edi.buy.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lottemart.epc.edi.buy.dao.PEDMBUY0020Dao;
import com.lottemart.epc.edi.buy.service.PEDMBUY0020Service;

@Service("pedmbuy0020Service")
public class PEDMBUY0020ServiceImpl implements PEDMBUY0020Service{
	
	@Autowired
	private PEDMBUY0020Dao pedmbuy0020Dao;
	
	public List selectRejectInfo(Map<String,Object> map) throws Exception{
		// TODO Auto-generated method stub
		return pedmbuy0020Dao.selectRejectInfo(map );
	}
	
	public HashMap selectRejectPopupInfo(Map<String,Object> map) throws Exception{
		// TODO Auto-generated method stub
		return pedmbuy0020Dao.selectRejectPopupInfo(map );
	}
	
	public void updateRejectPopup(Map<String,Object> map) throws Exception{
		pedmbuy0020Dao.updateRejectPopup(map );
	}
	
}
