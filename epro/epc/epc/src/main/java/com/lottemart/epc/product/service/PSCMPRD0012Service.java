/**
 * @prjectName  : lottemart 프로젝트   
 * @since       : 2011. 10. 18. 오후 2:33:50
 * @author      : yskim 
 * @Copyright(c) 2000 ~ 2011 lottemart
 *  All rights reserved.
 */

package com.lottemart.epc.product.service;

import java.util.List;

import com.lottemart.common.util.DataMap;

/**
 * @Class Name : PSCMPRD0012Service
 * @Description : 상품이미지촬영스케쥴켈린더 Service 클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 18. 오후 5:06:10 yskim
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
public interface PSCMPRD0012Service {

	/**
	 * Desc : 상품이미지촬영스케쥴켈린더 조회 메소드
	 * @Method Name : selectCalendarScheduleList
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public List<DataMap> selectCalendarScheduleList(DataMap paramMap) throws Exception;
}
