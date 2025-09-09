package com.lottemart.epc.board.service;


import java.util.List;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.board.model.PSCMBRD0005SearchVO;

/**
 * @Class Name : PSCMBRD0005Service.java
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
public interface PSCMBRD0005Service 
{
	/**
	 * 업체문의사항 글상태 목록
	 * @Method Name : selectCategoryList
	 * @param VO
	 * @return List
	 * @throws Exception
	 */
	public List<DataMap> selectStatusList(PSCMBRD0005SearchVO searchVO) throws Exception;
	
	/**
	 * 업체문의사항 목록
	 * @Method Name : selectBoardList
	 * @param DataMap
	 * @return List
	 * @throws Exception
	 */
	public List<PSCMBRD0005SearchVO> selectBoardList(DataMap paramMap) throws Exception;
	
}
