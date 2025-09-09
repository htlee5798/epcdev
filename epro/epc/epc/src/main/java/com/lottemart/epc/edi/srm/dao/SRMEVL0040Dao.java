package com.lottemart.epc.edi.srm.dao;

import com.lottemart.epc.edi.srm.model.SRMEVL0040VO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 품질경영평가  > 품질경영평가  Dao
 * 
 * @author AN TAE KYUNG
 * @since 2016.07.19
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *  수정일 				수정자				수정내용
 *  -----------    	------------    ------------------
 *   2016.07.19  	AN TAE KYUNG	최초 생성
 *
 * </pre>
 */
@Repository("srmevl0040Dao")
public class SRMEVL0040Dao extends SRMDBConnDao {

	/**
	 * 품질경영평가 Tab List
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<SRMEVL0040VO> selectEvlTabList(SRMEVL0040VO vo) throws Exception {
		return queryForList("SRMEVL0040.selectEvlTabList", vo);
	}
	
	/**
	 * 품질경영평가 Item
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<SRMEVL0040VO> selectEvlItem(SRMEVL0040VO vo) throws Exception {
		return queryForList("SRMEVL0040.selectEvlItem", vo);
	}

	/**
	 * 품질경영평가 결과 체크
	 * @param SRMEVL0040VO
	 * @return List<SRMEVL0030VO>
	 * @throws Exception
	 */
	public int selectQualityEvaluationCheck(SRMEVL0040VO vo) throws Exception {
		return (Integer)queryForInteger("SRMEVL0040.selectQualityEvaluationCheck", vo);
	}

	/**
	 * 품질경영평가 결과 등록
	 * @param SRMEVL0040VO
	 * @throws Exception
	 */
	public void insertQualityEvaluation(SRMEVL0040VO vo) throws Exception {
		insert("SRMEVL0040.insertQualityEvaluation", vo);
	}

	/**
	 * 품질경영평가 결과 수정
	 * @param SRMEVL0040VO
	 * @throws Exception
	 */
	public void updateQualityEvaluation(SRMEVL0040VO vo) throws Exception {
		update("SRMEVL0040.updateQualityEvaluation", vo);
	}

	/**
	 * 평가시작일 check
	 * @param SRMEVL0040VO
	 * @return String
	 * @throws Exception
	 */
	public String selectQualityEvaluationEvalStartDateCheck(SRMEVL0040VO vo) throws Exception {
		return (String)queryForString("SRMEVL0040.selectQualityEvaluationEvalStartDateCheck", vo);
	}

	/**
	 * 평가시작일 등록
	 * @param SRMEVL0040VO
	 * @throws Exception
	 */
	public void updateQualityEvaluationEvalStartDate(SRMEVL0040VO vo) throws Exception {
		update("SRMEVL0040.updateQualityEvaluationEvalStartDate", vo);
	}


	/**
	 * 품질평가 모두 입력 여부 Check
	 * @param SRMEVL0040VO
	 * @return String
	 * @throws Exception
	 */
	public int selectQualityEvaluationEvalCheck(SRMEVL0040VO vo) throws Exception {
		return (Integer)queryForInteger("SRMEVL0040.selectQualityEvaluationEvalCheck", vo);
	}

	/**
	 * 평가 총점 등록
	 * @param SRMEVL0040VO
	 * @throws Exception
	 */
	public void updateQualityEvaluationEvScore(SRMEVL0040VO vo) throws Exception {
		update("SRMEVL0040.updateQualityEvaluationEvScore", vo);
	}

}






