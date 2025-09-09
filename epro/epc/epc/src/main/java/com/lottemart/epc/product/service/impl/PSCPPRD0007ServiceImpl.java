package com.lottemart.epc.product.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.product.dao.PSCPPRD0007Dao;
import com.lottemart.epc.product.service.PSCPPRD0007Service;

/**
 * @Class Name : PSCPPRD0007ServiceImpl.java
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
@Service("pscpprd0007Service")
public class PSCPPRD0007ServiceImpl implements PSCPPRD0007Service
{
	@Autowired
	private PSCPPRD0007Dao pscpprd0007Dao;

	/**
	 * 원산지정보 목록
	 * @Method Name : selectLocationList
	 * @param
	 * @return List
	 * @throws Exception
	 */
	public List<DataMap> selectLocationList() throws Exception 
	{
		return pscpprd0007Dao.selectLocationList();
	}

}
