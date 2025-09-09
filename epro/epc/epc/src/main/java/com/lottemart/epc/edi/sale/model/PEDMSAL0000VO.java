package com.lottemart.epc.edi.sale.model;

import java.io.Serializable;

public class PEDMSAL0000VO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6766182773315305924L;

	public PEDMSAL0000VO() {}

	/*매출일자*/
	private String stkDy;	
	
	/*매출수량*/
	private String saleQty;
	
	/*매출금액*/
	private String saleAmt;
	
	/*점포명*/
	private String strNm; 
	
	/*점포코드*/
	private String strCd;
	
	/*누계매출수량*/
	private String sumSaleQty;
	
	/*누계금액*/
	private String sumSaleAmt;
	
	/*판매코드*/
	private String srcmkCd;
	
	/*상품명*/
	private String prodNm;
	
	/*상푸모드*/
	private String prodCd;
	
	
	private String saleSaleAmt;
	

	public String getStkDy() {
		return stkDy;
	}
	public void setStkDy(String stkDy) {
		this.stkDy = stkDy;
	}
	public String getSaleQty() {
		return saleQty;
	}
	public void setSaleQty(String saleQty) {
		this.saleQty = saleQty;
	}
	public String getSaleAmt() {
		return saleAmt;
	}
	public void setSaleAmt(String saleAmt) {
		this.saleAmt = saleAmt;
	}
	public String getStrNm() {
		return strNm;
	}
	public void setStrNm(String strNm) {
		this.strNm = strNm;
	}
	public String getStrCd() {
		return strCd;
	}
	public void setStrCd(String strCd) {
		this.strCd = strCd;
	}
	public String getSumSaleQty() {
		return sumSaleQty;
	}
	public void setSumSaleQty(String sumSaleQty) {
		this.sumSaleQty = sumSaleQty;
	}
	public String getSumSaleAmt() {
		return sumSaleAmt;
	}
	public void setSumSaleAmt(String sumSaleAmt) {
		this.sumSaleAmt = sumSaleAmt;
	}
	public String getSrcmkCd() {
		return srcmkCd;
	}
	public void setSrcmkCd(String srcmkCd) {
		this.srcmkCd = srcmkCd;
	}
	public String getProdNm() {
		return prodNm;
	}
	public void setProdNm(String prodNm) {
		this.prodNm = prodNm;
	}
	public String getProdCd() {
		return prodCd;
	}
	public void setProdCd(String prodCd) {
		this.prodCd = prodCd;
	}
	public String getSaleSaleAmt() {
		return saleSaleAmt;
	}
	public void setSaleSaleAmt(String saleSaleAmt) {
		this.saleSaleAmt = saleSaleAmt;
	}
	
	
	
}
