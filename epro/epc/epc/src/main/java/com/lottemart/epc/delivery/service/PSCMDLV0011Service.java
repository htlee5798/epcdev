package com.lottemart.epc.delivery.service;

import java.util.List;
import java.util.Map;

import com.lottemart.common.util.DataMap;

public interface PSCMDLV0011Service {

	public List<DataMap> selectVendorPenaltyCompRate(DataMap paramMap) throws Exception;
	
	public int vendorPenaltyTotalCnt(Map<String, String> paramMap) throws Exception;
	
	public List<DataMap> selectVendorPenaltyCompRateExcel(DataMap paramMap) throws Exception;
}
