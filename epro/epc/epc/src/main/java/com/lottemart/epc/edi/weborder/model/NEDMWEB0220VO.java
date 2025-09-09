package com.lottemart.epc.edi.weborder.model;

import java.io.Serializable;

/**
 * @Class Name : NEDMWEB0220VO
 * @Description : 발주전체현황 VO Class
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      		수정자          		수정내용
 *  -        -
 * 2015.12.08	O YEUN KWON	  최초생성
 * 
 * @Copyright (C) 2000 ~ 2015 lottemart All right reserved.
 * </pre>
 */

public class NEDMWEB0220VO extends PagingVO implements Serializable {

	/**
	 * 
	 */
		private static final long serialVersionUID = 6715413512908897485L;
		
		private String areaCd; 			    	// 권역코드
		private String mdModCd; 			    // 작업구분
		private String venCd;  					// 협력업체코드  
		private String venNm; 					// 협력업체명  
		private String strCd;  				    // 점포코드
		private String strNm; 				    // 점포명  
		private String prodCd;  			    // 상품코드 
	    private String prodNm;  			    // 상품명  
	    private String srcmkCd; 			    // 판매코드
	    private String prodStd; 			    // 규격  
	    private String ordIpsu; 		    	// 입수 
	    private String ordUnit; 		    	// 단위
	    private String ordUnitNm; 		    	// 단위명
	    private String ordCfmQty; 			    // 발주확정수량 
	    private String ordQty; 			    	// 요청수량
	    private String eaQty; 			    	// EA
	    private String prc; 			    	// 금액
	    private String regStsCd; 			    // 일괄등록상태코드
	    private String regStsNm; 			    // 일괄등록상태명칭
	    private String regStsCdDetail; 			// 일괄등록상태상세
	    private String empNo;					// 사번
	    private String startDate; 				// 시작일
	    private String endDate;					// 종료일
	   
	    
	    
		public String getMdModCd() {
			return mdModCd;
		}
		public void setMdModCd(String mdModCd) {
			this.mdModCd = mdModCd;
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
		public String getProdStd() {
			return prodStd;
		}
		public void setProdStd(String prodStd) {
			this.prodStd = prodStd;
		}
		public String getOrdIpsu() {
			return ordIpsu;
		}
		public void setOrdIpsu(String ordIpsu) {
			this.ordIpsu = ordIpsu;
		}
		public String getOrdUnit() {
			return ordUnit;
		}
		public void setOrdUnit(String ordUnit) {
			this.ordUnit = ordUnit;
		}
		public String getOrdUnitNm() {
			return ordUnitNm;
		}
		public void setOrdUnitNm(String ordUnitNm) {
			this.ordUnitNm = ordUnitNm;
		}
		public String getOrdQty() {
			return ordQty;
		}
		public void setOrdQty(String ordQty) {
			this.ordQty = ordQty;
		}
		public String getEaQty() {
			return eaQty;
		}
		public void setEaQty(String eaQty) {
			this.eaQty = eaQty;
		}
		public String getPrc() {
			return prc;
		}
		public void setPrc(String prc) {
			this.prc = prc;
		}
		public String getRegStsCd() {
			return regStsCd;
		}
		public void setRegStsCd(String regStsCd) {
			this.regStsCd = regStsCd;
		}
		public String getRegStsNm() {
			return regStsNm;
		}
		public void setRegStsNm(String regStsNm) {
			this.regStsNm = regStsNm;
		}
		public String getRegStsCdDetail() {
			return regStsCdDetail;
		}
		public void setRegStsCdDetail(String regStsCdDetail) {
			this.regStsCdDetail = regStsCdDetail;
		}
		public static long getSerialversionuid() {
			return serialVersionUID;
		}
		public String getAreaCd() {
			return areaCd;
		}
		public void setAreaCd(String areaCd) {
			this.areaCd = areaCd;
		}
		public String getOrdCfmQty() {
			return ordCfmQty;
		}
		public void setOrdCfmQty(String ordCfmQty) {
			this.ordCfmQty = ordCfmQty;
		}
		public String getEmpNo() {
			return empNo;
		}
		public void setEmpNo(String empNo) {
			this.empNo = empNo;
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
	    
		
	    
}
