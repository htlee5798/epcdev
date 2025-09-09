package com.lottemart.epc.system.model;


import java.io.Serializable;

/**
 * @Class Name : PSCMSYS0004VO.java
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
public class PSCMSYS0004VO implements Serializable
{  
	private String vendorId			= "";
	private String addrSeq				= "";
	private String addrKindCd			= "";
	private String zipCd				= "";
	private String addr_1_nm			= ""; 
	private String addr_2_nm			= ""; 
	private String cellNo				= "";	
	private String regId				= "";
	private String modId				= "";
	private String useYn				= "";
	private String num = "";
	
	// 페이징
	private String currentPage = "";//페이지수
	private int startRow = 1;
	private int endRow = 10;
	public String getAddrSeq() {
		return addrSeq;
	}
	public void setAddrSeq(String addrSeq) {
		this.addrSeq = addrSeq;
	}
	public String getAddrKindCd() {
		return addrKindCd;
	}
	public void setAddrKindCd(String addrKindCd) {
		this.addrKindCd = addrKindCd;
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
	public String getCellNo() {
		return cellNo;
	}
	public void setCellNo(String cellNo) {
		this.cellNo = cellNo;
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
	public String getUseYn() {
		return useYn;
	}
	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
}
