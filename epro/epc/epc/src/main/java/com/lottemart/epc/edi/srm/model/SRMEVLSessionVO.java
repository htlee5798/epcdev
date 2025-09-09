package com.lottemart.epc.edi.srm.model;

import java.io.Serializable;

public class SRMEVLSessionVO implements Serializable {

	static final long serialVersionUID = -2141820938143316371L;
	
	/** 사용자 ID */
	private String userId;
	/** 평가사 회사코드 */
	private String companyCode;

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	
}
