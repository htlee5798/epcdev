package com.lottemart.epc.edi.weborder.service;

import java.util.Map;

import com.lottemart.epc.edi.weborder.model.NEDMWEB0040VO;

/**
 * @Class Name : NEDMWEB0040Service
 * @Description : 발주전체현황 Service Class
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

public interface NEDMWEB0040Service {


	Map<String,Object> selectOrdTotList(NEDMWEB0040VO vo);
	
}
