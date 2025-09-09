package com.lottemart.epc.edi.srm.model;

import java.io.Serializable;

public class SRMRST002002VO implements Serializable {

	private static final long serialVersionUID = -897615395126813700L;

	/** Locale */
	private String locale;
	/** 하우스코드 */
	private String houseCode;
	/** 업체코드 */
	private String sellerCode;
	/** 순번 */
	private String seq;
	/** 상담예정일자 */
	private String fairDate;
	/** 상담예정시간 */
	private String fairTime;
	/** 상담장소 */
	private String fairPlace;
	/** 요청내용 */
	private String fairRemark;
	/** 첨부 */
	private String attachNo;
	/** 거절사유 */
	private String rejectReason;
	/** 상담진행상태 */
	private String fairStatus;
	private String fairStatusName;
	/** 변경일자 */
	private String changeDate;
	/** 변경자 */
	private String changeUserId;
	/**담당MD*/
	private String ownerMd;
	/**담당MD연락처*/
	private String phone;

	public String getLocale() {
		return locale;
	}
	public void setLocale(String locale) {
		this.locale = locale;
	}
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

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getFairDate() {
		return fairDate;
	}

	public void setFairDate(String fairDate) {
		this.fairDate = fairDate;
	}

	public String getFairTime() {
		return fairTime;
	}

	public void setFairTime(String fairTime) {
		this.fairTime = fairTime;
	}

	public String getFairPlace() {
		return fairPlace;
	}

	public void setFairPlace(String fairPlace) {
		this.fairPlace = fairPlace;
	}

	public String getFairRemark() {
		return fairRemark;
	}

	public void setFairRemark(String fairRemark) {
		this.fairRemark = fairRemark;
	}

	public String getAttachNo() {
		return attachNo;
	}

	public void setAttachNo(String attachNo) {
		this.attachNo = attachNo;
	}

	public String getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	public String getFairStatus() {
		return fairStatus;
	}

	public void setFairStatus(String fairStatus) {
		this.fairStatus = fairStatus;
	}

	public String getFairStatusName() {
		return fairStatusName;
	}

	public void setFairStatusName(String fairStatusName) {
		this.fairStatusName = fairStatusName;
	}

	public String getChangeDate() {
		return changeDate;
	}

	public void setChangeDate(String changeDate) {
		this.changeDate = changeDate;
	}

	public String getChangeUserId() {
		return changeUserId;
	}

	public void setChangeUserId(String changeUserId) {
		this.changeUserId = changeUserId;
	}

	public String getOwnerMd() {
		return ownerMd;
	}

	public void setOwnerMd(String ownerMd) {
		this.ownerMd = ownerMd;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}
