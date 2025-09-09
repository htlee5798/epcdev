/**
 * @prjectName  : lottemart 프로젝트   
 * @since       : 2011. 10. 19. 오후 2:30:50
 * @author      : wcpark
 * @Copyright(c) 2000 ~ 2011 lottemart
 *  All rights reserved.
 */

package com.lottemart.epc.board.service;

import java.util.List;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.board.model.PSCPBRD0002SearchVO;

/**
 * @Class Name : PSCMBRD0001Service
 * @Description : 공지사항 목록조회 Service 클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 19. 오후 2:35:30 wcpark
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
public interface PSCMBRD0001Service {
	
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
	public List<PSCPBRD0002SearchVO> selectBoardList(DataMap paramMap) throws Exception;
	
	
} 
