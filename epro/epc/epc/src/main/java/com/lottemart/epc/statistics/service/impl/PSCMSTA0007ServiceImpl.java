package com.lottemart.epc.statistics.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.statistics.dao.PSCMSTA0007Dao;
import com.lottemart.epc.statistics.model.PSCMSTA0007VO;
import com.lottemart.epc.statistics.service.PSCMSTA0007Service;

@Service("PSCMSTA0007Service")
public class PSCMSTA0007ServiceImpl implements PSCMSTA0007Service {
	
	@Autowired
	private PSCMSTA0007Dao pscmsta0007Dao;

	public List<DataMap> selectToDate() throws Exception {
		return pscmsta0007Dao.selectToDate();
	}

	public List<DataMap> selectAffiliateLinkNoList(PSCMSTA0007VO searchVO) throws Exception {
		return pscmsta0007Dao.selectAffiliateLinkNoList(searchVO);
	}

	public List<DataMap> selectDaumTotalOrder(PSCMSTA0007VO searchVO) throws Exception {
		return pscmsta0007Dao.selectDaumTotalOrder(searchVO);
	}

	public List<DataMap> selectDaumOrderList(PSCMSTA0007VO searchVO) throws Exception {
		return pscmsta0007Dao.selectDaumOrderList(searchVO);
	}
	
	public List<DataMap> selectDaumOrderListExcel(PSCMSTA0007VO searchVO) throws Exception {
		return pscmsta0007Dao.selectDaumOrderListExcel(searchVO);
	}
	
	
}
