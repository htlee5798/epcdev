package com.lottemart.epc.edi.product.dao;

import java.util.List;
import java.util.Map;

import lcn.module.common.util.HashBox;
import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;
import com.lottemart.epc.edi.product.model.PEDMPRO0005VO;
import com.lottemart.epc.edi.product.model.SearchProduct;


@Repository("NEDMPRO0210Dao")
public class NEDMPRO0210Dao  extends AbstractDAO  {

	
	/**
	 * 물류 바코드 현황 리스트 조회
	 * @param map
	 * @return
	 */
	public List selectBarcodeList_2(Map<String,Object> map) {
		// TODO Auto-generated method stub
		return (List<HashBox>)getSqlMapClientTemplate().queryForList("NEDMPRO0210.selectBarcodeList_2", map);
	}
	
}
