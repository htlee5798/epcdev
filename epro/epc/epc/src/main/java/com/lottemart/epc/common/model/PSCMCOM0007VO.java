package com.lottemart.epc.common.model;

import java.io.Serializable;

public class PSCMCOM0007VO implements Serializable {

	/**
	 * 클러스터 시스템에서의 직력화 아이디 
	 */
	private static final long serialVersionUID = -8120634536814198185L;
	
	private String startDate = "";
	
	private String endDate = "";
	
	private String fromDate = "";
	
	private String toDate = "";
	
	private String vendorId;
	
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getStartDate() {
		return startDate;
	}    
	
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	public String getEndDate() {
		return endDate;
	}    

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getFromDate() {
		return fromDate;
	}    
	
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	
	public String getToDate() {
		return toDate;
	}    	
	
	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}
	
	public String getVendorId() {
		return vendorId;
	}    	
	
	
	
}
