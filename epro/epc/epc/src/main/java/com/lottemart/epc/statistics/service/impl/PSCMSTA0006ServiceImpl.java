package com.lottemart.epc.statistics.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.statistics.dao.PSCMSTA0006Dao;
import com.lottemart.epc.statistics.model.PSCMSTA0006VO;
import com.lottemart.epc.statistics.service.PSCMSTA0006Service;

@Service("PSCMSTA0006Service")
public class PSCMSTA0006ServiceImpl implements PSCMSTA0006Service {
	
	@Autowired
	private PSCMSTA0006Dao pscmsta0006Dao;

	public List<DataMap> selectToDate() throws Exception {
		return pscmsta0006Dao.selectToDate();
	}

	public List<DataMap> selectAffiliateLinkNoList(PSCMSTA0006VO searchVO) throws Exception {
		return pscmsta0006Dao.selectAffiliateLinkNoList(searchVO);
	}

	public List<DataMap> selectNaverEdmSummaryTotal(PSCMSTA0006VO searchVO) throws Exception {
		return pscmsta0006Dao.selectNaverEdmSummaryTotal(searchVO);
	}

	public List<DataMap> selectNaverEdmSummaryList(PSCMSTA0006VO searchVO) throws Exception {
		return pscmsta0006Dao.selectNaverEdmSummaryList(searchVO);
	}
	
	public List<DataMap> selectNaverEdmSummaryListExcel(PSCMSTA0006VO searchVO) throws Exception {
		return pscmsta0006Dao.selectNaverEdmSummaryListExcel(searchVO);
	}
}
