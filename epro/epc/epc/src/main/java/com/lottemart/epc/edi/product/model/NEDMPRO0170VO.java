package com.lottemart.epc.edi.product.model;

import java.io.Serializable;
import java.util.ArrayList;

import com.lottemart.epc.edi.comm.model.PagingVO;

public class NEDMPRO0170VO extends PagingVO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5221261338424703529L;
	/** 단종적용 일자 */
	private String disconApplyDt;
	/** 협력업체코드 */
	private String[] entpCds;
	/** 상품코드 */
	private String prodCd;
	/** 상품이름 */
	private String prodNm;
	/** 순번 */
	private String rnum;
	/** 업체코드 */
	private String entpCd;
	/** 업체명 */
	private String entpNm;
	/** 등록일시 */
	private String regDt;
	/** 등록시간 */
	private String regTm;
	/** 등록자 */
	private String regNm;
	/** 수정일시 */
	private String modDt;
	/** 수정시간 */
	private String modTm;
	/** 수정자 */
	private String modNm;
	/** 기준일자 */
	private String stdDate;
	/** 상태 */
	private String mstate;
	/** 단품정보 ECO 상태 */
	private String zstat;
	/** 검색한 판매 코드 */
	private String srchSrcmkCd;
	/** 상품 점포 진열수 */
	private String werksCnt;
	/** 단품 정보 상태 */
	private String disconReason;
	/** 판매 코드 */
	private String srcmkCd;
	
	public String getDisconApplyDt() {
		return disconApplyDt;
	}
	public void setDisconApplyDt(String disconApplyDt) {
		this.disconApplyDt = disconApplyDt;
	}
	public String[] getEntpCds() {
		if (this.entpCds != null) {
			String[] ret = new String[entpCds.length];
			for (int i = 0; i < entpCds.length; i++) { 
				ret[i] = this.entpCds[i]; 
			}
			return ret;
		} else {
			return null;
		}
	}
	public void setEntpCds(String[] entpCds) {
		if (entpCds != null) {
			 this.entpCds = new String[entpCds.length];			 
			 for (int i = 0; i < entpCds.length; ++i) {
				 this.entpCds[i] = entpCds[i];
			 }
		 } else {
			 this.entpCds = null;
		 }
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
	public String getRnum() {
		return rnum;
	}
	public void setRnum(String rnum) {
		this.rnum = rnum;
	}
	public String getEntpCd() {
		return entpCd;
	}
	public void setEntpCd(String entpCd) {
		this.entpCd = entpCd;
	}
	public String getEntpNm() {
		return entpNm;
	}
	public void setEntpNm(String entpNm) {
		this.entpNm = entpNm;
	}	
	public String getRegDt() {
		return regDt;
	}
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}
	public String getRegNm() {
		return regNm;
	}
	public void setRegNm(String regNm) {
		this.regNm = regNm;
	}
	public String getRegTm() {
		return regTm;
	}
	public void setRegTm(String regTm) {
		this.regTm = regTm;
	}
	public String getModDt() {
		return modDt;
	}
	public void setModDt(String modDt) {
		this.modDt = modDt;
	}
	public String getModTm() {
		return modTm;
	}
	public void setModTm(String modTm) {
		this.modTm = modTm;
	}
	public String getModNm() {
		return modNm;
	}
	public void setModNm(String modNm) {
		this.modNm = modNm;
	}
	public String getStdDate() {
		return stdDate;
	}
	public void setStdDate(String stdDate) {
		this.stdDate = stdDate;
	}
	public String getMState() {
		return mstate;
	}
	public void setMstate(String mstate) {
		this.mstate = mstate;
	}
	public String getZstat() {
		return zstat;
	}
	public void setZstat(String zstat) {
		this.zstat = zstat;
	}
	public String getSrchSrcmkCd() {
		return srchSrcmkCd;
	}
	public void setSrchSrcmkCd(String srchSrcmkCd) {
		this.srchSrcmkCd = srchSrcmkCd;
	}
	public String getWerksCnt() {
		return werksCnt;
	}
	public void setWerksCnt(String werksCnt) {
		this.werksCnt = werksCnt;
	}
	public String getDisconReason() {
		return disconReason;
	}
	public void setDisconReason(String disconReason) {
		this.disconReason = disconReason;
	}
	public String getSrcmkCd() {
		return srcmkCd;
	}
	public void setSrcmkCd(String srcmkCd) {
		this.srcmkCd = srcmkCd;
	}
}
