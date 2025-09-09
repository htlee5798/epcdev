package com.lottemart.epc.edi.srm.service;

import com.lottemart.epc.edi.srm.model.SRMEVL0060VO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 품질경영평가 / 품질경영평가 대상 Service
 * 
 * @author LeeHyoungTak
 * @since 2016.07.11
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      			수정자           		수정내용
 *  -----------    	------------    ------------------
 *   2016.10.07     LEE HYOUNG TAK   추가
 * </pre>
 */
public interface SRMEVL0060Service {
    /**
     * 품질평가 list
     * @param SRMEVL0060VO
     * @param HttpServletRequest
     * @return Map<String,Object>
     * @throws Exception
     */
    public Map<String,Object> selectQualityEvaluationPeriodicList(SRMEVL0060VO vo, HttpServletRequest request) throws Exception;

    /**
     * 품질평가 list Excel
     * @param HttpServletRequest
     * @param SRMEVL0060VO
     * @return List<SRMEVL0060VO>
     * @throws Exception
     */
    public List<SRMEVL0060VO> selectQualityEvaluationListExcel(SRMEVL0060VO vo, HttpServletRequest request) throws Exception;


    /**
     * 파트너사 정보 조회
     * @param SRMEVL0060VO
     * @return SRMEVL0060VO
     * @throws Exception
     */
    public SRMEVL0060VO selectQualityEvaluationCompInfo(SRMEVL0060VO vo, HttpServletRequest request) throws Exception;
//
//    /**
//     * 현장점검일 등록/상태정보 수정
//     * @param SRMEVL0060VO
//     * @return Map<String,Object>
//     * @throws Exception
//     */
//    public Map<String,Object> updateQualityEvaluationCheckDate(SRMEVL0060VO vo, HttpServletRequest request) throws Exception;
}
