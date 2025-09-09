package com.lottemart.epc.edi.product.service;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.lottemart.epc.edi.product.model.NEDMPRO0320VO;


public interface NEDMPRO0320Service {
	
	
	/**
	 * 반품 제안 등록 조회
	 * @param paramVo
	 * @return List<NEDMPRO0320VO>
	 * @throws Exception
	 */
	public HashMap<String,Object>  selectProdRtnItemList(NEDMPRO0320VO paramVo) throws Exception;
	
	
	/**
	 * 반품 제안 등록
	 * @param paramVo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public HashMap<String,Object> insertProdRtnItem(NEDMPRO0320VO paramVo, HttpServletRequest request) throws Exception;
	
	/**
	 * 반품 제안 삭제
	 * @param paramVo
	 * @param request
	 * @return HashMap<String,Object>
	 * @throws Exception
	 */
	public HashMap<String,Object> deleteProdRtnItem(NEDMPRO0320VO paramVo, HttpServletRequest request) throws Exception;
	
	/**
	 * 반품 제안 정보 ECO 전송
	 * @param paramVo
	 * @param request
	 * @return Map<String,Object>
	 * @throws Exception
	 */
	public Map<String,Object> insertProdRtnItemRfcCall(NEDMPRO0320VO paramVo, HttpServletRequest request) throws Exception;
	
	
	
}

