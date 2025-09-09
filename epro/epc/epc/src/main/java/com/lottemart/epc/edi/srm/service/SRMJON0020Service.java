package com.lottemart.epc.edi.srm.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.lottemart.epc.edi.srm.model.SRMJON0020VO;


/**
 * 입점상담 / 입점상담신청  / 입점상당신청 로그인 Service
 * 
 * @author SHIN SE JIN
 * @since 2016.07.06
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      			수정자           		수정내용
 *  -----------    	------------    ------------------
 *   2016.07.06  	SHIN SE JIN		 최초 생성
 *
 * </pre>
 */
public interface SRMJON0020Service {
	
	/**
	 * 기등록여부 확인
	 * @param SRMSessionVO
	 * @return SRMSessionVO
	 * @throws Exception
	 */
	public SRMJON0020VO selectSRMJONLogin(SRMJON0020VO vo) throws Exception;

	/**
	 * 입점상담 신청 로그인(비밀번호 포함)
	 * @param SRMSessionVO
	 * @return SRMSessionVO
	 * @throws Exception
	 */
	public SRMJON0020VO selectSRMJONReLogin(SRMJON0020VO vo) throws Exception;
	
	/**
	 * 상담신청 리스트 조회 카운트
	 * @param SRMJON0020VO
	 * @return int
	 * @throws Exception
	 */
	public int selectCounselListCount(SRMJON0020VO vo) throws Exception;
	
	/**
	 * 상담신청 리스트 조회
	 * @param SRMSessionVO
	 * @return List<SRMCounselVO>
	 * @throws Exception
	 */
	public List<SRMJON0020VO> selectCounselList(SRMJON0020VO vo) throws Exception;
	
	/**
	 * 비밀번호 틀린 경우 카운트 증가
	 * @param SRMJON0020VO
	 * @throws Exception
	 */
	public void updatePassCheckCnt(SRMJON0020VO vo) throws Exception;
	
	/**
	 * 로그인 성공 시 카운트 초기화
	 * @param SRMJON0020VO
	 * @throws Exception
	 */
	public void updatePassCheckCntReset(SRMJON0020VO vo) throws Exception;
	
	/**
	 * 로그인 성공 시 세션 생성
	 * @param SRMJON0020VO
	 * @param request
	 * @throws Exception
	 */
	public void setSession(SRMJON0020VO vo, HttpServletRequest request) throws Exception;
	
	
	/**
	 * 사업자명/사업자 번호 찾기
	 * @param SRMJON0020VO
	 * @param request
	 * @throws Exception
	 */
	public SRMJON0020VO selectSRMJONSearch(SRMJON0020VO vo) throws Exception;
	
}
