/**
 * @prjectName  : lottemart 프로젝트   
 * @since       : 2011. 10. 18. 오후 2:33:50
 * @author      : yskim 
 * @Copyright(c) 2000 ~ 2011 lottemart
 *  All rights reserved.
 */

package com.lottemart.epc.product.service;

import java.util.List;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.product.model.PSCPPRD0010VO;

/**
 * @Class Name : PSCPPRD0010Service
 * @Description : 상품검색 조회 Service 클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 18. 오후 5:09:06 yskim
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
public interface PSCPPRD0010Service {

	/**
	 * Desc : 상품검색 조회 메소드
	 * @Method Name : selectProductPopupList
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public List<PSCPPRD0010VO> selectProductPopupList(DataMap paramMap) throws Exception;
	
	public List<PSCPPRD0010VO> selectProductArrayPopupList(DataMap paramMap) throws Exception;
	
	/**
	 * Desc : 상품검색 카운트 조회 메소드
	 * @Method Name : selectProductPopupCount
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public List<DataMap> selectProductPopupCount(DataMap paramMap) throws Exception;

	/**
	 * Desc : 상품구분코드 조회 메소드
	 * @Method Name : selectProdDivnCdList
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public List<DataMap> selectProdDivnCdList() throws Exception;

	/**
	 * Desc : 대분류 카테고리 조회 메소드
	 * @Method Name : selectCategoryList
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public List<DataMap> selectCategoryList() throws Exception;

	/**
	 * Desc : 중분류 카테고리 조회 메소드
	 * @Method Name : selectMiddleCategoryList
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public List<DataMap> selectMiddleCategoryList(DataMap paramMap) throws Exception;

	/**
	 * Desc : 소분류 카테고리 조회 메소드
	 * @Method Name : selectSubCategoryList
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	 public List<DataMap> selectSubCategoryList(DataMap paramMap) throws Exception;
	
	/**
	 * Desc : 세분류 카테고리 조회 메소드
	 * @Method Name : selectDetailCategoryList
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	 /* 2015.10.30 by kmlee 카테고리 체계 변경으로 사용하지 않는 함수임.
	 public List<DataMap> selectDetailCategoryList(DataMap paramMap) throws Exception;
	 */
}
