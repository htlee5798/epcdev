package com.lottemart.epc.delivery.model;

import java.io.Serializable;

public class PSCMDLV0012VO implements Serializable {

	/**
	 * 클러스터 시스템에서의 직렬화 아이디
	 */
	private static final long serialVersionUID = 3257422998022759739L;
	
	/** 조회시작일 */
	private String startDate = "";
	
	/** 조회종료일 */
	private String endDate = "";
	
	/** 주문번호 */
	private String orderId = "";
	
	/** 협력업체코드 */
	private String[] vendorIds;
	
	/** 상품유형 */
	private String prodTypeCd;	
	
	/** 주문상태 */
	private String saleStsCd = "";
	
	/** 배송상태 */
	private String deliStatusCode = "";

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

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	
	public String[] getVendorIds() {
		return vendorIds;
	}

	public void setVendorIds(String[] vendorIds) {
		this.vendorIds = vendorIds;
	}

	public String getProdTypeCd() {
		return prodTypeCd;
	}

	public void setProdTypeCd(String prodTypeCd) {
		this.prodTypeCd = prodTypeCd;
	}

	public String getSaleStsCd() {
		return saleStsCd;
	}

	public void setSaleStsCd(String saleStsCd) {
		this.saleStsCd = saleStsCd;
	}

	public String getDeliStatusCode() {
		return deliStatusCode;
	}

	public void setDeliStatusCode(String deliStatusCode) {
		this.deliStatusCode = deliStatusCode;
	}

	
}
