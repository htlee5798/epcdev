package com.lottemart.epc.edi.sale.service;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lottemart.epc.edi.sale.model.NEDMSAL0040VO;


public interface NEDMSAL0040Service {
	/**
	 * 매출정보 - > 매출정보(상품상세별)
	 * @param NEDMSAL0040VO
	 * @return
	 */
	public List<NEDMSAL0040VO> selectProductDetailInfo(NEDMSAL0040VO map ) throws Exception;
	
	/**
	 * 매출정보 - > 매출정보(상품상세별)  txt파일 생성
	 * @param NEDMSAL0040VO
	 * @param response
	 * @param request
	 * @return
	 */
	public void createTextProductDetail(NEDMSAL0040VO map ,HttpServletRequest request, HttpServletResponse response ) throws Exception;
	
}

