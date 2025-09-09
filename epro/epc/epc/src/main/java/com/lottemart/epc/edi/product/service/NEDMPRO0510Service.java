package com.lottemart.epc.edi.product.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.lottemart.epc.edi.product.model.NEDMPRO0500VO;
/**
 * 
 * @Class Name : NEDMPRO0510Service.java
 * @Description : 원가변경요청 
 * @Modification Information
 * 
 *               <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      			수정자           		수정내용
 *  -------    		--------    	---------------------------
 * 	2025.03.19		yun				최초생성
 *               </pre>
 */
public interface NEDMPRO0510Service {
	
	/**
	 * 원가변경요청정보 현황 조회
	 * @param NEDMPRO0500VO
	 * @param request
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	public Map<String,Object> selectTpcProdChgCostDetailView(NEDMPRO0500VO NEDMPRO0500VO, HttpServletRequest request) throws Exception;
	
	/**
	 * 원가변경요청정보 복사
	 * @param NEDMPRO0500VO
	 * @param request
	 * @return Map<String,Object>
	 * @throws Exception
	 */
	public Map<String,Object> insertCopyTpcProdChgCost(NEDMPRO0500VO NEDMPRO0500VO, HttpServletRequest request) throws Exception;
	
	/**
	 * 원가변경요청정보 엑셀 다운로드 조회 
	 * @param NEDMPRO0500VO
	 * @return
	 * @throws Exception
	 */
	public List<HashMap<String, String>> selectTpcProdChgCostDetailExcelInfo(NEDMPRO0500VO NEDMPRO0500VO) throws Exception;
	
	/**
	 * 원가변경요청정보 수정 저장
	 * @param nEDMPRO0500VO
	 * @param request
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	public Map<String, Object> updateTpcProdChgCostInfo(NEDMPRO0500VO nEDMPRO0500VO, HttpServletRequest request) throws Exception;
}
