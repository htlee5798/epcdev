package com.lottemart.epc.talk.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.common.util.DataMap;
@Repository("TalkDao")
public class TalkDao extends AbstractDAO {

	@SuppressWarnings("unchecked") 
	public List<DataMap> selectMemberInfo(Map<String,Object> map) throws SQLException{
		return (List<DataMap>)getSqlMapClientTemplate().queryForList("Talk.selectMemberInfo", map);
	}
	
	@SuppressWarnings("unchecked") 
	public List<DataMap> selectOrderInfo(Map<String,Object> map) throws SQLException{
		return (List<DataMap>)getSqlMapClientTemplate().queryForList("Talk.selectOrderInfo", map);
	}
	
}
