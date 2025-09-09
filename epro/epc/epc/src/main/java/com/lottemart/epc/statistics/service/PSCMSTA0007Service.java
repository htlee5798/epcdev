package com.lottemart.epc.statistics.service;

import java.util.List;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.statistics.model.PSCMSTA0007VO;

public interface PSCMSTA0007Service {
	
	public List<DataMap> selectToDate() throws Exception ;

	public List<DataMap> selectAffiliateLinkNoList(PSCMSTA0007VO searchVO) throws Exception;
	
	public List<DataMap> selectDaumTotalOrder(PSCMSTA0007VO searchVO) throws Exception;

	public List<DataMap> selectDaumOrderList(PSCMSTA0007VO searchVO) throws Exception;

	public List<DataMap> selectDaumOrderListExcel(PSCMSTA0007VO searchVO) throws Exception;
	
	
}
