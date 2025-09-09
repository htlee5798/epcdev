package com.lottemart.epc.board.service.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.board.dao.PSCPBRD0011Dao;
import com.lottemart.epc.board.model.PSCPBRD0011SaveVO;
import com.lottemart.epc.board.service.PSCPBRD0011Service;

/**
 * @Class Name : PSCPBRD0011ServiceImpl.java
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
@Service("pscpbrd0011Service")
public class PSCPBRD0011ServiceImpl implements PSCPBRD0011Service 
{
	
	@Autowired
	private PSCPBRD0011Dao pscpbrd0011Dao;
	
	/**
	 * 주문번호 목록
	 * @Description : 주문번호 조회
	 * @Method Name : selectOrderIdList
	 * @param HashMap
	 * @return List
	 * @throws Exception
	 */
	public List<DataMap> selectOrderIdList(HashMap map) throws Exception {
		return pscpbrd0011Dao.selectOrderIdList(map);
	}
	
	/**
	 * 상품 목록 총카운트
	 * @param HashMap
	 * @return int
	 * @throws Exception
	 */
	public int selectProductTotalCnt(HashMap map) throws Exception 
	{
		return pscpbrd0011Dao.selectProductTotalCnt(map);
	}
	
	/**
	 * 상품 목록
	 * @Description : 상품 조회
	 * @Method Name : selectProductList
	 * @param HashMap
	 * @return List
	 * @throws Exception
	 */
	public List<DataMap> selectProductList(HashMap map) throws Exception {
		return pscpbrd0011Dao.selectProductList(map);
	}
	
	/**
	 * 상담원 목록 총카운트
	 * @param HashMap
	 * @return int
	 * @throws Exception
	 */
	public int selectCCagentTotalCnt(HashMap map) throws Exception 
	{
		return pscpbrd0011Dao.selectCCagentTotalCnt(map);
	}
	
	/**
	 * 상담원 목록
	 * @Description : 상담원 조회
	 * @Method Name : selectCCagentList
	 * @param HashMap
	 * @return List
	 * @throws Exception
	 */
	public List<DataMap> selectCCagentList(HashMap map) throws Exception {
		return pscpbrd0011Dao.selectCCagentList(map);
	}
	
	/**
	 * 고객센터문의요청 담당자 연락처 조회
	 * @Description : 고객센터문의요청 담당자 연락처 조회
	 * @Method Name : selectBoardAuthCellNo
	 * @param HashMap
	 * @return List
	 * @throws Exception
	 */
	public List<DataMap> selectBoardAuthCellNo() throws Exception {
		return pscpbrd0011Dao.selectBoardAuthCellNo();
	}
	
	/**
	 * 업체문의사항을 등록
	 * @Method Name : insertCcQnaPopup
	 * @param VO
	 * @return void
	 * @throws Exception
	 */
	public void insertCcQnaPopup(PSCPBRD0011SaveVO saveVO) throws Exception 
	{
		pscpbrd0011Dao.insertCcQnaPopup(saveVO);
	}
	
}
 