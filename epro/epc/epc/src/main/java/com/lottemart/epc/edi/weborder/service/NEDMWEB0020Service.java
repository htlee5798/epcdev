package com.lottemart.epc.edi.weborder.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.lottemart.epc.edi.weborder.model.NEDMWEB0010VO;
import com.lottemart.epc.edi.weborder.model.NEDMWEB0020VO;
import com.lottemart.epc.edi.weborder.model.SearchWebOrder;
import com.lottemart.epc.edi.weborder.model.TedOrdProcess001VO;

/**
 * @Class Name : NEDMWEB0020Service
 * @Description : 상품별 발주등록회 Service Class
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      		수정자          		수정내용
 *  -------    --------    ---------------------------
 * 2015.12.07	O YEUN KWON	  최초생성
 * 
 * @Copyright (C) 2000 ~ 2015 lottemart All right reserved.
 * </pre>
*/


public interface NEDMWEB0020Service {


	Map<String,Object> selectProdOrdList(NEDMWEB0020VO vo);
	
	Map<String,Object> selectProdOrdDetList(NEDMWEB0020VO vo);
	
	Map<String,Object> selectVendorException(NEDMWEB0020VO vo);
	
	public String insertProdOrdTemp(NEDMWEB0020VO vo, HttpServletRequest request) throws Exception;
	
	public String deleteProdOrdInfo (NEDMWEB0020VO vo) throws Exception;
}
