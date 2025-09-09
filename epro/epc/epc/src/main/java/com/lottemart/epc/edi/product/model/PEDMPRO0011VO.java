package com.lottemart.epc.edi.product.model;

import java.io.Serializable;

public class PEDMPRO0011VO implements Serializable {

	private static final long serialVersionUID = -1116743453133616024L;
	
	private String pgmId = "";
	private String seq = "";
	private String searchKywrd = "";
	private String keyCount = "";
	private String byteChk = "";
	private String regId = "";
	private String totalKywrd = "";
	private String num = "";
	
	public String getPgmId() {
		return pgmId;
	}
	public void setPgmId(String pgmId) {
		this.pgmId = pgmId;
	}
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getSearchKywrd() {
		return searchKywrd;
	}
	public void setSearchKywrd(String searchKywrd) {
		this.searchKywrd = searchKywrd;
	}
	public String getKeyCount() {
		return keyCount;
	}
	public void setKeyCount(String keyCount) {
		this.keyCount = keyCount;
	}
	public String getByteChk() {
		return byteChk;
	}
	public void setByteChk(String byteChk) {
		this.byteChk = byteChk;
	}
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	public String getTotalKywrd() {
		return totalKywrd;
	}
	public void setTotalKywrd(String totalKywrd) {
		this.totalKywrd = totalKywrd;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	
}
