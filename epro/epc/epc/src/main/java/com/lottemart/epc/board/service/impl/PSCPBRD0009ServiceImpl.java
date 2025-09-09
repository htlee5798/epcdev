package com.lottemart.epc.board.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.epc.board.dao.PSCPBRD0009Dao;
import com.lottemart.epc.board.model.PSCPBRD0009SaveVO;
import com.lottemart.epc.board.service.PSCPBRD0009Service;

/**
 * @Class Name : PSCPBRD0009ServiceImpl.java
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
@Service("pscpbrd0009Service")
public class PSCPBRD0009ServiceImpl implements PSCPBRD0009Service 
{
	
	@Autowired
	private PSCPBRD0009Dao pscpbrd0009Dao;
	
	/**
	 * 콜센터 1:1 내역을 등록
	 * @Method Name : insertCallCenterPopup
	 * @param VO
	 * @return void
	 * @throws Exception
	 */
	public void insertCallCenterPopup(PSCPBRD0009SaveVO saveVO) throws Exception 
	{
		pscpbrd0009Dao.insertCallCenterPopup(saveVO);
	}
	
}
 