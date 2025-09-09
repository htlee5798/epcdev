/**
 * @prjectName  : lottemart 프로젝트   
 * @since       : 2011. 10. 19. 오후5:10:37
 * @author      : wcpark 
 * @Copyright(c) 2000 ~ 2011 lottemart
 *  All rights reserved.
 */

package com.lottemart.epc.board.dao;

import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import lcn.module.framework.base.AbstractDAO;

import com.lottemart.epc.board.model.PSCPBRD0004SearchVO;


/**
 * @Class Name : PSCPBRD0004Dao.java
 * @Description : 콜센터 1:1문의 상세보기Dao클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 19. 오후 5:10:47 wcpark
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
@Repository("PSCPBRD0004Dao")
public class PSCPBRD0004Dao extends AbstractDAO{
	

	/**
	 * Desc : 콜센터 1:1문의 상세내용을 조회하는 메소드
	 * @Method Name : selectCounselPopupDetail
	 * @param vo
	 * @return
	 * @throws SQLException
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public PSCPBRD0004SearchVO selectCounselPopupDetail(PSCPBRD0004SearchVO vo) throws SQLException{
		return (PSCPBRD0004SearchVO) selectByPk("PSCPBRD0004.selectCounselPopupDetail", vo);
	}
	
	/**
	 * Desc : 콜센터 1:1문의 상세보기에서 메모를 저장하는 메소드
	 * @Method Name : updateMemo
	 * @param searchVO
	 * @throws SQLException
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public void updateMemo(PSCPBRD0004SearchVO searchVO) throws SQLException{
		update("PSCPBRD0004.updateMemo", searchVO);
	}

}
 