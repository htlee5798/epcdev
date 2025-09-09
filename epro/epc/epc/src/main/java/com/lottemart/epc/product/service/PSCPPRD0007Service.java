package com.lottemart.epc.product.service;


import java.util.List;

import com.lottemart.common.util.DataMap;

/**
 * @Class Name : PSCPPRD0007Service.java
 * @Description :
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 20. 오후 02:02:02 mjChoi
 * 
 * @Copyright (C) 2000 ~ 2011 롯데정보통신(주) All right reserved.
 * </pre>
 */
public interface PSCPPRD0007Service 
{
	/**
	 * 원산지정보 목록
	 * Desc : 원산지코드 목록을 얻어 온다.
	 * @Method Name : selectLocationList
	 * @param
	 * @return List
	 * @throws Exception
	 */
	public List<DataMap> selectLocationList() throws Exception;
			
}
