package com.lottemart.epc.order.model;

import java.io.Serializable;

public class PSCMORD0010VO implements Serializable {
	private static final long serialVersionUID = -1076051384328306836L;
	//순번
	private int num;
	// 대분류
	private String l1Nm = "";
	// 중분류
	private String l2Nm = "";
	// 상품코드
	private String prodCd = "";
	// 상품명
	private String prodNm = "";
	// ITEM_CD
	private String itemCd = "";
	// 이익률
	private double profitRate;
	// 원가
	private int buyPrc;
	// 판매가
	private int currSellPrc;
	// 총 판매 수량
	private int ordQty;
	// 총 판매 금액
	private int ordAmt;

	public String getL1Nm() {
		return l1Nm;
	}

	public void setL1Nm(String l1Nm) {
		this.l1Nm = l1Nm;
	}

	public String getL2Nm() {
		return l2Nm;
	}

	public void setL2Nm(String l2Nm) {
		this.l2Nm = l2Nm;
	}

	public String getProdCd() {
		return prodCd;
	}

	public void setProdCd(String prodCd) {
		this.prodCd = prodCd;
	}

	public String getProdNm() {
		return prodNm;
	}

	public void setProdNm(String prodNm) {
		this.prodNm = prodNm;
	}

	public String getItemCd() {
		return itemCd;
	}

	public void setItemCd(String itemCd) {
		this.itemCd = itemCd;
	}

	public double getProfitRate() {
		return profitRate;
	}

	public void setProfitRate(double profitRate) {
		this.profitRate = profitRate;
	}

	public int getBuyPrc() {
		return buyPrc;
	}

	public void setBuyPrc(int buyPrc) {
		this.buyPrc = buyPrc;
	}

	public int getCurrSellPrc() {
		return currSellPrc;
	}

	public void setCurrSellPrc(int currSellPrc) {
		this.currSellPrc = currSellPrc;
	}

	public int getOrdQty() {
		return ordQty;
	}

	public void setOrdQty(int ordQty) {
		this.ordQty = ordQty;
	}

	public int getOrdAmt() {
		return ordAmt;
	}

	public void setOrdAmt(int ordAmt) {
		this.ordAmt = ordAmt;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

}
