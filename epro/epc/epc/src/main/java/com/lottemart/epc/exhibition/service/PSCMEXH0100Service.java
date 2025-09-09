/**
 * @prjectName  : 롯데마트 온라인 쇼핑몰 lottemart-epc
 * @since    : 2016.05
 * @Description : SCM 기획전관리
 * @author : ljh
 * @Copyright (C) 2011 ~ 2012 lottemart All right reserved.
 * </pre>
 */
package com.lottemart.epc.exhibition.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.lottemart.epc.exhibition.model.PSCMEXH010050VO;
import com.lottemart.common.util.DataMap;


public interface PSCMEXH0100Service {
	
	//기획전관리 - 리스트 조회
	public List<DataMap> selectExhibitionList(DataMap paramMap) throws Exception;
	
	//통합기획전관리 - 리스트 조회
	public List<DataMap> selectExhibitionIntList(DataMap paramMap) throws Exception;
	
	//기획전관리 - 기획전 대카테고리 콤보 조회
	public List<DataMap> selectCategoryList() throws Exception;
	
	//기획전관리 - 승인처리
	public int updateApply(HttpServletRequest request)  throws Exception;

	//기획전관리 - 기획전 등록팝업 조회
	public DataMap selectExhibitionInfo(DataMap param) throws Exception;
	
	//기획전관리 - 기획전 기본정보 등록
	public int insertExhibition(DataMap param, HttpServletRequest request) throws Exception;
	
	//기획전관리 - 기획전 소카테고리 콤보 조회
	public List<DataMap> selectCategoryChildIdList(DataMap param) throws Exception;

	
	
	//기획전관리 - 기획전 구분자정보 조회
	public List<DataMap> selectDivnInfoList(DataMap param) throws Exception;
	
	//기획전관리 - 기획전 구분자정보 등록
	public int insertDivnInfo(DataMap param) throws Exception;
	
	//기획전관리 - 이미지/HTML 정보 - 기획전 내용(소개) 목록 조회
	public List<DataMap> selectContentsList(DataMap param) throws Exception;
	
	//기획전관리 - 이미지/HTML 정보 - 기획전 내용(소개) 저장
	public int insertContentsImage(DataMap param, HttpServletRequest request)  throws Exception;
	
	//기획전관리 - 이미지/HTML 정보 - 기획전 내용(소개) 정보 조회
	public DataMap selectContentsInfo(DataMap param) throws Exception;
	
	//기획전관리 - 이미지/HTML 정보 - 기획전 내용(소개) 정보 삭제
	public int deleteContentsImage(HttpServletRequest request) throws Exception;

	//기획전관리 - 기획전 구분자 콤보 조회
	public List<DataMap> selectDivnSeqList(DataMap param)  throws Exception;
	
	//기획전관리 - 기획전 승인요청
	public int updateAprv(HttpServletRequest request) throws Exception;
	
	//기획전관리 - 기획전 상품등록
	public int insertExhibitionProdInfo(HttpServletRequest request) throws Exception;
	
	//통합기획전관리 - 통합기획전 상품등록
	public int insertIntExhibitionProdInfo(HttpServletRequest request) throws Exception;

	//기획전관리 - 통합기획전 할당 요청
	public int updateStsCd(HttpServletRequest request) throws Exception;

	//기획전관리 - 통합기획전 상품 삭제
	public int deleteProdCd(HttpServletRequest request) throws Exception;

	//기획전관리 - 기획전 상품등록 팝업 조회
	public List<DataMap> selectExhibitionProdInfoList(DataMap param) throws Exception;
	
	//기획전관리 - 기획전 상품등록 팝업 조회 (구분자조회)
	public List<DataMap> selectDivnSeqlist(DataMap param) throws Exception;

	//기획전관리 - 통합기획전 상품등록 팝업 조회
	public List<DataMap> selectExhibitionProdList1(DataMap param) throws Exception;

	//기획전관리 - 통합기획전 상품등록 팝업 조회
	public List<DataMap> selectExhibitionProdList2(DataMap param) throws Exception;
	
	//기획전관리 - 기획전 상품삭제
	public int deleteExhibitionProdInfo(HttpServletRequest request) throws Exception;
	
	//기획전관리 - 기획전 상품등록 - 상품정렬방식 조회
	public DataMap selectDivnProdSortInfo(DataMap param) throws Exception;

	//기획전관리 - 기획전 상품등록 - 점포별 상품정보 조회(Ajax)
	public List<DataMap> selectProdItemList(DataMap param) throws Exception;
	
	//컨텐츠   채널, 점포범위 체크
	public DataMap getContentsChStoreCheck(DataMap dmapParam) throws Exception;	
	
	/**
	 * Desc : MD 리스트 가져오기
	 * @Method Name : selectMdList
	 * @param paramMap
	 * @throws Exception
	 * @return List<PBOMBRD0014VO>
	 */
	public List<PSCMEXH010050VO> selectMdList(Map<String, String> paramMap) throws Exception;
		
	/**
	 * Desc : MD 총건수
	 * @Method Name : selectMdTotalCnt
	 * @param paramMap
	 * @throws Exception
	 * @return iTotCnt
	 */
	public int selectMdTotalCnt(Map<String, String> paramMap) throws Exception;
	
	public int selectAdminInfoTotalCnt(Map<String, String> paramMap) throws Exception;
	
	public List<DataMap> selectAdminInfoList(Map<String, String> paramMap) throws Exception;
	
	//통합기획전관리 - 통합기획전 기획전명
	public String mkdpNm(DataMap param) throws Exception;
}
