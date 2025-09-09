package com.lottemart.epc.edi.inventory.dao;

import java.util.List;
import java.util.Map;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.epc.edi.inventory.model.NEDMINV0010VO;
import com.lottemart.epc.edi.inventory.model.PEDMINV0000VO;

/**
 * @Class Name : NEDMINV0010Dao
 * @Description : 재고정보 현재고(점포) 조회 Dao Class
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

@Repository("nedminv0010Dao")
public class NEDMINV0010Dao extends AbstractDAO {

	
	@SuppressWarnings("unchecked")
	public List<NEDMINV0010VO> selectStoreInfo(NEDMINV0010VO nEDMINV0010VO) throws Exception{
		return (List<NEDMINV0010VO>)getSqlMapClientTemplate().queryForList("NEDMINV0010.TSC_STORE-SELECT01", nEDMINV0010VO);
	}
	
}