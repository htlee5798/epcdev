package com.lottemart.epc.edi.srm.dao;

import com.lottemart.epc.edi.srm.model.SRMEVL0060VO;
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
@Repository("SRMEVL0060Dao")
public class SRMEVL0060Dao extends SRMDBConnDao {
    /**
     * 품질평가 list count
     * @param SRMEVL0060VO
     * @return int
     * @throws Exception
     */
    public int selectQualityEvaluationPeriodicListCount(SRMEVL0060VO vo) throws Exception {
        return (Integer)queryForInteger("SRMEVL0060.selectQualityEvaluationPeriodicListCount", vo);
    }

    /**
     * 품질평가 list
     * @param SRMEVL0060VO
     * @return List<SRMEVL0060VO>
     * @throws Exception
     */
    public List<SRMEVL0060VO> selectQualityEvaluationPeriodicList(SRMEVL0060VO vo) throws Exception {
        return queryForList("SRMEVL0060.selectQualityEvaluationPeriodicList", vo);
    }

    /**
     * 품질평가 list Excel
     * @param SRMEVL0060VO
     * @return List<SRMEVL0060VO>
     * @throws Exception
     */
    public List<SRMEVL0060VO> selectQualityEvaluationPeriodicListExcel(SRMEVL0060VO vo) throws Exception {
        return queryForList("SRMEVL0060.selectQualityEvaluationPeriodicListExcel", vo);
    }

    /**
     * 파트너사 정보 조회
     * @param SRMEVL0060VO
     * @return SRMEVL0060VO
     * @throws Exception
     */
    public SRMEVL0060VO selectQualityEvaluationCompInfo(SRMEVL0060VO vo) throws Exception {
        return (SRMEVL0060VO)queryForObject("SRMEVL0060.selectQualityEvaluationCompInfo", vo);
    }

    /**
     * SSUGL_PROCESS_SITEVISIT 현장점검일 UPDATE
     * @param SRMEVL0060VO
     * @throws Exception
     */
    public void updateQualityEvaluationSITEVISITCheckDate(SRMEVL0060VO vo) throws Exception {
        update("SRMEVL0060.updateQualityEvaluationSITEVISITCheckDate", vo);
    }

    /**
     * SSUGL_PROCESS_SITEVISIT 상태값 UPDATE
     * @param SRMEVL0060VO
     * @throws Exception
     */
    public void updateQualityEvaluationSITEVISITStatus(SRMEVL0060VO vo) throws Exception {
        update("SRMEVL0060.updateQualityEvaluationSITEVISITStatus", vo);
    }

    /**
     * SEVEM 상태값 UPDATE
     * @param SRMEVL0060VO
     * @throws Exception
     */
    public void updateQualityEvaluationSEVEMStatus(SRMEVL0060VO vo) throws Exception {
        update("SRMEVL0060.updateQualityEvaluationSEVEMStatus", vo);
    }

    /**
     * SEVEU 상태값 UPDATE
     * @param SRMEVL0060VO
     * @throws Exception
     */
    public void updateQualityEvaluationSEVEUStatus(SRMEVL0060VO vo) throws Exception {
        update("SRMEVL0060.updateQualityEvaluationSEVEUStatus", vo);
    }

    /**
     * SEVUS 상태값 UPDATE
     * @param SRMEVL0060VO
     * @throws Exception
     */
    public void updateQualityEvaluationSEVUSStatus(SRMEVL0060VO vo) throws Exception {
        update("SRMEVL0060.updateQualityEvaluationSEVUSStatus", vo);
    }

    /**
     * SEVES 상태값 UPDATE
     * @param SRMEVL0060VO
     * @throws Exception
     */
    public void updateQualityEvaluationSEVESStatus(SRMEVL0060VO vo) throws Exception {
        update("SRMEVL0060.updateQualityEvaluationSEVESStatus", vo);
    }

    /**
     * 품질평가 list count
     * @param SRMEVL0060VO
     * @return int
     * @throws Exception
     */
    public SRMEVL0060VO selectQualityEvaluationEMSInfo(SRMEVL0060VO vo) throws Exception {
        return (SRMEVL0060VO)queryForObject("SRMEVL0060.selectQualityEvaluationEMSInfo", vo);
    }


}




