package com.lottemart.epc.edi.srm.model;

import java.io.Serializable;

public class SRMJON0010VO implements Serializable {

	static final long serialVersionUID = -254238134556327798L;
	
	/** 하우스코드 */
	private String houseCode;
	/** 업체코드 */
	private String sellerCode;
	/** 해외업체구분 */
	private String shipperType;
	/** 상호명 */
	private String sellerNameLoc;
	/** 사업자등록번호 */
	private String irsNo;
	/** 순번 */
	private String reqSeq;
	/** 임시비밀번호 */
	private String tempPw;
	/** 국가 */
	private String country;
	/** IP주소 */
	private String ipAddress;
	/** 약관동의 파일 버전 */
	private String agreeFileVer;
	/** 동의 유형 */
	private String agreeType;
	
	public String getHouseCode() {
		return houseCode;
	}
	public void setHouseCode(String houseCode) {
		this.houseCode = houseCode;
	}
	public String getSellerCode() {
		return sellerCode;
	}
	public void setSellerCode(String sellerCode) {
		this.sellerCode = sellerCode;
	}
	public String getShipperType() {
		return shipperType;
	}
	public void setShipperType(String shipperType) {
		this.shipperType = shipperType;
	}
	public String getSellerNameLoc() {
		return sellerNameLoc;
	}
	public void setSellerNameLoc(String sellerNameLoc) {
		this.sellerNameLoc = sellerNameLoc;
	}
	public String getIrsNo() {
		return irsNo;
	}
	public void setIrsNo(String irsNo) {
		this.irsNo = irsNo;
	}
	public String getReqSeq() {
		return reqSeq;
	}
	public void setReqSeq(String reqSeq) {
		this.reqSeq = reqSeq;
	}
	public String getTempPw() {
		return tempPw;
	}
	public void setTempPw(String tempPw) {
		this.tempPw = tempPw;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getAgreeFileVer() {
		return agreeFileVer;
	}
	public void setAgreeFileVer(String agreeFileVer) {
		this.agreeFileVer = agreeFileVer;
	}
	public String getAgreeType() {
		return agreeType;
	}
	public void setAgreeType(String agreeType) {
		this.agreeType = agreeType;
	}
	
}
