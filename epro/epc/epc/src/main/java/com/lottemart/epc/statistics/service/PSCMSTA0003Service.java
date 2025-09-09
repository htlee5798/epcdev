package com.lottemart.epc.statistics.service;

import java.util.List;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.statistics.model.PSCMSTA0003VO;

public interface PSCMSTA0003Service {
	
	public List<DataMap> selectToDate() throws Exception ;
	
	public List<DataMap> selectAsianaBalanceAccountList(PSCMSTA0003VO searchVO) throws Exception;
	
	public DataMap selectAsianaBalanceAccountSum(PSCMSTA0003VO searchVO) throws Exception;

	public List<DataMap> selectAsianaBalanceAccountListExcel(PSCMSTA0003VO searchVO) throws Exception;
}