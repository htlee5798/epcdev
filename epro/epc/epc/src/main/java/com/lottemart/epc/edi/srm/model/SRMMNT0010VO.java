package com.lottemart.epc.edi.srm.model;

import com.lottemart.epc.edi.comm.model.PagingVO;

import java.io.Serializable;

public class SRMMNT0010VO extends PagingVO implements Serializable {
	
	private static final long serialVersionUID = -9182078691197569401L;

	private String irsNo;
	private String email;
	private String authMaxCnt;
	private String authCnt;
	private String authCd;
	private String ipAddress;

	public String getIrsNo() {
		return irsNo;
	}

	public void setIrsNo(String irsNo) {
		this.irsNo = irsNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAuthMaxCnt() {
		return authMaxCnt;
	}

	public void setAuthMaxCnt(String authMaxCnt) {
		this.authMaxCnt = authMaxCnt;
	}

	public String getAuthCd() {
		return authCd;
	}

	public void setAuthCd(String authCd) {
		this.authCd = authCd;
	}

	public String getAuthCnt() {
		return authCnt;
	}

	public void setAuthCnt(String authCnt) {
		this.authCnt = authCnt;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
}
