package com.lottemart.epc.edi.srm.service;

import com.lottemart.epc.common.model.CommonFileVO;
import com.lottemart.epc.edi.srm.model.SRMEVL0040VO;
import com.lottemart.epc.edi.srm.model.SRMEVL0050VO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 품질경영평가  > 품질경영평가 평가완료  Service
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
 *   2016.07.29  	LEE HYOUNG TAK	최초 생성
 *
 * </pre>
 */
public interface SRMEVL0050Service {

	/**
	 * 첨부파일 조회
	 * @param SRMEVL0050VO
	 * @return List<CommonFileVO>
	 * @throws Exception
	 */
	public List<CommonFileVO> selectQualityEvaluationAttachFileList(SRMEVL0050VO vo) throws Exception;


	/**
	 * 점검요약 조회
	 * @param SRMEVL0050VO
	 * @return SRMEVL0050VO
	 * @throws Exception
	 */
	public SRMEVL0050VO selectQualityEvaluationSiteVisit1(SRMEVL0050VO vo, HttpServletRequest request) throws Exception;

	/**
	 * 참석자 조회
	 * @param SRMEVL0050VO
	 * @return List<SRMEVL0050VO>
	 * @throws Exception
	 */
	public List<SRMEVL0050VO> selectQualityEvaluationSiteVisit2(SRMEVL0050VO vo) throws Exception;

	/**
	 * 조치내역 조회
	 * @param SRMEVL0050VO
	 * @return List<SRMEVL0050VO>
	 * @throws Exception
	 */
	public List<SRMEVL0050VO> selectQualityEvaluationSiteVisit3(SRMEVL0050VO vo, HttpServletRequest request) throws Exception;

	/**
	 * 품질경영평가 평가완료
	 * @param SRMEVL0050VO
	 * @return
	 * @throws Exception
	 */
	public void updateQualityEvaluationComplete(SRMEVL0050VO vo, HttpServletRequest request) throws Exception;



	/**
	 * 상세내역 보기 팝업
	 * @param SRMEVL0050VO
	 * @return List<SRMEVL0050VO>
	 * @throws Exception
	 */
	public List<SRMEVL0050VO> selectQualityEvaluationSiteVisitDetailPopup(SRMEVL0050VO vo, HttpServletRequest request) throws Exception;


	/**
	 * 품질경영평가 Tab List
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<SRMEVL0040VO> selectEvlTabList(SRMEVL0050VO vo, HttpServletRequest request) throws Exception;



	/**
	 * 결과보고서 평가결과
	 * @param SRMEVL0050VO
	 * @return List<SRMEVL0050VO>
	 * @throws Exception
	 */
	public List<SRMEVL0050VO> selectQualityEvaluationSiteVisitResult(SRMEVL0050VO vo, HttpServletRequest request) throws Exception;
}
