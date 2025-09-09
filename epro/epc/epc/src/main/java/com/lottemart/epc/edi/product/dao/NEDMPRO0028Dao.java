package com.lottemart.epc.edi.product.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.lottemart.epc.common.model.CommonFileVO;
import com.lottemart.epc.edi.product.model.NEDMPRO0028VO;

import lcn.module.framework.base.AbstractDAO;


@Repository("nEDMPRO0028Dao")
public class NEDMPRO0028Dao extends AbstractDAO {

	/**
	 * ESG 항목 조회
	 * @param pedmord0001Vo
	 * @return List<NEDMPRO0028VO>
	 * @throws Exception
	 */
	public int selectNewProdEsgListCount(NEDMPRO0028VO paramVo) throws Exception {
		return (Integer) getSqlMapClientTemplate().queryForObject("NEDMPRO0028.selectNewProdEsgListCount", paramVo);
	}
	
	/**
	 * ESG 항목 리스트 조회
	 * @param pedmord0001Vo
	 * @return List<NEDMPRO0028VO>
	 * @throws Exception
	 */
	public List<NEDMPRO0028VO> selectNewProdEsgList(NEDMPRO0028VO paramVo) throws Exception{
		return (List<NEDMPRO0028VO>)getSqlMapClientTemplate().queryForList("NEDMPRO0028.selectNewProdEsgList", paramVo);
	}
	
	
	/**
	 * 대분류(유형) 조회
	 * @param paramVo
	 * @return List<NEDMPRO0028VO>
	 * @throws DataAccessException
	 */
	public List<NEDMPRO0028VO> selectEsgMstlList(NEDMPRO0028VO paramVo) throws DataAccessException {
		// TODO Auto-generated method stub
		return (List<NEDMPRO0028VO>)getSqlMapClientTemplate().queryForList("NEDMPRO0028.selectEsgMstlList", paramVo);
	}
	
	/**
	 * 중분류(인증유형) 조회ㅡ
	 * @param paramVo
	 * @return List<NEDMPRO0028VO>
	 * @throws DataAccessException
	 */
	public List<NEDMPRO0028VO> selectEsgMstMList(NEDMPRO0028VO paramVo) throws DataAccessException {
		// TODO Auto-generated method stub
		return (List<NEDMPRO0028VO>)getSqlMapClientTemplate().queryForList("NEDMPRO0028.selectEsgMstMList", paramVo);
	}
	
	/**
	 * 소분류(인증상세유형) 조회
	 * @param paramVo
	 * @return List<NEDMPRO0028VO>
	 * @throws DataAccessException
	 */
	public List<NEDMPRO0028VO> selectEsgMstSList(NEDMPRO0028VO paramVo) throws DataAccessException {
		// TODO Auto-generated method stub
		return (List<NEDMPRO0028VO>)getSqlMapClientTemplate().queryForList("NEDMPRO0028.selectEsgMstSList", paramVo);
	}
	
	/**
	 * ESG 항목 등록
	 * @param paramVo
	 * @throws DataAccessException
	 */
	public void mergeNewProdEsg(NEDMPRO0028VO paramVo) throws DataAccessException {
		getSqlMapClientTemplate().insert("NEDMPRO0028.mergeNewProdEsg", paramVo);
	}
	
	/**
	 * ESG 첨부파일 등록
	 * @param paramVo
	 * @throws DataAccessException
	 */
	public void mergeProdEsgFile(NEDMPRO0028VO paramVo) throws DataAccessException {
		getSqlMapClientTemplate().insert("NEDMPRO0028.mergeProdEsgFile", paramVo);
	}
	
	/**
	 * 상품 마스터 UPDATE
	 * @param paramVo
	 * @throws DataAccessException
	 */
	public void updateNewProdReg(NEDMPRO0028VO paramVo) throws DataAccessException {
		getSqlMapClientTemplate().update("NEDMPRO0028.updateNewProdReg", paramVo);
	}
	
	/**
	 * ESG 항목 삭제 처리
	 * @param paramVo
	 * @throws DataAccessException
	 */
	public void updateNewProdEsg(NEDMPRO0028VO paramVo) throws DataAccessException {
		getSqlMapClientTemplate().update("NEDMPRO0028.updateNewProdEsg", paramVo);
	}
	
	/**
	 * 다운로드 파일 조회
	 * @param vo
	 * @return CommonFileVO
	 * @throws Exception
	 */
	public CommonFileVO selectProdEsgFile(CommonFileVO paramVo) throws Exception{
		return (CommonFileVO)getSqlMapClientTemplate().queryForObject("NEDMPRO0028.selectProdEsgFile", paramVo);
	}
	
	/**
	 * ESG 파일정보 조회
	 * @param nEDMPRO0028VO
	 * @return NEDMPRO0028VO
	 * @throws Exception
	 */
	public NEDMPRO0028VO selectEsgFileInfo(NEDMPRO0028VO nEDMPRO0028VO) throws Exception {
		return (NEDMPRO0028VO) getSqlMapClientTemplate().queryForObject("NEDMPRO0028.selectEsgFileInfo", nEDMPRO0028VO);
	}
	
	/**
	 * ESG 인증기간 필수여부 확인
	 * @param nEDMPRO0028VO
	 * @return String
	 * @throws Exception
	 */
	public String selectChkEsgDtFg(NEDMPRO0028VO nEDMPRO0028VO) throws Exception {
		return (String) getSqlMapClientTemplate().queryForObject("NEDMPRO0028.selectChkEsgDtFg", nEDMPRO0028VO);
	}
	
	/**
	 * 항목삭제된 ESG정보 제거
	 * @param paramVo
	 * @return int
	 * @throws DataAccessException
	 */
	public int updateCtgDelEsgInfo(NEDMPRO0028VO paramVo) throws DataAccessException {
		return (int) getSqlMapClientTemplate().update("NEDMPRO0028.updateCtgDelEsgInfo", paramVo);
	}
	
	
}
