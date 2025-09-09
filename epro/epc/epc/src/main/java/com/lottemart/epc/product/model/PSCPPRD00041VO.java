package com.lottemart.epc.product.model;

import java.io.Serializable;
import java.util.List;

public class PSCPPRD00041VO implements Serializable {

	private static final long serialVersionUID = 9132805425930407823L;
	
	private String seq;

	private String prodCd;

	private String themaSeq;

	private String themaNm;

	private String orderSeq;

	private String repProdExistsYn;

	private String mainProdCd;

	private String mainProdNm;

	private String prodQty;
	
	private String themaProd;
	
	private String regId;

	private String modId;

	private List<PSCPPRD0004VO> themaProdList;


	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getProdCd() {
		return prodCd;
	}

	public void setProdCd(String prodCd) {
		this.prodCd = prodCd;
	}

	public String getThemaSeq() {
		return themaSeq;
	}

	public void setThemaSeq(String themaSeq) {
		this.themaSeq = themaSeq;
	}

	public String getThemaNm() {
		return themaNm;
	}

	public void setThemaNm(String themaNm) {
		this.themaNm = themaNm;
	}

	public String getOrderSeq() {
		return orderSeq;
	}

	public void setOrderSeq(String orderSeq) {
		this.orderSeq = orderSeq;
	}

	public String getRepProdExistsYn() {
		return repProdExistsYn;
	}

	public void setRepProdExistsYn(String repProdExistsYn) {
		this.repProdExistsYn = repProdExistsYn;
	}

	public String getMainProdCd() {
		return mainProdCd;
	}

	public void setMainProdCd(String mainProdCd) {
		this.mainProdCd = mainProdCd;
	}

	public String getMainProdNm() {
		return mainProdNm;
	}

	public void setMainProdNm(String mainProdNm) {
		this.mainProdNm = mainProdNm;
	}

	public String getProdQty() {
		return prodQty;
	}

	public void setProdQty(String prodQty) {
		this.prodQty = prodQty;
	}

	public String getThemaProd() {
		return themaProd;
	}

	public void setThemaProd(String themaProd) {
		this.themaProd = themaProd;
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

	public List<PSCPPRD0004VO> getThemaProdList() {
		return themaProdList;
	}

	public void setThemaProdList(List<PSCPPRD0004VO> themaProdList) {
		this.themaProdList = themaProdList;
	}

}
