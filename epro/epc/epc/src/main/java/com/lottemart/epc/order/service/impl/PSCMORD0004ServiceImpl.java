package com.lottemart.epc.order.service.impl;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.order.dao.PSCMORD0004Dao;
import com.lottemart.epc.order.model.PSCMORD0004VO;
import com.lottemart.epc.order.service.PSCMORD0004Service;


@Service("PSCMORD0004Service")
public class PSCMORD0004ServiceImpl  implements PSCMORD0004Service {
	@Autowired
	private PSCMORD0004Dao pscmord0004Dao;

	
	public List<DataMap> selectAllOnlineStore() throws Exception {
		return pscmord0004Dao.selectAllOnlineStore();
	}	
	
	public DataMap selectHodevMallStrYn(PSCMORD0004VO searchVO) throws Exception {
		return pscmord0004Dao.selectHodevMallStrYn(searchVO);
	}

	public List<DataMap> selectCodeList(PSCMORD0004VO searchVO) throws Exception {
		return pscmord0004Dao.selectCodeList(searchVO);
	}		
	
	public List<DataMap> selectOrderItemList(DataMap paramMap) throws Exception {
		return pscmord0004Dao.selectOrderItemList(paramMap);
	}		
	
	public List<DataMap> selectOrderItemListExcel(Map<String, String> paramMap) throws Exception {
		return pscmord0004Dao.selectOrderItemListExcel(paramMap);
	}	
	
	public String selectCustInfoByGoods(Map<String, Object> paramMap) throws Exception {
		return pscmord0004Dao.selectCustInfoByGoods(paramMap);
	}		
	
}