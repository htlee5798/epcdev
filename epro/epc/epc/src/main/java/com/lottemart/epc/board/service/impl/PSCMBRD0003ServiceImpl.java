/**
 * @prjectName  : lottemart 프로젝트   
 * @since       : 2011. 10. 19. 오후 5:20:50
 * @author      : wcpark
 * @Copyright(c) 2000 ~ 2011 lottemart
 *  All rights reserved.
 */

package com.lottemart.epc.board.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.board.dao.PSCMBRD0003Dao;
import com.lottemart.epc.board.model.PSCMBRD0003SearchVO;
import com.lottemart.epc.board.service.PSCMBRD0003Service;

/**
 * @Class Name : PSCMBRD0003ServiceImpl
 * @Description : 공지사항 목록조회 ServiceImpl 클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 19. 오후5:25:05 wcpark
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
@Service("PSCMBRD0003Service")
public class PSCMBRD0003ServiceImpl implements PSCMBRD0003Service {
	
	@Autowired
	private PSCMBRD0003Dao pscmbrd0003Dao;

	/**
	 * Desc : 총처리건, 접수건, 완료건을 조회하는 메소드
	 * @Method Name : selectCounselCount
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public List<DataMap> selectCounselCount(DataMap paramMap) throws Exception {
		return pscmbrd0003Dao.selectCounselCount(paramMap);
	}

	
	/**
	 * Desc : 콜센터 1:1문의 목록을 조회하는 메소드
	 * @Method Name : selectinquireList
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public List<DataMap> selectinquireList(DataMap paramMap) throws Exception {
		return pscmbrd0003Dao.getinquireList(paramMap);
	}
	
	
	/**
	 * Desc : 접수위치 select box
	 * @Method Name : agentLocation
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public List<DataMap> agentLocation(PSCMBRD0003SearchVO searchVO) throws Exception {
		return pscmbrd0003Dao.agentLocation(searchVO);
	}

	
	/**
	 * Desc : 고객문의 구분 select box
	 * @Method Name : custQstDivnCd
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public List<DataMap> custQstDivnCd(PSCMBRD0003SearchVO searchVO) throws Exception {
		return pscmbrd0003Dao.custQstDivnCd(searchVO);
	}
	
}
