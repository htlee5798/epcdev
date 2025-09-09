package com.lottemart.epc.product.model;

import java.io.Serializable;

public class PSCPPRD0025HistVO implements Serializable{

	private static final long serialVersionUID = 8399559853845415422L;
	
	private String keywordSeq = "";
	private String prodCd = "";
	private String keyword	= "";
	private String keywordString = "";
	private String regId = "";
	private String modId = "";
	

	public String getKeywordSeq() {
		return keywordSeq;
	}
	public void setKeywordSeq(String keywordSeq) {
		this.keywordSeq = keywordSeq;
	}
	public String getProdCd() {
		return prodCd;
	}
	public void setProdCd(String prodCd) {
		this.prodCd = prodCd;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getKeywordString() {
		return keywordString;
	}
	public void setKeywordString(String keywordString) {
		this.keywordString = keywordString;
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
}
