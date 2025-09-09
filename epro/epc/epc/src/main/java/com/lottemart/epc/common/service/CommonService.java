package com.lottemart.epc.common.service;


import java.util.List;
import java.util.Map;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.common.model.EpcLoginVO;

/**
 * @Class Name : CommonService.java
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
public interface CommonService 
{
	/**
	 * 카테고리 대분류 콤보
	 * @param 
	 * @return List<DataMap>
	 * @throws Exception
	 */
	public List<DataMap> selectDaeCdList() throws Exception;
	
	/**
	 * 카테고리 중분류 콤보
	 * @param Map<String, String>
	 * @return List<DataMap>
	 * @throws Exception
	 */
	public List<DataMap> selectJungCdList(Map<String, String> map) throws Exception;
	
	/**
	 * 카테고리 소분류 콤보
	 * @param Map<String, String>
	 * @return List<DataMap>
	 * @throws Exception
	 */
	public List<DataMap> selectSoCdList(Map<String, String> map) throws Exception;
	
	/**
	 * 카테고리 세분류 콤보
	 * @param Map<String, String>
	 * @return List<DataMap>
	 * @throws Exception
	 */
	public List<DataMap> selectSeCdList(Map<String, String> map) throws Exception;

	/**
	 * Desc : 공통코드 조회
	 * @Method Name : selectTetCodeList
	 * @param map
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	public List<DataMap> selectTetCodeList(Map<String, String> map) throws Exception;

	/**
	 * Desc : 공통코드 조회(Ref 1 ~ 10 으로 필터링)
	 * @Method Name : selectTetCodeRefList
	 * @param map
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	public List<DataMap> selectTetCodeRefList(Map<String, String> map) throws Exception;
	
	/**
	 * Desc : openappi 업체 대표 vendorId 조회
	 * @Method Name : selectOpenappiVendor
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public List<EpcLoginVO> selectOpenappiVendor() throws Exception;

}
