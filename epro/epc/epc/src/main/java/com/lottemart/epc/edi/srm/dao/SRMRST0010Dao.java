package com.lottemart.epc.edi.srm.dao;

import org.springframework.stereotype.Repository;

import com.lottemart.epc.edi.srm.model.SRMRST0010VO;

/**
 * 입점상담 > 입점상담결과확인 로그인 Dao
 * 
 * @author SHIN SE JIN
 * @since 2016.07.25
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      			수정자           		수정내용
 *  -----------    	------------    ------------------
 *   2016.07.25  	SHIN SE JIN		 최초 생성
 *
 * </pre>
 */
@Repository("srmrst0010Dao")
public class SRMRST0010Dao extends SRMDBConnDao {
	
	/**
	 * 비밀번호 체크
	 * @param SRMRST0010VO
	 * @return String
	 * @throws Exception
	 */
	public SRMRST0010VO selectPasswdCheck(SRMRST0010VO vo) throws Exception {
		return (SRMRST0010VO) queryForObject("SRMRST0010.selectPasswdCheck", vo);
	}
	
}




