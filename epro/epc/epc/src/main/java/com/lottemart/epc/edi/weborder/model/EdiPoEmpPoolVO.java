package com.lottemart.epc.edi.weborder.model;

import java.io.Serializable;

/**
 * @Class Name : EdiPoEmPoolVO
 * @Description : MDer 담당자정보
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

public class EdiPoEmpPoolVO extends PagingVO implements Serializable {

	


	



	/**
	 * 
	 */
	private static final long serialVersionUID = 2624480952235485454L;



	public EdiPoEmpPoolVO() {}
	
	/**조회상태정보 0:정상, 1:중복, -1:exception*/
	private String state		;
	/**상태 메세지*/
	private String message      ;
	/**작업자아이디*/
	private String workUser		;

	/**사원번호 복사,이동용 */
	private String frEmpNo		;
	private String toEmpNo		;
	private String eventType	;
	
	/**사원번호 */
	private String empNo		;
	/**사원명 */
	private String empNm		;
	/**MD부서코드 */
	private String orgCd		;
	/**MD부서명 */
	private String orgNm		;
	/**메일 */
	private String mail			;
	/**직무코드 */
	private String jobCd		;
	/**직무명 */
	private String jobNm		;
	/**사무실번호 */
	private String phoneNo		;
	/**핸드폰번호 */
	private String hpNo			;
	/**등록일 */
	private String regDt		;
	/**수정일 */
	private String lstChgDt 	;
	/**수정구분 */
	private String chgFg		;
	
	/**협력업체코드*/
	private String venCd		;
	/**협력업체명*/
	private String venNm		;
	
	
	/**사원번호 */
	private String[] empNos		;
	/**협력업체코드*/
	private String[] venCds		;
	


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
	public String getFrEmpNo() {
		return frEmpNo;
	}
	public void setFrEmpNo(String frEmpNo) {
		this.frEmpNo = frEmpNo;
	}
	public String getToEmpNo() {
		return toEmpNo;
	}
	public void setToEmpNo(String toEmpNo) {
		this.toEmpNo = toEmpNo;
	}
	public String getEmpNo() {
		return empNo;
	}
	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}
	public String getEmpNm() {
		return empNm;
	}
	public void setEmpNm(String empNm) {
		this.empNm = empNm;
	}
	public String getOrgCd() {
		return orgCd;
	}
	public void setOrgCd(String orgCd) {
		this.orgCd = orgCd;
	}
	public String getOrgNm() {
		return orgNm;
	}
	public void setOrgNm(String orgNm) {
		this.orgNm = orgNm;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getJobCd() {
		return jobCd;
	}
	public void setJobCd(String jobCd) {
		this.jobCd = jobCd;
	}
	public String getJobNm() {
		return jobNm;
	}
	public void setJobNm(String jobNm) {
		this.jobNm = jobNm;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getHpNo() {
		return hpNo;
	}
	public void setHpNo(String hpNo) {
		this.hpNo = hpNo;
	}
	public String getRegDt() {
		return regDt;
	}
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}
	public String getLstChgDt() {
		return lstChgDt;
	}
	public void setLstChgDt(String lstChgDt) {
		this.lstChgDt = lstChgDt;
	}
	public String getChgFg() {
		return chgFg;
	}
	public void setChgFg(String chgFg) {
		this.chgFg = chgFg;
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
	public String[] getEmpNos() {
		return empNos;
	}
	public void setEmpNos(String[] empNos) {
		this.empNos = empNos;
	}
	public String[] getVenCds() {
		return venCds;
	}
	public void setVenCds(String[] venCds) {
		this.venCds = venCds;
	}
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	
	
	
	
	
	
}
