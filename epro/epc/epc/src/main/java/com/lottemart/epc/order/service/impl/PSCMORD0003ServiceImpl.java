package com.lottemart.epc.order.service.impl;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.order.dao.PSCMORD0003Dao;
import com.lottemart.epc.order.model.PSCMORD0003VO;
import com.lottemart.epc.order.service.PSCMORD0003Service;


@Service("PSCMORD0003Service")
public class PSCMORD0003ServiceImpl  implements PSCMORD0003Service {
	@Autowired
	private PSCMORD0003Dao pscmord0003Dao;

	
	public List<DataMap> selectAllOnlineStore() throws Exception {
		return pscmord0003Dao.selectAllOnlineStore();
	}	
	
	public DataMap selectHodevMallStrYn(PSCMORD0003VO searchVO) throws Exception {
		return pscmord0003Dao.selectHodevMallStrYn(searchVO);
	}

	public List<DataMap> selectCodeList(PSCMORD0003VO searchVO) throws Exception {
		return pscmord0003Dao.selectCodeList(searchVO);
	}		
	
	public List<DataMap> selectOrderList(DataMap paramMap) throws Exception {
		return pscmord0003Dao.selectOrderList(paramMap);
	}		
	
	public List<DataMap> selectOrderListExcel(Map<String, String> paramMap) throws Exception {
		return pscmord0003Dao.selectOrderListExcel(paramMap);
	}			
	
	public String selectCustInfo(Map<String, Object> paramMap) throws Exception {
		return pscmord0003Dao.selectCustInfo(paramMap);
	}		
	
}