package com.lottemart.epc.edi.comm.service.impl;

import java.util.List;
import java.util.Map;

import lcn.module.common.util.HashBox;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.edi.comm.dao.PEDPCOM0001Dao;
import com.lottemart.epc.edi.comm.service.PEDPCOM0001Service;
import com.lottemart.epc.edi.comm.model.Constants;
import com.lottemart.epc.edi.comm.model.EdiCommonCode;
import com.lottemart.epc.edi.comm.model.SearchParam;
import com.lottemart.epc.edi.product.model.OnlineDisplayCategory;

@Service("PEDPCOM0001Service")
public class PEDPCOM0001ServiceImpl implements PEDPCOM0001Service{
	
	@Autowired
	private PEDPCOM0001Dao pedpcom001Dao;
	
	
	@SuppressWarnings("rawtypes")
	public List selectStore(Map<String, Object> map) throws Exception{
		return pedpcom001Dao.selectStore(map );
	}
	

	public List<EdiCommonCode> selectCountryList() {
		// TODO Auto-generated method stub
		SearchParam searchParam = new SearchParam();
		searchParam.setGroupCode(Constants.COUNTRY_GROUP_CD);
		return pedpcom001Dao.selectCommonCodeList(searchParam);
	}


	public List<EdiCommonCode> selectProductPatternList() {
		// TODO Auto-generated method stub
		SearchParam searchParam = new SearchParam();
		searchParam.setGroupCode(Constants.PRODUCT_PATTERN_GROUP_CD);
		return pedpcom001Dao.selectCommonCodeList(searchParam);
	}


	public List<EdiCommonCode> selectOrderUnitList() {
		// TODO Auto-generated method stub
		SearchParam searchParam = new SearchParam();
		searchParam.setGroupCode(Constants.ORD_UNIT_GROUP_CD);
		return pedpcom001Dao.selectCommonCodeList(searchParam);
	}


	public List<EdiCommonCode> selectTaxTypeList() {
		// TODO Auto-generated method stub
		SearchParam searchParam = new SearchParam();
		searchParam.setGroupCode(Constants.TAX_TYPE_GROUP_CD);
		return pedpcom001Dao.selectCommonCodeList(searchParam);
	}


	public List<EdiCommonCode> selectDisplayUnitList() {
		// TODO Auto-generated method stub
		SearchParam searchParam = new SearchParam();
		searchParam.setGroupCode(Constants.DISPLAY_UNIT_GROUP_CD);
		return pedpcom001Dao.selectCommonCodeList(searchParam);
	}


	public List<EdiCommonCode> selectBuyTypeListWithoutOrder() {
		// TODO Auto-generated method stub
		SearchParam searchParam = new SearchParam();
		searchParam.setGroupCode(Constants.NO_ORDER_GROUP_CD);
		return pedpcom001Dao.selectCommonCodeList(searchParam);
	}


	
	
	public List<EdiCommonCode> selectProtectTagList() {
		// TODO Auto-generated method stub
		SearchParam searchParam = new SearchParam();
		searchParam.setGroupCode(Constants.PROTECT_TAG_GROUP_CD);
		return pedpcom001Dao.selectCommonCodeList(searchParam);
	}

	
	public List<EdiCommonCode> selectCenterTypeList() {
		// TODO Auto-generated method stub
		SearchParam searchParam = new SearchParam();
		searchParam.setGroupCode(Constants.CENTER_GROUP_CD);
		return pedpcom001Dao.selectCommonCodeList(searchParam);
	}


	public List<EdiCommonCode> selectTempTypeList() {
		// TODO Auto-generated method stub
		SearchParam searchParam = new SearchParam();
		searchParam.setGroupCode(Constants.TEMP_GROUP_CD);
		return pedpcom001Dao.selectCommonCodeList(searchParam);
	}


	public List<EdiCommonCode> selectFlowDateManageTypeList() {
		// TODO Auto-generated method stub
		SearchParam searchParam = new SearchParam();
		searchParam.setGroupCode(Constants.FLOW_DATE_GROUP_CD);
		return pedpcom001Dao.selectCommonCodeList(searchParam);
	}


	public List<EdiCommonCode> selectSeasonTypeList(String productDivnCode) {
		
		SearchParam searchParam = new SearchParam();
		searchParam.setGroupCode(Constants.SEASON_GROUP_CD);
		
		// TODO 패션 상품일때 계절값이 2자리이므로 현재 칼럼사이즈 조정 필요. 조정후 아래 주석 해제 할것.
		if(Constants.STANDARD_PRODUCT_CD.equals(productDivnCode)) {
			return pedpcom001Dao.selectSeasonTypeList(searchParam);
		} else {
			return pedpcom001Dao.selectStyleCodeList();
		}
	}

	public List<EdiCommonCode> selectSeasonTypeListOld(String productDivnCode) {
		
		SearchParam searchParam = new SearchParam();
		searchParam.setGroupCode(Constants.SEASON_GROUP_CD);
		
		// TODO 패션 상품일때 계절값이 2자리이므로 현재 칼럼사이즈 조정 필요. 조정후 아래 주석 해제 할것.
		if(Constants.STANDARD_PRODUCT_CD.equals(productDivnCode)) {
			return pedpcom001Dao.selectSeasonTypeListOld(searchParam);
		} else {
			return pedpcom001Dao.selectStyleCodeList();
		}
	}


	public List<EdiCommonCode> selectTotalCheckTypeList() {
		// TODO Auto-generated method stub
		SearchParam searchParam = new SearchParam();
		searchParam.setGroupCode(Constants.TOTAL_CHECK_GROUP_CD);
		return pedpcom001Dao.selectCommonCodeList(searchParam);
	}


	
	
	public List<EdiCommonCode> selectTeamList() {
		// TODO Auto-generated method stub
		SearchParam searchParam = new SearchParam();
		searchParam.setDetailCode(Constants.DEFAULT_TEAM_CD);
		return pedpcom001Dao.selectTeamList(searchParam);
	}
	
	public List<EdiCommonCode> selectDepartmentList() {
		// TODO Auto-generated method stub
		return pedpcom001Dao.selectDepartmentList();
	}
	
	public List<HashBox> selectConsultcategroyListNew() {
		// TODO Auto-generated method stub 
		return pedpcom001Dao.selectConsultcategroyListNew();
	}
	public List<HashBox> selectConsultcategroyListNew2() {
		// TODO Auto-generated method stub 
		return pedpcom001Dao.selectConsultcategroyListNew2();
	}
	public List<HashBox> selectConsultTeamList() {
		// TODO Auto-generated method stub 
		return pedpcom001Dao.selectConsultTeamList();
	} 
	
	public List<HashBox> selectDepartmentListNew() {
		// TODO Auto-generated method stub
		return pedpcom001Dao.selectDepartmentListNew();
	}
	//사용안함 이동빈 20120326
	public List<HashBox> selectDepartmentDetailList(Map<String,Object> map) {
		// TODO Auto-generated method stub
		return pedpcom001Dao.selectDepartmentDetailList(map);
	}
	public List<HashBox> selectL1cdDetailList(Map<String,Object> map) {
		// TODO Auto-generated method stub
		return pedpcom001Dao.selectL1cdDetailList(map);
	}
	public List<HashBox> selectAllL1cdDetailList(Map<String,Object> map) {
		// TODO Auto-generated method stub
		return pedpcom001Dao.selectAllL1cdDetailList(map);
	}
	
	
	public List<EdiCommonCode> selectOtherStoreList() {
		// TODO Auto-generated method stub
		return pedpcom001Dao.selectOtherStoreList();
	}
	
	
	public List<EdiCommonCode> selectColorList() {
		// TODO Auto-generated method stub
		return pedpcom001Dao.selectColorList();
	}

	public List<EdiCommonCode> selectColorListOld() {
		// TODO Auto-generated method stub
		return pedpcom001Dao.selectColorListOld();
	}
	
	public List<EdiCommonCode> selectSizeCategoryList() {
		// TODO Auto-generated method stub
		return pedpcom001Dao.selectSizeCategoryList();
	}
	
	public List<EdiCommonCode> selectSizeCategoryListOld() {
		// TODO Auto-generated method stub
		return pedpcom001Dao.selectSizeCategoryListOld();
	}
	
	
	public List<EdiCommonCode> selectSizeList( String sizeCategoryCode) {
		// TODO Auto-generated method stub
		return pedpcom001Dao.selectSizeList(sizeCategoryCode);
	}

	public List<EdiCommonCode> selectSizeListOld( String sizeCategoryCode) {
		// TODO Auto-generated method stub
		return pedpcom001Dao.selectSizeList(sizeCategoryCode);
	}

	public List<EdiCommonCode> selectL1List(SearchParam searchParam) {
		// TODO Auto-generated method stub
		if(StringUtils.isEmpty(searchParam.getGroupCode())) {
			searchParam.setGroupCode(Constants.DEFAULT_TEAM_CD);
			searchParam.setDetailCode(Constants.DEFAULT_DETAIL_CD);
		}
		
		return pedpcom001Dao.selectL1List(searchParam);
	}


	public List<EdiCommonCode> selectL1ListOld(SearchParam searchParam) {
		// TODO Auto-generated method stub
		if(StringUtils.isEmpty(searchParam.getGroupCode())) {
			searchParam.setGroupCode(Constants.DEFAULT_TEAM_CD);
			searchParam.setDetailCode(Constants.DEFAULT_DETAIL_CD);
		}
	
		return pedpcom001Dao.selectL1ListOld(searchParam);
	}


	
	public List<EdiCommonCode> selectL4List(SearchParam searchParam) {
		// TODO Auto-generated method stub
		return pedpcom001Dao.selectL4List(searchParam);
	}


	public List<EdiCommonCode> selectDistinctTeamList() {
		// TODO Auto-generated method stub
		SearchParam searchParam = new SearchParam();
		searchParam.setDetailCode(Constants.DEFAULT_DETAIL_CD);
		return pedpcom001Dao.selectDistinctTeamList(searchParam);
	}

	public List<EdiCommonCode> selectDistinctTeamListOld() {
		// TODO Auto-generated method stub
		SearchParam searchParam = new SearchParam();
		searchParam.setDetailCode(Constants.DEFAULT_DETAIL_CD);
		return pedpcom001Dao.selectDistinctTeamListOld(searchParam);
	}


	public List<EdiCommonCode> selectL4ListAjax(SearchParam searchParam) {
		// TODO Auto-generated method stub
		return pedpcom001Dao.selectL4ListAjax(searchParam);
	}
	
	
	public List<EdiCommonCode> selectL4ListAjaxOld(SearchParam searchParam) {
		// TODO Auto-generated method stub
		return pedpcom001Dao.selectL4ListAjaxOld(searchParam);
	}
	
	
	
	public HashBox selectOrg(Map<String,Object> map) throws Exception{
		// TODO Auto-generated method stub
		return pedpcom001Dao.selectOrg(map );
	}
	public HashBox selectL1Team(Map<String,Object> map) throws Exception{
		// TODO Auto-generated method stub
		return pedpcom001Dao.selectL1Team(map );
	}
	public List<HashBox> selectL1Cd(Map<String,Object> map) throws Exception{
		// TODO Auto-generated method stub
		return pedpcom001Dao.selectL1Cd(map );
	}
	public List<HashBox> selectL1CdEtc(Map<String,Object> map) throws Exception{
		// TODO Auto-generated method stub
		return pedpcom001Dao.selectL1CdEtc(map );
	}



	@Override
	public List<EdiCommonCode> selectBrandList(String brandName) {
		// TODO Auto-generated method stub
		return pedpcom001Dao.selectBrandList(brandName);
	}


	@Override
	public List<EdiCommonCode> selectMakerList(String makerName) {
		// TODO Auto-generated method stub
		return pedpcom001Dao.selectMakerList(makerName);
	}


	@Override
	public List<EdiCommonCode> selectStyleCodeList() {
		// TODO Auto-generated method stub
		return pedpcom001Dao.selectStyleCodeList();
	}


	@Override
	public List<OnlineDisplayCategory> selectOnlineDisplayCategoryList(String categoryDepth2 ) {
		// TODO Auto-generated method stub
		return pedpcom001Dao.selectOnlineDisplayCategoryList( categoryDepth2 );
	}
	
	@Override
	public List<OnlineDisplayCategory> selectNewOnlineDisplayAllCategoryList(DataMap paramMap ) {
		// TODO Auto-generated method stub
		return pedpcom001Dao.selectNewOnlineDisplayAllCategoryList( paramMap );
	}
	
	
	public List<OnlineDisplayCategory> selectNewOnlineDisplayCategoryList(DataMap paramMap ) {
		// TODO Auto-generated method stub
		return pedpcom001Dao.selectNewOnlineDisplayCategoryList( paramMap );
	}


	@Override
	public List<EdiCommonCode> selectL4ListForOnlineAjax(SearchParam searchParam) {
		// TODO Auto-generated method stub
		return pedpcom001Dao.selectL4ListForOnlineAjax(searchParam);
	}
	
	@Override
	public List<EdiCommonCode> selectL4ListForOnlineAjaxOld(SearchParam searchParam) {
		// TODO Auto-generated method stub
		return pedpcom001Dao.selectL4ListForOnlineAjaxOld(searchParam);
	}


	@Override
	public List<EdiCommonCode> selectVendorListByBusinessNo(String bman_no) {
		// TODO Auto-generated method stub
		return pedpcom001Dao.selectVendorListByBusinessNo(bman_no);
	}
	
	public List<EdiCommonCode> selectProdAddTemplateList(SearchParam searchParam) {
		
		return pedpcom001Dao.selectProdAddTemplateList(searchParam);
	}
	
	public List<EdiCommonCode> selectProdAddTemplateUpdateList(SearchParam searchParam) {
		
		return pedpcom001Dao.selectProdAddTemplateUpdateList(searchParam);
	}
	
	
	
	
	
	
	public List<EdiCommonCode> selectProdAddTemplateDetailList(SearchParam searchParam) {
		
		return pedpcom001Dao.selectProdAddTemplateDetailList(searchParam);
	}
	public List<EdiCommonCode> selectProdAddTemplateUpdateDetailList(SearchParam searchParam) {
		
		return pedpcom001Dao.selectProdAddTemplateUpdateDetailList(searchParam);
	}


	@Override
	public Object selectTeamCode2(Map<String, Object> hmap) {
		// TODO Auto-generated method stub
		return pedpcom001Dao.selectTeamCode2(hmap);
	}
	
	
	public List<EdiCommonCode> selectProdCertTemplateList(SearchParam searchParam) {
		
		return pedpcom001Dao.selectProdCertTemplateList(searchParam);
	}
	
	public List<EdiCommonCode> selectProdCertTemplateDetailList(SearchParam searchParam) {
		
		return pedpcom001Dao.selectProdCertTemplateDetailList(searchParam);
	}	


	public List<EdiCommonCode> selectProdCertTemplateUpdateDetailList(SearchParam searchParam) {
		
		return pedpcom001Dao.selectProdCertTemplateUpdateDetailList(searchParam);
	}	
	
	
	
	
}
