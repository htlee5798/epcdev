package com.lottemart.epc.calculation.dao;

import java.sql.SQLException;
import java.util.List;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.calculation.model.PSCMCAL0003SearchVO;

@Repository("PSCMCAL0003Dao")
public class PSCMCAL0003Dao extends AbstractDAO {

	@SuppressWarnings("unchecked")
	public List<PSCMCAL0003SearchVO> getDeliverySettleCostsCalculateList(DataMap paramMap) throws SQLException{
		return (List<PSCMCAL0003SearchVO>)getSqlMapClientTemplate().queryForList("PSCMCAL0003.selectDeliverySettleCostsCalculateList", paramMap);
	}
	@SuppressWarnings("unchecked")
	public DataMap getDeliverySettleCostsCalculateSum(DataMap paramMap) throws SQLException{
		return (DataMap)getSqlMapClientTemplate().queryForObject("PSCMCAL0003.selectDeliverySettleCostsCalculateSum", paramMap);
		
	}
	@SuppressWarnings("unchecked")
	public List<DataMap> getDeliverySettleCostsCalculateListExcel(PSCMCAL0003SearchVO searchVO) throws SQLException{
		return (List<DataMap>)getSqlMapClientTemplate().queryForList("PSCMCAL0003.selectDeliverySettleCostsCalculateListExcel", searchVO);
	}

	
}
