
package com.lottemart.epc.delivery.dao;

import java.sql.SQLException;
import java.util.List;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.delivery.model.PSCMDLV0007VO;

@Repository("PSCMDLV0007Dao")
public class PSCMDLV0007Dao extends AbstractDAO{

	@SuppressWarnings("unchecked")
	public List<DataMap> getTetCodeList(PSCMDLV0007VO vo) throws SQLException{
		return (List<DataMap>)getSqlMapClientTemplate().queryForList("PSCMDLV0007.getTetCodeList", vo);
	}	
	
	@SuppressWarnings("unchecked")
	public List<DataMap> selectPartherReturnStatusList(PSCMDLV0007VO vo) throws SQLException{
		return (List<DataMap>)getSqlMapClientTemplate().queryForList("PSCMDLV0007.selectPartherReturnStatusList", vo);
	}
	
	public DataMap selectPartherReturnStatusSum(PSCMDLV0007VO vo) throws SQLException{
		return (DataMap)getSqlMapClientTemplate().queryForObject("PSCMDLV0007.selectPartherReturnStatusSum", vo);
	}		
	

}
