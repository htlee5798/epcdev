package com.lottemart.epc.edi.payment.service;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lottemart.epc.edi.payment.model.NEDMPAY0050VO;


public interface NEDMPAY0050Service {
	/**
	 * 기간별 결산정보  - > 물류비정보  txt 파일 생성
	 * @param NEDMPAY0050VO 
	 * @return
	 */
	public List<NEDMPAY0050VO> selectLogiAmtInfo(NEDMPAY0050VO map ) throws Exception;
	
	/**
	 * 기간별 결산정보  - > 물류비정보  txt 파일 생성
	 * @param NEDMPAY0050VO 
	 * @param request
	 * @param response
	 * @return
	 */
	public void createTextLogiAmt(NEDMPAY0050VO map ,HttpServletRequest request, HttpServletResponse response ) throws Exception;
	
}


