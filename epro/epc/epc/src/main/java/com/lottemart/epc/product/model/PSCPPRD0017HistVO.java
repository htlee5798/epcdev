package com.lottemart.epc.product.model;

import java.io.Serializable;

/**
 *  
 * @Class Name : PSCPPRD0017VO
 * @Description :
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자	   수정내용
 *  -------    --------    ---------------------------
 * 2011. 12. 09. 오후 03:03:03 mjChoi
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
public class PSCPPRD0017HistVO implements Serializable {

	private static final long serialVersionUID = -115670070784727574L;

	private String keywordSeq = "";

	private String prodCd = "";

	private String seq = "";

	private String searchKywrd = "";

	private String keyCount = "";

	private String byteChk = "";

	private String regId = "";

	private String totalKywrd = "";

	private String num = "";

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

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getSearchKywrd() {
		return searchKywrd;
	}

	public void setSearchKywrd(String searchKywrd) {
		this.searchKywrd = searchKywrd;
	}

	public String getKeyCount() {
		return keyCount;
	}

	public void setKeyCount(String keyCount) {
		this.keyCount = keyCount;
	}

	public String getByteChk() {
		return byteChk;
	}

	public void setByteChk(String byteChk) {
		this.byteChk = byteChk;
	}

	public String getRegId() {
		return regId;
	}

	public void setRegId(String regId) {
		this.regId = regId;
	}

	public String getTotalKywrd() {
		return totalKywrd;
	}

	public void setTotalKywrd(String totalKywrd) {
		this.totalKywrd = totalKywrd;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}
}