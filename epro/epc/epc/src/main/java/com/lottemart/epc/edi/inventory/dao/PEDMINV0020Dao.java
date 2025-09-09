package com.lottemart.epc.edi.inventory.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lcn.module.common.util.HashBox;
import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.epc.edi.inventory.model.PEDMINV0020VO;


@Repository("pedminv0020Dao")
public class PEDMINV0020Dao extends AbstractDAO {

	
	public List<PEDMINV0020VO> selectBadProdInfo(Map<String,Object> map) throws Exception{
		return (List<PEDMINV0020VO>)getSqlMapClientTemplate().queryForList("PEDMINV0020.TSC_BAD_PROD-SELECT01",map);
	}
	
	public PEDMINV0020VO selectBadProdPopupInfo(Map<String,Object> map) throws Exception{
		return (PEDMINV0020VO)getSqlMapClientTemplate().queryForObject("PEDMINV0020.TSC_BAD_PROD_POPUP-SELECT01",map);
	}
	
	public void selectBadProdPopupUpdate(Map<String,Object> map) throws Exception{
		getSqlMapClientTemplate().update("PEDMINV0020.TSC_BAD_PROD_POPUP-UPDATE01",map);
	}
	
}
