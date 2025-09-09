package com.lottemart.epc.delivery.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.delivery.dao.PSCMDLV0001Dao;
import com.lottemart.epc.delivery.service.PSCMDLV0001Service;

@Service("PSCMDLV0001Service")
public class PSCMDLV0001ServiceImpl implements PSCMDLV0001Service {
	
	@Autowired
	private PSCMDLV0001Dao pscmdlv0001Dao;

	public List<DataMap> selectVenStatusList(DataMap paramMap) throws Exception {
		return pscmdlv0001Dao.selectVenStatusList(paramMap);
	}

}
