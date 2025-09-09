package com.lottemart.epc.edi.weborder.model;

import java.io.Serializable;

/**
 * @Class Name : NEDMWEB0220VO
 * @Description : 발주전체현황 VO Class
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      		수정자          		수정내용
 *  -        -
 * 2015.12.08	O YEUN KWON	  최초생성
 * 
 * @Copyright (C) 2000 ~ 2015 lottemart All right reserved.
 * </pre>
 */

public class NEDMWEB0240VO extends PagingVO implements Serializable {

	/**
	 * 
	 */
		private static final long serialVersionUID = 6715413512908897485L;
		

	   
	    private String empNo;					// 사번
	    private String empNm;					// 사원명
	    private String orgCd;					// MD부서코드
	    private String orgNm;					// MDMD부서명
	    private String mail;					// 메일
	    private String jobCd;					// 직무코드
	    private String jobNm;					// 직무명
	    private String phoneNo;					// 사무실번호
	    private String hpNo;					// 핸드폰번호
	    private String regDt;					// 등록일
	    private String lstChgDt;				// 수정일
	    private String chgFg;					// 수정구분
	    private String venCd;					// 협력업체코드
	    private String venNm;					// 협력업체명
	    private String workUser;				// 작업자아이디
	    /**사원번호 복사,이동용 */
		private String frEmpNo;
		private String toEmpNo;
		private String eventType;
		private String[] venCds;				// 로그인사용자의 협력업체코드 전체
		
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
		public static long getSerialversionuid() {
			return serialVersionUID;
		}
		public String getEventType() {
			return eventType;
		}
		public void setEventType(String eventType) {
			this.eventType = eventType;
		}
		public String[] getVenCds() {
			if (this.venCds != null) {
				String[] ret = new String[venCds.length];
				for (int i = 0; i < venCds.length; i++) { 
					ret[i] = this.venCds[i]; 
				}
				return ret;
			} else {
				return null;
			}		
		}
		
		public void setVenCds(String[] venCds) {
			 if (venCds != null) {
				 this.venCds = new String[venCds.length];			 
				 for (int i = 0; i < venCds.length; ++i) {
					 this.venCds[i] = venCds[i];
				 }
			 } else {
				 this.venCds = null;
			 }

		}
		
	    
}
