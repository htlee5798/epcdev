package com.lottemart.epc.edi.consult.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lcn.module.common.util.HashBox;

import com.lottemart.epc.edi.consult.model.Sale;
import com.lottemart.epc.edi.comm.model.EdiCommonCode;
import com.lottemart.epc.edi.comm.model.SearchParam;
import com.lottemart.epc.edi.consult.model.NEDMCST0310VO;
import com.lottemart.epc.edi.consult.model.Vendor;
import com.lottemart.epc.edi.consult.model.VendorProduct;
import com.lottemart.epc.edi.consult.model.AutionItem;


public interface NEDMCST0310Service {

	/**
	 * 협력사 정보??
	 * @param businessNo
	 * @return
	 */
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
	
	void insertNewVendorAnswer(NEDMCST0310VO nEDMCST0310VO); //20150715 입점개편 이동빈
	
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
	
	/**
	 * 신청 결과 정보??
	 * @param businessNo
	 * @return
	 * @throws Exception
	 */
	public NEDMCST0310VO selectResultInfo(String businessNo) throws Exception;
	
	public HashBox processCk(String businessNo) throws Exception;
	
	void deleteAuctionProdItem( String businessNo);
	
	void insertAuctionProdItem(String prodSeq , String businessNo);
	
	Vendor selectNewVendorInfoApply(String bmanNo);
	
	void updateNewVendorInfoApply(HashMap<String, Object> hmap);

	/**
	 * 협력사 상담신청서 질문 응답정보??
	 * @param businessNo
	 * @return
	 */
	List<Vendor> selectVendorAnswer(String businessNo);

	void insertNewVendorKindCd(NEDMCST0310VO nEDMCST0310VO);

	void updateNewVendorKindCd(NEDMCST0310VO nEDMCST0310VO);

	void deleteNewVendorAnswer(NEDMCST0310VO nEDMCST0310VO);

	void insertNewVendorAnswer2(NEDMCST0310VO nEDMCST0310VO);
	
	public List consultAdminSelectDetail(String str ) throws Exception;
	public List consultAdminSelectDetailPast(String str ) throws Exception;
	public List<NEDMCST0310VO> selectTeamCdListApply(NEDMCST0310VO nEDMCST0310VO);
	
	
	/**
	 * 입점상담신청 상품카테고리의 상담신청서 작성화면에서 희망부서 선택시 해당부서의 팀 조회
	 * @param nEDMCST0310VO
	 * @return
	 */
	public List<EdiCommonCode> selectL1ListApply(NEDMCST0310VO nEDMCST0310VO);
	
	public void updateKindProductInfoApply(Vendor submittedVendorInfo) throws Exception; 
	
	int updateAtchFileKindCd(Vendor vendor);
	int updateAttachFileCode(Vendor vendor);
	int NEDMCST0310deleteImg(Vendor vendor);
}
