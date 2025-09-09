package com.lottemart.epc.edi.order.service;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lottemart.epc.edi.order.model.NEDMORD0010VO;


public interface NEDMORD0010Service {
	
	/**
	 * 기간정보 - > 상품별 조회
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<NEDMORD0010VO> selectOrdPordList(NEDMORD0010VO vo) throws Exception;
	
	/**
	 * 발주정보(상품별) txt파일 생성
	 * @param vo
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void createTextOrdProdList(NEDMORD0010VO vo, HttpServletRequest request, HttpServletResponse response) throws Exception;
	
}

