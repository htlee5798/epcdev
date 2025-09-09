/**
 * @prjectName  : lottemart 프로젝트   
 * @since       : 2011. 10. 18. 오후 2:33:50
 * @author      : yskim 
 * @Copyright(c) 2000 ~ 2011 lottemart
 *  All rights reserved.
 */

package com.lottemart.epc.statistics.service;

import java.util.List;
import java.util.Map;

import com.lottemart.common.util.DataMap;

/**
 * @Class Name : PSCMSTA0005Service
 * @Description : 적립마일리지정산관리 Service 클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 18. 오후 5:37:17 yskim
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
public interface PSCMSTA0005Service {
	
	/**
	 * Desc : 적립마일리지정산관리 목록 조회 메소드
	 * @Method Name : selectSavingMileageList
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public List<DataMap> selectSavingMileageList(Map<String, String> paramMap) throws Exception;

	/**
	 * Desc : 적립마일리지 코드 조회 메소드
	 * @Method Name : selectSavingMileageCodeList
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public List<DataMap> selectSavingMileageCodeList() throws Exception;
}
