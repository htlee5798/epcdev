package com.lottemart.epc.edi.srm.dao;

import com.lottemart.epc.edi.srm.model.SRMEVL0030VO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 품질경영평가  / 품질경영평가 대상 Dao
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
 *   2016.07.06  	SHIN SE JIN		 최초 생성
 *   2016.07.26     LEE HYOUNG TAK   수정
 *
 * </pre>
 */
@Repository("srmevl0030Dao")
public class SRMEVL0030Dao extends SRMDBConnDao {
    /**
     * 품질평가 list count
     * @param SRMEVL0030VO
     * @return int
     * @throws Exception
     */
    public int selectQualityEvaluationListCount(SRMEVL0030VO vo) throws Exception {
        return (Integer)queryForInteger("SRMEVL0030.selectQualityEvaluationListCount", vo);
    }

    /**
     * 품질평가 list
     * @param SRMEVL0030VO
     * @return List<SRMEVL0030VO>
     * @throws Exception
     */
    public List<SRMEVL0030VO> selectQualityEvaluationList(SRMEVL0030VO vo) throws Exception {
        return queryForList("SRMEVL0030.selectQualityEvaluationList", vo);
    }

    /**
     * 품질평가 list Excel
     * @param SRMEVL0030VO
     * @return List<SRMEVL0030VO>
     * @throws Exception
     */
    public List<SRMEVL0030VO> selectQualityEvaluationListExcel(SRMEVL0030VO vo) throws Exception {
        return queryForList("SRMEVL0030.selectQualityEvaluationListExcel", vo);
    }

    /**
     * 파트너사 정보 조회
     * @param SRMEVL0030VO
     * @return SRMEVL0030VO
     * @throws Exception
     */
    public SRMEVL0030VO selectQualityEvaluationCompInfo(SRMEVL0030VO vo) throws Exception {
        return (SRMEVL0030VO)queryForObject("SRMEVL0030.selectQualityEvaluationCompInfo", vo);
    }

    /**
     * SSUGL_PROCESS_SITEVISIT 현장점검일 UPDATE
     * @param SRMEVL0030VO
     * @throws Exception
     */
    public void updateQualityEvaluationSITEVISITCheckDate(SRMEVL0030VO vo) throws Exception {
        update("SRMEVL0030.updateQualityEvaluationSITEVISITCheckDate", vo);
    }

    /**
     * SSUGL_PROCESS_SITEVISIT 상태값 UPDATE
     * @param SRMEVL0030VO
     * @throws Exception
     */
    public void updateQualityEvaluationSITEVISITStatus(SRMEVL0030VO vo) throws Exception {
        update("SRMEVL0030.updateQualityEvaluationSITEVISITStatus", vo);
    }

    /**
     * SEVEM 상태값 UPDATE
     * @param SRMEVL0030VO
     * @throws Exception
     */
    public void updateQualityEvaluationSEVEMStatus(SRMEVL0030VO vo) throws Exception {
        update("SRMEVL0030.updateQualityEvaluationSEVEMStatus", vo);
    }

    /**
     * SEVEU 상태값 UPDATE
     * @param SRMEVL0030VO
     * @throws Exception
     */
    public void updateQualityEvaluationSEVEUStatus(SRMEVL0030VO vo) throws Exception {
        update("SRMEVL0030.updateQualityEvaluationSEVEUStatus", vo);
    }

    /**
     * SEVUS 상태값 UPDATE
     * @param SRMEVL0030VO
     * @throws Exception
     */
    public void updateQualityEvaluationSEVUSStatus(SRMEVL0030VO vo) throws Exception {
        update("SRMEVL0030.updateQualityEvaluationSEVUSStatus", vo);
    }

    /**
     * SEVES 상태값 UPDATE
     * @param SRMEVL0030VO
     * @throws Exception
     */
    public void updateQualityEvaluationSEVESStatus(SRMEVL0030VO vo) throws Exception {
        update("SRMEVL0030.updateQualityEvaluationSEVESStatus", vo);
    }

    /**
     * SSUGL_PROCESS_MAIN 상태값 UPDATE
     * @param SRMEVL0030VO
     * @throws Exception
     */
    public void updateQualityEvaluationMAINStatus(SRMEVL0030VO vo) throws Exception {
        update("SRMEVL0030.updateQualityEvaluationMAINStatus", vo);
    }

    /**
     * 품질평가 list count
     * @param SRMEVL0030VO
     * @return int
     * @throws Exception
     */
    public SRMEVL0030VO selectQualityEvaluationEMSInfo(SRMEVL0030VO vo) throws Exception {
        return (SRMEVL0030VO)queryForObject("SRMEVL0030.selectQualityEvaluationEMSInfo", vo);
    }


}




