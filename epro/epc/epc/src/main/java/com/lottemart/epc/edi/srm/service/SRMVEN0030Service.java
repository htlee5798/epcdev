package com.lottemart.epc.edi.srm.service;

import com.lottemart.epc.common.model.CommonFileVO;
import com.lottemart.epc.common.model.OptionTagVO;
import com.lottemart.epc.edi.srm.model.SRMVEN003001VO;
import com.lottemart.epc.edi.srm.model.SRMVEN0030VO;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SRM 모니터링 Service
 *
 * @author LEE HYOUNG TAK
 * @since 2016.08.18
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      			수정자           		수정내용
 *  -----------    	------------    ------------------
 *   2016.08.18  	LEE HYOUNG TAK		 최초 생성
 *
 * </pre>
 */
public interface SRMVEN0030Service {
	/**
	 * 대분류 코드 LIST 조회
	 * @throws Exception
	 */
	public List<OptionTagVO> selectCatLv1CodeList(SRMVEN0030VO vo) throws Exception;


	/**
	 * SRM 모니터링 LIST
	 * @param SRMVEN0030VO
	 * @param HttpServletRequest
	 * @return Map<String,Object>
	 * @throws Exception
	 */
	public Map<String,Object> selectSRMmoniteringList(SRMVEN0030VO vo, HttpServletRequest request) throws Exception;


	/**
	 * SRM 모니터링 상세조회
	 * @param SRMVEN003001VO
	 * @return List<SRMVEN003001VO>
	 * @throws Exception
	 */
	public List<SRMVEN003001VO> selectSRMmoniteringDetail(SRMVEN003001VO vo, HttpServletRequest request) throws Exception;

	/**
	 * SRM 모니터링 차트
	 * @param SRMVEN003001VO
	 * @return List<SRMVEN003001VO>
	 * @throws Exception
	 */
	public List<SRMVEN003001VO> selectSRMmoniteringDetailChart(SRMVEN003001VO vo) throws Exception;


	/**
	 * 현재까지 점수
	 * @param SRMVEN003001VO
	 * @return HashMap<String, List<SRMVEN003001VO>>
	 * @throws Exception
	 */
	public HashMap<String, List<SRMVEN003001VO>> selectSRMmoniteringDetailCurrentValue(SRMVEN003001VO vo) throws Exception;

	/**
	 * 고객컴플레인수
	 * @param SRMVEN003001VO
	 * @return String
	 * @throws Exception
	 */
	public String selectSRMmoniteringDetailClaim(SRMVEN003001VO vo) throws Exception;

	/**
	 * 등급조회
	 * @param SRMVEN003001VO
	 * @return SRMVEN003001VO
	 * @throws Exception
	 */
	public SRMVEN003001VO selectSRMmoniteringDetailGrade(SRMVEN003001VO vo) throws Exception;


	/**
	 * 비고, 특이사항 조회
	 * @param SRMVEN003001VO
	 * @return SRMVEN003001VO
	 * @throws Exception
	 */
	public SRMVEN003001VO selectSRMmoniteringDetailRemarkEtc(SRMVEN003001VO vo) throws Exception;

	/**
	 * 등급 예제 조회
	 * @param SRMVEN003001VO
	 * @return List<SRMVEN003001VO>
	 * @throws Exception
	 */
	public List<SRMVEN003001VO> selectSRMmoniteringDetailGradeExemple(SRMVEN003001VO vo) throws Exception;


	/**
	 * PLC등급 리스트 조회
	 * @param SRMVEN003001VO
	 * @return List<SRMVEN003001VO>
	 * @throws Exception
	 */
	public List<SRMVEN003001VO> selectSRMmoniteringDetailPlc(SRMVEN003001VO vo) throws Exception;


	/**
	 * 불량등록건수
	 * @param SRMVEN003001VO
	 * @return String
	 * @throws Exception
	 */
	public String selectSRMmoniteringDetailDefective(SRMVEN003001VO vo) throws Exception;

	/**
	 * 불량등록건수 리스트
	 * @param SRMVEN003001VO
	 * @return List<SRMVEN003001VO>
	 * @throws Exception
	 */
	public List<SRMVEN003001VO> selectSRMmoniteringDetailDefectiveList(SRMVEN003001VO vo, HttpServletRequest request) throws Exception;


	/**
	 * 개선조치
	 * @param SRMVEN003001VO
	 * @return SRMVEN003001VO
	 * @throws Exception
	 */
	public SRMVEN003001VO selectSRMmoniteringDetailImpReq(SRMVEN003001VO vo, HttpServletRequest request) throws Exception;


	/**
	 * 개선조치 등옥
	 * @param SRMVEN003001VO
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	public Map<String, Object> updateSRMmoniteringDetailImpReq(SRMVEN003001VO vo) throws Exception;

	/**
	 * 첨부파일 조회
	 * @param SRMVEN003001VO
	 * @return List<CommonFileVO>
	 * @throws Exception
	 */
	public List<CommonFileVO> selectSRMmoniteringDetailImpReqFileList(SRMVEN003001VO vo) throws Exception;
}
