package com.lottemart.epc.edi.srm.service;

import com.lottemart.epc.edi.srm.model.SRMJON0043VO;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * 입점상담 / 입점상담신청  / 잠재업체 정보확인
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
public interface SRMJON0043Service {
    /**
     * 잠재업체 신용정보 조회
     * @param SRMJON0043VO
     * @return SRMJON0043VO
     * @throws Exception
     */
    public SRMJON0043VO selectHiddenComp(SRMJON0043VO vo, HttpServletRequest request) throws Exception;

    /**
     * 잠재업체 입점상담신청 수정
     * @param SRMJON0043VO
     * @return HashMap<String, String>
     * @throws Exception
     */
    public HashMap<String, String> updateHiddenCompReq(SRMJON0043VO vo, HttpServletRequest request) throws Exception;

    /**
     * 입점상담신청 취소
     * @param SRMJON0043VO
     * @return HashMap<String, String>
     * @throws Exception
     */
    public HashMap<String, String> updateHiddenCompReqCancel(SRMJON0043VO vo, HttpServletRequest request) throws Exception;
}
