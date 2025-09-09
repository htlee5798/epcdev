package com.lottemart.epc.edi.product.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.dao.DataAccessException;

import com.lottemart.epc.edi.comm.model.EdiCommonCode;
import com.lottemart.epc.edi.comm.model.SearchParam;
import com.lottemart.epc.edi.product.model.EdiCdListCodeVO;
import com.lottemart.epc.edi.product.model.PEDMPRO0005VO;
import com.lottemart.epc.edi.product.model.PEDMPRO0095VO;
import com.lottemart.epc.edi.product.model.PEDMPRO0096VO;
import com.lottemart.epc.edi.product.model.PEDMPRO0097VO;
import com.lottemart.epc.edi.product.model.PEDMPRO0098VO;
import com.lottemart.epc.edi.product.model.PEDMPRO0099VO;
import com.lottemart.epc.edi.product.model.SearchProduct;


public interface PEDMPRO0099Service {	
	
	/**
	 * List Count
	 * @param searchParam
	 * @return
	 */
	Integer selectTotalProductCount(SearchProduct searchParam);
	
	/**
	 * List
	 * @param searchParam
	 * @return
	 */
	List<PEDMPRO0099VO> selectWholeProductList(SearchProduct searchParam);
	
	/**
	 * 기존상품에 입력된 팀, 대분류, 세분류 정보 조회
	 * @param pEDMPRO0098VO
	 * @return
	 */
	PEDMPRO0098VO selectNewWholeProductOldTeamInfo (PEDMPRO0098VO pEDMPRO0098VO);
	
	/**
	 * 새로입력될 속성 List
	 * @param pEDMPRO0098VO
	 * @return
	 */
	List<PEDMPRO0097VO> selectNewWholeProductAttrList(PEDMPRO0098VO pEDMPRO0098VO); 
	
	/**
	 * 새로입력될 속성타입이 콤보일경우 콤보박스 구성
	 * @param pEDMPRO0098VO
	 * @return
	 */
	List<PEDMPRO0096VO> selectNewWholeProductAttComboList(PEDMPRO0098VO pEDMPRO0098VO);
	
	/**
	 * 그룹분석속성 저장
	 * @param pEDMPRO0096VO
	 * @return
	 */
	public Map<String, Object> insertWholeProductAtt(PEDMPRO0096VO pEDMPRO0096VO, HttpServletRequest request);
	
	/**
	 * 그룹분석속성 삭제
	 * @param pEDMPRO0096VO
	 * @param request
	 * @return
	 */
	public Map<String, Object> deleteGroupAttr(PEDMPRO0096VO pEDMPRO0096VO, HttpServletRequest request);
	
	/**
	 * 입력된 그룹속성분류코드 값 리스트 조회
	 * @param pEDMPRO0095VO
	 * @param request
	 * @return
	 */
	public List<PEDMPRO0095VO> selectGrpAttrValList(PEDMPRO0098VO pEDMPRO0098VO, HttpServletRequest request);
	
	/**
	 * 기존 세분류에 매핑되어 있는  SAP_L3_CD 카운트
	 * @param pEDMPRO0098VO
	 * @return
	 */
	public Integer selectWholeGrpArrCnt(PEDMPRO0098VO pEDMPRO0098VO);
	
	/**
	 * 기존 세분류에 매핑되어 있는 SAP 소분류 콤보리스트 조회
	 * @param pEDMPRO0098VO
	 * @return
	 */
	public List<PEDMPRO0099VO> selectSapMapAttrList(PEDMPRO0098VO pEDMPRO0098VO);
	
	/**
	 * 규격단위 콤보리스트 조회
	 * @param pEDMPRO0098VO
	 * @return
	 */
	public List<PEDMPRO0095VO> selectkyekeokList(PEDMPRO0098VO pEDMPRO0098VO);
	
	/**
	 * 상품 분석속성관리(일괄 list count)
	 * @param searchParam
	 * @return
	 */
	Integer selectNewWholeProductCountBatch(SearchProduct searchParam);
	
	/**
	 * 상품분석속성관리(일괄) 리스트
	 * @param searchProduct
	 * @return
	 */
	public List<PEDMPRO0099VO> selectNewWholeProductListBatch(SearchProduct searchProduct);
	
	/**
	 * 팀에 해당하는 대분류 콤보박스 리스트
	 * @param searchParam
	 * @return
	 */
	public List<EdiCommonCode> selectWholeProductAttrBatchTeamCombo(SearchProduct searchParam);
	
	/**
	 * 대분류에 해당하는 세분류 콤보리스트 구성
	 * @param searchProduct
	 * @return
	 */
	public List<EdiCommonCode> selectProductBatchL4CdList(SearchProduct searchProduct);
	
	/**
	 * 상품속성관리(일괄)저장
	 * @param pEDMPRO0096VO
	 * @param request
	 * @return
	 */
	public Map<String, Object> insertWholeProductAttBatch(PEDMPRO0096VO pEDMPRO0096VO, HttpServletRequest request);
	
	/**
	 * 상품속성관리(일괄) sap 소분류에 따른 그룹분석속성리스트 조회
	 * @param pEDMPRO0098VO
	 * @return
	 */
	public List<PEDMPRO0097VO> selectNewWholeProductAttrListBatch(PEDMPRO0098VO pEDMPRO0098VO);
	
	/**
	 * 상품속성관리(일괄) 삭제
	 * @param pEDMPRO0096VO
	 * @param request
	 * @return
	 */
	public Map<String, Object> delWholeProductAttBatch(PEDMPRO0096VO pEDMPRO0096VO, HttpServletRequest request);
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 분석속성관리(일괄) 에서 사용할 입력할 대상 상품들의 분석속성&마트단독속성 리스트
	 * @param searchProduct
	 * @return
	 * @throws DataAccessException
	 */
	public List<PEDMPRO0097VO> selectProductBatchGrpAttrList(PEDMPRO0098VO pEDMPRO0098VO);
		
	
	/**
	 * 새로입력될 속성 List
	 * @param pEDMPRO0098VO
	 * @return
	 */
	List<PEDMPRO0097VO> selectProductGrpAttrList(PEDMPRO0098VO pEDMPRO0098VO); 
	
	/**
	 * 새로입력될 속성 List의 콤보박스 리스트 
	 * @param pEDMPRO0098VO
	 * @return
	 */
	public List<PEDMPRO0096VO> selectProductGrpAttrComboList(PEDMPRO0098VO pEDMPRO0098VO);
	
	
	/**
	 * 새로입력될 속성 List의 콤보박스 리스트 [일괄에서 사용] 
	 * @param pEDMPRO0098VO
	 * @return
	 */
	public List<PEDMPRO0096VO> selectProductGrpAttrComboListBatch(PEDMPRO0098VO pEDMPRO0098VO);
	
	/**
	 * 상품 분석속성관리(일괄) 대분류 리스트 조회
	 * @param searchProduct
	 * @return
	 */
	public List<EdiCdListCodeVO> selectProductL1CdListBatch(SearchProduct searchProduct);
	
	/**
	 * 상품 분석속성관리(일괄) 세분류 리스트 조회
	 * @param searchProduct
	 * @return
	 */
	public List<EdiCdListCodeVO> selectProductL4CdListBatch(SearchProduct searchProduct);
	
	/**
	 * 상품속성관리(일괄) 일괄완료처리
	 * @param pEDMPRO0096VO
	 * @param request
	 * @return
	 */
	public Map<String, Object> updateCompleteGroupAttrBatch(PEDMPRO0096VO pEDMPRO0096VO, HttpServletRequest request);
	
	/**
	 * 상품속성관리 완료처리
	 * @param pEDMPRO0096VO
	 * @param request
	 * @return
	 */
	public Map<String, Object> updateCompleteGroupAttr(PEDMPRO0096VO pEDMPRO0096VO, HttpServletRequest request);
	
	/**
	 * sap소분류에 매핑되어 있는 그룹소분류 호출
	 * @param pEDMPRO0098VO
	 * @return
	 */
	public Map<String, Object> selectSrchGrpCd(PEDMPRO0098VO pEDMPRO0098VO);
	
}
