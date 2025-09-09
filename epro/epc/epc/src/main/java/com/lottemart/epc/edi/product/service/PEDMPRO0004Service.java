package com.lottemart.epc.edi.product.service;

import java.util.ArrayList;
import java.util.List;

import com.lottemart.epc.edi.product.model.ColorSize;
import com.lottemart.epc.edi.product.model.NewProduct;

public interface PEDMPRO0004Service {

	void insertColorSizeFromExcelBatch(List<ColorSize> colorSizeList) throws Exception;

	void insertProductInfoList(ArrayList<NewProduct> newProductArrayList) throws Exception;
	
	boolean isNotValidTeamcode(String teamCode) throws Exception;
	
	boolean isNotValidEntpcode(String entpCode, String[] sessionUserVendorArrays) throws Exception;
	
	boolean isNotValidL1Code(String teamCode, String l1GroupCode) throws Exception;
	
	boolean isNotValidCommonCode(String taxDivnCode, String parentCode) throws Exception;
	
	boolean isNotValidStyleCode(String mdStyleCode) throws Exception;

	boolean isNotValidSubGroupCode(String teamCode, String l1GroupCode,
			String subGroupCode) throws Exception;
}
