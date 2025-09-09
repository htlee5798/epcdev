package com.lottemart.epc.board.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.epc.board.dao.PSCPBRD0006Dao;
import com.lottemart.epc.board.model.PSCPBRD0006SaveVO;
import com.lottemart.epc.board.service.PSCPBRD0006Service;

/**
 * @Class Name : PSCPBRD0006ServiceImpl.java
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
@Service("pscpbrd0006Service")
public class PSCPBRD0006ServiceImpl implements PSCPBRD0006Service 
{
	
	@Autowired
	private PSCPBRD0006Dao pscpbrd0006Dao;
	
	/**
	 * 업체문의사항을 등록
	 * @Method Name : insertSuggestionPopup
	 * @param VO
	 * @return void
	 * @throws Exception
	 */
	public void insertSuggestionPopup(PSCPBRD0006SaveVO saveVO) throws Exception 
	{
		pscpbrd0006Dao.insertSuggestionPopup(saveVO);
	}
	
}
 