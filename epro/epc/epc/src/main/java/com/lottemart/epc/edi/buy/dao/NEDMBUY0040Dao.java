package com.lottemart.epc.edi.buy.dao;

import java.util.List;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.epc.edi.buy.model.NEDMBUY0040VO;

/**
 * @Class Name : NEDMBUY0040Dao
 * @Description : 매입정보 전표별 조회 Dao Class
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


@Repository("nedmbuy0040Dao")
public class NEDMBUY0040Dao extends AbstractDAO {
	
//	@Autowired
//	public PEDMBUY0000Dao(SqlMapClient sqlMapClient) {
//		super();
//		setSqlMapClient(sqlMapClient);
//	}
	
	public List selectJunpyoInfo(NEDMBUY0040VO nEDMBUY0040VO) throws Exception{
		return (List<NEDMBUY0040VO>)getSqlMapClientTemplate().queryForList("NEDMBUY0040.TSC_JUNPYO-SELECT01",nEDMBUY0040VO);
	}
	
	
	
	
}
