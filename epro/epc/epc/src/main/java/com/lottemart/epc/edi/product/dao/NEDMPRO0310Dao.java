package com.lottemart.epc.edi.product.dao;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.lottemart.epc.edi.product.model.NEDMPRO0310VO;

import lcn.module.framework.base.AbstractDAO;


@Repository("nedmpro0310Dao")
public class NEDMPRO0310Dao extends AbstractDAO {

	/**
	 * 파트너사 행사 신청 상세 헤더 조회
	 * @param pedmord0001Vo
	 * @return List<NEDMPRO0310VO>
	 * @throws Exception
	 */
	public NEDMPRO0310VO selectProEventAppDetail(NEDMPRO0310VO paramVo) throws Exception{
		return (NEDMPRO0310VO)getSqlMapClientTemplate().queryForObject("NEDMPRO0310.selectProEventAppDetail", paramVo);
	}
	
	/**
	 * 파트너사 행사 신청 상세 아이템 조회
	 * @param paramVo
	 * @return
	 * @throws Exception
	 */
	public List<NEDMPRO0310VO> selectProEventAppItemList(NEDMPRO0310VO paramVo) throws DataAccessException {
		// TODO Auto-generated method stub
		return (List<NEDMPRO0310VO>)getSqlMapClientTemplate().queryForList("NEDMPRO0310.selectProEventAppItemList", paramVo);
	}
	
	/**
	 * 행사 header 정보 저장 전 삭제
	 * @param paramVo
	 * @throws DataAccessException
	 */
	public void deleteProEventApp(NEDMPRO0310VO paramVo) throws DataAccessException{
		// TODO Auto-generated method stub
		getSqlMapClientTemplate().delete("NEDMPRO0310.deleteProEventApp", paramVo);
	}
	
	/**
	 * 파트너사 행사 번호 순번 조회
	 * @param paramVo
	 * @throws DataAccessException
	 */
	public String selectReqOfrcdSeq(NEDMPRO0310VO paramVo) throws Exception {
		return (String) getSqlMapClientTemplate().queryForObject("NEDMPRO0310.selectReqOfrcdSeq", paramVo);
	}
	
	/**
	 * 행사 header 정보 저장
	 * @param paramVo
	 * @throws DataAccessException
	 */
	public void insertProEventApp(NEDMPRO0310VO paramVo) throws DataAccessException {
		// TODO Auto-generated method stub
		getSqlMapClientTemplate().insert("NEDMPRO0310.insertProEventApp", paramVo);
	}
	
	/**
	 * 행사 header 정보 수정
	 * @param paramVo
	 * @throws DataAccessException
	 */
	public void updateProEventApp(NEDMPRO0310VO paramVo) throws DataAccessException {
		// TODO Auto-generated method stub
		getSqlMapClientTemplate().update("NEDMPRO0310.updateProEventApp", paramVo);
	}
	
	/**
	 * 행사 itme 저장 전 정보 삭제
	 * @param paramVo
	 * @throws DataAccessException
	 */
	public void deleteProEventAppItemList(NEDMPRO0310VO paramVo) throws DataAccessException{
		// TODO Auto-generated method stub
		getSqlMapClientTemplate().delete("NEDMPRO0310.deleteProEventAppItemList", paramVo);
	}
	
	/**
	 * 행사 item 저장
	 * @param paramVo
	 * @throws DataAccessException
	 */
	public void insertProEventAppItemList(NEDMPRO0310VO paramVo) throws DataAccessException {
		// TODO Auto-generated method stub
		getSqlMapClientTemplate().insert("NEDMPRO0310.insertProEventAppItemList", paramVo);
	}
	
	/**
	 * 행사 item 정보 수정
	 * @param paramVo
	 * @throws DataAccessException
	 */
	public void updateProEventAppItemList(NEDMPRO0310VO paramVo) throws DataAccessException {
		// TODO Auto-generated method stub
		getSqlMapClientTemplate().update("NEDMPRO0310.updateProEventAppItemList", paramVo);
	}
	
	/**
	 * 행사 item 저장(MERGE)
	 * @param paramVo
	 * @throws DataAccessException
	 */
	public void mergeProEvntItemList(NEDMPRO0310VO paramVo) throws DataAccessException {
		// TODO Auto-generated method stub
		getSqlMapClientTemplate().insert("NEDMPRO0310.mergeProEvntItemList", paramVo);
	}
	
	
	/**
	 * 판매 바코드 카운터 조회
	 * @param pedmord0001Vo
	 * @return List<NEDMPRO0310VO>
	 * @throws Exception
	 */
	public int selectSaleBarcodeListCount(NEDMPRO0310VO paramVo) throws Exception {
		return (Integer) getSqlMapClientTemplate().queryForObject("NEDMPRO0310.selectSaleBarcodeListCount", paramVo);
	}
	
	/**
	 * 판매 바코드 조회
	 * @param pedmord0001Vo
	 * @return List<NEDMPRO0310VO>
	 * @throws Exception
	 */
	public List<NEDMPRO0310VO> selectSaleBarcodeList(NEDMPRO0310VO paramVo) throws Exception{
		return (List<NEDMPRO0310VO>)getSqlMapClientTemplate().queryForList("NEDMPRO0310.selectSaleBarcodeList", paramVo);
	}
	
	/**
	 * ecs 연계 계약 번호 생성
	 * @param paramVo
	 * @return String
	 * @throws Exception
	 */
	public String selectEcsContCode(NEDMPRO0310VO paramVo) throws Exception {
		return (String) getSqlMapClientTemplate().queryForObject("NEDMPRO0310.selectEcsContCode", paramVo);
	}
	
	/**
	 * RFC Call Data 조회
	 * @param vo
	 * @return List<LinkedHashMap>
	 * @throws Exception
	 */
	public List<LinkedHashMap> selectEvntRfcCallData(NEDMPRO0310VO vo) throws Exception {
		return this.list("NEDMPRO0310.selectEvntRfcCallData", vo);
	}
	
	/**
	 * RFC Call Item Data 조회
	 * @param vo
	 * @return List<LinkedHashMap>
	 * @throws Exception
	 */
	public List<LinkedHashMap> selectEvntItemRfcCallData(NEDMPRO0310VO vo) throws Exception {
		return this.list("NEDMPRO0310.selectEvntItemRfcCallData", vo);
	}
	
	
	/**
	 * 행사 테마 번호 카운트 조회
	 * @param pedmord0001Vo
	 * @return List<NEDMPRO0310VO>
	 * @throws Exception
	 */
	public int selectProdEvntThmCount(NEDMPRO0310VO paramVo) throws Exception {
		return (Integer) getSqlMapClientTemplate().queryForObject("NEDMPRO0310.selectProdEvntThmCount", paramVo);
	}
	
	/**
	 * 행사 테마 번호 조회
	 * @param pedmord0001Vo
	 * @return List<NEDMPRO0310VO>
	 * @throws Exception
	 */
	public List<NEDMPRO0310VO> selectProdEvntThmList(NEDMPRO0310VO paramVo) throws Exception{
		return (List<NEDMPRO0310VO>)getSqlMapClientTemplate().queryForList("NEDMPRO0310.selectProdEvntThmList", paramVo);
	}
	
	/**
	 * 팀 정보 카운트 조회
	 * @param pedmord0001Vo
	 * @return List<NEDMPRO0310VO>
	 * @throws Exception
	 */
	public int selectDepCdCount(NEDMPRO0310VO paramVo) throws Exception {
		return (Integer) getSqlMapClientTemplate().queryForObject("NEDMPRO0310.selectDepCdCount", paramVo);
	}
	
	/**
	 * 팀 정보 조회
	 * @param pedmord0001Vo
	 * @return List<NEDMPRO0310VO>
	 * @throws Exception
	 */
	public List<NEDMPRO0310VO> selectDepCdList(NEDMPRO0310VO paramVo) throws Exception{
		return (List<NEDMPRO0310VO>)getSqlMapClientTemplate().queryForList("NEDMPRO0310.selectDepCdList", paramVo);
	}

	
	
	
	
	
	
}
