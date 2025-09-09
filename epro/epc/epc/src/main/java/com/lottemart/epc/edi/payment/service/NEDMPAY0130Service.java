package com.lottemart.epc.edi.payment.service;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lottemart.epc.edi.payment.model.NEDMPAY0130VO;


public interface NEDMPAY0130Service {
	/**
	 * 거래실적조회  - > 잔액조회
	 * @param NEDMPAY0130VO
	 * @return
	 */
	public List<NEDMPAY0130VO> selectCredLedStoreInfo(NEDMPAY0130VO map ) throws Exception;
	
	/**
	 * 거래실적조회  - > 잔액조회 txt파일 생성
	 * @param NEDMPAY0130VO
	 * @param request
	 * @param response
	 * @return
	 */
	public void createTextCredLedStore(NEDMPAY0130VO map ,HttpServletRequest request, HttpServletResponse response ) throws Exception;
	
	
}


