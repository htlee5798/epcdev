package com.lottemart.epc.statistics.service;

import java.util.List;
import java.util.Map;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.statistics.model.PSCMSTA0010VO;

public interface PSCMSTA0010Service {
	
	public List<DataMap> selectToDate() throws Exception ;

	public DataMap selectRecipeConEdmSummaryTotal(Map<String,String> paramMap) throws Exception;
	
	public List<DataMap> selectRecipeConEdmSummaryList(PSCMSTA0010VO searchVO) throws Exception;
	
	public List<DataMap> selectRecipeConEdmSummaryListExcel(PSCMSTA0010VO searchVO) throws Exception;
	
}
