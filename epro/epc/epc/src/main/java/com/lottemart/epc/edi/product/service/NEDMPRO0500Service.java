package com.lottemart.epc.edi.product.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.lottemart.epc.edi.product.model.NEDMPRO0500VO;

/**
 * 
 * @Class Name : NEDMPRO0500Service.java
 * @Description : 원가변경요청 등록
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
public interface NEDMPRO0500Service {
	
	/**
	 * 원가변경 상세정보 조회
	 * @param nEDMPRO0500VO
	 * @param request
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	public Map<String,Object> selectTpcProdChgCostDetail(NEDMPRO0500VO nEDMPRO0500VO, HttpServletRequest request) throws Exception;
	
	/**
	 * 원가변경요청 정보 저장
	 * @param nEDMPRO0500VO
	 * @param request
	 * @return Map<String,Object>
	 * @throws Exception
	 */
	public Map<String,Object> insertTpcProdChgCostInfo(NEDMPRO0500VO nEDMPRO0500VO, HttpServletRequest request) throws Exception;
	
	/**
	 * 원가변경요청 아이템 정보 삭제
	 * @param nEDMPRO0500VO
	 * @param request
	 * @return Map<String,Object>
	 * @throws Exception
	 */
	public Map<String,Object> deleteTpcProdChgCostItem(NEDMPRO0500VO nEDMPRO0500VO, HttpServletRequest request) throws Exception;
	
	/**
	 * 판매코드 선택가능 여부 확인
	 * @param nEDMPRO0500VO
	 * @param request
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	public Map<String,Object> selectCheckProdChgCostSelOkStatus(NEDMPRO0500VO nEDMPRO0500VO, HttpServletRequest request) throws Exception;
	
	/**
	 * 원가변경요청 공문생성
	 * @param nEDMPRO0500VO
	 * @param request
	 * @return Map<String,Object>
	 * @throws Exception
	 */
	public Map<String,Object> insertCreDcDocProChgCost(NEDMPRO0500VO nEDMPRO0500VO, HttpServletRequest request) throws Exception;
	
	/**
	 * 원가변경요청 MD협의요청
	 * @param nEDMPRO0500VO
	 * @param request
	 * @return Map<String,Object>
	 * @throws Exception
	 */
	public Map<String,Object> insertReqMdProChgCost(NEDMPRO0500VO nEDMPRO0500VO, HttpServletRequest request) throws Exception;
	
}
