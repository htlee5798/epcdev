package com.lottemart.epc.edi.srm.model;

import java.io.Serializable;

public class SRMRST002001VO implements Serializable {

	private static final long serialVersionUID = -897615395126813700L;

	/** Locale */
	private String locale;
	/** 하우스코드 */
	private String houseCode;
	/** 업체코드 */
	private String sellerCode;
	/** 순번 */
	private String seq;
	/** 상담예정일 */
	private String expectedDate;
	/** 상담예정시간 */
	private String expectedTime;
	/** 장소 */
	private String expectedPlace;
	/** 요청내용 */
	private String expectedRemark;
	/** 입점거절사유 */
	private String rejectReason;
	/** 요청첨부 */
	private String attachNo;
	/** 상태 */
	private String counselStatus;
	private String counselStatusName;
	/** 수정일자 */
	private String changeDate;
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

	public String getExpectedDate() {
		return expectedDate;
	}

	public void setExpectedDate(String expectedDate) {
		this.expectedDate = expectedDate;
	}

	public String getExpectedTime() {
		return expectedTime;
	}

	public void setExpectedTime(String expectedTime) {
		this.expectedTime = expectedTime;
	}

	public String getExpectedPlace() {
		return expectedPlace;
	}

	public void setExpectedPlace(String expectedPlace) {
		this.expectedPlace = expectedPlace;
	}

	public String getExpectedRemark() {
		return expectedRemark;
	}

	public void setExpectedRemark(String expectedRemark) {
		this.expectedRemark = expectedRemark;
	}

	public String getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	public String getAttachNo() {
		return attachNo;
	}

	public void setAttachNo(String attachNo) {
		this.attachNo = attachNo;
	}

	public String getCounselStatus() {
		return counselStatus;
	}

	public void setCounselStatus(String counselStatus) {
		this.counselStatus = counselStatus;
	}

	public String getChangeDate() {
		return changeDate;
	}

	public void setChangeDate(String changeDate) {
		this.changeDate = changeDate;
	}

	public String getOwnerMd() {
		return ownerMd;
	}

	public void setOwnerMd(String ownerMd) {
		this.ownerMd = ownerMd;
	}

	public String getCounselStatusName() {
		return counselStatusName;
	}

	public void setCounselStatusName(String counselStatusName) {
		this.counselStatusName = counselStatusName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}
