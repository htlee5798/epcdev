package com.lottemart.epc.edi.comm.dao;

import java.util.List;
import java.util.Map;

import lcn.module.common.util.HashBox;
import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;


@Repository("PEDPCOM0002Dao")
public class PEDPCOM0002Dao extends AbstractDAO {

	
	@SuppressWarnings("unchecked")
	public List selectProduct(Map<String, Object> map ) throws Exception	{
		return (List<HashBox>)getSqlMapClientTemplate().queryForList("common.PRODUCT_SELECT",map);
	}
}
