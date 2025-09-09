package com.lottemart.epc.edi.product.dao;

import java.util.List;
import java.util.Map;

import lcn.module.common.util.HashBox;
import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;
import com.lottemart.epc.edi.product.model.PEDMPRO0005VO;
import com.lottemart.epc.edi.product.model.SearchProduct;


@Repository("pEDMPRO0005Dao")
public class PEDMPRO0005Dao  extends AbstractDAO  {


	public List selectBarcodeList(Map<String,Object> map) {
		// TODO Auto-generated method stub
		return (List<HashBox>)getSqlMapClientTemplate().queryForList("PEDMPRO0005.selectBarcodeList", map);
	} 

	
}
