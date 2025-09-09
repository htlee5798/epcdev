package com.lottemart.epc.etc.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;


@SuppressWarnings("serial")
public class PSCMETC0001VO implements Serializable {

    /** 페이징 관련 페이지당 목록 수 */
    private int rowsPerPage;
    
    /** 페이지 관련 현재 페이지 */
    private int currentPage;
    
    /** 저장시 등록과 수정을 구분하는 플래그 */
	private String crud = "";    
    
	/** 코드그룹ID */
	private String majorCd = "";
	
	/** MINOR 코드 */
	private String minorCd = "";
	
	/** 코드명 */
	private String cdNm = "";
	
	/** 설명 */
	private String cdDesc = "";
	
	/** 정렬순서 */
	private String orderSeq = "";
	
	/** 코드등록구분(SM105) */
	private String regDivnCd = "";
	
	/** 사용여부 */
	private String useYn = "";
	
	/** 유효끝일시 */
	private String valiEndDate = "";
	
	/** 유효시작일시 */
	private String valiStartDate = "";

	public int getRowsPerPage() {
		return rowsPerPage;
	}

	public void setRowsPerPage(int rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

    public String getCrud() {
		return crud;
	}

	public void setCrud(String crud) {
		this.crud = crud;
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

	public String getCdNm() {
		return cdNm;
	}

	public void setCdNm(String cdNm) {
		this.cdNm = cdNm;
	}

	public String getCdDesc() {
		return cdDesc;
	}

	public void setCdDesc(String cdDesc) {
		this.cdDesc = cdDesc;
	}

	public String getOrderSeq() {
		return orderSeq;
	}

	public void setOrderSeq(String orderSeq) {
		this.orderSeq = orderSeq;
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

	public String getValiEndDate() {
		return valiEndDate;
	}

	public void setValiEndDate(String valiEndDate) {
		this.valiEndDate = valiEndDate;
	}

	public String getValiStartDate() {
		return valiStartDate;
	}

	public void setValiStartDate(String valiStartDate) {
		this.valiStartDate = valiStartDate;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("\n rowsPerPage :: ").append(rowsPerPage);
		sb.append("\n currentPage :: ").append(currentPage);
		sb.append("\n majorCd :: ").append(majorCd);
		sb.append("\n minorCd :: ").append(minorCd);
		sb.append("\n cdNm :: ").append(cdNm);
		sb.append("\n cdDesc :: ").append(cdDesc);
		sb.append("\n orderSeq :: ").append(orderSeq);
		sb.append("\n regDivnCd :: ").append(regDivnCd);
		sb.append("\n useYn :: ").append(useYn);
		sb.append("\n valiEndDate :: ").append(valiEndDate);
		sb.append("\n valiStartDate :: ").append(valiStartDate).append("\n");
		return sb.toString();
	}

}
