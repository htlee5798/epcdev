package com.lottemart.epc.edi.sale.service;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lottemart.epc.edi.sale.model.NEDMSAL0020VO;


public interface NEDMSAL0020Service {
	/**
	 * 매출정보 - > 매출정보(점포별)  
	 * @param NEDMSAL0020VO
	 * @return
	 */
	public List<NEDMSAL0020VO> selectStoreInfo(NEDMSAL0020VO map ) throws Exception;
	
	/**
	 * 매출정보 - > 매출정보(점포별)  txt파일 생성
	 * @param NEDMSAL0020VO
	 * @param request
	 * @param response
	 * @return
	 */
	public void createTextStore(NEDMSAL0020VO map ,HttpServletRequest request, HttpServletResponse response ) throws Exception;
	
}

