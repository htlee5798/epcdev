package com.lottemart.epc.edi.product.model;

import java.io.Serializable;

public class PEDMPRO0095VO implements Serializable {

	private static final long serialVersionUID = -3421567863315305924L;

	public PEDMPRO0095VO() {}

	/* List Column */
	private String prodCd;		//상품코드
    private String srcmkCd;		//판매코드
    private String l4Cd;		//기존세분류코드 	
    private String sapL3Cd;		//sap 변경된 소분류코드 
    private String attid;		//그룹속성분류코드
    private String attValId;	//그룹속성분류코드값
    private String attValNm;	//그룹속성분류코드값명칭
    private String regDate;		//등록일자 
    private String regId;		//협력업체코드
    
    private String codeId;
    private String codeNm;
        

	public String getProdCd() {
		return prodCd;
	}
	public void setProdCd(String prodCd) {
		this.prodCd = prodCd;
	}
	public String getSrcmkCd() {
		return srcmkCd;
	}
	public void setSrcmkCd(String srcmkCd) {
		this.srcmkCd = srcmkCd;
	}
	public String getL4Cd() {
		return l4Cd;
	}
	public void setL4Cd(String l4Cd) {
		this.l4Cd = l4Cd;
	}
	public String getSapL3Cd() {
		return sapL3Cd;
	}
	public void setSapL3Cd(String sapL3Cd) {
		this.sapL3Cd = sapL3Cd;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	public String getAttid() {
		return attid;
	}
	public void setAttid(String attid) {
		this.attid = attid;
	}
	public String getAttValId() {
		return attValId;
	}
	public void setAttValId(String attValId) {
		this.attValId = attValId;
	}
	public String getAttValNm() {
		return attValNm;
	}
	public void setAttValNm(String attValNm) {
		this.attValNm = attValNm;
	}
	public String getCodeId() {
		return codeId;
	}
	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}
	public String getCodeNm() {
		return codeNm;
	}
	public void setCodeNm(String codeNm) {
		this.codeNm = codeNm;
	}    
	
}
