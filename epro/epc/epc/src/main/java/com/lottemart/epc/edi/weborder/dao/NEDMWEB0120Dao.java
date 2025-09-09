package com.lottemart.epc.edi.weborder.dao;


import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.lottemart.epc.edi.weborder.model.NEDMWEB0120VO;


@Repository("nedmweb0120Dao")
public class NEDMWEB0120Dao extends AbstractDAO{


	/**
	 *  N 당일 반품등록 내역 조회 
	 * @param NEDMWEB0120VO
	 * @return List<NEDMWEB0120VO>
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<NEDMWEB0120VO> selectDayRtnProdList(NEDMWEB0120VO vo)  throws Exception{
		// TODO Auto-generated method stub
		return (List<NEDMWEB0120VO>)getSqlMapClientTemplate().queryForList("NEDMWEB0120.EDI_TPC_PO_RRL_PROD_01", vo);
	}
	
	public void EDI_TPC_PO_RRL_MST_01(NEDMWEB0120VO vo) throws Exception{
		getSqlMapClientTemplate().update("NEDMWEB0120.EDI_TPC_PO_RRL_MST_01", vo);
		
	}
	
	public void EDI_TPC_PO_RRL_MST_02(NEDMWEB0120VO vo) throws Exception{
		getSqlMapClientTemplate().delete("NEDMWEB0120.EDI_TPC_PO_RRL_MST_02", vo);
	}
	
	public void EDI_TPC_PO_RRL_PROD_02(NEDMWEB0120VO vo) throws Exception{
		getSqlMapClientTemplate().update("NEDMWEB0120.EDI_TPC_PO_RRL_PROD_02", vo);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map> selectRfcReqData(Map vo)  throws Exception{
		// TODO Auto-generated method stub
		return (List<Map> )getSqlMapClientTemplate().queryForList("NEDMWEB0120.selectRfcReqData", vo);
	}
	
	public void updateRfcReqData(Map vo) throws Exception{
		getSqlMapClientTemplate().update("NEDMWEB0120.updateRfcReqData", vo);
	}
	
	
	public void EDI_TPC_PO_RRL_PROD_03(NEDMWEB0120VO vo) throws Exception{
		getSqlMapClientTemplate().delete("NEDMWEB0120.EDI_TPC_PO_RRL_PROD_03", vo);
	}

}
