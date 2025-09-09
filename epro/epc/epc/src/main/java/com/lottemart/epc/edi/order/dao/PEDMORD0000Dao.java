package com.lottemart.epc.edi.order.dao;

import java.util.List;
import java.util.Map;

import lcn.module.common.util.HashBox;
import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.common.util.DataMap;


@Repository("pedmord0000Dao")
public class PEDMORD0000Dao extends AbstractDAO {
	
//	@Autowired
//	public PEDMORD0000Dao(SqlMapClient sqlMapClient) {
//		super();
//		setSqlMapClient(sqlMapClient);
//	}

	
	public List selectPeriodInfo(Map<String,Object> map) throws Exception{
		return (List)getSqlMapClientTemplate().queryForList("PEDMORD0000.TSC_ORD_PROD-SELECT01",map);
	}
	
	public List selectJunpyoInfo(Map<String,Object> map) throws Exception{
		return (List<HashBox>)getSqlMapClientTemplate().queryForList("PEDMORD0000.TSC_ORD-SELECT01",map);
	}
	
	public List selectJunpyoDetailInfo(Map<String,Object> map) throws Exception{
		return (List<HashBox>)getSqlMapClientTemplate().queryForList("PEDMORD0000.TSC_JUNPYO_DETAIL-SELECT01",map);
	}
	
	public List selectStoreInfo(Map<String,Object> map) throws Exception{
		return (List<HashBox>)getSqlMapClientTemplate().queryForList("PEDMORD0000.TSC_STORE-SELECT01",map);
	}
	
	public List selectJunpyoDetailInfoPDC(Map<String,Object> map) throws Exception{
		return (List<HashBox>)getSqlMapClientTemplate().queryForList("PEDMORD0000.TSC_JUNPYO_DETAIL_PDC-SELECT01",map);
	}
}
