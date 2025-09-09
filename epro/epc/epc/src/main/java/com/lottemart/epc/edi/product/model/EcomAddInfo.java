package com.lottemart.epc.edi.product.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class EcomAddInfo implements Serializable {

	private static final long serialVersionUID = -8217038698476076151L;

	public EcomAddInfo() {}


	
	private String newProductCode;
	private String infoGrpCd;
	private String infoColCd;
	private String colVal;

	public String getNewProductCode() {
		return newProductCode;
	}
	public void setNewProductCode(String newProductCode) {
		this.newProductCode = newProductCode;
	}
	public String getInfoGrpCd() {
		return infoGrpCd;
	}
	public void setInfoGrpCd(String infoGrpCd) {
		this.infoGrpCd = infoGrpCd;
	}
	public String getInfoColCd() {
		return infoColCd;
	}
	public void setInfoColCd(String infoColCd) {
		this.infoColCd = infoColCd;
	}
	public String getColVal() {
		return colVal;
	}
	public void setColVal(String colVal) {
		this.colVal = colVal;
	}
	

	

}
