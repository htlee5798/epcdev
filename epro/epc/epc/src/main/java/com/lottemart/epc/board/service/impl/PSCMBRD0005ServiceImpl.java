package com.lottemart.epc.board.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.board.dao.PSCMBRD0005Dao;
import com.lottemart.epc.board.model.PSCMBRD0005SearchVO;
import com.lottemart.epc.board.service.PSCMBRD0005Service;

/**
 * @Class Name : PSCMBRD0005ServiceImpl.java
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
@Service("pscmbrd0005Service")
public class PSCMBRD0005ServiceImpl implements PSCMBRD0005Service 
{
	@Autowired
	private PSCMBRD0005Dao pscmbrd0005Dao;
	
	/**
	 * 업체문의사항 글상태 목록
	 * @Method Name : selectStatusList
	 * @param VO
	 * @return List
	 * @throws Exception
	 */
	public List<DataMap> selectStatusList(PSCMBRD0005SearchVO searchVO) throws Exception 
	{
		return pscmbrd0005Dao.selectStatusList(searchVO);
	}
	
	/**
	 * 업체문의사항 목록
	 * @Method Name : selectBoardList
	 * @param DataMap
	 * @return List
	 * @throws Exception
	 */
	public List<PSCMBRD0005SearchVO> selectBoardList(DataMap paramMap) throws Exception 
	{
		return pscmbrd0005Dao.selectList(paramMap);
	}
	
}
