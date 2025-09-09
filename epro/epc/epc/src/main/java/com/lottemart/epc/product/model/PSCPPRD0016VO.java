package com.lottemart.epc.product.model;


import java.io.Serializable;

/**
 * @Class Name : PSCPPRD0016VO.java
 * @Description :
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 20. 오후 02:02:02 mjChoi
 * 
 * @Copyright (C) 2000 ~ 2011 롯데정보통신(주) All right reserved.
 * </pre>
 */
public class PSCPPRD0016VO implements Serializable 
{
	private static final long serialVersionUID = -8880104139137198938L;
	
	private String rnum = "";
	private String strCd = "";
	private String regId = "";
	private String prodCd = "";
	private String prest = "";
	private String prestStartDy = "";
	private String prestEndDy = "";
	private String icon6 = "";
	private String strNm = "";
	private String strGubun = "";
	private String prestType = "";
	
	public String getStrCd() {
		return strCd;
	}
	public void setStrCd(String strCd) {
		this.strCd = strCd;
	}
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	public String getProdCd() {
		return prodCd;
	}
	public void setProdCd(String prodCd) {
		this.prodCd = prodCd;
	}
	public String getPrest() {
		return prest;
	}
	public void setPrest(String prest) {
		this.prest = prest;
	}
	public String getPrestStartDy() {
		return prestStartDy;
	}
	public void setPrestStartDy(String prestStartDy) {
		this.prestStartDy = prestStartDy;
	}
	public String getPrestEndDy() {
		return prestEndDy;
	}
	public void setPrestEndDy(String prestEndDy) {
		this.prestEndDy = prestEndDy;
	}
	public String getIcon6() {
		return icon6;
	}
	public void setIcon6(String icon6) {
		this.icon6 = icon6;
	}
	public String getStrNm() {
		return strNm;
	}
	public void setStrNm(String strNm) {
		this.strNm = strNm;
	}
	public String getStrGubun() {
		return strGubun;
	}
	public void setStrGubun(String strGubun) {
		this.strGubun = strGubun;
	}
	public String getPrestType() {
		return prestType;
	}
	public void setPrestType(String prestType) {
		this.prestType = prestType;
	}
	public String getRnum() {
		return rnum;
	}
	public void setRnum(String rnum) {
		this.rnum = rnum;
	}	

}
