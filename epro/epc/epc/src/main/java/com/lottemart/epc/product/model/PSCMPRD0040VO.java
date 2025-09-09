package com.lottemart.epc.product.model;

import java.io.Serializable;

/**
 * @Class Name : PSCMPRD0040VO.java
 * @Description : 상품관리 - 상품정보관리 - 셀링포인트관리
 * @Modification Information
 * 
 *               <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2016. 5. 11. 오후 3:25:11 choi
 * 
 * @Copyright (C) 2000 ~ 2011 롯데정보통신(주) All right reserved.
 * </pre>
 */
public class PSCMPRD0040VO implements Serializable {
	private static final long serialVersionUID = -2469343349532012383L;
	// 게시판 순번
	private String num = "";
	// MD톡순번
	private String mdTalkSeq = "";
	// 제목
	private String title = "";
	// 내용
	private String content = "";
	// 공지시작일자
	private String announStartDy = "";
	// 공지종료일자
	private String announEndDy = "";
	// 등록구분
	private String regDivnCd = "";
	// 사용여부
	private String useYn = "";
	// 승인여부
	private String apryYn = "";
	// 승인일시
	private String apryDate = "";
	// 승인자
	private String apryId = "";
	// 등록자
	private String regId = "";
	// 등록일시
	private String regDate = "";
	// 수정자
	private String modId = "";
	// 수정일시
	private String modDate = "";

	public String getMdTalkSeq() {
		return mdTalkSeq;
	}

	public void setMdTalkSeq(String mdTalkSeq) {
		this.mdTalkSeq = mdTalkSeq;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public String getRegDivnCd() {
		return regDivnCd;
	}

	public void setRegDivnCd(String regDivnCd) {
		this.regDivnCd = regDivnCd;
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

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}
}
