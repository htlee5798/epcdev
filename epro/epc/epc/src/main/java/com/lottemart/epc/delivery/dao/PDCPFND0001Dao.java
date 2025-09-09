package com.lottemart.epc.delivery.dao;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.lottemart.common.util.DataMap;


@Repository("PDCPFND0001Dao")
public class PDCPFND0001Dao {

	@Autowired
	private SqlMapClient sqlMapClient;
	

	@SuppressWarnings("unchecked")
	public List<DataMap> selectAcceptList(DataMap paramMap) throws SQLException{
		return (List<DataMap>)sqlMapClient.queryForList("PDCPFND0001.selectAcceptList", paramMap);
	}
	
	public DataMap selectStoreDetailInfo(String strCd) throws SQLException{
		return (DataMap)sqlMapClient.queryForObject("PDCPFND0001.selectStoreDetailInfo", strCd);
	}
}
