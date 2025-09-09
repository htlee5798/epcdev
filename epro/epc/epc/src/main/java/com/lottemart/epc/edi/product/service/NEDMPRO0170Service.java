package com.lottemart.epc.edi.product.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.lottemart.epc.edi.product.model.NEDMPRO0150VO;
import com.lottemart.epc.edi.product.model.NEDMPRO0170VO;

public interface NEDMPRO0170Service {
	
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
	public int selectDisconProductCount(NEDMPRO0170VO vo) throws Exception;
	
	/**
	 * List 조회
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<NEDMPRO0170VO> selectProductList(NEDMPRO0170VO vo) throws Exception;
	
	/**
	 * 단품정보 등록
	 * @param vo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String insertDisconProduct(NEDMPRO0170VO vo) throws Exception;
	
	/**
	 * 단품정보 삭제
	 * @param vo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String deleteDisconProduct(NEDMPRO0170VO vo) throws Exception;
	
	/**
	 * 단품정보 List 조회 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<NEDMPRO0170VO> selectDisconProductList(NEDMPRO0170VO vo) throws Exception;
	
	/**
	 * 단품정보 List 조회 ( 날짜에 기반해서 )
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<NEDMPRO0170VO> selectDisconProductListByDate(NEDMPRO0170VO vo) throws Exception;
}
