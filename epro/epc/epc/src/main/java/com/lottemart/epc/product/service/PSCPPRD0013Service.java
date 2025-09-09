/**
 * @prjectName  : lottemart 프로젝트   
 * @since       : 2011. 10. 18. 오후 2:33:50
 * @author      : yskim 
 * @Copyright(c) 2000 ~ 2011 lottemart
 *  All rights reserved.
 */

package com.lottemart.epc.product.service;

import com.lottemart.epc.product.model.PSCMPRD0011VO;

/**
 * @Class Name : PSCPPRD0013Service
 * @Description : 상품이미지촬영스케쥴상세 조회 Service 클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 18. 오후 5:15:31 yskim
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
public interface PSCPPRD0013Service {

	/**
	 * Desc : 상품이미지촬영스케쥴상세 조회 메소드
	 * @Method Name : selectSchedulePopup
	 * @param vo
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public PSCMPRD0011VO selectSchedulePopup(PSCMPRD0011VO vo) throws Exception;
	
	/**
	 * Desc : 상품이미지촬영스케쥴상세 등록 메소드
	 * @Method Name : insertSchedulePopup
	 * @param vo
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public void insertSchedulePopup(PSCMPRD0011VO vo) throws Exception;

	/**
	 * Desc : 상품이미지촬영스케쥴상세 수정 메소드
	 * @Method Name : updateSchedulePopup
	 * @param vo
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public void updateSchedulePopup(PSCMPRD0011VO vo) throws Exception;
	
	/**
	 * Desc : 상품이미지촬영스케쥴상세 삭제 메소드
	 * @Method Name : deleteSchedulePopup
	 * @param vo
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public void deleteSchedulePopup(PSCMPRD0011VO vo) throws Exception;
}
