package com.lottemart.epc.edi.ems.service;

import java.util.Map;

import com.lottemart.epc.edi.ems.model.NEDMEMS0010VO;

public interface NEDMEMS0010Service {

	public NEDMEMS0010VO selectEmsData(Map<String,Object> map) throws Exception;
	
}
