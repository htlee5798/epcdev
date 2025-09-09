package com.lottemart.epc.edi.product.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.lottemart.epc.edi.product.model.NEDMPRO0530VO;

/**
 * 
 * @Class Name : NEDMPRO0530Service.java
 * @Description : 채널확장
 * @Modification Information
 * 
 *               <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      			수정자           		수정내용
 *  -------    		--------    	---------------------------
 * 	2025.07.31		yun				최초생성
 *               </pre>
 */
public interface NEDMPRO0530Service {
	
	/**
	 * 확장가능 상품내역 리스트 조회 jqGrid
	 * @param paramVo
	 * @param request
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	public Map<String,Object> selectExtAvailProdList(NEDMPRO0530VO paramVo, HttpServletRequest request) throws Exception;
	
	/**
	 * 등록된 확장요청 정보 조회 (TAB 구성용)
	 * @param paramVo
	 * @return List<NEDMPRO0530VO> 
	 * @throws Exception
	 */
	public List<NEDMPRO0530VO> selectExtTabList(NEDMPRO0530VO paramVo) throws Exception;
	
	/**
	 * 채널확장 상세정보 조회
	 * @param paramVo
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	public Map<String,Object> selectTpcProdChanExtendDetailInfo(NEDMPRO0530VO paramVo) throws Exception;
	
	/**
	 * 채널확장정보 등록
	 * @param nEDMPRO0530VO
	 * @param request
	 * @return int
	 * @throws Exception
	 */
	public Map<String,Object> updateTpcProdChanExtendDetailInfo(NEDMPRO0530VO nEDMPRO0530VO, HttpServletRequest request) throws Exception;
	
	/**
	 * 채널확장 요청 전송
	 * @param nEDMPRO0530VO
	 * @param request
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	public Map<String,Object> insertRequestProdChanExtendInfo(NEDMPRO0530VO nEDMPRO0530VO, HttpServletRequest request) throws Exception;
	
	/**
	 * 확장요청정보 삭제
	 * @param nEDMPRO0530VO
	 * @param request
	 * @return Map<String,Object>
	 * @throws Exception
	 */
	public Map<String,Object> deleteTpcExtProdReg(NEDMPRO0530VO nEDMPRO0530VO, HttpServletRequest request) throws Exception;
}
