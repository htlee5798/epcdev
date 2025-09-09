package com.lottemart.epc.edi.srm.service;

import com.lottemart.epc.edi.srm.model.SRMJON0044VO;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


/**
 * 입점상담 / 입점상담신청  / 잠재업체 (해외)
 * 
 * @author LEE HYOUNG TAK
 * @since 2016.11.16
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      			수정자           		수정내용
 *  -----------    	------------    ---------------------------
 *   2016.11.16  	LEE HYOUNG TAK				 최초 생성
 *
 * </pre>
 */
public interface SRMJON0044Service {

    /**
     * 잠재업체 기본정보 조회
     * @param HttpServletRequest
     * @param SRMJON0044VO
     * @return SRMJON0044VO
     * @throws Exception
     */
    public SRMJON0044VO selectGlobalHiddenCompInfo(SRMJON0044VO vo, HttpServletRequest request) throws Exception;

    /**
     * 잠재업체 기본정보 체크
     * @param SRMJON0044VO
     * @return HashMap<String, String>
     * @throws Exception
     */
    public Map<String, Object> selectGlobalHiddenCompInfoCheck(SRMJON0044VO vo, HttpServletRequest request) throws Exception;

    /**
     * 잠재업체 기본정보 저장
     * @param SRMJON0044VO
     * @return HashMap<String, String>
     * @throws Exception
     */
    public Map<String, Object> updateGlobalHiddenCompInfo(SRMJON0044VO vo, HttpServletRequest request) throws Exception;

}
