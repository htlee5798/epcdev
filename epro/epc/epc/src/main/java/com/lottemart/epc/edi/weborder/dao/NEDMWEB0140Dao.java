package com.lottemart.epc.edi.weborder.dao;


import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.lottemart.epc.edi.weborder.model.EdiRtnPackListVO;
import com.lottemart.epc.edi.weborder.model.EdiRtnPackVO;
import com.lottemart.epc.edi.weborder.model.EdiRtnProdListVO;
import com.lottemart.epc.edi.weborder.model.NEDMWEB0140VO;
import com.lottemart.epc.edi.weborder.model.SearchWebOrder;
import com.lottemart.epc.edi.weborder.model.EdiRtnProdVO;
import com.lottemart.epc.edi.weborder.model.TedPoOrdMstVO;
import com.lottemart.epc.edi.weborder.model.TedPoOrdPackVO;


@Repository("NEDMWEB0140Dao")
public class NEDMWEB0140Dao extends AbstractDAO {

	
////////////
	/**
	 *  당일 반품등록 내역 카운드
	 * @param SearchWebOrder
	 * @return int
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public int selectDayRtnListTotCnt(NEDMWEB0140VO vo) {
		return ((Integer) getSqlMapClientTemplate().queryForObject("NEDMWEB0140.EDI_RETURN_SELECT_03", vo)).intValue();
	}
	
	/**
	 *  당일 반품등록 내역 조회
	 * @param SearchWebOrder
	 * @return List<EdiRtnProdVO>
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<NEDMWEB0140VO> selectDayRtnList(NEDMWEB0140VO vo)  throws Exception{
		// TODO Auto-generated method stub
		return (List<NEDMWEB0140VO>)getSqlMapClientTemplate().queryForList("NEDMWEB0140.EDI_RETURN_SELECT_01", vo);
	}
	
	/**
	 *  당일 반품등록 내역 합계
	 * @param SearchWebOrder
	 * @return EdiRtnProdVO
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public NEDMWEB0140VO selectDayRtnSum(NEDMWEB0140VO vo) {
		// TODO Auto-generated method stub
		return (NEDMWEB0140VO)getSqlMapClientTemplate().queryForObject("NEDMWEB0140.EDI_RETURN_SELECT_02", vo);
	}
	//////////


	
	

}
