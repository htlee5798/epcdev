/**
 * @prjectName  : lottemart 프로젝트   
 * @since       : 2011. 10. 18. 오후 2:33:50
 * @author      : yskim 
 * @Copyright(c) 2000 ~ 2011 lottemart
 *  All rights reserved.
 */

package com.lottemart.epc.statistics.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.statistics.dao.PSCMSTA0011Dao;
import com.lottemart.epc.statistics.service.PSCMSTA0011Service;

/**
 * @Class Name : PSCMSTA0011ServiceImpl
 * @Description : 에누리 ServiceImpl 클래스
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
@Service("PSCMSTA0011Service")
public class PSCMSTA0011ServiceImpl implements PSCMSTA0011Service {
	
	@Autowired
	private PSCMSTA0011Dao pscmsta0011Dao;

	/**
	 * 구매상품 정보 목록
	 * @return PBOMAFF0001VO
	 * @throws Exception
	 */
	public List<DataMap> selectOrderProdItemList(Map<String, String> paramMap) throws Exception {
		return pscmsta0011Dao.selectOrderProdItemList(paramMap);
	}
}
