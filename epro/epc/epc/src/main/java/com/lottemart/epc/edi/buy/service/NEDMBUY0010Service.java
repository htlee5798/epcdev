package com.lottemart.epc.edi.buy.service;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lottemart.epc.edi.buy.model.NEDMBUY0010VO;

/**
 * @Class Name : NEDMBUY0010Service
 * @Description : 매입정보 일자별 조회 Service Class
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      		수정자          		수정내용
 *  -------    --------    ---------------------------
 * 2015.11.16	O YEUN KWON	  최초생성
 * 
 * @Copyright (C) 2000 ~ 2015 lottemart All right reserved.
 * </pre>
*/

public interface NEDMBUY0010Service {
	
	/**
	 * 조회
	 * @param nEDMBUY0010VO
	 * @return
	 * @throws Exception
	 */
	public List<NEDMBUY0010VO> selectBuyInfo(NEDMBUY0010VO nEDMBUY0010VO) throws Exception;	
	
	/**
	 * 텍스트 생성
	 * @param vo
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void createTextOrdProdList(NEDMBUY0010VO vo, HttpServletRequest request, HttpServletResponse response) throws Exception;
	
}

