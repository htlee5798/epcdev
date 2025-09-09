/**
 * @prjectName  : lottemart 프로젝트   
 * @since       : 2011. 10. 19. 오후 2:36:30
 * @author      : wcpark 
 * @Copyright(c) 2000 ~ 2011 lottemart
 *  All rights reserved.
 */

package com.lottemart.epc.board.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.board.dao.PSCMBRD0001Dao;
import com.lottemart.epc.board.model.PSCPBRD0002SearchVO;
import com.lottemart.epc.board.service.PSCMBRD0001Service;

/**
 * @Class Name : PSCMBRD0001ServiceImpl
 * @Description : 공지사항 목록조회 ServiceImpl 클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 19. 오후 2:37:05 wcpark
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
@Service("PSCMBRD0001Service")
public class PSCMBRD0001ServiceImpl implements PSCMBRD0001Service {
	
	@Autowired
	private PSCMBRD0001Dao pscmbrd0001Dao;
	
	
	/**
	 * Desc : 공지사항 목록을 조회하는 메소드
	 * @Method Name : selectBoardList
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public List<PSCPBRD0002SearchVO> selectBoardList(DataMap paramMap) throws Exception {
		return pscmbrd0001Dao.getList(paramMap);
	}
}
