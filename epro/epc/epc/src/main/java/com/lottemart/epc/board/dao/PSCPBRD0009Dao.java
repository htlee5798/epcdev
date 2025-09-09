package com.lottemart.epc.board.dao;


import java.sql.SQLException;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.epc.board.model.PSCPBRD0009SaveVO;

/**
 * @Class Name : PSCPBRD0009Dao.java
 * @Description :
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 12. 16. 오후 03:03:03 mjChoi
 * 
 * @Copyright (C) 2000 ~ 2011 롯데정보통신(주) All right reserved.
 * </pre>
 */
@Repository("pscpbrd0009Dao")
public class PSCPBRD0009Dao extends AbstractDAO
{
	/**
	 * 콜센터1:1 내역을 등록
	 * @Method Name : insertCallCenterPopup
	 * @param VO
	 * @return void
	 * @throws SQLException
	 */
	public void insertCallCenterPopup(PSCPBRD0009SaveVO saveVO) throws SQLException
	{
		insert("pscpbrd0009.insertCallCenterPopupp", saveVO);
	}
	
}
 