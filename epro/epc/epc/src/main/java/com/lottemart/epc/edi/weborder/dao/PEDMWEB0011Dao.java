package com.lottemart.epc.edi.weborder.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import lcn.module.framework.base.AbstractDAO;

import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.lottemart.epc.edi.weborder.model.SearchWebOrder;
import com.lottemart.epc.edi.weborder.model.TedOrdList;




@Repository("pedmweb0011Dao")
public class PEDMWEB0011Dao extends AbstractDAO {
		
	
	
	/**
	 *  발주전제 현황 내역 조회
	 * @param SearchWebOrder
	 * @return List<TedOrdList>
	 * @throws SQLException
	 */
	public List<TedOrdList> selectVenOrdAllInfo(SearchWebOrder vo) throws Exception{
		return (List<TedOrdList>)getSqlMapClientTemplate().queryForList("PEDMWEB0011.VEND_SELECT01",vo);
	}
	

	/**
	 *  발주전제 현황 내역 조회 전제 카운트 
	 * @param SearchWebOrder
	 * @return int
	 * @throws SQLException
	 */
	public int selectVendOrdAllListTotCnt(SearchWebOrder vo) {
		return ((Integer) getSqlMapClientTemplate().queryForObject("PEDMWEB0011.VEND_SELECT_TOT_CNT_01", vo)).intValue();
	}
	

	
	
	
	
}
