package com.lottemart.epc.edi.consult.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

public class AutionItem {

	//table column mapping field
	private String 	bmanNo;
	private String 	prodSeqs;
	private String  auDy;
	private String  l1Cd;
	private String 	l1Nm;
	private String  prodNm;
	private String 	unit;
	private int 	startPrc;
	private int 	qty;
    private String detlContent;
    
	public String getBmanNo() {
		return bmanNo;
	}
	public void setBmanNo(String bmanNo) {
		this.bmanNo = bmanNo;
	}
	public String getProdSeqs() {
		return prodSeqs;
	}
	public void setProdSeqs(String prodSeqs) {
		this.prodSeqs = prodSeqs;
	}
	public String getAuDy() {
		return auDy;
	}
	public void setAuDy(String auDy) {
		this.auDy = auDy;
	}
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
	public String getProdNm() {
		return prodNm;
	}
	public void setProdNm(String prodNm) {
		this.prodNm = prodNm;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public int getStartPrc() {
		return startPrc;
	}
	public void setStartPrc(int startPrc) {
		this.startPrc = startPrc;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	public String getDetlContent() {
		return detlContent;
	}
	public void setDetlContent(String detlContent) {
		this.detlContent = detlContent;
	}
	
	
}
