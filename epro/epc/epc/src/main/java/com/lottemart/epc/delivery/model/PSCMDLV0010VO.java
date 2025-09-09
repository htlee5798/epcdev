package com.lottemart.epc.delivery.model;

import java.io.Serializable;

public class PSCMDLV0010VO implements Serializable {

	/**
	 * 클러스터 시스템에서의 직력화 아이디 
	 */
	private static final long serialVersionUID = 2767297035426102659L;

	/** 조회시작일 */
    private String startDate = "";
    
    /** 조회종료일 */
    private String endDate = "";
    
    /** 협력업체코드 */
    private String vendorId;
    
    /** 협력업체코드 */
    private String prodCd;

    /** 협력업체코드 */
    private String prodNm;
    
    /** 협력업체코드 */
    private String penaltyCnt;
    
    /** 협력업체코드 */
    private String soutYn;
    
    /** 협력업체코드 */
    private String dispYn;
    
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

	public String getProdCd() {
		return prodCd;
	}

	public void setProdCd(String prodCd) {
		this.prodCd = prodCd;
	}

	public String getProdNm() {
		return prodNm;
	}

	public void setProdNm(String prodNm) {
		this.prodNm = prodNm;
	}

	public String getPenaltyCnt() {
		return penaltyCnt;
	}

	public void setPenaltyCnt(String penaltyCnt) {
		this.penaltyCnt = penaltyCnt;
	}

	public String getSoutYn() {
		return soutYn;
	}

	public void setSoutYn(String soutYn) {
		this.soutYn = soutYn;
	}

	public String getDispYn() {
		return dispYn;
	}

	public void setDispYn(String dispYn) {
		this.dispYn = dispYn;
	}
}
