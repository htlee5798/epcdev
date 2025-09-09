package com.lottemart.epc.product.service;


import java.util.List;
import java.util.Map;

import com.lottemart.epc.product.model.PSCPPRD00041VO;
import com.lottemart.epc.product.model.PSCPPRD0004VO;

/**
 * @Class Name : PSCPPRD0004Service.java
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
public interface PSCPPRD0004Service {
	/**
	 * 추천상품 목록
	 * @param Map<String, String>
	 * @return List<VO>
	 * @throws Exception
	 */
	public List<PSCPPRD0004VO> selectPrdRecommendList(Map<String, String> paramMap) throws Exception;

	/**
	 * 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<PSCPPRD00041VO> selectPrdThemaList(Map<String, String> paramMap) throws Exception;

	/**
	 * 추천상품 수정, 삭제 처리
	 * @param List<VO>
	 * @param String
	 * @return int
	 * @throws Exception
	 */
	public int updatePrdRecommendList(List<PSCPPRD0004VO> pscpprd0004VOList, String mode) throws Exception;

	/**
	 * 
	 * @param pscpprd00041VOList
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public int updatePrdPrdThemaList(List<PSCPPRD00041VO> pscpprd00041VOList) throws Exception;
	
	/**
	 * 추천할 상품 목록 총카운트
	 * @param Map<String, String>
	 * @return int
	 * @throws Exception
	 */
	public int selectPrdRecommendCrossTotalCnt(Map<String, String> paramMap) throws Exception;
	
	/**
	 * 추천할 상품 목록
	 * @param Map<String, String>
	 * @return List<VO>
	 * @throws Exception
	 */
	public List<PSCPPRD0004VO> selectPrdRecommendCrossList(Map<String, String> paramMap) throws Exception;
	
	/**
	 * 추천상품 입력 처리
	 * @param List<VO>
	 * @return int
	 * @throws Exception
	 */
	public int insertPrdRecommend(List<PSCPPRD0004VO> pscpprd0004VOList) throws Exception;

}
