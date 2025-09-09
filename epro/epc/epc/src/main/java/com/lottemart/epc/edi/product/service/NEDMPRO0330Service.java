package com.lottemart.epc.edi.product.service;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.lottemart.epc.edi.product.model.NEDMPRO0330VO;


public interface NEDMPRO0330Service {
	
	
	/**
	 * 약정서 조회
	 * @param paramVo
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	public Map<String, Object>  getProdRtnDocList(NEDMPRO0330VO paramVo, HttpServletRequest request) throws Exception;
	
	/**
	 * 반품 약정서 조회( 상품별, 점포 & 상품별 )
	 * @param paramVo
	 * @param request
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	public Map<String, Object>  selectProdRtnCntrStrProdList(NEDMPRO0330VO paramVo, HttpServletRequest request) throws Exception;
	
}

