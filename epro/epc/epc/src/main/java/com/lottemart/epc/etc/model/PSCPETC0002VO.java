package com.lottemart.epc.etc.model;

import java.io.Serializable;

public class PSCPETC0002VO  implements Serializable {

    private static final long serialVersionUID = 1L;
    
    /** 페이징 관련 페이지당 목록 수 */
    private int rowsPerPage;
    
    /** 페이지 관련 현재 페이지 */
    private int currentPage;
    
	/** 코드그룹ID */
    private String majorCd;
    
    /** MAJOR코드 */
    private String minorCd;

    /** 코드명 */
    private String cdNm;
    
    /** 내용 */
    private String cdDesc;

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
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(">>>> SamplePopupVO = \n");
		sb.append("majorCd :: " + majorCd + "\n");
		sb.append("minorCd :: " + minorCd + "\n");
		sb.append("cdNm :: " + cdNm + "\n");
		sb.append("cdDesc :: " + cdDesc + "\n");
		return sb.toString(); 
	}
    
}
