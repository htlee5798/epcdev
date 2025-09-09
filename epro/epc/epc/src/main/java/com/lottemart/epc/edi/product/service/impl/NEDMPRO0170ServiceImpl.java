package com.lottemart.epc.edi.product.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.epc.edi.product.dao.NEDMPRO0170Dao;
import com.lottemart.epc.edi.product.model.NEDMPRO0150VO;
import com.lottemart.epc.edi.product.model.NEDMPRO0170VO;
import com.lottemart.epc.edi.product.service.NEDMPRO0170Service;

import lcn.module.framework.property.ConfigurationService;

@Service("nedmpro0170Service")
public class NEDMPRO0170ServiceImpl implements NEDMPRO0170Service {
	
	@Autowired
	private ConfigurationService config;
	
	@Autowired
	private NEDMPRO0170Dao nedmpro0170Dao;

	/**
	 * 현재일자 가져오기
	 * @return
	 * @throws Exception
	 */
	public String selectCurrDate() throws Exception {
		return nedmpro0170Dao.selectCurrDate();
	}
	
	/**
	 * Count
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int selectDisconProductCount(NEDMPRO0170VO vo) throws Exception {
		return nedmpro0170Dao.selectDisconProductCount(vo);
	}
	
	/**
	 * List 조회
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<NEDMPRO0170VO> selectProductList(NEDMPRO0170VO vo) throws Exception {
		return nedmpro0170Dao.selectProductList(vo);
	}
	
	/**
	 * 단품정보 등록
	 * @param vo
	 * @throws Exception
	 */
	public String insertDisconProduct(NEDMPRO0170VO vo) throws Exception {
		
		nedmpro0170Dao.deleteDisconProduct(vo);
		nedmpro0170Dao.insertDisconProduct(vo);
		
		return "S";
	}
	
	/**
	 * 단품정보 삭제
	 * @param vo
	 * @throws Exception
	 */
	public String deleteDisconProduct(NEDMPRO0170VO vo) throws Exception {
		
		nedmpro0170Dao.deleteDisconProduct(vo);
		
		return "S";
	}
	
	/**
	 * 단품정보 List 조회 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<NEDMPRO0170VO> selectDisconProductList(NEDMPRO0170VO vo) throws Exception {
		return nedmpro0170Dao.selectDisconProductList(vo);
	}
	
	/**
	 * 단품정보 List 조회 ( 날짜에 기반해서 )
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<NEDMPRO0170VO> selectDisconProductListByDate(NEDMPRO0170VO vo) throws Exception {
		return nedmpro0170Dao.selectDisconProductListByDate(vo);
	}
}
