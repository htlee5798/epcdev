/**
 * @prjectName  : lottemart 프로젝트   
 * @since       : 2011. 10. 19. 오후 5:13:18
 * @author      : wcpark 
 * @Copyright(c) 2000 ~ 2011 lottemart
 *  All rights reserved.
 */

package com.lottemart.epc.board.model;

import java.io.Serializable;

/**
 * @Class Name : PSCPBRD0004SearchVO.java
 * @Description : 콜센터 1:1문의 상세SearchVO 클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 19. 오후 5:13:29 wcpark
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
public class PSCPBRD0004SearchVO implements Serializable
{
	private static final long serialVersionUID = 8895835137950304898L;

	/* 처리상태코드 BOARD_PRGS_STS_CD */
	private String boardPrgsStsCd = "";
	
	/* 처리상태명칭 BOARD_PRGS_STS_CD_NM */
	private String boardPrgsStsCdNm = "";
	
	/* 회원명 MEMBER_NM */
	private String memberNm = "";
	
	/* 민원대분류코드(QA001) CLM_LGRP_CD */
	private String clmLgrpCd = "";
	
	/* 민원중분류코드(QA100~QA999) CLM_MGRP_CD */
	private String clmMgrpCd = "";
	
	/* 코드명 CD_NM */
	private String cdNm = "";
	
	/* MINOR 코드 MINOR_CD */
	private String minorCd = "";
	
	/* 코드그룹ID MAJOR_CD */
	private String majorCd = "";
	
	/* 접수위치(QA005) ACCEPT_LOCA_CD */
	private String acceptLocaCd = "";
	
	/* 회원로그인아이디 MEMBER_LOGIN_ID */
	private String memberLoginId = "";
	
	/* 회원번호 MEMBER_NO */
	private String memberNo="";
	
	/* EMAIL */
	private String email = "";
	
	/* 휴대폰번호 CELL_NO */
	private String cellNo = "";
	
	/* 제목 TITLE */
	private String title = "";
	
	/* 내용 CONTENT */
	private String content = "";
	
	/* 상담순번 COUNSEL_SEQ */
	private String counselSeq = "";
	
	/* 상담순번 COUNSEL_SEQ */
	private String transferSeq = "";	
	
	/* 주문번호 ORDER_ID */
	private String orderId = "";
	
	/* 답변이메일수신여부 ANS_EMAIL_RECV_YN */
	private String ansEmailRecvYn = "";
	
	/* 로그인ID LOGIN_ID */
	private String loginId = "";
	
	/* 관리자ID ADMIN_ID */
	private String adminId = "";
	
	/* 접수자ID ACCEPT_ID */
	private String acceptId = "";
	
	/* 관리자명 ADMIN_NM */
	private String adminNm = "";
	
	/* 답변책임자 ID ANS_MNGR_ID */
	private String ansMngrId = "";
	
	/* 삭제여부 DEL_YN */
	private String delYn = "";
	
	/* 공개여부 PBL_YN */
	private String pblYn = "";
	
	/* 질문대상코드점포코드 QSTN_OBJECT_CD_STR_CD */
	private String qstnObjectCdStrCd = "";
	
	/* 점포코드 STR_NM */
	private String strNm = "";

	/* 상품코드 PROD_CD */
	private String prodCd = "";
	
	/* 글종류코드(QA003) SCRP_KIND_CD */
	private String scrpKindCd = "";
	
	/* 고객문의 CLM_LGRP_NM */
	private String clmLgrpNm = "";
	
	/* 메모내용(업체) */
	private String memoTC = "";

	/* 메모내용(상담원) */
	private String memoTCT = "";

	/* 답변제목 REPLY_TITLE */
	private String replyTitle = "";
	
	/* 답변내용 REPLY */
	private String reply = "";
	
	/* 답글 ANSWER */
	private String answer = "";
	
	/* 등록일시 REG_DATE */
	private String regDate = "";

	/* 등록자 REG_DATE */
	private String regId;
	
	/* 수정일시 REG_DATE */
	private String modDate;
	
	/* 수정자 REG_DATE */
	private String modId;
	
	public String getBoardPrgsStsCd() {
		return boardPrgsStsCd;
	}

	public void setBoardPrgsStsCd(String boardPrgsStsCd) {
		this.boardPrgsStsCd = boardPrgsStsCd;
	}
	
	public String getBoardPrgsStsCdNm() {
		return boardPrgsStsCdNm;
	}
	
	public void setBoardPrgsStsCdNm(String boardPrgsStsCdNm) {
		this.boardPrgsStsCdNm = boardPrgsStsCdNm;
	}
	
	public String getMemberNm() {
		return memberNm;
	}
	
	public void setMemberNm(String memberNm) {
		this.memberNm = memberNm;
	}

	public String getClmLgrpCd() {
		return clmLgrpCd;
	}

	public void setClmLgrpCd(String clmLgrpCd) {
		this.clmLgrpCd = clmLgrpCd;
	}

	public String getClmMgrpCd() {
		return clmMgrpCd;
	}

	public void setClmMgrpCd(String clmMgrpCd) {
		this.clmMgrpCd = clmMgrpCd;
	}

	public String getCdNm() {
		return cdNm;
	}

	public void setCdNm(String cdNm) {
		this.cdNm = cdNm;
	}

	public String getMinorCd() {
		return minorCd;
	}

	public void setMinorCd(String minorCd) {
		this.minorCd = minorCd;
	}

	public String getMajorCd() {
		return majorCd;
	}

	public void setMajorCd(String majorCd) {
		this.majorCd = majorCd;
	}

	public String getAcceptLocaCd() {
		return acceptLocaCd;
	}

	public void setAcceptLocaCd(String acceptLocaCd) {
		this.acceptLocaCd = acceptLocaCd;
	}

	public String getMemberLoginId() {
		return memberLoginId;
	}

	public void setMemberLoginId(String memberLoginId) {
		this.memberLoginId = memberLoginId;
	}

	public String getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCellNo() {
		return cellNo;
	}

	public void setCellNo(String cellNo) {
		this.cellNo = cellNo;
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


	public String getCounselSeq() {
		return counselSeq;
	}

	public void setCounselSeq(String counselSeq) {
		this.counselSeq = counselSeq;
	}
	
	public String gettTransferSeq() {
		return transferSeq;
	}

	public void setTransferSeq(String transferSeq) {
		this.transferSeq = transferSeq;
	}	
	
	
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getAnsEmailRecvYn() {
		return ansEmailRecvYn;
	}

	public void setAnsEmailRecvYn(String ansEmailRecvYn) {
		this.ansEmailRecvYn = ansEmailRecvYn;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getAdminId() {
		return adminId;
	}

	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

	public String getAcceptId() {
		return acceptId;
	}

	public void setAcceptId(String acceptId) {
		this.acceptId = acceptId;
	}

	public String getAdminNm() {
		return adminNm;
	}

	public void setAdminNm(String adminNm) {
		this.adminNm = adminNm;
	}

	public String getAnsMngrId() {
		return ansMngrId;
	}

	public void setAnsMngrId(String ansMngrId) {
		this.ansMngrId = ansMngrId;
	}

	public String getDelYn() {
		return delYn;
	}

	public void setDelYn(String delYn) {
		this.delYn = delYn;
	}

	public String getPblYn() {
		return pblYn;
	}

	public void setPblYn(String pblYn) {
		this.pblYn = pblYn;
	}

	public String getQstnObjectCdStrCd() {
		return qstnObjectCdStrCd;
	}

	public void setQstnObjectCdStrCd(String qstnObjectCdStrCd) {
		this.qstnObjectCdStrCd = qstnObjectCdStrCd;
	}

	public String getStrNm() {
		return strNm;
	}

	public void setStrNm(String strNm) {
		this.strNm = strNm;
	}

	public String getProdCd() {
		return prodCd;
	}

	public void setProdCd(String prodCd) {
		this.prodCd = prodCd;
	}

	public String getScrpKindCd() {
		return scrpKindCd;
	}

	public void setScrpKindCd(String scrpKindCd) {
		this.scrpKindCd = scrpKindCd;
	}

	public String getClmLgrpNm() {
		return clmLgrpNm;
	}

	public void setClmLgrpNm(String clmLgrpNm) {
		this.clmLgrpNm = clmLgrpNm;
	}

	public String getMemoTC() {
		return memoTC;
	}

	public void setMemoTC(String memoTC) {
		this.memoTC = memoTC;
	}

	public String getMemoTCT() {
		return memoTCT;
	}

	public void setMemoTCT(String memoTCT) {
		this.memoTCT = memoTCT;
	}

	public String getReplyTitle() {
		return replyTitle;
	}

	public void setReplyTitle(String replyTitle) {
		this.replyTitle = replyTitle;
	}

	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public String getRegId() {
		return regId;
	}

	public void setRegId(String regId) {
		this.regId = regId;
	}

	public String getModDate() {
		return modDate;
	}

	public void setModDate(String modDate) {
		this.modDate = modDate;
	}

	public String getModId() {
		return modId;
	}

	public void setModId(String modId) {
		this.modId = modId;
	}

}
