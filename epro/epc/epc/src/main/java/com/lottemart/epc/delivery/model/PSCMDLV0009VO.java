package com.lottemart.epc.delivery.model;

import java.io.Serializable;

public class PSCMDLV0009VO implements Serializable {

	/**
	 * 클러스터 시스템에서의 직렬화 아이디 
	 */
	private static final long serialVersionUID = 2816737160725464999L;

	private String majorCd = "";
	
	/** 조회시작일(yyyy-mm-dd) */
    private String startDate = "";
    
    /** 조회종료일(yyyy-mm-dd) */
    private String endDate = "";
    
    /** 조회시작일(yyyymmdd) */
    private String fromDate = "";
    
    /** 조회종료일(yyyymmdd) */
    private String toDate = "";    

    /** 협력업체코드 */
    private String vendorId;
    
    private String vendorMode = "";
    
    /** 주문종류(주문/반품/취소 구분) */
    private String ordRtnDivnCd = "";
        
    /** 주문상태 */
    private String ordStsCd = "";

    /** 주문번호 */
    private String orderId = "";

    /** 주문단품순번 */
    private String orderItemSeq = "";

    /** 배송상태 */
    private String deliStatusCd = "";

    /** 배송지순번 */
    private String deliveryId = "";

    /** 수정자ID */
    private String modId = "";

    /** 주문타입(주문번호, 회원ID, 회원성명, 상품명) */
    private String searchType = "";
    
    /** 주문타입 content */
    private String searchContent = "";
   
    private String flag = "";

    /** 통합ec 주문여부 */
    private String ecOrderYn = "";

    public String getEcOrderYn() {
		return ecOrderYn;
	}

	public void setEcOrderYn(String ecOrderYn) {
		this.ecOrderYn = ecOrderYn;
	}

	public String getVendorId() {
		return vendorId;
	}

	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}

	public String getVendorMode() {
		return vendorMode;
	}

	public void setVendorMode(String vendorMode) {
		this.vendorMode = vendorMode;
	}
    
	public String getMajorCd() {
		return majorCd;
	}

	public void setMajorCd(String majorCd) {
		this.majorCd = majorCd;
	}

	public String getFromDate() {
		return fromDate;
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

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getOrdRtnDivnCd() {
		return ordRtnDivnCd;
	}

	public void setOrdRtnDivnCd(String ordRtnDivnCd) {
		this.ordRtnDivnCd = ordRtnDivnCd;
	}

	public String getOrdStsCd() {
		return ordStsCd;
	}

	public void setOrdStsCd(String ordStsCd) {
		this.ordStsCd = ordStsCd;
	}

	public String getSearchContent() {
		return searchContent;
	}

	public void setSearchContent(String searchContent) {
		this.searchContent = searchContent;
	}
	
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderItemSeq() {
		return orderItemSeq;
	}

	public void setOrderItemSeq(String orderItemSeq) {
		this.orderItemSeq = orderItemSeq;
	}

	public String getDeliStatusCd() {
		return deliStatusCd;
	}

	public void setDeliStatusCd(String deliStatusCd) {
		this.deliStatusCd = deliStatusCd;
	}

	public String getDeliveryId() {
		return deliveryId;
	}

	public void setDeliveryId(String deliveryId) {
		this.deliveryId = deliveryId;
	}
	
	public String getModId() {
		return modId;
	}

	public void setModId(String modId) {
		this.modId = modId;
	}

}