package com.lottemart.epc.edi.product.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lottemart.epc.edi.product.model.NEDMPRO0300VO;

import lcn.module.framework.base.AbstractDAO;


@Repository("nedmpro0300Dao")
public class NEDMPRO0300Dao extends AbstractDAO {

	/**
	 * 행사정보 등록내역 카운터 조회
	 * @param pedmord0001Vo
	 * @return List<NEDMPRO0300VO>
	 * @throws Exception
	 */
	public int selectProEventAppListCount(NEDMPRO0300VO paramVo) throws Exception {
		return (Integer) getSqlMapClientTemplate().queryForObject("NEDMPRO0300.selectProEventAppListCount", paramVo);
	}
	
	/**
	 * 행사정보 등록내역 조회
	 * @param pedmord0001Vo
	 * @return List<NEDMPRO0300VO>
	 * @throws Exception
	 */
	public List<NEDMPRO0300VO> selectProEventAppList(NEDMPRO0300VO paramVo) throws Exception{
		return (List<NEDMPRO0300VO>)getSqlMapClientTemplate().queryForList("NEDMPRO0300.selectProEventAppList", paramVo);
	}

	/**
	 * ECS 계약서 조회
	 * @param paramVo
	 * @return NEDMPRO0300VO
	 * @throws Exception
	 */
	public NEDMPRO0300VO selectEcsDocInfo(NEDMPRO0300VO paramVo) throws Exception{
		return (NEDMPRO0300VO)getSqlMapClientTemplate().queryForObject("NEDMPRO0300.selectEcsDocInfo", paramVo);
	}
	
}

