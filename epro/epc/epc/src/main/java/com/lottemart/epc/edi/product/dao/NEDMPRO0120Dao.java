package com.lottemart.epc.edi.product.dao;

import java.util.List;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Component;

import com.lottemart.epc.edi.product.model.NEDMPRO0120VO;

@Component("nedmpro0120Dao")
public class NEDMPRO0120Dao extends AbstractDAO {
	
	/**
	 * Count
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int selectWholeStoreProductCount(NEDMPRO0120VO vo) throws Exception {
		return (Integer) this.selectByPk("NEDMPRO0120.selectWholeStoreProductCount", vo);
	}
	
	/**
	 * List 조회
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<NEDMPRO0120VO> selectWholeStoreProductList(NEDMPRO0120VO vo) throws Exception {
		return this.list("NEDMPRO0120.selectWholeStoreProductList", vo);
	}

}
