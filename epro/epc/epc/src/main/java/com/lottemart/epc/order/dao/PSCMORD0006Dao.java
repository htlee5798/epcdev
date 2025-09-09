package com.lottemart.epc.order.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lottemart.common.util.DataMap;

import lcn.module.framework.base.AbstractDAO;

@Repository("PSCMORD0006Dao")
public class PSCMORD0006Dao extends AbstractDAO {

	public List<DataMap> selectSaleInfoByStore(Map<String, Object> map) throws Exception {
		return (List<DataMap>) getSqlMapClientTemplate().queryForList("PSCMORD0006.selectSaleInfoByStore", map);
	}

}
