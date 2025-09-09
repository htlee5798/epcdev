package com.lottemart.epc.common.service;

import java.util.List;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.common.model.PSCMCOM0007VO;

public interface PSCMCOM0007Service {

	public List<DataMap> selectOrderIdList(PSCMCOM0007VO vo) throws Exception;
	
}
