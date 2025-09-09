package com.lottemart.epc.edi.srm.service;

import com.lottemart.epc.common.model.CommonFileVO;
import com.lottemart.epc.common.model.OptionTagVO;
import com.lottemart.epc.edi.srm.model.SRMMNT002001VO;
import com.lottemart.epc.edi.srm.model.SRMMNT0020VO;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 대표자 SRM 모니터링 Service
 *
 * @author LEE HYOUNG TAK
 * @since 2016.08.25
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      			수정자           		수정내용
 *  -----------    	------------    ------------------
 *   2016.08.25  	LEE HYOUNG TAK		 최초 생성
 *
 * </pre>
 */
public interface SRMMNT0020Service {
	/**
	 * 대분류 코드 LIST 조회
	 * @throws Exception
	 */
	public List<OptionTagVO> selectCatLv1CodeList(SRMMNT0020VO vo) throws Exception;


	/**
	 * SRM 모니터링 LIST
	 * @param SRMMNT0020VO
	 * @param HttpServletRequest
	 * @return Map<String,Object>
	 * @throws Exception
	 */
	public Map<String,Object> selectSRMmoniteringList(SRMMNT0020VO vo, HttpServletRequest request) throws Exception;


	/**
	 * SRM 모니터링 상세조회
	 * @param SRMMNT002001VO
	 * @return List<SRMMNT002001VO>
	 * @throws Exception
	 */
	public List<SRMMNT002001VO> selectSRMmoniteringDetail(SRMMNT002001VO vo, HttpServletRequest request) throws Exception;

	/**
	 * SRM 모니터링 차트
	 * @param SRMMNT002001VO
	 * @return List<SRMMNT002001VO>
	 * @throws Exception
	 */
	public List<SRMMNT002001VO> selectSRMmoniteringDetailChart(SRMMNT002001VO vo) throws Exception;


	/**
	 * 사업자 번호로 업체코드 조회
	 * @param String
	 * @return List<SRMMNT0020VO>
	 * @throws Exception
	 */
	public List<SRMMNT0020VO> selectCeoLoginVenCdLIst(String irsNo) throws Exception;


	/**
	 * 현재까지 점수
	 * @param SRMMNT002001VO
	 * @return HashMap<String, List<SRMMNT002001VO>>
	 * @throws Exception
	 */
	public HashMap<String, List<SRMMNT002001VO>> selectSRMmoniteringDetailCurrentValue(SRMMNT002001VO vo) throws Exception;

	/**
	 * 고객컴플레인수
	 * @param SRMMNT002001VO
	 * @return String
	 * @throws Exception
	 */
	public String selectSRMmoniteringDetailClaim(SRMMNT002001VO vo) throws Exception;

	/**
	 * 등급조회
	 * @param SRMMNT002001VO
	 * @return SRMMNT002001VO
	 * @throws Exception
	 */
	public SRMMNT002001VO selectSRMmoniteringDetailGrade(SRMMNT002001VO vo) throws Exception;


	/**
	 * 비고, 특이사항 조회
	 * @param SRMMNT002001VO
	 * @return SRMMNT002001VO
	 * @throws Exception
	 */
	public SRMMNT002001VO selectSRMmoniteringDetailRemarkEtc(SRMMNT002001VO vo) throws Exception;

	/**
	 * 등급 예제 조회
	 * @param SRMMNT002001VO
	 * @return List<SRMMNT002001VO>
	 * @throws Exception
	 */
	public List<SRMMNT002001VO> selectSRMmoniteringDetailGradeExemple(SRMMNT002001VO vo) throws Exception;

	/**
	 * PLC등급 리스트 조회
	 * @param SRMMNT002001VO
	 * @return List<SRMMNT002001VO>
	 * @throws Exception
	 */
	public List<SRMMNT002001VO> selectSRMmoniteringDetailPlc(SRMMNT002001VO vo) throws Exception;

	/**
	 * 불량등록건수
	 * @param SRMVEN003001VO
	 * @return String
	 * @throws Exception
	 */
	public String selectSRMmoniteringDetailDefective(SRMMNT002001VO vo) throws Exception;

	/**
	 * 불량등록건수 리스트
	 * @param SRMMNT002001VO
	 * @return List<SRMVEN003001VO>
	 * @throws Exception
	 */
	public List<SRMMNT002001VO> selectSRMmoniteringDetailDefectiveList(SRMMNT002001VO vo, HttpServletRequest request) throws Exception;

	/**
	 * 개선조치
	 * @param SRMMNT002001VO
	 * @return SRMVEN003001VO
	 * @throws Exception
	 */
	public SRMMNT002001VO selectSRMmoniteringDetailImpReq(SRMMNT002001VO vo, HttpServletRequest request) throws Exception;

	/**
	 * 첨부파일 조회
	 * @param SRMMNT002001VO
	 * @return List<CommonFileVO>
	 * @throws Exception
	 */
	public List<CommonFileVO> selectSRMmoniteringDetailImpReqFileList(SRMMNT002001VO vo) throws Exception;
}
