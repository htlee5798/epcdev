package com.lottemart.epc.edi.product.dao;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.lottemart.epc.edi.product.model.NEDMPRO0320VO;

import lcn.module.framework.base.AbstractDAO;


@Repository("nedmpro0320Dao")
public class NEDMPRO0320Dao extends AbstractDAO {

	/**
	 * 반품 제안 등록 카운터 조회
	 * @param pedmord0001Vo
	 * @return List<NEDMPRO0320VO>
	 * @throws Exception
	 */
	public int selectProdRtnItemListCount(NEDMPRO0320VO paramVo) throws Exception {
		return (Integer) getSqlMapClientTemplate().queryForObject("NEDMPRO0320.selectProdRtnItemListCount", paramVo);
	}
	
	/**
	 * 반품 제안 등록 리스트 조회
	 * @param pedmord0001Vo
	 * @return List<NEDMPRO0320VO>
	 * @throws Exception
	 */
	public List<NEDMPRO0320VO> selectProdRtnItemList(NEDMPRO0320VO paramVo) throws Exception{
		return (List<NEDMPRO0320VO>)getSqlMapClientTemplate().queryForList("NEDMPRO0320.selectProdRtnItemList", paramVo);
	}
	
	
	/**
	 * 반품 제안 등록
	 * @param paramVo
	 * @throws DataAccessException
	 */
	public void mergeProdRtnItem(NEDMPRO0320VO paramVo) throws DataAccessException {
		getSqlMapClientTemplate().insert("NEDMPRO0320.mergeProdRtnItem", paramVo);
	}
	
	/**
	 * 반품 요청 번호 조회
	 * @param paramVo
	 * @return String
	 * @throws Exception
	 */
	public NEDMPRO0320VO selectRtnItemProdCdChk(NEDMPRO0320VO paramVo) throws Exception {
		return (NEDMPRO0320VO) getSqlMapClientTemplate().queryForObject("NEDMPRO0320.selectRtnItemProdCdChk", paramVo);
	}
	
	/**
	 * 반품 요청 번호 조회
	 * @param paramVo
	 * @return String
	 * @throws Exception
	 */
	public String selectProdRtnNoSeq(NEDMPRO0320VO paramVo) throws Exception {
		return (String) getSqlMapClientTemplate().queryForObject("NEDMPRO0320.selectProdRtnNoSeq", paramVo);
	}
	
	/**
	 * RFC Call Item Data 조회
	 * @param vo
	 * @return List<LinkedHashMap>
	 * @throws Exception
	 */
	public List<LinkedHashMap> selectProdRtnItemRfcCallData(NEDMPRO0320VO vo) throws Exception {
		return this.list("NEDMPRO0320.selectProdRtnItemRfcCallData", vo);
	}
	
	/**
	 * 반품 아이템 상태값 update → md 협의 요청
	 * @param paramVo
	 * @throws DataAccessException
	 */
	public void updateProdRtnItem(NEDMPRO0320VO paramVo) throws DataAccessException {
		// TODO Auto-generated method stub
		getSqlMapClientTemplate().update("NEDMPRO0320.updateProdRtnItem", paramVo);
	}
	
}
