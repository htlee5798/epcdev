package com.lottemart.epc.order.service;

import java.util.List;
import java.util.Map;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.order.model.PSCMORD0010VO;

/**
 * @Class Name : PSCMORD0010Service.java
 * @Description :
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2016. 6. 9. 오후 2:28:22 UNI
 * 
 * @Copyright (C) 2000 ~ 2011 롯데정보통신(주) All right reserved.
 * </pre>
 */
public interface PSCMORD0010Service {

	/**
	 * Desc : 
	 * @Method Name : selectCSRList
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public List<PSCMORD0010VO> selectCSRList(DataMap paramMap) throws Exception;

	/**
	 * Desc : 
	 * @Method Name : selectCSRListCnt
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public int selectCSRListCnt(Map<String, String> paramMap) throws Exception;
	
	/**
	 * Desc : 
	 * @Method Name : selectPscmord0011Export
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public List<Map<Object, Object>> selectPscmord0011Export( Map<Object, Object> paramMap) throws Exception;

}
