package com.lottemart.epc.edi.buy.dao;

import java.util.List;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.epc.edi.buy.model.NEDMBUY0010VO;

/**
 * @Class Name : NEDMBUY0010Dao
 * @Description : 매입정보 일자별 조회 Dao Class
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      		수정자          		수정내용
 *  -------    --------    ---------------------------
 * 2015.11.16	O YEUN KWON	  최초생성
 * 
 * @Copyright (C) 2000 ~ 2015 lottemart All right reserved.
 * </pre>
 */


@Repository("nedmbuy0010Dao")
public class NEDMBUY0010Dao extends AbstractDAO {
	
	/**
	 * 조회
	 * @param nEDMBUY0010VO
	 * @return
	 * @throws Exception
	 */
	public List selectBuyInfo(NEDMBUY0010VO nEDMBUY0010VO) throws Exception{
		return (List<NEDMBUY0010VO>)getSqlMapClientTemplate().queryForList("NEDMBUY0010.TSC_BUY-SELECT01",nEDMBUY0010VO);
	}
	
	
	
	
}
