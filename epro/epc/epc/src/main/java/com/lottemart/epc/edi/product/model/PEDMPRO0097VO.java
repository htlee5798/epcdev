package com.lottemart.epc.edi.product.model;

import java.io.Serializable;

public class PEDMPRO0097VO implements Serializable {

	private static final long serialVersionUID = -3421182773315305924L;

	public PEDMPRO0097VO() {}

	private String l1Cd;		// 대분류
	private String l1Nm;		// 대분류명
	private String l2Cd;		// 중분류
	private String l2Nm;		// 중분류명
	private String l3Cd;		// 소분류
	private String l3Nm;		// 소분류명
	private String seq;			// 순번
	private String attId;		// 속성ID
	private String attNm;		// 속성명
	private String attTypCd;	// 속성타입
	private String val;			// 속성 값
	private String attValNm;	// 속성값 명칭

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
	public String getL2Cd() {
		return l2Cd;
	}
	public void setL2Cd(String l2Cd) {
		this.l2Cd = l2Cd;
	}
	public String getL2Nm() {
		return l2Nm;
	}
	public void setL2Nm(String l2Nm) {
		this.l2Nm = l2Nm;
	}
	public String getL3Cd() {
		return l3Cd;
	}
	public void setL3Cd(String l3Cd) {
		this.l3Cd = l3Cd;
	}
	public String getL3Nm() {
		return l3Nm;
	}
	public void setL3Nm(String l3Nm) {
		this.l3Nm = l3Nm;
	}
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getAttId() {
		return attId;
	}
	public void setAttId(String attId) {
		this.attId = attId;
	}
	public String getAttNm() {
		return attNm;
	}
	public void setAttNm(String attNm) {
		this.attNm = attNm;
	}
	public String getAttTypCd() {
		return attTypCd;
	}
	public void setAttTypCd(String attTypCd) {
		this.attTypCd = attTypCd;
	}
	public String getVal() {
		return val;
	}
	public void setVal(String val) {
		this.val = val;
	}
	public String getAttValNm() {
		return attValNm;
	}
	public void setAttValNm(String attValNm) {
		this.attValNm = attValNm;
	}	
}
