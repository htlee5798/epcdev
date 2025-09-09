package com.lottemart.epc.edi.product.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.epc.edi.product.dao.NEDMPRO0110Dao;
import com.lottemart.epc.edi.product.model.NEDMPRO0110VO;
import com.lottemart.epc.edi.product.service.NEDMPRO0110Service;

@Service("nedmpro0110Service")
public class NEDMPRO0110ServiceImpl implements NEDMPRO0110Service {

	@Autowired
	private NEDMPRO0110Dao nedmpro0110Dao;
	
	/**
	 * Count
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int selectWholeProductCount(NEDMPRO0110VO vo) throws Exception {
		return nedmpro0110Dao.selectWholeProductCount(vo);
	}
	
	/**
	 * List 조회
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<NEDMPRO0110VO> selectWholeProductList(NEDMPRO0110VO vo) throws Exception {
		return nedmpro0110Dao.selectWholeProductList(vo);
	}
	
}
