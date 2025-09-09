
package com.lottemart.epc.statistics.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.statistics.dao.PSCMSTA0003Dao;
import com.lottemart.epc.statistics.model.PSCMSTA0003VO;
import com.lottemart.epc.statistics.service.PSCMSTA0003Service;

@Service("PSCMSTA0003Service")
public class PSCMSTA0003ServiceImpl implements PSCMSTA0003Service {
	
	@Autowired
	private PSCMSTA0003Dao pscmsta0003Dao;
	
	public List<DataMap> selectToDate() throws Exception {
		return pscmsta0003Dao.selectToDate();
	}	
	
	public List<DataMap> selectAsianaBalanceAccountList(PSCMSTA0003VO searchVO) throws Exception {
		return pscmsta0003Dao.getAsianaBalanceAccountList(searchVO);
	}
	
	public DataMap selectAsianaBalanceAccountSum(PSCMSTA0003VO searchVO) throws Exception {
		return pscmsta0003Dao.getAsianaBalanceAccountSum(searchVO);
	}

	public List<DataMap> selectAsianaBalanceAccountListExcel(PSCMSTA0003VO searchVO) throws Exception {
		return pscmsta0003Dao.getAsianaBalanceAccountListExcel(searchVO);
	}
}
