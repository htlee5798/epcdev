package com.lottemart.epc.statistics.service;

import java.util.List;
import java.util.Map;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.statistics.model.PSCMSTA0013VO;

public interface PSCMSTA0013Service {

	public List<DataMap> selectCrossPicUpList(PSCMSTA0013VO searchVO) throws Exception;
	
	public List<DataMap> selectCrossPicUpListExcel(PSCMSTA0013VO searchVO) throws Exception;

	public List<DataMap> selectSuperStrList() throws Exception;

	public List<DataMap> selectMartStrList() throws Exception;

	public List<DataMap> selectPickupStsList() throws Exception;
	
	public List<DataMap> selectDeliStatusList() throws Exception;

	public void insertPickupStatus(Map<String, String> paramMap) throws Exception;
}
