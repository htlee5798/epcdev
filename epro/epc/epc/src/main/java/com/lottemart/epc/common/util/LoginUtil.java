/**
 * @prjectName  : lottemart 프로젝트   
 * @since       : 2011. 10. 24. 오전 11:01:10
 * @author      : yskim 
 * @Copyright(c) 2000 ~ 2011 lottemart
 *  All rights reserved.
 */
package com.lottemart.epc.common.util;

import java.util.ArrayList;
import java.util.List;

import com.lottemart.epc.common.model.EpcLoginVO;

/**
 * @Class Name : LoginUtil
 * @Description : 로그인 관련 유틸리티 클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 24. 오전 11:01:10 yskim
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
public class LoginUtil {
	
	
	/**
	 * Desc : 세션에 있는 vendorId를 list 형태로 리턴 (SQL에서 iterate 사용을 위해) 
	 * @Method Name : getVendorList
	 * @param vendorId
	 * @return
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public static List<String> getVendorList(EpcLoginVO epcLoginVO) {
		ArrayList<String> list = new ArrayList<String>();
		if(epcLoginVO == null) {
			return list;
		}
		String[] vendorId = epcLoginVO.getVendorId();
		int size = vendorId.length;
		for(int i = 0; i < size; i++) {
			list.add(vendorId[i]);
		}
		return list;
	}
}
