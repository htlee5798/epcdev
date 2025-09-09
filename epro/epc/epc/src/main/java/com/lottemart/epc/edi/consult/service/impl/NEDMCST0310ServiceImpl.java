
package com.lottemart.epc.edi.consult.service.impl;

import java.util.HashMap;
import java.util.List;

import lcn.module.common.util.HashBox;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.epc.edi.comm.model.EdiCommonCode;
import com.lottemart.epc.edi.comm.model.SearchParam;
import com.lottemart.epc.edi.consult.dao.NEDMCST0310Dao;
import com.lottemart.epc.edi.consult.model.AutionItem;
import com.lottemart.epc.edi.consult.model.NEDMCST0310VO;
import com.lottemart.epc.edi.consult.model.Sale;
import com.lottemart.epc.edi.consult.model.Vendor;
import com.lottemart.epc.edi.consult.model.VendorProduct;
import com.lottemart.epc.edi.consult.service.NEDMCST0310Service;

@Service("nEDMCST0310Service")
public class NEDMCST0310ServiceImpl implements NEDMCST0310Service {

	@Autowired
	private NEDMCST0310Dao nEDMCST0310Dao;



	public Vendor selectVendorInfo(String businessNo) {

		Vendor tmpVendor = nEDMCST0310Dao.selectVendorInfo(businessNo);
		return tmpVendor == null ? new Vendor() : tmpVendor ;
	}
	public Vendor selectVendorInfoResult(String businessNo) {

		Vendor tmpVendor = nEDMCST0310Dao.selectVendorInfoResult(businessNo);
		return tmpVendor == null ? new Vendor() : tmpVendor ;
	}

	public Vendor selectVendorInfo2(String businessNo) {

		Vendor tmpVendor = nEDMCST0310Dao.selectVendorInfo2(businessNo);
		return tmpVendor == null ? new Vendor() : tmpVendor ;
	}

	public Integer selectVendorPassword(SearchParam searchParam) {

		Integer resultCount = nEDMCST0310Dao.selectVendorPassword(searchParam);

		return resultCount == null ? 0 : resultCount;
	}


	public Integer updateVendorPassword(SearchParam searchParam) {

		return nEDMCST0310Dao.updateVendorPassword(searchParam);
	}


	public List<Sale> selectSaleInfoByVendor(String businessNo) {

		return nEDMCST0310Dao.selectSaleInfoByVendor(businessNo) ;
	}


	public List<VendorProduct> selectVendorProduct(String businessNo) {

		return nEDMCST0310Dao.selectVendorProduct(businessNo);
	}

	public List<Vendor> selectVendorAnswer(String businessNo) {

		return nEDMCST0310Dao.selectVendorAnswer(businessNo);
	}


	public List<AutionItem> selectAuItem(String businessNo,String L1_CD) {

		AutionItem  autionitem  = new AutionItem();
		//searchParam.setBusinessNo(businessNo);
		//searchParam.setProdArraySeq(prodSeq);
		autionitem.setBmanNo(businessNo);
		autionitem.setL1Cd(L1_CD);

		return nEDMCST0310Dao.selectAuItem(autionitem);
	}
	public List<AutionItem> selectAuItemSum(String businessNo) {

		return nEDMCST0310Dao.selectAuItemSum(businessNo);
	}



	//public List<AutionItem> selectAuItem(String businessNo) {
	//
	//	return nEDMCST0310Dao.selectAuItem(businessNo);
	//}


	public void insertNewVendorInfo(Vendor submittedVendorInfo) {

		nEDMCST0310Dao.insertNewVendorInfo(submittedVendorInfo);
	}


	public void updateVendorInfo(Vendor submittedVendorInfo) {

		nEDMCST0310Dao.updateVendorInfo(submittedVendorInfo);
	}

	public void updateVendorInfoApply(Vendor submittedVendorInfo) {

		nEDMCST0310Dao.updateVendorInfoApply(submittedVendorInfo);
	}


	//public void updateVendorSaleInfo(Vendor vendor, List<Sale> saleDataList) {
	public void updateVendorSaleInfo(Vendor vendor) {

		nEDMCST0310Dao.updateVendorSaleInfo(vendor);

		//nEDMCST0310Dao.updateOtherStoreVendorSaleInfo(saleDataList);
	}


	public void updateVendorProductInfo(Vendor vendor,
			List<VendorProduct> vendorProductList) {

		nEDMCST0310Dao.updateVendorProductContent(vendor);

		nEDMCST0310Dao.updateVendorProductList(vendorProductList);
	}


	public void updateVendorStatus(Vendor vendor) {
		// TODO Auto-generated method s	tub
		nEDMCST0310Dao.updateVendorStatus(vendor);
	}

	public NEDMCST0310VO selectResultInfo(String businessNo) throws Exception {

		return nEDMCST0310Dao.selectResultInfo(businessNo);
	}

	public HashBox processCk(String businessNo) throws Exception {

		return nEDMCST0310Dao.processCk(businessNo);
	}

	public void deleteAuctionProdItem( String businessNo){
		SearchParam searchParam = new SearchParam();

		searchParam.setBusinessNo(businessNo);


		nEDMCST0310Dao.deleteAuctionProdItem(searchParam);
	}

	public void insertAuctionProdItem(String prodSeq , String businessNo){
		SearchParam searchParam = new SearchParam();

		searchParam.setBusinessNo(businessNo);
		searchParam.setProdArraySeq(prodSeq);

		nEDMCST0310Dao.insertAuctionProdItem(searchParam);
	}


	public void updateVendorInfoNew(String pwd , String businessNo, String teamCd){
		SearchParam searchParam = new SearchParam();
		searchParam.setBusinessNo(businessNo);
		//searchParam.setL1Code(l1Cd);
		//searchParam.setL1Code(teamCd);
		searchParam.setTeamCode(teamCd);
		searchParam.setPassword(pwd);
		nEDMCST0310Dao.updateVendorInfoNew(searchParam);
	}


	public void insertNewVendorInfoNew(String pwd , String businessNo, String teamCd){
		SearchParam searchParam = new SearchParam();
		searchParam.setBusinessNo(businessNo);
		//searchParam.setL1Code(l1Cd);
		searchParam.setTeamCode(teamCd);
		searchParam.setPassword(pwd);
		nEDMCST0310Dao.insertNewVendorInfoNew(searchParam);
	}

	//TED_VENDOR_EDI 포함  Insert 로직 (기존 업체가 아닐경우 )
	@Override
	public void insertNewVendorAnswer(NEDMCST0310VO nEDMCST0310VO) {

		String temp = nEDMCST0310VO.getColVal();
		String seq  = nEDMCST0310VO.getSeq();

		String[] colVal = temp.split(";");
		for (int i = 0; i < colVal.length; i++) {
			nEDMCST0310VO.setSeq(seq + i);
			nEDMCST0310VO.setColVal(colVal[i]);
			nEDMCST0310Dao.insertNewVendorInfoApply(nEDMCST0310VO);
		}
		nEDMCST0310Dao.insertNewVendorInfoApply2(nEDMCST0310VO);
	}

	//TED_VENDOR_EDI 제외 Insert 로직 (기존 업체가 존재할 시에 Insert에러방지)
	@Override
	public void insertNewVendorAnswer2(NEDMCST0310VO nEDMCST0310VO) {

		String temp = nEDMCST0310VO.getColVal();
		String seq  = nEDMCST0310VO.getSeq();

		String[] colVal = temp.split(";");
		for (int i = 0; i < colVal.length; i++) {
			nEDMCST0310VO.setSeq(seq + i);
			nEDMCST0310VO.setColVal(colVal[i]);

			nEDMCST0310Dao.insertNewVendorInfoApply(nEDMCST0310VO);

		}
	}

	@Override
	public Vendor selectNewVendorInfoApply(String bmanNo) {

		return nEDMCST0310Dao.selectNewVendorInfoApply(bmanNo);

	}


	@Override
	public void updateNewVendorInfoApply(HashMap<String, Object> hmap) {


		String temp = (String)hmap.get("colVal");
		String seq  = (String)hmap.get("seq");
		String[] colVal = temp.split(";");
		for (int i = 0; i < colVal.length; i++) {
			hmap.put("seq",seq + i);
			hmap.put("colVal", colVal[i]);

			nEDMCST0310Dao.updateNewVendorInfoApply(hmap);
    	}
	//	nEDMCST0310Dao.updateNewVendorAnswer(hmap);

	}

//	@Override
//	public Vendor selectVendorInfoApply(String businessNo) {
//
//		return null;
//	}


	public Vendor selectVendorInfoApply(String businessNo) {

		Vendor tmpVendor = nEDMCST0310Dao.selectVendorInfoApply(businessNo);
		return tmpVendor == null ? new Vendor() : tmpVendor ;
	}



	@Override
	public void insertNewVendorInfoApply(Vendor submittedVendorInfo) {
		// TODO Auto-generated method stub
	}

	@Override
	public void updateVendorProductInfoApply(Vendor vendor,	List<VendorProduct> vendorProductList) {


		nEDMCST0310Dao.updateVendorProductContentApply(vendor);

		nEDMCST0310Dao.updateVendorProductListApply(vendorProductList);


	}

	@Override
	public void updateVendorProductInfoApplySupport(Vendor vendor,
			List<VendorProduct> vendorProductList) {


		nEDMCST0310Dao.updateVendorProductContentApplySupport(vendor);
	}





	@Override
	public void updateVendorStatusApply(Vendor vendor) {
		// TODO Auto-generated method stub
	}

	@Override
	public void insertNewVendorKindCd(NEDMCST0310VO nEDMCST0310VO) {
		nEDMCST0310Dao.insertNewVendorInfoApply2(nEDMCST0310VO);
	}

	@Override
	public void updateNewVendorKindCd(NEDMCST0310VO nEDMCST0310VO) {
		nEDMCST0310Dao.updateNewVendorAnswer(nEDMCST0310VO);

	}

	@Override
	public void deleteNewVendorAnswer(NEDMCST0310VO nEDMCST0310VO) {
		nEDMCST0310Dao.deleteNewVendorAnswer(nEDMCST0310VO);

	}

	public List consultAdminSelectDetail(String str) throws Exception{
		return nEDMCST0310Dao.consultAdminSelectDetail(str );
	}

	public List consultAdminSelectDetailPast(String str) throws Exception{
		return nEDMCST0310Dao.consultAdminSelectDetailPast(str );
	}
	public List<NEDMCST0310VO> selectTeamCdListApply(NEDMCST0310VO nEDMCST0310VO) {
		return nEDMCST0310Dao.selectTeamCdListApply(nEDMCST0310VO);
	}

	/**
	 * 입점상담신청 상품카테고리의 상담신청서 작성화면에서 희망부서 선택시 해당부서의 팀 조회
	 * @param nEDMCST0310VO
	 * @return
	 */
	public List<EdiCommonCode> selectL1ListApply(NEDMCST0310VO nEDMCST0310VO) {
		return nEDMCST0310Dao.selectL1ListApply(nEDMCST0310VO);
	}

	@Override
	public void updateKindProductInfoApply(Vendor submittedVendorInfo)	throws Exception {
		nEDMCST0310Dao.updateKindProductInfoApply(submittedVendorInfo);
	}

	@Override
	public int updateAtchFileKindCd(Vendor vendor) {
		Integer resultCount = nEDMCST0310Dao.updateAtchFileKindCd(vendor);
		return resultCount == null ? 0 : resultCount;

	}


	@Override
	public int updateAttachFileCode(Vendor vendor) {
		Integer resultCount = nEDMCST0310Dao.updateAttachFileCode(vendor);
		return resultCount == null ? 0 : resultCount;

	}

	@Override
	public int NEDMCST0310deleteImg(Vendor vendor) {
		Integer resultCount = nEDMCST0310Dao.NEDMCST0310deleteImg(vendor);
		return resultCount == null ? 0 : resultCount;

	}


}
