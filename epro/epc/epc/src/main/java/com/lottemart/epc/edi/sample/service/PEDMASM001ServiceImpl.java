package com.lottemart.epc.edi.sample.service;


import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.lottemart.epc.edi.sample.dao.PEDMASM001Dao;

@Service("pedmsam001Service")
public class PEDMASM001ServiceImpl implements PEDMSAM001Service{
	
	@Autowired
	private PEDMASM001Dao pedmasm001Dao;
	
	public List selectOrderInfo(Map<String,Object> map) throws Exception{
		// TODO Auto-generated method stub
		return pedmasm001Dao.selectOrderInfo(map );
	}

}
