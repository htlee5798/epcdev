package com.lottemart.epc.edi.buy.dao;

import java.util.List;
import java.util.Map;

import lcn.module.common.util.HashBox;
import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;


@Repository("pedmbuy0000Dao")
public class PEDMBUY0000Dao extends AbstractDAO {
	
//	@Autowired
//	public PEDMBUY0000Dao(SqlMapClient sqlMapClient) {
//		super();
//		setSqlMapClient(sqlMapClient);
//	}

	
	public List selectBuyInfo(Map<String,Object> map) throws Exception{
		return (List<HashBox>)getSqlMapClientTemplate().queryForList("PEDMBUY0000.TSC_BUY-SELECT01",map);
	}
	
	public List selectStoreInfo(Map<String,Object> map) throws Exception{
		return (List<HashBox>)getSqlMapClientTemplate().queryForList("PEDMBUY0000.TSC_STORE-SELECT01",map);
	}
	
	public List selectProductInfo(Map<String,Object> map) throws Exception{
		return (List<HashBox>)getSqlMapClientTemplate().queryForList("PEDMBUY0000.TSC_PRODUCT-SELECT01",map);
	}
	
	public List selectJunpyoInfo(Map<String,Object> map) throws Exception{
		return (List<HashBox>)getSqlMapClientTemplate().queryForList("PEDMBUY0000.TSC_JUNPYO-SELECT01",map);
	}
	
	public List selectJunpyoDetailInfo(Map<String,Object> map) throws Exception{
		return (List<HashBox>)getSqlMapClientTemplate().queryForList("PEDMBUY0000.TSC_JUNPYO_DETAIL-SELECT01",map);
	}
	
	public List selectStoreProductInfo(Map<String,Object> map) throws Exception{
		return (List<HashBox>)getSqlMapClientTemplate().queryForList("PEDMBUY0000.TSC_STORE_PRODUCT-SELECT01",map);
	}
	
	public List selectStorePurchaseInfo(Map<String,Object> map) throws Exception{
		return (List<HashBox>)getSqlMapClientTemplate().queryForList("PEDMBUY0000.TSC_STORE_PURCHASE-SELECT01",map);
	}
	
	public List selectGiftInfo(Map<String,Object> map) throws Exception{
		return (List<HashBox>)getSqlMapClientTemplate().queryForList("PEDMBUY0000.TSC_GIFT-SELECT01",map);
	}
	
	
}
