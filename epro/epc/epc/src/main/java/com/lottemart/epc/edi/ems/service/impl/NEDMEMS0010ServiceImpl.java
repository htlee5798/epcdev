package com.lottemart.epc.edi.ems.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.epc.edi.ems.dao.NEDMEMS0010Dao;
import com.lottemart.epc.edi.ems.model.NEDMEMS0010VO;
import com.lottemart.epc.edi.ems.service.NEDMEMS0010Service;

@Service("nedmems0010Service")
public class NEDMEMS0010ServiceImpl implements NEDMEMS0010Service {

	@Autowired
	private NEDMEMS0010Dao nedmems0010Dao;
	
	public NEDMEMS0010VO selectEmsData(Map<String,Object> map) throws Exception {
		return nedmems0010Dao.selectEmsData(map);
	}
	
}
