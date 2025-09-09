/**
 * @prjectName  : lottemart 프로젝트   
 * @since       : 2011. 10. 18. 오후 2:33:50
 * @author      : yskim 
 * @Copyright(c) 2000 ~ 2011 lottemart
 *  All rights reserved.
 */

package com.lottemart.epc.product.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.product.dao.PSCMPRD0012Dao;
import com.lottemart.epc.product.service.PSCMPRD0012Service;

/**
 * @Class Name : PSCMPRD0012ServiceImpl
 * @Description : 상품이미지촬영스케쥴켈린더 ServiceImpl 클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 18. 오후 5:06:08 yskim
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
@Service("PSCMPRD0012Service")
public class PSCMPRD0012ServiceImpl implements PSCMPRD0012Service {
	
	@Autowired
	private PSCMPRD0012Dao pscmprd0012Dao;

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
	public List<DataMap> selectCalendarScheduleList(DataMap paramMap) throws Exception {
		return pscmprd0012Dao.selectCalendarScheduleList(paramMap);
	}
}
