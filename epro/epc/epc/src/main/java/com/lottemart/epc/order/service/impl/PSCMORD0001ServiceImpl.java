package com.lottemart.epc.order.service.impl;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.order.dao.PSCMORD0001Dao;
import com.lottemart.epc.order.model.PSCMORD0001VO;
import com.lottemart.epc.order.service.PSCMORD0001Service;


@Service("PSCMORD0001Service")
public class PSCMORD0001ServiceImpl  implements PSCMORD0001Service {
	@Autowired
	private PSCMORD0001Dao pscmord0001Dao;

	
	public List<DataMap> selectAllOnlineStore() throws Exception {
		return pscmord0001Dao.selectAllOnlineStore();
	}	
	
	public DataMap selectHodevMallStrYn(PSCMORD0001VO searchVO) throws Exception {
		return pscmord0001Dao.selectHodevMallStrYn(searchVO);
	}

	public List<DataMap> selectCodeList(PSCMORD0001VO searchVO) throws Exception {
		return pscmord0001Dao.selectCodeList(searchVO);
	}		
	
	public List<DataMap> selectOrderList(DataMap paramMap) throws Exception {
		return pscmord0001Dao.selectOrderList(paramMap);
	}		
	
	public List<DataMap> selectOrderListExcel(Map<String, String> paramMap) throws Exception {
		return pscmord0001Dao.selectOrderListExcel(paramMap);
	}			
	
	public String selectCustInfoList(Map<String, Object> paramMap) throws Exception {
		return pscmord0001Dao.selectCustInfoList(paramMap);
	}		
}