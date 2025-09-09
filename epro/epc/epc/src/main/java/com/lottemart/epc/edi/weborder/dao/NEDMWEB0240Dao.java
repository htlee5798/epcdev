package com.lottemart.epc.edi.weborder.dao;

import java.sql.SQLException;
import java.util.List;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.epc.edi.weborder.model.EdiPoEmpPoolVO;
import com.lottemart.epc.edi.weborder.model.NEDMWEB0240VO;
import com.lottemart.epc.edi.weborder.model.SearchWebOrder;

/**
 * @Class Name : NEDMWEB0240Dao
 * @Description : 협력업체설정 Dao Class
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      		수정자          		수정내용
 *  -------    --------    ---------------------------
 * 2015.12.09	O YEUN KWON	  최초생성
 * 
 * @Copyright (C) 2000 ~ 2015 lottemart All right reserved.
 * </pre>
 */

@Repository("nedmweb0240Dao")
public class NEDMWEB0240Dao extends AbstractDAO {
		
	
	
	/**
	 * 사원목록 조회  
	 * @param SearchWebOrder
	 * @return List<EdiPoEmPoolVO>
	 * @throws SQLException
	 */
	public List<NEDMWEB0240VO> selectEmpPoolList(NEDMWEB0240VO vo) throws Exception{
		return (List<NEDMWEB0240VO>)getSqlMapClientTemplate().queryForList("NEDMWEB0240.EDI_EMP_POOL_LIST_01",vo);
		
	}
	
	/**
	 * 사원별 협력사 매핑목록 조회  
	 * @param SearchWebOrder
	 * @return List<EdiPoEmPoolVO>
	 * @throws SQLException
	 */
	public List<NEDMWEB0240VO> selectEmpVenList(NEDMWEB0240VO vo) throws Exception{
		return (List<NEDMWEB0240VO>)getSqlMapClientTemplate().queryForList("NEDMWEB0240.EDI_EMP_POOL_LIST_02",vo);
		
	}
	
	
	/**
	 * 사원별 협력사 선택목록 삭제
	 * @param SearchWebOrder
	 * @return void
	 * @throws SQLException
	 */
	public void deleteEmpVenList(NEDMWEB0240VO vo ) throws Exception{
		getSqlMapClientTemplate().delete("NEDMWEB0240.EDI_EMP_POOL_DEL_01", vo);
	}
	

	
	/**
	 * 사원별 협력사 기 등록 count
	 * @param SearchWebOrder
	 * @return int
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public int selectEmpVenDataCnt(NEDMWEB0240VO vo) {
		return ((Integer) getSqlMapClientTemplate().queryForObject("NEDMWEB0240.EDI_EMP_POOL_COUNT_01", vo)).intValue();
	}
	
	
	
	/**
	 * 사원별 협력사 저장
	 * @param SearchWebOrder
	 * @return int
	 * @throws SQLException
	 */
	public int insertEmpVenData(NEDMWEB0240VO vo) throws Exception{
		return getSqlMapClientTemplate().update("NEDMWEB0240.EDI_EMP_POOL_SAVE_01", vo);
	}
	


	/**
	 * 사원별 협력사 복사
	 * @param EdiPoEmpPoolVO
	 * @return int
	 * @throws SQLException
	 */
	public int insertEmpVenCopy(NEDMWEB0240VO vo) throws Exception{
		return getSqlMapClientTemplate().update("NEDMWEB0240.EDI_EMP_POOL_SAVE_02", vo);
	}
	
	/**
	 * 사원별 협력사 복사 후 삭제용
	 * @param EdiPoEmpPoolVO
	 * @return int
	 * @throws SQLException
	 */
	public int deleteEmpVenData(NEDMWEB0240VO vo) throws Exception{
		return getSqlMapClientTemplate().update("NEDMWEB0240.EDI_EMP_POOL_DEL_02", vo);
	}
	
	
	
}
