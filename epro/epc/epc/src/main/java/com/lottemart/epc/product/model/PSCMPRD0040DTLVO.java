package com.lottemart.epc.product.model;

import java.io.Serializable;

public class PSCMPRD0040DTLVO implements Serializable {
	private static final long serialVersionUID = 1L;
	// 넘버
	private int num;
	//적용대상명
	private String applyToName="";
	// MD 톡 순서
	private String mdTalkSeq = "";
	// MD 톡상세 순번
	private String mdTalkDtlSeq = "";
	// 적용대상 유형 코드
	private String applyToTypeCd = "";
	// 적용대상 코드
	private String applyToCd = "";
	// 등록자
	private String regId = "";
	// 등록일자
	private String regDate = "";
	// 등록 일시
	private String modId = "";
	// 수정자
	private String modDate = "";

	// 수정일시
	public String getMdTalkSeq() {
		return mdTalkSeq;
	}

	public void setMdTalkSeq(String mdTalkSeq) {
		this.mdTalkSeq = mdTalkSeq;
	}

	public String getMdTalkDtlSeq() {
		return mdTalkDtlSeq;
	}

	public void setMdTalkDtlSeq(String mdTalkDtlSeq) {
		this.mdTalkDtlSeq = mdTalkDtlSeq;
	}

	public String getApplyToTypeCd() {
		return applyToTypeCd;
	}

	public void setApplyToTypeCd(String applyToTypeCd) {
		this.applyToTypeCd = applyToTypeCd;
	}

	public String getApplyToCd() {
		return applyToCd;
	}

	public void setApplyToCd(String applyToCd) {
		this.applyToCd = applyToCd;
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

	public String getApplyToName() {
		return applyToName;
	}

	public void setApplyToName(String applyToName) {
		this.applyToName = applyToName;
	}

}
