package com.lottemart.epc.edi.usply.dao;

import java.util.List;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.epc.edi.usply.model.NEDMUSP0030VO;


@Repository("nedmusp0030Dao")
public class NEDMUSP0030Dao extends AbstractDAO {
	/**
	 * 미납정보 - > 기간정보  - > 상품별 조회
	 * @param NEDMUSP0030VO
	 * @return
	 */
	public List<NEDMUSP0030VO> selectProductInfo(NEDMUSP0030VO map) throws Exception{
		return (List<NEDMUSP0030VO>)getSqlMapClientTemplate().queryForList("NEDMUSP0030.TSC_PRODUCT-SELECT01",map);
	}
	
	
	
	
}
