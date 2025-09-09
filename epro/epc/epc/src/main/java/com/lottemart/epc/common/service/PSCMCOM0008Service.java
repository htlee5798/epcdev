package com.lottemart.epc.common.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.lottemart.epc.common.model.PSCMCOM0008VO;

public interface PSCMCOM0008Service {

	

	Map<String,Object>  selectVendCodeList(PSCMCOM0008VO vo,HttpServletRequest request);
	
	
}
