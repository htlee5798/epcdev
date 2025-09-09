package com.lottemart.epc.edi.usply.service;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lottemart.epc.edi.usply.model.NEDMUSP0040VO;


public interface NEDMUSP0040Service {
	/**
	 * 미납정보 - > 기간정보  - > 상품상세 첫페이지
	 * @param NEDMUSP0040VO
	 * @return
	 */
	public List<NEDMUSP0040VO> selectProductDetailInfo(NEDMUSP0040VO map ) throws Exception;
	
	/**
	 * 텍스트 생성
	 * @param vo
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void createTextOrdProdList(NEDMUSP0040VO vo, HttpServletRequest request, HttpServletResponse response) throws Exception;
}

