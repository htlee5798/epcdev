package com.lottemart.epc.edi.product.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class EdiCdListCodeVO implements Serializable {

	private static final long serialVersionUID = 6766182773315305924L;

	public EdiCdListCodeVO() {}

	private String l1Cd;
	private String l1Nm;
	
	private String l4Cd;
	private String l4Nm;

	public String getL1Cd() {
		return l1Cd;
	}
	public void setL1Cd(String l1Cd) {
		this.l1Cd = l1Cd;
	}
	public String getL1Nm() {
		return l1Nm;
	}
	public void setL1Nm(String l1Nm) {
		this.l1Nm = l1Nm;
	}
	public String getL4Cd() {
		return l4Cd;
	}
	public void setL4Cd(String l4Cd) {
		this.l4Cd = l4Cd;
	}
	public String getL4Nm() {
		return l4Nm;
	}
	public void setL4Nm(String l4Nm) {
		this.l4Nm = l4Nm;
	}
	
}
