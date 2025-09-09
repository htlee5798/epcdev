package com.lottemart.epc.edi.comm.dao;

//import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lcn.module.common.util.HashBox;
import lcn.module.framework.base.AbstractDAO;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.lottemart.epc.common.util.SecureUtil;
import com.lottemart.epc.edi.comm.model.EdiCommonCode;
import com.lottemart.epc.edi.comm.model.SearchParam;
import com.lottemart.epc.edi.consult.model.NEDMCST0310VO;
import com.lottemart.epc.edi.product.model.OnlineDisplayCategory;

@Repository("NEDPCOM0010Dao")
public class NEDPCOM0010Dao extends AbstractDAO {

	
	@SuppressWarnings("unchecked")
	public List<HashBox> selectStore(Map<String, Object> map ) throws Exception	{
		return (List<HashBox>)getSqlMapClientTemplate().queryForList("common.STORE_SELECT");
	}
	
	
	

	@SuppressWarnings("unchecked")
	public List<EdiCommonCode> selectCommonCodeList(SearchParam searchParam)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().queryForList("common.getCommonCodeList", searchParam);
	}


	@SuppressWarnings("unchecked")
	public List<EdiCommonCode> selectTeamList(SearchParam searchParam)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().queryForList("common.getTeamList", searchParam);
	}


	@SuppressWarnings("unchecked")
	public List<EdiCommonCode> selectDepartmentList()
			throws DataAccessException {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().queryForList("common.getDepartmentListGroupByOrgCd", null);
	}
	
	@SuppressWarnings("unchecked")
	public List<HashBox> selectDepartmentListNew()
			throws DataAccessException {
		// TODO Auto-generated method stub
		return (List<HashBox>)getSqlMapClientTemplate().queryForList("common.getDepartmentListGroupByOrgCdNew", null);
	}
	//추가이동빈 카테고리 조회
	@SuppressWarnings("unchecked")
	public List<HashBox> selectConsultcategroyListNew()
			throws DataAccessException {
		// TODO Auto-generated method stub
		return (List<HashBox>)getSqlMapClientTemplate().queryForList("common.getConsultcategroyListNew", null);
	}
	//추가손영광 카테고리 조회
		@SuppressWarnings("unchecked")
		public List<NEDMCST0310VO> selectConsultcategroyListNew2()
				throws DataAccessException {
			return (List<NEDMCST0310VO>)getSqlMapClientTemplate().queryForList("common.getConsultcategroyListNew2", null);
		}
	//	selectConsultTeamList
		//추가손영광 카테고리 조회
		@SuppressWarnings("unchecked")
		public List<HashBox> selectConsultTeamList()
				throws DataAccessException {
			// TODO Auto-generated method stub
			return (List<HashBox>)getSqlMapClientTemplate().queryForList("common.getConsultTeamList", null);
		}	
		
	
	//사용안함 20120326 이동빈
	@SuppressWarnings("unchecked")
	public List<HashBox> selectDepartmentDetailList(Map<String,Object> map)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return (List<HashBox>)getSqlMapClientTemplate().queryForList("common.selectL4CodeList", map);
	}
	@SuppressWarnings("unchecked")
	public List<HashBox> selectL1cdDetailList(Map<String,Object> map)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return (List<HashBox>)getSqlMapClientTemplate().queryForList("common.selectL1CodeListNew", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<HashBox> selectAllL1cdDetailList(Map<String,Object> map)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return (List<HashBox>)getSqlMapClientTemplate().queryForList("common.selectAllL1CodeListNew", map);
	}
	@SuppressWarnings("unchecked")
	public List<EdiCommonCode> selectOtherStoreList()
			throws DataAccessException {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().queryForList("common.getOtherStoreList", null);
	}
	
	@SuppressWarnings("unchecked")
	public List<EdiCommonCode> selectColorList()
			throws DataAccessException {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().queryForList("common.getColorList", null);
	}
	

	@SuppressWarnings("unchecked")
	public List<EdiCommonCode> selectSizeCategoryList()
			throws DataAccessException {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().queryForList("common.getSizeCategoryList", null);
	}
	
	@SuppressWarnings("unchecked")
	public List<EdiCommonCode> selectSizeList(String sizeCategoryCode)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().queryForList("common.getSizeList", sizeCategoryCode);
	}




	@SuppressWarnings("unchecked")
	public List<EdiCommonCode> selectL1List(SearchParam searchParam) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().queryForList("common.getL1List", searchParam);
	}




	@SuppressWarnings("unchecked")
	public List<EdiCommonCode> selectL4List(SearchParam searchParam) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().queryForList("common.getL4List", searchParam);
	}




	@SuppressWarnings("unchecked")
	public List<EdiCommonCode> selectDistinctTeamList(SearchParam searchParam) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().queryForList("common.getTeamList", searchParam);
	}




	@SuppressWarnings("unchecked")
	public List<EdiCommonCode> selectL4ListAjax(SearchParam searchParam) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().queryForList("common.getL4ListAjax", searchParam);
	}
	
	public HashBox selectOrg(Map<String, Object> map ) throws Exception	{
		return (HashBox)getSqlMapClientTemplate().queryForObject("common.ORG_SELECT",map);
	}
	public HashBox selectL1Team(String teamCd ) throws Exception	{
		return (HashBox)getSqlMapClientTemplate().queryForObject("common.L1Team_SELECT",teamCd);
	}
	public List<HashBox> selectL1Cd(String teamCd) throws Exception	{
		return (List<HashBox>)getSqlMapClientTemplate().queryForList("common.L1Cd_SELECT",teamCd);
	}
	public List<HashBox> selectL1CdEtc(String teamCd ) throws Exception	{
		return (List<HashBox>)getSqlMapClientTemplate().queryForList("common.L1Cd_SELECT_ETC",teamCd);
	}
	

	@SuppressWarnings("unchecked")
	public List<EdiCommonCode> selectBrandList(String brandName) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().queryForList("common.selectBrandList", brandName);
	}
	
	@SuppressWarnings("unchecked")
	public List<EdiCommonCode> selectMakerList(String makerName) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().queryForList("common.selectMakerList", makerName);
	}




	@SuppressWarnings("unchecked")
	public List<EdiCommonCode> selectStyleCodeList() {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().queryForList("common.getStyleCodeList", null);
	}




	@SuppressWarnings("unchecked")
	public List<EdiCommonCode> selectSeasonTypeList(SearchParam searchParam) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().queryForList("common.getSeasonTypeList", searchParam);
	}




	@SuppressWarnings("unchecked")
	public List<OnlineDisplayCategory> selectOnlineDisplayCategoryList(String categoyDepth2) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().queryForList("common.selectOnlineDisplayCategoryList", categoyDepth2);
	}




	@SuppressWarnings("unchecked")
	public List<EdiCommonCode> selectL4ListForOnlineAjax(SearchParam searchParam) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().queryForList("common.getL4ListForOnlineAjax", searchParam);
	}



	@SuppressWarnings("unchecked")
	public List<EdiCommonCode> selectVendorListByBusinessNo(String parentCodeId) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().queryForList("common.geVendorListByBusinessNo", SecureUtil.sqlValid(parentCodeId));
	}
	
	
	@SuppressWarnings("unchecked")
	public List<EdiCommonCode> selectProdAddTemplateList(SearchParam searchParam) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().queryForList("common.getProdAddTemplateList", searchParam);
	}
	
	@SuppressWarnings("unchecked")
	public List<EdiCommonCode> selectProdAddTemplateUpdateList(SearchParam searchParam) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().queryForList("common.getProdAddTemplateUpdateList", searchParam);
	}

	
	@SuppressWarnings("unchecked")
	public List<EdiCommonCode> selectProdAddTemplateDetailList(SearchParam searchParam) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().queryForList("common.getProdAddTemplateDetailList", searchParam);
	}
	@SuppressWarnings("unchecked")
	public List<EdiCommonCode> selectProdAddTemplateUpdateDetailList(SearchParam searchParam) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().queryForList("common.getProdAddTemplateUpdateDetailList", searchParam);
	}
											//	selectProdAddTemplateUpdateDetailList




	public Object selectTeamCode2(Map<String, Object>map) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().queryForObject("common.Team2_SELECT",map);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<EdiCommonCode> selectProdCertTemplateList(SearchParam searchParam) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().queryForList("common.getProdCertTemplateList", searchParam);
	}
	
		@SuppressWarnings("unchecked")
	public List<EdiCommonCode> selectProdCertTemplateDetailList(SearchParam searchParam) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().queryForList("common.getProdCertTemplateDetailList", searchParam);
	}
	
	
		@SuppressWarnings("unchecked")
	public List<EdiCommonCode> selectProdCertTemplateUpdateDetailList(SearchParam searchParam) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().queryForList("common.getProdCertTemplateUpdateDetailList", searchParam);
	}	
}
