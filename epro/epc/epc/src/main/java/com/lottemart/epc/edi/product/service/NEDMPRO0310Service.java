package com.lottemart.epc.edi.product.service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.lottemart.epc.edi.product.model.NEDMPRO0310VO;


public interface NEDMPRO0310Service {
	
	/**
	 * 파트너사 행사 신청 상세 헤더 조회
	 * @param map
	 * @return List<NEDMPRO0310VO>
	 * @throws Exception
	 */
	public NEDMPRO0310VO selectProEventAppDetail(NEDMPRO0310VO paramVo) throws Exception;
	
	/**
	 * 파트너사 행사 신청 아이템 조회
	 * @param paramVo
	 * @return List<NEDMPRO0310VO>
	 * @throws Exception
	 */
	public List<NEDMPRO0310VO> selectProEventAppItemList(NEDMPRO0310VO paramVo) throws Exception;
	
	/**
	 * 파트너사 행사 정보 저장
	 * @param paramVo
	 * @return HashMap<String,Object>
	 * @throws Exception
	 */
	public HashMap<String,Object> insertProEventApp(NEDMPRO0310VO paramVo, HttpServletRequest request) throws Exception;
	
	/**
	 * 파트너사 행사 정보 삭제
	 * @param paramVo
	 * @param request
	 * @return HashMap<String,Object>
	 * @throws Exception
	 */
	public HashMap<String,Object> deleteProEventApp(NEDMPRO0310VO paramVo, HttpServletRequest request) throws Exception;
	
	/**
	 * 파트너사 행사 itme 정보 삭제
	 * @param paramVo
	 * @param request
	 * @return HashMap<String,Object>
	 * @throws Exception
	 */
	public HashMap<String,Object> deleteProEventAppItem(NEDMPRO0310VO paramVo, HttpServletRequest request) throws Exception;
	
	/**
	 * 파트너사 행사 정보 ECO 전송
	 * @param paramVo
	 * @param request
	 * @return HashMap<String,Object>
	 * @throws Exception
	 */
	public Map<String, Object> insertProEventAppRfcCall(NEDMPRO0310VO paramVo, HttpServletRequest request) throws Exception;
	
	/**
	 * 판매바코드 조회
	 * @param paramVo
	 * @return HashMap<String,Object>
	 * @throws Exception
	 */
	public HashMap<String,Object> selectSaleBarcodeList(NEDMPRO0310VO paramVo) throws Exception;
	
	/**
	 * 공문서 작성 요청
	 * @param paramVo
	 * @return Map<String,Object>
	 * @throws Exception
	 */
	public Map<String,Object> insertProdEcsIntgr(NEDMPRO0310VO paramVo) throws Exception;
	
	/**
	 * 행사 테마 번호 조회
	 * @param paramVo
	 * @return HashMap<String,Object>
	 * @throws Exception
	 */
	public HashMap<String,Object> selectProdEvntThmList(NEDMPRO0310VO paramVo) throws Exception;
	
	/**
	 * 팀 정보 조회
	 * @param paramVo
	 * @return HashMap<String,Object>
	 * @throws Exception
	 */
	public HashMap<String,Object> selectDepCdList(NEDMPRO0310VO paramVo) throws Exception;
}

