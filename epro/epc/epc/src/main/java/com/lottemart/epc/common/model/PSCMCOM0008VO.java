package com.lottemart.epc.common.model;

import java.io.Serializable;

import com.lottemart.epc.edi.weborder.model.PagingVO;

public class PSCMCOM0008VO extends PagingVO  implements Serializable {


	/** 업체코드 조회조건(LIKE 용)*/
	public String vendorId;
	/** 업체코드 조회조건(EQUAIL 용)*/
	public String _vendorId;
	/**업체명 LIKE*/
	public String vendorNm;
	/**로그인 사번(본인 관계협력사 조회 조건)*/
	public String empNo;
	/**검색방법 (01:전체검색, 02:관리협력사)*/
	public String findType;
	
	/** 업체코드     */ 
    private String venCd    ;  
    /** 업체명       */ 
    private String venNm    ;  
    /** 사업자번호   */ 
    private String bmanNo   ;
    /** 사업자번호 FMT  */
    private String bmanNoFmt ;
    /** 업태         */ 
    private String btyp     ;  
    /** 업종         */ 
    private String bkind    ;  
    /** 주소1        */ 
    private String addr1    ;  
    /** 주소2        */ 
    private String addr2    ;  
    /** 대표전화     */ 
    private String repTelNo ;  
    /** 대표자명     */ 
    private String presidNm ;  
    
    
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
	
	 public String getEmpNo() {
			return empNo;
	}
	public void setEmpNo(String empNo) {
			this.empNo = empNo;
	}
	public String getFindType() {
		return findType;
	}
	public void setFindType(String findType) {
		this.findType = findType;
	}
	public String get_vendorId() {
		return _vendorId;
	}
	public void set_vendorId(String _vendorId) {
		this._vendorId = _vendorId;
	}
	public String getVenCd() {
		return venCd;
	}
	public void setVenCd(String venCd) {
		this.venCd = venCd;
	}
	public String getVenNm() {
		return venNm;
	}
	public void setVenNm(String venNm) {
		this.venNm = venNm;
	}
	public String getBmanNo() {
		return bmanNo;
	}
	public void setBmanNo(String bmanNo) {
		this.bmanNo = bmanNo;
	}
	public String getBmanNoFmt() {
		return bmanNoFmt;
	}
	public void setBmanNoFmt(String bmanNoFmt) {
		this.bmanNoFmt = bmanNoFmt;
	}
	public String getBtyp() {
		return btyp;
	}
	public void setBtyp(String btyp) {
		this.btyp = btyp;
	}
	public String getBkind() {
		return bkind;
	}
	public void setBkind(String bkind) {
		this.bkind = bkind;
	}
	public String getAddr1() {
		return addr1;
	}
	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}
	public String getAddr2() {
		return addr2;
	}
	public void setAddr2(String addr2) {
		this.addr2 = addr2;
	}
	public String getRepTelNo() {
		return repTelNo;
	}
	public void setRepTelNo(String repTelNo) {
		this.repTelNo = repTelNo;
	}
	public String getPresidNm() {
		return presidNm;
	}
	public void setPresidNm(String presidNm) {
		this.presidNm = presidNm;
	}
	
	
	
	
}
