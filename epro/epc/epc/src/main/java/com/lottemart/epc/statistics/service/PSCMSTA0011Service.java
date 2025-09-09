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
 * @Class Name : PSCMSTA0011Service
 * @Description : 에누리 Service 클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2015.02.23	hippie		
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
public interface PSCMSTA0011Service {
	
	/**
	 * 구매상품 정보 목록
	 * @return 
	 * @throws Exception
	 */
	public List<DataMap> selectOrderProdItemList(Map<String, String> paramMap) throws Exception;	
	
	
}
