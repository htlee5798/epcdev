package com.lottemart.epc.edi.sale.service;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lottemart.epc.edi.sale.model.NEDMSAL0010VO;


public interface NEDMSAL0010Service {
	/**
	 * 매입정보 - > //매출정보(일자별) 조회
	 * @param NEDMSAL0010VO
	 * @return
	 */
	public List<NEDMSAL0010VO> selectDayInfo(NEDMSAL0010VO map ) throws Exception;
	
	public int selectDayInfoCntSum(NEDMSAL0010VO map ) throws Exception;
	
	
	
	/**
	 * 매입정보 - > //매출정보(일자별) txt파일 생성
	 * @param NEDMSAL0010VO
	 * @return
	 */
	public void createTextDay(NEDMSAL0010VO map,HttpServletRequest request, HttpServletResponse response) throws Exception;
		
}

