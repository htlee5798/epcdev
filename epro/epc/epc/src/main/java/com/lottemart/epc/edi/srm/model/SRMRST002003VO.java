package com.lottemart.epc.edi.srm.model;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

public class SRMRST002003VO implements Serializable {
	
	private static final long serialVersionUID = -897615395126813700L;

	/** 하우스코드 */
	private String houseCode;
	/** 업체코드 */
	private String sellerCode;
	/** 순번 */
	private String seq;
	/** 요청일자 */
	private String insReqDate;
	/** 요청내용 */
	private String insReqRemark;
	/** 상태 */
	private String reqStatus;
	/** 접수(등록일자) */
	private String insReceiptDate;
	/** 보증보험사명 */
	private String insCompanyName;
	/** 보증보험번호 */
	private String insNo;
	/** 보증보험서 첨부 */
	private String insAttachNo;
	private String insAttachNoName;
	private MultipartFile insAttachNoFile;

	private String tempYn;

    private String deptCd;
	private String userNameLoc;
	private String email;
    private String sellerNameLoc;

    private String locale;

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

	public String getInsReqDate() {
		return insReqDate;
	}

	public void setInsReqDate(String insReqDate) {
		this.insReqDate = insReqDate;
	}

	public String getInsReqRemark() {
		return insReqRemark;
	}

	public void setInsReqRemark(String insReqRemark) {
		this.insReqRemark = insReqRemark;
	}

	public String getReqStatus() {
		return reqStatus;
	}

	public void setReqStatus(String reqStatus) {
		this.reqStatus = reqStatus;
	}

	public String getInsReceiptDate() {
		return insReceiptDate;
	}

	public void setInsReceiptDate(String insReceiptDate) {
		this.insReceiptDate = insReceiptDate;
	}

	public String getInsCompanyName() {
		return insCompanyName;
	}

	public void setInsCompanyName(String insCompanyName) {
		this.insCompanyName = insCompanyName;
	}

	public String getInsNo() {
		return insNo;
	}

	public void setInsNo(String insNo) {
		this.insNo = insNo;
	}

	public String getInsAttachNo() {
		return insAttachNo;
	}

	public void setInsAttachNo(String insAttachNo) {
		this.insAttachNo = insAttachNo;
	}

	public String getInsAttachNoName() {
		return insAttachNoName;
	}

	public void setInsAttachNoName(String insAttachNoName) {
		this.insAttachNoName = insAttachNoName;
	}

	public MultipartFile getInsAttachNoFile() {
		return insAttachNoFile;
	}

	public void setInsAttachNoFile(MultipartFile insAttachNoFile) {
		this.insAttachNoFile = insAttachNoFile;
	}

	public String getTempYn() {
		return tempYn;
	}

	public void setTempYn(String tempYn) {
		this.tempYn = tempYn;
	}

	public String getUserNameLoc() {
		return userNameLoc;
	}

	public void setUserNameLoc(String userNameLoc) {
		this.userNameLoc = userNameLoc;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getDeptCd() {
        return deptCd;
    }

    public void setDeptCd(String deptCd) {
        this.deptCd = deptCd;
    }

    public String getSellerNameLoc() {
        return sellerNameLoc;
    }

    public void setSellerNameLoc(String sellerNameLoc) {
        this.sellerNameLoc = sellerNameLoc;
    }
}
