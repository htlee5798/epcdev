package com.lottemart.epc.product.service;


import java.util.List;
import java.util.Map;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.product.model.PSCPPRD0003VO;

/**
 * @Class Name : PSCPPRD0003Service.java
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
public interface PSCPPRD0003Service 
{
	/**
	 * 단품 칼라 목록
	 * @param 
	 * @return DataMap
	 * @throws Exception
	 */
	public List<DataMap> selectPrdItemColorList() throws Exception;

	/**
	 * 단품 사이즈 구분 목록
	 * @param 
	 * @return DataMap
	 * @throws Exception
	 */
	public List<DataMap> selectPrdItemSizeCategoryList() throws Exception;

	/**
	 * 단품 사이즈 목록
	 * @param String
	 * @return DataMap
	 * @throws Exception
	 */
	public List<DataMap> selectPrdItemSizeList(String szCatCd) throws Exception;
	
	/**
	 * 인터넷 전용상품 여부
	 * @param Map<String, String>
	 * @return int
	 * @throws Exception
	 */
	public int selectPrdType(Map<String, String> paramMap) throws Exception;
	
	
	/**
	 * 속성 값 체크
	 * @return int
	 * @throws Exception
	 */
	public int selectPrdItemType(Map<String, String> paramMap) throws Exception;
	
	/**
	 * 단품 정보 목록
	 * @param paramMap
	 * @return PSCPPRD0003VO
	 * @throws Exception
	 */
	public List<PSCPPRD0003VO> selectPrdItemList(Map<String, String> paramMap) throws Exception;

	/**
	 * 단품 정보 온라인 목록
	 * @return PSCPPRD0003VO
	 * @throws Exception
	 */
	public List<PSCPPRD0003VO> selectPrdItemOnlineList(Map<String, String> paramMap) throws Exception;
	
	
	/**
	 * 단품 정보
	 * @param paramMap
	 * @return PSCPPRD0003VO
	 * @throws Exception
	 */
	public PSCPPRD0003VO selectPrdItem(Map<String, String> paramMap) throws Exception;
	
	/**
	 * 단품 정보 입력 처리
	 * @param VO
	 * @return int
	 * @throws Exception
	 */
	public int insertPrdItem(PSCPPRD0003VO bean) throws Exception;
	
	/**
	 * 단품 정보 수정 처리
	 * @param VO
	 * @return int
	 * @throws Exception
	 */
	public int updatePrdItem(PSCPPRD0003VO bean) throws Exception;

	/**
	 * 단품 정보 수정 처리
	 * @return int
	 * @throws Exception
	 */
	public int updateTprItemList(PSCPPRD0003VO bean, String mode) throws Exception;


	/**
	 * 단품 사이즈 콤보 목록
	 * @param map
	 * @return List<DataMap>
	 * @throws Exception
	 */
	public List<DataMap> selectPrdItemSizeList(Map<String, String> map) throws Exception;	

}
