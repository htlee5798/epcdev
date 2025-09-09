package com.lottemart.epc.edi.usply.service;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lottemart.epc.edi.usply.model.NEDMUSP0020VO;


public interface NEDMUSP0020Service {
	/**
	 * 미납정보 - > 기간정보  - > 점포별 조회
	 * @param NEDMUSP0020VO
	 * @return
	 */
	public List<NEDMUSP0020VO> selectStoreInfo(NEDMUSP0020VO map ) throws Exception;
	
	/**
	 * 텍스트 생성
	 * @param vo
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void createTextOrdProdList(NEDMUSP0020VO vo, HttpServletRequest request, HttpServletResponse response) throws Exception;
}

