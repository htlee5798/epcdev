package com.lottemart.epc.delivery.dao;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.lottemart.common.util.DataMap;

@Repository("PSCMDLV0001Dao")
public class PSCMDLV0001Dao {

	@Autowired
	private SqlMapClient sqlMapClient;

	@SuppressWarnings("unchecked")
	public List<DataMap> selectVenStatusList(DataMap paramMap) throws SQLException{
		return (List<DataMap>)sqlMapClient.queryForList("PSCMDLV0001.selectVenStatusList", paramMap);
	}
	
}
