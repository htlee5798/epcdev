package com.lottemart.epc.edi.inventory.dao;

import java.util.List;
import java.util.Map;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.epc.edi.inventory.model.NEDMINV0020VO;
import com.lottemart.epc.edi.inventory.model.PEDMINV0000VO;

/**
 * @Class Name : NEDMINV0020Dao
 * @Description : 재고정보 현재고(상품) 조회 Dao Class
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

@Repository("nedminv0020Dao")
public class NEDMINV0020Dao extends AbstractDAO {

	
	@SuppressWarnings("unchecked")
	public List<NEDMINV0020VO> selectProductInfo(NEDMINV0020VO nEDMINV0020VO) throws Exception{
		return (List<NEDMINV0020VO>)getSqlMapClientTemplate().queryForList("NEDMINV0020.TSC_PRODUCT-SELECT01", nEDMINV0020VO);
	}
	
	public List<NEDMINV0020VO> selectProductInfoText(NEDMINV0020VO nEDMINV0020VO) throws Exception{
		return (List<NEDMINV0020VO>)getSqlMapClientTemplate().queryForList("NEDMINV0020.TSC_PRODUCT-SELECT02", nEDMINV0020VO);
	}
}