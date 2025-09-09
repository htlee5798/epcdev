package com.lottemart.epc.board.dao;


import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.board.model.PSCPBRD0011SaveVO;

/**
 * @Class Name : PSCPBRD0011Dao.java
 * @Description :
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2016. 01. 18. projectBOS32
 * 
 * @Copyright (C) 2000 ~ 2011 롯데정보통신(주) All right reserved.
 * </pre>
 */
@Repository("pscpbrd0011Dao")
public class PSCPBRD0011Dao extends AbstractDAO
{
	/**
	 * 주문번호 목록
	 * @Description : 주문번호 조회
	 * @Method Name : selectOrderIdList
	 * @param HashMap
	 * @return List
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<DataMap> selectOrderIdList(HashMap map) throws SQLException{
		return (List<DataMap>)getSqlMapClientTemplate().queryForList("pscpbrd0011.selectOrderIdList", map);
	}
	
	/**
	 * 상품 목록 총카운트
	 * Desc : 
	 * @Method Name : selectProductTotalCnt
	 * @param HashMap
	 * @return int
	 * @throws SQLException
	 */
	public int selectProductTotalCnt(HashMap map) throws SQLException
	{
		Integer iTotCnt = new Integer(0);
		iTotCnt = (Integer)getSqlMapClientTemplate().queryForObject("pscpbrd0011.selectProductTotalCnt",map);
		
		return iTotCnt.intValue();
	}	
	
	/**
	 * 상품 목록
	 * @Description : 상품 조회
	 * @Method Name : selectProductList
	 * @param HashMap
	 * @return List
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<DataMap> selectProductList(HashMap map) throws SQLException{
		return (List<DataMap>)getSqlMapClientTemplate().queryForList("pscpbrd0011.selectProductList", map);
	}
	
	/**
	 * 상담원 목록 총카운트
	 * Desc : 
	 * @Method Name : selectCCagentTotalCnt
	 * @param HashMap
	 * @return int
	 * @throws SQLException
	 */
	public int selectCCagentTotalCnt(HashMap map) throws SQLException
	{
		Integer iTotCnt = new Integer(0);
		iTotCnt = (Integer)getSqlMapClientTemplate().queryForObject("pscpbrd0011.selectCCagentTotalCnt",map);
		
		return iTotCnt.intValue();
	}
	
	/**
	 * 상담원 목록
	 * @Description : 상담원 조회
	 * @Method Name : selectCCagentList
	 * @param HashMap
	 * @return List
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<DataMap> selectCCagentList(HashMap map) throws SQLException{
		return (List<DataMap>)getSqlMapClientTemplate().queryForList("pscpbrd0011.selectCCagentList", map);
	}
	
	/**
	 * 고객센터문의요청 담당자 연락처 조회
	 * @Description : 고객센터문의요청 담당자 연락처 조회
	 * @Method Name : selectBoardAuthCellNo
	 * @param HashMap
	 * @return List
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<DataMap> selectBoardAuthCellNo() throws SQLException{
		return (List<DataMap>)getSqlMapClientTemplate().queryForList("pscpbrd0011.selectBoardAuthCellNo");
	}
	
	/**
	 * 업체문의사항 등록
	 * @Method Name : insertCcQnaPopup
	 * @param VO
	 * @return void
	 * @throws SQLException
	 */
	public void insertCcQnaPopup(PSCPBRD0011SaveVO saveVO) throws SQLException
	{
		insert("pscpbrd0011.insertCcQnaPopup", saveVO);
	}
	
}
 