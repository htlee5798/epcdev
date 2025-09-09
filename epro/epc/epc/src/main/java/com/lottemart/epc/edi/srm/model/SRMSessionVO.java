package com.lottemart.epc.edi.srm.model;

import java.io.Serializable;

public class SRMSessionVO implements Serializable {

	static final long serialVersionUID = -254238134556327798L;
	
	/** 하우스코드 */
	private String houseCode;
	/** 해외업체구분 */
	private String shipperType;
	/** 사업자등록번호 */
	private String irsNo;
	/***/
	private String reqSeq;
	/** 상호명 */
	private String sellerNameLoc;
	/** 업체코드 */
	private String sellerCode;
	/** 국가코드 */
	private String country;
	/** 평가업체 사업자번호 */
	private String evalSellerCode;
	/** 평가업체 ID */
	private String evUserId;
	
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
	public String getIrsNo() {
		return irsNo;
	}
	public void setIrsNo(String irsNo) {
		this.irsNo = irsNo;
	}
	public String getSellerNameLoc() {
		return sellerNameLoc;
	}
	public void setSellerNameLoc(String sellerNameLoc) {
		this.sellerNameLoc = sellerNameLoc;
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
	public String getReqSeq() {
		return reqSeq;
	}
	public void setReqSeq(String reqSeq) {
		this.reqSeq = reqSeq;
	}
	public String getEvalSellerCode() {
		return evalSellerCode;
	}
	public void setEvalSellerCode(String evalSellerCode) {
		this.evalSellerCode = evalSellerCode;
	}
	public String getEvUserId() {
		return evUserId;
	}
	public void setEvUserId(String evUserId) {
		this.evUserId = evUserId;
	}
	
}
