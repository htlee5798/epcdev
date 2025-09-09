package com.lottemart.epc.edi.comm.model;

import java.io.Serializable;

public class SearchParam implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1389842604426902601L;
	private String vendorCode;
	private String startDate;
	private String endDate;
	private String productConfirmFlag;
	
	
	private String groupCode;
	private String detailCode;
	private String l4Code;
	private String l1Code;
	private String teamCode;

	private String businessNo;
	private String password;
	private String vendorName;
	
	private String newProductCode;
	
	
	private Integer stopTradeVendorCount;
	
	private String tradeType;
	private String taxDivnCode;
	
	
	private String onlineCategoryName;
	
	private String ceoName;
	private String email;
	
	private String prodArraySeq;
	
	private String catCd;
	private String infoGrpCd;
	private String flag;
	
	private String cityNm;
//	private String guNm;
//	private String streetNm;
	
	private String pageIdx;
	
	
	
	
	public String getPageIdx() {
		return pageIdx;
	}
	public void setPageIdx(String pageIdx) {
		this.pageIdx = pageIdx;
	}
	public String getCeoName() {
		return ceoName;
	}
	public void setCeoName(String ceoName) {
		this.ceoName = ceoName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getOnlineCategoryName() {
		return onlineCategoryName;
	}
	public void setOnlineCategoryName(String onlineCategoryName) {
		this.onlineCategoryName = onlineCategoryName;
	}
	public String getTaxDivnCode() {
		return taxDivnCode;
	}
	public void setTaxDivnCode(String taxDivnCode) {
		this.taxDivnCode = taxDivnCode;
	}
	public Integer getStopTradeVendorCount() {
		return stopTradeVendorCount;
	}
	public void setStopTradeVendorCount(Integer stopTradeVendorCount) {
		this.stopTradeVendorCount = stopTradeVendorCount;
	}
	public String getTradeType() {
		return tradeType;
	}
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
	public String getNewProductCode() {
		return newProductCode;
	}
	public void setNewProductCode(String newProductCode) {
		this.newProductCode = newProductCode;
	}
	public String getL1Code() {
		return l1Code;
	}
	public void setL1Code(String l1Code) {
		this.l1Code = l1Code;
	}
	public String getTeamCode() {
		return teamCode;
	}
	public void setTeamCode(String teamCode) {
		this.teamCode = teamCode;
	}
	public String getL4Code() {
		return l4Code;
	}
	public void setL4Code(String l4Code) {
		this.l4Code = l4Code;
	}	
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getBusinessNo() {
		return businessNo;
	}
	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
	}
	public String getGroupCode() {
		return groupCode;
	}
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	public String getDetailCode() {
		return detailCode == null ? Constants.DEFAULT_DETAIL_CD : detailCode;
	}
	public void setDetailCode(String detailCode) {
		this.detailCode = detailCode;
	}
	public String getVendorCode() {
		return vendorCode;
	}
	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getProductConfirmFlag() {
		return productConfirmFlag;
	}
	public void setProductConfirmFlag(String productConfirmFlag) {
		this.productConfirmFlag = productConfirmFlag;
	}
	public String getProdArraySeq() {
		return prodArraySeq;
	}
	public void setProdArraySeq(String prodArraySeq) {
		this.prodArraySeq = prodArraySeq;
	}
	public String getCatCd() {
		return catCd;
	}
	public void setCatCd(String catCd) {
		this.catCd = catCd;
	}
	public String getInfoGrpCd() {
		return infoGrpCd;
	}
	public void setInfoGrpCd(String infoGrpCd) {
		this.infoGrpCd = infoGrpCd;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getCityNm() {
		return cityNm;
	}
	public void setCityNm(String cityNm) {
		this.cityNm = cityNm;
	}

	
}
