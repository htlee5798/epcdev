package com.lottemart.epc.common.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import lcn.module.common.util.HashBox;
import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.common.model.PSCMCOM0006VO;

@Repository("PSCMCOM0006Dao")
public class PSCMCOM0006Dao extends AbstractDAO  {

	@SuppressWarnings("unchecked")
	public List<DataMap> selectPopupProductList(PSCMCOM0006VO vo) throws SQLException{
		return (List<DataMap>)getSqlMapClientTemplate().queryForList("PSCMCOM0006.selectPopupProductList", vo);
	}
	
	public List selectProduct(Map<String, Object> map ) throws Exception	{
		return (List<HashBox>)getSqlMapClientTemplate().queryForList("PSCMCOM0006.selectProduct",map);
	}
	
	public List<DataMap> selectNorProdDetailList(Map<String, Object> map) throws SQLException{
		return (List<DataMap>)getSqlMapClientTemplate().queryForList("PSCMCOM0006.selectNorProdDetailList", map);
	}
	
	public List<DataMap> selectKcProdDetailList(Map<String, Object> map) throws SQLException{
		return (List<DataMap>)getSqlMapClientTemplate().queryForList("PSCMCOM0006.selectKcProdDetailList", map);
	}
}




