package com.lottemart.epc.edi.weborder.dao;

import java.sql.SQLException;
import java.util.List;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.epc.edi.weborder.model.EdiPoEmpPoolVO;
import com.lottemart.epc.edi.weborder.model.SearchWebOrder;



@Repository("pedmweb0020Dao")
public class PEDMWEB0020Dao extends AbstractDAO {
		
	
	
	/**
	 * 사원목록 조회  
	 * @param SearchWebOrder
	 * @return List<EdiPoEmPoolVO>
	 * @throws SQLException
	 */
	public List<EdiPoEmpPoolVO> selectEmpPoolList(SearchWebOrder vo) throws Exception{
		return (List<EdiPoEmpPoolVO>)getSqlMapClientTemplate().queryForList("PEDMWEB0020.EDI_EMP_POOL_LIST_01",vo);
		
	}
	
	/**
	 * 사원별 협력사 매핑목록 조회  
	 * @param SearchWebOrder
	 * @return List<EdiPoEmPoolVO>
	 * @throws SQLException
	 */
	public List<EdiPoEmpPoolVO> selectEmpVenList(SearchWebOrder vo) throws Exception{
		return (List<EdiPoEmpPoolVO>)getSqlMapClientTemplate().queryForList("PEDMWEB0020.EDI_EMP_POOL_LIST_02",vo);
		
	}
	
	
	/**
	 * 사원별 협력사 선택목록 삭제
	 * @param SearchWebOrder
	 * @return void
	 * @throws SQLException
	 */
	public void deleteEmpVenList(SearchWebOrder vo ) throws Exception{
		getSqlMapClientTemplate().delete("PEDMWEB0020.EDI_EMP_POOL_DEL_01", vo);
	}
	

	
	/**
	 * 사원별 협력사 기 등록 count
	 * @param SearchWebOrder
	 * @return int
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public int selectEmpVenDataCnt(SearchWebOrder vo) {
		return ((Integer) getSqlMapClientTemplate().queryForObject("PEDMWEB0020.EDI_EMP_POOL_COUNT_01", vo)).intValue();
	}
	
	
	
	/**
	 * 사원별 협력사 저장
	 * @param SearchWebOrder
	 * @return int
	 * @throws SQLException
	 */
	public int insertEmpVenData(SearchWebOrder vo) throws Exception{
		return getSqlMapClientTemplate().update("PEDMWEB0020.EDI_EMP_POOL_SAVE_01", vo);
	}
	


	/**
	 * 사원별 협력사 복사
	 * @param EdiPoEmpPoolVO
	 * @return int
	 * @throws SQLException
	 */
	public int insertEmpVenCopy(EdiPoEmpPoolVO vo) throws Exception{
		return getSqlMapClientTemplate().update("PEDMWEB0020.EDI_EMP_POOL_SAVE_02", vo);
	}
	
	/**
	 * 사원별 협력사 복사 후 삭제용
	 * @param EdiPoEmpPoolVO
	 * @return int
	 * @throws SQLException
	 */
	public int deleteEmpVenData(EdiPoEmpPoolVO vo) throws Exception{
		return getSqlMapClientTemplate().update("PEDMWEB0020.EDI_EMP_POOL_DEL_02", vo);
	}
	
	
	
}
