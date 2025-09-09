package com.lottemart.epc.delivery.model;

import java.io.Serializable;

public class PSCMDLV0011VO implements Serializable {

	/**
	 * 클러스터 시스템에서의 직력화 아이디 
	 */
	private static final long serialVersionUID = 4103966355223031481L;

	/** 조회시작일 */
    private String startDate = "";
    
    /** 조회종료일 */
    private String endDate = "";
    
    /** 협력업체코드 */
    private String vendorId;

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

	public String getVendorId() {
		return vendorId;
	}

	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}
    
}
