/**
 * @prjectName  : lottemart 프로젝트   
 * @since       : 2016. 01. 19. 
 * @author      : projectBOS32 
 * @Copyright(c) 2000 ~ 2011 lottemart
 *  All rights reserved.
 */

package com.lottemart.epc.board.service;

import java.util.List;
import com.lottemart.epc.board.model.PSCPBRD0012VO;
import com.lottemart.epc.common.model.EpcLoginVO;


/**
 * @Class Name : PSCPBRD0012Service
 * @Description : 고객센터문의사항 상세 Service 클래스
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
public interface PSCPBRD0012Service {
	
	/**
	 * Desc : 고객센터문의사항 상세(원문) 정보를 조회하는 메소드
	 * @Method Name : selectCcQnaDetailPopup
	 * @param vo
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public PSCPBRD0012VO selectCcQnaDetailPopup(PSCPBRD0012VO vo, EpcLoginVO epcLoginVO) throws Exception;
	
	public PSCPBRD0012VO selectCcQnaDetail(PSCPBRD0012VO vo) throws Exception;
	
	/**
	 * Desc : 고객센터문의사항 상세 정보를 조회하는 메소드
	 * @Method Name : selectCcQnaDetailPopup
	 * @param vo
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public List<PSCPBRD0012VO> selectCcQnaDetailList(PSCPBRD0012VO vo) throws Exception;

	/**
	 * Desc : 고객센터문의사항 답변 정보를 등록하는 메소드
	 * @Method Name : insertReCcQnaDetailPopup
	 * @param vo
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public void insertReCcQnaDetailPopup(PSCPBRD0012VO vo) throws Exception;
	
	/**
	 * Desc : 고객센터문의사항 상세 정보를 수정하는 메소드
	 * @Method Name : updateCcQnaDetailPopup
	 * @param vo
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public void updateCcQnaDetailPopup(PSCPBRD0012VO vo) throws Exception;

	/**
	 * Desc : 고객센터문의사항 상세 조회시 조회수를 증가시키는 메소드
	 * @Method Name : updateCcQnaReadCount
	 * @param vo
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public void updateCcQnaReadCount(PSCPBRD0012VO vo) throws Exception;

	/**
	 * Desc : 다운로드 파일 정보를 삭제하는 메소드
	 * @Method Name : deleteAtchFileId
	 * @param vo
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public void deleteAtchFileId(PSCPBRD0012VO vo) throws Exception;
}
 