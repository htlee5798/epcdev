package com.lottemart.epc.edi.product.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.lottemart.epc.edi.product.model.NEDMPRO0330VO;

import lcn.module.framework.base.AbstractDAO;


@Repository("nedmpro0330Dao")
public class NEDMPRO0330Dao extends AbstractDAO {

	/**
	 * 약정서 카운터 조회
	 * @param pedmord0001Vo
	 * @return List<NEDMPRO0330VO>
	 * @throws Exception
	 */
	public int selectProdRtnDocListCount(NEDMPRO0330VO paramVo) throws Exception {
		return (Integer) getSqlMapClientTemplate().queryForObject("NEDMPRO0330.selectProdRtnDocListCount", paramVo);
	}
	
	/**
	 * 약정서 리스트 조회
	 * @param pedmord0001Vo
	 * @return List<NEDMPRO0330VO>
	 * @throws Exception
	 */
	public List<NEDMPRO0330VO> selectProdRtnDocList(NEDMPRO0330VO paramVo) throws Exception{
		return (List<NEDMPRO0330VO>)getSqlMapClientTemplate().queryForList("NEDMPRO0330.selectProdRtnDocList", paramVo);
	}
	
	/**
	 * 반품 약정서 등록
	 * @param paramVo
	 * @throws DataAccessException
	 */
	public void mergeProdRtnCntr(Map<String, Object> paramVo) throws DataAccessException {
		// TODO Auto-generated method stub
		getSqlMapClientTemplate().insert("NEDMPRO0330.mergeProdRtnCntr", paramVo);
	}
	
	/**
	 * 반품 약정서 등록 (상품별)
	 * @param paramVo
	 * @throws DataAccessException
	 */
	public void mergeProdRtnCntrProd(Map<String, Object> paramVo) throws DataAccessException {
		// TODO Auto-generated method stub
		getSqlMapClientTemplate().insert("NEDMPRO0330.mergeProdRtnCntrProd", paramVo);
	}
	
	/**
	 * 반품 약정서 등록( 점포&상품별)
	 * @param paramVo
	 * @throws DataAccessException
	 */
	public void mergeProdRtnCntrStrProd(Map<String, Object> paramVo) throws DataAccessException {
		// TODO Auto-generated method stub
		getSqlMapClientTemplate().insert("NEDMPRO0330.mergeProdRtnCntrStrProd", paramVo);
	}
	
	/**
	 * 반품 약정서 조회 ( 약정서 목록 )
	 * @param paramVo
	 * @return List<NEDMPRO0330VO>
	 * @throws DataAccessException
	 */
	public List<NEDMPRO0330VO> selectProdRtnCntrList(NEDMPRO0330VO paramVo) throws DataAccessException {
		// TODO Auto-generated method stub
		return (List<NEDMPRO0330VO>)getSqlMapClientTemplate().queryForList("NEDMPRO0330.selectProdRtnCntrList", paramVo);
	}
	
	/**
	 * 반품 약정서 조회 ( 상품별 )
	 * @param paramVo
	 * @return List<NEDMPRO0330VO>
	 * @throws DataAccessException
	 */
	public List<NEDMPRO0330VO> selectProdRtnCntrProdList(NEDMPRO0330VO paramVo) throws DataAccessException {
		// TODO Auto-generated method stub
		return (List<NEDMPRO0330VO>)getSqlMapClientTemplate().queryForList("NEDMPRO0330.selectProdRtnCntrProdList", paramVo);
	}
	
	/**
	 * 반품 약정서 조회 ( 점포 & 상품별 )
	 * @param paramVo
	 * @return List<NEDMPRO0330VO>
	 * @throws DataAccessException
	 */
	public List<NEDMPRO0330VO> selectProdRtnCntrStrProdList(NEDMPRO0330VO paramVo) throws DataAccessException {
		// TODO Auto-generated method stub
		return (List<NEDMPRO0330VO>)getSqlMapClientTemplate().queryForList("NEDMPRO0330.selectProdRtnCntrStrProdList", paramVo);
	}
	
}
