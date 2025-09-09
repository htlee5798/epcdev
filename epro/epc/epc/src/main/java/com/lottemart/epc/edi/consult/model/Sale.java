package com.lottemart.epc.edi.consult.model;

import java.io.Serializable;

public class Sale implements Serializable {

	private String businessNo;
	private String otherStoreCode;
	private int enteredStoreCount;
	private int marginRate1;
	private int marginRate2;
	private int subAmount;
	private int buyAmount;
	private String tradeTypeFlag;
	private String tradeTypeContent;
	private String otherStoreName;
	private int pcost;
	
	
	
	
	
	
	public String getTradeTypeContent() {
		return tradeTypeContent;
	}
	public void setTradeTypeContent(String tradeTypeContent) {
		this.tradeTypeContent = tradeTypeContent;
	}
	public int getPcost() {
		return pcost;
	}
	public void setPcost(int pcost) {
		this.pcost = pcost;
	}
	public String getBusinessNo() {
		return businessNo;
	}
	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
	}
	public String getOtherStoreCode() {
		return otherStoreCode;
	}
	public void setOtherStoreCode(String otherStoreCode) {
		this.otherStoreCode = otherStoreCode;
	}
	public int getEnteredStoreCount() {
		return enteredStoreCount;
	}
	public void setEnteredStoreCount(int enteredStoreCount) {
		this.enteredStoreCount = enteredStoreCount;
	}
	public int getMarginRate1() {
		return marginRate1;
	}
	public void setMarginRate1(int marginRate1) {
		this.marginRate1 = marginRate1;
	}
	public int getMarginRate2() {
		return marginRate2;
	}
	public void setMarginRate2(int marginRate2) {
		this.marginRate2 = marginRate2;
	}
	public int getSubAmount() {
		return subAmount;
	}
	public void setSubAmount(int subAmount) {
		this.subAmount = subAmount;
	}
	public int getBuyAmount() {
		return buyAmount;
	}
	public void setBuyAmount(int buyAmount) {
		this.buyAmount = buyAmount;
	}
	public String getTradeTypeFlag() {
		return tradeTypeFlag;
	}
	public void setTradeTypeFlag(String tradeTypeFlag) {
		this.tradeTypeFlag = tradeTypeFlag;
	}
	public String getOtherStoreName() {
		return otherStoreName;
	}
	public void setOtherStoreName(String otherStoreName) {
		this.otherStoreName = otherStoreName;
	}
	
	
	
}
