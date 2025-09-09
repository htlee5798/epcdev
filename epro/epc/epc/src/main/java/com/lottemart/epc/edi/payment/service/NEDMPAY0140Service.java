package com.lottemart.epc.edi.payment.service;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lottemart.epc.edi.payment.model.NEDMPAY0140VO;


public interface NEDMPAY0140Service {
	/**
	 * 거래실적조회  - > 패밀리론 
	 * @param NEDMPAY0140VO
	 * @return
	 */
	public List<NEDMPAY0140VO> selectFamilyLoan(NEDMPAY0140VO map ) throws Exception;
	
	/**
	 * 거래실적조회  - > 패밀리론 txt파일생성
	 * @param NEDMPAY0140VO
	 * @param request
	 * @param response
	 * @return
	 */
	public void createTextFamilyLoan(NEDMPAY0140VO map ,HttpServletRequest request, HttpServletResponse response ) throws Exception;
	
	
}


