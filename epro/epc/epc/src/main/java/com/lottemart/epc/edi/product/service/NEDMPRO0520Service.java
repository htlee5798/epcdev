package com.lottemart.epc.edi.product.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.lottemart.epc.edi.product.model.NEDMPRO0520VO;


/**
 * 
 * @Class Name : NEDMPRO0520Service.java
 * @Description : ESG 인증관리 Service
 * @Modification Information
 * 
 *               <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      			수정자           		수정내용
 *  -------    		--------    	---------------------------
 * 	2025.03.28		yun				최초생성
 *               </pre>
 */
public interface NEDMPRO0520Service {
	
	/**
	 * ESG 인증리스트 조회
	 * @param nEDMPRO0520VO
	 * @param request
	 * @return Map<String,Object>
	 * @throws Exception
	 */
	public Map<String,Object> selectProdEsgList(NEDMPRO0520VO nEDMPRO0520VO, HttpServletRequest request) throws Exception;
	
	/**
	 * ESG 인증정보 변경
	 * @param nEDMPRO0520VO
	 * @param request
	 * @return Map<String,Object>
	 * @throws Exception
	 */
	public Map<String,Object> updateProdEsgInfo(NEDMPRO0520VO nEDMPRO0520VO, HttpServletRequest request) throws Exception;
	
	/**
	 * ESG 인증서첨부 (단건)
	 * @param nEDMPRO0520VO
	 * @param request
	 * @return Map<String,Object>
	 * @throws Exception
	 */
	public Map<String,Object> updateProdEsgFileInfo(NEDMPRO0520VO nEDMPRO0520VO, HttpServletRequest request) throws Exception;
	
	/**
	 * ESG 인증정보 변경 (파일 업로드 동시에 진행)
	 * @param nEDMPRO0520VO
	 * @param request
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	public Map<String, Object> updateProdEsgInfoWithFile(NEDMPRO0520VO nEDMPRO0520VO, HttpServletRequest request) throws Exception;
	
	/**
	 * ESG 인증정보 변경 요청 (Proxy 전송)
	 * @param nEDMPRO0520VO
	 * @param request
	 * @return Map<String,Object>
	 * @throws Exception
	 */
	public Map<String, Object> updateProdEsgSendProxy(NEDMPRO0520VO nEDMPRO0520VO, HttpServletRequest request) throws Exception;
	
	/**
	 * ESG 파일 STFP 전송 (EPC to PO)	
	 * @param nEDMPRO0520VO
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	public Map<String, Object> updateSendSftpEsgFile(NEDMPRO0520VO nEDMPRO0520VO) throws Exception;
}
