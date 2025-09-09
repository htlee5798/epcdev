package com.lottemart.epc.edi.product.service;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.lottemart.epc.edi.product.model.NEDMPRO0140VO;


public interface NEDMPRO0140Service {
	
	
	public List<NEDMPRO0140VO> selectPlcProductList(NEDMPRO0140VO vo) throws Exception;
	
	
	//public void createTextOrdProdList(NEDMPRO0140VO vo, HttpServletRequest request, HttpServletResponse response) throws Exception;
	

}
