package com.lottemart.epc.edi.usply.service;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lottemart.epc.edi.usply.model.NEDMUSP0010VO;


public interface NEDMUSP0010Service {
	
	////////////////////////////////////////////////////
	/**
	 * 미납정보 - > 기간정보  - > 일자별 조회
	 * @param NEDMUSP0010VO
	 * @return
	 */
	public List<NEDMUSP0010VO> selectDayInfo(NEDMUSP0010VO map ) throws Exception;
	
	/**
	 * 텍스트 생성
	 * @param vo
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void createTextOrdProdList(NEDMUSP0010VO vo, HttpServletRequest request, HttpServletResponse response) throws Exception;
}

