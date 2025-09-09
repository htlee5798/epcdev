package com.lottemart.epc.edi.srm.service;

import com.lottemart.epc.edi.srm.model.SRMEVL0010VO;

/**
 * 품질경영평가 / 품질경영평가 로그인 Service
 * 
 * @author SHIN SE JIN
 * @since 2016.07.08
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      			수정자           		수정내용
 *  -----------    	------------    ------------------
 *   2016.07.08  	SHIN SE JIN		 최초 생성
 *
 * </pre>
 */
public interface SRMEVL0010Service {
	
	/**
	 * 품질평가로그인 기 등록 확인
	 * @param SRMEVL0010VO
	 * @return SRMEVL0010VO
	 * @throws Exception
	 */
	public SRMEVL0010VO selectSRMEVLLogin(SRMEVL0010VO vo) throws Exception;
	
	/**
	 * 로그인 실패 시 카운트 증가
	 * @param SRMEVL0010VO
	 * @throws Exception
	 */
	public void updatePassCheckCnt(SRMEVL0010VO vo) throws Exception;
	
	/**
	 * 로그인 성공 시 카운트 초기화
	 * @param SRMEVL0010VO
	 * @throws Exception
	 */
	public void updatePassCheckCntReset(SRMEVL0010VO vo) throws Exception;
}
