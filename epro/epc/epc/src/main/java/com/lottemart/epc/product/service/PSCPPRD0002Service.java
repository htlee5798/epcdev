package com.lottemart.epc.product.service;


import java.util.List;
import java.util.Map;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.product.model.PSCPPRD0002VO;

/**
 * @Class Name : PSCPPRD0002Service.java
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
public interface PSCPPRD0002Service 
{
	/**
	 * 공통코드 목록
	 * @return DataMap
	 * @throws Exception
	 */
	public List<DataMap> selectPrdCommonCodeList() throws Exception;
	
	/**
	 * 상품 상세정보 조회
	 * @param String
	 * @return DataMap
	 * @throws Exception
	 */
	public DataMap selectPrdInfo(String prodCd) throws Exception;
	
	/**
	 * 상품 테마 딜 설정 조회
	 * @param prodCd
	 * @return
	 * @throws Exception
	 */
	public DataMap selectPrdExtThemaDealInfo(String prodCd) throws Exception;

	/**
	 * 상품 테마 딜 설정 등록/수정
	 * @param dataMap
	 * @return
	 * @throws Exception
	 */
	public int updatePrdExtThemaDealInfo(DataMap dataMap) throws Exception;
		
	/**
	 * 상품 상세정보 수정
	 * @param VO
	 * @return int
	 * @throws Exception
	 */
	public int updatePrdInfo(PSCPPRD0002VO bean) throws Exception;

	/**
	 * 상품 증정품 COMBO내용
	 * @param String
	 * @return List<DataMap>
	 * @throws Exception
	 */
	public List<DataMap> selectPrdPresentList(String prodCd) throws Exception;
	
	/**
	 * 전상법 품목 체크
	 * @return int
	 * @throws Exception
	 */
	public String prodCommerceCheck(String prodCd, String flag) throws Exception;
	
	//20180911 상품키워드 입력 기능 추가
	/**
	 * 상품검색어 확인 여부
	 * @Method selectKeywordYnChk
	 * @param String prodCd
	 * @return MAP
	 */	
	public Map<String, Object>	selectKeywordYnChk(Map<String, Object> paramMap) throws Exception;
	//20180911 상품키워드 입력 기능 추가
	
}
