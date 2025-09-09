package com.lottemart.epc.delivery.dao;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.lottemart.common.util.DataMap;

import lcn.module.framework.base.AbstractDAO;

@Repository("PSCMDLV0012Dao")
public class PSCMDLV0012Dao extends AbstractDAO{
	@Autowired
	private SqlMapClient sqlMapClient;
	public Object selectDeliHistList;
	
	@SuppressWarnings("unchecked")
	public List<DataMap> selectPartnerNoFirmsOrderList(DataMap paramMap) throws SQLException {
		return (List<DataMap>)sqlMapClient.queryForList("PSCMDLV0012.selectPartnerNoFirmsOrderList", paramMap);
	}
}
