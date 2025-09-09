package com.lottemart.epc.edi.weborder.dao;

import java.util.List;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.epc.edi.weborder.model.NEDMWEB0040VO;

/**
 * @Class Name : NEDMWEB0040Dao
 * @Description : 발주전체현황 Dao Class
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      		수정자          		수정내용
 *  -------    --------    ---------------------------
 * 2015.12.08	O YEUN KWON	  최초생성
 * 
 * @Copyright (C) 2000 ~ 2015 lottemart All right reserved.
 * </pre>
 */

@Repository("nedmweb0040Dao")
public class NEDMWEB0040Dao extends AbstractDAO {

	@SuppressWarnings("deprecation")
	public int selectOrdTotListTotCnt(NEDMWEB0040VO vo) {
		return ((Integer) getSqlMapClientTemplate().queryForObject("NEDMWEB0040.selectOrdTotListTotCnt", vo)).intValue();
	}
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<NEDMWEB0040VO> selectOrdTotList(NEDMWEB0040VO vo) {
		return (List<NEDMWEB0040VO>)getSqlMapClientTemplate().queryForList("NEDMWEB0040.selectOrdTotList", vo);
	}
	
	@SuppressWarnings({ "deprecation" })
	public NEDMWEB0040VO selectOrdTotCntSum(NEDMWEB0040VO vo) {
		return (NEDMWEB0040VO)getSqlMapClientTemplate().queryForObject("NEDMWEB0040.selectOrdTotCntSum", vo);
	}
}
