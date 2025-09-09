package com.lottemart.epc.board.model;


import java.io.Serializable;

/**
 * @Class Name : PSCMBRD0008SearchVO.java
 * @Description :
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 12. 16. 오후 03:03:03 mjChoi
 * 
 * @Copyright (C) 2000 ~ 2011 롯데정보통신(주) All right reserved.
 * </pre>
 */
public class PSCMBRD0008SearchVO implements Serializable 
{
	private static final long serialVersionUID = -4908470996938970475L;

	/** 기간시작일 **/
    private String startDate = "";
    
    /** 기간종료일  **/
    private String endDate = "";
    
    /** 접수위치 */
    private String agentLocation = "";
    
    /** 협력사코드 */
    private String[] vendorId;
    
    private String searchVendorId = "";
   
    private String typeCheck = "";
    
    
    /** MAJOR_CD */
    private String majorCd = "";

    /** MINOR_CD */
    private String minorCd = "";
    
    /******************** 문의목록 리스트  ***************************/
    
    /** 순번 RANK*/
    private int rank;   
    
    /** 상담순번 COUNSEL_SEQ **/
	private String counselSeq = "";
    
    /**  UP_BOARD_SEQ */
    private String upBoardSeq = "";
    
    /** UP_COUNSEL_SEQ */
    private String upCounselSeq="";   
    
    /** ORDER_ID */
    private String orderId = "";
    
    /** CUSTOMER_ID */
    private String customerId = "";
    
    /** MEMBER_NO */
    private String memberNo = "";
    
    /** MEMBER-NM */
    private String memberNm = "";
    
    /** EMAIL */
    private String email = "";
    
    /** CUST_QST_TYPE */
    private String custQstType = "";
    
    /** QST_MGRP_CD */
    private String qstMgrpCd = "";
    
    /** 고객 문의구분(대) CUST_QST_DIVN_CD */
    private String custQstDivnCd = "";
    
    /** CUST_QST_MGRP_CD */
    private String custQstMgrpCd = "";
    
    /** 문의유형(대) CLM_LGRP_CD */
    private String clmLgrpCd = "";
    
    /** 문의유형(소) CLM_MGRP_CD */
    private String clmMgrpCd = "";
    
    /** TITLE */
    private String title = "";
    
    /** BOARD_PRGS_STS_CD */
    private String boardPrgsStsCd = "";
    
    /** ACCEPT_LOCA_CD*/
    private String acceptLocaCd = "";
    
    /** LOGIN_ID */
    private String loginId = "";
    
    /** 접수자아이디  ACCEPT_ID */
    private String acceptId = "";
    
    /** 처리담당자 LOGIN_NM */
    private String loginNm = "";
    
    /** TRANSFER_REG_DATE */
    private String transferRegDate = "";
    
    /** RESPONDENT_NM */
    private String respondentNm = "";
    
    /** ANS_MNGR_NM */
    private String ansMngrNm = "";
    
    /** ANSWER_NAME */
    private String answername = "";
    
    /** ANSWER_DATE */
    private String answerDate = "";
    
    /** DEL_YN */
    private String delYn = "";
    
    /** REASON_CD */
    private String reasonCd = "";
    
    /** STATUS_OK */
    private String statusOk = "";
    
    /** STATUS_MISS */
    private String statusMiss = "";

    /** LOGIN_ADMIN_ID */
    private String loginAdminId = "";
    
    
    /** 페이지 세팅 **/
	/** 현재페이지 */
    private int pageIndex = 1;
    
    /** 페이지갯수 */
    private int pageUnit = 15;
    
    /** 페이지사이즈 */
    private int pageSize = 10;

    /** firstIndex */
    private int firstIndex = 1;

    /** lastIndex */
    private int lastIndex = 1;

    /** recordCountPerPage */
    private int recordCountPerPage = 10;
    

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageUnit() {
		return pageUnit;
	}

	public void setPageUnit(int pageUnit) {
		this.pageUnit = pageUnit;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getFirstIndex() {
		return firstIndex;
	}

	public void setFirstIndex(int firstIndex) {
		this.firstIndex = firstIndex;
	}

	public int getLastIndex() {
		return lastIndex;
	}

	public void setLastIndex(int lastIndex) {
		this.lastIndex = lastIndex;
	}

	public int getRecordCountPerPage() {
		return recordCountPerPage;
	}

	public void setRecordCountPerPage(int recordCountPerPage) {
		this.recordCountPerPage = recordCountPerPage;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String[] getVendorId() {
		return vendorId;
	}

	public void setVendorId(String[] vendorId) {
		this.vendorId = vendorId;
	}

	public String getSearchVendorId() {
		return searchVendorId;
	}

	public void setSearchVendorId(String searchVendorId) {
		this.searchVendorId = searchVendorId;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public String getCounselSeq() {
		return counselSeq;
	}

	public void setCounselSeq(String counselSeq) {
		this.counselSeq = counselSeq;
	}

	public String getUpBoardSeq() {
		return upBoardSeq;
	}

	public void setUpBoardSeq(String upBoardSeq) {
		this.upBoardSeq = upBoardSeq;
	}

	public String getUpCounselSeq() {
		return upCounselSeq;
	}

	public void setUpCounselSeq(String upCounselSeq) {
		this.upCounselSeq = upCounselSeq;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}

	public String getMemberNm() {
		return memberNm;
	}

	public void setMemberNm(String memberNm) {
		this.memberNm = memberNm;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCustQstType() {
		return custQstType;
	}

	public void setCustQstType(String custQstType) {
		this.custQstType = custQstType;
	}

	public String getQstMgrpCd() {
		return qstMgrpCd;
	}

	public void setQstMgrpCd(String qstMgrpCd) {
		this.qstMgrpCd = qstMgrpCd;
	}

	public String getCustQstDivnCd() {
		return custQstDivnCd;
	}

	public void setCustQstDivnCd(String custQstDivnCd) {
		this.custQstDivnCd = custQstDivnCd;
	}

	public String getCustQstMgrpCd() {
		return custQstMgrpCd;
	}

	public void setCustQstMgrpCd(String custQstMgrpCd) {
		this.custQstMgrpCd = custQstMgrpCd;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBoardPrgsStsCd() {
		return boardPrgsStsCd;
	}

	public void setBoardPrgsStsCd(String boardPrgsStsCd) {
		this.boardPrgsStsCd = boardPrgsStsCd;
	}

	public String getAcceptLocaCd() {
		return acceptLocaCd;
	}

	public void setAcceptLocaCd(String acceptLocaCd) {
		this.acceptLocaCd = acceptLocaCd;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getAcceptId() {
		return acceptId;
	}

	public void setAcceptId(String acceptId) {
		this.acceptId = acceptId;
	}

	public String getLoginNm() {
		return loginNm;
	}

	public void setLoginNm(String loginNm) {
		this.loginNm = loginNm;
	}

	public String getTransferRegDate() {
		return transferRegDate;
	}

	public void setTransferRegDate(String transferRegDate) {
		this.transferRegDate = transferRegDate;
	}

	public String getRespondentNm() {
		return respondentNm;
	}

	public void setRespondentNm(String respondentNm) {
		this.respondentNm = respondentNm;
	}

	public String getAnsMngrNm() {
		return ansMngrNm;
	}

	public void setAnsMngrNm(String ansMngrNm) {
		this.ansMngrNm = ansMngrNm;
	}

	public String getAnswername() {
		return answername;
	}

	public void setAnswername(String answername) {
		this.answername = answername;
	}

	public String getAnswerDate() {
		return answerDate;
	}

	public void setAnswerDate(String answerDate) {
		this.answerDate = answerDate;
	}

	public String getDelYn() {
		return delYn;
	}

	public void setDelYn(String delYn) {
		this.delYn = delYn;
	}

	public String getAgentLocation() {
		return agentLocation;
	}

	public void setAgentLocation(String agentLocation) {
		this.agentLocation = agentLocation;
	}

	public String getReasonCd() {
		return reasonCd;
	}

	public void setReasonCd(String reasonCd) {
		this.reasonCd = reasonCd;
	}

	public String getStatusOk() {
		return statusOk;
	}

	public void setStatusOk(String statusOk) {
		this.statusOk = statusOk;
	}

	public String getStatusMiss() {
		return statusMiss;
	}

	public void setStatusMiss(String statusMiss) {
		this.statusMiss = statusMiss;
	}

	public String getLoginAdminId() {
		return loginAdminId;
	}

	public void setLoginAdminId(String loginAdminId) {
		this.loginAdminId = loginAdminId;
	}

	public String getTypeCheck() {
		return typeCheck;
	}

	public void setTypeCheck(String typeCheck) {
		this.typeCheck = typeCheck;
	}
	
	public String getMajorCd() {
		return majorCd;
	}
	
	public void setMajorCd(String majorCd) {
		this.majorCd = majorCd;
	}

	public String getMinorCd() {
		return minorCd;
	}

	public void setMinorCd(String minorCd) {
		this.minorCd = minorCd;
	}

}
