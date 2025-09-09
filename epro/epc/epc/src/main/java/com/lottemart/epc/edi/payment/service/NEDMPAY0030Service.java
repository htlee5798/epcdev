package com.lottemart.epc.edi.payment.service;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lottemart.epc.edi.payment.model.NEDMPAY0030VO;


public interface NEDMPAY0030Service {
	/**
	 * 기간별 결산정보  - > 점포별 대금결제
	 * @param NEDMPAY0030VO
	 * @return
	 */
	public List<NEDMPAY0030VO> selectPaymentStoreInfo(NEDMPAY0030VO map ) throws Exception;
	
	/**
	 * 기간별 결산정보  - > 점포별 대금결제 txt 파일 생성
	 * @param NEDMPAY0030VO
	 * @param request
	 * @param response
	 * @return
	 */
	public void createTextPaymentStore(NEDMPAY0030VO map ,HttpServletRequest request, HttpServletResponse response ) throws Exception;
	
}


