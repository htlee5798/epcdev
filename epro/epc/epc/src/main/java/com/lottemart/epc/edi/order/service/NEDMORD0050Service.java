package com.lottemart.epc.edi.order.service;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lottemart.epc.edi.order.model.NEDMORD0050VO;


public interface NEDMORD0050Service {
	
	/**
	 * 기간정보 - > PDC전표상세 조회
	 * @param NEDMORD0050VO
	 * @return
	 * @throws Exception
	 */
	public List<NEDMORD0050VO> selectJunpyoDetailInfoPDC(NEDMORD0050VO map ) throws Exception;
	
	/**
	 * 기간정보 - > PDC전표상세  txt파일 생성
	 * @param NEDMORD0050VO
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return
	 * @throws Exception
	 */
	public void createTextJunpyoDetailPDC(NEDMORD0050VO map ,HttpServletRequest request, HttpServletResponse response ) throws Exception;
	
	
}

