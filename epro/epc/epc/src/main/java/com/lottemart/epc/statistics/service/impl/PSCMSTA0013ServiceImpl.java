package com.lottemart.epc.statistics.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.statistics.dao.PSCMSTA0013Dao;
import com.lottemart.epc.statistics.model.PSCMSTA0013VO;
import com.lottemart.epc.statistics.service.PSCMSTA0013Service;

@Service("PSCMSTA0013Service")
public class PSCMSTA0013ServiceImpl implements PSCMSTA0013Service{

	@Autowired
	private PSCMSTA0013Dao pscmsta0013Dao;
	
	@Override
	public List<DataMap> selectCrossPicUpList(PSCMSTA0013VO searchVO) throws Exception {
		return pscmsta0013Dao.selectCrossPicUpList(searchVO);
	}

	@Override
	public List<DataMap> selectCrossPicUpListExcel(PSCMSTA0013VO searchVO) throws Exception {
		return pscmsta0013Dao.selectCrossPicUpListExcel(searchVO);
	}

	@Override
	public List<DataMap> selectSuperStrList() throws Exception {
		return pscmsta0013Dao.selectSuperStrList();
	}
	
	@Override
	public List<DataMap> selectMartStrList() throws Exception {
		return pscmsta0013Dao.selectMartStrList();
	}

	@Override
	public List<DataMap> selectPickupStsList() throws Exception {
		return pscmsta0013Dao.selectPickupStsList();
	}
	
	@Override
	public List<DataMap> selectDeliStatusList() throws Exception {
		return pscmsta0013Dao.selectDeliStatusList();
	}
	
	@Override
	public void insertPickupStatus(Map<String, String> paramMap) throws Exception {
		 pscmsta0013Dao.insertPickupStatus(paramMap);
	}
	

}
