package com.lottemart.epc.edi.buy.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lcn.module.common.util.HashBox;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;


@Repository("pedmbuy0020Dao")
public class PEDMBUY0020Dao extends SqlMapClientDaoSupport {
	
	@Autowired
	public PEDMBUY0020Dao(SqlMapClient sqlMapClient) {
		super();
		setSqlMapClient(sqlMapClient);
	}

	
	public List selectRejectInfo(Map<String,Object> map) throws Exception{
		return (List<HashBox>)getSqlMapClientTemplate().queryForList("PEDMBUY0020.TSC_REJECT-SELECT01",map);
	}
	
	public HashMap selectRejectPopupInfo(Map<String,Object> map) throws Exception{
		return (HashMap)getSqlMapClientTemplate().queryForObject("PEDMBUY0020.TSC_REJECT_POPUP-SELECT01",map);
	}
	
	public void updateRejectPopup(Map<String,Object> map) throws Exception{
		getSqlMapClientTemplate().update("PEDMBUY0020.TSC_REJECT_POPUP-UPDATE01",map);
	}
	
	
}
