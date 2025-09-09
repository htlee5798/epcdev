package com.lottemart.epc.edi.usply.service;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lottemart.epc.edi.usply.model.NEDMUSP0030VO;


public interface NEDMUSP0030Service {
	/**
	 * 미납정보 - > 기간정보  - > 상품별 조회
	 * @param NEDMUSP0030VO
	 * @return
	 */
	public List<NEDMUSP0030VO> selectProductInfo(NEDMUSP0030VO map ) throws Exception;
	
	/**
	 * 텍스트 생성
	 * @param vo
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void createTextOrdProdList(NEDMUSP0030VO vo, HttpServletRequest request, HttpServletResponse response) throws Exception;
}

