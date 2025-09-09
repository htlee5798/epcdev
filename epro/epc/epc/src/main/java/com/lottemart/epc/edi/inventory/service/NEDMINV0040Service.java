package com.lottemart.epc.edi.inventory.service;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lottemart.epc.edi.inventory.model.NEDMINV0040VO;

/**
 * @Class Name : NEDMINV0040Service
 * @Description : 재고정보 센터 점출입 상세 조회 Service Class
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

public interface NEDMINV0040Service {
	
	public List<NEDMINV0040VO> selectCenterStoreDetailInfo(NEDMINV0040VO nEDMINV0040VO ) throws Exception;
	public void createTextCenterStoreDetail(NEDMINV0040VO nEDMINV0040VO ,HttpServletRequest request, HttpServletResponse response) throws Exception;
	

	
}

