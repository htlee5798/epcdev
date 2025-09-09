package com.lottemart.epc.product.model;

import java.io.Serializable;

/**
 * @Class Name : PSCMPRD0041DtlVO.java
 * @Description : 협력사공지 관리상세 VO
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2016. 6. 3. 오후 2:57:03 UNI
 * 
 * @Copyright (C) 2000 ~ 2011 롯데정보통신(주) All right reserved.
 * </pre>
 */
public class PSCMPRD0041DtlVO implements Serializable {
	private static final long serialVersionUID = -890843719174820103L;
	// 공지순번
	private String announSeq = "";
	// 공지 상세 순번
	private String announDtlSeq = "";
	// 적용대상유형코드
	private String applyToTypeCd = "";
	// 적용대상 코드
	private String applyToCd = "";
	// 적용대상 코드
	private String applyToNm = "";
	// 등록자
	private String regId = "";
	// 등록일시
	private String regDate = "";
	// 수정자
	private String modId = "";
	// 수정일시
	private String modDate = "";

	public String getAnnounSeq() {
		return announSeq;
	}

	public void setAnnounSeq(String announSeq) {
		this.announSeq = announSeq;
	}

	public String getAnnounDtlSeq() {
		return announDtlSeq;
	}

	public void setAnnounDtlSeq(String announDtlSeq) {
		this.announDtlSeq = announDtlSeq;
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

	public String getApplyToNm() {
		return applyToNm;
	}

	public void setApplyToNm(String applyToNm) {
		this.applyToNm = applyToNm;
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

}
