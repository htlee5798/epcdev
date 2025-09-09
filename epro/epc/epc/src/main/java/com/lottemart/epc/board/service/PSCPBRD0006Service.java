package com.lottemart.epc.board.service;


import com.lottemart.epc.board.model.PSCPBRD0006SaveVO;

/**
 * @Class Name : PSCPBRD0006Service.java
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
public interface PSCPBRD0006Service 
{
	/**
	 * 업체문의사항을 등록
	 * @Method Name : insertSuggestionPopup
	 * @param VO
	 * @return void
	 * @throws Exception
	 */
	public void insertSuggestionPopup(PSCPBRD0006SaveVO saveVO) throws Exception;

}
 