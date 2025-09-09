package com.lottemart.epc.edi.product.service;

import java.util.List;
import java.util.Map;

import lcn.module.common.util.HashBox;

import com.lottemart.epc.edi.product.model.ColorSize;
import com.lottemart.epc.edi.product.model.EcomAddInfo;
import com.lottemart.epc.edi.product.model.NewProduct;
import com.lottemart.epc.edi.product.model.SearchProduct;

public interface PEDMPRO0003Service {
	void updateNewProductInfoInTemp(NewProduct newProduct);
	
	List<NewProduct> selectNewProductListInTemp(SearchProduct searchParam);
	
	NewProduct selectNewProductInfoInTemp(String newProductCode);

	/**
	 * 구 EDI 조회용 신규상품등록현황조회 & 임시보관함에서 상세보기 
	 * @param newProductCode
	 * @return
	 */
	NewProduct selectNewProductInfoInTempOld(String newProductCode);
	

	void fixNewProductInTemp(String productCode, String productType, List fileList) throws Exception ;

	List<ColorSize> selectProductColorListInTemp(String newProductCode); 
	
	List<ColorSize> selectProductColorListInTempOld(String newProductCode);
	
	List<ColorSize> sellCodeView(Map<String,Object> map);
	
	
	List<EcomAddInfo> selectProductEcomAddInfoListInTemp(String newProductCode); 
	
	
	List selctPogList() throws Exception;
	void updatePogList(HashBox hData)throws Exception ;
	
	List<ColorSize> selectProductItemListInTemp(String newProductCode); 
	
	List<ColorSize> selectProductItemListInTempOld(String newProductCode);
	
	public int selectMdSendDivnCdCheck(String pgmId) throws Exception;
}
