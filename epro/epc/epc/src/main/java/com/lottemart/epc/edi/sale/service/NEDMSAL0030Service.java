package com.lottemart.epc.edi.sale.service;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lottemart.epc.edi.sale.model.NEDMSAL0030VO;


public interface NEDMSAL0030Service {
	/**
	 * 매출정보 - > 매출정보(상품별)  
	 * @param NEDMSAL0030VO
	 * @return
	 */
	public List<NEDMSAL0030VO> selectProductInfo(NEDMSAL0030VO map ) throws Exception;
	/**
	 * 매출정보 - > 매출정보(상품별)   txt파일 생성
	 * @param NEDMSAL0030VO
	 * @return
	 */
	public void createTextProduct(NEDMSAL0030VO map ,HttpServletRequest request, HttpServletResponse response ) throws Exception;
	
}

