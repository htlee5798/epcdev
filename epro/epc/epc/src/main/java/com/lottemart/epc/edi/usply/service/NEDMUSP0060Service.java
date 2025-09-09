package com.lottemart.epc.edi.usply.service;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lottemart.epc.edi.usply.model.NEDMUSP0060VO;


public interface NEDMUSP0060Service {
	/**
	 * 미납정보 - > 기간정보  - > 패널티 확정 조회
	 * @param NEDMUSP0060VO
	 * @return
	 */
	public List<NEDMUSP0060VO> selectPenaltyInfo(NEDMUSP0060VO map ) throws Exception;
	
	/**
	 * 텍스트 생성
	 * @param vo
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void createTextOrdProdList(NEDMUSP0060VO vo, HttpServletRequest request, HttpServletResponse response) throws Exception;
}

