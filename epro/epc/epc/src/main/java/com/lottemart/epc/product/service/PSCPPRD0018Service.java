package com.lottemart.epc.product.service;


import java.util.List;
import java.util.Map;

import com.lottemart.epc.product.model.PSCPPRD0018VO;
import com.lottemart.common.util.DataMap;

/**
 *  
 * @Class Name : PSCPPRD0018Service
 * @Description :
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자	   수정내용
 *  -------    --------    ---------------------------
 * 2011. 12. 27. 오후 03:03:03 mjChoi
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
public interface PSCPPRD0018Service
{
	/**
	 * 상품종류
	 * @return String
	 * @throws Exception
	 */
	public DataMap selectPrdDivnType(String prodCd) throws Exception;
	
	/**
	 * 점포 목록
	 * @return DataMap
	 * @throws Exception
	 */
	public List<DataMap> selectPrdStoreList(String strGubun) throws Exception;

	/**
	 * 공통코드 목록
	 * @return DataMap
	 * @throws Exception
	 */
	public List<DataMap> selectPrdCommonCodeList(String prodCd) throws Exception;
	
	/**
	 * 가격정보 목록
	 * @return PSCPPRD0018VO
	 * @throws Exception
	 */
	public List<PSCPPRD0018VO> selectPrdPriceList(DataMap paramMap) throws Exception;

	/**
	 * 가격정보 수정 처리
	 * @return int
	 * @throws Exception
	 */
	public int updatePrdPriceList(List<PSCPPRD0018VO> pbomprd0004VOList, String mode) throws Exception;

	/**
	 * 가격정보 상세 목록
	 * @return PSCPPRD0018VO
	 * @throws Exception
	 */
	public List<PSCPPRD0018VO> selectPrdPriceDetailList(Map<String, String> paramMap) throws Exception;

}
