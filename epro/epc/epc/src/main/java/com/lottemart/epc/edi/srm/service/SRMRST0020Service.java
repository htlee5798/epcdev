package com.lottemart.epc.edi.srm.service;

import com.lottemart.epc.common.model.CommonFileVO;
import com.lottemart.epc.edi.srm.model.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 입점상담 > 입점상담결과확인 Service
 * 
 * @author SHIN SE JIN
 * @since 2016.07.21
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      			수정자           		수정내용
 *  -----------    	------------    ------------------
 *   2016.07.21  	SHIN SE JIN		 최초 생성
 *
 * </pre>
 */
public interface SRMRST0020Service {
	
	/**
	 * 파트너사 상세 정보
	 * @param SRMSessionVO
	 * @return SRMRST0020VO
	 * @throws Exception
	 */
	public SRMRST0020VO selectPartnerInfo(SRMSessionVO vo) throws Exception ;
	
	/**
	 * 입점상담 내역별 상태 조회 카운트
	 * @param SRMRST0020VO
	 * @return int
	 * @throws Exception
	 */
	public int selectStatusListCount(SRMRST0020VO vo) throws Exception;

	/**
	 * 입점상담 내역별 상태 조회
	 * @param SRMRST0020VO
	 * @return List<SRMRST0020VO>
	 * @throws Exception
	 */
	public List<SRMRST0020VO> selectStatusList(SRMRST0020VO vo) throws Exception ;

    /**
     * 상담 조회
     * @param SRMRST002001VO
     * @return SRMRST002001VO
     * @throws Exception
     */
    public SRMRST002001VO selectCompCounselInfo(SRMRST002001VO vo) throws Exception;

    /**
     * 품평회 조회
     * @param SRMRST002002VO
     * @return SRMRST002002VO
     * @throws Exception
     */
    public SRMRST002002VO selectCompFairInfo(SRMRST002002VO vo) throws Exception;

    /**
     * 파일정보 조회
     * @param String
	 * @return List<CommonFileVO>
     * @throws Exception
     */
    public List<CommonFileVO> selectCompCounselFileList(String fileId) throws Exception;

    /**
     * 이행보증증권 조회
     * @param SRMRST002003VO
     * @return SRMRST002003VO
     * @throws Exception
     */
    public SRMRST002003VO selectCompInsInfo(SRMRST002003VO vo) throws Exception;
    /**
     * 이행보증증권 조회
     * @param SRMRST002003VO
     * @return HashMap<String, String>
     * @throws Exception
     */
    public HashMap<String, String> updateCompInsInfo(SRMRST002003VO vo, HttpServletRequest request) throws Exception;
    
    /**
	 * 선택한 입점상담 내역 삭제
	 * @param SRMRST0020VO
	 * @return String
	 * @throws Exception
	 */
	public String deleteCounselInfo(SRMRST0020VO vo) throws Exception;

	/**
	 * 시정조치 리스트 조회
	 * @param SRMRST002004VO
	 * @return List<SRMRST002004VO>
	 * @throws Exception
	 */
	public List<SRMRST002004VO> selectCompSiteVisitCover3List(SRMRST002004VO vo) throws Exception;


	/**
	 * 시정조치 상세 조회
	 * @param SRMRST002004VO
	 * @return SRMRST002004VO
	 * @throws Exception
	 */
	public SRMRST002004VO selectCompSiteVisitCover3Detail(SRMRST002004VO vo) throws Exception;


	/**
	 * 시정조치 상세 수정
	 * @param SRMRST002004VO
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	public Map<String, Object> updateCompSiteVisitCover3Detail(SRMRST002004VO vo) throws Exception;



	/**
	 * 시정 조치 내역 삭제
	 * @param SRMRST002004VO
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	public Map<String, Object> updateCompSiteVisitCover3Detaildel(SRMRST002004VO vo) throws Exception;

	/**
	 * 품질경영평가 기관정보
	 * @param SRMRST002005VO
	 * @return SRMRST002005VO
	 * @throws Exception
	 */
	public SRMRST002005VO selectCompSiteVisitComp(SRMRST002005VO vo, HttpServletRequest request) throws Exception;


	/**
	 * 선택한 입점상담 내역 임시저장상태로 되돌림
	 * @param SRMRST0020VO
	 * @return
	 * @throws Exception
	 */
	public String updateCounselInfoCancel(SRMRST0020VO vo) throws Exception;
	
	/**
	 * MD거절 사유 조회
	 * @param SRMRST002008VO
	 * @return SRMRST002008VO
	 * @throws Exception
	 */
	public SRMRST002008VO selectRejectReasonInfo(SRMRST002008VO vo) throws Exception;

	/**
	 * 품질경영평가 기관 선택 팝업
	 * @param SRMRST002005VO
	 * @return List<SRMRST002005VO>
	 * @throws Exception
	 */
	public List<SRMRST002005VO> selectCompSiteVisitCompList(SRMRST002005VO vo) throws Exception;
	
	
	/**
	 * 품질경영평가 기관 선택 후 저장
	 * @param SRMRST002005VO
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	public Map<String, Object> UpdateCompSiteVisitComp(SRMRST002005VO vo) throws Exception;
	
	/**
	 * 업체가 선택한  품질경영평가 기관 확인
	 * @param SRMRST002005VO
	 * @return String
	 * @throws Exception
	 */
	public String selectedEvalSellerCode(SRMRST002005VO vo) throws Exception;
}
