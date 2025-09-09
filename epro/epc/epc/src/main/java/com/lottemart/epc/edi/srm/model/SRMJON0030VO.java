package com.lottemart.epc.edi.srm.model;

import java.io.Serializable;

public class SRMJON0030VO implements Serializable {

	static final long serialVersionUID = -254238134556327798L;

	/** No */
	private String rnum;
	/** Evaluation Template No */
	private String evTplNo;
	/** Evaluation Item No */
	private String evItemNo;
	/**항목순번*/
	private String evIdSeq;
	/**항목값*/
	private String evIdScore;
	/** Evaluation Item Subject */
	private String evItemSubject;
	/**  Evaluation Item Content */
	private String evItemContents;
	/**rowspan*/
	private String rowspan;
	/**채널코드[SRM053]*/
	private String channelCode;
	/**해외업체구분*/
	private String shipperType;
	/**사업자번호*/
	private String irsNo;
	/**항목*/
	private String evIdContents;
	/**대분류코드*/
	private String sgNo;
	/**대분류 코드명*/
	private String sgName;
	/**대분류 신용등급*/
	private String scKind;
	/**상세내용*/
	private String remark;
	/**대분류명[검색조건]*/
	private String catLv1Code;


	public String getRnum() {
		return rnum;
	}

	public void setRnum(String rnum) {
		this.rnum = rnum;
	}

	public String getEvTplNo() {
		return evTplNo;
	}

	public void setEvTplNo(String evTplNo) {
		this.evTplNo = evTplNo;
	}

	public String getEvItemNo() {
		return evItemNo;
	}

	public void setEvItemNo(String evItemNo) {
		this.evItemNo = evItemNo;
	}

	public String getEvItemSubject() {
		return evItemSubject;
	}

	public void setEvItemSubject(String evItemSubject) {
		this.evItemSubject = evItemSubject;
	}

	public String getEvItemContents() {
		return evItemContents;
	}

	public void setEvItemContents(String evItemContents) {
		this.evItemContents = evItemContents;
	}

	public String getRowspan() {
		return rowspan;
	}

	public void setRowspan(String rowspan) {
		this.rowspan = rowspan;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public String getEvIdSeq() {
		return evIdSeq;
	}

	public void setEvIdSeq(String evIdSeq) {
		this.evIdSeq = evIdSeq;
	}

	public String getEvIdScore() {
		return evIdScore;
	}

	public void setEvIdScore(String evIdScore) {
		this.evIdScore = evIdScore;
	}

	public String getShipperType() {
		return shipperType;
	}

	public void setShipperType(String shipperType) {
		this.shipperType = shipperType;
	}

	public String getIrsNo() {
		return irsNo;
	}

	public void setIrsNo(String irsNo) {
		this.irsNo = irsNo;
	}

	public String getEvIdContents() {
		return evIdContents;
	}

	public void setEvIdContents(String evIdContents) {
		this.evIdContents = evIdContents;
	}

	public String getSgNo() {
		return sgNo;
	}

	public void setSgNo(String sgNo) {
		this.sgNo = sgNo;
	}

	public String getSgName() {
		return sgName;
	}

	public void setSgName(String sgName) {
		this.sgName = sgName;
	}

	public String getScKind() {
		return scKind;
	}

	public void setScKind(String scKind) {
		this.scKind = scKind;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCatLv1Code() {
		return catLv1Code;
	}

	public void setCatLv1Code(String catLv1Code) {
		this.catLv1Code = catLv1Code;
	}
}
