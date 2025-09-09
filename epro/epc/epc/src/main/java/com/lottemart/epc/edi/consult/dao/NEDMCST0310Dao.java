package com.lottemart.epc.edi.consult.dao;

import java.util.HashMap;
import java.util.List;

import lcn.module.common.util.HashBox;
import lcn.module.framework.base.AbstractDAO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.lottemart.epc.edi.comm.model.Constants;
import com.lottemart.epc.edi.comm.model.EdiCommonCode;
import com.lottemart.epc.edi.comm.model.SearchParam;
import com.lottemart.epc.edi.consult.model.AutionItem;
import com.lottemart.epc.edi.consult.model.NEDMCST0310VO;
import com.lottemart.epc.edi.consult.model.Sale;
import com.lottemart.epc.edi.consult.model.Vendor;
import com.lottemart.epc.edi.consult.model.VendorProduct;


@Repository("nEDMCST0310Dao")
public class NEDMCST0310Dao extends AbstractDAO{
	private static final Logger logger = LoggerFactory.getLogger(NEDMCST0310Dao.class);


	public Vendor selectVendorInfo(String businessNo) throws DataAccessException {
		return (Vendor) getSqlMapClientTemplate().queryForObject("NEDMCST0310.selectVendorInfo", businessNo);
	}


	public Vendor selectVendorInfoResult(String businessNo) throws DataAccessException {
		return (Vendor) getSqlMapClientTemplate().queryForObject("NEDMCST0310.selectVendorInfoResult", businessNo);
	}


	public Vendor selectVendorInfo2(String businessNo) throws DataAccessException {
		return (Vendor) getSqlMapClientTemplate().queryForObject("NEDMCST0310.selectVendorInfo2", businessNo);
	}
	public Vendor selectVendorInfoApply(String businessNo) throws DataAccessException {
		return (Vendor) getSqlMapClientTemplate().queryForObject("NEDMCST0310.selectVendorInfoApply", businessNo);
	}

	public Vendor selectVendorInfoApply2(String businessNo) throws DataAccessException {
		return (Vendor) getSqlMapClientTemplate().queryForObject("NEDMCST0310.selectVendorInfoApply2", businessNo);
	}



	public Integer selectVendorPassword(SearchParam searchParam) throws DataAccessException {
		return (Integer) getSqlMapClientTemplate().queryForObject("NEDMCST0310.getVendorPassword", searchParam);
	}


	public Integer updateVendorPassword(SearchParam searchParam) throws DataAccessException {
		return getSqlMapClientTemplate().update("NEDMCST0310.updateVendorPassword", searchParam);
	}


	@SuppressWarnings("unchecked")
	public List<Sale> selectSaleInfoByVendor(String businessNo) throws DataAccessException {
		return (List<Sale>)getSqlMapClientTemplate().queryForList("NEDMCST0310.getVendorSaleInfo", businessNo);
	}


	@SuppressWarnings("unchecked")
	public List<VendorProduct> selectVendorProduct(String businessNo) throws DataAccessException {
		return (List<VendorProduct>)getSqlMapClientTemplate().queryForList("NEDMCST0310.getVendorProductInfo", businessNo);
	}


	@SuppressWarnings("unchecked")
	public List<Vendor> selectVendorAnswer(String businessNo) throws DataAccessException {
		return (List<Vendor>)getSqlMapClientTemplate().queryForList("NEDMCST0310.selectVendorAnswer", businessNo);
	}


	@SuppressWarnings("unchecked")
	public List<AutionItem> selectAuItem(AutionItem  autionitem) throws DataAccessException {
		return (List<AutionItem>)getSqlMapClientTemplate().queryForList("NEDMCST0310.getVendorAutionItemInfo",autionitem);
	}
	@SuppressWarnings("unchecked")
	public List<AutionItem> selectAuItemSum(String businessNo) throws DataAccessException {
		return (List<AutionItem>)getSqlMapClientTemplate().queryForList("NEDMCST0310.getVendorAutionItemInfoSum", businessNo);
	}




//	@SuppressWarnings("unchecked")
//	public List<AutionItem> selectAuItem(String businessNo)
//			throws DataAccessException {
//
//		return (List<AutionItem>)getSqlMapClientTemplate().queryForList("NEDMCST0310.getVendorAutionItemInfo", businessNo);
//	}


	public void insertNewVendorInfo(Vendor submittedVendorInfo) throws DataAccessException {
		getSqlMapClientTemplate().update("NEDMCST0310.insertNewVendorInfo", submittedVendorInfo);
	}

	public void insertNewVendorInfoApply(Vendor submittedVendorInfo) throws DataAccessException {
		getSqlMapClientTemplate().update("NEDMCST0310.insertNewVendorInfoApply", submittedVendorInfo);
	}


	public void updateVendorInfo(Vendor submittedVendorInfo) throws DataAccessException {
		getSqlMapClientTemplate().update("NEDMCST0310.updateVendorInfo", submittedVendorInfo);
	}

	public void updateVendorInfoApply(Vendor submittedVendorInfo) throws DataAccessException {
		getSqlMapClientTemplate().update("NEDMCST0310.updateVendorInfoApply", submittedVendorInfo);
	}


	public void updateVendorSaleInfo(Vendor vendor) {
		getSqlMapClientTemplate().update("NEDMCST0310.updateVendorSaleInfo", vendor);
	}


	public void updateOtherStoreVendorSaleInfo(List<Sale> saleDataList) {

		for(Sale inputSaleObj : saleDataList) {
			Integer saleInfoCount = (Integer) getSqlMapClientTemplate().queryForObject("NEDMCST0310.getVendorOtherStoreSaleInfoCount", inputSaleObj);
			Integer insertUpdateFlag = saleInfoCount == null? 0 : saleInfoCount;
			if( insertUpdateFlag == 0 ) {
				getSqlMapClientTemplate().update("NEDMCST0310.insertOtherStoreVendorSaleInfo", inputSaleObj);
			} else {
				getSqlMapClientTemplate().update("NEDMCST0310.updateOtherStoreVendorSaleInfo", inputSaleObj);
			}
		}
	}


	public void updateVendorProductContent(Vendor vendor) {
		getSqlMapClientTemplate().update("NEDMCST0310.updateVendorProductContent", vendor);
	}

	public void updateVendorProductContentApply(Vendor vendor) {
		getSqlMapClientTemplate().update("NEDMCST0310.updateVendorProductContentApply", vendor);
	}

	public void updateVendorProductContentApplySupport(Vendor vendor) {
		getSqlMapClientTemplate().update("NEDMCST0310.updateVendorProductContentApplySupport", vendor);
	}


	public void updateVendorProductList(List<VendorProduct> vendorProductList) {

		for(VendorProduct vendorProduct : vendorProductList) {
			Integer vendorProductInfoCount = (Integer) getSqlMapClientTemplate().queryForObject("NEDMCST0310.getVendorProductInfoCount", vendorProduct);
			Integer insertUpdateFlag = vendorProductInfoCount == null? 0 : vendorProductInfoCount;
			if( insertUpdateFlag == 0 ) {
				getSqlMapClientTemplate().update("NEDMCST0310.insertVendorProductInfo", vendorProduct);
			} else {
				getSqlMapClientTemplate().update("NEDMCST0310.updateVendorProductInfo", vendorProduct);
			}
		}
	}

	public void updateVendorProductListApply(List<VendorProduct> vendorProductList) {

		for(VendorProduct vendorProduct : vendorProductList) {
			Integer vendorProductInfoCount = (Integer) getSqlMapClientTemplate().queryForObject("NEDMCST0310.getVendorProductInfoCount", vendorProduct);
			Integer insertUpdateFlag = vendorProductInfoCount == null? 0 : vendorProductInfoCount;

			if( insertUpdateFlag == 0 ) {
				getSqlMapClientTemplate().update("NEDMCST0310.insertVendorProductInfoApply", vendorProduct);
			} else {
				getSqlMapClientTemplate().update("NEDMCST0310.updateVendorProductInfoApply", vendorProduct);
			}
		}
	}


	public void updateVendorStatus(Vendor vendor) {

		logger.debug("leedbgetChgStatusCd:"+vendor.getChgStatusCd());
		if(Constants.CONSULT_VENDOR_STATUS_NEW.equals(vendor.getChgStatusCd())) {
			getSqlMapClientTemplate().update("NEDMCST0310.updateVendorInfoFinalStep", vendor);
		}
		if(Constants.CONSULT_VENDOR_STATUS_REASSIGN.equals(vendor.getChgStatusCd())) {
			getSqlMapClientTemplate().update("NEDMCST0310.updateVendorInfoFinalStep", vendor);
		}
		if(Constants.CONSULT_VENDOR_STATUS_REJECT.equals(vendor.getChgStatusCd())) {
			getSqlMapClientTemplate().update("NEDMCST0310.updateVendorInfoConsultResult", vendor);
		}
		if(vendor.getChgStatusCd()==null) {
			getSqlMapClientTemplate().update("NEDMCST0310.updateVendorInfoConsultResult", vendor);
		}

	}



	public void updateVendorStatusApply(Vendor vendor) {

		logger.debug("leedbgetChgStatusCd:"+vendor.getChgStatusCd());
		if(Constants.CONSULT_VENDOR_STATUS_NEW.equals(vendor.getChgStatusCd())) {
			getSqlMapClientTemplate().update("NEDMCST0310.updateVendorInfoFinalStepApply", vendor);
		}
		if(Constants.CONSULT_VENDOR_STATUS_REASSIGN.equals(vendor.getChgStatusCd())) {
			getSqlMapClientTemplate().update("NEDMCST0310.updateVendorInfoFinalStepApply", vendor);
		}
		if(Constants.CONSULT_VENDOR_STATUS_REJECT.equals(vendor.getChgStatusCd())) {
			getSqlMapClientTemplate().update("NEDMCST0310.updateVendorInfoConsultResultApply", vendor);
		}
		if(vendor.getChgStatusCd()==null) {
			getSqlMapClientTemplate().update("NEDMCST0310.updateVendorInfoConsultResultApply", vendor);
		}

	}


	public NEDMCST0310VO selectResultInfo(String businessNo) throws Exception{
		return (NEDMCST0310VO)getSqlMapClientTemplate().queryForObject("NEDMCST0310.getResultInfo", businessNo);
	}

	public HashBox processCk(String businessNo) throws Exception{
		return (HashBox)getSqlMapClientTemplate().queryForObject("NEDMCST0310.processCk", businessNo);
	}

	public void deleteAuctionProdItem(SearchParam searchParam) throws DataAccessException {
		getSqlMapClientTemplate().delete("NEDMCST0310.deleteAuctionProdItem", searchParam);
	}


	public void insertAuctionProdItem(SearchParam searchParam) throws DataAccessException {
		getSqlMapClientTemplate().insert("NEDMCST0310.insertAuctionProdItem", searchParam);
	}



	public void insertNewVendorInfoNew(SearchParam searchParam) throws DataAccessException {
		getSqlMapClientTemplate().insert("NEDMCST0310.insertNewVendorInfoNew", searchParam);
	}

	public void updateVendorInfoNew(SearchParam searchParam) throws DataAccessException {
		getSqlMapClientTemplate().update("NEDMCST0310.updateVendorInfoNew", searchParam);
	}

	 public void updateNewVendorInfoApply(HashMap<String, Object> hmap) {
		 getSqlMapClientTemplate().update("NEDMCST0310.updateNewVendorInfoNew", hmap);
	 }


	public void insertNewVendorInfoApply(NEDMCST0310VO nEDMCST0310VO) {
		getSqlMapClientTemplate().insert("NEDMCST0310.insertNewVendorInfoApply", nEDMCST0310VO);
	}


	public void insertNewVendorInfoApply2(NEDMCST0310VO nEDMCST0310VO) {
		getSqlMapClientTemplate().insert("NEDMCST0310.insertNewVendorInfoNew2", nEDMCST0310VO);
	}

	public void updateNewVendorAnswer(NEDMCST0310VO nEDMCST0310VO) {
		getSqlMapClientTemplate().insert("NEDMCST0310.updateNewVendorAnswer", nEDMCST0310VO);
	}


	public Vendor selectNewVendorInfoApply(String businessNo) {
		return (Vendor) getSqlMapClientTemplate().queryForObject("NEDMCST0310.selectNewVendorInfoApply", businessNo);
	}


	public void deleteNewVendorAnswer(NEDMCST0310VO nEDMCST0310VO) {
		getSqlMapClientTemplate().delete("NEDMCST0310.deleteNewVendorAnswer", nEDMCST0310VO);
	}

	public List consultAdminSelectDetail(String str) throws Exception{
		return (List<NEDMCST0310VO>)getSqlMapClientTemplate().queryForList("NEDMCST0310.TSC_CONSULT_ADMIN-SELECT02",str);
	}

	public List consultAdminSelectDetailPast(String str) throws Exception{
		return (List<NEDMCST0310VO>)getSqlMapClientTemplate().queryForList("NEDMCST0310.TSC_CONSULT_ADMIN_PAST-SELECT01",str);
	}

	public List<NEDMCST0310VO> selectTeamCdListApply(NEDMCST0310VO nEDMCST0310VO) {
		return getSqlMapClientTemplate().queryForList("NEDMCST0310.getTeamListApply", nEDMCST0310VO);
	}

	/**
	 * 입점상담신청 상품카테고리의 상담신청서 작성화면에서 희망부서 선택시 해당부서의 팀 조회
	 * @param nEDMCST0310VO
	 * @return
	 */
	public List<EdiCommonCode> selectL1ListApply(NEDMCST0310VO nEDMCST0310VO) {
		return getSqlMapClientTemplate().queryForList("NEDMCST0310.getL1ListApply", nEDMCST0310VO);
	}

	public void updateKindProductInfoApply(Vendor submittedVendorInfo) throws DataAccessException {
		getSqlMapClientTemplate().update("NEDMCST0310.updateKindProductInfoApply", submittedVendorInfo);
	}


	public Integer updateAtchFileKindCd(Vendor vendor) throws DataAccessException {
		return getSqlMapClientTemplate().update("NEDMCST0310.updateAtchFileKindCd", vendor);
	}

	public Integer updateAttachFileCode(Vendor vendor) throws DataAccessException {
		return getSqlMapClientTemplate().update("NEDMCST0310.updateAttachFileCode", vendor);
	}

	public Integer NEDMCST0310deleteImg(Vendor vendor) throws DataAccessException {
		return getSqlMapClientTemplate().update("NEDMCST0310.NEDMCST0310deleteImg", vendor);
	}
}
