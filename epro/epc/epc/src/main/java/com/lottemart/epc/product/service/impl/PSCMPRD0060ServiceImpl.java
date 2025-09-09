package com.lottemart.epc.product.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.epc.product.dao.PSCMPRD0060Dao;
import com.lottemart.epc.product.service.PSCMPRD0060Service;

/**
 * @Class Name : PSCMPRD0060ServiceImpl
 * @Description : 공통 점포 팝업 Service 클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2019. 4. 18. 오전 10:12:00 신규생성
 * 
 * @Copyright (C) 2000 ~ 2011 롯데정보통신(주) All right reserved.
 * </pre>
 */
@Service("pscmprd0060Service")
public class PSCMPRD0060ServiceImpl implements PSCMPRD0060Service {
	
	@Autowired
	private PSCMPRD0060Dao pscmprd0060Dao;
	
	@Override
	public List<Map<String, Object>> selectStoreList() throws Exception{
		return pscmprd0060Dao.selectStoreList(null);
	}
	
	/* 점포 목록을 조회한다.
	 * @see com.lottemart.bos.product.service.PSCMPRD0060Service#selectStoreList(java.util.Map)
	 */
	@Override
	public List<Map<String, Object>> selectStoreList(Map<Object, Object> paramMap) throws Exception{
		return pscmprd0060Dao.selectStoreList(paramMap);
	}
}
