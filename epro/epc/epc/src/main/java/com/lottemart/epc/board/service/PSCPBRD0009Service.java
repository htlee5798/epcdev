package com.lottemart.epc.board.service;


import com.lottemart.epc.board.model.PSCPBRD0009SaveVO;

/**
 * @Class Name : PSCPBRD0009Service.java
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
public interface PSCPBRD0009Service 
{
	/**
	 * 콜센터 1:1문의 내역을 등록
	 * @Method Name : insertCallCenterPopup
	 * @param VO
	 * @return void
	 * @throws Exception
	 */
	public void insertCallCenterPopup(PSCPBRD0009SaveVO saveVO) throws Exception;

}
 