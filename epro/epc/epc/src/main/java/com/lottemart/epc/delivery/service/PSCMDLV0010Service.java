package com.lottemart.epc.delivery.service;

import java.util.List;

import com.lottemart.common.util.DataMap;

public interface PSCMDLV0010Service {
	
	public List<DataMap> selectVendorPenalty(DataMap paramMap) throws Exception;

}
