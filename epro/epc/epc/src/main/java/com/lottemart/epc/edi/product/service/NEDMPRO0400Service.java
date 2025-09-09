package com.lottemart.epc.edi.product.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.lottemart.epc.edi.product.model.CommonProductVO;
import com.lottemart.epc.edi.product.model.NEDMPRO0020VO;
import com.lottemart.epc.edi.product.model.NEDMPRO0400VO;

public interface NEDMPRO0400Service {

	/**
	 * 입점 제안 등록 갯수
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int selectProposeStoreListCount(NEDMPRO0400VO vo, HttpServletRequest request) throws Exception;
	
	/**
	 * 입점제안 등록 리스트
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<NEDMPRO0400VO> selectProposeStoreList(NEDMPRO0400VO vo, HttpServletRequest request) throws Exception;
	
	/**
	 * 신규 입점 제안 정보 저장 
	 * @param nEDMPRO0020VO
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> insertNewPropStore(List<NEDMPRO0400VO> nEDMPRO0020VO , HttpServletRequest request, MultipartFile[] files) throws Exception;
	
	/**
	 * 등록된 이미지 상세보기
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public NEDMPRO0400VO selectDetailImgInfo(String param)throws Exception;

	/**
	 * 신규 입점 정보 삭제 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> deleteNewPropStore(List<NEDMPRO0400VO> vo) throws Exception;
	
	/**
	 * 신규 입점 제안 제안요청 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> updateNewPropStoreRequest(List<NEDMPRO0400VO> vo , HttpServletRequest request) throws Exception;

	/**
	 * 선택된 팀의 대분류 값 조회 
	 * @param paramMap
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public List<CommonProductVO> selectTempL1List(Map<String, Object> paramMap, HttpServletRequest request) throws Exception;
	
	public List<CommonProductVO> selectTempL1List2(CommonProductVO paramMap) throws Exception;


	/**
	 * 신상품입점제안 엑셀 리스트 조회 
	 * @param paramVo
	 * @return
	 * @throws Exception
	 */
	public List<HashMap<String, String>> selectNewPropStoreExcelInfo(NEDMPRO0400VO paramVo) throws Exception;
	
	/**
	 * 상품제안 채널 조회 (selectbox 구성용)
	 * @param paramMap
	 * @return List<Map<String,Object>>
	 * @throws Exception
	 */
	public List<Map<String,Object>> selectTpcNewPropChanCodes(Map<String,Object> paramMap) throws Exception;

	/**
	 * 상품제안 파일 STFP 전송 (EPC to PO)
	 * @param paramMap
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	public Map<String, Object> selectTpcProdNewPropFileForSend(Map<String,Object> paramMap) throws Exception;
}	
