package com.lottemart.epc.edi.srm.dao;

import java.util.List;

import com.lottemart.epc.edi.srm.model.SRMJON0020VO;
import org.springframework.stereotype.Repository;

import com.lottemart.epc.edi.srm.model.SRMSPW0010VO;

/**
 * 비밀번호 변경 Dao
 *
 * @author SHIN SE JIN
 * @since 2016.07.19
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      			수정자           		수정내용
 *  -----------    	------------    ------------------
 *   2016.07.19  	SHIN SE JIN		 최초 생성
 *
 * </pre>
 */
@Repository("srmspw0010Dao")
public class SRMSPW0010Dao extends SRMDBConnDao {

	/**
	 * 비밀번호 변경전 확인
	 * @param SRMSPW0010VO
	 * @return SRMSPW0010VO
	 * @throws Exception
	 */
	public SRMSPW0010VO passwdChangeCheck(SRMSPW0010VO vo) throws Exception {
 		return (SRMSPW0010VO) queryForObject("SRMSPW0010.passwdChangeCheck", vo);
	}

	/**
	 * 비밀번호 변경
	 * @param SRMSPW0010VO
	 * @return Integer
	 * @throws Exception
	 */
	public void updatePasswdChange(SRMSPW0010VO vo) throws Exception {
		update("SRMSPW0010.updatePasswdChange", vo);
	}

	/**
	 * 담당자 이메일 리스트
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<SRMSPW0010VO> selectVEmailList(SRMSPW0010VO vo) throws Exception {
		return queryForList("SRMSPW0010.selectVEmailList", vo);
	}

	/**
	 * 담당자 이메일 정보
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public SRMSPW0010VO selectVEmail(SRMSPW0010VO vo) throws Exception {
		return (SRMSPW0010VO) queryForObject("SRMSPW0010.selectVEmail", vo);
	}

	/**
	 *  임시비밀번호 발급여부 확인
	 * @param  vo
	 * @return SRMSessionVO
	 * @throws Exception
	 */
	public String selectTempPwFlag(SRMSPW0010VO vo) throws Exception {
		return (String)queryForString("SRMSPW0010.selectTempPwFlag", vo);
	}

	/**
	 * 임시비밀번호 변경
	 * @param vo
	 * @throws Exception
	 */
	public void updateTempPasswdChange(SRMSPW0010VO vo) throws Exception {
		update("SRMSPW0010.updateTempPasswdChange", vo);
	}

	/**
	 * 비밀번호 변경한지 90일 지났는지 확인
	 * @param vo
	 * @throws Exception
	 */
	public Integer selectIsNotChangePasswordOver90(SRMSPW0010VO vo) throws Exception {
		return (Integer)queryForInteger("SRMSPW0010.selectIsNotChangePasswordOver90", vo);
	}

	/**
	 * 최근 접근이력 90일 지났는지 확인
	 * @param vo
	 * @throws Exception
	 */
	public Integer selectIsNotAccessBefore90(SRMSPW0010VO vo) throws Exception {
		return (Integer)queryForInteger("SRMSPW0010.selectIsNotAccessBefore90", vo);
	}

	/**
	 * 비밀번호 변경 날짜 오늘로 변경
	 * @param SRMSPW0010VO
	 * @return Integer
	 * @throws Exception
	 */
	public void updatePwdChgDateToday(SRMSPW0010VO vo) throws Exception {
		update("SRMSPW0010.updatePwdChgDateToday", vo);
	}
}




