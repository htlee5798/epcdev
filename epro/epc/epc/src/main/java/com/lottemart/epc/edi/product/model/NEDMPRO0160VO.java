package com.lottemart.epc.edi.product.model;

import java.io.Serializable;
import java.util.ArrayList;

import com.lottemart.epc.edi.comm.model.PagingVO;

public class NEDMPRO0160VO extends PagingVO implements Serializable {

	static final long serialVersionUID = 1776508153203875384L;
	
	/** 협력업체코드 */
	private String srchEntpCd;
	/** 판매(88)코드 */
	private String srchSrcmkCd;
	/** */
	private String downloadFlag;
	/** 협력업체 코드 */
	private String[] venCds;
	/** 순번 */
	private String rnum;
	/** 이벤트순번 */
	private String seq;
	/** 이벤트명 */
	private String seqNm;
	/** 업체코드 */
	private String venCd;
	/** 업체명 */
	private String venNm;
	/** 대분류명 */
	private String l1Nm;
	/** 중분류명 */
	private String l2Nm;
	/** 소분류명 */
	private String l3Nm;
	/** 상품코드 */
	private String prodCd;
	/** 판매코드 */
	private String srcmkCd;
	/** 상품명 */
	private String prodNm;
	/** 거래형태 */
	private String trdTypNm;
	/** 점포구분 */
	private String strFg;
	
	public String getSrchEntpCd() {
		return srchEntpCd;
	}
	public void setSrchEntpCd(String srchEntpCd) {
		this.srchEntpCd = srchEntpCd;
	}
	public String getSrchSrcmkCd() {
		return srchSrcmkCd;
	}
	public void setSrchSrcmkCd(String srchSrcmkCd) {
		this.srchSrcmkCd = srchSrcmkCd;
	}
	public String getDownloadFlag() {
		return downloadFlag;
	}
	public void setDownloadFlag(String downloadFlag) {
		this.downloadFlag = downloadFlag;
	}
	public String[] getVenCds() {
		if (this.venCds != null) {
			String[] ret = new String[venCds.length];
			for (int i = 0; i < venCds.length; i++) { 
				ret[i] = this.venCds[i]; 
			}
			return ret;
		} else {
			return null;
		}
	}
	public void setVenCds(String[] venCds) {
		if (venCds != null) {
			 this.venCds = new String[venCds.length];			 
			 for (int i = 0; i < venCds.length; ++i) {
				 this.venCds[i] = venCds[i];
			 }
		 } else {
			 this.venCds = null;
		 }
	}
	public String getRnum() {
		return rnum;
	}
	public void setRnum(String rnum) {
		this.rnum = rnum;
	}

	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getSeqNm() {
		return seqNm;
	}
	public void setSeqNm(String seqNm) {
		this.seqNm = seqNm;
	}
	public String getVenCd() {
		return venCd;
	}
	public void setVenCd(String venCd) {
		this.venCd = venCd;
	}
	public String getVenNm() {
		return venNm;
	}
	public void setVenNm(String venNm) {
		this.venNm = venNm;
	}
	
	public String getl1Nm() {
		return l1Nm;
	}
	public void setl1Nm(String l1Nm) {
		this.l1Nm = l1Nm;
	}
	
	public String getl2Nm() {
		return l2Nm;
	}
	public void setl2Nm(String l2Nm) {
		this.l2Nm = l2Nm;
	}
	
	public String getl3Nm() {
		return l3Nm;
	}
	public void setl3Nm(String l3Nm) {
		this.l3Nm = l3Nm;
	}
	
	
	public String getProdCd() {
		return prodCd;
	}
	public void setProdCd(String prodCd) {
		this.prodCd = prodCd;
	}
	public String getSrcmkCd() {
		return srcmkCd;
	}
	public void setSrcmkCd(String srcmkCd) {
		this.srcmkCd = srcmkCd;
	}
	public String getProdNm() {
		return prodNm;
	}
	public void setProdNm(String prodNm) {
		this.prodNm = prodNm;
	}
	
	public String gettrdTypNm() {
		return trdTypNm;
	}
	public void settrdTypNm(String trdTypNm) {
		this.trdTypNm = trdTypNm;
	}
	
	public String getstrFg() {
		return strFg;
	}
	public void setstrFg(String strFg) {
		this.strFg = strFg;
	}

	
}
