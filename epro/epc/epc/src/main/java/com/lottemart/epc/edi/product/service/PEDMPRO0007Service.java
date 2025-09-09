package com.lottemart.epc.edi.product.service;

import java.util.List;
import java.util.Map;

import com.lottemart.epc.edi.product.model.PEDMPRO0005VO;
import com.lottemart.epc.edi.product.model.SearchProduct;



public interface PEDMPRO0007Service {


	List<PEDMPRO0005VO> selectProductImageList(SearchProduct searchParam);

	void deleteMDLegacyImageData(SearchProduct searchParam);
	
	void changeMDLegacyImageDataState(SearchProduct searchParam);

	void updateMDPOGImageSize(SearchProduct searchParam);
}
