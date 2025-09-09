package com.lottemart.epc.common.model;

import java.io.Serializable;

public class PSCMCOM0006VO implements Serializable {

	/**
	 * 클러스터 시스템에서의 직력화 아이디
	 */
	private static final long serialVersionUID = -8120634536814198185L;

	private String condition1 = "";

	private String condition2 = "";

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

	private String[] vendorId;

	/** 포함상품 조건 **/
	private String[] inVal;

	/** 제외상품 조건 **/
	private String[] notInVal;

	/** 온라인상품유형코드 **/
	private String onlineProdTypeCd;

	/** 상품승인여부 **/
	private String aprvYn;

	/** 온오프/온라인 상품구분 **/
	private String prodDivnCd;

	private String ecLinkYn;
	
	private String dealProdYn;

	public String getCondition1() {
		return condition1;
	}

	public void setCondition1(String condition1) {
		this.condition1 = condition1;
	}

	public String getCondition2() {
		return condition2;
	}

	public void setCondition2(String condition2) {
		this.condition2 = condition2;
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

	public String[] getVendorId() {
		return vendorId;
	}

	public void setVendorId(String[] vendorId) {
		this.vendorId = vendorId;
	}

	public String[] getNotInVal() {
		return notInVal;
	}

	public void setNotInVal(String[] notInVal) {
		this.notInVal = notInVal;
	}

	public String getOnlineProdTypeCd() {
		return onlineProdTypeCd;
	}

	public void setOnlineProdTypeCd(String onlineProdTypeCd) {
		this.onlineProdTypeCd = onlineProdTypeCd;
	}

	public String[] getInVal() {
		return inVal;
	}

	public void setInVal(String[] inVal) {
		this.inVal = inVal;
	}

	public String getProdDivnCd() {
		return prodDivnCd;
	}

	public void setProdDivnCd(String prodDivnCd) {
		this.prodDivnCd = prodDivnCd;
	}

	public String getAprvYn() {
		return aprvYn;
	}

	public void setAprvYn(String aprvYn) {
		this.aprvYn = aprvYn;
	}

	public String getEcLinkYn() {
		return ecLinkYn;
	}

	public void setEcLinkYn(String ecLinkYn) {
		this.ecLinkYn = ecLinkYn;
	}

	public String getDealProdYn() {
		return dealProdYn;
	}

	public void setDealProdYn(String dealProdYn) {
		this.dealProdYn = dealProdYn;
	}

}
