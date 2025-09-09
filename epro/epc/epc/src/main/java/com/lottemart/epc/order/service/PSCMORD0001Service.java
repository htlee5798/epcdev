package com.lottemart.epc.order.service;

import java.util.List;


import java.util.Map;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.order.model.PSCMORD0001VO;

public interface PSCMORD0001Service {
	
	public List<DataMap> selectAllOnlineStore() throws Exception;
	
	public DataMap selectHodevMallStrYn(PSCMORD0001VO searchVO) throws Exception;

	public List<DataMap> selectCodeList(PSCMORD0001VO searchVO) throws Exception;
	
	public List<DataMap> selectOrderList(DataMap paramMap) throws Exception;
	
	public List<DataMap> selectOrderListExcel(Map<String, String> paramMap) throws Exception;

	public String selectCustInfoList(Map<String, Object> paramMap) throws Exception;
}
