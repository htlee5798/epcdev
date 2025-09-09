package com.lottemart.epc.common.service.impl;


import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.common.dao.PSCMCOM0004Dao;
import com.lottemart.epc.common.model.PSCMCOM0004VO;
import com.lottemart.epc.common.service.PSCMCOM0004Service;

/**
 * @Class Name : PSCMCOM0004ServiceImpl.java
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
@Service("pscmcom0004Service")
public class PSCMCOM0004ServiceImpl implements PSCMCOM0004Service
{
	@Autowired
	private PSCMCOM0004Dao PSCMCOM0004Dao;
	
	/**
	 * 대분류(카테고리id:01 depth:2) 카테고리 목록
	 * @MethodName  : selectCategoryListDepth2
	 * @param String
	 * @return List<DataMap>
	 * @throws Exception
	 */
	public List<DataMap> selectCategoryListDepth2(String categoryTypeCd) throws Exception{
		return PSCMCOM0004Dao.selectCategoryListDepth2(categoryTypeCd);
		
	}

	/**
	 * 기본카테고리 목록
	 * @Method Name : selectCategoryList
	 * @param Map<String, String>
	 * @return List<VO>
	 * @throws SQLException
	 */
	public List<PSCMCOM0004VO> selectCategoryList(DataMap paramMap) throws Exception 
	{
		return PSCMCOM0004Dao.selectCategoryList(paramMap);
	}

	/**
	 * 기본카테고리 목록(IN 조건)
	 * @Method Name : selectCategoryInList
	 * @param Map<String, String>
	 * @return List<DataMap>
	 * @throws SQLException
	 */
	public List<DataMap> selectCategoryInList(Map<String, Object> paramMap) throws Exception 
	{
		return PSCMCOM0004Dao.selectCategoryInList(paramMap);
	}
	
	/**
	 * 업체 주소 정보
	 * @Method Name : selectVendorAddrList
	 * @param Map<String, String>
	 * @return List<DataMap>
	 * @throws SQLException
	 */
	public List<DataMap> selectVendorAddrList(Map<String, Object> paramMap) throws Exception 
	{
		return PSCMCOM0004Dao.selectVendorAddrList(paramMap);
	}
}
