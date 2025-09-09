package com.lottemart.epc.edi.inventory.service;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lottemart.epc.edi.inventory.model.NEDMINV0020VO;

/**
 * @Class Name : NEDMINV0020Service
 * @Description : 재고정보 현재고(상품) 조회 Service Class
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

public interface NEDMINV0020Service {
	
	public List<NEDMINV0020VO> selectProductInfo(NEDMINV0020VO nEDMINV0020VO ) throws Exception;
	public void createTextProduct(NEDMINV0020VO nEDMINV0020VO ,HttpServletRequest request, HttpServletResponse response) throws Exception;
	public void createTextProductText(NEDMINV0020VO nEDMINV0020VO ,HttpServletRequest request, HttpServletResponse response) throws Exception;
	

	
}

