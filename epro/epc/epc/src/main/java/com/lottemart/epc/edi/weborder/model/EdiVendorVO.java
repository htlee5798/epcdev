package com.lottemart.epc.edi.weborder.model;

import java.io.Serializable;

/**
 * @Class Name : EdiVendorVO
 * @Description : MDer 담당자별 업체설정 vo
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2014. 08. 22. 오후 2:32:38 DADA
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */

public class EdiVendorVO extends PagingVO implements Serializable {

	


	/**
	 * 
	 */
	private static final long serialVersionUID = 8679200336688160135L;




	public EdiVendorVO() {}
	
	/**조회상태정보 0:정상, 1:중복, -1:exception*/
	private String state		;
	/**상태 메세지*/
	private String message      ;
	/**작업자아이디*/
	private String workUser		;
	
	 /**사번        */        
	 private String empCd	   ;
	 /**업체코드        */        
	 private String venCd	   ;  
	 /**업체명          */        
	 private String venNm      ;  
	 /**사업자번호      */        
	 private String bmanNo     ;  
	 /**사업자번호 패턴 */        
	 private String bmanNoFmt  ;  
	 /**업태            */        
	 private String btyp       ;  
	 /**업종            */        
	 private String bkind      ;  
	 /**주소1           */        
	 private String addr1      ;  
	 /**주소2           */        
	 private String addr2      ;  
	 /**대표전화        */        
	 private String repTelNo   ;  
	 /**대표자명        */        
	 private String presidNm   ;  

	


	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getWorkUser() {
		return workUser;
	}
	public void setWorkUser(String workUser) {
		this.workUser = workUser;
	}
	public String getEmpCd() {
		return empCd;
	}
	public void setEmpCd(String empCd) {
		this.empCd = empCd;
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
