package com.lottemart.epc.edi.inventory.service;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lottemart.epc.edi.inventory.model.NEDMINV0010VO;

/**
 * @Class Name : NEDMINV0010Service
 * @Description : 재고정보 현재고(점포) 조회 Service Class
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      		수정자          		수정내용
 *  -------    --------    ---------------------------
 * 2015.11.18	O YEUN KWON	  최초생성
 * 
 * @Copyright (C) 2000 ~ 2015 lottemart All right reserved.
 * </pre>
*/

public interface NEDMINV0010Service {
	
	public List<NEDMINV0010VO> selectStoreInfo(NEDMINV0010VO nEDMINV0010VO ) throws Exception;
	public void createTextPeriod(NEDMINV0010VO nEDMINV0010VO ,HttpServletRequest request, HttpServletResponse response) throws Exception;
	

	
}

