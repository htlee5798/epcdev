package com.lottemart.epc.edi.batch.dao;


import java.util.List;
import java.util.Map;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.edi.batch.model.FuelSale001VO;

@Repository
 
public class PEDMBAT0001Dao extends AbstractDAO {
	
	
	
	
	public List<DataMap> selectFuesSale001SendDistinctDate(Map<String,Object> map)  throws DataAccessException  {
		// TODO Auto-generated method stub
		return (List<DataMap>)getSqlMapClientTemplate().queryForList("PEDMBAT0001.selectFuesSale001SendDistinctDate",map);
	}	

//	public List<DataMap> selectFuesSale001SendDistinctBill(String dateYYYYMMDD)  throws DataAccessException  {
//		// TODO Auto-generated method stub
//		return (List<DataMap>)getSqlMapClientTemplate().queryForList("PEDMBAT0001.selectFuesSale001SendDistinctBill",dateYYYYMMDD);
//	}
	public List<DataMap> selectFuesSale001SendDistinctBill(Map<String,Object> map)  throws DataAccessException  {
		// TODO Auto-generated method stub
		return (List<DataMap>)getSqlMapClientTemplate().queryForList("PEDMBAT0001.selectFuesSale001SendDistinctBill",map);
	}
	
	

	
	//public FuelSale001VO selectFuesSale001Send(String dateYYYYMMDD,String billnumber)  throws DataAccessException  {
	//	// TODO Auto-generated method stub
	//	return (FuelSale001VO) getSqlMapClientTemplate().queryForObject("PEDMBAT0001.selectFuesSale001Send",dateYYYYMMDD,billnumber);
	//}
	
	public FuelSale001VO selectFuesSale001Send(Map<String,Object> map)  throws DataAccessException  {
		// TODO Auto-generated method stub
		return (FuelSale001VO) getSqlMapClientTemplate().queryForObject("PEDMBAT0001.selectFuesSale001Send",map);
	}
	
	public void insertFuesSale001log(FuelSale001VO tmpFuelSale001VO) throws DataAccessException {	
			getSqlMapClientTemplate().insert("PEDMBAT0001.insertFuesSale001log", tmpFuelSale001VO);		
	}


	
	
	
	
	
}
