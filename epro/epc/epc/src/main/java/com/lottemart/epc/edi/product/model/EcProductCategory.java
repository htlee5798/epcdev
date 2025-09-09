package com.lottemart.epc.edi.product.model;

import java.io.Serializable;

public class EcProductCategory implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8737941835121763447L;

	private String prodCd;
	private String pgmId;
	private String stdCatCd;		//EC 표준 카테고리
	private String dispCatCd;		//EC 전시 카테고리
	private String dispYn;
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
	public String getStdCatCd() {
		return stdCatCd;
	}
	public void setStdCatCd(String stdCatCd) {
		this.stdCatCd = stdCatCd;
	}
	public String getDispCatCd() {
		return dispCatCd;
	}
	public void setDispCatCd(String dispCatCd) {
		this.dispCatCd = dispCatCd;
	}
	public String getDispYn() {
		return dispYn;
	}
	public void setDispYn(String dispYn) {
		this.dispYn = dispYn;
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
