package com.lottemart.epc.common.model;

import java.io.Serializable;

public class PSCMCOM0005VO implements Serializable {

	/**
	 * 클러스터 시스템에서의 직력화 아이디
	 */
	private static final long serialVersionUID = -8120634536814198185L;

	/** 조회시작일 */
	private String startDate = "";

	/** 조회종료일 */
	private String endDate = "";

	/** 조회시작일 */
	private String fromDate = "";

	/** 조회종료일 */
	private String toDate = "";

	private String dateGbn = "";

	private String majorCd = "";

	private String deliTypeCd = "";

	/** 배송상태(검색조건) */
	private String deliStatusCode = "";

	/** 협력사ID */
	private String strVendorId = "";

	/** 협력사명 */
	private String strVendorNm = "";

	/** 주문상태 */
	private String saleStsCd = "";

	/** 주문타입(주문번호, 로그인ID, 보내는분, 받는분) */
	private String searchType = "";

	/** 주문타입 content */
	private String searchContent = "";

	/** 점포 */
	private String strCd = "";

	/** 협력업체코드 */
	private String[] vendorId;

	/** Update List정보 */
	private String codeList = "";

	
	/** update 관련 변수 */

	/** 주문번호 */
	private String orderId = "";

	/** 주문번호 */
	private String upOrderId = "";

	/** 택배사코드 */
	private String hodecoCd = "";

	/** 택배사 운송장번호 */
	private String hodecoInvoiceNo = "";

	/** 택배사 추가운송장번호 */
	private String hodecoAddInvoiceNo = "";

	/** List 업체배송상태 */
	private String venDeliStatusCd = "";

	/** 배송상태 */
	private String deliStatusCd = "";

	/** 배송지순번 */
	private String deliveryId = "";

	/** 배송완료일시 */
	private String deliFnshDate = "";

	/** 배송번호 */
	private String deliNo = "";

	/** 운송장순번 */
	private String invoiceSeq = "";

	/** 운송장순번 */
	private String smsSendYn = "";

	/** 배송유형 */
	private String onlnDeliTypeCd = "";

	/** 상태코드 */
	private String statusFg = "";

	/** 협력업체코드 */
	private String venCd = "";

	/** 등록자 */
	private String regId = "";

	/** 수정자 */
	private String modId = "";

	private String adminId = "";

	public String getRegId() {
		return regId;
	}

	public void setRegId(String regId) {
		this.regId = regId;
	}

	public String[] getVendorId() {
		return vendorId;
	}

	public void setVendorId(String[] vendorId) {
		this.vendorId = vendorId;
	}

	public String getModId() {
		return modId;
	}

	public void setModId(String modId) {
		this.modId = modId;
	}

	public String getAdminId() {
		return adminId;
	}

	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

	public void setVenCd(String venCd) {
		this.venCd = venCd;
	}

	public String getVenCd() {
		return venCd;
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

	public String getDateGbn() {
		return dateGbn;
	}

	public void setDateGbn(String dateGbn) {
		this.dateGbn = dateGbn;
	}

	public String getMajorCd() {
		return majorCd;
	}

	public void setMajorCd(String majorCd) {
		this.majorCd = majorCd;
	}

	public String getDeliTypeCd() {
		return deliTypeCd;
	}

	public void setDeliTypeCd(String deliTypeCd) {
		this.deliTypeCd = deliTypeCd;
	}

	public String getVenDeliStatusCd() {
		return venDeliStatusCd;
	}

	public String getDeliStatusCd() {
		return deliStatusCd;
	}

	public void setDeliStatusCd(String deliStatusCd) {
		this.deliStatusCd = deliStatusCd;
	}

	public String getDeliStatusCode() {
		return deliStatusCode;
	}

	public void setDeliStatusCode(String deliStatusCode) {
		this.deliStatusCode = deliStatusCode;
	}

	public void setVenDeliStatusCd(String venDeliStatusCd) {
		this.venDeliStatusCd = venDeliStatusCd;
	}

	public String getStrVendorId() {
		return strVendorId;
	}

	public void setStrVendorId(String strVendorId) {
		this.strVendorId = strVendorId;
	}

	public String getStrVendorNm() {
		return strVendorNm;
	}

	public void setStrVendorNm(String strVendorNm) {
		this.strVendorNm = strVendorNm;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getSaleStsCd() {
		return saleStsCd;
	}

	public void setSaleStsCd(String saleStsCd) {
		this.saleStsCd = saleStsCd;
	}

	public String getSearchContent() {
		return searchContent;
	}

	public void setSearchContent(String searchContent) {
		this.searchContent = searchContent;
	}

	public String getCodeList() {
		return codeList;
	}

	public void setCodeList(String codeList) {
		this.codeList = codeList;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getUpOrderId() {
		return upOrderId;
	}

	public void setUpOrderId(String upOrderId) {
		this.upOrderId = upOrderId;
	}

	public String getHodecoCd() {
		return hodecoCd;
	}

	public void setHodecoCd(String hodecoCd) {
		this.hodecoCd = hodecoCd;
	}

	public String getHodecoInvoiceNo() {
		return hodecoInvoiceNo;
	}

	public void setHodecoInvoiceNo(String hodecoInvoiceNo) {
		this.hodecoInvoiceNo = hodecoInvoiceNo;
	}

	public String getHodecoAddInvoiceNo() {
		return hodecoAddInvoiceNo;
	}

	public void setHodecoAddInvoiceNo(String hodecoAddInvoiceNo) {
		this.hodecoAddInvoiceNo = hodecoAddInvoiceNo;
	}

	public String getDeliveryId() {
		return deliveryId;
	}

	public void setDeliveryId(String deliveryId) {
		this.deliveryId = deliveryId;
	}

	public String getDeliFnshDate() {
		return deliFnshDate;
	}

	public void setDeliFnshDate(String deliFnshDate) {
		this.deliFnshDate = deliFnshDate;
	}

	public String getDeliNo() {
		return deliNo;
	}

	public void setDeliNo(String deliNo) {
		this.deliNo = deliNo;
	}

	public String getInvoiceSeq() {
		return invoiceSeq;
	}

	public void setInvoiceSeq(String invoiceSeq) {
		this.invoiceSeq = invoiceSeq;
	}

	public String getSmsSendYn() {
		return smsSendYn;
	}

	public void setSmsSendYn(String smsSendYn) {
		this.smsSendYn = smsSendYn;
	}

	public String getStatusFg() {
		return statusFg;
	}

	public void setStatusFg(String statusFg) {
		this.statusFg = statusFg;
	}

	public String getOnlnDeliTypeCd() {
		return onlnDeliTypeCd;
	}

	public void setOnlnDeliTypeCd(String onlnDeliTypeCd) {
		this.onlnDeliTypeCd = onlnDeliTypeCd;
	}

}
