package com.lottemart.epc.product.service;

import java.util.List;

import com.lottemart.common.util.DataMap;

/**
 * @Class Name : PSCMPRD0010Service
 * @Description : 추가구성품관리 Service 클래스
 * @Modification Information
 * 
 * << 개정이력(Modification Information) >>
 *   
 *   수정일       수정자           수정내용
 *  -------         --------    ---------------------------
 * 2016.04.27   projectBOS32	신규생성      
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
public interface PSCMPRD0010Service {

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
	public List<DataMap> selectComponentList(DataMap paramMap) throws Exception;

	
}
