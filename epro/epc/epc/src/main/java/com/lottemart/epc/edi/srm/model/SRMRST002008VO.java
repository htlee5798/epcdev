package com.lottemart.epc.edi.srm.model;

import java.io.Serializable;

public class SRMRST002008VO implements Serializable {

	private static final long serialVersionUID = 6273683550342906355L;
	
	/** Locale */
	private String locale;
	/** 하우스코드 */
	private String houseCode;
	/** 업체코드 */
	private String sellerCode;
	/** 순번 */
	private String seq;
	/** 접수일자 */
	private String receiptDate;
	/** 수정일자 */
	private String changeDate;
	/** 진행상태 */
	private String processStatus;
	/** 진행상태 이름 */
	private String processStatusName;
	/** 담당MD*/
	private String ownerMd;
	/** 담당MD연락처*/
	private String phone;
	/** 입점거절사유 */
	private String rejectReason;

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

	public String getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	public String getChangeDate() {
		return changeDate;
	}

	public void setChangeDate(String changeDate) {
		this.changeDate = changeDate;
	}
	
	public String getProcessStatusName() {
		return processStatusName;
	}
	
	public void setProcessStatusName(String processStatusName) {
		this.processStatusName = processStatusName;
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
	
	public String getReceiptDate() {
		return receiptDate;
	}
	
	public void setReceiptDate(String receiptDate) {
		this.receiptDate = receiptDate;
	}
	
	public String getProcessStatus() {
		return processStatus;
	}
	
	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}
	
}
