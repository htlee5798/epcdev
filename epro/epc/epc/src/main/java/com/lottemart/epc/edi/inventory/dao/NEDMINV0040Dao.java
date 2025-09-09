package com.lottemart.epc.edi.inventory.dao;

import java.util.List;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.epc.edi.inventory.model.NEDMINV0040VO;

/**
 * @Class Name : NEDMINV0040Dao
 * @Description : 재고정보 센터 점출입 상세 조회 Dao Class
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

@Repository("nedminv0040Dao")
public class NEDMINV0040Dao extends AbstractDAO {

	
	@SuppressWarnings("unchecked")
	public List<NEDMINV0040VO> selectCenterStoreDetailInfo(NEDMINV0040VO nEDMINV0040VO) throws Exception{
		return (List<NEDMINV0040VO>)getSqlMapClientTemplate().queryForList("NEDMINV0040.TSC_CENTER_STORE_DETAIL-SELECT01", nEDMINV0040VO);
	}
	
}