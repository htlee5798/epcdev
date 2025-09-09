package com.lottemart.epc.edi.consult.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lcn.module.common.util.HashBox;
import lcn.module.framework.base.AbstractDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;


@Repository("pedmsct0005Dao")
public class PEDMSCT0005Dao extends AbstractDAO {
	
	public List selectVenCd(Map<String,Object> map) throws Exception{
		return (List<HashBox>)getSqlMapClientTemplate().queryForList("PEDMSCT0005.TSC_VENDOR-SELECT01",map);
	}
	
	public List alertPageInsertPageSelect(Map<String,Object> map) throws Exception{
		return (List<HashBox>)getSqlMapClientTemplate().queryForList("PEDMSCT0005.TSC_ALERT_PAGE-SELECT01",map);
	}
	
	public Integer ajaxEmailCk(Map<String,Object> map) throws Exception{
		return (Integer)getSqlMapClientTemplate().queryForObject("PEDMSCT0005.TSC_EMAIL_CK_AJAX-SELECT01",map);
	}
	
	public Integer ajaxEmailCkUP(Map<String,Object> map) throws Exception{
		return (Integer)getSqlMapClientTemplate().queryForObject("PEDMSCT0005.TSC_EMAIL_CK_AJAX-SELECT02",map);
	}
	
	public Integer ajaxCellCk(Map<String,Object> map) throws Exception{
		return (Integer)getSqlMapClientTemplate().queryForObject("PEDMSCT0005.TSC_CELL_CK_AJAX-SELECT01",map);
	}
	
	public Integer ajaxCellCkUP(Map<String,Object> map) throws Exception{
		return (Integer)getSqlMapClientTemplate().queryForObject("PEDMSCT0005.TSC_CELL_CK_AJAX-SELECT02",map);
	}
	
	public List ajaxVendor(Map<String,Object> map) throws Exception{
		return (List<HashBox>)getSqlMapClientTemplate().queryForList("PEDMSCT0005.TSC_VENDOR-SELECT01",map);
	}
	
	public List ajaxVendorCK(Map<String,Object> map) throws Exception{
		return (List<HashBox>)getSqlMapClientTemplate().queryForList("PEDMSCT0005.TSC_VENDOR-SELECT02",map);
	}
	
	public HashMap getAjaxCount(Map<String,Object> map) throws Exception{
		return (HashMap)getSqlMapClientTemplate().queryForObject("PEDMSCT0005.TSC_COUNT_ID-SELECT01",map);
	}
	
	public void alertPageInsertID(Map<String,Object> map) throws Exception{
		getSqlMapClientTemplate().insert("PEDMSCT0005.TSC_ALERT_PAGE_ID-INSERT01",map);
	}
	
	public String alertPageInsertIDSeq(Map<String,Object> map) throws Exception{
		return (String)getSqlMapClientTemplate().queryForObject("PEDMSCT0005.ALERT_ID_SEQ-SELECT01",map);
	}
	
	public void alertPageInsertUser(Map<String,Object> map) throws Exception{
		getSqlMapClientTemplate().insert("PEDMSCT0005.TSC_ALERT_PAGE_USER-INSERT01",map);
	}
	
	public HashMap alertPageUpdatePage(Map<String,Object> map) throws Exception{
		return (HashMap)getSqlMapClientTemplate().queryForObject("PEDMSCT0005.TSC_ALERT_UPDATE_PAGE-SELECT01",map);
	}
	
	public void alertPageUpdateID(Map<String,Object> map) throws Exception{
		getSqlMapClientTemplate().update("PEDMSCT0005.TSC_ALERT_PAGE_ID-UPDATE01",map);
	}
	
	
	public void alertPageDeleteID(Map<String,Object> map) throws Exception{
		getSqlMapClientTemplate().delete("PEDMSCT0005.TSC_ALERT_PAGE_ID-DELETE01",map);
	}
	
	public void alertPageDeleteUser(Map<String,Object> map) throws Exception{
		getSqlMapClientTemplate().delete("PEDMSCT0005.TSC_ALERT_PAGE_USER-DELETE01",map);
	}
	
	
}




