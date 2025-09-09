package com.lottemart.epc.edi.zip.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lcn.module.common.util.HashBox;
import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.lottemart.epc.edi.comm.model.SearchParam;
import com.lottemart.epc.edi.comm.model.EdiCommonCode;

@Repository("pedpzip0001Dao")
public class PEDPZIP0001Dao extends AbstractDAO {
		
	public List selectZipCodeList(Map<String,Object> map) throws Exception{
		return (List<HashBox>)getSqlMapClientTemplate().queryForList("PEDPZIP0001.ZIP_CODE_SELECT01",map);
	}
	public List selectStreetCodeList(Map<String,Object> map) throws Exception{
		return (List<HashBox>)getSqlMapClientTemplate().queryForList("PEDPZIP0001.STREET_CODE_SELECT01",map);
	}	
	public List<EdiCommonCode> selectCityList(SearchParam searchParam) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().queryForList("PEDPZIP0001.getCityList", searchParam);
	}		
	public List<EdiCommonCode> getSelectedGuNmList(SearchParam searchParam) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().queryForList("PEDPZIP0001.getSelectedGuNmList", searchParam);
	}	
	
}
