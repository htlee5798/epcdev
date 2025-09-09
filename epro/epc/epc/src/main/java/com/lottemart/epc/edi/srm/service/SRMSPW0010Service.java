package com.lottemart.epc.edi.srm.service;

import java.util.List;

import com.lottemart.epc.edi.srm.model.SRMJON0020VO;
import com.lottemart.epc.edi.srm.model.SRMSPW0010VO;

/**
 * 비밀번호 변경 Service
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
public interface SRMSPW0010Service {

	/**
	 * 비밀번호 변경 로그인
	 * @param SRMSPW0010VO
	 * @return SRMSPW0010VO
	 * @throws Exception
	 */
	public SRMSPW0010VO passwdChangeCheck(SRMSPW0010VO vo) throws Exception;

	/**
	 * 비밀번호 변경
	 * @param SRMSPW0010VO
	 * @return String
	 * @throws Exception
	 */
	public String updatePasswdChange(SRMSPW0010VO vo) throws Exception;

	/**
	 * 담당자 이메일 리스트
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<SRMSPW0010VO> selectVEmailList(SRMSPW0010VO vo) throws Exception;

	/**
	 * 담당자 이메일 정보
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public SRMSPW0010VO selectVEmail(SRMSPW0010VO vo) throws Exception;

	/**
	 * 임시비밀번호 발급 여부 확인
	 * @param vo
	 * @throws Exception
	 */
	public String selectTempPwFlag(SRMSPW0010VO vo) throws Exception;

	/**
	 * 임시비밀번호 발급후 로그인시, 비밀번호변경
	 * @param vo
	 * @throws Exception
	 */
	public void updateTempPasswdChange(SRMSPW0010VO vo) throws Exception;

	/**
	 * 비밀번호 변경한지 90일 지났는지 확인
	 * @param vo
	 * @throws Exception
	 */
	public Integer selectIsNotChangePasswordOver90(SRMSPW0010VO vo) throws Exception;

	/**
	 * 최근 접근 이력 90일 지났는지 확인
	 */
	public Integer selectIsNotAccessBefore90(SRMSPW0010VO vo) throws Exception ;

	/**
	 * 최근 접근 이력 90일 지났는지 확인
	 */
	public void updatePwdChgDateToday(SRMSPW0010VO vo) throws Exception ;

}
