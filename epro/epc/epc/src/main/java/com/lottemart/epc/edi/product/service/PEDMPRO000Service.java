package com.lottemart.epc.edi.product.service;

import java.util.List;
import java.util.Map;

import com.lottemart.epc.edi.comm.model.SearchParam;
import com.lottemart.epc.edi.product.model.NewProduct;
import com.lottemart.epc.edi.product.model.PEDMPRO0011VO;
import com.lottemart.epc.edi.product.model.SearchProduct;

public interface PEDMPRO000Service {

	public String selectNewProductCode(); // 가등록번호 채번

	public void insertProductInfo(NewProduct newProduct) throws Exception; // 온라인전용 상품 등록

	public List<NewProduct> selectOnlineRepresentProductList(String entpCode); // 온라인 대표상품 콛드 조회
	
	public List<NewProduct> selectOnlineRepresentProductListOld(String entpCode); // 온라인 대표상품 콛드 조회

	public SearchParam selectCountVendorStopTrading(String vendorCode); // 협력업체 거래중지 여부

	public SearchParam selectCountVendorStopTradingOld(String vendorCode); // 협력업체 거래중지 여부

	public void deleteProdAddDetail(NewProduct newProduct); // 해당 상품 부가정보 삭제

	public void insertProdAddDetail(NewProduct newProduct); // 상품 부가정보 생성 

	public void deleteProdCertDetail(NewProduct newProduct); // 상품 KC인증 삭제

	public void insertProdCertDetail(NewProduct newProduct); // 상품 KC인증 생성

	public List<String> selectProductColorList(String newProductCode);

	public List<String> selectProductColorListOld(String newProductCode);

	public Map<String, Object> deleteTempProductItem(Map<String, Object> map); // 온라인 전용 단품정보 삭제

	/**
	 * 구 EDI 신규상품등록현황 조회 Service
	 * @param searchProduct
	 * @return
	 */
	public List<NewProduct> selectNewProductListOld(SearchProduct searchProduct);

	/* ****************************** */
	// 20180821 - 상품키워드 입력 기능 추가
	/**
	 * 신규 상품키워드 조회
	 * @param newProductCode
	 * @return List<PEDMPRO0003VO>
	 * @throws Exception
	 */
	public List<PEDMPRO0011VO> selectTpcPrdKeywordList(String newProductCode) throws Exception;

	/**
	 * 신규 상품키워드 등록
	 * @return int
	 * @throws Exception
	 */
	public int insertTpcPrdKeyword(PEDMPRO0011VO bean) throws Exception;

	/**
	 * 신규 상품키워드 삭제
	 * @return INT
	 * @throws Exception
	 */
	public Map<String, Object> deleteTpcPrdKeyword(Map<String, Object> map) throws Exception;
	// 20180821 - 상품키워드 입력 기능 추가
	/* ****************************** */

	/**
	 * 신규 상품키워드 전체 삭제
	 */
	public int deleteTpcPrdAllKeyword(String pgmId) throws Exception;

	public int deleteTpcNewPrdAllKeyword(String pgmId) throws Exception;

}