package com.lottemart.epc.board.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.board.dao.PSCMBRD0010Dao;
import com.lottemart.epc.board.service.PSCMBRD0010Service;

/**
 * @Class Name : PSCMBRD0010ServiceImpl.java
 * @Description :
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2016. 01. 15. projectBOS32
 * 
 * @Copyright (C) 2000 ~ 2011 롯데정보통신(주) All right reserved.
 * </pre>
 */
@Service("pscmbrd0010Service")
public class PSCMBRD0010ServiceImpl implements PSCMBRD0010Service 
{
	@Autowired
	private PSCMBRD0010Dao pscmbrd0010Dao;
	
	/**
	 * 업체문의사항 목록
	 * @Method Name : selectBoardList
	 * @param DataMap
	 * @return List
	 * @throws Exception
	 */
	public List<DataMap> selectBoardList(DataMap paramMap) throws Exception 
	{
		return pscmbrd0010Dao.selectList(paramMap);
	}
	
}
