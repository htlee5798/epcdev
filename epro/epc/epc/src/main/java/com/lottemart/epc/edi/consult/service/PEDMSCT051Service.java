package com.lottemart.epc.edi.consult.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lcn.module.common.util.HashBox;

import com.lottemart.epc.edi.consult.model.Sale;
import com.lottemart.epc.edi.comm.model.SearchParam;
import com.lottemart.epc.edi.consult.model.Vendor;
import com.lottemart.epc.edi.consult.model.VendorProduct;
import com.lottemart.epc.edi.consult.model.AutionItem;


public interface PEDMSCT051Service {

	Vendor selectVendorInfo(String businessNo);
	Vendor selectVendorInfoResult(String businessNo);
	
	Vendor selectVendorInfo2(String businessNo);

	
	Vendor selectVendorInfoApply(String businessNo);
	
	Integer selectVendorPassword(SearchParam searchParam);

	Integer updateVendorPassword(SearchParam searchParam);

	List<Sale> selectSaleInfoByVendor(String businessNo);

	List<VendorProduct> selectVendorProduct(String businessNo);
	
	//List<AutionItem> selectAuItem(String businessNo);
	List<AutionItem> selectAuItem(String businessNo,String L1_CD);
	
	List<AutionItem> selectAuItemSum(String businessNo);	

	void insertNewVendorInfo(Vendor submittedVendorInfo);
	
	void insertNewVendorInfoApply(Vendor submittedVendorInfo); //20150715 입점개편 이동빈
	
	
	//void insertNewVendorInfoNew(String pwd , String businessNo, String l1Cd);
	void insertNewVendorInfoNew(String pwd , String businessNo, String teamCd);
	
	void updateVendorInfo(Vendor submittedVendorInfo);
	
	void updateVendorInfoApply(Vendor submittedVendorInfo); //20150715 입점개편 이동빈
	
	void insertNewVendorAnswer(HashMap<String, Object> hmap); //20150715 입점개편 이동빈
	
	//void updateVendorInfoNew(String pwd , String businessNo, String l1Cd);
	void updateVendorInfoNew(String pwd , String businessNo, String teamCd);
	
	//void updateVendorSaleInfo(Vendor vendor, List<Sale> saleDataList);
	void updateVendorSaleInfo(Vendor vendor);

	void updateVendorProductInfo(Vendor vendor,
			List<VendorProduct> vendorProductList);

	void updateVendorProductInfoApply(Vendor vendor,
			List<VendorProduct> vendorProductList);
	
	void updateVendorProductInfoApplySupport(Vendor vendor,
			List<VendorProduct> vendorProductList);
	
	void updateVendorStatus(Vendor vendor);
	
	void updateVendorStatusApply(Vendor vendor);
	

	public HashBox selectResultInfo(String businessNo) throws Exception;
	
	public HashBox processCk(String businessNo) throws Exception;
	
	void deleteAuctionProdItem( String businessNo);
	
	void insertAuctionProdItem(String prodSeq , String businessNo);
	
	Vendor selectNewVendorInfoApply(String bmanNo);
	
	void updateNewVendorInfoApply(HashMap<String, Object> hmap);

	List<Vendor> selectVendorAnswer(String businessNo);

	void insertNewVendorKindCd(HashMap<String, Object> hmap);

	void updateNewVendorKindCd(HashMap<String, Object> hmap);

	void deleteNewVendorAnswer(HashMap<String, Object> hmap);

	void insertNewVendorAnswer2(HashMap<String, Object> hmap);
}
