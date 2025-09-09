package com.lottemart.epc.edi.product.service;

import java.util.List;

import com.lottemart.epc.edi.product.model.NEDMPRO0110VO;

public interface NEDMPRO0110Service {

	/**
	 * Count
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int selectWholeProductCount(NEDMPRO0110VO vo) throws Exception;
	
	/**
	 * List 조회
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<NEDMPRO0110VO> selectWholeProductList(NEDMPRO0110VO vo) throws Exception;
	
}
