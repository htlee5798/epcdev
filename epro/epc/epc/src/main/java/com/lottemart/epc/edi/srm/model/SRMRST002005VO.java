package com.lottemart.epc.edi.srm.model;

import java.io.Serializable;

public class SRMRST002005VO implements Serializable {
	
	private static final long serialVersionUID = -897615395126813700L;

	/** 하우스코드 */
	private String houseCode;
	/**업체코드*/
	private String evalSellerCode;
	/***/
	private String evNo;
	/***/
	private String seq;


	/**평가기관명*/
	private String sellerNameLoc;
	/**담당자명*/
	private String evCtrlName;
	/**대표전화*/
	private String evCtrlPhone;
	
	/** 01 일반잠재업체,02 외부평가업체*/
	private String inOutKind;
	
	/**입점신청순서**/
	private String reqSeq;	
	/**입청신청업체코드**/
	private String sellerCode;
	
	/** 담당 MD 메일주소**/
	private String name;
	/** 담당 MD 메일주소**/
	private String email;
	/** 담당 MD 전화번호**/
	private String mobile;
	
	private String locale;

	private String checkDate;
	private String checkTime;

	public String getHouseCode() {
		return houseCode;
	}

	public void setHouseCode(String houseCode) {
		this.houseCode = houseCode;
	}

	public String getEvalSellerCode() {
		return evalSellerCode;
	}

	public void setEvalSellerCode(String evalSellerCode) {
		this.evalSellerCode = evalSellerCode;
	}

	public String getEvNo() {
		return evNo;
	}

	public void setEvNo(String evNo) {
		this.evNo = evNo;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getSellerNameLoc() {
		return sellerNameLoc;
	}

	public void setSellerNameLoc(String sellerNameLoc) {
		this.sellerNameLoc = sellerNameLoc;
	}

	public String getEvCtrlName() {
		return evCtrlName;
	}

	public void setEvCtrlName(String evCtrlName) {
		this.evCtrlName = evCtrlName;
	}

	public String getEvCtrlPhone() {
		return evCtrlPhone;
	}

	public void setEvCtrlPhone(String evCtrlPhone) {
		this.evCtrlPhone = evCtrlPhone;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(String checkTime) {
		this.checkTime = checkTime;
	}

	public String getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}

	public String getInOutKind() {
		return inOutKind;
	}

	public void setInOutKind(String inOutKind) {
		this.inOutKind = inOutKind;
	}

	public String getSellerCode() {
		return sellerCode;
	}

	public void setSellerCode(String sellerCode) {
		this.sellerCode = sellerCode;
	}

	public String getReqSeq() {
		return reqSeq;
	}

	public void setReqSeq(String reqSeq) {
		this.reqSeq = reqSeq;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
}
