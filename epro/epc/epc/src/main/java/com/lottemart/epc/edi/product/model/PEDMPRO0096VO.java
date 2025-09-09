package com.lottemart.epc.edi.product.model;

import java.io.Serializable;

public class PEDMPRO0096VO implements Serializable {

	private static final long serialVersionUID = -3421134563315305924L;

	public PEDMPRO0096VO() {}

	private String attId;			// 그룹속성
	private String attValId;		// 그룹속성의 분류코드
	private String attIdTyp;		// 그룹속성의 분류코드 타입
	private String attValNm;		// 분류코드의 Value
	
	/* 그룹분석속성 저장시 넘어 오는 배열 */
	private String[] arrGrpAttr;	// 그룹속성분류코드
	private String[] arrVal;		// 그룹속성분류코드값
	private String[] arrGrpAttrTyp;	// 그룹속성분류코드 타입
	
	/* 그룹분석속성 저장시 넘어 오는 Parameter */
	private String newProductCode;	// 상품코드
	private String sellCode;		// 판매코드
	private String srchL4Cd;		// 기존상품세분류
	private String entpCd;			// 리스트에서 입력 혹은 선택되어있는 협력업체 코드
	private String sapL3Cd;			// SAP 소분류코드
	
	/* 그룹분석속성 저장시 */
	private String regId;			// 등록자(로그인 유저 ID)
	
	/* 상품속성관리(일괄)에서 저장시 */
	private String[] arrSrcmkCd;	// 일괄로 선택된 판매코드
	private String[] arrProdCd;		// 일괄로 선택된 상품코드
	private String[] arrSapL3Cd;	// 일괄로 선택된 SAP L3_CD
	private String[] arrL4Cd;		// 일괄로 선택된 기존 세분류
	
	private String[] delGrpAttr;
	
	private String l1Cd;			// 대분류 코드
	private String cfmFg;			// 확정구분
	private String l4Cd;			// 이전 세분류
	
	public String getAttId() {
		return attId;
	}
	public void setAttId(String attId) {
		this.attId = attId;
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
	
	public String[] getArrGrpAttr() {
		if (this.arrGrpAttr != null) {
			String[] ret = new String[arrGrpAttr.length];
			for (int i = 0; i < arrGrpAttr.length; i++) { 
				ret[i] = this.arrGrpAttr[i]; 
			}
			return ret;
		} else {
			return null;
		}		
	}
	
	public void setArrGrpAttr(String[] arrGrpAttr) {
		 if (arrGrpAttr != null) {
			 this.arrGrpAttr = new String[arrGrpAttr.length];			 
			 for (int i = 0; i < arrGrpAttr.length; ++i) {
				 this.arrGrpAttr[i] = arrGrpAttr[i];
			 }
		 } else {
			 this.arrGrpAttr = null;
		 }
	}
	
	public String[] getArrVal() {
		if (this.arrVal != null) {
			String[] ret = new String[arrVal.length];
			for (int i = 0; i < arrVal.length; i++) { 
				ret[i] = this.arrVal[i]; 
			}
			return ret;
		} else {
			return null;
		}		
	}
	
	public void setArrVal(String[] arrVal) {
		 if (arrVal != null) {
			 this.arrVal = new String[arrVal.length];			 
			 for (int i = 0; i < arrVal.length; ++i) {
				 this.arrVal[i] = arrVal[i];
			 }
		 } else {
			 this.arrVal = null;
		 }

	}
	public String getNewProductCode() {
		return newProductCode;
	}
	public void setNewProductCode(String newProductCode) {
		this.newProductCode = newProductCode;
	}
	public String getSellCode() {
		return sellCode;
	}
	public void setSellCode(String sellCode) {
		this.sellCode = sellCode;
	}
	public String getSrchL4Cd() {
		return srchL4Cd;
	}
	public void setSrchL4Cd(String srchL4Cd) {
		this.srchL4Cd = srchL4Cd;
	}
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	public String getEntpCd() {
		return entpCd;
	}
	public void setEntpCd(String entpCd) {
		this.entpCd = entpCd;
	}	
	
	public String[] getArrGrpAttrTyp() {
		if (this.arrGrpAttrTyp != null) {
			String[] ret = new String[arrGrpAttrTyp.length];
			for (int i = 0; i < arrGrpAttrTyp.length; i++) { 
				ret[i] = this.arrGrpAttrTyp[i]; 
			}
			return ret;
		} else {
			return null;
		}		
	}
	
	public void setArrGrpAttrTyp(String[] arrGrpAttrTyp) {
		 if (arrGrpAttrTyp != null) {
			 this.arrGrpAttrTyp = new String[arrGrpAttrTyp.length];			 
			 for (int i = 0; i < arrGrpAttrTyp.length; ++i) {
				 this.arrGrpAttrTyp[i] = arrGrpAttrTyp[i];
			 }
		 } else {
			 this.arrGrpAttrTyp = null;
		 }
	}
	public String getAttIdTyp() {
		return attIdTyp;
	}
	public void setAttIdTyp(String attIdTyp) {
		this.attIdTyp = attIdTyp;
	}
	public String getSapL3Cd() {
		return sapL3Cd;
	}
	public void setSapL3Cd(String sapL3Cd) {
		this.sapL3Cd = sapL3Cd;
	}	
	
	public String[] getDelGrpAttr() {
		if (this.delGrpAttr != null) {
			String[] ret = new String[delGrpAttr.length];
			for (int i = 0; i < delGrpAttr.length; i++) { 
				ret[i] = this.delGrpAttr[i]; 
			}
			return ret;
		} else {
			return null;
		}		
	}
	
	public void setDelGrpAttr(String[] delGrpAttr) {
		 if (delGrpAttr != null) {
			 this.delGrpAttr = new String[delGrpAttr.length];			 
			 for (int i = 0; i < delGrpAttr.length; ++i) {
				 this.delGrpAttr[i] = delGrpAttr[i];
			 }
		 } else {
			 this.delGrpAttr = null;
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
	
	
	
	
	public String getL1Cd() {
		return l1Cd;
	}
	public void setL1Cd(String l1Cd) {
		this.l1Cd = l1Cd;
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
	public String getCfmFg() {
		return cfmFg;
	}
	public void setCfmFg(String cfmFg) {
		this.cfmFg = cfmFg;
	}
	
	
	public String[] getArrSapL3Cd() {
		if (this.arrSapL3Cd != null) {
			String[] ret = new String[arrSapL3Cd.length];
			for (int i = 0; i < arrSapL3Cd.length; i++) { 
				ret[i] = this.arrSapL3Cd[i]; 
			}
			return ret;
		} else {
			return null;
		}		
	}
	
	public void setArrSapL3Cd(String[] arrSapL3Cd) {
		 if (arrSapL3Cd != null) {
			 this.arrSapL3Cd = new String[arrSapL3Cd.length];			 
			 for (int i = 0; i < arrSapL3Cd.length; ++i) {
				 this.arrSapL3Cd[i] = arrSapL3Cd[i];
			 }
		 } else {
			 this.arrSapL3Cd = null;
		 }
	}
	
	
	public String[] getArrL4Cd() {
		if (this.arrL4Cd != null) {
			String[] ret = new String[arrL4Cd.length];
			for (int i = 0; i < arrL4Cd.length; i++) { 
				ret[i] = this.arrL4Cd[i]; 
			}
			return ret;
		} else {
			return null;
		}		
	}
	
	public void setArrL4Cd(String[] arrL4Cd) {
		 if (arrL4Cd != null) {
			 this.arrL4Cd = new String[arrL4Cd.length];			 
			 for (int i = 0; i < arrL4Cd.length; ++i) {
				 this.arrL4Cd[i] = arrL4Cd[i];
			 }
		 } else {
			 this.arrL4Cd = null;
		 }
	}
	public String getL4Cd() {
		return l4Cd;
	}
	public void setL4Cd(String l4Cd) {
		this.l4Cd = l4Cd;
	}
	
	
	
}
