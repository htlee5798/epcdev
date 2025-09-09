package com.lottemart.epc.product.service;


import java.util.List;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.product.model.PSCMPRD0001VO;

/**
 * @Class Name : PSCMPRD0001Service.java
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
public interface PSCMPRD0001Service 
{
	/**
	 * 상품 구분 목록
	 * @param  
	 * @return List<DataMap>
	 * @throws Exception
	 */
	public List<DataMap> selectProdDivNCdList() throws Exception;
	
	/**
	 * 상품 목록 총카운트
	 * @param DataMap 
	 * @return int
	 * @throws Exception
	 */
	public int selectPrdTotalCnt(DataMap paramMap) throws Exception;
	
	/**
	 * 상품 목록
	 * @param DataMap
	 * @return List<VO>
	 * @throws Exception
	 */
	public List<PSCMPRD0001VO> selectPrdList(DataMap paramMap) throws Exception;

	public List<DataMap> selectPrdListForBatchImageUpload(DataMap paramMap) throws Exception;

	/**
	 * 상품코드 목록
	 * @param String
	 * @return PSCMPRD0001VO
	 * @throws Exception
	 */
	public PSCMPRD0001VO selectProdCdInfo(String asKeywordValue) throws Exception;
	
	public List<PSCMPRD0001VO> selectProductExcel(DataMap paramMap) throws Exception;
}
