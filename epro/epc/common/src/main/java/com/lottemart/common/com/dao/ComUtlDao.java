package com.lottemart.common.com.dao;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;

@Repository("comUtlDao")
public class ComUtlDao {
	
	@Autowired
	private SqlMapClient sqlMapClient;
	
	/**
     * Desc : 트랜잭션중 강제커밋
     * @Method Name : commit
     * @param 
     * @return
     * @throws SQLException    
     */
	public void commit() throws SQLException {
		sqlMapClient.getDataSource().getConnection().commit();
	}
}
