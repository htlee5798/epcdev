package com.lottemart.epc.product.service;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.product.model.PSCPPRD0016VO;

/**
 * @Class Name : PSCPPRD0016Service.java
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
public interface PSCPPRD0016Service 
{
	/**
	 * 증정품 목록
	 * @param Map<String, String>
	 * @return PSCPPRD0016VO
	 * @throws Exception
	 */
	public List<DataMap> selectPrdPresentList(Map<String, String> paramMap) throws Exception;

	/**
	 * 증정품 수정 처리
	 * @param List<VO>
	 * @param String
	 * @return int
	 * @throws Exception
	 */
	public int updatePrdPresentList(List<PSCPPRD0016VO> pscpprd0016VOList, String mode) throws Exception;
	
	/**
	 * 증정품 수정 처리
	 * @return int
	 * @throws Exception
	 */
	public int updatePrdPresentList(HttpServletRequest request) throws Exception;
	
}
