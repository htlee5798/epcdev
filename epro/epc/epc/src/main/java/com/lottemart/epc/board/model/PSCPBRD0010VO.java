package com.lottemart.epc.board.model;

import java.io.Serializable;

public class PSCPBRD0010VO implements Serializable {

	/**
	 * 클러스터 시스템에서의 직력화 아이디 
	 */
	private static final long serialVersionUID = 8778645137849717613L;

	/** 조회시작일 */
    private String startDate = "";
    
    /** 조회종료일 */
    private String endDate = "";
    
    /** 조회시작일 */
    private String fromDate = "";
    
    /** 조회종료일 */
    private String toDate = "";
    
    private String vendorMode = "";    
    
    /** 업체코드 */
    private String[] vendorId;
    
	public String[] getVendorId() {
		return vendorId;
	}

	public void setVendorId(String[] vendorId) {
		this.vendorId = vendorId;
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

	public String getVendorMode() {
		return vendorMode;
	}

	public void setVendorMode(String vendorMode) {
		this.vendorMode = vendorMode;
	}

    
}
