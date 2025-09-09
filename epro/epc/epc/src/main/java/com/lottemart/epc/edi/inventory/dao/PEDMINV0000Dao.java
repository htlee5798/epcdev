package com.lottemart.epc.edi.inventory.dao;

import java.util.List;
import java.util.Map;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.epc.edi.inventory.model.PEDMINV0000VO;


@Repository("pedminv0000Dao")
public class PEDMINV0000Dao extends AbstractDAO {

	
	public List<PEDMINV0000VO> selectStoreInfo(Map<String,Object> map) throws Exception{
		return (List<PEDMINV0000VO>)getSqlMapClientTemplate().queryForList("PEDMINV0000.TSC_STORE-SELECT01",map);
	}
	
	public List<PEDMINV0000VO> selectProductInfo(Map<String,Object> map) throws Exception{
		return (List<PEDMINV0000VO>)getSqlMapClientTemplate().queryForList("PEDMINV0000.TSC_PRODUCT-SELECT01",map);
	}
	
	public List<PEDMINV0000VO> selectProductInfoText(Map<String,Object> map) throws Exception{
		return (List<PEDMINV0000VO>)getSqlMapClientTemplate().queryForList("PEDMINV0000.TSC_PRODUCT-SELECT02",map);
	}
	
	public List<PEDMINV0000VO> selectCenterStoreInfo(Map<String,Object> map) throws Exception{
		return (List<PEDMINV0000VO>)getSqlMapClientTemplate().queryForList("PEDMINV0000.TSC_CENTER_STORE-SELECT01",map);
	}
	
	public List<PEDMINV0000VO> selectCenterStoreDetailInfo(Map<String,Object> map) throws Exception{
		return (List<PEDMINV0000VO>)getSqlMapClientTemplate().queryForList("PEDMINV0000.TSC_CENTER_STORE_DETAIL-SELECT01",map);
	}
	
	
}