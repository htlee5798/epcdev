/**
 * @prjectName  : lottemart 프로젝트   
 * @since       : 2011. 10. 18. 오후 2:33:50
 * @author      : yskim 
 * @Copyright(c) 2000 ~ 2011 lottemart
 *  All rights reserved.
 */

package com.lottemart.epc.statistics.service;

import java.util.List;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.statistics.model.PSCMSTA0004SearchVO;

/**
 * @Class Name : PSCMSTA0004Service
 * @Description : 아시아나정산관리 목록 조회 Service 클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 18. 오후 5:31:54 yskim
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
public interface PSCMSTA0004Service {
	
	/**
	 * Desc : 아시아나정산관리 목록 조회 메소드
	 * @Method Name : selectAsianaMileageList
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public List<DataMap> selectAsianaMileageList(PSCMSTA0004SearchVO searchVO) throws Exception;
	
	/**
	 * Desc : 아시아나정산관리 써머리 조회 메소드
	 * @Method Name : selectAsianaMileageList
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public DataMap selectAsianaMileageSum(PSCMSTA0004SearchVO searchVO) throws Exception;	
	
	/**
	 * Desc : 아시아나정산관리 목록 엑셀조회 메소드
	 * @Method Name : selectAsianaMileageList
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public List<DataMap> selectAsianaMileageListExcel(PSCMSTA0004SearchVO searchVO) throws Exception;
	
}
