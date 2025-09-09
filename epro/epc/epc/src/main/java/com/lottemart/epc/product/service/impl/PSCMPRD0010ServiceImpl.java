package com.lottemart.epc.product.service.impl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.product.dao.PSCMPRD0010Dao;
import com.lottemart.epc.product.service.PSCMPRD0010Service;

/**
 * @Class Name : PSCMPRD0010ServiceImpl
 * @Description : 추가구성품관리 ServiceImpl 클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일       수정자           수정내용
 *  -------         --------    ---------------------------
 * 2016.04.27   projectBOS32	신규생성      
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
@Service("PSCMPRD0010Service")
public class PSCMPRD0010ServiceImpl implements PSCMPRD0010Service {
	
	@Autowired
	private PSCMPRD0010Dao pscmprd0010Dao;

	/**
	 * Desc : 추가구성품을 조회하는 메소드
	 * @Method Name : selectComponentList
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public List<DataMap> selectComponentList(DataMap paramMap) throws Exception {
		return pscmprd0010Dao.selectComponentList(paramMap);
	}
}
