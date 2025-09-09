package com.lottemart.epc.edi.buy.dao;

import java.util.List;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.epc.edi.buy.model.NEDMBUY0030VO;

/**
 * @Class Name : NEDMBUY0030Dao
 * @Description : 매입정보 상품별 조회 Dao Class
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


@Repository("nedmbuy0030Dao")
public class NEDMBUY0030Dao extends AbstractDAO {
	
//	@Autowired
//	public PEDMBUY0000Dao(SqlMapClient sqlMapClient) {
//		super();
//		setSqlMapClient(sqlMapClient);
//	}
	
	public List selectProductInfo(NEDMBUY0030VO nEDMBUY0030VO) throws Exception{
		return (List<NEDMBUY0030VO>)getSqlMapClientTemplate().queryForList("NEDMBUY0030.TSC_PRODUCT-SELECT01",nEDMBUY0030VO);
	}
	
	
	
	
}
