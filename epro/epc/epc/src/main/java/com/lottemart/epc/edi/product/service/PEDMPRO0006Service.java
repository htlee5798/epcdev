package com.lottemart.epc.edi.product.service;

import java.util.HashMap;
import java.util.List;

import com.lottemart.epc.edi.product.model.PEDMPRO00061VO;
import com.lottemart.epc.edi.product.model.PEDMPRO0006VO;

public interface PEDMPRO0006Service {

	public HashMap selectBarCodeProductInfo(PEDMPRO0006VO searchParam);
	
	public HashMap selectNewprodregInfo(PEDMPRO0006VO searchParam) ;
	
	public List selectBarcodeList(PEDMPRO0006VO searchParam) ;

	public void insertNewBarcode(PEDMPRO0006VO pedmpro0006vo);
	
	
	public HashMap newBarcodeRegistTmp(String val) ;
	public HashMap selectBarcodeListTmp(PEDMPRO0006VO searchParam) ;
	public void insertNewBarcodeTmp(PEDMPRO0006VO pedmpro0006vo);
	
}
