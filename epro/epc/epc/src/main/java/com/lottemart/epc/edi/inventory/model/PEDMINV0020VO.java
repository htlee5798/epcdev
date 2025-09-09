package com.lottemart.epc.edi.inventory.model;

import java.io.Serializable;

public class PEDMINV0020VO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6766182773315305924L;

	public PEDMINV0020VO() {}

	
	private String sendDate;                                      
	private String prodCd;                                        
	private String prodNm;                                        
	private String occurDate; 
	private String occurdy;                                      
	private String strNm;                                         
	private String badL1Nm;                                      
	private String badCatNm;                                     
	private String procGb;                                     
	private String strCd;                                     	
	private String occurDy;                                     	
	private String sendFg;
	
	private String mdEmpNm;                                                                  
	private String mdStrNm;                                                                  
	private String srcmkCd;                                                                   
	private String venNm;                                                                     
	private String badCmt;                                                                    
	private String ediCmt;                                                                    
	private String venCmt;                                                                    
	private String imgFileNm;         
	
	private String venEmpNm;         
	
	
	
	
	public String getSendDate() {
		return sendDate;
	}
	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
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
	public String getOccurDate() {
		return occurDate;
	}
	public void setOccurDate(String occurDate) {
		this.occurDate = occurDate;
	}
	public String getOccurdy() {
		return occurdy;
	}
	public void setOccurdy(String occurdy) {
		this.occurdy = occurdy;
	}
	public String getStrNm() {
		return strNm;
	}
	public void setStrNm(String strNm) {
		this.strNm = strNm;
	}
	public String getBadL1Nm() {
		return badL1Nm;
	}
	public void setBadL1Nm(String badL1Nm) {
		this.badL1Nm = badL1Nm;
	}
	public String getBadCatNm() {
		return badCatNm;
	}
	public void setBadCatNm(String badCatNm) {
		this.badCatNm = badCatNm;
	}
	public String getProcGb() {
		return procGb;
	}
	public void setProcGb(String procGb) {
		this.procGb = procGb;
	}
	public String getStrCd() {
		return strCd;
	}
	public void setStrCd(String strCd) {
		this.strCd = strCd;
	}
	public String getOccurDy() {
		return occurDy;
	}
	public void setOccurDy(String occurDy) {
		this.occurDy = occurDy;
	}
	public String getSendFg() {
		return sendFg;
	}
	public void setSendFg(String sendFg) {
		this.sendFg = sendFg;
	}
	public String getMdEmpNm() {
		return mdEmpNm;
	}
	public void setMdEmpNm(String mdEmpNm) {
		this.mdEmpNm = mdEmpNm;
	}
	public String getMdStrNm() {
		return mdStrNm;
	}
	public void setMdStrNm(String mdStrNm) {
		this.mdStrNm = mdStrNm;
	}
	public String getSrcmkCd() {
		return srcmkCd;
	}
	public void setSrcmkCd(String srcmkCd) {
		this.srcmkCd = srcmkCd;
	}
	public String getVenNm() {
		return venNm;
	}
	public void setVenNm(String venNm) {
		this.venNm = venNm;
	}
	public String getBadCmt() {
		return badCmt;
	}
	public void setBadCmt(String badCmt) {
		this.badCmt = badCmt;
	}
	public String getEdiCmt() {
		return ediCmt;
	}
	public void setEdiCmt(String ediCmt) {
		this.ediCmt = ediCmt;
	}
	public String getVenCmt() {
		return venCmt;
	}
	public void setVenCmt(String venCmt) {
		this.venCmt = venCmt;
	}
	public String getImgFileNm() {
		return imgFileNm;
	}
	public void setImgFileNm(String imgFileNm) {
		this.imgFileNm = imgFileNm;
	}
	
	public String getVenEmpNm() {
		return venEmpNm;
	}
	public void setVenEmpNm(String venEmpNm) {
		this.venEmpNm = venEmpNm;
	}
	
	
	
	
}
