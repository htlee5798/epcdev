/**
 * @prjectName  : lottemart 프로젝트   
 * @since       : 2011. 10. 19. 오후 5:16:03
 * @author      : wcpark 
 * @Copyright(c) 2000 ~ 2011 lottemart
 *  All rights reserved.
 */

package com.lottemart.epc.board.service;


import com.lottemart.epc.board.model.PSCPBRD0004SearchVO;


/**
 * @Class Name : PSCPBRD0004Service.java
 * @Description : 1:1문의상세 Service클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 19. 오후 5:16:49 wcpark
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
public interface PSCPBRD0004Service {
	
	/**
	 * Desc : 콜센터 1:1문의상세내용을 조회하는 메소드
	 * @Method Name : selectCounselPopupDetail
	 * @param vo
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public PSCPBRD0004SearchVO selectCounselPopupDetail(PSCPBRD0004SearchVO vo) throws Exception;
	
	/**
	 * Desc : 콜센터 1:1문의상세에서 메모를 저장하는 클래스
	 * @Method Name : updateMemo
	 * @param searchVO
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public void updateMemo(PSCPBRD0004SearchVO searchVO) throws Exception;
	
}
 