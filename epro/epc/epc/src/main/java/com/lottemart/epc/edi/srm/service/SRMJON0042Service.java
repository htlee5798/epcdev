package com.lottemart.epc.edi.srm.service;

import com.lottemart.epc.edi.srm.model.SRMJON0042VO;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * 입점상담 / 입점상담신청  / 잠재업체 인증/신용정보
 * 
 * @author LEE HYOUNG TAK
 * @since 2016.07.19
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      			수정자           		수정내용
 *  -----------    	------------    ---------------------------
 *   2016.07.19  	LEE HYOUNG TAK				 최초 생성
 *
 * </pre>
 */
public interface SRMJON0042Service {
    /**
     * 잠재업체 신용정보 조회
     * @param SRMJON0042VO
     * @return SRMJON0042VO
     * @throws Exception
     */
    public SRMJON0042VO selectHiddenCompCreditInfo(SRMJON0042VO vo) throws Exception;

    /**
     * 잠재업체 신용정보 수정
     * @param SRMJON0042VO
     * @return HashMap<String, String>
     * @throws Exception
     */
    public HashMap<String, String> updateHiddenCompCreditInfo(SRMJON0042VO vo) throws Exception;
    
    /**
	 * 신용평가기관 정보
	 * @param vo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String selectCreditInfo(SRMJON0042VO vo, HttpServletRequest request) throws Exception;
	
	/**
     * 신용평가기관별 정보조회
     * @param SRMJON0042VO
     * @param HttpServletRequest
     * @return List<SRMJON0042VO>
     * @throws Exception
     */
    public List<SRMJON0042VO> selectCreditAllInfo(SRMJON0042VO vo, HttpServletRequest request) throws Exception;
}
