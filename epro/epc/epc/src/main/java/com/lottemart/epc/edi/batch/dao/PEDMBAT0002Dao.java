package com.lottemart.epc.edi.batch.dao;


import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;


import com.lottemart.common.util.DataMap;
import com.lottemart.epc.edi.batch.model.FuelCalc001VO;

import lcn.module.framework.base.AbstractDAO;

@Repository

public class PEDMBAT0002Dao extends AbstractDAO {

	
	
	public List<DataMap> selectFuesSale002SendDistinctDate(Map<String,Object> map)  throws DataAccessException  {
		// TODO Auto-generated method stub
		return (List<DataMap>)getSqlMapClientTemplate().queryForList("PEDMBAT0002.selectFuesSale002SendDistinctDate",map);
	}	
	
	
	
//	public FuelCalc001VO selectFuesSale002Send(String dateYYYYMMDD)  throws DataAccessException  {
//		// TODO Auto-generated method stub
//		return (FuelCalc001VO) getSqlMapClientTemplate().queryForObject("PEDMBAT0002.selectFuesSale002Send", dateYYYYMMDD);
//	}
	public FuelCalc001VO selectFuesSale002Send(Map<String,Object> map)  throws DataAccessException  {
		// TODO Auto-generated method stub
		return (FuelCalc001VO) getSqlMapClientTemplate().queryForObject("PEDMBAT0002.selectFuesSale002Send", map);
	}
	
	public void insertFuesSale002log(FuelCalc001VO tmpFuelCalc001VO) throws DataAccessException {
		// 온오프 겸용 상품일 경우, 
		
			getSqlMapClientTemplate().insert("PEDMBAT0002.insertFuesSale002log", tmpFuelCalc001VO);
		
	}

}
