package com.lottemart.epc.statistics.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import lcn.module.framework.base.AbstractDAO;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.statistics.model.PSCMSTA0014VO;

@Repository("PSCMSTA0014Dao")
public class PSCMSTA0014Dao  extends AbstractDAO  {

	
	@SuppressWarnings("unchecked")
	public List<DataMap> selectRentCarPickUp(PSCMSTA0014VO searchVO) throws SQLException{
		return getSqlMapClientTemplate().queryForList("PSCMSTA0014.selectRentCarPickUp", searchVO );
	}

	@SuppressWarnings("unchecked")
	public List<DataMap> selectgasStationPickUp(PSCMSTA0014VO searchVO) throws SQLException{
		return getSqlMapClientTemplate().queryForList("PSCMSTA0014.selectgasStationPickUp", searchVO );
	}

	@SuppressWarnings("unchecked")
	public int selectRentCarPickUpTotal(PSCMSTA0014VO searchVO) throws SQLException{
		return (Integer)getSqlMapClientTemplate().queryForObject("PSCMSTA0014.selectRentCarPickUpTotal", searchVO );
	}

	@SuppressWarnings("unchecked")
	public List<DataMap> selectRentCarPickUpListExcel(PSCMSTA0014VO searchVO) throws SQLException{
		return getSqlMapClientTemplate().queryForList("PSCMSTA0014.selectRentCarPickUpListExcel", searchVO );
	}
	
	@SuppressWarnings("unchecked")
	public List<DataMap> selectSuperStrList(String extStrCd) throws SQLException{
		return getSqlMapClientTemplate().queryForList("PSCMSTA0014.selectSuperStrList", extStrCd);
	}
	
	@SuppressWarnings("unchecked")
	public List<DataMap> selectMartStrList() throws SQLException{
		return getSqlMapClientTemplate().queryForList("PSCMSTA0014.selectMartStrList");
	}
	
	@SuppressWarnings("unchecked")
	public List<DataMap> selectPickupStsList() throws SQLException{
		return getSqlMapClientTemplate().queryForList("PSCMSTA0014.selectPickupStsList");
	}
	
	@SuppressWarnings("unchecked")
	public List<DataMap> selectDeliStatusList() throws SQLException{
		return getSqlMapClientTemplate().queryForList("PSCMSTA0014.selectDeliStatusList");
	}

	@SuppressWarnings("unchecked")
	public List<DataMap> selectOrdDivnList() throws SQLException{
		return getSqlMapClientTemplate().queryForList("PSCMSTA0014.selectOrdDivnList");
	}

	@SuppressWarnings("unchecked")
	public List<DataMap> pickUpListExcel(DataMap paramMap) throws SQLException{
		return getSqlMapClientTemplate().queryForList("PSCMSTA0014.pickUpListExcel", paramMap);
	}
	
	@SuppressWarnings("unchecked")
	public List<DataMap> pickUpListExcelByGas(DataMap paramMap) throws SQLException{
		return getSqlMapClientTemplate().queryForList("PSCMSTA0014.pickUpListExcelByGas", paramMap);
	}
	
	@SuppressWarnings("unchecked")
	public List<DataMap> selectPickUpTime() throws SQLException{
		return getSqlMapClientTemplate().queryForList("PSCMSTA0014.selectPickUpTime");
	}
	
	@SuppressWarnings("unchecked")
	public void insertPickupStatus(Map<String, String> paramMap) throws SQLException{
		 getSqlMapClientTemplate().insert("PSCMSTA0014.insertPickupStatus", paramMap);
	}
}
