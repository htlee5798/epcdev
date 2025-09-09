/**
 * @prjectName  : lottemart 프로젝트   
 * @since       : 2011. 10. 19. 오후5:57:50
 * @author      : wcpark
 * @Copyright(c) 2000 ~ 2011 lottemart
 *  All rights reserved.
 */

package com.lottemart.epc.board.service;

import com.lottemart.epc.board.model.PSCPBRD0002SearchVO;

/**
 * @Class Name : PSCPBRD0002Service
 * @Description : 공지사항 상세보기 Service 클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 19. 오후 5:58:30 wcpark
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
public interface PSCPBRD0002Service {
	
	
	/**
	 * Desc : 공지사항 상세 정보를 조회하는 메소드
	 * @Method Name : selectDetailPopup
	 * @param vo
	 * @param model
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public PSCPBRD0002SearchVO selectDetailPopup(PSCPBRD0002SearchVO vo) throws Exception;
	

}
 