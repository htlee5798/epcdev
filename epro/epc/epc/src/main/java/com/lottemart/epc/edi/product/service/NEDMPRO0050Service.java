package com.lottemart.epc.edi.product.service;

import java.util.List;

import com.lottemart.common.util.DataMap;


/**
 * @Class Name : NEDMPRO0050Service
 * @Description : 온라인신상품등록(딜) Service
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      			수정자           		수정내용
 *  -------    		--------    	---------------------------
 * 	2016.04.22		projectBOS32 	최초생성
 * </pre>
 */

public interface NEDMPRO0050Service {

	/**
	 * 묶음상품정보 등록
	 * 
	 * @param DataMap
	 * @return
	 * @throws Exception
	 */
	public void newOnlineDealProductSave(DataMap paramMap) throws Exception;

	/**
	 * 묶음상품정보 조회
	 * 
	 * @param DataMap
	 * @return
	 * @throws Exception
	 */
	public List<DataMap> selectDealProductList(DataMap paramMap) throws Exception;

	/**
	 * 테마 목록 조회
	 * 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<DataMap> selectDealThemaList(DataMap paramMap) throws Exception;

	/**
	 * 테마 딜 상품목록 조회
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<DataMap> selectDealThemaProdList(DataMap paramMap) throws Exception;

}
