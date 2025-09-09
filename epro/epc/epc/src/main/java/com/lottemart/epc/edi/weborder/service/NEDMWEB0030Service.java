package com.lottemart.epc.edi.weborder.service;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.lottemart.epc.edi.weborder.model.NEDMWEB0030VO;
import com.lottemart.epc.edi.weborder.model.TedPoOrdPackList001VO;

/**
 * @Class Name : NEDMWEB0030Service
 * @Description : 발주일괄등록 Service Class
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      		수정자          		수정내용
 *  -------    --------    ---------------------------
 * 2015.12.08	O YEUN KWON	  최초생성
 * 
 * @Copyright (C) 2000 ~ 2015 lottemart All right reserved.
 * </pre>
 */

public interface NEDMWEB0030Service {

	Map<String,Object> selectOrdPackInfo(NEDMWEB0030VO vo);
	
	public String insertOrdPackInfo(ArrayList<NEDMWEB0030VO> list, HttpServletRequest request, String ordVenCd) throws Exception;
	
	public String deleteExcelOrdInfo (NEDMWEB0030VO vo) throws Exception;
	
	public String insertExcelOrdInfo(NEDMWEB0030VO vo, HttpServletRequest request) throws Exception;
}
