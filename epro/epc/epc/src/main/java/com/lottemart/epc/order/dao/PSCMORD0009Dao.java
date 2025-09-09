package com.lottemart.epc.order.dao;

import java.sql.SQLException;
import java.util.List;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.order.model.PSCMORD0009VO;

@Repository("PSCMORD0009Dao")
public class PSCMORD0009Dao extends AbstractDAO{

	@SuppressWarnings("unchecked")
	public List<DataMap> getTetCodeList(PSCMORD0009VO vo) throws SQLException{
		return (List<DataMap>)getSqlMapClientTemplate().queryForList("PSCMORD0009.getTetCodeList", vo);
	}	

	@SuppressWarnings("unchecked")
	public List<DataMap> selectPartnerReturnList(DataMap paramMap) throws SQLException{
		return (List<DataMap>)getSqlMapClientTemplate().queryForList("PSCMORD0009.selectPartnerReturnList", paramMap);
	}	

}