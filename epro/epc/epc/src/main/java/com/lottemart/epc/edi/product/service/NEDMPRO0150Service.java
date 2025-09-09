package com.lottemart.epc.edi.product.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.lottemart.epc.edi.product.model.NEDMPRO0150VO;

public interface NEDMPRO0150Service {

	/**
	 * 현재일자 가져오기
	 * @return
	 * @throws Exception
	 */
	public String selectCurrDate() throws Exception;
	
	/**
	 * Count
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int selectPbProductCount(NEDMPRO0150VO vo) throws Exception;
	
	/**
	 * List 조회
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<NEDMPRO0150VO> selectPbProductList(NEDMPRO0150VO vo) throws Exception;
	
	/**
	 * 재고등록
	 * @param vo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String insertPbProduct(NEDMPRO0150VO vo, HttpServletRequest request) throws Exception;
	
}
