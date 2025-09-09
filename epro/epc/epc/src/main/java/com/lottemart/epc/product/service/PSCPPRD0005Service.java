package com.lottemart.epc.product.service;


import java.util.Map;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.product.model.PSCPPRD0005VO;

/**
 * @Class Name : PSCPPRD0005Service.java
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
public interface PSCPPRD0005Service 
{
	/**
	 * 추가설명 정보
	 * @param Map<String, String>
	 * @return PSCPPRD0005VO
	 * @throws Exception
	 */
	public PSCPPRD0005VO selectPrdDescription(Map<String, String> paramMap) throws Exception;
	
	/**
	 * 추가설명 정보 입력 처리
	 * @param VO	
	 * @return int
	 * @throws Exception
	 */
	public int insertPrdDescription(PSCPPRD0005VO bean) throws Exception;
	
	/**
	 * 추가설명 정보 수정 처리
	 * @param VO
	 * @return int
	 * @throws Exception
	 */
	public int updatePrdDescription(PSCPPRD0005VO bean) throws Exception;
	
	/**
	 * 추가설명 정보 로그
	 * @param bean
	 * @return int
	 * @throws Exception
	 */
	public int insertPrdDescrLog(PSCPPRD0005VO bean) throws Exception;
	
	/**
	 * 추가설명 정보
	 * @param Map<String, String>
	 * @return PSCPPRD0005VO
	 * @throws Exception
	 */
	public PSCPPRD0005VO selectPrdMdAprvMst(Map<String, String> paramMap) throws Exception;
	
	/**
	 * 상품 MD승인 히스토리 마스터 카운트
	 * @param DataMap 
	 * @return int
	 * @throws Exception
	 */
	public int selectPrdMdAprvMstCnt(DataMap paramMap) throws Exception;
	
	/**
	 * 추가설명 히스토리 마스터 정보 입력 처리
	 * @param VO	
	 * @return int
	 * @throws Exception
	 */
	public String insertPrdMdAprvMst(PSCPPRD0005VO bean) throws Exception;	
	
	/**
	 * 추가설명 히스토리 마스터 정보 입력 처리
	 * @param VO	
	 * @return int
	 * @throws Exception
	 */
	public int insertPrdDescriptionHist(PSCPPRD0005VO bean) throws Exception;

	/**
	 * 추가설명 히스토리 정보 수정 처리
	 * @param VO
	 * @return int
	 * @throws Exception
	 */
	public int updatePrdDescriptionHist(PSCPPRD0005VO bean) throws Exception;

}
