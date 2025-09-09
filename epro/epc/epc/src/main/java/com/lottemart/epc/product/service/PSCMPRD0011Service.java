/**
 * @prjectName  : lottemart 프로젝트   
 * @since       : 2011. 10. 18. 오후 2:33:50
 * @author      : yskim 
 * @Copyright(c) 2000 ~ 2011 lottemart
 *  All rights reserved.
 */

package com.lottemart.epc.product.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.product.model.PSCMPRD0011VO;

/**
 * @Class Name : PSCMPRD0011Service
 * @Description : 상품이미지촬영스케쥴목록 Service 클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 18. 오후 5:02:12 yskim
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
public interface PSCMPRD0011Service {

	/**
	 * Desc : 상품이미지촬영스케쥴목록 조회 메소드
	 * @Method Name : selectScheduleList
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public List<PSCMPRD0011VO> selectScheduleList(DataMap paramMap) throws Exception;

	/**
	 * Desc : 상품이미지촬영스케쥴목록 수정 메소드
	 * @Method Name : updateSchedule
	 * @param pscmprd0011VO
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public int updateSchedule(HttpServletRequest request) throws Exception;

	/**
	 * Desc : 상품이미지촬영스케쥴목록 삭제 메소드
	 * @Method Name : deleteSchedule
	 * @param pscmprd0011VO
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public int deleteSchedule(HttpServletRequest request) throws Exception;

	/**
	 * Desc : 상품이미지촬영스케쥴목록 엑셀다운로드하는 메소드
	 * @Method Name : selectScheduleMgrListExcel
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public List<PSCMPRD0011VO> selectScheduleMgrListExcel(PSCMPRD0011VO pscmprd0011VO) throws Exception;

}
