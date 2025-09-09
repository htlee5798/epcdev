package com.lottemart.epc.edi.srm.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lottemart.epc.edi.srm.model.SRMJON0020VO;

/**
 * 입점상담 / 입점상담신청  / 입점상당신청 로그인 Dao
 * 
 * @author SHIN SE JIN
 * @since 2016.07.06
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      			수정자           		수정내용
 *  -----------    	------------    ------------------
 *   2016.07.06  	SHIN SE JIN		 최초 생성
 *
 * </pre>
 */
@Repository("srmjon0020Dao")
public class SRMJON0020Dao extends SRMDBConnDao {
	
	/**
	 * 기등록여부 확인
	 * @param SRMSessionVO
	 * @return SRMSessionVO
	 * @throws Exception
	 */
	public SRMJON0020VO selectSRMJONLogin(SRMJON0020VO vo) throws Exception {
		return (SRMJON0020VO) queryForObject("SRMJON0020.selectSRMJONLogin", vo);
	}

	/**
	 * 입점상담 신청 로그인(비밀번호 포함)
	 * @param SRMSessionVO
	 * @return SRMSessionVO
	 * @throws Exception
	 */
	public SRMJON0020VO selectSRMJONReLogin(SRMJON0020VO vo) throws Exception {
		return (SRMJON0020VO) queryForObject("SRMJON0020.selectSRMJONReLogin", vo);
	}
	
	/**
	 * 상담신청 리스트 조회 카운트
	 * @param SRMJON0020VO
	 * @return int
	 * @throws Exception
	 */
	public int selectCounselListCount(SRMJON0020VO vo) throws Exception {
		return (Integer) queryForInteger("SRMJON0020.selectCounselListCount", vo);
	}
	
	/**
	 * 상담신청 리스트 조회
	 * @param SRMJON0020VO
	 * @return List<SRMJON0020VO>
	 * @throws Exception
	 */
	public List<SRMJON0020VO> selectCounselList(SRMJON0020VO vo) throws Exception {
		return (List<SRMJON0020VO>) queryForList("SRMJON0020.selectCounselList", vo);
	}
	
	/**
	 * 비밀번호가 틀린 경우 카운트 증가
	 * @param SRMJON0020VO
	 * @throws Exception
	 */
	public void updatePassCheckCnt(SRMJON0020VO vo) throws Exception {
		update("SRMJON0020.updatePassCheckCnt", vo);
	}
	
	/**
	 * 로그인 성공 시 카운트 초기화
	 * @param SRMJON0020VO
	 * @throws Exception
	 */
	public void updatePassCheckCntReset(SRMJON0020VO vo) throws Exception {
		update("SRMJON0020.updatePassCheckCntReset", vo);
	}
	
	/**
	 * 사업자명/ 사업자번호 학인
	 * @param SRMSessionVO
	 * @return SRMSessionVO
	 * @throws Exception
	 */
	public SRMJON0020VO selectSRMJONSearch(SRMJON0020VO vo) throws Exception {
		return (SRMJON0020VO) queryForObject("SRMJON0020.selectSRMJONSearch", vo);
	}
	
}




