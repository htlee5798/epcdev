package com.lottemart.epc.edi.srm.dao;

import org.springframework.stereotype.Repository;

import com.lottemart.epc.edi.srm.model.SRMEVL0010VO;

/**
 * 품질경영평가 / 품질경영평가 로그인 Dao
 * 
 * @author SHIN SE JIN
 * @since 2016.07.08
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      			수정자           		수정내용
 *  -----------    	------------    ------------------
 *   2016.07.08  	SHIN SE JIN		 최초 생성
 *
 * </pre>
 */
@Repository("srmevl0010Dao")
public class SRMEVL0010Dao extends SRMDBConnDao {
	
	/**
	 * 품질평가로그인 기 등록 확인
	 * @param SRMEVL0010VO
	 * @return SRMEVL0010VO
	 * @throws Exception
	 */
	public SRMEVL0010VO selectSRMEVLLogin(SRMEVL0010VO vo) throws Exception {
		return (SRMEVL0010VO) queryForObject("SRMEVL0010.selectSRMEVLLogin", vo);
	}
	
	/**
	 * 로그인 실패 시 카운트 증가
	 * @param SRMEVL0010VO
	 * @throws Exception
	 */
	public void updatePassCheckCnt(SRMEVL0010VO vo) throws Exception {
		update("SRMEVL0010.updatePassCheckCnt", vo);
	}
	
	/**
	 * 로그인 성공 시 카운트 초기화
	 * @param SRMEVL0010VO
	 * @throws Exception
	 */
	public void updatePassCheckCntReset(SRMEVL0010VO vo) throws Exception {
		update("SRMEVL0010.updatePassCheckCntReset", vo);
	}
	
}




