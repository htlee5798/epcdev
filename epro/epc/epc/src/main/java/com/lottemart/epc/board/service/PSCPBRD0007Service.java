/**
 * @prjectName  : lottemart 프로젝트   
 * @since       : 2011. 10. 18. 오후 2:33:50
 * @author      : yskim 
 * @Copyright(c) 2000 ~ 2011 lottemart
 *  All rights reserved.
 */

package com.lottemart.epc.board.service;


import com.lottemart.epc.board.model.PSCPBRD0007VO;


/**
 * @Class Name : PSCPBRD0007Service
 * @Description : 업체문의사항 상세 Service 클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 18. 오후 4:06:30 yskim
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
public interface PSCPBRD0007Service {
	
	/**
	 * Desc : 업체문의사항 상세 정보를 조회하는 메소드
	 * @Method Name : selectSuggestionDetailPopup
	 * @param vo
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public PSCPBRD0007VO selectSuggestionDetailPopup(PSCPBRD0007VO vo) throws Exception;

	/**
	 * Desc : 업체문의사항 상세 정보를 수정하는 메소드
	 * @Method Name : updateSuggestionDetailPopup
	 * @param vo
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public void updateSuggestionDetailPopup(PSCPBRD0007VO vo) throws Exception;

	/**
	 * Desc : 업체문의사항 상세 조회시 조회수를 증가시키는 메소드
	 * @Method Name : updateSuggestionReadCount
	 * @param vo
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public void updateSuggestionReadCount(PSCPBRD0007VO vo) throws Exception;

	/**
	 * Desc : 다운로드 파일 정보를 삭제하는 메소드
	 * @Method Name : deleteAtchFileId
	 * @param vo
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public void deleteAtchFileId(PSCPBRD0007VO vo) throws Exception;
}
 