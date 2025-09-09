package com.lottemart.epc.edi.consult.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lcn.module.common.util.HashBox;
import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.epc.edi.consult.model.NEDMCST0030VO;


@Repository("nedmcst0030Dao")
public class NEDMCST0030Dao extends AbstractDAO {
	
	public List selectVenCd(Map<String,Object> map) throws Exception{
		return (List<HashBox>)getSqlMapClientTemplate().queryForList("NEDMCST0030.TSC_VENDOR-SELECT01",map);
	}
	
	public List<NEDMCST0030VO> alertPageInsertPageSelect(NEDMCST0030VO map) throws Exception{
		return (List<NEDMCST0030VO>)getSqlMapClientTemplate().queryForList("NEDMCST0030.TSC_ALERT_PAGE-SELECT01",map);
	}
	
	public Integer ajaxEmailCk(Map<String,Object> map) throws Exception{
		return (Integer)getSqlMapClientTemplate().queryForObject("NEDMCST0030.TSC_EMAIL_CK_AJAX-SELECT01",map);
	}
	
	public Integer ajaxEmailCkUP(Map<String,Object> map) throws Exception{
		return (Integer)getSqlMapClientTemplate().queryForObject("NEDMCST0030.TSC_EMAIL_CK_AJAX-SELECT02",map);
	}
	
	public Integer ajaxCellCk(Map<String,Object> map) throws Exception{
		return (Integer)getSqlMapClientTemplate().queryForObject("NEDMCST0030.TSC_CELL_CK_AJAX-SELECT01",map);
	}
	
	public Integer ajaxCellCkUP(Map<String,Object> map) throws Exception{
		return (Integer)getSqlMapClientTemplate().queryForObject("NEDMCST0030.TSC_CELL_CK_AJAX-SELECT02",map);
	}
	
	public List ajaxVendor(Map<String,Object> map) throws Exception{
		return (List<HashBox>)getSqlMapClientTemplate().queryForList("NEDMCST0030.TSC_VENDOR-SELECT01",map);
	}
	
	public List ajaxVendorCK(Map<String,Object> map) throws Exception{
		return (List<HashBox>)getSqlMapClientTemplate().queryForList("NEDMCST0030.TSC_VENDOR-SELECT02",map);
	}
	
	public HashMap getAjaxCount(Map<String,Object> map) throws Exception{
		return (HashMap)getSqlMapClientTemplate().queryForObject("NEDMCST0030.TSC_COUNT_ID-SELECT01",map);
	}
	
	/**
	 * 알리미 서비스 ID 등록
	 * @param map
	 * @throws Exception
	 */
	public void insertPageInsertID(NEDMCST0030VO map) throws Exception{
		getSqlMapClientTemplate().insert("NEDMCST0030.TSC_ALERT_PAGE_ID-INSERT01",map);
	}
	
	/**
	 * 서비스순번 Max Seq
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public String selectPageInsertIDSeq(NEDMCST0030VO map) throws Exception{
		return (String)getSqlMapClientTemplate().queryForObject("NEDMCST0030.ALERT_ID_SEQ-SELECT01",map);
	}
	
	public void insertPageInsertUser(NEDMCST0030VO map) throws Exception{
		getSqlMapClientTemplate().insert("NEDMCST0030.TSC_ALERT_PAGE_USER-INSERT01",map);
	}
	
	public NEDMCST0030VO alertPageUpdatePage(NEDMCST0030VO map) throws Exception{
		return (NEDMCST0030VO)getSqlMapClientTemplate().queryForObject("NEDMCST0030.TSC_ALERT_UPDATE_PAGE-SELECT01",map);
	}
	
	public void alertPageUpdateID(NEDMCST0030VO map) throws Exception{
		getSqlMapClientTemplate().update("NEDMCST0030.TSC_ALERT_PAGE_ID-UPDATE01",map);
	}
	
	
	public void alertPageDeleteID(NEDMCST0030VO map) throws Exception{
		getSqlMapClientTemplate().delete("NEDMCST0030.TSC_ALERT_PAGE_ID-DELETE01",map);
	}
	
	public void alertPageDeleteUser(NEDMCST0030VO map) throws Exception{
		getSqlMapClientTemplate().delete("NEDMCST0030.TSC_ALERT_PAGE_USER-DELETE01",map);
	}
	
	
}




