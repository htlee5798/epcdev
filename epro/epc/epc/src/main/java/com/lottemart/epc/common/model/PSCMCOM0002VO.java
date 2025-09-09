package com.lottemart.epc.common.model;

import java.io.Serializable;

public class PSCMCOM0002VO implements Serializable {

	/** 
	 * 클러스터 시스템에서의 직력화 아이디 
	 */
	private static final long serialVersionUID = -8120634536814198185L;
	
	/** 협력사ID */
    private String vendorId = "";
    
    /** 협력사명 */
    private String vendorNm = "";
    
    /** 사업자번 display */
    private String cono = "";
    
    /** 사업자번호 parameter */
    private String parmCono;
    
    /** 조회구분 */
    private String gubn = "";
    
    /** 관리자 ID */
    private String loginId;
    
    /** LCN 회사 ID */
    private String lcnComId;
    
    /** 활성화 여부 */
    private String  activeYn="";
    
    /** 비밀번호 오류 횟수*/
    private int pwdErrorCnt;

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	/** 패스워드 */
    private String pwd;
    
    
	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
	
	 public String getLcnComId() {
		return lcnComId;
	}
	public void setLcnComId(String lcnComId) {
		this.lcnComId = lcnComId;
	}
		

	/** 현재페이지 */
    private int pageIndex = 1;
    
    /** 페이지갯수 */
    private int pageUnit = 10;
    
    /** 페이지사이즈 */
    private int pageSize = 10;

    /** firstIndex */
    private int firstIndex = 1;

    /** lastIndex */
    private int lastIndex = 1;

    /** recordCountPerPage */
    private int recordCountPerPage = 10;


    
    /** 사업자번호 parameter */
	public String getParmCono() {
		return parmCono;
	}

	public void setParmCono(String parmCono) {
		this.parmCono = parmCono;
	}
    
	/** 조회구분 */
	public String getGubn() {
		return gubn;
	}

	public void setGubn(String gubn) {
		this.gubn = gubn;
	}

	public String getVendorId() {
		return vendorId;
	}

	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}

	public String getVendorNm() {
		return vendorNm;
	}

	public void setVendorNm(String vendorNm) {
		this.vendorNm = vendorNm;
	}

	public String getCono() {
		return cono;
	}

	public void setCono(String cono) {
		this.cono = cono;
	}

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

	public String getActiveYn() {
		return activeYn;
	}

	public void setActiveYn(String activeYn) {
		this.activeYn = activeYn;
	}

	public int getPwdErrorCnt() {
		return pwdErrorCnt;
	}

	public void setPwdErrorCnt(int pwdErrorCnt) {
		this.pwdErrorCnt = pwdErrorCnt;
	}

}
