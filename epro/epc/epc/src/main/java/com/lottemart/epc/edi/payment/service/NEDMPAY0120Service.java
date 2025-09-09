package com.lottemart.epc.edi.payment.service;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lottemart.epc.edi.payment.model.NEDMPAY0120VO;


public interface NEDMPAY0120Service {
	/**
	 * 거래실적조회  - > 점포별 거래실적
	 * @param NEDMPAY0120VO
	 * @return
	 */
	public List<NEDMPAY0120VO> selectCredLedStoreDetail(NEDMPAY0120VO map ) throws Exception;
	
	/**
	 * 거래실적조회  - > 점포별 거래실적 txt파일 생성
	 * @param NEDMPAY0120VO
	 * @param request
	 * @param response
	 * @return
	 */
	public void createTextCredLedStoreDetail(NEDMPAY0120VO map ,HttpServletRequest request, HttpServletResponse response ) throws Exception;
	
	
}


