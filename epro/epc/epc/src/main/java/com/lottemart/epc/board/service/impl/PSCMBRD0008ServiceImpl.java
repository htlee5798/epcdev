package com.lottemart.epc.board.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.board.dao.PSCMBRD0008Dao;
import com.lottemart.epc.board.service.PSCMBRD0008Service;

/**
 * @Class Name : PSCMBRD0008ServiceImpl.java
 * @Description :
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 12. 16. 오후 03:03:03 mjChoi
 * 
 * @Copyright (C) 2000 ~ 2011 롯데정보통신(주) All right reserved.
 * </pre>
 */
@Service("pscmbrd0008Service")
public class PSCMBRD0008ServiceImpl implements PSCMBRD0008Service 
{
	@Autowired
	private PSCMBRD0008Dao pscmbrd0008Dao;
	
	/**
	 * 콜센터 1:1문의 목록을 조회하는 메소드
	 * @Method Name : selectCallCenterList
	 * @param DataMap
	 * @return List<DataMap>
	 * @throws Exception
	 */
	public List<DataMap> selectCallCenterList(DataMap paramMap) throws Exception 
	{
		return pscmbrd0008Dao.selectCallCenterList(paramMap);
	}
	
}
