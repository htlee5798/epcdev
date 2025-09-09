package com.lottemart.epc.edi.payment.service;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lottemart.epc.edi.payment.model.NEDMPAY0020VO;


public interface NEDMPAY0020Service {
	/**
	 * 기간별 결산정보  - > 대금결제정보
	 * @param NEDMPAY0020VO
	 * @return
	 */
	public List<NEDMPAY0020VO> selectPaymentDayInfo(NEDMPAY0020VO map ) throws Exception;
	
	/**
	 * 기간별 결산정보  - > 대금결제정보 txt 파일 생성
	 * @param NEDMPAY0020VO
	 * @param request
	 * @param response
	 * @return
	 */
	public void createTextPaymentDay(NEDMPAY0020VO map ,HttpServletRequest request, HttpServletResponse response ) throws Exception;
	
}


