package com.lottemart.epc.edi.product.model;

import java.io.Serializable;

import com.lottemart.epc.edi.comm.model.PagingVO;

public class NEDMPRO0190VO extends PagingVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String srchEntpCd;      // 업체코드
	private String srchL1Cd;        // 대분류
	private String srchSellCd;      // 판매코드
	private String[] srchSellCds;   // 판매코드 복수
	private String srchAprvStat;    // 승인상태
	private String[] venCds;        // 업체코드 복수
	
	private String aprvStat;        // 승인상태
	private String prodCd;			// 상품코드
	private String l3Cd;  			// 소분류
	
	public String getSrchEntpCd() {
		return srchEntpCd;
	}
	
	public void setSrchEntpCd(String srchEntpCd) {
		this.srchEntpCd = srchEntpCd;
	}
	
	public String getSrchL1Cd() {
		return srchL1Cd;
	}
	
	public void setSrchL1Cd(String srchL1Cd) {
		this.srchL1Cd = srchL1Cd;
	}
	
	public String getSrchSellCd() {
		return srchSellCd;
	}
	
	public void setSrchSellCd(String srchSellCd) {
		this.srchSellCd = srchSellCd;
	}
	
	public String[] getSrchSellCds() {
		if (this.srchSellCds != null) {
			String[] ret = new String[srchSellCds.length];
			for (int i = 0; i < srchSellCds.length; i++) { 
				ret[i] = this.srchSellCds[i]; 
			}
			return ret;
		} else {
			return null;
		}
	}
	
	public void setSrchSellCds(String[] srchSellCds) {
		if (srchSellCds != null) {
			 this.srchSellCds = new String[srchSellCds.length];			 
			 for (int i = 0; i < srchSellCds.length; ++i) {
				 this.srchSellCds[i] = srchSellCds[i];
			 }
		 } else {
			 this.srchSellCds = null;
		 }
	}
	
	public String getSrchAprvStat() {
		return srchAprvStat;
	}
	
	public void setSrchAprvStat(String srchAprvStat) {
		this.srchAprvStat = srchAprvStat;
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
	
	public String getProdCd() {
		return prodCd;
	}
	
	public void setProdCd(String prodCd) {
		this.prodCd = prodCd;
	}
	
	public String getAprvStat() {
		return aprvStat;
	}
	
	public void setAprvStat(String aprvStat) {
		this.aprvStat = aprvStat;
	}
	
	public String getL3Cd() {
		return l3Cd;
	}
	
	public void setL3Cd(String l3Cd) {
		this.l3Cd = l3Cd;
	}
}
