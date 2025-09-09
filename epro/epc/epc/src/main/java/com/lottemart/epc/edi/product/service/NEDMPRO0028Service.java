package com.lottemart.epc.edi.product.service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.lottemart.epc.common.model.CommonFileVO;
import com.lottemart.epc.edi.product.model.NEDMPRO0028VO;


public interface NEDMPRO0028Service {
	
	
	/**
	 * ESG 등록 항목 조회
	 * @param paramVo
	 * @return List<NEDMPRO0028VO>
	 * @throws Exception
	 */
	public HashMap<String,Object>  selectNewProdEsgList(NEDMPRO0028VO paramVo) throws Exception;
	
	/**
	 * 대분류(유형) 조회
	 * @param paramVo
	 * @return List<NEDMPRO0028VO>
	 * @throws Exception
	 */
	public List<NEDMPRO0028VO>  selectEsgMstlList(NEDMPRO0028VO paramVo) throws Exception;
	
	
	/**
	 * 중분류(인증유형) 조회
	 * @param paramVo
	 * @return List<NEDMPRO0028VO>
	 * @throws Exception
	 */
	public List<NEDMPRO0028VO>  selectEsgMstMList(NEDMPRO0028VO paramVo) throws Exception;
	
	
	/**
	 * 소분류(인증상세유형) 조회
	 * @param paramVo
	 * @return List<NEDMPRO0028VO>
	 * @throws Exception
	 */
	public List<NEDMPRO0028VO>  selectEsgMstSList(NEDMPRO0028VO paramVo) throws Exception;
	
	
	
	/**
	 * ESG 항목 등록( ESG 상품 - 적용 )
	 * @param paramVo
	 * @param request
	 * @return HashMap<String,Object>
	 * @throws Exception
	 */
	public HashMap<String,Object> insertNewProdEsg(MultipartHttpServletRequest multipartRequest, NEDMPRO0028VO paramVo, HttpServletRequest request) throws Exception;
	
	/**
	 * ESG 항목 등록( ESG 상품 - 미적용 )
	 * @param paramVo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public HashMap<String,Object> updateNewProdEsg(NEDMPRO0028VO paramVo, HttpServletRequest request) throws Exception;
	
	/**
	 * ESG 항목 삭제
	 * @param paramVo
	 * @param request
	 * @return HashMap<String,Object>
	 * @throws Exception
	 */
	public HashMap<String,Object> deleteNewProdEsg(NEDMPRO0028VO paramVo, HttpServletRequest request) throws Exception;
	
	/**
	 * 다운로드 파일 조회
	 * @param paramVo
	 * @return CommonFileVO
	 * @throws Exception
	 */
	public CommonFileVO selectProdEsgFile(CommonFileVO paramVo) throws Exception;

	
	/**
	 * ESG 인증서첨부 (단건)
	 * @param nEDMPRO0028VO
	 * @param request
	 * @return Map<String,Object>
	 * @throws Exception
	 */
	public Map<String,Object> updateProdEsgFileInfo(NEDMPRO0028VO nEDMPRO0028VO, HttpServletRequest request) throws Exception;

	/**
	 * ESG 인증정보 저장 (파일 업로드 동시에 진행)
	 * @param nEDMPRO0028VO
	 * @param request
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	public Map<String, Object> updateNewProdEsgInfoWithFile(NEDMPRO0028VO nEDMPRO0028VO, HttpServletRequest request) throws Exception;

}

