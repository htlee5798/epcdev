package com.lottemart.epc.statistics.service;

import java.util.List;
import java.util.Map;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.statistics.model.PSCMSTA0014VO;

public interface PSCMSTA0014Service {

	public List<DataMap> selectRentCarPickUp(PSCMSTA0014VO searchVO) throws Exception;

	public List<DataMap> selectGasStationPickUp(PSCMSTA0014VO searchVO) throws Exception;

	public int selectRentCarPickUpTotal(PSCMSTA0014VO searchVO) throws Exception;
	
	public List<DataMap> selectRentCarPickUpListExcel(PSCMSTA0014VO searchVO) throws Exception;

	public List<DataMap> selectSuperStrList(String extStrCd) throws Exception;

	public List<DataMap> selectMartStrList() throws Exception;

	public List<DataMap> selectPickupStsList() throws Exception;
	
	public List<DataMap> selectDeliStatusList() throws Exception;

	public List<DataMap> selectOrdDivnList() throws Exception;

	public List<DataMap> pickUpListExcel(DataMap parmaMap) throws Exception;

	public List<DataMap> pickUpListExcelByGas(DataMap parmaMap) throws Exception;

	public List<DataMap> selectPickUpTime() throws Exception;

	public void insertPickupStatus(Map<String, String> paramMap) throws Exception;
}
