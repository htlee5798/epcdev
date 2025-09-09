package com.lottemart.epc.order.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.order.dao.PSCMORD0010Dao;
import com.lottemart.epc.order.model.PSCMORD0010VO;
import com.lottemart.epc.order.service.PSCMORD0010Service;

/**
 * @Class Name : PSCMORD0010ServiceImpl.java
 * @Description :
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2016. 6. 9. 오후 2:28:28 UNI
 * 
 * @Copyright (C) 2000 ~ 2011 롯데정보통신(주) All right reserved.
 * </pre>
 */
@Service
public class PSCMORD0010ServiceImpl implements  PSCMORD0010Service{
	
	@Autowired
	private PSCMORD0010Dao pscmord0010Dao; 

	//
	@Override
	public List<PSCMORD0010VO> selectCSRList(DataMap paramMap) throws Exception {
		return pscmord0010Dao.selectCSRList(paramMap);
	}

	//
	@Override
	public int selectCSRListCnt(Map<String, String> paramMap) throws Exception {
		return pscmord0010Dao.selectCSRListCnt(paramMap);
	}
	
	//
	@Override
	public List<Map<Object, Object>> selectPscmord0011Export( Map<Object, Object> paramMap) throws Exception {
		return pscmord0010Dao.selectPscmord0011Export(paramMap);
	}

}
