/**
 * @prjectName  : lottemart 프로젝트   
 * @since       : 2011. 10. 19. 오후 5:19:38
 * @author      : wcpark 
 * @Copyright(c) 2000 ~ 2011 lottemart
 *  All rights reserved.
 */

package com.lottemart.epc.board.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.epc.board.dao.PSCPBRD0004Dao;
import com.lottemart.epc.board.model.PSCPBRD0004SearchVO;
import com.lottemart.epc.board.service.PSCPBRD0004Service;

/**
 * @Class Name : PSCPBRD0004ServiceImpl.java
 * @Description : 콜센터 1:1문의 상세 ServiceImpl클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 19. 오후 5:19:52 wcpark
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
@Service("PSCPBRD0004Service")
public class PSCPBRD0004ServiceImpl implements PSCPBRD0004Service {
	@Autowired
	private PSCPBRD0004Dao pscpbrd0004Dao;
	
	/**
	 * Desc : 1:1문의상세내용을 조회하는 메소드
	 * @Method Name : selectCounselPopupDetail
	 * @param vo
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public PSCPBRD0004SearchVO selectCounselPopupDetail(PSCPBRD0004SearchVO vo) throws Exception {
		return pscpbrd0004Dao.selectCounselPopupDetail(vo);
	}
	
	/**
	 * Desc : 1:1문의상세에서 메모를 저장하는 클래스
	 * @Method Name : updateMemo
	 * @param searchVO
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public void updateMemo(PSCPBRD0004SearchVO searchVO) throws Exception {
		pscpbrd0004Dao.updateMemo(searchVO);
	}

}
 