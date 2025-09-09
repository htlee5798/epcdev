package com.lottemart.epc.statistics.service;

import java.util.List;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.statistics.model.PSCMSTA0006VO;

public interface PSCMSTA0006Service {
	
	public List<DataMap> selectToDate() throws Exception ;

	public List<DataMap> selectAffiliateLinkNoList(PSCMSTA0006VO searchVO) throws Exception;
	
	public List<DataMap> selectNaverEdmSummaryTotal(PSCMSTA0006VO searchVO) throws Exception;

	public List<DataMap> selectNaverEdmSummaryList(PSCMSTA0006VO searchVO) throws Exception;
	
	public List<DataMap> selectNaverEdmSummaryListExcel(PSCMSTA0006VO searchVO) throws Exception;
	
}
