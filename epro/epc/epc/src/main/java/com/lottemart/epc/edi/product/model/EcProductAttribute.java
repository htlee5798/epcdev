package com.lottemart.epc.edi.product.model;

import java.io.Serializable;

public class EcProductAttribute implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4648172851372268684L;

	private String prodCd;
	private String pgmId;
	private String itemCd;
	private String attrPiType;
	private String stdCatCd;
	private String attrId;
	private String attrValId;
	private String attrValNm;
	private String attrVal;
	private String attrDtlVal;
	private String attrCodeUseYn;
	private String useYn;
	private String regId;
	
	public String getProdCd() {
		return prodCd;
	}
	public void setProdCd(String prodCd) {
		this.prodCd = prodCd;
	}
	public String getPgmId() {
		return pgmId;
	}
	public void setPgmId(String pgmId) {
		this.pgmId = pgmId;
	}
	public String getItemCd() {
		return itemCd;
	}
	public void setItemCd(String itemCd) {
		this.itemCd = itemCd;
	}
	public String getAttrPiType() {
		return attrPiType;
	}
	public void setAttrPiType(String attrPiType) {
		this.attrPiType = attrPiType;
	}
	public String getStdCatCd() {
		return stdCatCd;
	}
	public void setStdCatCd(String stdCatCd) {
		this.stdCatCd = stdCatCd;
	}
	public String getAttrId() {
		return attrId;
	}
	public void setAttrId(String attrId) {
		this.attrId = attrId;
	}
	public String getAttrValId() {
		return attrValId;
	}
	public void setAttrValId(String attrValId) {
		this.attrValId = attrValId;
	}
	public String getAttrValNm() {
		return attrValNm;
	}
	public void setAttrValNm(String attrValNm) {
		this.attrValNm = attrValNm;
	}
	public String getAttrVal() {
		return attrVal;
	}
	public void setAttrVal(String attrVal) {
		this.attrVal = attrVal;
	}
	public String getAttrDtlVal() {
		return attrDtlVal;
	}
	public void setAttrDtlVal(String attrDtlVal) {
		this.attrDtlVal = attrDtlVal;
	}
	public String getAttrCodeUseYn() {
		return attrCodeUseYn;
	}
	public void setAttrCodeUseYn(String attrCodeUseYn) {
		this.attrCodeUseYn = attrCodeUseYn;
	}
	public String getUseYn() {
		return useYn;
	}
	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
}
