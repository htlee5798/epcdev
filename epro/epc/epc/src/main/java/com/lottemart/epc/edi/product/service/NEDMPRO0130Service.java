package com.lottemart.epc.edi.product.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.lottemart.epc.edi.product.model.NEDMPRO0130VO;

public interface NEDMPRO0130Service {

	/**
	 * Count
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int selectProductImageCount(NEDMPRO0130VO vo) throws Exception;

	/**
	 * List 조회
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<NEDMPRO0130VO> selectProductImageList(NEDMPRO0130VO vo) throws Exception;

	/**
	 * 사이즈 변경 예약
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> insertMDSizeReserv(NEDMPRO0130VO vo, HttpServletRequest request) throws Exception;

	/**
	 * 이미지 저장
	 * @param paramMap
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String insertSaleImgAllApply(Map<String, Object> paramMap, HttpServletRequest request) throws Exception;

	/**
	 * 상품 중량정보 가져오기
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public HashMap selectWgtInfo(Map<String, String> paramMap) throws Exception;

	public HashMap selectWgtResInfo(Map<String, String> paramMap) throws Exception;

	/**
	 * 상품 중량정보 RFC 요청
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public String requestWgtRfcCall(Map<String, String> paramMap) throws Exception;

	public String submitWgtInfo(NEDMPRO0130VO nedmpro0130VO) throws Exception;
}
