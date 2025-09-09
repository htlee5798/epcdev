package com.lottemart.epc.edi.product.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.lottemart.epc.edi.product.model.NEDMPRO0160VO;

public interface NEDMPRO0160Service {
	
	/**
	 * Count
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int selectPbProductCount(NEDMPRO0160VO vo) throws Exception;
	
	/**
	 * List 조회
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<NEDMPRO0160VO> selectPbProductList(NEDMPRO0160VO vo) throws Exception;
	
}
