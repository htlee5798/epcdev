package com.lottemart.epc.delivery.dao;

import java.sql.SQLException;
import java.util.List;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.common.util.DataMap;

@Repository
public class PSCMDLV0010Dao extends AbstractDAO {
	
	@SuppressWarnings("unchecked")
	public List<DataMap> selectVendorPenalty(DataMap paramMap) throws SQLException {
		return (List<DataMap>)list("pscmdlv0010.selectVendorPenalty", paramMap);
	}

}
