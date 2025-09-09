package com.lottemart.epc.board.service;


import java.util.List;

import com.lottemart.common.util.DataMap;

/**
 * @Class Name : PSCMBRD0010Service.java
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
public interface PSCMBRD0010Service 
{
	/**
	 * 업체문의사항 목록
	 * @Method Name : selectBoardList
	 * @param DataMap
	 * @return List
	 * @throws Exception
	 */
	public List<DataMap> selectBoardList(DataMap paramMap) throws Exception;
	
}
