package com.lottemart.epc.system.model;


import java.io.Serializable;

/**
 * @Class Name : PSCMSYS0003VO.java
 * @Description :
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 20. 오후 02:02:02 mjChoi
 * 
 * @Copyright (C) 2000 ~ 2011 롯데정보통신(주) All right reserved.
 * </pre>
 */
public class PSCMSYS0003VO implements Serializable {

	private String vendorId = "";
	private String vendorNm = "";
	private String repTelNo = "";
	private String zipCd = "";
	private String addr_1_nm = "";
	private String addr_2_nm = "";
	private String islndDeliAmtYn = "";
	private String regId = "";
	private String cono = "";
	private String ceoNm = "";
	private String vendorTypeCd = "";
	private String repFaxNo = "";
	private String vendorKindCd = "";
	private String bizBtyp = "";
	private String baseDlvAmt = "";
	private String rtnAmt = "";
	private String exchAmt = "";
	private String addDeliAmt1 = "";
	private String addDeliAmt2 = "";
	private int count;
	private String deliApplyStartDy = "";
	private String returnApplyStartDy = "";

	// 페이징
	private String currentPage = "";// 페이지수
	private int startRow = 1;
	private int endRow = 10;

	public String getVendorNm() {
		return vendorNm;
	}

	public void setVendorNm(String vendorNm) {
		this.vendorNm = vendorNm;
	}

	public String getRepTelNo() {
		return repTelNo;
	}

	public void setRepTelNo(String repTelNo) {
		this.repTelNo = repTelNo;
	}

	public String getZipCd() {
		return zipCd;
	}

	public void setZipCd(String zipCd) {
		this.zipCd = zipCd;
	}

	public String getAddr_1_nm() {
		return addr_1_nm;
	}

	public void setAddr_1_nm(String addr_1_nm) {
		this.addr_1_nm = addr_1_nm;
	}

	public String getAddr_2_nm() {
		return addr_2_nm;
	}

	public void setAddr_2_nm(String addr_2_nm) {
		this.addr_2_nm = addr_2_nm;
	}

	public String getIslndDeliAmtYn() {
		return islndDeliAmtYn;
	}

	public void setIslndDeliAmtYn(String islndDeliAmtYn) {
		this.islndDeliAmtYn = islndDeliAmtYn;
	}

	public String getRegId() {
		return regId;
	}

	public void setRegId(String regId) {
		this.regId = regId;
	}

	public String getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public int getEndRow() {
		return endRow;
	}

	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}

	public String getVendorId() {
		return vendorId;
	}

	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}

	public String getCono() {
		return cono;
	}

	public void setCono(String cono) {
		this.cono = cono;
	}

	public String getCeoNm() {
		return ceoNm;
	}

	public void setCeoNm(String ceoNm) {
		this.ceoNm = ceoNm;
	}

	public String getVendorTypeCd() {
		return vendorTypeCd;
	}

	public void setVendorTypeCd(String vendorTypeCd) {
		this.vendorTypeCd = vendorTypeCd;
	}

	public String getRepFaxNo() {
		return repFaxNo;
	}

	public void setRepFaxNo(String repFaxNo) {
		this.repFaxNo = repFaxNo;
	}

	public String getVendorKindCd() {
		return vendorKindCd;
	}

	public void setVendorKindCd(String vendorKindCd) {
		this.vendorKindCd = vendorKindCd;
	}

	public String getBizBtyp() {
		return bizBtyp;
	}

	public void setBizBtyp(String bizBtyp) {
		this.bizBtyp = bizBtyp;
	}

	public String getBaseDlvAmt() {
		return baseDlvAmt;
	}

	public void setBaseDlvAmt(String baseDlvAmt) {
		this.baseDlvAmt = baseDlvAmt;
	}

	public String getRtnAmt() {
		return rtnAmt;
	}

	public void setRtnAmt(String rtnAmt) {
		this.rtnAmt = rtnAmt;
	}

	public String getExchAmt() {
		return exchAmt;
	}

	public void setExchAmt(String exchAmt) {
		this.exchAmt = exchAmt;
	}

	public String getAddDeliAmt1() {
		return addDeliAmt1;
	}

	public void setAddDeliAmt1(String addDeliAmt1) {
		this.addDeliAmt1 = addDeliAmt1;
	}

	public String getAddDeliAmt2() {
		return addDeliAmt2;
	}

	public void setAddDeliAmt2(String addDeliAmt2) {
		this.addDeliAmt2 = addDeliAmt2;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getDeliApplyStartDy() {
		return deliApplyStartDy;
	}

	public void setDeliApplyStartDy(String deliApplyStartDy) {
		this.deliApplyStartDy = deliApplyStartDy;
	}

	public String getReturnApplyStartDy() {
		return returnApplyStartDy;
	}

	public void setReturnApplyStartDy(String returnApplyStartDy) {
		this.returnApplyStartDy = returnApplyStartDy;
	}

	@Override
	public String toString() {
		return "PSCMSYS0003VO [vendorId=" + vendorId + ", vendorNm=" + vendorNm + ", repTelNo=" + repTelNo
				+ ", islndDeliAmtYn=" + islndDeliAmtYn + ", regId=" + regId + ", ceoNm=" + ceoNm + ", vendorTypeCd="
				+ vendorTypeCd + ", repFaxNo=" + repFaxNo + ", vendorKindCd=" + vendorKindCd + ", bizBtyp=" + bizBtyp
				+ ", baseDlvAmt=" + baseDlvAmt + ", rtnAmt=" + rtnAmt + ", exchAmt=" + exchAmt + ", addDeliAmt1="
				+ addDeliAmt1 + ", addDeliAmt2=" + addDeliAmt2 + "]";
	}

}
