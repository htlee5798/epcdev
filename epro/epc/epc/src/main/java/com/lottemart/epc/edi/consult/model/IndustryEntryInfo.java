package com.lottemart.epc.edi.consult.model;

import java.io.Serializable;
import java.util.Date;

public class IndustryEntryInfo implements Serializable {

	
	private String sorterFlag;
	private String weight;
	private String height;
	private String length;
	private String width;
	private String logistBarcode;
	private String productName;
	private String productConfirmCode;
	private String logistConfirmFlag;
	private Date registDate;
	
	
	
	public String getSorterFlag() {
		return sorterFlag;
	}
	public void setSorterFlag(String sorterFlag) {
		this.sorterFlag = sorterFlag;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getLength() {
		return length;
	}
	public void setLength(String length) {
		this.length = length;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public String getLogistBarcode() {
		return logistBarcode;
	}
	public void setLogistBarcode(String logistBarcode) {
		this.logistBarcode = logistBarcode;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductConfirmCode() {
		return productConfirmCode;
	}
	public void setProductConfirmCode(String productConfirmCode) {
		this.productConfirmCode = productConfirmCode;
	}
	public String getLogistConfirmFlag() {
		return logistConfirmFlag;
	}
	public void setLogistConfirmFlag(String logistConfirmFlag) {
		this.logistConfirmFlag = logistConfirmFlag;
	}
	public Date getRegistDate() {
		return registDate;
	}
	public void setRegistDate(Date registDate) {
		this.registDate = registDate;
	}
	
	
	
	
}
