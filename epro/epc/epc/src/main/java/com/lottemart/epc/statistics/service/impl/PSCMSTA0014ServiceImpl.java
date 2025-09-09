package com.lottemart.epc.statistics.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.statistics.dao.PSCMSTA0014Dao;
import com.lottemart.epc.statistics.model.PSCMSTA0014VO;
import com.lottemart.epc.statistics.service.PSCMSTA0014Service;

@Service("PSCMSTA0014Service")
public class PSCMSTA0014ServiceImpl implements PSCMSTA0014Service{

	@Autowired
	private PSCMSTA0014Dao pscmsta0014Dao;
	
	@Override
	public List<DataMap> selectRentCarPickUp(PSCMSTA0014VO searchVO) throws Exception {
		return pscmsta0014Dao.selectRentCarPickUp(searchVO);
	}

	@Override
	public List<DataMap> selectGasStationPickUp(PSCMSTA0014VO searchVO) throws Exception {
		return pscmsta0014Dao.selectgasStationPickUp(searchVO);
	}

	@Override
	public int selectRentCarPickUpTotal(PSCMSTA0014VO searchVO) throws Exception {
		return pscmsta0014Dao.selectRentCarPickUpTotal(searchVO);
	}

	@Override
	public List<DataMap> selectRentCarPickUpListExcel(PSCMSTA0014VO searchVO) throws Exception {
		return pscmsta0014Dao.selectRentCarPickUpListExcel(searchVO);
	}

	@Override
	public List<DataMap> selectSuperStrList(String extStrCd) throws Exception {
		return pscmsta0014Dao.selectSuperStrList(extStrCd);
	}
	
	@Override
	public List<DataMap> selectMartStrList() throws Exception {
		return pscmsta0014Dao.selectMartStrList();
	}

	@Override
	public List<DataMap> selectPickupStsList() throws Exception {
		return pscmsta0014Dao.selectPickupStsList();
	}
	
	@Override
	public List<DataMap> selectDeliStatusList() throws Exception {
		return pscmsta0014Dao.selectDeliStatusList();
	}

	@Override
	public List<DataMap> selectOrdDivnList() throws Exception {
		return pscmsta0014Dao.selectOrdDivnList();
	}

	@Override
	public List<DataMap> pickUpListExcel(DataMap paramMap) throws Exception {
		return pscmsta0014Dao.pickUpListExcel(paramMap);
	}
	
	@Override
	public List<DataMap> pickUpListExcelByGas(DataMap paramMap) throws Exception {
		return pscmsta0014Dao.pickUpListExcelByGas(paramMap);
	}

	@Override
	public void insertPickupStatus(Map<String, String> paramMap) throws Exception {
		 pscmsta0014Dao.insertPickupStatus(paramMap);
	}
	
	@Override
	public List<DataMap> selectPickUpTime() throws Exception {
		return pscmsta0014Dao.selectPickUpTime();
	}

}
