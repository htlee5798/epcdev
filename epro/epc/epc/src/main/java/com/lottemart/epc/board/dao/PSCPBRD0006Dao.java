package com.lottemart.epc.board.dao;


import java.sql.SQLException;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.epc.board.model.PSCPBRD0006SaveVO;

/**
 * @Class Name : PSCPBRD0006Dao.java
 * @Description :
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 20. 오후 02:02:02 mjChoi
 * 
 * @Copyright (C) 2000 ~ 2011 롯데정보통신(주) All right reserved.
 * </pre>
 */
@Repository("pscpbrd0006Dao")
public class PSCPBRD0006Dao extends AbstractDAO
{
	/**
	 * 업체문의사항 등록
	 * @Method Name : insertSuggestionPopup
	 * @param VO
	 * @return void
	 * @throws SQLException
	 */
	public void insertSuggestionPopup(PSCPBRD0006SaveVO saveVO) throws SQLException
	{
		insert("pscpbrd0006.insertSuggestionPopup", saveVO);
	}
	
}
 