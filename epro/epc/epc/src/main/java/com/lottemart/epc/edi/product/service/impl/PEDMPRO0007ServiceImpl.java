package com.lottemart.epc.edi.product.service.impl;

import java.util.List;
import java.util.Map;


import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.epc.edi.comm.model.Constants;
import com.lottemart.epc.edi.product.dao.PEDMPRO0003Dao;
import com.lottemart.epc.edi.product.dao.PEDMPRO0007Dao;

import com.lottemart.epc.edi.product.model.NewProduct;
import com.lottemart.epc.edi.product.model.PEDMPRO0005VO;
import com.lottemart.epc.edi.product.model.SearchProduct;
import com.lottemart.epc.edi.product.service.PEDMPRO0007Service;

@Service("pEDMPRO0007Service")
public class PEDMPRO0007ServiceImpl implements PEDMPRO0007Service {

	@Autowired
	private PEDMPRO0007Dao pEDMPRO0007Dao;

	@Autowired
	private PEDMPRO0003Dao pEDMPRO0003Dao;

	



	public List<PEDMPRO0005VO> selectProductImageList(SearchProduct searchParam) {
		// TODO Auto-generated method stub
		if(Constants.STANDARD_PRODUCT_CD.equals(searchParam.getProductDivnCode())) { 
			return pEDMPRO0007Dao.selectStandardProductImageList(searchParam);
		} else {
			return pEDMPRO0007Dao.selectFashionProductImageList(searchParam);
		}
	}

	public void deleteMDLegacyImageData(SearchProduct searchParam) {
		// TODO Auto-generated method stub
		pEDMPRO0007Dao.deleteMDLegacyImageData(searchParam.getImageSeq());
		NewProduct paramProduct = new NewProduct();
		
		paramProduct.setNewProductCode(searchParam.getImageSeq());
		paramProduct.setSellCode(searchParam.getSellCode());
		paramProduct.setProductImageId(searchParam.getImageName());
		paramProduct.setEntpCode(searchParam.getEntpCode());
		
	
		paramProduct.setColorCodeCd(searchParam.getColorCd());
		paramProduct.setSizeCategoryCodeCd(searchParam.getSzCatCd());
		paramProduct.setSizeCodeCd(searchParam.getSzCd());
		
		paramProduct.setImageConfirmFlag("1");
		pEDMPRO0003Dao.insertNewProductChangedImageData(paramProduct);
	}

	public void changeMDLegacyImageDataState(SearchProduct searchParam) {
		// TODO Auto-generated method stub
		pEDMPRO0007Dao.changeMDLegacyImageData(searchParam.getImageSeq());
		NewProduct paramProduct = new NewProduct();
		
		paramProduct.setNewProductCode(searchParam.getImageSeq());
		paramProduct.setSellCode(searchParam.getSellCode());
		paramProduct.setProductImageId(searchParam.getImageName());
		paramProduct.setEntpCode(searchParam.getEntpCode());
		
		pEDMPRO0003Dao.insertNewProductImageData(paramProduct);
	}
	
	public void updateMDPOGImageSize(SearchProduct searchParam) {
		// TODO Auto-generated method stub
		String sellCodeValue = pEDMPRO0007Dao.selectMDSellCodeData(searchParam);
		if(StringUtils.isNotEmpty(sellCodeValue)) {
			pEDMPRO0007Dao.updateMDSizeInReserv(searchParam);
		} else {
			pEDMPRO0007Dao.inserNewMDSize(searchParam);
		}
	}

	
}
