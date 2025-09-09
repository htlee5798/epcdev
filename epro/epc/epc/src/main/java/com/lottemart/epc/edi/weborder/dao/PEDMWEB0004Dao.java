package com.lottemart.epc.edi.weborder.dao;

import java.util.List;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.epc.edi.weborder.model.SearchWebOrder;
import com.lottemart.epc.edi.weborder.model.TedOrdList;


@Repository("pedmweb0004Dao")
public class PEDMWEB0004Dao extends AbstractDAO {

	@SuppressWarnings("deprecation")
	public int selectOrdTotListTotCnt(SearchWebOrder vo) {
		return ((Integer) getSqlMapClientTemplate().queryForObject("PEDMWEB0004.selectOrdTotListTotCnt", vo)).intValue();
	}
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<TedOrdList> selectOrdTotList(SearchWebOrder vo) {
		// TODO Auto-generated method stub
		return (List<TedOrdList>)getSqlMapClientTemplate().queryForList("PEDMWEB0004.selectOrdTotList", vo);
	}
	
	@SuppressWarnings({ "deprecation" })
	public TedOrdList selectOrdTotCntSum(SearchWebOrder vo) {
		// TODO Auto-generated method stub
		return (TedOrdList)getSqlMapClientTemplate().queryForObject("PEDMWEB0004.selectOrdTotCntSum", vo);
	}
}
