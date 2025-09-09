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
import com.lottemart.epc.edi.weborder.model.EdiRtnProdVO;
import com.lottemart.epc.edi.weborder.model.SearchWebOrder;



@Repository("pedmweb0016Dao")
public class PEDMWEB0016Dao extends AbstractDAO {
		
	
	

	/**
	 *  반품전제 현황 내역 조회
	 * @param SearchWebOrder
	 * @return List<EdiRtnProdVO>
	 * @throws SQLException
	 */
	public List<EdiRtnProdVO> selectVenRtnInfo(SearchWebOrder vo) throws Exception{
		
		return (List<EdiRtnProdVO>)getSqlMapClientTemplate().queryForList("PEDMWEB0016.EDI_RETURN_SELECT_01",vo);
	}
	
	
	/**
	 *  반품전제 현황 내역 조회 전제 카운트 
	 * @param SearchWebOrder
	 * @return int
	 * @throws SQLException
	 */
	public int selectVendRtnListTotCnt(SearchWebOrder vo) {

		return ((Integer) getSqlMapClientTemplate().queryForObject("PEDMWEB0016.EDI_RETURN_SELECT_02", vo)).intValue();
		
	}
	

	
	
}
