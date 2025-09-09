package com.lottemart.epc.common.dao;

import java.sql.SQLException;
import java.util.List;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.common.model.PSCMCOM0007VO;

@Repository("PSCMCOM0007Dao")
public class PSCMCOM0007Dao extends AbstractDAO  {

	@SuppressWarnings("unchecked")
	public List<DataMap> selectOrderIdList(PSCMCOM0007VO vo) throws SQLException{
		return (List<DataMap>)getSqlMapClientTemplate().queryForList("PSCMCOM0007.selectOrderIdList", vo);
	}
}




