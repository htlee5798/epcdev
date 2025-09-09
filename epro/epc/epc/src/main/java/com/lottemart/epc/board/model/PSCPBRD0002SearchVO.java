/**
 * @prjectName  : lottemart 프로젝트   
 * @since       : 2011. 10. 19. 오후 5:54:50
 * @author      : wcpark 
 * @Copyright(c) 2000 ~ 2011 lottemart
 *  All rights reserved.
 */

package com.lottemart.epc.board.model;

import java.io.Serializable;

/**
 * @Class Name : PSCPBRD0002SearchVO
 * @Description : 공지사항 상세보기 SearchVO 클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 19. 오후 5:55:38 wcpark
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
public class PSCPBRD0002SearchVO implements Serializable {

	private static final long serialVersionUID = 1224797135937284335L;

	/** 게시글 순번 */
	private String boardSeq = "";
	private String rowNum = "";
	
	/** 게시글 제목 */
	private String title = "";
	
	/** 작성자 */
	private String regId = "";
	
	/** 작성일자 */
	private String wrtDy = "";
	
	/** 게시글 내용 */
	private String content = "";
	
	/** 첨부파일 아이디 */
	private String atchFileId = "";
	
	/** 작성일자 */
	private String regDate = "";
	
	/** 시작일 */
	private String ntceStartDy = "";

	/** 종료일 */
	private String ntceEndDy = "";
	
	private String totalCount = "";
	
	private String listSeq = "";
	private String upBoardSeq = "";
	private String boardDqSeq = "";
	private String scrpObjectCd = "";
	private String childScrpCnt = "";
	private String depth = "";
	private String memberNo = "";
	private String email = "";
	private String boardStatus = "";
	private String reasonBcd = "";
	private String reasonScd = "";
	private String boardGb = "";
	private String viewCnt = "";
	private String recomCnt = "";
	private String atchFileYn = "";
	private String pblYn = "";
	
	public String getBoardSeq() {
		return boardSeq;
	}

	public void setBoardSeq(String boardSeq) {
		this.boardSeq = boardSeq;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getRegId() {
		return regId;
	}

	public void setRegId(String regId) {
		this.regId = regId;
	}

	public String getWrtDy() {
		return wrtDy;
	}

	public void setWrtDy(String wrtDy) {
		this.wrtDy = wrtDy;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAtchFileId() {
		return atchFileId;
	}

	public void setAtchFileId(String atchFileId) {
		this.atchFileId = atchFileId;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public String getNtceStartDy() {
		return ntceStartDy;
	}

	public void setNtceStartDy(String ntceStartDy) {
		this.ntceStartDy = ntceStartDy;
	}

	public String getNtceEndDy() {
		return ntceEndDy;
	}

	public void setNtceEndDy(String ntceEndDy) {
		this.ntceEndDy = ntceEndDy;
	}

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

	public String getRowNum() {
		return rowNum;
	}

	public void setRowNum(String rowNum) {
		this.rowNum = rowNum;
	}

	public String getListSeq() {
		return listSeq;
	}

	public void setListSeq(String listSeq) {
		this.listSeq = listSeq;
	}

	public String getUpBoardSeq() {
		return upBoardSeq;
	}

	public void setUpBoardSeq(String upBoardSeq) {
		this.upBoardSeq = upBoardSeq;
	}

	public String getBoardDqSeq() {
		return boardDqSeq;
	}

	public void setBoardDqSeq(String boardDqSeq) {
		this.boardDqSeq = boardDqSeq;
	}

	public String getScrpObjectCd() {
		return scrpObjectCd;
	}

	public void setScrpObjectCd(String scrpObjectCd) {
		this.scrpObjectCd = scrpObjectCd;
	}

	public String getChildScrpCnt() {
		return childScrpCnt;
	}

	public void setChildScrpCnt(String childScrpCnt) {
		this.childScrpCnt = childScrpCnt;
	}

	public String getDepth() {
		return depth;
	}

	public void setDepth(String depth) {
		this.depth = depth;
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

	public String getBoardStatus() {
		return boardStatus;
	}

	public void setBoardStatus(String boardStatus) {
		this.boardStatus = boardStatus;
	}

	public String getReasonBcd() {
		return reasonBcd;
	}

	public void setReasonBcd(String reasonBcd) {
		this.reasonBcd = reasonBcd;
	}

	public String getReasonScd() {
		return reasonScd;
	}

	public void setReasonScd(String reasonScd) {
		this.reasonScd = reasonScd;
	}

	public String getBoardGb() {
		return boardGb;
	}

	public void setBoardGb(String boardGb) {
		this.boardGb = boardGb;
	}

	public String getViewCnt() {
		return viewCnt;
	}

	public void setViewCnt(String viewCnt) {
		this.viewCnt = viewCnt;
	}

	public String getRecomCnt() {
		return recomCnt;
	}

	public void setRecomCnt(String recomCnt) {
		this.recomCnt = recomCnt;
	}

	public String getAtchFileYn() {
		return atchFileYn;
	}

	public void setAtchFileYn(String atchFileYn) {
		this.atchFileYn = atchFileYn;
	}

	public String getPblYn() {
		return pblYn;
	}

	public void setPblYn(String pblYn) {
		this.pblYn = pblYn;
	}
	
}
