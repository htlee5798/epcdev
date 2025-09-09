package com.lottemart.epc.edi.srm.model;

import java.io.Serializable;

public class SRMRST0010VO implements Serializable {

	private static final long serialVersionUID = -307775383584548514L;
	
	/** 하우스코드 */
	private String houseCode;
	/** 해외업체구분 */
	private String shipperType;
	/** 사업자명 */
	private String sellerNameLoc;
	/** 사업자등록번호 */
	private String irsNo;
	/** 업체코드 */
	private String sellerCode;
	/** 국가코드 */
	private String country;
	/** 비밀번호 */
	private String tempPw;
	/** 비밀번호 오류 횟수 카운트 컬럼 */
	private int passCheckCnt;
	
	public String getHouseCode() {
		return houseCode;
	}
	public void setHouseCode(String houseCode) {
		this.houseCode = houseCode;
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
	public String getSellerCode() {
		return sellerCode;
	}
	public void setSellerCode(String sellerCode) {
		this.sellerCode = sellerCode;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getTempPw() {
		return tempPw;
	}
	public void setTempPw(String tempPw) {
		this.tempPw = tempPw;
	}
	public int getPassCheckCnt() {
		return passCheckCnt;
	}
	public void setPassCheckCnt(int passCheckCnt) {
		this.passCheckCnt = passCheckCnt;
	}
	
}
