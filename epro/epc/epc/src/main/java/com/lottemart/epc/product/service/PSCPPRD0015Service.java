package com.lottemart.epc.product.service;


import java.util.List;
import java.util.Map;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.product.model.PSCPPRD0015VO;

/**
 * @Class Name : PSCPPRD0015Service.java
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
public interface PSCPPRD0015Service 
{
	/**
	 * 점포 목록
	 * @param String
	 * @return List<DataMap>
	 * @throws Exception
	 */
	public List<DataMap> selectPrdStoreList(String strGubun) throws Exception;
	
	/**
	 * 상품 아이콘 목록
	 * @param Map<String, String>
	 * @return List<VO>
	 * @throws Exception
	 */
	public List<PSCPPRD0015VO> selectPrdIconList(Map<String, String> paramMap) throws Exception;
	
	/**
	 * 상품 아이콘 수정 처리
	 * @param List<VO>
	 * @return int
	 * @throws Exception
	 */
	public int updatePrdIconList(List<PSCPPRD0015VO> pbomprd0008VOList) throws Exception;

}
