package com.lottemart.epc.edi.product.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class PEDMPRO0098VO implements Serializable {

	private static final long serialVersionUID = -6766182773315305924L;

	public PEDMPRO0098VO() {}

	private String teamCd;		// 팀코드
	private String teamNm;		// 팀명칭
	private String l1Cd;		// 대분류코드
	private String l1Nm;		// 대분류명칭
	private String l4Cd;		// 세분류코드
	private String l4Nm;		// 세분류명칭
	private String entpCd;		// 입력 혹은 선택되어 있는 협력업체코드
	
	
	private String srchL4Cd;		// 기존상품의 세분류코드
	private String sellCode;		// 기존상품의 판매코드
	private String newProductCode;	// 기존상품의 상품코드
	private String sapL3Cd;			// sap 소분류 코드
	private String majorCd;			// 공통코드에서 규격단위 콤보를 구성하기 위한 Parameter
	private String pageGbn;			// 페이지 구분
	
	private String[] arrSrcmkCd;
	private String[] arrProdCd;
	
	private String srchSellCode;
	
	
	
	/** 2016.03.07 그룹 소분류 추가 by song min kyo */
	private String grpCd;
	private String entpCode;
	private String srchGrpCd;
	
	public String getTeamCd() {
		return teamCd;
	}
	public void setTeamCd(String teamCd) {
		this.teamCd = teamCd;
	}
	public String getTeamNm() {
		return teamNm;
	}
	public void setTeamNm(String teamNm) {
		this.teamNm = teamNm;
	}
	public String getL1Cd() {
		return l1Cd;
	}
	public void setL1Cd(String l1Cd) {
		this.l1Cd = l1Cd;
	}
	public String getL1Nm() {
		return l1Nm;
	}
	public void setL1Nm(String l1Nm) {
		this.l1Nm = l1Nm;
	}
	public String getL4Cd() {
		return l4Cd;
	}
	public void setL4Cd(String l4Cd) {
		this.l4Cd = l4Cd;
	}
	public String getL4Nm() {
		return l4Nm;
	}
	public void setL4Nm(String l4Nm) {
		this.l4Nm = l4Nm;
	}
	public String getSrchL4Cd() {
		return srchL4Cd;
	}
	public void setSrchL4Cd(String srchL4Cd) {
		this.srchL4Cd = srchL4Cd;
	}
	public String getEntpCd() {
		return entpCd;
	}
	public void setEntpCd(String entpCd) {
		this.entpCd = entpCd;
	}
	public String getSellCode() {
		return sellCode;
	}
	public void setSellCode(String sellCode) {
		this.sellCode = sellCode;
	}
	public String getNewProductCode() {
		return newProductCode;
	}
	public void setNewProductCode(String newProductCode) {
		this.newProductCode = newProductCode;
	}
	public String getSapL3Cd() {
		return sapL3Cd;
	}
	public void setSapL3Cd(String sapL3Cd) {
		this.sapL3Cd = sapL3Cd;
	}
	public String getMajorCd() {
		return majorCd;
	}
	public void setMajorCd(String majorCd) {
		this.majorCd = majorCd;
	}		
	
	public String[] getArrSrcmkCd() {
		if (this.arrSrcmkCd != null) {
			String[] ret = new String[arrSrcmkCd.length];
			for (int i = 0; i < arrSrcmkCd.length; i++) { 
				ret[i] = this.arrSrcmkCd[i]; 
			}
			return ret;
		} else {
			return null;
		}		
	}
	
	public void setArrSrcmkCd(String[] arrSrcmkCd) {
		 if (arrSrcmkCd != null) {
			 this.arrSrcmkCd = new String[arrSrcmkCd.length];			 
			 for (int i = 0; i < arrSrcmkCd.length; ++i) {
				 this.arrSrcmkCd[i] = arrSrcmkCd[i];
			 }
		 } else {
			 this.arrSrcmkCd = null;
		 }

	}
	
	public String[] getArrProdCd() {
		if (this.arrProdCd != null) {
			String[] ret = new String[arrProdCd.length];
			for (int i = 0; i < arrProdCd.length; i++) { 
				ret[i] = this.arrProdCd[i]; 
			}
			return ret;
		} else {
			return null;
		}		
	}
	
	public void setArrProdCd(String[] arrProdCd) {
		 if (arrProdCd != null) {
			 this.arrProdCd = new String[arrProdCd.length];			 
			 for (int i = 0; i < arrProdCd.length; ++i) {
				 this.arrProdCd[i] = arrProdCd[i];
			 }
		 } else {
			 this.arrProdCd = null;
		 }

	}
	public String getPageGbn() {
		return pageGbn;
	}
	public void setPageGbn(String pageGbn) {
		this.pageGbn = pageGbn;
	}
	public String getSrchSellCode() {
		return srchSellCode;
	}
	public void setSrchSellCode(String srchSellCode) {
		this.srchSellCode = srchSellCode;
	}
	public String getGrpCd() {
		return grpCd;
	}
	public void setGrpCd(String grpCd) {
		this.grpCd = grpCd;
	}
	public String getEntpCode() {
		return entpCode;
	}
	public void setEntpCode(String entpCode) {
		this.entpCode = entpCode;
	}
	public String getSrchGrpCd() {
		return srchGrpCd;
	}
	public void setSrchGrpCd(String srchGrpCd) {
		this.srchGrpCd = srchGrpCd;
	}		
}
