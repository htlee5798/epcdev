package com.lottemart.epc.edi.weborder.service;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.lottemart.epc.edi.weborder.model.NEDMWEB0220VO;

/**
 * @Class Name : NEDMWEB0220Service
 * @Description : 발주전체현황 Service Class
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      		수정자          		수정내용
 *  -------    --------    ---------------------------
 * 2015.12.09	O YEUN KWON	  최초생성
 * 
 * @Copyright (C) 2000 ~ 2015 lottemart All right reserved.
 * </pre>
 */


public interface NEDMWEB0220Service {
	
	
	/**
	 * 협력사코드별  발주전제현황 조회
	 * @Method Name : selectVenOrdAllInfo
	 * @param SearchWebOrder
	 * @param HttpServletRequest
	 * @return  Map<String, String>
	 */
	public Map<String,Object> selectVenOrdAllInfo(NEDMWEB0220VO vo ,HttpServletRequest request) throws Exception;

	

}

