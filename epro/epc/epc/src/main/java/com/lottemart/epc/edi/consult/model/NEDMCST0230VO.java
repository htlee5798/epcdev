package com.lottemart.epc.edi.consult.model;

import java.io.Serializable;

import com.lottemart.epc.edi.comm.model.PagingVO;

public class NEDMCST0230VO extends PagingVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1389842604426904701L;
	
	/** 검색 */
	private String srchStartDate;			
	private String srchEndDate;			
	private String srchEntpCd;		
	private String srchProcStat;	
	
	private String[] venCds;		/** 협력업체 코드 */
	
	private String rnum;			/** rownum */
	private String snum;			/** rownum */
	private String slipNo;			/** 전표번호 */
	private String werks;			/** 등록점포 */
	private String regDy;			/** 접수일자 */
	private String custNm;			/** 고객명 */
	private String hpNo;			/** 연락처 */
	private String zip;				/** 우편번호 */
	private String newAddrFg;		/** 새주소 사용구분 */
	private String addr1;			/** 주소1 */
	private String addr2;			/** 주소2 */
	private String addr3;			/** 주소3 */
	private String newAddr1;		/** 새주소1 */
	private String newAddr2;		/** 새주소2 */
	private String newAddr3;		/** 새주소3 */
	private String newAddr4;		/** 새주소4 */
	private String ean11;			/** 판매코드 */
	private String prodCd;			/** 상품코드 */
	private String prodNm;			/** 상품명 */
	private String lifnr;			/** 업체코드 */
	private String reqCmt;			/** 접수내용 */
	private String procStat;		/** 진행상태 */
	private String procStatNm;		/** 진행상태명 */
	private String ifDt;			/** 인터페이스 일시 */
	private String repairScheDt;	/** 수리예정일 */
	private String repairCompDt;	/** 수리완료일 */
	private String transCompDt;		/** 인도완료일 */
	
	
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
	
	
	public String getSlipNo() {
		return slipNo;
	}
	public void setSlipNo(String slipNo) {
		this.slipNo = slipNo;
	}
	public String getWerks() {
		return werks;
	}
	public void setWerks(String werks) {
		this.werks = werks;
	}
	public String getRegDy() {
		return regDy;
	}
	public void setRegDy(String regDy) {
		this.regDy = regDy;
	}
	public String getCustNm() {
		return custNm;
	}
	public void setCustNm(String custNm) {
		this.custNm = custNm;
	}
	public String getHpNo() {
		return hpNo;
	}
	public void setHpNo(String hpNo) {
		this.hpNo = hpNo;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getNewAddrFg() {
		return newAddrFg;
	}
	public void setNewAddrFg(String newAddrFg) {
		this.newAddrFg = newAddrFg;
	}
	public String getAddr1() {
		return addr1;
	}
	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}
	public String getAddr2() {
		return addr2;
	}
	public void setAddr2(String addr2) {
		this.addr2 = addr2;
	}
	public String getAddr3() {
		return addr3;
	}
	public void setAddr3(String addr3) {
		this.addr3 = addr3;
	}
	public String getNewAddr1() {
		return newAddr1;
	}
	public void setNewAddr1(String newAddr1) {
		this.newAddr1 = newAddr1;
	}
	public String getNewAddr2() {
		return newAddr2;
	}
	public void setNewAddr2(String newAddr2) {
		this.newAddr2 = newAddr2;
	}
	public String getNewAddr3() {
		return newAddr3;
	}
	public void setNewAddr3(String newAddr3) {
		this.newAddr3 = newAddr3;
	}
	public String getNewAddr4() {
		return newAddr4;
	}
	public void setNewAddr4(String newAddr4) {
		this.newAddr4 = newAddr4;
	}
	public String getEan11() {
		return ean11;
	}
	public void setEan11(String ean11) {
		this.ean11 = ean11;
	}
	public String getProdCd() {
		return prodCd;
	}
	public void setProdCd(String prodCd) {
		this.prodCd = prodCd;
	}
	public String getLifnr() {
		return lifnr;
	}
	public void setLifnr(String lifnr) {
		this.lifnr = lifnr;
	}
	public String getReqCmt() {
		return reqCmt;
	}
	public void setReqCmt(String reqCmt) {
		this.reqCmt = reqCmt;
	}
	public String getProcStat() {
		return procStat;
	}
	public void setProcStat(String procStat) {
		this.procStat = procStat;
	}
	public String getIfDt() {
		return ifDt;
	}
	public void setIfDt(String ifDt) {
		this.ifDt = ifDt;
	}
	public String getRepairScheDt() {
		return repairScheDt;
	}
	public void setRepairScheDt(String repairScheDt) {
		this.repairScheDt = repairScheDt;
	}
	public String getRepairCompDt() {
		return repairCompDt;
	}
	public void setRepairCompDt(String repairCompDt) {
		this.repairCompDt = repairCompDt;
	}
	public String getTransCompDt() {
		return transCompDt;
	}
	public void setTransCompDt(String transCompDt) {
		this.transCompDt = transCompDt;
	}
	public String getProcStatNm() {
		return procStatNm;
	}
	public void setProcStatNm(String procStatNm) {
		this.procStatNm = procStatNm;
	}
	public String getProdNm() {
		return prodNm;
	}
	public void setProdNm(String prodNm) {
		this.prodNm = prodNm;
	}
	public String getSrchStartDate() {
		return srchStartDate;
	}
	public void setSrchStartDate(String srchStartDate) {
		this.srchStartDate = srchStartDate;
	}
	public String getSrchEndDate() {
		return srchEndDate;
	}
	public void setSrchEndDate(String srchEndDate) {
		this.srchEndDate = srchEndDate;
	}
	public String getSrchEntpCd() {
		return srchEntpCd;
	}
	public void setSrchEntpCd(String srchEntpCd) {
		this.srchEntpCd = srchEntpCd;
	}
	public String getSrchProcStat() {
		return srchProcStat;
	}
	public void setSrchProcStat(String srchProcStat) {
		this.srchProcStat = srchProcStat;
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
	
	
	
}
