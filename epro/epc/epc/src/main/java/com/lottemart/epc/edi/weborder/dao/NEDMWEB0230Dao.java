package com.lottemart.epc.edi.weborder.dao;

import java.sql.SQLException;
import java.util.List;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.epc.edi.weborder.model.EdiRtnProdVO;
import com.lottemart.epc.edi.weborder.model.NEDMWEB0230VO;



@Repository("nedmweb0230Dao")
public class NEDMWEB0230Dao extends AbstractDAO {
		
	
	

	/**
	 *  반품전제 현황 내역 조회
	 * @param NEDMWEB0230VO
	 * @return List<EdiRtnProdVO>
	 * @throws SQLException
	 */
	public List<EdiRtnProdVO> selectVenRtnInfo(NEDMWEB0230VO vo) throws Exception{
		
		return (List<EdiRtnProdVO>)getSqlMapClientTemplate().queryForList("NEDMWEB0230.EDI_RETURN_SELECT_01",vo);
	}
	
	
	/**
	 *  반품전제 현황 내역 조회 전제 카운트 
	 * @param NEDMWEB0230VO
	 * @return int
	 * @throws SQLException
	 */
	public int selectVendRtnListTotCnt(NEDMWEB0230VO vo) {

		return ((Integer) getSqlMapClientTemplate().queryForObject("NEDMWEB0230.EDI_RETURN_SELECT_02", vo)).intValue();
		
	}
	

	
	
}
