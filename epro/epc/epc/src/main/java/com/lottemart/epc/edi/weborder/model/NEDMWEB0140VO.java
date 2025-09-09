package com.lottemart.epc.edi.weborder.model;

import java.io.Serializable;

/**
 * @Class Name : SearchWebOrder
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

public class NEDMWEB0140VO extends PagingVO implements Serializable {

	


	/**
	 * 
	 */
	private static final long serialVersionUID = 6715413512908899494L;

	public NEDMWEB0140VO() {}
	
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
	
	/**조회상태정보 0:정상, 1:중복, -1:exception*/
	private String state		;
	/**상태 메세지*/
	private String message      ;
	/**반품등록번호*/
	private String rrlReqNo		;
	/**반품수량*/
	private String rrlQty		;
	
	/**반품등록번호*/
	private String rrlReqNos[]		;
	/**상품코드*/
	private String prodCds[]		;
	
	/**SP OUT*/
	private String o_ret			;
	private String o_proc_cmt		;
	
	
	/**상품규격*/
	private String prodStd		;
	/**업체코드명*/
	private String venNm		;
	/**센터유형구분*/
	private String ctrTypFg	  	;
	/**루트구분*/
	private String routeFg		;
	/**발주입수*/
	private String ordIpsu		;
	/**원가-MARTNIS*/
	private String buyPrc		;
	/**매가-MARTNIS*/
	private String salePrc		;
	
	
	/**점포카운트*/
	private String strCdCnt			   ;
	/**업체전체상품수*/
	private String rrlTotProdQtySum    ;
	/**반품수량전체*/
	private String rrlTotQtySum        ;
	/**반품금액전체*/
	private String rrlTotPrcSum        ;
	/**단위*/
	private String ordUnit             ;
	/**매가-EDI*/
	private String stdSalePrc          ;
	/**원가-EID*/
	private String stdBuyPrc           ;
	/**반품환산금액(수량*매가)*/
	private String stdProdPrc          ;
	/**일괄등록상태코드*/
	private String regStsCd            ;
	/**등록상태명칭*/
	private String regStsCdNm          ;
	/**등록상태상세내용*/
	private String regStsCdDetail       ;
	
	/** 재고 수량*/
	private String rrlStkQty;
	private String rrlStkQty2;
	
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
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getRrlReqNo() {
		return rrlReqNo;
	}
	public void setRrlReqNo(String rrlReqNo) {
		this.rrlReqNo = rrlReqNo;
	}
	public String getRrlQty() {
		return rrlQty;
	}
	public void setRrlQty(String rrlQty) {
		this.rrlQty = rrlQty;
	}
	public String[] getRrlReqNos() {
		return rrlReqNos;
	}
	public void setRrlReqNos(String[] rrlReqNos) {
		this.rrlReqNos = rrlReqNos;
	}
	public String[] getProdCds() {
		return prodCds;
	}
	public void setProdCds(String[] prodCds) {
		this.prodCds = prodCds;
	}
	public String getO_ret() {
		return o_ret;
	}
	public void setO_ret(String o_ret) {
		this.o_ret = o_ret;
	}
	public String getO_proc_cmt() {
		return o_proc_cmt;
	}
	public void setO_proc_cmt(String o_proc_cmt) {
		this.o_proc_cmt = o_proc_cmt;
	}
	public String getProdStd() {
		return prodStd;
	}
	public void setProdStd(String prodStd) {
		this.prodStd = prodStd;
	}
	public String getVenNm() {
		return venNm;
	}
	public void setVenNm(String venNm) {
		this.venNm = venNm;
	}
	public String getCtrTypFg() {
		return ctrTypFg;
	}
	public void setCtrTypFg(String ctrTypFg) {
		this.ctrTypFg = ctrTypFg;
	}
	public String getRouteFg() {
		return routeFg;
	}
	public void setRouteFg(String routeFg) {
		this.routeFg = routeFg;
	}
	public String getOrdIpsu() {
		return ordIpsu;
	}
	public void setOrdIpsu(String ordIpsu) {
		this.ordIpsu = ordIpsu;
	}
	public String getBuyPrc() {
		return buyPrc;
	}
	public void setBuyPrc(String buyPrc) {
		this.buyPrc = buyPrc;
	}
	public String getSalePrc() {
		return salePrc;
	}
	public void setSalePrc(String salePrc) {
		this.salePrc = salePrc;
	}
	public String getStrCdCnt() {
		return strCdCnt;
	}
	public void setStrCdCnt(String strCdCnt) {
		this.strCdCnt = strCdCnt;
	}
	public String getRrlTotProdQtySum() {
		return rrlTotProdQtySum;
	}
	public void setRrlTotProdQtySum(String rrlTotProdQtySum) {
		this.rrlTotProdQtySum = rrlTotProdQtySum;
	}
	public String getRrlTotQtySum() {
		return rrlTotQtySum;
	}
	public void setRrlTotQtySum(String rrlTotQtySum) {
		this.rrlTotQtySum = rrlTotQtySum;
	}
	public String getRrlTotPrcSum() {
		return rrlTotPrcSum;
	}
	public void setRrlTotPrcSum(String rrlTotPrcSum) {
		this.rrlTotPrcSum = rrlTotPrcSum;
	}
	public String getOrdUnit() {
		return ordUnit;
	}
	public void setOrdUnit(String ordUnit) {
		this.ordUnit = ordUnit;
	}
	public String getStdSalePrc() {
		return stdSalePrc;
	}
	public void setStdSalePrc(String stdSalePrc) {
		this.stdSalePrc = stdSalePrc;
	}
	public String getStdBuyPrc() {
		return stdBuyPrc;
	}
	public void setStdBuyPrc(String stdBuyPrc) {
		this.stdBuyPrc = stdBuyPrc;
	}
	public String getStdProdPrc() {
		return stdProdPrc;
	}
	public void setStdProdPrc(String stdProdPrc) {
		this.stdProdPrc = stdProdPrc;
	}
	public String getRegStsCd() {
		return regStsCd;
	}
	public void setRegStsCd(String regStsCd) {
		this.regStsCd = regStsCd;
	}
	public String getRegStsCdNm() {
		return regStsCdNm;
	}
	public void setRegStsCdNm(String regStsCdNm) {
		this.regStsCdNm = regStsCdNm;
	}
	public String getRegStsCdDetail() {
		return regStsCdDetail;
	}
	public void setRegStsCdDetail(String regStsCdDetail) {
		this.regStsCdDetail = regStsCdDetail;
	}
	public String getRrlStkQty() {
		return rrlStkQty;
	}
	public void setRrlStkQty(String rrlStkQty) {
		this.rrlStkQty = rrlStkQty;
	}
	public String getRrlStkQty2() {
		return rrlStkQty2;
	}
	public void setRrlStkQty2(String rrlStkQty2) {
		this.rrlStkQty2 = rrlStkQty2;
	}
	
}
