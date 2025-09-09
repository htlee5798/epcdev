package com.lottemart.common.notice.model;

import java.io.Serializable;

public class NoticeVO implements Serializable{
	private static final long serialVersionUID = 6722086646190255621L;

	public String num = "";
	public String title = "";
	public String boardSeq = "";
	public String atchFileId = "";
	public String strCd = "";
	public String strName = ""; 
	public String chkStrCd = ""; 
	public String startDt = ""; 
	public String endDt = ""; 
	public String startTime = ""; 
	public String endTime = ""; 
	public String content = ""; 
	public String pblYN = ""; 
	public String viewCnt = "";
	
	
	public String getViewCnt() {
		return viewCnt;
	}
	public void setViewCnt(String viewCnt) {
		this.viewCnt = viewCnt;
	}
	public String getStrCd() {
		return strCd;
	}
	public void setStrCd(String strCd) {
		this.strCd = strCd;
	}
	public String getStrName() {
		return strName;
	}
	public void setStrName(String strName) {
		this.strName = strName;
	}
	public String getChkStrCd() {
		return chkStrCd;
	}
	public void setChkStrCd(String chkStrCd) {
		this.chkStrCd = chkStrCd;
	}
	public String getStartDt() {
		return startDt;
	}
	public void setStartDt(String startDt) {
		this.startDt = startDt;
	}
	public String getEndDt() {
		return endDt;
	}
	public void setEndDt(String endDt) {
		this.endDt = endDt;
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getPblYN() {
		return pblYN;
	}
	public void setPblYN(String pblYN) {
		this.pblYN = pblYN;
	}
	public String getAtchFileId() {
		return atchFileId;
	}
	public void setAtchFileId(String atchFileId) {
		this.atchFileId = atchFileId;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getBoardSeq() {
		return boardSeq;
	}
	public void setBoardSeq(String boardSeq) {
		this.boardSeq = boardSeq;
	}
}
