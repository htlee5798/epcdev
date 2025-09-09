package com.lottemart.epc.edi.buy.dao;

import java.util.List;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.epc.edi.buy.model.NEDMBUY0050VO;

/**
 * @Class Name : NEDMBUY0050Dao
 * @Description : 매입정보 전표상세별 조회 Dao Class
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


@Repository("nedmbuy0050Dao")
public class NEDMBUY0050Dao extends AbstractDAO {
	
//	@Autowired
//	public PEDMBUY0000Dao(SqlMapClient sqlMapClient) {
//		super();
//		setSqlMapClient(sqlMapClient);
//	}
	
	public List selectJunpyoDetailInfo(NEDMBUY0050VO nEDMBUY0050VO) throws Exception{
		return (List<NEDMBUY0050VO>)getSqlMapClientTemplate().queryForList("NEDMBUY0050.TSC_JUNPYO_DETAIL-SELECT01",nEDMBUY0050VO);
	}
	
	
	
	
}
