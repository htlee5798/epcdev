package com.lottemart.epc.edi.buy.dao;

import java.util.List;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.epc.edi.buy.model.NEDMBUY0020VO;

/**
 * @Class Name : NEDMBUY0020Dao
 * @Description : 매입정보 점포별 조회 Dao Class
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


@Repository("nedmbuy0020Dao")
public class NEDMBUY0020Dao extends AbstractDAO {
	
//	@Autowired
//	public PEDMBUY0000Dao(SqlMapClient sqlMapClient) {
//		super();
//		setSqlMapClient(sqlMapClient);
//	}
	
	public List selectStoreInfo(NEDMBUY0020VO nEDMBUY0020VO) throws Exception{
		return (List<NEDMBUY0020VO>)getSqlMapClientTemplate().queryForList("NEDMBUY0020.TSC_STORE-SELECT01",nEDMBUY0020VO);
	}
	
	
	
	
}
