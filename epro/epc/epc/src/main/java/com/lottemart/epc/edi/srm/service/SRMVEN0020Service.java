package com.lottemart.epc.edi.srm.service;

import java.util.List;

import com.lottemart.epc.common.model.CommonFileVO;
import com.lottemart.epc.edi.srm.model.SRMVEN002001VO;
import com.lottemart.epc.edi.srm.model.SRMVEN0020VO;

/**
 * SRM정보 / 인증정보 등록 Service
 * 
 * @author SHIN SE JIN
 * @since 2016.07.26
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      			수정자           		수정내용
 *  -----------    	------------    ------------------
 *   2016.07.26  	SHIN SE JIN		 최초 생성
 *
 * </pre>
 */
public interface SRMVEN0020Service {
	
	/**
	 * 인증등록 정보 내역 카운트
	 * @param SRMVEN002001VO
	 * @return Integer
	 * @throws Exception
	 */
	public int selectCertiInfoAddListCount(SRMVEN0020VO vo) throws Exception;
	
	/**
	 * 인증등록 정보 내역
	 * @param SRMVEN0020VO
	 * @return List<SRMVEN0020VO>
	 * @throws Exception
	 */
	public List<SRMVEN0020VO> selectCertiInfoAddList(SRMVEN0020VO vo) throws Exception;
	
	/**
	 * 인증정보 내역
	 * @param SRMVEN0020VO
	 * @return List<SRMVEN0020VO>
	 * @throws Exception
	 */
	public List<SRMVEN0020VO> selectCertiInfoList(SRMVEN0020VO vo) throws Exception;
	
	/**
	 * 상품검색 카운드
	 * @param SRMVEN002001VO
	 * @return Integer
	 * @throws Exception
	 */
	public int selectProdInfoCount(SRMVEN002001VO vo) throws Exception;
	
	/**
	 * 상품검색
	 * @param SRMVEN002001VO
	 * @return List<SRMVEN002001VO>
	 * @throws Exception
	 */
	public List<SRMVEN002001VO> selectProdInfo(SRMVEN002001VO vo) throws Exception;
	
	/**
	 * 파트너사 인증정보 등록 및 수정
	 * @param SRMVEN0020VO
	 * @return String
	 * @throws Exception
	 */
	public String updateCertiInfo(SRMVEN0020VO vo) throws Exception;
	
	/**
	 * 파트너사 인증정보 삭제
	 * @param SRMVEN0020VO
	 * @return String
	 * @throws Exception
	 */
	public String deleteCertiInfo(SRMVEN0020VO vo) throws Exception;
	
	/**
	 * 파일 정보조회
	 * @param CommonFileVO
	 * @return CommonFileVO
	 * @throws Exception
	 */
	public CommonFileVO selectFileInfo(CommonFileVO vo) throws Exception;
	
	/**
	 * 인증정보 정보 중복체크
	 * @param SRMVEN0020VO
	 * @return int
	 * @throws Exception
	 */
	public int selectCertiInfoCheck(SRMVEN0020VO vo) throws Exception;
	
}
