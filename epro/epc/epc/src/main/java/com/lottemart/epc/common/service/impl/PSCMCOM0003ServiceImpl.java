package com.lottemart.epc.common.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.common.dao.PSCMCOM0003Dao;
import com.lottemart.epc.common.service.PSCMCOM0003Service;

@Service("PSCMCOM0003Service")
public class PSCMCOM0003ServiceImpl implements PSCMCOM0003Service {
	
	@Autowired
	private PSCMCOM0003Dao PSCMCOM0003Dao;

	public List<DataMap> selectDeliCodePopup() throws Exception {
		return PSCMCOM0003Dao.selectDeliCodePopup();
	}

}
