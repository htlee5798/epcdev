package com.lottemart.epc.edi.consult.model;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * @Class Name : NEDMCST0030VO
 * @Description : 알리미
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 11. 04. 오후 2:32:38 
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */

public class NEDMCST0030VO implements Serializable {

	
	private static final long serialVersionUID = -6918711902569942487L;


	public NEDMCST0030VO () {}
	
	private String[] bmans;
	private String loginId;
	private String business;
	private String anxInfoCd;
	private String bmanNo;
	private String svcSeq;
	private String telnoNm;
	private String telNo;
	private String email;
	private String regDate;
	private String[] venCds;        // 협력업체 코드 배열
	private String venCd;
	
	private String cell1;
	private String cell2;
	private String cell3;
	
	
	//수정 파람
	private String oldBmanNo;
	private String oldAnxInfoCd;
	private String oldCell1;
	private String oldCell2;
	private String oldCell3;
	private String oldEmail;
	
	
	 
	
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
	//
	public String[] getBmans() {
		if (this.bmans != null) {
			String[] ret = new String[bmans.length];
			for (int i = 0; i < bmans.length; i++) { 
				ret[i] = this.bmans[i]; 
			}
			return ret;
		} else {
			return null;
		}
	}
	public void setBmans(String[] bmans) {
		if (bmans != null) {
			 this.bmans = new String[bmans.length];			 
			 for (int i = 0; i < bmans.length; ++i) {
				 this.bmans[i] = bmans[i];
			 }
		 } else {
			 this.bmans = null;
		 }
	}
	public String getBusiness() {
		return business;
	}
	public void setBusiness(String business) {
		this.business = business;
	}
	public String getAnxInfoCd() {
		return anxInfoCd;
	}
	public void setAnxInfoCd(String anxInfoCd) {
		this.anxInfoCd = anxInfoCd;
	}
	public String getBmanNo() {
		return bmanNo;
	}
	public void setBmanNo(String bmanNo) {
		this.bmanNo = bmanNo;
	}
	public String getSvcSeq() {
		return svcSeq;
	}
	public void setSvcSeq(String svcSeq) {
		this.svcSeq = svcSeq;
	}
	public String getTelnoNm() {
		return telnoNm;
	}
	public void setTelnoNm(String telnoNm) {
		this.telnoNm = telnoNm;
	}
	public String getTelNo() {
		return telNo;
	}
	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public String getVenCd() {
		return venCd;
	}
	public void setVenCd(String venCd) {
		this.venCd = venCd;
	}
	public String getCell1() {
		return cell1;
	}
	public void setCell1(String cell1) {
		this.cell1 = cell1;
	}
	public String getCell2() {
		return cell2;
	}
	public void setCell2(String cell2) {
		this.cell2 = cell2;
	}
	public String getCell3() {
		return cell3;
	}
	public void setCell3(String cell3) {
		this.cell3 = cell3;
	}
	public String getOldBmanNo() {
		return oldBmanNo;
	}
	public void setOldBmanNo(String oldBmanNo) {
		this.oldBmanNo = oldBmanNo;
	}
	public String getOldAnxInfoCd() {
		return oldAnxInfoCd;
	}
	public void setOldAnxInfoCd(String oldAnxInfoCd) {
		this.oldAnxInfoCd = oldAnxInfoCd;
	}
	public String getOldCell1() {
		return oldCell1;
	}
	public void setOldCell1(String oldCell1) {
		this.oldCell1 = oldCell1;
	}
	public String getOldCell2() {
		return oldCell2;
	}
	public void setOldCell2(String oldCell2) {
		this.oldCell2 = oldCell2;
	}
	public String getOldCell3() {
		return oldCell3;
	}
	public void setOldCell3(String oldCell3) {
		this.oldCell3 = oldCell3;
	}
	public String getOldEmail() {
		return oldEmail;
	}
	public void setOldEmail(String oldEmail) {
		this.oldEmail = oldEmail;
	}


}
