package com.lottemart.epc.edi.product.model;

import java.io.Serializable;

import com.lottemart.epc.edi.comm.model.PagingVO;

public class NEDMPRO0110VO extends PagingVO implements Serializable {

	static final long serialVersionUID = 1776508153203875384L;
	
	/** 협력업체 */
	private String srchEntpCode;
	/** 판매(88)코드 */
	private String srchSellCode;
	/** 상태 */
	private String srchLogiBcdFg;
	/** */
	private String downloadFlag;
	
	/** MD등록일자 */
	private String regDt;
	/** MD판매코드 */
	private String srcmkCd;
	/** 상품번호 */
	private String prodCd;
	/** 상품명 */
	private String prodNm;
	/** 물류바코드 */
	private String logiBcd;
	/** 사용여부 */
	private String useFg;
	/** 가로 */
	private String width;
	/** 세로길이 */
	private String length;
	/** 높이 */
	private String height;
	/** 중량 */
	private String wg;
	/** 소터구분 */
	private String sorterFg;
	/**  */
	private String barDesc;
	/** 혼재구분 */
	private String mixProdFg;
	/**  */
	private String rnum;
	/**  */
	private String snum;
	/** 발주입수 */
	private String ordIpsu;
	
	public String getSrchEntpCode() {
		return srchEntpCode;
	}
	public void setSrchEntpCode(String srchEntpCode) {
		this.srchEntpCode = srchEntpCode;
	}
	public String getSrchSellCode() {
		return srchSellCode;
	}
	public void setSrchSellCode(String srchSellCode) {
		this.srchSellCode = srchSellCode;
	}
	public String getSrchLogiBcdFg() {
		return srchLogiBcdFg;
	}
	public void setSrchLogiBcdFg(String srchLogiBcdFg) {
		this.srchLogiBcdFg = srchLogiBcdFg;
	}
	public String getDownloadFlag() {
		return downloadFlag;
	}
	public void setDownloadFlag(String downloadFlag) {
		this.downloadFlag = downloadFlag;
	}
	public String getRegDt() {
		return regDt;
	}
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}
	public String getSrcmkCd() {
		return srcmkCd;
	}
	public void setSrcmkCd(String srcmkCd) {
		this.srcmkCd = srcmkCd;
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
	public String getLogiBcd() {
		return logiBcd;
	}
	public void setLogiBcd(String logiBcd) {
		this.logiBcd = logiBcd;
	}
	public String getUseFg() {
		return useFg;
	}
	public void setUseFg(String useFg) {
		this.useFg = useFg;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public String getLength() {
		return length;
	}
	public void setLength(String length) {
		this.length = length;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getWg() {
		return wg;
	}
	public void setWg(String wg) {
		this.wg = wg;
	}
	public String getSorterFg() {
		return sorterFg;
	}
	public void setSorterFg(String sorterFg) {
		this.sorterFg = sorterFg;
	}
	public String getBarDesc() {
		return barDesc;
	}
	public void setBarDesc(String barDesc) {
		this.barDesc = barDesc;
	}
	public String getMixProdFg() {
		return mixProdFg;
	}
	public void setMixProdFg(String mixProdFg) {
		this.mixProdFg = mixProdFg;
	}
	public String getRnum() {
		return rnum;
	}
	public void setRnum(String rnum) {
		this.rnum = rnum;
	}
	public String getSnum() {
		return snum;
	}
	public void setSnum(String snum) {
		this.snum = snum;
	}
	public String getOrdIpsu() {
		return ordIpsu;
	}
	public void setOrdIpsu(String ordIpsu) {
		this.ordIpsu = ordIpsu;
	}
	
}
