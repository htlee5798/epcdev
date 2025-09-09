package com.lottemart.epc.edi.srm.dao;

import com.lottemart.epc.common.model.CommonFileVO;
import com.lottemart.epc.edi.srm.model.SRMEVL0050VO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 품질경영평가  > 품질경영평가 평가완료  Dao
 * 
 * @author LEE HYOUNG TAK
 * @since 2016.07.29
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *  수정일 				수정자				수정내용
 *  -----------    	------------    ------------------
 *   2016.07.29 	LEE HYOUNG TAK	최초 생성
 *
 * </pre>
 */
@Repository("srmevl0050Dao")
public class SRMEVL0050Dao extends SRMDBConnDao {


    /**
     * 첨부파일 조회
     * @param SRMEVL0050VO
     * @return List<CommonFileVO>
     * @throws Exception
     */
    public List<CommonFileVO> selectQualityEvaluationAttachFileList(SRMEVL0050VO vo) throws Exception {
        return queryForList("SRMEVL0050.selectQualityEvaluationAttachFileList", vo);
    }

    /**
     * 파일정보 등록(SSVEM)
     * @param String
     * @return List<CommonFileVO>
     * @throws Exception
     */
    public void updateQualityEvaluationFile(SRMEVL0050VO vo) throws Exception {
        update("SRMEVL0050.updateQualityEvaluationFile", vo);
    }

    /**
     * 파일정보 조회
     * @param String
     * @return List<CommonFileVO>
     * @throws Exception
     */
    public List<CommonFileVO> selectQualityEvaluationFileList(String fileId) throws Exception {
        return queryForListParamString("SRMEVL0050.selectQualityEvaluationFileList", fileId);
    }




    /**
     * 파일정보 등록
     * @param CommonFileVO
     * @throws Exception
     */
    public void insertQualityEvaluationFile(CommonFileVO vo) throws Exception {
        insert("SRMEVL0050.insertQualityEvaluationFile", vo);
    }

    /**
     * 점검요약 조회
     * @param SRMEVL0050VO
     * @return SRMEVL0050VO
     * @throws Exception
     */
    public SRMEVL0050VO selectQualityEvaluationSiteVisit1(SRMEVL0050VO vo) throws Exception {
        return (SRMEVL0050VO)queryForObject("SRMEVL0050.selectQualityEvaluationSiteVisit1", vo);
    }

    /**
     * 점검요약 등록
     * @param SRMEVL0050VO
     * @throws Exception
     */
    public void insertQualityEvaluationSiteVisit1(SRMEVL0050VO vo) throws Exception {
        insert("SRMEVL0050.insertQualityEvaluationSiteVisit1", vo);
    }

    /**
     * 점검요약 삭제
     * @param SRMEVL0050VO
     * @throws Exception
     */
    public void deleteQualityEvaluationSiteVisit1(SRMEVL0050VO vo) throws Exception {
        insert("SRMEVL0050.deleteQualityEvaluationSiteVisit1", vo);
    }

    /**
     * 참석자 조회
     * @param SRMEVL0050VO
     * @return SRMEVL0050VO
     * @throws Exception
     */
    public List<SRMEVL0050VO> selectQualityEvaluationSiteVisit2(SRMEVL0050VO vo) throws Exception {
        return queryForList("SRMEVL0050.selectQualityEvaluationSiteVisit2", vo);
    }

    /**
     * 참석자 등록
     * @param SRMEVL0050VO
     * @throws Exception
     */
    public void insertQualityEvaluationSiteVisit2(SRMEVL0050VO vo) throws Exception {
        insert("SRMEVL0050.insertQualityEvaluationSiteVisit2", vo);
    }

    /**
     * 참석자 삭제
     * @param SRMEVL0050VO
     * @throws Exception
     */
    public void deleteQualityEvaluationSiteVisit2(SRMEVL0050VO vo) throws Exception {
        insert("SRMEVL0050.deleteQualityEvaluationSiteVisit2", vo);
    }

    /**
     * 조치내역 조회
     * @param SRMEVL0050VO
     * @return SRMEVL0050VO
     * @throws Exception
     */
    public List<SRMEVL0050VO> selectQualityEvaluationSiteVisit3(SRMEVL0050VO vo) throws Exception {
        return queryForList("SRMEVL0050.selectQualityEvaluationSiteVisit3", vo);
    }

    /**
     * 조치내역 등록
     * @param SRMEVL0050VO
     * @throws Exception
     */
    public void insertQualityEvaluationSiteVisit3(SRMEVL0050VO vo) throws Exception {
        insert("SRMEVL0050.insertQualityEvaluationSiteVisit3", vo);
    }

    /**
     * 조치내역 삭제
     * @param SRMEVL0050VO
     * @throws Exception
     */
    public void deleteQualityEvaluationSiteVisit3(SRMEVL0050VO vo) throws Exception {
        insert("SRMEVL0050.deleteQualityEvaluationSiteVisit3", vo);
    }

    /**
     * 상세내역 보기 팝업
     * @param SRMEVL0050VO
     * @return List<SRMEVL0050VO>
     * @throws Exception
     */
    public List<SRMEVL0050VO> selectQualityEvaluationSiteVisitDetailPopup(SRMEVL0050VO vo) throws Exception {
        return queryForList("SRMEVL0050.selectQualityEvaluationSiteVisitDetailPopup", vo);
    }

    /**
     * 결과보고서 평가결과
     * @param SRMEVL0050VO
     * @return List<SRMEVL0050VO>
     * @throws Exception
     */
    public List<SRMEVL0050VO> selectQualityEvaluationSiteVisitResult(SRMEVL0050VO vo) throws Exception {
        return queryForList("SRMEVL0050.selectQualityEvaluationSiteVisitResult", vo);
    }


    /**
     * 품질경영평가 담당 email
     * @param SRMEVL0050VO
     * @return SRMEVL0050VO
     * @throws Exception
     */
    public SRMEVL0050VO selectQualityEvaluationSiteVisitEmailList(SRMEVL0050VO vo) throws Exception {
        return (SRMEVL0050VO)queryForObject("SRMEVL0050.selectQualityEvaluationSiteVisitEmailList", vo);
    }


}




