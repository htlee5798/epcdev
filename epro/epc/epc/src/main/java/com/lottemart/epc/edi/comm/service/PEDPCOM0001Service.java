package com.lottemart.epc.edi.comm.service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lcn.module.common.util.HashBox;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.edi.comm.model.EdiCommonCode;
import com.lottemart.epc.edi.comm.model.SearchParam;
import com.lottemart.epc.edi.product.model.OnlineDisplayCategory;

public interface PEDPCOM0001Service {
	@SuppressWarnings("rawtypes")
	public List selectStore(Map<String,Object> map) throws Exception;

	
	public List<EdiCommonCode> selectCountryList();

	public List<EdiCommonCode> selectProductPatternList();
	
	public List<EdiCommonCode> selectOrderUnitList();
	
	public List<EdiCommonCode> selectTaxTypeList();
	
	public List<EdiCommonCode> selectBuyTypeListWithoutOrder();
	
	public List<EdiCommonCode> selectDisplayUnitList();
	
	public List<EdiCommonCode> selectProtectTagList();
	
	public List<EdiCommonCode> selectCenterTypeList();
	
	public List<EdiCommonCode> selectTempTypeList();
	
	public List<EdiCommonCode> selectFlowDateManageTypeList();
	
	public List<EdiCommonCode> selectSeasonTypeList(String productDivnCode);
	
	public List<EdiCommonCode> selectSeasonTypeListOld(String productDivnCode);
	
	
	public List<EdiCommonCode> selectStyleCodeList();
	
	public List<EdiCommonCode> selectTotalCheckTypeList();

	public List<EdiCommonCode> selectTeamList();
	
	public List<EdiCommonCode> selectDepartmentList();
	
	public List<EdiCommonCode> selectOtherStoreList();
	
	public List<EdiCommonCode> selectColorList();
	
	public List<EdiCommonCode> selectColorListOld();
	
	public List<EdiCommonCode> selectSizeCategoryList();
	
	public List<EdiCommonCode> selectSizeCategoryListOld();
	
	public List<EdiCommonCode> selectSizeList(String sizeCategoryCode);
	
	public List<EdiCommonCode> selectSizeListOld(String sizeCategoryCode);
	
	public List<EdiCommonCode> selectL1List(SearchParam searchParam);
	
	public List<EdiCommonCode> selectL1ListOld(SearchParam searchParam);
	
	public List<EdiCommonCode> selectL4List(SearchParam searchParam);


	
	public List<EdiCommonCode> selectDistinctTeamList();

	public List<EdiCommonCode> selectDistinctTeamListOld();

	public List<EdiCommonCode> selectL4ListAjax(SearchParam searchParam);
	
	public List<EdiCommonCode> selectL4ListAjaxOld(SearchParam searchParam);
	
	//사용안함 20120326 이동빈
	public HashBox selectOrg(Map<String,Object> map ) throws Exception;
	public HashBox selectL1Team(Map<String,Object> map ) throws Exception;
	public List<HashBox> selectL1Cd(Map<String,Object> map ) throws Exception;
	public List<HashBox> selectL1CdEtc(Map<String,Object> map ) throws Exception;
	
	public List<HashBox> selectDepartmentListNew();
	
	
	public List<HashBox> selectConsultcategroyListNew();
	
	public List<HashBox> selectConsultcategroyListNew2();
	//입점추가 20150717
	public List<HashBox> selectConsultTeamList();
	
	//사용안함 20120326 이동빈 
	public List<HashBox> selectDepartmentDetailList(Map<String,Object> map) ;
	
	public List<HashBox> selectL1cdDetailList(Map<String,Object> map) ;
	
	public List<HashBox> selectAllL1cdDetailList(Map<String,Object> map) ;
						 
	public List<EdiCommonCode> selectBrandList(String brandName);
	
	public List<EdiCommonCode> selectMakerList(String brandName);
	
	public List<OnlineDisplayCategory> selectOnlineDisplayCategoryList(String categoryDepth2);

	public List<OnlineDisplayCategory> selectNewOnlineDisplayCategoryList(DataMap paramMap);
	
	public List<OnlineDisplayCategory> selectNewOnlineDisplayAllCategoryList(DataMap paramMap);

	public List<EdiCommonCode> selectL4ListForOnlineAjax(SearchParam searchParam);

	public List<EdiCommonCode> selectL4ListForOnlineAjaxOld(SearchParam searchParam);


	public List<EdiCommonCode> selectVendorListByBusinessNo(String bman_no);
	
	public List<EdiCommonCode> selectProdAddTemplateList(SearchParam searchParam);
	
	public List<EdiCommonCode> selectProdAddTemplateUpdateList(SearchParam searchParam);
	
	
	public List<EdiCommonCode> selectProdAddTemplateDetailList(SearchParam searchParam);
	
	public List<EdiCommonCode> selectProdAddTemplateUpdateDetailList(SearchParam searchParam);

	public Object selectTeamCode2(Map<String, Object> hmap);

	public List<EdiCommonCode> selectProdCertTemplateList(SearchParam searchParam);
	
	public List<EdiCommonCode> selectProdCertTemplateDetailList(SearchParam searchParam);
	
	public List<EdiCommonCode> selectProdCertTemplateUpdateDetailList(SearchParam searchParam);	
	
	
	
}
