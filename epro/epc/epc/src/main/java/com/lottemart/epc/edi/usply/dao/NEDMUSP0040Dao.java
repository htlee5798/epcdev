package com.lottemart.epc.edi.usply.dao;

import java.util.List;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.epc.edi.usply.model.NEDMUSP0040VO;


@Repository("nedmusp0040Dao")
public class NEDMUSP0040Dao extends AbstractDAO {
	/**
	 * 미납정보 - > 기간정보  - > 상품상세 첫페이지
	 * @param NEDMUSP0040VO
	 * @return
	 */
	public List<NEDMUSP0040VO> selectProductDetailInfo(NEDMUSP0040VO map) throws Exception{
		return (List<NEDMUSP0040VO>)getSqlMapClientTemplate().queryForList("NEDMUSP0040.TSC_PRODUCT_DETAIL-SELECT01",map);
	}
	
	
	
	
}
