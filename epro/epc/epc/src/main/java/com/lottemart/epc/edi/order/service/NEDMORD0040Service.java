package com.lottemart.epc.edi.order.service;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lottemart.epc.edi.order.model.NEDMORD0040VO;


public interface NEDMORD0040Service {
	
	/**
	 * 기간정보 - > 점포별 조회
	 * @param NEDMORD0040VO
	 * @return
	 * @throws Exception
	 */
	public List<NEDMORD0040VO> selectStoreInfo(NEDMORD0040VO map ) throws Exception;
	
	/**
	 * 기간정보 - > 점포별 txt파일 생성
	 * @param NEDMORD0040VO
	 * @return
	 * @throws Exception
	 */
	public void createTextStore(NEDMORD0040VO map ,HttpServletRequest request, HttpServletResponse response ) throws Exception;
	
}

