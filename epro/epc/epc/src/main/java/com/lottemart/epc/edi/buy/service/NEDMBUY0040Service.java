package com.lottemart.epc.edi.buy.service;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lottemart.epc.edi.buy.model.NEDMBUY0040VO;

/**
 * @Class Name : NEDMBUY0040Service
 * @Description : 매입정보 전표별 조회 Service Class
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      		수정자          		수정내용
 *  -------    --------    ---------------------------
 * 2015.11.17	O YEUN KWON	  최초생성
 * 
 * @Copyright (C) 2000 ~ 2015 lottemart All right reserved.
 * </pre>
*/

public interface NEDMBUY0040Service {
	/**
	 * 조회
	 * @param nEDMBUY0040VO
	 * @return
	 * @throws Exception
	 */
	public List<NEDMBUY0040VO> selectJunpyoInfo(NEDMBUY0040VO nEDMBUY0040VO) throws Exception;
	
	/**
	 * 텍스트 파일 생성
	 * @param vo
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void createTextOrdProdList(NEDMBUY0040VO vo, HttpServletRequest request, HttpServletResponse response) throws Exception;	
}

