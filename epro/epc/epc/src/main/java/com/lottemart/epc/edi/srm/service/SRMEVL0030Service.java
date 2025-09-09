package com.lottemart.epc.edi.srm.service;

import com.lottemart.epc.edi.srm.model.SRMEVL0030VO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 품질경영평가 / 품질경영평가 대상 Service
 * 
 * @author SHIN SE JIN
 * @since 2016.07.11
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      			수정자           		수정내용
 *  -----------    	------------    ------------------
 *   2016.07.11  	SHIN SE JIN		 최초 생성
 *   2016.07.26     LEE HYOUNG TAK   수정
 * </pre>
 */
public interface SRMEVL0030Service {
    /**
     * 품질평가 list
     * @param SRMEVL0030VO
     * @param HttpServletRequest
     * @return Map<String,Object>
     * @throws Exception
     */
    public Map<String,Object> selectQualityEvaluationList(SRMEVL0030VO vo, HttpServletRequest request) throws Exception;

    /**
     * 품질평가 list Excel
     * @param HttpServletRequest
     * @param SRMEVL0030VO
     * @return List<SRMEVL0030VO>
     * @throws Exception
     */
    public List<SRMEVL0030VO> selectQualityEvaluationListExcel(SRMEVL0030VO vo, HttpServletRequest request) throws Exception;


    /**
     * 파트너사 정보 조회
     * @param SRMEVL0030VO
     * @return SRMEVL0030VO
     * @throws Exception
     */
    public SRMEVL0030VO selectQualityEvaluationCompInfo(SRMEVL0030VO vo, HttpServletRequest request) throws Exception;

    /**
     * 현장점검일 등록/상태정보 수정
     * @param SRMEVL0030VO
     * @return Map<String,Object>
     * @throws Exception
     */
    public Map<String,Object> updateQualityEvaluationCheckDate(SRMEVL0030VO vo, HttpServletRequest request) throws Exception;
}
