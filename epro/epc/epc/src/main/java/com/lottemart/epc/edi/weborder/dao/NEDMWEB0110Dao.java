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
import com.lottemart.epc.edi.weborder.model.EdiRtnProdVO;
import com.lottemart.epc.edi.weborder.model.NEDMWEB0110VO;
import com.lottemart.epc.edi.weborder.model.SearchWebOrder;


@Repository("nedmweb0110Dao")
public class NEDMWEB0110Dao extends AbstractDAO {
	/** 반품 등록번호 생성 */
	public String selectNewRtnReqNo() {
		return (String)selectByPk("NEDMWEB0110.selectNewRtnReqNo", null);
	}
	
	/**
	 * 1. 반품상품 점별 마스터 생성  
	 * @param EdiRtnProdVO
	 * @return void
	 * @throws SQLException
	 */
	public int saveRtnProdMst(NEDMWEB0110VO vo) throws Exception{
		return getSqlMapClientTemplate().update("NEDMWEB0110.EDI_RETURN_PROD_DATA_03", vo);
	}
	
	/**
	 * 2. 반품상품 점별 목록 생성
	 * @param EdiRtnProdVO
	 * @return void
	 * @throws SQLException
	 */
	public int saveRtnProdList(NEDMWEB0110VO vo) throws Exception{
		return getSqlMapClientTemplate().update("NEDMWEB0110.EDI_RETURN_PROD_DATA_04", vo);
	}
	
	/**
	 *  N 당일 반품등록 내역 조회 
	 * @param NEDMWEB0110VO
	 * @return List<NEDMWEB0110VO>
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<NEDMWEB0110VO> selectDayRtnProdList(NEDMWEB0110VO vo)  throws Exception{
		// TODO Auto-generated method stub
		return (List<NEDMWEB0110VO>)getSqlMapClientTemplate().queryForList("NEDMWEB0110.EDI_TED_PO_RRL_PROD_01", vo);
	}
	//////



}
