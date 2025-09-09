package com.lottemart.epc.product.model;

import java.io.Serializable;

/**
 * 
 * @author hjKim
 * @Description : 상품관리 - 대표상품코드관리
 * @Class : com.lottemart.bos.product.model
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011.12.16  hjKim
 * @version : 
 * </pre>
 */
public class PSCMPRD0021VO implements Serializable{

	/** 
	 * @see 
	 * @Description : 
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -9834076763987858L;

	private String prodCd 			= "";	/* 인터넷상품코드 */
	private String repProdCd 		= "";	/* 대표판매코드 */
	private String applyStartDy 	= "";	/* 적용시작일자 */
	//대표상품코드 이력에 종료일자 등록할 수 있또록 수정 2012-03-08 임재유
	private String applyEndDy 	= "";	/* 적용시작일자 */
	
	private String sellPrc 			= "";	/* 매가 */
	private String currSellPrc 		= "";	/* 판매가 */
	private String profitRate 		= "";	/* 이익율 */
	private String taxatDivnCd 		= "";	/* 과세구분코드 */
	
	private String regId 			= "";	/* 등록자 */
	private String modId 			= "";	/* 수정자 */
	
	private String applyStartDy_old 	= ""; /* update 할 대상의 적용시작일자 */
	
	private String vendorId			= "";
	
	
	public String getProdCd() {
		return prodCd;
	}
	public void setProdCd(String prodCd) {
		this.prodCd = prodCd;
	}
	public String getRepProdCd() {
		return repProdCd;
	}
	public void setRepProdCd(String repProdCd) {
		this.repProdCd = repProdCd;
	}
	public String getApplyStartDy() {
		return applyStartDy;
	}
	public void setApplyStartDy(String applyStartDy) {
		this.applyStartDy = applyStartDy;
	}
	
	public String getApplyEndDy() {
		return applyEndDy;
	}
	public void setApplyEndDy(String applyEndDy) {
		this.applyEndDy = applyEndDy;
	}
	public String getSellPrc() {
		return sellPrc;
	}
	public void setSellPrc(String sellPrc) {
		this.sellPrc = sellPrc;
	}
	public String getCurrSellPrc() {
		return currSellPrc;
	}
	public void setCurrSellPrc(String currSellPrc) {
		this.currSellPrc = currSellPrc;
	}
	public String getProfitRate() {
		return profitRate;
	}
	public void setProfitRate(String profitRate) {
		this.profitRate = profitRate;
	}
	public String getTaxatDivnCd() {
		return taxatDivnCd;
	}
	public void setTaxatDivnCd(String taxatDivnCd) {
		this.taxatDivnCd = taxatDivnCd;
	}
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	public String getModId() {
		return modId;
	}
	public void setModId(String modId) {
		this.modId = modId;
	}
	public String getApplyStartDy_old() {
		return applyStartDy_old;
	}
	public void setApplyStartDy_old(String applyStartDy_old) {
		this.applyStartDy_old = applyStartDy_old;
	}
	public String getVendorId() {
		return vendorId;
	}
	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}
	
	
	
}
