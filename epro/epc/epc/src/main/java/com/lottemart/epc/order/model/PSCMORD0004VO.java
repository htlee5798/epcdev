package com.lottemart.epc.order.model;

import java.io.Serializable;

public class PSCMORD0004VO implements Serializable {

	/**
	 * 클러스터 시스템에서의 직력화 아이디 
	 */
	private static final long serialVersionUID = 5475322303065492429L;

	/** 조회기간 구분 */
	private String searchType = "";

	/** 조회시작일 */
    private String startDate = "";
    
    /** 조회종료일 */
    private String endDate = "";
    
	/** 조회시작일 */
    private String fromDate = "";
    
    /** 조회종료일 */
    private String toDate = "";    
    
    /** 주문번호 */
    private String orderId = "";
    
    /** 주문상태 */
    private String ordStsCd = "";

    /** 결제방법 */
    private String setlTypeCd = "";
    
    /** 매출상태 */
    private String saleStsCd = "";    
    
    /** 주문자 */
    private String custNm = "";
    
    /** 점포코드 */
    private String strCd = "";    

    /** 협력업체코드 */
    private String vendorId = "";    
    
    private String majorCd = "";

	/** 검색사용여부 */
    private String searchUseYn = "";
    
	/** 현재페이지 */
    private int pageIndex = 1;
    
    /** 페이지갯수 */
    private int pageUnit = 10;
    
    /** 페이지사이즈 */
    private int pageSize = 10;

    /** firstIndex */
    private int firstIndex = 1;

    /** lastIndex */
    private int lastIndex = 1;

    /** recordCountPerPage */
    private int recordCountPerPage = 10;
    
    /** 주문번호 구분*/
    private String searchOrderType = "";
    
    public String getMajorCd() {
		return majorCd;
	}

	public void setMajorCd(String majorCd) {
		this.majorCd = majorCd;
	}
	
	public String getStrCd() {
		return strCd;
	}

	public void setStrCd(String strCd) {
		this.strCd = strCd;
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

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrdStsCd() {
		return ordStsCd;
	}

	public void setOrdStsCd(String ordStsCd) {
		this.ordStsCd = ordStsCd;
	}

	public String getSaleStsCd() {
		return saleStsCd;
	}

	public void setSaleStsCd(String saleStsCd) {
		this.saleStsCd = saleStsCd;
	}

	public String getVendorId() {
		return vendorId;
	}

	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}

	public String getSetlTypeCd() {
		return setlTypeCd;
	}

	public void setSetlTypeCd(String setlTypeCd) {
		this.setlTypeCd = setlTypeCd;
	}

	public String getCustNm() {
		return custNm;
	}

	public void setCustNm(String custNm) {
		this.custNm = custNm;
	}

	public String getSearchUseYn() {
		return searchUseYn;
	}

	public void setSearchUseYn(String searchUseYn) {
		this.searchUseYn = searchUseYn;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageUnit() {
		return pageUnit;
	}

	public void setPageUnit(int pageUnit) {
		this.pageUnit = pageUnit;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getFirstIndex() {
		return firstIndex;
	}

	public void setFirstIndex(int firstIndex) {
		this.firstIndex = firstIndex;
	}

	public int getLastIndex() {
		return lastIndex;
	}

	public void setLastIndex(int lastIndex) {
		this.lastIndex = lastIndex;
	}

	public int getRecordCountPerPage() {
		return recordCountPerPage;
	}

	public void setRecordCountPerPage(int recordCountPerPage) {
		this.recordCountPerPage = recordCountPerPage;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}    
    
	public String getSearchOrderType() {
		return searchOrderType;
	}

	public void setSearchOrderType(String searchOrderType) {
		this.searchOrderType = searchOrderType;
	}
    
}
