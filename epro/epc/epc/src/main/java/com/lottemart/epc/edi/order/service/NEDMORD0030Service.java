package com.lottemart.epc.edi.order.service;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lottemart.epc.edi.order.model.NEDMORD0030VO;


public interface NEDMORD0030Service {
	
	/**
	 * 기간정보 - > 전표상세 조회
	 * @param NEDMORD0030VO
	 * @return
	 * @throws Exception
	 */
	public List<NEDMORD0030VO> selectJunpyoDetailInfo(NEDMORD0030VO map ) throws Exception;
	/**
	 * 기간정보 - > 전표상세  txt파일 생성
	 * @param NEDMORD0030VO
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return
	 * @throws Exception
	 */
	public void createTextJunpyoDetail(NEDMORD0030VO map ,HttpServletRequest request, HttpServletResponse response ) throws Exception;
	
	
}

