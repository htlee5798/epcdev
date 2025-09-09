package com.lottemart.epc.edi.inventory.dao;

import java.util.List;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.epc.edi.inventory.model.NEDMINV0030VO;

/**
 * @Class Name : NEDMINV0030Dao
 * @Description : 재고정보 센터 점출입 조회 Dao Class
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      		수정자          		수정내용
 *  -------    --------    ---------------------------
 * 2015.11.18	O YEUN KWON	  최초생성
 * 
 * @Copyright (C) 2000 ~ 2015 lottemart All right reserved.
 * </pre>
 */

@Repository("nedminv0030Dao")
public class NEDMINV0030Dao extends AbstractDAO {

	
	@SuppressWarnings("unchecked")
	public List<NEDMINV0030VO> selectCenterStoreInfo(NEDMINV0030VO nEDMINV0030VO) throws Exception{
		return (List<NEDMINV0030VO>)getSqlMapClientTemplate().queryForList("NEDMINV0030.TSC_CENTER_STORE-SELECT01", nEDMINV0030VO);
	}
	
}