package com.lottemart.epc.statistics.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.statistics.dao.PSCMSTA0010Dao;
import com.lottemart.epc.statistics.model.PSCMSTA0010VO;
import com.lottemart.epc.statistics.service.PSCMSTA0010Service;

@Service("PSCMSTA0010Service")
public class PSCMSTA0010ServiceImpl implements PSCMSTA0010Service {
	
	@Autowired
	private PSCMSTA0010Dao pscmsta0010Dao;

	public List<DataMap> selectToDate() throws Exception {
		return pscmsta0010Dao.selectToDate();
	}
	
	public DataMap selectRecipeConEdmSummaryTotal(Map<String, String> paramMap) throws Exception {
		return (DataMap)pscmsta0010Dao.selectRecipeConEdmSummaryTotal(paramMap);
	}
	
	
	public List<DataMap> selectRecipeConEdmSummaryList(PSCMSTA0010VO searchVO) throws Exception {
		return pscmsta0010Dao.selectRecipeConEdmSummaryList(searchVO);
	}

	public List<DataMap> selectRecipeConEdmSummaryListExcel(PSCMSTA0010VO searchVO) throws Exception {
		return pscmsta0010Dao.selectRecipeConEdmSummaryListExcel(searchVO);
	}
}
