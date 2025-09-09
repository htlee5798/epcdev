package com.lottemart.epc.edi.srm.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lottemart.epc.common.model.CommonFileVO;
import com.lottemart.epc.edi.srm.model.*;

/**
 * 입점상담 > 입점상담결과확인 Dao
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
@Repository("srmrst0020Dao")
public class SRMRST0020Dao extends SRMDBConnDao {
	
	/**
	 * 파트너사 상세 정보
	 * @param SRMSessionVO
	 * @return SRMRST0020VO
	 * @throws Exception
	 */
	public SRMRST0020VO selectPartnerInfo(SRMSessionVO vo) throws Exception {
		return (SRMRST0020VO) queryForObject("SRMRST0020.selectPartnerInfo", vo);
	}
	
	/**
	 * 입점상담 내역별 상태 조회 카운트
	 * @param SRMRST0020VO
	 * @return int
	 * @throws Exception
	 */
	public int selectStatusListCount(SRMRST0020VO vo) throws Exception {
		return (Integer) queryForInteger("SRMRST0020.selectStatusListCount", vo);
	}

	/**
	 * 입점상담 내역별 상태 조회
	 * @param SRMRST0020VO
	 * @return List<SRMRST0020VO>
	 * @throws Exception
	 */
	public List<SRMRST0020VO> selectStatusList(SRMRST0020VO vo) throws Exception {
		return (List<SRMRST0020VO>) queryForList("SRMRST0020.selectStatusList", vo);
	}


	/**
	 * 상담 조회
	 * @param SRMRST002001VO
	 * @return SRMRST002001VO
	 * @throws Exception
	 */
	public SRMRST002001VO selectCompCounselInfo(SRMRST002001VO vo) throws Exception {
		return (SRMRST002001VO) queryForObject("SRMRST0020.selectCompCounselInfo", vo);
	}

	/**
	 * 품평회 조회
	 * @param SRMRST002002VO
	 * @return SRMRST002002VO
	 * @throws Exception
	 */
	public SRMRST002002VO selectCompFairInfo(SRMRST002002VO vo) throws Exception {
		return (SRMRST002002VO) queryForObject("SRMRST0020.selectCompFairInfo", vo);
	}


	/**
	 * 파일정보 조회
	 * @param String
	 * @throws Exception
	 */
	public List<CommonFileVO> selectCompCounselFileList(String fileId) throws Exception {
		return queryForListParamString("SRMRST0020.selectCompCounselFileList", fileId);
	}

	/**
	 * 이행보증증권 조회
	 * @param SRMRST002003VO
	 * @return SRMRST002003VO
	 * @throws Exception
	 */
	public SRMRST002003VO selectCompInsInfo(SRMRST002003VO vo) throws Exception {
		return (SRMRST002003VO) queryForObject("SRMRST0020.selectCompInsInfo", vo);
	}

	/**
	 * 이행보증증권 등록
	 * @param SRMRST002003VO
	 * @return
	 * @throws Exception
	 */
	public void updateCompInsInfo(SRMRST002003VO vo) throws Exception {
		update("SRMRST0020.updateCompInsInfo", vo);
	}

	/**
	 * 이행보증증권 MAIN 상태값 수정
	 * @param SRMRST002003VO
	 * @return
	 * @throws Exception
	 */
	public void updateCompInsInfoMainStatus(SRMRST002003VO vo) throws Exception {
		update("SRMRST0020.updateCompInsInfoMainStatus", vo);
	}

	/**
	 * 파일정보 조회(CREDIT_ATTACH_NO)
	 * @param String
	 * @throws Exception
	 */
	public String selectCompInsAttachNoFileId(SRMRST002003VO vo) throws Exception {
		return (String)queryForString("SRMRST0020.selectCompInsAttachNoFileId", vo);
	}
	
	/**
	 * 선택한 입점상담 내역 삭제
	 * @param SRMRST0020VO
	 * @throws Exception
	 */
	public void deleteCounselInfo(SRMRST0020VO vo) throws Exception {
		delete("SRMRST0020.deleteCounselInfo", vo);
	}
	
	/**
	 * 잠재엄체 삭제 여부 확인
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int selectAllCounselInfo(SRMRST0020VO vo) throws Exception {
		return (Integer) queryForInteger("SRMRST0020.selectAllCounselInfo", vo);
	}
	
	/**
	 * 선택한 입점상담 내역 삭제
	 * @param SRMRST0020VO
	 * @throws Exception
	 */
	public void deleteHiddenCompInfo(SRMRST0020VO vo) throws Exception {
		delete("SRMRST0020.deleteHiddenCompInfo", vo);
	}

	/**
	 * 시정조치 리스트 조회
	 * @param SRMRST002004VO
	 * @return List<SRMRST002004VO>
	 * @throws Exception
	 */
	public List<SRMRST002004VO> selectCompSiteVisitCover3List(SRMRST002004VO vo) throws Exception {
		return queryForList("SRMRST0020.selectCompSiteVisitCover3List", vo);
	}


	/**
	 * 시정조치 상세 조회
	 * @param SRMRST002004VO
	 * @return SRMRST002004VO
	 * @throws Exception
	 */
	public SRMRST002004VO selectCompSiteVisitCover3Detail(SRMRST002004VO vo) throws Exception {
		return (SRMRST002004VO)queryForObject("SRMRST0020.selectCompSiteVisitCover3Detail", vo);
	}


	/**
	 * 시정조치 상세 수정
	 * @param SRMRST002004VO
	 * @return SRMRST002004VO
	 * @throws Exception
	 */
	public void updateCompSiteVisitCover3Detail(SRMRST002004VO vo) throws Exception {
		update("SRMRST0020.updateCompSiteVisitCover3Detail", vo);
	}



	/**
	 * 시정 조치 내역 삭제
	 * @param SRMRST002004VO
	 * @return SRMRST002004VO
	 * @throws Exception
	 */
	public void updateCompSiteVisitCover3Detaildel(SRMRST002004VO vo) throws Exception {
		update("SRMRST0020.updateCompSiteVisitCover3Detaildel", vo);
	}


	/**
	 * 품질경영평가 기관정보
	 * @param SRMRST002005VO
	 * @return SRMRST002005VO
	 * @throws Exception
	 */
	public SRMRST002005VO selectCompSiteVisitComp(SRMRST002005VO vo) throws Exception {
		return (SRMRST002005VO)queryForObject("SRMRST0020.selectCompSiteVisitComp", vo);
	}
	
	
	/**
	 * 품질경영평가기관 선택 팝업
	 * @param SRMRST002005VO
	 * @return SRMRST002005VO
	 * @throws Exception
	 */
	public List<SRMRST002005VO> selectCompSiteVisitCompList(SRMRST002005VO vo) throws Exception {
		return queryForList("SRMRST0020.selectCompSiteVisitCompList", vo);
	}
	
	
	/**
	 * 품질경영평가 기관 선택 후 저장
	 * SSUGL_PROCESS_SITEVISIT 업데이트
	 * @param SRMRST002005VO
	 * @return String
	 * @throws Exception	
    */
	public void UpdateCompSiteVisitComp(SRMRST002005VO vo) throws Exception {
		update("SRMRST0020.UpdateCompSiteVisitComp", vo);
	}
	
	/**
	 * 품질경영평가 기관 선택 후 저장
	 * SSUGL_PROCESS_MAIN  업데이트
	 * @param SRMRST002005VO
	 * @return String
	 * @throws Exception	
    */
	public void UpdateCompProcessMainComp(SRMRST002005VO vo) throws Exception {
		update("SRMRST0020.UpdateCompProcessMainComp", vo);
	}
		
	
	/**
	 * 업체가 선택한  품질경영평가 기관 확인
	 * @param SRMRST002005VO
	 * @return String
	 * @throws Exception
	 */	
	public String selectedEvalSellerCode(SRMRST002005VO vo) throws Exception {
		// TODO Auto-generated method stub
		return (String)queryForString("SRMRST0020.selectedEvalSellerCode", vo);
	}


	/**
	 * 선택한 입점상담 내역 임시저장상태로 되돌림
	 * @param SRMRST002004VO
	 * @return SRMRST002004VO
	 * @throws Exception
	 */
	public void updateCounselInfoCancel(SRMRST0020VO vo) throws Exception {
		update("SRMRST0020.updateCounselInfoCancel", vo);
	}

	/**
	 * 품질경영평가 기관정보
	 * @param SRMRST002005VO
	 * @return SRMRST002005VO
	 * @throws Exception
	 */
	public List<SRMRST002003VO> selectCompInsInfoDept(SRMRST002003VO vo) throws Exception {
		return queryForList("SRMRST0020.selectCompInsInfoDept", vo);
	}

	/**
	 * 이행보증증권 담당MD 이메일
	 * @param SRMRST002003VO
	 * @return SRMRST002003VO
	 * @throws Exception
	 */
	public SRMRST002003VO selectCompInsInfoMD(SRMRST002003VO vo) throws Exception {
		return (SRMRST002003VO)queryForObject("SRMRST0020.selectCompInsInfoMD", vo);
	}



	/**
	 * 대분류조회
	 * @param SRMRST002003VO
	 * @return SRMRST002003VO
	 * @throws Exception
	 */
	public SRMJON0043VO selectCatLv1CodeInfo(SRMRST0020VO vo) throws Exception {
		return (SRMJON0043VO)queryForObject("SRMRST0020.selectCatLv1CodeInfo", vo);
	}
	
	/**
	 * MD거절 사유 조회
	 * @param SRMRST002008VO
	 * @return SRMRST002008VO
	 * @throws Exception
	 */
	public SRMRST002008VO selectRejectReasonInfo(SRMRST002008VO vo) throws Exception {
		return (SRMRST002008VO) queryForObject("SRMRST0020.selectRejectReasonInfo", vo);
	}

	/**
	 * 풍질경영평가 업체 선택 , 저장 후 담당 MD 정보 가져오기(Email, 
	 * @param SRMRST002008VO
	 * @return SRMRST002008VO
	 * @throws Exception
	 */	
	public SRMRST0020051VO SelectChargeMdInfo(SRMRST002005VO vo) throws Exception{
		// TODO Auto-generated method stub
		return (SRMRST0020051VO)queryForObject("SRMRST0020.SelectChargeMdInfo", vo);
	}


}




