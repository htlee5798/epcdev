/**
 * @prjectName  : lottemart 프로젝트
 * @since       : 2011. 10. 18. 오후 2:33:50
 * @author      : yskim
 * @Copyright(c) 2000 ~ 2011 lottemart
 *  All rights reserved.
 */

package com.lottemart.epc.product.model;

import java.io.Serializable;

/**
 * @Class Name : PSCMPRD0011VO
 * @Description : 상품이미지촬영스케쥴 목록 VO 클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 18. 오후 4:44:54 yskim
 *
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
public class PSCMPRD0011VO implements Serializable {

	private static final long serialVersionUID = 8663332516557737871L;

	/** 페이징 관련 페이지당 목록 수 */
    private int rowsPerPage;

    /** 페이지 관련 현재 페이지 */
    private int currentPage;

    /** 저장시 등록과 수정을 구분하는 플래그 */
	private String crud = "";

    /** 스케줄순번 */
	private String scdlSeqs = "";

    /** 거래처ID */
	private String vendorId = "";

    /** 거래처명 */
	private String vendorNm = "";

    /** 예약시작일자 */
	private String rservStartDy = "";

    /** 예약시작시각 */
	private String rservStartTm = "";

    /** 예약시작시각(시) */
	private String rservStartHour = "";

    /** 예약시작시각(분) */
	private String rservStartMin = "";

    /** 예약종료일자 */
	private String rservEndDy = "";

    /** 예약종료시각 */
	private String rservEndTm = "";

    /** 예약종료시각(시) */
	private String rservEndHour = "";

    /** 예약종료시각(분) */
	private String rservEndMin = "";

    /** 휴대폰번호 */
	private String cellNo = "";

    /** 스케줄메모 */
	private String scdlMemo = "";

	/** 등록일시 */
	private String regDate = "";

	/** 등록자 */
	private String regId = "";

	/** 수정일시 */
	private String modDate = "";

	/** 수정자 */
	private String modId = "";

	/** 조회시작일 */
    private String startDate = "";
    /** 조회종료일 */
    private String endDate = "";

    //private String rankNum = "";

    private int totalCount = 0;


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
	public String getScdlSeqs() {
		return scdlSeqs;
	}
	public void setScdlSeqs(String scdlSeqs) {
		this.scdlSeqs = scdlSeqs;
	}
	public String getVendorId() {
		return vendorId;
	}
	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}
	public String getVendorNm() {
		return vendorNm;
	}
	public void setVendorNm(String vendorNm) {
		this.vendorNm = vendorNm;
	}
	public String getRservStartDy() {
		return rservStartDy;
	}
	public void setRservStartDy(String rservStartDy) {
		this.rservStartDy = rservStartDy;
	}
	public String getRservStartTm() {
		return rservStartTm;
	}
	public void setRservStartTm(String rservStartTm) {
		this.rservStartTm = rservStartTm;
	}
	public String getRservEndDy() {
		return rservEndDy;
	}
	public void setRservEndDy(String rservEndDy) {
		this.rservEndDy = rservEndDy;
	}
	public String getRservEndTm() {
		return rservEndTm;
	}
	public void setRservEndTm(String rservEndTm) {
		this.rservEndTm = rservEndTm;
	}
	public String getRservStartHour() {
		return rservStartHour;
	}
	public void setRservStartHour(String rservStartHour) {
		this.rservStartHour = rservStartHour;
	}
	public String getRservStartMin() {
		return rservStartMin;
	}
	public void setRservStartMin(String rservStartMin) {
		this.rservStartMin = rservStartMin;
	}
	public String getRservEndHour() {
		return rservEndHour;
	}
	public void setRservEndHour(String rservEndHour) {
		this.rservEndHour = rservEndHour;
	}
	public String getRservEndMin() {
		return rservEndMin;
	}
	public void setRservEndMin(String rservEndMin) {
		this.rservEndMin = rservEndMin;
	}
	public String getCellNo() {
		return cellNo;
	}
	public void setCellNo(String cellNo) {
		this.cellNo = cellNo;
	}
	public String getScdlMemo() {
		return scdlMemo;
	}
	public void setScdlMemo(String scdlMemo) {
		this.scdlMemo = scdlMemo;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
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

	public String toString() {
		StringBuffer sb = new StringBuffer("\n");
		sb.append("\n rowsPerPage::").append(rowsPerPage);
		sb.append("\n currentPage::").append(currentPage);
		sb.append("\n crud::").append(crud);
		sb.append("\n scdlSeqs::").append(scdlSeqs);
		sb.append("\n vendorId::").append(vendorId);
		sb.append("\n vendorNm::").append(vendorNm);
		sb.append("\n rservStartDy::").append(rservStartDy);
		sb.append("\n rservStartTm::").append(rservStartTm);
		sb.append("\n rservStartHour::").append(rservStartHour);
		sb.append("\n rservStartMin::").append(rservStartMin);
		sb.append("\n rservEndDy::").append(rservEndDy);
		sb.append("\n rservEndTm::").append(rservEndTm);
		sb.append("\n rservEndHour::").append(rservEndHour);
		sb.append("\n rservEndMin::").append(rservEndMin);
		sb.append("\n cellNo::").append(cellNo);
		sb.append("\n scdlMemo::").append(scdlMemo);
		sb.append("\n regDate::").append(regDate);
		sb.append("\n regId::").append(regId);
		sb.append("\n modDate::").append(modDate);
		sb.append("\n modId::").append(modId);
		sb.append("\n");
		return sb.toString();
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
}
