package com.lottemart.epc.common.service;


import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.common.model.PSCMCOM0004VO;

/**
 * @Class Name : PSCMCOM0004Service.java
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
public interface PSCMCOM0004Service 
{
	/**
	 * 대분류(카테고리id:01 depth:2) 카테고리 목록
	 * @MethodName  : selectCategoryListDepth2
	 * @param String
	 * @return List<DataMap>
	 * @throws Exception
	 */
	public List<DataMap> selectCategoryListDepth2(String categoryTypeCd) throws Exception;
	
	/**
	 * 기본카테고리 목록
	 * @Method Name : selectCategoryList
	 * @param Map<String, String>
	 * @return List<VO>
	 * @throws SQLException
	 */
	public List<PSCMCOM0004VO> selectCategoryList(DataMap paramMap) throws Exception;
	
	/**
	 * 기본카테고리 목록(IN 조건)
	 * @Method Name : selectCategoryInList
	 * @param Map<String, String>
	 * @return List<VO>
	 * @throws SQLException
	 */
	public List<DataMap> selectCategoryInList(Map<String, Object> paramMap) throws Exception;
	
	/**
	 * 업체 주소 정보
	 * @Method Name : selectVendorAddrList
	 * @param Map<String, String>
	 * @return List<VO>
	 * @throws SQLException
	 */
	public List<DataMap> selectVendorAddrList(Map<String, Object> paramMap) throws Exception;

}
