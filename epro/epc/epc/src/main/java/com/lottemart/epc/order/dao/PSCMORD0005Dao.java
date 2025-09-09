package com.lottemart.epc.order.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lottemart.common.util.DataMap;

import lcn.module.framework.base.AbstractDAO;

@Repository("PSCMORD0005Dao")
public class PSCMORD0005Dao extends AbstractDAO {

	public List<DataMap> selectSaleInfoByDate(Map<String, Object> map) throws Exception {
		return (List<DataMap>) getSqlMapClientTemplate().queryForList("PSCMORD0005.selectSaleInfoByDate", map);
	}
}
