/**
 * @prjectName  : lottemart 프로젝트   
 * @since       : 2011. 10. 19. 오후 5:59:30
 * @author      : wcpark 
 * @Copyright(c) 2000 ~ 2011 lottemart
 *  All rights reserved.
 */

package com.lottemart.epc.board.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.epc.board.dao.PSCPBRD0002Dao;
import com.lottemart.epc.board.model.PSCPBRD0002SearchVO;
import com.lottemart.epc.board.service.PSCPBRD0002Service;

/**
 * @Class Name : PSCPBRD0002ServiceImpl
 * @Description : 공지사항 상세보기 ServiceImpl 클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 19. 오후 6:01:05 wcpark
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
@Service("PSCPBRD0002Service")
public class PSCPBRD0002ServiceImpl implements PSCPBRD0002Service {
	@Autowired
	private PSCPBRD0002Dao pscpbrd0002Dao;
	
	
	/**
	 * Desc : 공지사항 상세 내용을 조회하는 메소드
	 * @Method Name : selectDetailPopup
	 * @param vo
	 * @param model
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public PSCPBRD0002SearchVO selectDetailPopup(PSCPBRD0002SearchVO vo) throws Exception {
		return pscpbrd0002Dao.selectDetailPopup(vo);
	}
	
}
 