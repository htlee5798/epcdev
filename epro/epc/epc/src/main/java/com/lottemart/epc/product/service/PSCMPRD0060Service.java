package com.lottemart.epc.product.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.lottemart.common.util.DataMap;

/**
 * @Class Name : PSCMPRD0060Service
 * @Description : 공통 점포 팝업 Service 클래스
 * @Modification Information
 * 
 * << 개정이력(Modification Information) >>
 *   
 *   수정일       수정자           수정내용
 *  -------         --------    ---------------------------
 * 2019. 4. 18. 오전 10:12:00 신규생성
 * 
 * @Copyright (C) 2000 ~ 2011 롯데정보통신(주) All right reserved.
 * </pre>
 */
public interface PSCMPRD0060Service {
	
	
	/**
	 * Desc : 점포검색
	 * @Method Name : selectStoreList
	 * @param 
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public List<Map<String, Object>> selectStoreList() throws Exception;
	
	/**
	 * Desc : 점포검색
	 * @Method Name : selectStoreList
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public List<Map<String, Object>> selectStoreList(Map<Object, Object> paramMap) throws Exception;
}
