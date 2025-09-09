package com.lottemart.epc.edi.order.service;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lottemart.epc.edi.order.model.NEDMORD0020VO;


public interface NEDMORD0020Service {
	
	/**
	 * 기간정보 - > 전표별 조회
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<NEDMORD0020VO> selectJunpyoInfo(NEDMORD0020VO map) throws Exception;
	
	/**
	 * 기간정보 - > 점포별 txt파일 생성
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public void createTextJunpyo(NEDMORD0020VO map ,HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	
}

