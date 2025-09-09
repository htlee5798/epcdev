/**
 * @prjectName  : lottemart 프로젝트
 * @since       : 2011. 10. 18. 오후 2:33:50
 * @author      : yskim
 * @Copyright(c) 2000 ~ 2011 lottemart
 *  All rights reserved.
 */

package com.lottemart.epc.product.model;

import java.io.Serializable;
import java.util.List;

/**
 * @Class Name : PSCMPRD0014VO
 * @Description : 품절관리 VO 클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 18. 오후 4:44:58 yskim
 *
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
public class PSCMPRD0014VO implements Serializable {

	private static long serialVersionUID = -2017685870992017203L;

	/** 페이징 관련 페이지당 목록 수 */
    private int rowsPerPage;

    /** 페이지 관련 현재 페이지 */
    private int currentPage;

    /** 저장시 등록과 수정을 구분하는 플래그 */
	private String crud = "";

	/** 인터넷상품코드 */
	private String prodCd = "";

	/** 단품코드 */
	private String itemCd = "";

	/** 점포코드 */
	private String strCd = "";

	/** 협력업체코드 */
	private String vendorId = "";

	/** 전시여부 */
	private String dispYn = "";

	/** 품절여부 */
	private String soutYn = "";

	/** 수정일시 */
	private String modDate = "";

	/** 수정자 */
	private String modId = "";

	/** 조회일자 구분자 */
	private String chkVal = "";
	//private String chkVal2 = "";

	/** 조회시작일 */
    private String startDate = "";
    /** 조회종료일 */
    private String endDate = "";

    private String searchDateType = "";
    private String categoryId = "";
    private String prodNm = "";

    private String mdProdCd = "";
    private String l1Nm = "";
    private String l2Nm = "";
    private String l3Nm = "";
    private String l1Cd = "";
    private String l2Cd = "";
    private String l3Cd = "";
    private String eComYn = "";
    private String eComYnApprove = "";
    private int rowSeq;



    /** 협력업체코드 */
    private List<String> vendorList;




	public List<String> getVendorList() {
		return vendorList;
	}

	public void setVendorList(List<String> vendorList) {
		this.vendorList = vendorList;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getSearchDateType() {
		return searchDateType;
	}

	public void setSearchDateType(String searchDateType) {
		this.searchDateType = searchDateType;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getProdNm() {
		return prodNm;
	}

	public void setProdNm(String prodNm) {
		this.prodNm = prodNm;
	}

	public int getRowsPerPage() {
		return rowsPerPage;
	}

	public void setRowsPerPage(int rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public String getCrud() {
		return crud;
	}

	public void setCrud(String crud) {
		this.crud = crud;
	}

	public String getProdCd() {
		return prodCd;
	}

	public void setProdCd(String prodCd) {
		this.prodCd = prodCd;
	}

	public String getItemCd() {
		return itemCd;
	}

	public void setItemCd(String itemCd) {
		this.itemCd = itemCd;
	}

	public String getStrCd() {
		return strCd;
	}

	public void setStrCd(String strCd) {
		this.strCd = strCd;
	}

	public String getVendorId() {
		return vendorId;
	}

	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}

	public String getDispYn() {
		return dispYn;
	}

	public void setDispYn(String dispYn) {
		this.dispYn = dispYn;
	}

	public String getSoutYn() {
		return soutYn;
	}

	public void setSoutYn(String soutYn) {
		this.soutYn = soutYn;
	}

	public String getModDate() {
		return modDate;
	}

	public void setModDate(String modDate) {
		this.modDate = modDate;
	}

	public String getModId() {
		return modId;
	}

	public void setModId(String modId) {
		this.modId = modId;
	}

	public String getChkVal() {
		return chkVal;
	}

	public void setChkVal(String chkVal) {
		this.chkVal = chkVal;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public static void setSerialversionuid(long serialversionuid) {
		serialVersionUID = serialversionuid;
	}

	public String getMdProdCd() {
		return mdProdCd;
	}

	public void setMdProdCd(String mdProdCd) {
		this.mdProdCd = mdProdCd;
	}

	public String getL1Nm() {
		return l1Nm;
	}

	public void setL1Nm(String l1Nm) {
		this.l1Nm = l1Nm;
	}

	public String getL2Nm() {
		return l2Nm;
	}

	public void setL2Nm(String l2Nm) {
		this.l2Nm = l2Nm;
	}

	public String getL3Nm() {
		return l3Nm;
	}

	public void setL3Nm(String l3Nm) {
		this.l3Nm = l3Nm;
	}

	public String getL1Cd() {
		return l1Cd;
	}

	public void setL1Cd(String l1Cd) {
		this.l1Cd = l1Cd;
	}

	public String getL2Cd() {
		return l2Cd;
	}

	public void setL2Cd(String l2Cd) {
		this.l2Cd = l2Cd;
	}

	public String getL3Cd() {
		return l3Cd;
	}

	public void setL3Cd(String l3Cd) {
		this.l3Cd = l3Cd;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer("\n");
		sb.append("\n rowsPerPage::").append(rowsPerPage);
		sb.append("\n currentPage::").append(currentPage);
		sb.append("\n crud::").append(crud);
		sb.append("\n prodCd::").append(prodCd);
		sb.append("\n itemCd::").append(itemCd);
		sb.append("\n strCd::").append(strCd);
		sb.append("\n dispYn::").append(dispYn);
		sb.append("\n soutYn::").append(soutYn);
		sb.append("\n modDate::").append(modDate);
		sb.append("\n modId::").append(modId);

		sb.append("\n rowSeq::").append(rowSeq);
		sb.append("\n l1Nm::").append(l1Nm);
		sb.append("\n l2Nm::").append(l2Nm);
		sb.append("\n l3Nm::").append(l3Nm);
		sb.append("\n l1Cd::").append(l1Cd);
		sb.append("\n l2Cd::").append(l2Cd);
		sb.append("\n l3Cd::").append(l3Cd);
		sb.append("\n eComYn::").append(eComYn);
		sb.append("\n eComYnApprove::").append(eComYnApprove);

		sb.append("\n");
		return sb.toString();
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public static void setSerialVersionUID(long serialVersionUID) {
		PSCMPRD0014VO.serialVersionUID = serialVersionUID;
	}

	public int getRowSeq() {
		return rowSeq;
	}

	public void setRowSeq(int rowSeq) {
		this.rowSeq = rowSeq;
	}

	public String geteComYn() {
		return eComYn;
	}

	public void seteComYn(String eComYn) {
		this.eComYn = eComYn;
	}

	public String geteComYnApprove() {
		return eComYnApprove;
	}

	public void seteComYnApprove(String eComYnApprove) {
		this.eComYnApprove = eComYnApprove;
	}

}
