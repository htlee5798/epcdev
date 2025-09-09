package com.lottemart.epc.delivery.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.common.util.DataMap;

@Repository
public class PSCMDLV0011Dao extends AbstractDAO {
	@SuppressWarnings("unchecked")
	public List<DataMap> selectVendorPenaltyCompRate(DataMap paramMap) throws SQLException {
		return (List<DataMap>)list("pscmdlv0011.selectVendorPenaltyCompRate", paramMap);
	}
	
	public int vendorPenaltyTotalCnt(Map<String, String> paramMap) throws SQLException{
		Integer iTotCnt = new Integer(0);
		iTotCnt = (Integer)getSqlMapClientTemplate().queryForObject("pscmdlv0011.vendorPenaltyTotalCnt",paramMap);
		return iTotCnt.intValue();
	}
	
	@SuppressWarnings("unchecked")
	public List<DataMap> selectVendorPenaltyCompRateExcel(DataMap paramMap) throws SQLException {
		return (List<DataMap>)list("pscmdlv0011.selectVendorPenaltyCompRateExcel", paramMap);
	}
}
