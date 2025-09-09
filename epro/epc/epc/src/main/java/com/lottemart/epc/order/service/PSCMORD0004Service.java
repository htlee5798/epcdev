package com.lottemart.epc.order.service;

import java.util.List;


import java.util.Map;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.order.model.PSCMORD0004VO;

public interface PSCMORD0004Service {
	
	public List<DataMap> selectAllOnlineStore() throws Exception;
	
	public DataMap selectHodevMallStrYn(PSCMORD0004VO searchVO) throws Exception;

	public List<DataMap> selectCodeList(PSCMORD0004VO searchVO) throws Exception;
	
	public List<DataMap> selectOrderItemList(DataMap paramMap) throws Exception;
	
	public List<DataMap> selectOrderItemListExcel(Map<String, String> paramMap) throws Exception;

	public String selectCustInfoByGoods(Map<String, Object> paramMap) throws Exception;

}