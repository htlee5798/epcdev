package com.lottemart.epc.edi.product.service;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import com.lottemart.epc.edi.product.model.NEDMPRO0300VO;


public interface NEDMPRO0300Service {
	
	/**
	 * 행사정보 등록내역 조회
	 * @param map
	 * @return List<NEDMPRO0300VO>
	 * @throws Exception
	 */
	public HashMap<String,Object> selectProEventAppList(NEDMPRO0300VO paramVo, HttpServletRequest request) throws Exception;
	
	/**
	 * ECS 계약서 조회
	 * @param paramVo
	 * @return NEDMPRO0300VO
	 * @throws Exception
	 */
	public NEDMPRO0300VO selectEcsDocInfo(NEDMPRO0300VO paramVo) throws Exception;
	
}

