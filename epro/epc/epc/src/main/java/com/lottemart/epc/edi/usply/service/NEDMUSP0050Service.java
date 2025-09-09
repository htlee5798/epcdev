package com.lottemart.epc.edi.usply.service;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lottemart.epc.edi.usply.model.NEDMUSP0050VO;


public interface NEDMUSP0050Service {
	/**
	 * 미납정보 - > 기간정보  - > 미납사유 입력&조회 조회
	 * @param NEDMUSP0050VO
	 * @param request
	 * @return
	 */
	public List<NEDMUSP0050VO> selectUsplyReasonInfo(NEDMUSP0050VO map ) throws Exception;
	
						       
	public List<NEDMUSP0050VO> selectUpdateUsplyReasonInfo(NEDMUSP0050VO map ) throws Exception;
	
	
	/**
	 * 미납정보 - > 기간정보  - > 미납사유 입력&조회 저장
	 * @param NEDMUSP0050VO
	 * @param request
	 * @return
	 */
	public void selectUsplyReasonUpdate(NEDMUSP0050VO map) throws Exception;
	
	/**
	 * 텍스트 생성
	 * @param vo
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void createTextOrdProdList(NEDMUSP0050VO vo, HttpServletRequest request, HttpServletResponse response) throws Exception;
}

