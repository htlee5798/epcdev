package com.lottemart.epc.edi.product.dao;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.lottemart.epc.edi.comm.model.EdiCommonCode;
import com.lottemart.epc.edi.product.model.EdiCdListCodeVO;
import com.lottemart.epc.edi.product.model.PEDMPRO0005VO;
import com.lottemart.epc.edi.product.model.PEDMPRO0095VO;
import com.lottemart.epc.edi.product.model.PEDMPRO0096VO;
import com.lottemart.epc.edi.product.model.PEDMPRO0097VO;
import com.lottemart.epc.edi.product.model.PEDMPRO0098VO;
import com.lottemart.epc.edi.product.model.PEDMPRO0099VO;
import com.lottemart.epc.edi.product.model.SearchProduct;

/**
 * @Class Name : PEDMPRO0099Dao.java
 * @Description : 기존상품 관련 dao 클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      		수정자           		수정내용
 *  -------    --------    ---------------------------
 * 2015.11.10	SONG MIN KYO	최초생성
 * 
 * @Copyright (C) 2000 ~ 2011 롯데정보통신(주) All right reserved.
 * </pre>
 */
@Repository("pEDMPRO0099Dao")
public class PEDMPRO0099Dao extends SqlMapClientDaoSupport {

	@Resource(name = "sqlMapClient")
    public void setSuperSqlMapClient(SqlMapClient sqlMapClient) {
        super.setSqlMapClient(sqlMapClient);
    }
	
	/** 2016.03.07 이전 버전
	 * List Count
	 * @param searchParam
	 * @return
	 * @throws DataAccessException
	 */
	public Integer selectTotalProductCount(SearchProduct searchParam) throws DataAccessException  {
		// TODO Auto-generated method stub
		return (Integer) getSqlMapClientTemplate().queryForObject("PEDMPRO0099.selectNewWholeProductCount", searchParam);
	}
	
	/**
	 * List count
	 * 2016.03.07 이후 버전
	 * @param searchParam
	 * @return
	 * @throws DataAccessException
	 */	
	@SuppressWarnings("unchecked")
	public List<PEDMPRO0099VO> selectTotalProductCount_3(SearchProduct searchProduct) throws DataAccessException {
		// TODO Auto-generated method stub
		return (List<PEDMPRO0099VO>)getSqlMapClientTemplate().queryForList("PEDMPRO0099.selectNewWholeProductCount_3", searchProduct);
	}
	
	/**
	 * list count 2016.03.10 이후 버전
	 * @param searchParam
	 * @return
	 * @throws DataAccessException
	 */
	public Integer selectTotalProductCount_4(SearchProduct searchParam) throws DataAccessException  {
		// TODO Auto-generated method stub
		return (Integer) getSqlMapClientTemplate().queryForObject("PEDMPRO0099.selectNewWholeProductCount_4", searchParam);
	}
	
	
	/**
	 * List
	 * 2016.03.07 이전버전
	 * @param searchProduct
	 * @return
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<PEDMPRO0005VO> selectWholeProductList(SearchProduct searchProduct) throws DataAccessException {
		// TODO Auto-generated method stub
		return (List<PEDMPRO0005VO>)getSqlMapClientTemplate().queryForList("PEDMPRO0099.selectNewWholeProductList", searchProduct);
	}
	
	/**
	 * List
	 * 2016.03.07 이후버전
	 * @param searchProduct
	 * @return
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<PEDMPRO0099VO> selectWholeProductList_3(SearchProduct searchProduct) throws DataAccessException {
		// TODO Auto-generated method stub
		return (List<PEDMPRO0099VO>)getSqlMapClientTemplate().queryForList("PEDMPRO0099.selectNewWholeProductList_3", searchProduct);
	}
	
	/**
	 * List 2016.03.10 이후 버전
	 * @param searchProduct
	 * @return
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<PEDMPRO0099VO> selectNewWholeProductList_4(SearchProduct searchProduct) throws DataAccessException {
		// TODO Auto-generated method stub
		return (List<PEDMPRO0099VO>)getSqlMapClientTemplate().queryForList("PEDMPRO0099.selectNewWholeProductList_4", searchProduct);
	}
	
	
	/**
	 * 기존상품에 입력된 팀, 대분류, 세분류 정보 
	 * @param pEDMPRO0098VO
	 * @return
	 * @throws DataAccessException
	 */
	public PEDMPRO0098VO selectNewWholeProductOldTeamInfo(PEDMPRO0098VO pEDMPRO0098VO) throws DataAccessException  {
		// TODO Auto-generated method stub
		return  (PEDMPRO0098VO) getSqlMapClientTemplate().queryForObject("PEDMPRO0099.selectNewWholeProductOldTeamInfo", pEDMPRO0098VO);
	}
	
	/**
	 * 새로 추가 입력될 속성 List
	 * @param pEDMPRO0098VO
	 * @return
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<PEDMPRO0097VO> selectNewWholeProductAttrList(PEDMPRO0098VO pEDMPRO0098VO) throws DataAccessException {
		// TODO Auto-generated method stub
		return (List<PEDMPRO0097VO>)getSqlMapClientTemplate().queryForList("PEDMPRO0099.selectNewWholeProductAttrList", pEDMPRO0098VO);
	}
	
	/**
	 * 새로 입력될 속성의 타입이 콤보일 경우 콤보리스트 구성 조회
	 * @param pEDMPRO0098VO
	 * @return
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<PEDMPRO0096VO> selectNewWholeProductAttComboList(PEDMPRO0098VO pEDMPRO0098VO) throws DataAccessException {
		// TODO Auto-generated method stub
		return (List<PEDMPRO0096VO>)getSqlMapClientTemplate().queryForList("PEDMPRO0099.selectNewWholeProductAttComboList", pEDMPRO0098VO);
	}
	
	/**
	 * 그룹분석속성 삭제
	 * @param pEDMPRO0096VO
	 * @throws DataAccessException
	 */
	public void deletetWholeProductAtt(PEDMPRO0096VO pEDMPRO0096VO) throws DataAccessException {
		getSqlMapClientTemplate().insert("PEDMPRO0099.deletetWholeProductAtt", pEDMPRO0096VO);
	}
		
	/**
	 * 그룹분석속성 저장
	 * @param pEDMPRO0096VO
	 * @throws DataAccessException
	 */
	public void insertWholeProductAtt(PEDMPRO0096VO pEDMPRO0096VO) throws DataAccessException {
		getSqlMapClientTemplate().insert("PEDMPRO0099.insertWholeProductAtt", pEDMPRO0096VO);
	}
	
	/**
	 * 입력된 그룹속성분류코드 값 리스트 조회
	 * @param pEDMPRO0095VO
	 * @return
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<PEDMPRO0095VO> selectGrpAttrValList(PEDMPRO0098VO pEDMPRO0098VO) throws DataAccessException {
		// TODO Auto-generated method stub
		return (List<PEDMPRO0095VO>)getSqlMapClientTemplate().queryForList("PEDMPRO0099.selectGrpAttrValList", pEDMPRO0098VO);
	}
	
	/**
	 * 기존세분류에 매핑 되어 있는 SAP_L3_CD의 카운트 조회
	 * @param pEDMPRO0098VO
	 * @return
	 * @throws DataAccessException
	 */
	public Integer selectWholeGrpArrCnt(PEDMPRO0098VO pEDMPRO0098VO) throws DataAccessException {
		return (Integer) getSqlMapClientTemplate().queryForObject("PEDMPRO0099.selectWholeGrpArrCnt", pEDMPRO0098VO);
	}
	
	
	/**
	 * 기존세분류에 매핑되어 있는 SAP 소분류 콤보 리스트 조회
	 * @param pEDMPRO0098VO
	 * @return
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<PEDMPRO0099VO> selectSapMapAttrList(PEDMPRO0098VO pEDMPRO0098VO) throws DataAccessException {
		// TODO Auto-generated method stub
		return (List<PEDMPRO0099VO>)getSqlMapClientTemplate().queryForList("PEDMPRO0099.selectSapMapAttrList", pEDMPRO0098VO);
	}
	
	/**
	 * 규격단위 콤보리스트 조회
	 * @param pEDMPRO0098VO
	 * @return
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<PEDMPRO0095VO> selectkyekeokList(PEDMPRO0098VO pEDMPRO0098VO) throws DataAccessException {
		// TODO Auto-generated method stub
		return (List<PEDMPRO0095VO>)getSqlMapClientTemplate().queryForList("PEDMPRO0099.selectkyekeokList", pEDMPRO0098VO);
	}
	
	/**
	 * 상품 분석속성관리(일괄) 리스트 카운트 
	 * 2016.03.07 이전 버전
	 * @param searchParam
	 * @return
	 * @throws DataAccessException
	 */
	public Integer selectNewWholeProductCountBatch(SearchProduct searchParam) throws DataAccessException  {
		// TODO Auto-generated method stub
		return (Integer) getSqlMapClientTemplate().queryForObject("PEDMPRO0099.selectNewWholeProductCountBatch", searchParam);
	}
	
	
	/**
	 * 상품 분석속성관리(일괄) 리스트 카운트 
	 * 2016.03.07 이후 버전
	 * @param searchParam
	 * @return
	 * @throws DataAccessException
	 */	
	@SuppressWarnings("unchecked")
	public List<PEDMPRO0099VO> selectNewWholeProductCountBatch_3(SearchProduct searchProduct) throws DataAccessException {
		// TODO Auto-generated method stub
		return (List<PEDMPRO0099VO>)getSqlMapClientTemplate().queryForList("PEDMPRO0099.selectNewWholeProductCountBatch_3", searchProduct);
	}
	
	/**
	 * 일괄 리스트 카운트 2016.03.10 이후 버전
	 * @param searchParam
	 * @return
	 * @throws DataAccessException
	 */
	public Integer selectNewWholeProductCountBatch_4(SearchProduct searchParam) throws DataAccessException  {
		// TODO Auto-generated method stub
		return (Integer) getSqlMapClientTemplate().queryForObject("PEDMPRO0099.selectNewWholeProductCountBatch_4", searchParam);
	}
	
	/**
	 * 상품 분석속성관리(일괄) 리스트 
	 * 2016.03.07 이전 버전
	 * @param searchProduct
	 * @return
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<PEDMPRO0005VO> selectNewWholeProductListBatch(SearchProduct searchProduct) throws DataAccessException {
		// TODO Auto-generated method stub
		return (List<PEDMPRO0005VO>)getSqlMapClientTemplate().queryForList("PEDMPRO0099.selectNewWholeProductListBatch", searchProduct);
	}
	
	/**
	 * 상품 분석속성관리(일괄) 리스트 
	 * 2016.03.07 이후 버전
	 * @param searchProduct
	 * @return
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<PEDMPRO0099VO> selectNewWholeProductListBatch_3(SearchProduct searchProduct) throws DataAccessException {
		// TODO Auto-generated method stub
		return (List<PEDMPRO0099VO>)getSqlMapClientTemplate().queryForList("PEDMPRO0099.selectNewWholeProductListBatch_3", searchProduct);
	}
	
	/**
	 * 상품분석속성관리(일괄) 리스트 2016.03.10 이후 버전
	 * @param searchProduct
	 * @return
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<PEDMPRO0099VO> selectNewWholeProductListBatch_4(SearchProduct searchProduct) throws DataAccessException {
		// TODO Auto-generated method stub
		return (List<PEDMPRO0099VO>)getSqlMapClientTemplate().queryForList("PEDMPRO0099.selectNewWholeProductListBatch_4", searchProduct);
	}
	
	/**
	 * 팀에 해당하는 대분류 콤보박스 리스트 구성
	 * @param searchParam
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<EdiCommonCode> selectWholeProductAttrBatchTeamCombo(SearchProduct searchProduct) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().queryForList("PEDMPRO0099.selectProductBatchL1CdList", searchProduct);
	}
	
	/**
	 * 대븐류에 해당하는 세분류 콤보리스트 구성
	 * @param searchProduct
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<EdiCommonCode> selectProductBatchL4CdList(SearchProduct searchProduct) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().queryForList("PEDMPRO0099.selectProductBatchL4CdList", searchProduct);
	}
	
	/**
	 * 상품속성관리(일괄) 삭제
	 * @param pEDMPRO0096VO
	 * @throws DataAccessException
	 */
	public void deletetWholeProductAttBatch(PEDMPRO0096VO pEDMPRO0096VO) throws DataAccessException {
		getSqlMapClientTemplate().insert("PEDMPRO0099.deletetWholeProductAttBatch", pEDMPRO0096VO);
	}
	
	/**
	 * 상품속성관리(일괄) sap소분류에 따른 그룹분석속성 리스트 조회
	 * @param pEDMPRO0098VO
	 * @return
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<PEDMPRO0097VO> selectNewWholeProductAttrListBatch(PEDMPRO0098VO pEDMPRO0098VO) throws DataAccessException {
		// TODO Auto-generated method stub
		return (List<PEDMPRO0097VO>)getSqlMapClientTemplate().queryForList("PEDMPRO0099.selectNewWholeProductAttrListBatch", pEDMPRO0098VO);
	}
	
	/**
	 * 그룹분석속성 삭제전 카운트
	 * @param pEDMPRO0096VO
	 * @return
	 * @throws DataAccessException
	 */
	public Integer selectDelWholeProductAttCnt(PEDMPRO0096VO pEDMPRO0096VO) throws DataAccessException {
		return (Integer) getSqlMapClientTemplate().queryForObject("PEDMPRO0099.selectDelWholeProductAttCnt", pEDMPRO0096VO);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 분석속성관리(일괄) 에서 사용할 입력할 대상 상품들의 분석속성&마트단독속성
	 * 2016.03.07 이전 버전
	 * @param pEDMPRO0098VO
	 * @return
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<PEDMPRO0097VO> selectProductBatchGrpAttrList(PEDMPRO0098VO pEDMPRO0098VO) throws DataAccessException {
		// TODO Auto-generated method stub
		return (List<PEDMPRO0097VO>)getSqlMapClientTemplate().queryForList("PEDMPRO0099.selectProductBatchGrpAttrList", pEDMPRO0098VO);
	}
	
	/**
	 * 분석속성관리(일괄) 에서 사용할 입력할 대상 상품들의 분석속성&마트단독속성
	 * 2016.03.07 이후 버전
	 * @param pEDMPRO0098VO
	 * @return
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<PEDMPRO0097VO> selectProductBatchGrpAttrList_3(PEDMPRO0098VO pEDMPRO0098VO) throws DataAccessException {
		// TODO Auto-generated method stub
		return (List<PEDMPRO0097VO>)getSqlMapClientTemplate().queryForList("PEDMPRO0099.selectProductBatchGrpAttrList_3", pEDMPRO0098VO);
	}
		
	
	/**
	 * 해당상품의 그룹분석속성 리스트 
	 * 2016.03.07 이전 버전
	 * @param pEDMPRO0098VO
	 * @return
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<PEDMPRO0097VO> selectProductGrpAttrList(PEDMPRO0098VO pEDMPRO0098VO) throws DataAccessException {
		// TODO Auto-generated method stub
		return (List<PEDMPRO0097VO>)getSqlMapClientTemplate().queryForList("PEDMPRO0099.selectProductGrpAttrList", pEDMPRO0098VO);
	}
	
	/**
	 * 해당상품의 그룹분석속성 & 마트단독속성 리스트
	 * 2016.03.07 이후 버전
	 * @param pEDMPRO0098VO
	 * @return
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<PEDMPRO0097VO> selectProductGrpAttrList_3(PEDMPRO0098VO pEDMPRO0098VO) throws DataAccessException {
		// TODO Auto-generated method stub
		return (List<PEDMPRO0097VO>)getSqlMapClientTemplate().queryForList("PEDMPRO0099.selectProductGrpAttrList_3", pEDMPRO0098VO);
	}
	
	/**
	 * 해당상품의 그룹분석속성 리스트의 콤보박스 리스트
	 * 2016.03.07 이전 버전
	 * @param pEDMPRO0098VO
	 * @return
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<PEDMPRO0096VO> selectProductGrpAttrComboList(PEDMPRO0098VO pEDMPRO0098VO) throws DataAccessException {
		// TODO Auto-generated method stub
		return (List<PEDMPRO0096VO>)getSqlMapClientTemplate().queryForList("PEDMPRO0099.selectProductGrpAttrComboList", pEDMPRO0098VO);
	}
	
	/**
	 * 해당상품의 그룹분석속성 리스트의 콤보박스 리스트
	 * 2016.03.07 이후 버전
	 * @param pEDMPRO0098VO
	 * @return
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<PEDMPRO0096VO> selectProductGrpAttrComboList_3(PEDMPRO0098VO pEDMPRO0098VO) throws DataAccessException {
		// TODO Auto-generated method stub
		return (List<PEDMPRO0096VO>)getSqlMapClientTemplate().queryForList("PEDMPRO0099.selectProductGrpAttrComboList_3", pEDMPRO0098VO);
	}
	
	/**
	 * 해당상품의 그룹분석속성 리스트의 콤보박스 리스트[일괄에서 사용]
	 * @param pEDMPRO0098VO
	 * @return
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<PEDMPRO0096VO> selectProductGrpAttrComboListBatch(PEDMPRO0098VO pEDMPRO0098VO) throws DataAccessException {
		// TODO Auto-generated method stub
		return (List<PEDMPRO0096VO>)getSqlMapClientTemplate().queryForList("PEDMPRO0099.selectProductGrpAttrComboListBatch", pEDMPRO0098VO);
	}
	
	
	/**
	 * 상품 분석속성관리(일괄) 대분류 리스트 조회
	 * 2016.03.07 이전 버전
	 * @param searchProduct
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<EdiCdListCodeVO> selectProductL1CdListBatch(SearchProduct searchProduct) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().queryForList("PEDMPRO0099.selectProductL1CdListBatch", searchProduct);
	}
	
	/**
	 * 상품 분석속성관리(일괄) 대분류 리스트 조회
	 * 2016.03.07 이후 버전
	 * @param searchProduct
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<EdiCdListCodeVO> selectProductL1CdListBatch_3(SearchProduct searchProduct) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().queryForList("PEDMPRO0099.selectProductL1CdListBatch_3", searchProduct);
	}
	
	/**
	 * 상품 분석속성관리(일괄) 세분류 리스트 조회
	 * 2016.03.07 이전 버전
	 * @param searchProduct
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<EdiCdListCodeVO> selectProductL4CdListBatch(SearchProduct searchProduct) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().queryForList("PEDMPRO0099.selectProductL4CdListBatch", searchProduct);
	}
	
	/**
	 * 상품 분석속성관리(일괄) 세분류 리스트 조회
	 * 2016.03.07 이후 버전
	 * @param searchProduct
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<EdiCdListCodeVO> selectProductL4CdListBatch_3(SearchProduct searchProduct) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().queryForList("PEDMPRO0099.selectProductL4CdListBatch_3", searchProduct);
	}
	
	/**
	 * 상품 분석속성관리(일괄) 일괄완료처리
	 * @param pEDMPRO0096VO
	 * @throws DataAccessException
	 */
	public void updateCompleteGroupAttrBatch(PEDMPRO0096VO pEDMPRO0096VO) throws DataAccessException {
		getSqlMapClientTemplate().insert("PEDMPRO0099.updateCompleteGroupAttrBatch", pEDMPRO0096VO);
	}
	
	/**
	 * 상품 분석속성관리 완료, 취소 처리
	 * @param pEDMPRO0096VO
	 * @throws DataAccessException
	 */
	public void updateCompleteGroupAttr(PEDMPRO0096VO pEDMPRO0096VO) throws DataAccessException {
		getSqlMapClientTemplate().insert("PEDMPRO0099.updateCompleteGroupAttr", pEDMPRO0096VO);
	}
	
	/**
	 * 상품 완료여부 체크
	 * @param pEDMPRO0096VO
	 * @return
	 * @throws DataAccessException
	 */
	public Integer selectProductCompleChk(PEDMPRO0096VO pEDMPRO0096VO) throws DataAccessException {
		return (Integer) getSqlMapClientTemplate().queryForObject("PEDMPRO0099.selectProductCompleChk", pEDMPRO0096VO);
	}
	
	/**
	 * 해당상품의 판매코드 리스트 조회
	 * @param prodCd
	 * @return
	 * @throws DataAccessException
	 */
	public List<PEDMPRO0099VO> selectSrcmkCdList(String prodCd) throws DataAccessException {
		return getSqlMapClientTemplate().queryForList("PEDMPRO0099.selectSrcmkCdList", prodCd);				
	}	
	
	/**
	 * SAP소분류에 매핑되어 있는 그룹소분류 호출
	 * @param pEDMPRO0098VO
	 * @return
	 * @throws DataAccessException
	 */
	public List<PEDMPRO0099VO> selectSrchGrpCd(PEDMPRO0098VO pEDMPRO0098VO) throws DataAccessException {
		return getSqlMapClientTemplate().queryForList("PEDMPRO0099.selectSrchGrpCd", pEDMPRO0098VO);				
	}
	
}
