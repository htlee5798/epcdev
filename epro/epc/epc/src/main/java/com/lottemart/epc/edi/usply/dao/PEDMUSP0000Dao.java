package com.lottemart.epc.edi.usply.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lcn.module.common.util.HashBox;
import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;


@Repository("pedmusp0000Dao")
public class PEDMUSP0000Dao extends AbstractDAO {
	
//	@Autowired
//	public PEDMUSP0000Dao(SqlMapClient sqlMapClient) {
//		super();
//		setSqlMapClient(sqlMapClient);
//	}

	
	public List selectDayInfo(Map<String,Object> map) throws Exception{
		return (List<HashBox>)getSqlMapClientTemplate().queryForList("PEDMUSP0000.TSC_DAY-SELECT01",map);
	}
	
	public List selectStoreInfo(Map<String,Object> map) throws Exception{
		return (List<HashBox>)getSqlMapClientTemplate().queryForList("PEDMUSP0000.TSC_STORE-SELECT01",map);
	}
	
	public List selectProductInfo(Map<String,Object> map) throws Exception{
		return (List<HashBox>)getSqlMapClientTemplate().queryForList("PEDMUSP0000.TSC_PRODUCT-SELECT01",map);
	}
	
	public List selectProductDetailInfo(Map<String,Object> map) throws Exception{
		return (List<HashBox>)getSqlMapClientTemplate().queryForList("PEDMUSP0000.TSC_PRODUCT_DETAIL-SELECT01",map);
	}
	
	public List selectUsplyReasonInfo(Map<String,Object> map) throws Exception{
		return (List<HashBox>)getSqlMapClientTemplate().queryForList("PEDMUSP0000.TSC_USPLY_REASON-SELECT01",map);
	}
	
	public List selectPenaltyInfo(Map<String,Object> map) throws Exception{
		return (List<HashBox>)getSqlMapClientTemplate().queryForList("PEDMUSP0000.TSC_PENALTY-SELECT01",map);
	}
	
	public void selectUsplyReasonUpdate(HashMap<String,Object> map) throws Exception{
		getSqlMapClientTemplate().update("PEDMUSP0000.TSC_USPLY_REASON-UPDATE01", map);
	}
	
	
}
