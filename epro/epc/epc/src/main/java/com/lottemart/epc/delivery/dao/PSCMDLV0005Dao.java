package com.lottemart.epc.delivery.dao;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.lottemart.common.util.DataMap;
import com.lottemart.epc.delivery.model.PSCMDLV0005VO;

@Repository("PSCMDLV0005Dao")
public class PSCMDLV0005Dao {

	@Autowired
	private SqlMapClient sqlMapClient;

	@SuppressWarnings("unchecked")
	public List<DataMap> selectPartnerFirmsStatus_short(PSCMDLV0005VO vo) throws SQLException{
		return (List<DataMap>)sqlMapClient.queryForList("PSCMDLV0005.selectPartnerFirmsStatus_short", vo);
	}
	
	@SuppressWarnings("unchecked")
	public List<DataMap> selectPartnerFirmsStatus_holy(PSCMDLV0005VO vo) throws SQLException{
		return (List<DataMap>)sqlMapClient.queryForList("PSCMDLV0005.selectPartnerFirmsStatus_holy", vo);
	}
	
	@SuppressWarnings("unchecked")
	public List<DataMap> selectPartnerFirmsStatus_All(PSCMDLV0005VO vo) throws SQLException{
		return (List<DataMap>)sqlMapClient.queryForList("PSCMDLV0005.selectPartnerFirmsStatus_All", vo);
	}
	
	public int selectTotalOrderCnt(PSCMDLV0005VO vo) throws SQLException{
		return (Integer)sqlMapClient.queryForObject("PSCMDLV0005.selectTotalOrderCnt", vo);
	}
}
