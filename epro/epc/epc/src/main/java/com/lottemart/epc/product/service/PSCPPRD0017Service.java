package com.lottemart.epc.product.service;

import java.util.List;
import java.util.Map;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.product.model.PSCPPRD0005VO;
import com.lottemart.epc.product.model.PSCPPRD0017HistVO;
import com.lottemart.epc.product.model.PSCPPRD0017VO;

/**
 *  
 * @Class Name : PSCPPRD0017Service
 * @Description :
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자	   수정내용
 *  -------    --------    ---------------------------
 * 2011. 12. 09. 오후 03:03:03 mjChoi
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
public interface PSCPPRD0017Service
{
	/**
	 * 상품 키워드 목록
	 * @return PSCPPRD0017VO
	 * @throws Exception
	 */
	public List<PSCPPRD0017VO> selectPrdKeywordList(Map<String, String> paramMap) throws Exception;

	/**
	 * 상품 키워드 입력 처리
	 * @return int
	 * @throws Exception
	 */
	public int insertPrdKeyword(PSCPPRD0017VO bean) throws Exception;
	
	/**
	 * 상품 키워드 수정, 삭제 처리
	 * @return int
	 * @throws Exception
	 */
	public int updatePrdKeywordList(List<PSCPPRD0017VO> pbomprd0006VOList, String mode) throws Exception;
	
	//20180911 상품키워드 입력 기능 추가
	/**
	 * 상품키워드 이력 마스터
	 * @param Map<String, String>
	 * @return PSCPPRD0005VO
	 * @throws Exception
	 */
	public PSCPPRD0005VO selectPrdMdAprvMst(Map<String, String> paramMap) throws Exception;
	
	/**
	 * 상품키워드 이력 마스터
	 * @param DataMap 
	 * @return INT
	 * @throws Exception
	 */
	public int selectPrdMdAprvMstCnt(DataMap paramMap) throws Exception;
	
	/**
	 * 상품키워드 이력 마스터 입력 처리
	 * @param VO	
	 * @return INT
	 * @throws Exception
	 */
	public String insertPrdMdAprvMst(PSCPPRD0005VO bean) throws Exception;
	
	
	/**
	 * 상품키워드 이력 원본 삭제
	 * @Method deletePrdKeywordHist
	 * @param DataMap
	 * @return INT
	 * @throws Exception
	 */
	public int deletePrdKeywordHistAll(DataMap paramMap) throws Exception;
	
	/**
	 * 상품키워드 이력 원본 삭제
	 * @Method insertPrdKeywordHist
	 * @param DataMap
	 * @return INT
	 * @throws Exception
	 */
	public int insertPrdKeywordHistAll(DataMap paramMap) throws Exception;
	
	/**
	 * 상품키워드 이력 입력 처리
	 * @return INT
	 * @throws Exception
	 */
	public int insertPrdKeywordHist(PSCPPRD0017HistVO bean) throws Exception;
	
	/**
	 * 상품키워드 이력 수정, 삭제 처리
	 * @return INT
	 * @throws Exception
	 */
	public int updatePrdKeywordHistList(List<PSCPPRD0017HistVO> pSCPPRD0017HistVO, String mode) throws Exception;
	//20180911 상품키워드 입력 기능 추가
	
	public int mergePrdKeywordHistAll(DataMap paramMap) throws Exception;
	
	public int masterKeywordHistUpdate(DataMap paramMap) throws Exception;

	

}