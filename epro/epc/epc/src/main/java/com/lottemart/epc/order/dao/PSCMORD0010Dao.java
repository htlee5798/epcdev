package com.lottemart.epc.order.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.lottemart.common.util.DataMap;
import com.lottemart.epc.order.model.PSCMORD0010VO;

@Repository("pscmord0010Dao")
public class PSCMORD0010Dao {
	
	@Autowired
	private SqlMapClient sqlMapClient;
	
	/**
	 * Desc : 
	 * @Method Name : selectCSRList
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PSCMORD0010VO> selectCSRList(DataMap paramMap) throws Exception {
		return sqlMapClient.queryForList("pscmord0010.selectCSRList", paramMap);
	}
	
	/**
	 * Desc : 
	 * @Method Name : selectCSRListCnt
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public int selectCSRListCnt(Map<String, String> paramMap) throws Exception {
		return (Integer) sqlMapClient.queryForObject("pscmord0010.selectCSRListCnt", paramMap);
	}

	/**
	 * Desc : 
	 * @Method Name : selectPscmord0011Export
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Map<Object, Object>> selectPscmord0011Export(Map<Object, Object> paramMap) throws Exception {
		return sqlMapClient.queryForList("pscmord0010.selectPscmord0011Export",paramMap);
	}
	
}
