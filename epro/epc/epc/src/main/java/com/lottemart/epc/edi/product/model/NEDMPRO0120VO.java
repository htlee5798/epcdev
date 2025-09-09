package com.lottemart.epc.edi.product.model;

import java.io.Serializable;

import com.lcnjf.util.DateUtil;
import com.lottemart.epc.edi.comm.model.PagingVO;

public class NEDMPRO0120VO extends PagingVO implements Serializable {

	static final long serialVersionUID = 1776508153203875384L;
	
	/** 협력업체 */
	private String srchEntpCode;
	/** 판매(88)코드 */
	private String srchSellCode;
	/**  */
	private String srchOneStrCd;
	/** */
	private String downloadFlag;
	
	/**  */
	private String rnum;
	/**  */
	private String snum;
	/** 점포코드 */
	private String strCd;
	/** 점포명 */
	private String strNm;
	/** 상품번호 */
	private String prodCd;
	/** 상품명 */
	private String prodNm;
	/** 판매코드 */
	private String srcmkCd;
	/** 협력업체코드 */
	private String venCd;
	/** 상태값 */
	private String codeStaus;
	/** 등록일자 */
	private String regDt;
	
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
	public String getSrchOneStrCd() {
		return srchOneStrCd;
	}
	public void setSrchOneStrCd(String srchOneStrCd) {
		this.srchOneStrCd = srchOneStrCd;
	}
	public String getDownloadFlag() {
		return downloadFlag;
	}
	public void setDownloadFlag(String downloadFlag) {
		this.downloadFlag = downloadFlag;
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
	public String getStrCd() {
		return strCd;
	}
	public void setStrCd(String strCd) {
		this.strCd = strCd;
	}
	public String getStrNm() {
		return strNm;
	}
	public void setStrNm(String strNm) {
		this.strNm = strNm;
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
	public String getSrcmkCd() {
		return srcmkCd;
	}
	public void setSrcmkCd(String srcmkCd) {
		this.srcmkCd = srcmkCd;
	}
	public String getVenCd() {
		return venCd;
	}
	public void setVenCd(String venCd) {
		this.venCd = venCd;
	}
	public String getCodeStaus() {
		return codeStaus;
	}
	public void setCodeStaus(String codeStaus) {
		this.codeStaus = codeStaus;
	}
	public String getRegDt() {
		return regDt;
	}
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}
	public String getRegDtFmt() {
		if(regDt !=null && !"".equals(regDt) && DateUtil.isDate(regDt, "yyyyMMdd")) {
			return DateUtil.string2String(regDt, "yyyyMMdd", DateUtil.DATE_PATTERN);
		}
		return "";
	}
	
}
