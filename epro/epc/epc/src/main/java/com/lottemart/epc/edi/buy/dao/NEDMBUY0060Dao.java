package com.lottemart.epc.edi.buy.dao;

import java.util.List;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.epc.edi.buy.model.NEDMBUY0060VO;

/**
 * @Class Name : NEDMBUY0060Dao
 * @Description : 매입정보 점포상품별 조회 Dao Class
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      		수정자          		수정내용
 *  -------    --------    ---------------------------
 * 2015.11.17	O YEUN KWON	  최초생성
 * 
 * @Copyright (C) 2000 ~ 2015 lottemart All right reserved.
 * </pre>
 */


@Repository("nedmbuy0060Dao")
public class NEDMBUY0060Dao extends AbstractDAO {
	
	/**
	 * 리스트 total count
	 * @param nEDMBUY0060VO
	 * @return
	 * @throws Exception
	 */
	public int selectStoreProductInfoTotCnt(NEDMBUY0060VO nEDMBUY0060VO) throws Exception{
		return (Integer) getSqlMapClientTemplate().queryForObject("NEDMBUY0060.TSC_STORE_PRODUCT-SELECT01_CNT", nEDMBUY0060VO);
	} 
	
	/**
	 * 리스트 조회
	 * @param nEDMBUY0060VO
	 * @return
	 * @throws Exception
	 */
	public List selectStoreProductInfo(NEDMBUY0060VO nEDMBUY0060VO) throws Exception{
		return (List<NEDMBUY0060VO>)getSqlMapClientTemplate().queryForList("NEDMBUY0060.TSC_STORE_PRODUCT-SELECT01",nEDMBUY0060VO);
	}
	
}
