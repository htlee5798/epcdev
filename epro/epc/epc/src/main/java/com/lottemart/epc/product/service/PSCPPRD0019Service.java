package com.lottemart.epc.product.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.lottemart.epc.product.model.PSCPPRD0019VO;

/**
 *  
 * @Class Name : PSCPPRD0019Service
 * @Description :
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자	   수정내용
 *  -------    --------    ---------------------------
 * 2011. 9. 7   jib
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
public interface PSCPPRD0019Service {

	/**
	 * 추가속성 목록
	 * @return PSCPPRD0019VO
	 * @throws Exception
	 */
	public List<PSCPPRD0019VO> selectPrdAttributeList(Map<String, String> paramMap) throws Exception;

	/**
	 * 추가속성 입력, 삭제
	 * @return int
	 * @throws Exception
	 
	public int updatePrdAttributeList(List<PSCPPRD0019VO> PSCPPRD0019VOList, String mode) throws Exception;
	*/
	
	/**
	 * 추가속성 입력, 삭제
	 * @return int
	 * @throws Exception
	 */
	public int updatePrdAttributeList(HttpServletRequest request) throws Exception;

	/**
	 * 추가속성 카테고리
	 * @return PSCPPRD0019VO
	 * @throws Exception
	 */
	public PSCPPRD0019VO selectPrdAttributeCategory(Map<String, String> paramMap) throws Exception;

	/**
	 * 추가속성 항목값
	 * @return PSCPPRD0019VO
	 * @throws Exception
	 */
	public PSCPPRD0019VO selectPrdAttributeColVal(Map<String, String> paramMap) throws Exception;
	
	/**
	 * 추가속성 카테고리 목록
	 * @return PSCPPRD0019VO
	 * @throws Exception
	 */
	public List<PSCPPRD0019VO> selectPrdAttributeCategoryList(Map<String, String> paramMap) throws Exception;
	
	/**
	 * 추가속성 N 입력, 업데이트 처리
	 * @return int
	 * @throws Exception
	 */
	public int insertPrdAttribute(PSCPPRD0019VO bean) throws Exception;
}