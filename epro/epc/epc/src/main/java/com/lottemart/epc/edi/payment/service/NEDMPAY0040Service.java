package com.lottemart.epc.edi.payment.service;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lottemart.epc.edi.payment.model.NEDMPAY0040VO;


public interface NEDMPAY0040Service {
	/**
	 * 기간별 결산정보  - > 세금계산서 정보 
	 * @param NEDMPAY0040VO
	 * @return
	 */
	public List<NEDMPAY0040VO> selectCredAggInfo(NEDMPAY0040VO map ) throws Exception;
	
	/**
	 * 기간별 결산정보  - > 세금계산서 정보 txt 파일 생성
	 * @param NEDMPAY0040VO
	 * @param request
	 * @param response
	 * @return
	 */
	public void createTextCredAgg(NEDMPAY0040VO map ,HttpServletRequest request, HttpServletResponse response ) throws Exception;
	
}


