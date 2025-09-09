package com.lottemart.epc.edi.weborder.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.lottemart.epc.edi.weborder.model.NEDMWEB0010VO;
import com.lottemart.epc.edi.weborder.model.SearchWebOrder;
import com.lottemart.epc.edi.weborder.model.TedOrdProcess001VO;

/**
 * @Class Name : NEDMWEB0010Service
 * @Description : 점포별 발주등록 Service Class
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      		수정자          		수정내용
 *  -------    --------    ---------------------------
 * 2015.12.03	O YEUN KWON	  최초생성
 * 
 * @Copyright (C) 2000 ~ 2015 lottemart All right reserved.
 * </pre>
 */

public interface NEDMWEB0010Service {


	Map<String,Object> selectStoreOrdList(NEDMWEB0010VO nEDMWEB0010VO);
	
	Map<String,Object> selectVendorException(NEDMWEB0010VO nEDMWEB0010VO);
	
	/**
	 * 점포 정보 조회 [점포코드 필수]
	 * @param SearchWebOrder
	 * @return void
	 * @throws Exception
	 */
	public Map<String,Object> selectStoreOrdListFixedStr(NEDMWEB0010VO nEDMWEB0010VO) throws Exception;
	
	Map<String,Object> selectStoreOrdDetList(NEDMWEB0010VO nEDMWEB0010VO);
	
	public String insertStoreOrdTemp(NEDMWEB0010VO vo, HttpServletRequest request) throws Exception;
	
	public String deleteStoreOrdInfo (NEDMWEB0010VO vo) throws Exception;
	
}
