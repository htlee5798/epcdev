package com.lottemart.epc.edi.weborder.model;

import java.io.Serializable;

/**
 * @Class Name : NEDMWEB0099VO
 * @Description : 웹발주/반품에 사용하는 검색 조건
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2014. 08. 07. 오후 2:32:38 jyl
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */

public class NEDMWEB0099VO extends PagingVO implements Serializable {

	


	/**
	 * 
	 */
	private static final long serialVersionUID = 6715413512908899494L;

	public NEDMWEB0099VO() {}
	
	//업체코드
	private String entpCode;
	// 권역코드
	private String areaCd;
	// 점포코드
	private String strCd;
	// 점포명칭
	private String strNm;
	// 점포코드 다중선택
	private String[] strCds;
	//점포검색 방법 1:DropDown, 2:다중점선택
	private String strSearchType;
	// 상품코드
	private String prodCd;
	// 판매코드
	private String srcmkCd;
	// 상품명
	private String prodNm;
	// 작업구분
	private String workGb;
	// 조정승인
	private String adjustAppr;
	// 업로드 상태
	private String uploadState;
	//검색 시작일
	private String startDate;
	//검색 종료일
	private String endDate;
	//발주요청일자
	private String ordDy;
	//로그인사용자의 협력업체코드 전체
	private String[] venCds;		
	//조회조건에서 선택될 협력 업체코드
	private String   venCd;
	
	//발주등록번호
	private String ordReqNo;
	
	// 등록상태  [1.전체, 2.정상, 3.오류, 4.미전송,오류]
	private String regStsfg;
	// 조정승인 [1.전체, 2.조정, 3.삭제]
	private String mdModFg;
	// 업로드 상태 [1.전체, 2.정상, 3.오류]
	private String uploadGb;
	
	// 발주전송 목록선택[1.전체, 2.화면리스트]
	private String orderSendfg;
		// 사번
	private String empNo;
	// 사원명
	private String empNm;
	
	//반품일자
	private String rrlDy;
		
	// 파일 구분 코드
	private String packDivnCd;
	
	// 작업자아이디
	private String workUser;

	/**검색방법 (01:전체검색, 02:관리협력사)*/
	public String findType;
	
	// 협력업체 웹발주 가능 시작 시간
	public String vendorWebOrdFrDt;
	// 협력업체 웹발주 가능 끝 시간
	public String vendorWebOrdToDt;
	
	public String getEntpCode() {
		return entpCode;
	}
	public void setEntpCode(String entpCode) {
		this.entpCode = entpCode;
	}
	public String getAreaCd() {
		return areaCd;
	}
	public void setAreaCd(String areaCd) {
		this.areaCd = areaCd;
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
	public String[] getStrCds() {
		return strCds;
	}
	public void setStrCds(String[] strCds) {
		this.strCds = strCds;
	}
	public String getStrSearchType() {
		return strSearchType;
	}
	public void setStrSearchType(String strSearchType) {
		this.strSearchType = strSearchType;
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
	public String getProdNm() {
		return prodNm;
	}
	public void setProdNm(String prodNm) {
		this.prodNm = prodNm;
	}
	public void setSrcmkCd(String srcmkCd) {
		this.srcmkCd = srcmkCd;
	}
	public String getWorkGb() {
		return workGb;
	}
	public void setWorkGb(String workGb) {
		this.workGb = workGb;
	}
	public String getAdjustAppr() {
		return adjustAppr;
	}
	public void setAdjustAppr(String adjustAppr) {
		this.adjustAppr = adjustAppr;
	}
	public String getUploadState() {
		return uploadState;
	}
	public void setUploadState(String uploadState) {
		this.uploadState = uploadState;
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
	
	public String getOrdDy() {
		return ordDy;
	}
	public void setOrdDy(String ordDy) {
		this.ordDy = ordDy;
	}
	public String[] getVenCds() {
		return venCds;
	}
	public void setVenCds(String[] venCds) {
		this.venCds = venCds;
	}
	public String getVenCd() {
		return venCd;
	}
	public void setVenCd(String venCd) {
		this.venCd = venCd;
	}			
	
	public String getOrdReqNo() {
		return ordReqNo;
	}
	public void setOrdReqNo(String ordReqNo) {
		this.ordReqNo = ordReqNo;
	}
	public String getRegStsfg() {
		return regStsfg;
	}
	public void setRegStsfg(String regStsfg) {
		this.regStsfg = regStsfg;
	}
	public String getMdModFg() {
		return mdModFg;
	}
	public void setMdModFg(String mdModFg) {
		this.mdModFg = mdModFg;
	}
	
	public String getEmpNo() {
		return empNo;
	}
	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}
	public String getEmpNm() {
		return empNm;
	}
	public void setEmpNm(String empNm) {
		this.empNm = empNm;
	}
	public String getUploadGb() {
		return uploadGb;
	}
	public void setUploadGb(String uploadGb) {
		this.uploadGb = uploadGb;
	}
	public String getRrlDy() {
		return rrlDy;
	}
	public void setRrlDy(String rrlDy) {
		this.rrlDy = rrlDy;
	}
	public String getPackDivnCd() {
		return packDivnCd;
	}
	public void setPackDivnCd(String packDivnCd) {
		this.packDivnCd = packDivnCd;
	}
	public String getWorkUser() {
		return workUser;
	}
	public void setWorkUser(String workUser) {
		this.workUser = workUser;
	}
	public String getOrderSendfg() {
		return orderSendfg;
	}
	public void setOrderSendfg(String orderSendfg) {
		this.orderSendfg = orderSendfg;
	}

	public String getFindType() {
		return findType;
	}
	public void setFindType(String findType) {
		this.findType = findType;
	}
	public String getVendorWebOrdFrDt() {
		return vendorWebOrdFrDt;
	}
	public void setVendorWebOrdFrDt(String vendorWebOrdFrDt) {
		this.vendorWebOrdFrDt = vendorWebOrdFrDt;
	}
	public String getVendorWebOrdToDt() {
		return vendorWebOrdToDt;
	}
	public void setVendorWebOrdToDt(String vendorWebOrdToDt) {
		this.vendorWebOrdToDt = vendorWebOrdToDt;
	}
	
}
