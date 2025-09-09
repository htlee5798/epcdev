package com.lottemart.epc.edi.consult.dao;

import java.util.List;
import java.util.Map;

import lcn.module.common.util.HashBox;
import lcn.module.framework.base.AbstractDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.lottemart.epc.edi.comm.model.EdiCommonCode;
import com.lottemart.epc.edi.comm.model.SearchParam;


@Repository("pedmsct0002Dao")
public class PEDMSCT0002Dao extends AbstractDAO {
	
	public List consultAdminSelect(Map<String,Object> map) throws Exception{
		return (List<HashBox>)getSqlMapClientTemplate().queryForList("PEDMSCT0002.TSC_CONSULT_ADMIN-SELECT01",map);
	}
	
	public void papeUpdate1(Map<String,Object> map) throws Exception{
		getSqlMapClientTemplate().update("PEDMSCT0002.TSC_CONSULT_ADMIN_PAPE-UPDATE01",map);
	}
	
	public void papeUpdate2(Map<String,Object> map) throws Exception{
		getSqlMapClientTemplate().update("PEDMSCT0002.TSC_CONSULT_ADMIN_PAPE-UPDATE02",map);
	}
	
	public void cnslUpdate1(Map<String,Object> map) throws Exception{
		getSqlMapClientTemplate().update("PEDMSCT0002.TSC_CONSULT_ADMIN_CNSL-UPDATE01",map);
	}
	
	public void cnslUpdate2(Map<String,Object> map) throws Exception{
		getSqlMapClientTemplate().update("PEDMSCT0002.TSC_CONSULT_ADMIN_CNSL-UPDATE02",map);
	}
	
	public void entshpUpdate1(Map<String,Object> map) throws Exception{
		getSqlMapClientTemplate().update("PEDMSCT0002.TSC_CONSULT_ADMIN_ENTSHP-UPDATE01",map);
	}
	
	public void entshpUpdate2(Map<String,Object> map) throws Exception{
		getSqlMapClientTemplate().update("PEDMSCT0002.TSC_CONSULT_ADMIN_ENTSHP-UPDATE02",map);
	}
	
	public List consultAdminSelectDetail(String str) throws Exception{
		return (List<HashBox>)getSqlMapClientTemplate().queryForList("PEDMSCT0002.TSC_CONSULT_ADMIN-SELECT02",str);
	}
	
	public List consultAdminSelectDetailPast(String str) throws Exception{
		return (List<HashBox>)getSqlMapClientTemplate().queryForList("PEDMSCT0002.TSC_CONSULT_ADMIN_PAST-SELECT01",str);
	}
	
	public void historyInsert(Map<String,Object> map) throws Exception{
		getSqlMapClientTemplate().insert("PEDMSCT0002.TED_VENDOR_EDI_HIST-INSERT01",map);
	}
	
	public List<EdiCommonCode> selectL1List(SearchParam searchParam) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().queryForList("PEDMSCT0002.getL1List", searchParam);
	}
	
	public List<EdiCommonCode> selectL1ListApply(SearchParam searchParam) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().queryForList("PEDMSCT0002.getL1ListApply", searchParam);
	}
	
	
	public List<EdiCommonCode> selectDistinctTeamList(SearchParam searchParam) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().queryForList("PEDMSCT0002.getTeamList", searchParam);
	}
	
}
