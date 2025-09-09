/**
 * @prjectName  : lottemart 프로젝트   
 * @since       : 2011. 10. 19. 오후 6:10:50
 * @author      : wcpark 
 * @Copyright(c) 2000 ~ 2011 lottemart
 *  All rights reserved.
 */

package com.lottemart.epc.board.dao;

import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import lcn.module.framework.base.AbstractDAO;

import com.lottemart.epc.board.model.PSCPBRD0002SearchVO;

/**
 * @Class Name : PSCPBRD0002Dao.java
 * @Description : 공지사항 상세보기 Dao클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 19. 오후 4:19:12 wcpark
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
@Repository("PSCPBRD0002Dao")
public class PSCPBRD0002Dao extends AbstractDAO{
	
	/**
	 * Desc : 공지사항 상세정보를 조회하는 메소드
	 * @Method Name : selectDetailPopup
	 * @param vo
	 * @return
	 * @throws SQLException
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public PSCPBRD0002SearchVO selectDetailPopup(PSCPBRD0002SearchVO vo) throws SQLException{
		return (PSCPBRD0002SearchVO) selectByPk("PSCMBRD0001.selectDetailPopup", vo);
	}
	
}