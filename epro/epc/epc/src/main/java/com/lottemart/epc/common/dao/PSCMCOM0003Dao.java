package com.lottemart.epc.common.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.lottemart.common.util.DataMap;

@Repository("PSCMCOM0003Dao")
public class PSCMCOM0003Dao extends AbstractDAO  {

	@SuppressWarnings("unchecked")
	public List<DataMap> selectDeliCodePopup() throws SQLException{
		return (List<DataMap>)getSqlMapClientTemplate().queryForList("PSCMCOM0003.selectDeliCodePopup");
	}
}




