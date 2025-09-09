package com.lottemart.epc.edi.consult.service.impl;

import java.util.HashMap;
import java.util.List;

import lcn.module.common.util.HashBox;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;











import com.lottemart.epc.edi.consult.dao.PEDMSCT051Dao;
import com.lottemart.epc.edi.consult.model.Sale;
import com.lottemart.epc.edi.comm.model.SearchParam;
import com.lottemart.epc.edi.consult.model.Vendor;
import com.lottemart.epc.edi.consult.model.VendorProduct;
import com.lottemart.epc.edi.consult.model.AutionItem;
import com.lottemart.epc.edi.consult.service.PEDMSCT051Service;

@Service("consultService")
public class PEDMSCT051ServiceImpl implements PEDMSCT051Service {

	@Autowired
	private PEDMSCT051Dao consultDao;
	

  
	public Vendor selectVendorInfo(String businessNo) {
		// TODO Auto-generated method stub
		Vendor tmpVendor = consultDao.selectVendorInfo(businessNo);
		return tmpVendor == null ? new Vendor() : tmpVendor ;
	} 
	public Vendor selectVendorInfoResult(String businessNo) {
		// TODO Auto-generated method stub
		Vendor tmpVendor = consultDao.selectVendorInfoResult(businessNo);
		return tmpVendor == null ? new Vendor() : tmpVendor ;
	}
	
	public Vendor selectVendorInfo2(String businessNo) {
		// TODO Auto-generated method stub
		Vendor tmpVendor = consultDao.selectVendorInfo2(businessNo);
		return tmpVendor == null ? new Vendor() : tmpVendor ;
	}

	public Integer selectVendorPassword(SearchParam searchParam) {
		// TODO Auto-generated method stub
		Integer resultCount = consultDao.selectVendorPassword(searchParam);
		
		return resultCount == null ? 0 : resultCount;
	}


	public Integer updateVendorPassword(SearchParam searchParam) {
		// TODO Auto-generated method stub
		return consultDao.updateVendorPassword(searchParam);
	}


	public List<Sale> selectSaleInfoByVendor(String businessNo) {
		// TODO Auto-generated method stub
		return consultDao.selectSaleInfoByVendor(businessNo) ;
	}


	public List<VendorProduct> selectVendorProduct(String businessNo) {
		// TODO Auto-generated method stub
		return consultDao.selectVendorProduct(businessNo);
	}

	public List<Vendor> selectVendorAnswer(String businessNo) {
		// TODO Auto-generated method stub
		return consultDao.selectVendorAnswer(businessNo);
	}
	
	
	public List<AutionItem> selectAuItem(String businessNo,String L1_CD) {
		// TODO Auto-generated method stub
		AutionItem  autionitem  = new AutionItem();
		//searchParam.setBusinessNo(businessNo);
		//searchParam.setProdArraySeq(prodSeq);
		autionitem.setBmanNo(businessNo);
		autionitem.setL1Cd(L1_CD);
		
		return consultDao.selectAuItem(autionitem);
	}
	public List<AutionItem> selectAuItemSum(String businessNo) {
		// TODO Auto-generated method stub
		return consultDao.selectAuItemSum(businessNo);
	}
	
	
	
	//public List<AutionItem> selectAuItem(String businessNo) {
	//	// TODO Auto-generated method stub
	//	return consultDao.selectAuItem(businessNo);
	//}
	
	
	public void insertNewVendorInfo(Vendor submittedVendorInfo) {
		// TODO Auto-generated method stub
		consultDao.insertNewVendorInfo(submittedVendorInfo);
	}

	
	public void updateVendorInfo(Vendor submittedVendorInfo) {
		// TODO Auto-generated method stub
		consultDao.updateVendorInfo(submittedVendorInfo);
	}
	
	public void updateVendorInfoApply(Vendor submittedVendorInfo) {
		// TODO Auto-generated method stub
		consultDao.updateVendorInfoApply(submittedVendorInfo);
	}


	//public void updateVendorSaleInfo(Vendor vendor, List<Sale> saleDataList) {
	public void updateVendorSaleInfo(Vendor vendor) {
		// TODO Auto-generated method stub
		consultDao.updateVendorSaleInfo(vendor);
		
		//consultDao.updateOtherStoreVendorSaleInfo(saleDataList);
	}


	public void updateVendorProductInfo(Vendor vendor,
			List<VendorProduct> vendorProductList) {
		// TODO Auto-generated method stub
		consultDao.updateVendorProductContent(vendor);
		
		consultDao.updateVendorProductList(vendorProductList);
	}


	public void updateVendorStatus(Vendor vendor) {
		// TODO Auto-generated method s	tub
		consultDao.updateVendorStatus(vendor);
	}
	
	public HashBox selectResultInfo(String businessNo) throws Exception {
		// TODO Auto-generated method stub
		return consultDao.selectResultInfo(businessNo);
	}
	
	public HashBox processCk(String businessNo) throws Exception {
		// TODO Auto-generated method stub
		return consultDao.processCk(businessNo);
	}
	
	public void deleteAuctionProdItem( String businessNo){
		SearchParam searchParam = new SearchParam();
		
		searchParam.setBusinessNo(businessNo);
		
		
		consultDao.deleteAuctionProdItem(searchParam);
	}
	
	public void insertAuctionProdItem(String prodSeq , String businessNo){
		SearchParam searchParam = new SearchParam();
		
		searchParam.setBusinessNo(businessNo);
		searchParam.setProdArraySeq(prodSeq);
		
		consultDao.insertAuctionProdItem(searchParam);
	} 
	
	
	public void updateVendorInfoNew(String pwd , String businessNo, String teamCd){
		SearchParam searchParam = new SearchParam();		
		searchParam.setBusinessNo(businessNo);
		//searchParam.setL1Code(l1Cd);
		//searchParam.setL1Code(teamCd);
		searchParam.setTeamCode(teamCd);
		searchParam.setPassword(pwd);		
		consultDao.updateVendorInfoNew(searchParam);
	}
	
	
	public void insertNewVendorInfoNew(String pwd , String businessNo, String teamCd){
		SearchParam searchParam = new SearchParam();		
		searchParam.setBusinessNo(businessNo);
		//searchParam.setL1Code(l1Cd);
		searchParam.setTeamCode(teamCd);
		searchParam.setPassword(pwd);
		consultDao.insertNewVendorInfoNew(searchParam);
	}

	//TED_VENDOR_EDI 포함  Insert 로직 (기존 업체가 아닐경우 )
	@Override
	public void insertNewVendorAnswer(HashMap<String, Object> hmap) {
		
		String temp = (String)hmap.get("colVal");
		String seq  = (String)hmap.get("seq");
		
		String[] colVal = temp.split(";");
		for (int i = 0; i < colVal.length; i++) {
			hmap.put("seq",seq + i);
			hmap.put("colVal", colVal[i]);
			
			consultDao.insertNewVendorInfoApply(hmap);
    	
    	
		}
		consultDao.insertNewVendorInfoApply2(hmap);	
	}

	//TED_VENDOR_EDI 제외 Insert 로직 (기존 업체가 존재할 시에 Insert에러방지)
	@Override
	public void insertNewVendorAnswer2(HashMap<String, Object> hmap) {
		
		String temp = (String)hmap.get("colVal");
		String seq  = (String)hmap.get("seq");
		
		String[] colVal = temp.split(";");
		for (int i = 0; i < colVal.length; i++) {
			hmap.put("seq",seq + i);
			hmap.put("colVal", colVal[i]);
			
			consultDao.insertNewVendorInfoApply(hmap);
    	
		}
	}
	
	@Override
	public Vendor selectNewVendorInfoApply(String bmanNo) {
		
		return consultDao.selectNewVendorInfoApply(bmanNo);
		
	}

	
	@Override
	public void updateNewVendorInfoApply(HashMap<String, Object> hmap) {
		// TODO Auto-generated method stub
		
		String temp = (String)hmap.get("colVal");
		String seq  = (String)hmap.get("seq");
		String[] colVal = temp.split(";");
		for (int i = 0; i < colVal.length; i++) {
			hmap.put("seq",seq + i);
			hmap.put("colVal", colVal[i]);
			
			consultDao.updateNewVendorInfoApply(hmap);
    	}
	//	consultDao.updateNewVendorAnswer(hmap);	
		  
	}

//	@Override
//	public Vendor selectVendorInfoApply(String businessNo) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	
	public Vendor selectVendorInfoApply(String businessNo) {
		// TODO Auto-generated method stub 
		Vendor tmpVendor = consultDao.selectVendorInfoApply(businessNo);
		return tmpVendor == null ? new Vendor() : tmpVendor ;
	}
	
	
	
	@Override
	public void insertNewVendorInfoApply(Vendor submittedVendorInfo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateVendorProductInfoApply(Vendor vendor,
			List<VendorProduct> vendorProductList) {
		// TODO Auto-generated method stub
		
		consultDao.updateVendorProductContentApply(vendor);
		
		consultDao.updateVendorProductListApply(vendorProductList);
		
		
	}
	
	@Override
	public void updateVendorProductInfoApplySupport(Vendor vendor,
			List<VendorProduct> vendorProductList) {
		// TODO Auto-generated method stub
		
		consultDao.updateVendorProductContentApplySupport(vendor);		
	}
	

	
	

	@Override
	public void updateVendorStatusApply(Vendor vendor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void insertNewVendorKindCd(HashMap<String, Object> hmap) {
		consultDao.insertNewVendorInfoApply2(hmap);	
	}

	@Override
	public void updateNewVendorKindCd(HashMap<String, Object> hmap) {
		consultDao.updateNewVendorAnswer(hmap);	
		
	}

	@Override
	public void deleteNewVendorAnswer(HashMap<String, Object> hmap) {
		consultDao.deleteNewVendorAnswer(hmap);
	
	}
	

}
