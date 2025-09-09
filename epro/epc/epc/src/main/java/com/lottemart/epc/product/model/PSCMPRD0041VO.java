package com.lottemart.epc.product.model;

import java.io.Serializable;

public class PSCMPRD0041VO implements Serializable {
	private static final long serialVersionUID = 1L;
	// 공지번호
	private int num;
	// SEQ
	private String announSeq;
	// 거래처 ID
	private String vendorId;
	// 제목
	private String title;
	// 공지시작일자
	private String announStartDy;
	// 공지종료일자
	private String announEndDy;
	// 공지시작시간
	private String startTime;
	// 공지종료시간
	private String endTime;
	// PC 내용
	private String pcContent;
	// 모바일 내용
	private String moblContent;
	// 사용여부
	private String useYn;
	// 승인여부
	private String apryYn;
	// 승인일시
	private String apryDate;
	// 승인자
	private String apryId;
	// 등록자
	private String regId;
	// 등록일시
	private String regDate;
	// 수정자
	private String modId;
	// 수정일시
	private String modDate;
	// ec연동번호
	private String ecNotiNo;
	// 사용중지여부
	private String useFlag;

	public String getAnnounSeq() {
		return announSeq;
	}

	public void setAnnounSeq(String announSeq) {
		this.announSeq = announSeq;
	}

	public String getVendorId() {
		return vendorId;
	}

	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAnnounStartDy() {
		return announStartDy;
	}

	public void setAnnounStartDy(String announStartDy) {
		this.announStartDy = announStartDy;
	}

	public String getAnnounEndDy() {
		return announEndDy;
	}

	public void setAnnounEndDy(String announEndDy) {
		this.announEndDy = announEndDy;
	}

	public String getPcContent() {
		return pcContent;
	}

	public void setPcContent(String pcContent) {
		this.pcContent = pcContent;
	}

	public String getMoblContent() {
		return moblContent;
	}

	public void setMoblContent(String moblContent) {
		this.moblContent = moblContent;
	}

	public String getUseYn() {
		return useYn;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}

	public String getApryYn() {
		return apryYn;
	}

	public void setApryYn(String apryYn) {
		this.apryYn = apryYn;
	}

	public String getApryDate() {
		return apryDate;
	}

	public void setApryDate(String apryDate) {
		this.apryDate = apryDate;
	}

	public String getApryId() {
		return apryId;
	}

	public void setApryId(String apryId) {
		this.apryId = apryId;
	}

	public String getRegId() {
		return regId;
	}

	public void setRegId(String regId) {
		this.regId = regId;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public String getModId() {
		return modId;
	}

	public void setModId(String modId) {
		this.modId = modId;
	}

	public String getModDate() {
		return modDate;
	}

	public void setModDate(String modDate) {
		this.modDate = modDate;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getEcNotiNo() {
		return ecNotiNo;
	}

	public void setEcNotiNo(String ecNotiNo) {
		this.ecNotiNo = ecNotiNo;
	}

	public String getUseFlag() {
		return useFlag;
	}

	public void setUseFlag(String useFlag) {
		this.useFlag = useFlag;
	}
	
}
