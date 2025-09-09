package com.lottemart.epc.delivery.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.delivery.dao.PSCMDLV0011Dao;
import com.lottemart.epc.delivery.service.PSCMDLV0011Service;

@Service
public class PSCMDLV0011ServiceImpl implements PSCMDLV0011Service {
	@Autowired
	private PSCMDLV0011Dao pscmdlv0011Dao; 

	@Override
	public List<DataMap> selectVendorPenaltyCompRate(DataMap paramMap) throws Exception {
		return pscmdlv0011Dao.selectVendorPenaltyCompRate(paramMap);
	}

	@Override
	public int vendorPenaltyTotalCnt(Map<String, String> paramMap) throws Exception {
		return pscmdlv0011Dao.vendorPenaltyTotalCnt(paramMap);
	}
	
	@Override
	public List<DataMap> selectVendorPenaltyCompRateExcel(DataMap paramMap) throws Exception {
		return pscmdlv0011Dao.selectVendorPenaltyCompRateExcel(paramMap);
	}

}
