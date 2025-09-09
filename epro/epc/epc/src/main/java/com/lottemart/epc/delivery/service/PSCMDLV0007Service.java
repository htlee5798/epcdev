package com.lottemart.epc.delivery.service;

import java.util.List;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.delivery.model.PSCMDLV0007VO;

public interface PSCMDLV0007Service {
	
	public List<DataMap> getTetCodeList(PSCMDLV0007VO vo) throws Exception;
	
	public DataMap selectPartherReturnStatusSum(PSCMDLV0007VO vo) throws Exception;	
	
	public List<DataMap> selectPartherReturnStatusList(PSCMDLV0007VO vo) throws Exception;
		
}
