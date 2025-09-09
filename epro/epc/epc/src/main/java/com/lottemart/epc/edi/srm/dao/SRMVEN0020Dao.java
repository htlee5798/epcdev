package com.lottemart.epc.edi.srm.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lottemart.epc.common.model.CommonFileVO;
import com.lottemart.epc.edi.srm.model.SRMVEN002001VO;
import com.lottemart.epc.edi.srm.model.SRMVEN0020VO;

/**
 * SRM정보 / 인증정보 등록 Dao
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
@Repository("srmven0020Dao")
public class SRMVEN0020Dao extends SRMDBConnDao {
	
	/**
	 * 인증등록 정보 내역 카운트
	 * @param SRMVEN002001VO
	 * @return Integer
	 * @throws Exception
	 */
	public int selectCertiInfoAddListCount(SRMVEN0020VO vo) throws Exception {
		return (Integer) queryForInteger("SRMVEN0020.selectCertiInfoAddListCount", vo);
	}
	
	/**
	 * 인증등록 정보 내역
	 * @param SRMVEN002001VO
	 * @return List<SRMVEN002001VO>
	 * @throws Exception
	 */
	public List<SRMVEN0020VO> selectCertiInfoAddList(SRMVEN0020VO vo) throws Exception {
		return (List<SRMVEN0020VO>) queryForList("SRMVEN0020.selectCertiInfoAddList", vo);
	}
	
	/**
	 * 인증정보 내역
	 * @param SRMVEN0020VO
	 * @return List<SRMVEN0020VO>
	 * @throws Exception
	 */
	public List<SRMVEN0020VO> selectCertiInfoList(SRMVEN0020VO vo) throws Exception {
		return (List<SRMVEN0020VO>) queryForList("SRMVEN0020.selectCertiInfoList", vo);
	}
	
	/**
	 * 상품검색 카운드
	 * @param SRMVEN002001VO
	 * @return Integer
	 * @throws Exception
	 */
	public int selectProdInfoCount(SRMVEN002001VO vo) throws Exception {
		return (Integer) queryForInteger("SRMVEN0020.selectProdInfoCount", vo);
	}
	
	/**
	 * 상품검색
	 * @param SRMVEN002001VO
	 * @return List<SRMVEN002002VO>
	 * @throws Exception
	 */
	public List<SRMVEN002001VO> selectProdInfo(SRMVEN002001VO vo) throws Exception {
		return (List<SRMVEN002001VO>) queryForList("SRMVEN0020.selectProdInfo", vo);
	}
	
	/**
	 * 첨부파일 정보 조회
	 * @param SRMVEN0020VO
	 * @return String
	 * @throws Exception
	 */
	public String selectCertiAttachFileId(SRMVEN0020VO vo) throws Exception {
		return (String) queryForString("SRMVEN0020.selectCertiAttachFileId", vo);
	}
	
	/**
	 * 파트너사 인증정보 수정
	 * @param SRMVEN0020VO
	 * @throws Exception
	 */
	public void updateProdInfo(SRMVEN0020VO vo) throws Exception {
		update("SRMVEN0020.updateProdInfo", vo);
	}
	
	/**
	 * 파트너사 인증정보 등록
	 * @param SRMVEN0020VO
	 * @throws Exception
	 */
	public void insertProdInfo(SRMVEN0020VO vo) throws Exception {
		insert("SRMVEN0020.insertProdInfo", vo);
	}
	
	/**
	 * 파트너사 인증정보 삭제
	 * @param SRMVEN0020VO
	 * @throws Exception
	 */
	public void deleteCertiInfo(SRMVEN0020VO vo) throws Exception {
		delete("SRMVEN0020.deleteCertiInfo", vo);
	}
	
	/**
	 * 파일 정보조회
	 * @param CommonFileVO
	 * @return CommonFileVO
	 * @throws Exception
	 */
	public CommonFileVO selectFileInfo(CommonFileVO vo) throws Exception {
		return (CommonFileVO) queryForObject("SRMVEN0020.selectFileInfo", vo);
	}
	
	/**
	 * 파일 삭제
	 * @param CommonFileVO
	 * @throws Exception
	 */
	public void deleteFile(CommonFileVO vo) throws Exception {
		delete("SRMVEN0020.deleteFile", vo);
	}
	
	/**
	 * 인증정보 정보 중복체크
	 * @param SRMVEN0020VO
	 * @return int
	 * @throws Exception
	 */
	public int selectCertiInfoCheck(SRMVEN0020VO vo) throws Exception {
		return (Integer) queryForInteger("SRMVEN0020.selectCertiInfoCheck", vo);
	}
	
}




