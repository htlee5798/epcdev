package com.lottemart.epc.edi.srm.model;

import java.io.Serializable;

public class SRMSPW0010VO implements Serializable {

	private static final long serialVersionUID = -897615395126813700L;

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
	/** 이전비밀번호 */
	private String oldTempPw;
	/** 번경비밀번호 */
	private String tempPw;
	/** 국가 */
	private String country;
	/** 담당자명 */
	private String userName;
	/** 이메일 */
	private String vEmail;
	/** 비밀번호 변경/찾기 구분 */
	private String pwdGbn;
	/** 최대순번 */
	private String maxSeq;

	private String oldTempPw1;

	private String oldTempPw2;

	private String isKeepPassword90;

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
	public String getOldTempPw() {
		return oldTempPw;
	}
	public void setOldTempPw(String oldTempPw) {
		this.oldTempPw = oldTempPw;
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
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getvEmail() {
		return vEmail;
	}
	public void setvEmail(String vEmail) {
		this.vEmail = vEmail;
	}
	public String getPwdGbn() {
		return pwdGbn;
	}
	public void setPwdGbn(String pwdGbn) {
		this.pwdGbn = pwdGbn;
	}
	public String getMaxSeq() {
		return maxSeq;
	}
	public void setMaxSeq(String maxSeq) {
		this.maxSeq = maxSeq;
	}
	public String getOldTempPw1() {
		return oldTempPw1;
	}
	public void setOldTempPw1(String oldTempPw1) {
		this.oldTempPw1 = oldTempPw1;
	}
	public String getOldTempPw2() {
		return oldTempPw2;
	}
	public void setOldTempPw2(String oldTempPw2) {
		this.oldTempPw2 = oldTempPw2;
	}
	public String getIsKeepPassword90() {
		return isKeepPassword90;
	}
	public void setIsKeepPassword90(String isKeepPassword90) {
		this.isKeepPassword90 = isKeepPassword90;
	}

}
