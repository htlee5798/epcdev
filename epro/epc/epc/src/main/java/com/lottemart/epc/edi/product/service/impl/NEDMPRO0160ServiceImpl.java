package com.lottemart.epc.edi.product.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.epc.edi.product.dao.NEDMPRO0160Dao;
import com.lottemart.epc.edi.product.model.NEDMPRO0160VO;
import com.lottemart.epc.edi.product.service.NEDMPRO0160Service;

@Service("nedmpro0160Service")
public class NEDMPRO0160ServiceImpl implements NEDMPRO0160Service {
	
	@Autowired
	private NEDMPRO0160Dao nedmpro0160Dao;

	/**
	 * Count
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int selectPbProductCount(NEDMPRO0160VO vo) throws Exception {
		return nedmpro0160Dao.selectPbProductCount(vo);
	}
	
	/**
	 * List 조회
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<NEDMPRO0160VO> selectPbProductList(NEDMPRO0160VO vo) throws Exception {
		return nedmpro0160Dao.selectPbProductList(vo);
	}

}
