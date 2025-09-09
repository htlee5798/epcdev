package com.lottemart.epc.edi.consult.dao;

import java.util.HashMap;
import java.util.List;

import lcn.module.common.util.HashBox;
import lcn.module.framework.base.AbstractDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.lottemart.epc.edi.comm.model.Constants;
import com.lottemart.epc.edi.consult.model.Sale;
import com.lottemart.epc.edi.comm.model.SearchParam;
import com.lottemart.epc.edi.consult.model.Vendor;
import com.lottemart.epc.edi.consult.model.VendorProduct;
import com.lottemart.epc.edi.consult.model.AutionItem;
import com.lottemart.epc.edi.consult.service.PEDMSCT0002Service;


@Repository
public class PEDMSCT051Dao extends AbstractDAO{


	public Vendor selectVendorInfo(String businessNo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return (Vendor) getSqlMapClientTemplate().queryForObject("Consult.selectVendorInfo", businessNo);
	}


	public Vendor selectVendorInfoResult(String businessNo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return (Vendor) getSqlMapClientTemplate().queryForObject("Consult.selectVendorInfoResult", businessNo);
	}


	public Vendor selectVendorInfo2(String businessNo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return (Vendor) getSqlMapClientTemplate().queryForObject("Consult.selectVendorInfo2", businessNo);
	}
	public Vendor selectVendorInfoApply(String businessNo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return (Vendor) getSqlMapClientTemplate().queryForObject("Consult.selectVendorInfoApply", businessNo);
	}

	public Vendor selectVendorInfoApply2(String businessNo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return (Vendor) getSqlMapClientTemplate().queryForObject("Consult.selectVendorInfoApply2", businessNo);
	}



	public Integer selectVendorPassword(SearchParam searchParam)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return (Integer) getSqlMapClientTemplate().queryForObject("Consult.getVendorPassword", searchParam);
	}


	public Integer updateVendorPassword(SearchParam searchParam)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().update("Consult.updateVendorPassword", searchParam);
	}


	@SuppressWarnings("unchecked")
	public List<Sale> selectSaleInfoByVendor(String businessNo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return (List<Sale>)getSqlMapClientTemplate().queryForList("Consult.getVendorSaleInfo", businessNo);
	}


	@SuppressWarnings("unchecked")
	public List<VendorProduct> selectVendorProduct(String businessNo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return (List<VendorProduct>)getSqlMapClientTemplate().queryForList("Consult.getVendorProductInfo", businessNo);
	}


	@SuppressWarnings("unchecked")
	public List<Vendor> selectVendorAnswer(String businessNo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return (List<Vendor>)getSqlMapClientTemplate().queryForList("Consult.selectVendorAnswer", businessNo);
	}


	@SuppressWarnings("unchecked")
	public List<AutionItem> selectAuItem(AutionItem  autionitem)
			throws DataAccessException {
		// TODO Auto-generated method stub


		return (List<AutionItem>)getSqlMapClientTemplate().queryForList("Consult.getVendorAutionItemInfo",autionitem);
	}
	@SuppressWarnings("unchecked")
	public List<AutionItem> selectAuItemSum(String businessNo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return (List<AutionItem>)getSqlMapClientTemplate().queryForList("Consult.getVendorAutionItemInfoSum", businessNo);
	}




//	@SuppressWarnings("unchecked")
//	public List<AutionItem> selectAuItem(String businessNo)
//			throws DataAccessException {
//		// TODO Auto-generated method stub
//		return (List<AutionItem>)getSqlMapClientTemplate().queryForList("Consult.getVendorAutionItemInfo", businessNo);
//	}


	public void insertNewVendorInfo(Vendor submittedVendorInfo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		getSqlMapClientTemplate().update("Consult.insertNewVendorInfo", submittedVendorInfo);
	}
	public void insertNewVendorInfoApply(Vendor submittedVendorInfo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		getSqlMapClientTemplate().update("Consult.insertNewVendorInfoApply", submittedVendorInfo);
	}


	public void updateVendorInfo(Vendor submittedVendorInfo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		getSqlMapClientTemplate().update("Consult.updateVendorInfo", submittedVendorInfo);
	}

	public void updateVendorInfoApply(Vendor submittedVendorInfo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		getSqlMapClientTemplate().update("Consult.updateVendorInfoApply", submittedVendorInfo);
	}



	public void updateVendorSaleInfo(Vendor vendor) {
		// TODO Auto-generated method stub
		getSqlMapClientTemplate().update("Consult.updateVendorSaleInfo", vendor);
	}


	public void updateOtherStoreVendorSaleInfo(List<Sale> saleDataList) {
		// TODO Auto-generated method stub
		for(Sale inputSaleObj : saleDataList) {
			Integer saleInfoCount = (Integer) getSqlMapClientTemplate()
				.queryForObject("Consult.getVendorOtherStoreSaleInfoCount", inputSaleObj);
			Integer insertUpdateFlag = saleInfoCount == null? 0 : saleInfoCount;
			if( insertUpdateFlag == 0 ) {
				getSqlMapClientTemplate().update("Consult.insertOtherStoreVendorSaleInfo", inputSaleObj);
			} else {
				getSqlMapClientTemplate().update("Consult.updateOtherStoreVendorSaleInfo", inputSaleObj);
			}
		}
	}


	public void updateVendorProductContent(Vendor vendor) {
		// TODO Auto-generated method stub
		getSqlMapClientTemplate().update("Consult.updateVendorProductContent", vendor);
	}

	public void updateVendorProductContentApply(Vendor vendor) {
		// TODO Auto-generated method stub
		getSqlMapClientTemplate().update("Consult.updateVendorProductContentApply", vendor);
	}

	public void updateVendorProductContentApplySupport(Vendor vendor) {
		// TODO Auto-generated method stub
		getSqlMapClientTemplate().update("Consult.updateVendorProductContentApplySupport", vendor);
	}


	public void updateVendorProductList(List<VendorProduct> vendorProductList) {
		// TODO Auto-generated method stub
		for(VendorProduct vendorProduct : vendorProductList) {
			Integer vendorProductInfoCount = (Integer) getSqlMapClientTemplate()
				.queryForObject("Consult.getVendorProductInfoCount", vendorProduct);
			Integer insertUpdateFlag = vendorProductInfoCount == null? 0 : vendorProductInfoCount;
			if( insertUpdateFlag == 0 ) {
				getSqlMapClientTemplate().update("Consult.insertVendorProductInfo", vendorProduct);
			} else {
				getSqlMapClientTemplate().update("Consult.updateVendorProductInfo", vendorProduct);
			}
		}
	}

	public void updateVendorProductListApply(List<VendorProduct> vendorProductList) {
		// TODO Auto-generated method stub
		for(VendorProduct vendorProduct : vendorProductList) {
			Integer vendorProductInfoCount = (Integer) getSqlMapClientTemplate()
				.queryForObject("Consult.getVendorProductInfoCount", vendorProduct);
			Integer insertUpdateFlag = vendorProductInfoCount == null? 0 : vendorProductInfoCount;
			if( insertUpdateFlag == 0 ) {
				getSqlMapClientTemplate().update("Consult.insertVendorProductInfoApply", vendorProduct);
			} else {
				getSqlMapClientTemplate().update("Consult.updateVendorProductInfoApply", vendorProduct);
			}
		}
	}


	public void updateVendorStatus(Vendor vendor) {
		// TODO Auto-generated method stub
//		System.out.println("leedbgetChgStatusCd:"+vendor.getChgStatusCd());
		if(Constants.CONSULT_VENDOR_STATUS_NEW.equals(vendor.getChgStatusCd())) {
			getSqlMapClientTemplate().update("Consult.updateVendorInfoFinalStep", vendor);
		}
		if(Constants.CONSULT_VENDOR_STATUS_REASSIGN.equals(vendor.getChgStatusCd())) {
			getSqlMapClientTemplate().update("Consult.updateVendorInfoFinalStep", vendor);
		}
		if(Constants.CONSULT_VENDOR_STATUS_REJECT.equals(vendor.getChgStatusCd())) {
			getSqlMapClientTemplate().update("Consult.updateVendorInfoConsultResult", vendor);
		}
		if(vendor.getChgStatusCd()==null) {
			getSqlMapClientTemplate().update("Consult.updateVendorInfoConsultResult", vendor);
		}

	}



	public void updateVendorStatusApply(Vendor vendor) {
		// TODO Auto-generated method stub
//		System.out.println("leedbgetChgStatusCd:"+vendor.getChgStatusCd());
		if(Constants.CONSULT_VENDOR_STATUS_NEW.equals(vendor.getChgStatusCd())) {
			getSqlMapClientTemplate().update("Consult.updateVendorInfoFinalStepApply", vendor);
		}
		if(Constants.CONSULT_VENDOR_STATUS_REASSIGN.equals(vendor.getChgStatusCd())) {
			getSqlMapClientTemplate().update("Consult.updateVendorInfoFinalStepApply", vendor);
		}
		if(Constants.CONSULT_VENDOR_STATUS_REJECT.equals(vendor.getChgStatusCd())) {
			getSqlMapClientTemplate().update("Consult.updateVendorInfoConsultResultApply", vendor);
		}
		if(vendor.getChgStatusCd()==null) {
			getSqlMapClientTemplate().update("Consult.updateVendorInfoConsultResultApply", vendor);
		}

	}


	public HashBox selectResultInfo(String businessNo) throws Exception{
		return (HashBox)getSqlMapClientTemplate().queryForObject("Consult.getResultInfo", businessNo);
	}

	public HashBox processCk(String businessNo) throws Exception{
		return (HashBox)getSqlMapClientTemplate().queryForObject("Consult.processCk", businessNo);
	}

	public void deleteAuctionProdItem(SearchParam searchParam) throws DataAccessException {
		getSqlMapClientTemplate().delete("Consult.deleteAuctionProdItem", searchParam);
	}


	public void insertAuctionProdItem(SearchParam searchParam) throws DataAccessException {
		getSqlMapClientTemplate().insert("Consult.insertAuctionProdItem", searchParam);
	}



	public void insertNewVendorInfoNew(SearchParam searchParam) throws DataAccessException {
		getSqlMapClientTemplate().insert("Consult.insertNewVendorInfoNew", searchParam);
	}

	public void updateVendorInfoNew(SearchParam searchParam) throws DataAccessException {
		getSqlMapClientTemplate().update("Consult.updateVendorInfoNew", searchParam);


	}

	 public void updateNewVendorInfoApply(HashMap<String, Object> hmap) {
		 getSqlMapClientTemplate().update("Consult.updateNewVendorInfoNew", hmap);

	 }


	public void insertNewVendorInfoApply(HashMap<String, Object> hmap) {
		getSqlMapClientTemplate().insert("Consult.insertNewVendorInfoApply", hmap);
	}


	public void insertNewVendorInfoApply2(HashMap<String, Object> hmap) {
		getSqlMapClientTemplate().insert("Consult.insertNewVendorInfoNew2", hmap);

	}

	public void updateNewVendorAnswer(HashMap<String, Object> hmap) {
		getSqlMapClientTemplate().insert("Consult.updateNewVendorAnswer", hmap);

	}


	public Vendor selectNewVendorInfoApply(String businessNo) {
		return (Vendor) getSqlMapClientTemplate().queryForObject("Consult.selectNewVendorInfoApply", businessNo);
	}


	public void deleteNewVendorAnswer(HashMap<String, Object> hmap) {
		// TODO Auto-generated method stub
		getSqlMapClientTemplate().delete("Consult.deleteNewVendorAnswer", hmap);

	}
}
