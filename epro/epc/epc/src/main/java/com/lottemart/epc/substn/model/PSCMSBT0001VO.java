package com.lottemart.epc.substn.model;

import java.util.List;

import xlib.cmc.GridHeader;

/**
 *  
 * @Class Name : PBOMPRD0099VO
 * @Description :
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자	   수정내용
 *  -------    --------    ---------------------------
 * 2015. 11. 25   jib 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
public class PSCMSBT0001VO{  


	private String accotYm = "";
	private String seq = "";
	private String substnTypeCd = "";	
	private String strCd = "";	
	private String vendorCd = "";
	private String catCd = "";
	private String splyPrcCurr;
	private String evtTermType;
	private String splyPrc;
	private String vat;
	private String comShch;	
	private String cardcoShch;
	private String billIssueYn;
	private String adjReson;
	private String regId;
	private String modId;
	private String cfmPsn;
	private String cfmStatus;
	private String cfmDate;
	private String sendYn;
	
    /** 협력사코드 */
    private List<String> vendorId;
    
    /** 선택 협력사코드 */
    private String searchVendorId;
    
	public String getAccotYm() {
		return accotYm;
	}
	public void setAccotYm(String accotYm) {
		this.accotYm = accotYm;
	}
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getSubstnTypeCd() {
		return substnTypeCd;
	}
	public void setSubstnTypeCd(String substnTypeCd) {
		this.substnTypeCd = substnTypeCd;
	}
	public String getStrCd() {
		return strCd;
	}
	public void setStrCd(String strCd) {
		this.strCd = strCd;
	}
	public String getVendorCd() {
		return vendorCd;
	}
	public void setVendorCd(String vendorCd) {
		this.vendorCd = vendorCd;
	}
	public String getCatCd() {
		return catCd;
	}
	public void setCatCd(String catCd) {
		this.catCd = catCd;
	}
	public String getModId() {
		return modId;
	}
	public void setModId(String modId) {
		this.modId = modId;
	}
	public String getCfmPsn() {
		return cfmPsn;
	}
	public void setCfmPsn(String cfmPsn) {
		this.cfmPsn = cfmPsn;
	}
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	public String getSplyPrcCurr() {
		return splyPrcCurr;
	}
	public void setSplyPrcCurr(String splyPrcCurr) {
		this.splyPrcCurr = splyPrcCurr;
	}
	public String getSplyPrc() {
		return splyPrc;
	}
	public void setSplyPrc(String splyPrc) {
		this.splyPrc = splyPrc;
	}
	public String getVat() {
		return vat;
	}
	public void setVat(String vat) {
		this.vat = vat;
	}
	public String getAdjReson() {
		return adjReson;
	}
	public void setAdjReson(String adjReson) {
		this.adjReson = adjReson;
	}

	public String getCfmDate() {
		return cfmDate;
	}
	public void setCfmDate(String cfmDate) {
		this.cfmDate = cfmDate;
	}
	public String getSendYn() {
		return sendYn;
	}
	public void setSendYn(String sendYn) {
		this.sendYn = sendYn;
	}
	public String getBillIssueYn() {
		return billIssueYn;
	}
	public void setBillIssueYn(String billIssueYn) {
		this.billIssueYn = billIssueYn;
	}
	public String getComShch() {
		return comShch;
	}
	public void setComShch(String comShch) {
		this.comShch = comShch;
	}
	public String getCfmStatus() {
		return cfmStatus;
	}
	public void setCfmStatus(String cfmStatus) {
		this.cfmStatus = cfmStatus;
	}
	public String getEvtTermType() {
		return evtTermType;
	}
	public void setEvtTermType(String evtTermType) {
		this.evtTermType = evtTermType;
	}
	public String getCardcoShch() {
		return cardcoShch;
	}
	public void setCardcoShch(String cardcoShch) {
		this.cardcoShch = cardcoShch;
	}
	public List<String> getVendorId() {
		return vendorId;
	}
	public void setVendorId(List<String> vendorId) {
		this.vendorId = vendorId;
	}
	public String getSearchVendorId() {
		return searchVendorId;
	}
	public void setSearchVendorId(String searchVendorId) {
		this.searchVendorId = searchVendorId;
	}
	
}