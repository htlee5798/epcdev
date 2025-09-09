package com.lottemart.epc.delivery.service;

import java.util.List;

import com.lottemart.common.util.DataMap;

public interface PSCMDLV0001Service {

	public List<DataMap> selectVenStatusList(DataMap paramMap) throws Exception;
	
}
