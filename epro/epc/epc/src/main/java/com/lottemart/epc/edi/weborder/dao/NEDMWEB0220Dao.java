package com.lottemart.epc.edi.weborder.dao;

import java.sql.SQLException;
import java.util.List;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.epc.edi.weborder.model.NEDMWEB0220VO;

/**
 * @Class Name : NEDMWEB0220Dao
 * @Description : 발주전체현황 Dao Class
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      		수정자          		수정내용
 *  -------    --------    ---------------------------
 * 2015.12.09	O YEUN KWON	  최초생성
 * 
 * @Copyright (C) 2000 ~ 2015 lottemart All right reserved.
 * </pre>
 */


@Repository("nedmweb0220Dao")
public class NEDMWEB0220Dao extends AbstractDAO {
		
	
	
	/**
	 *  발주전제 현황 내역 조회
	 * @param SearchWebOrder
	 * @return List<TedOrdList>
	 * @throws SQLException
	 */
	public List<NEDMWEB0220VO> selectVenOrdAllInfo(NEDMWEB0220VO vo) throws Exception{
		return (List<NEDMWEB0220VO>)getSqlMapClientTemplate().queryForList("NEDMWEB0220.VEND_SELECT01",vo);
	}
	

	/**
	 *  발주전제 현황 내역 조회 전제 카운트 
	 * @param SearchWebOrder
	 * @return int
	 * @throws SQLException
	 */
	public int selectVendOrdAllListTotCnt(NEDMWEB0220VO vo) {
		return ((Integer) getSqlMapClientTemplate().queryForObject("NEDMWEB0220.VEND_SELECT_TOT_CNT_01", vo)).intValue();
	}
	

	
	
	
	
}
