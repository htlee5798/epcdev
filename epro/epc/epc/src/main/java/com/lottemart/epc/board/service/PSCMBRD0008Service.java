package com.lottemart.epc.board.service;


import java.util.List;

import com.lottemart.common.util.DataMap;

/**
 * @Class Name : PSCMBRD0008Service.java
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
public interface PSCMBRD0008Service 
{
	/**
	 * 콜센터 1:1 문의 목록을 조회하는 메소드
	 * @Method Name : selectCallCenterList
	 * @param DataMap
	 * @return List<DataMap>
	 * @throws Exception
	 */
	public List<DataMap> selectCallCenterList(DataMap paramMap) throws Exception;
	
}
