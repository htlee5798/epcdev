package com.lottemart.epc.order.service;

import java.util.List;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.order.model.PSCMORD0009VO;

public interface PSCMORD0009Service {
	
	public List<DataMap> getTetCodeList(PSCMORD0009VO vo) throws Exception;
	
	public List<DataMap> selectPartnerReturnList(DataMap paramMap) throws Exception;
	
}
