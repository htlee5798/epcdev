package com.lottemart.epc.delivery.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.delivery.dao.PSCMDLV0010Dao;
import com.lottemart.epc.delivery.service.PSCMDLV0010Service;

@Service
public class PSCMDLV0010ServiceImpl implements PSCMDLV0010Service {
	@Autowired
	private PSCMDLV0010Dao pscmdlv0010Dao; 
	
	@Override
	public List<DataMap> selectVendorPenalty(DataMap paramMap) throws Exception {
		return pscmdlv0010Dao.selectVendorPenalty(paramMap);
	}
}
