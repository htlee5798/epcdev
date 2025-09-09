package com.lottemart.epc.edi.payment.service;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lottemart.epc.edi.payment.model.NEDMPAY0110VO;


public interface NEDMPAY0110Service {
	/**
	 * 거래실적조회  - > 거래실적조회 
	 * @param NEDMPAY0110VO
	 * @return
	 */
	public List<NEDMPAY0110VO> selectCredLedInfo(NEDMPAY0110VO map ) throws Exception;
	
	/**
	 * 거래실적조회  - > 거래실적조회  txt파일 생성
	 * @param NEDMPAY0110VO
	 * @param request
	 * @param response
	 * @return
	 */
	public void createTextCredLed(NEDMPAY0110VO map ,HttpServletRequest request, HttpServletResponse response ) throws Exception;
	
	
}


