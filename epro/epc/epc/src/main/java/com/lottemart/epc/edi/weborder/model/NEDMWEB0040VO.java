package com.lottemart.epc.edi.weborder.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @Class Name : NEDMWEB0040VO
 * @Description : 발주전체현황 VO Class
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      		수정자          		수정내용
 *  -        -
 * 2015.12.07	O YEUN KWON	  최초생성
 * 
 * @Copyright (C) 2000 ~ 2015 lottemart All right reserved.
 * </pre>
 */

public class NEDMWEB0040VO extends PagingVO implements Serializable {

	
	private static final long serialVersionUID = 6715413512908891245L;
    

	private String prodCd; 			// 상품코드			
	private String ordDy; 			// 발주일자			
	private String strCd; 			// 첨포코드			
	private String strNm; 			// 점포명				
	private String venCd; 			// 협력업체코드		
	private String srcmkCd; 		// 판매코드			
	private String prodNm; 			// 상품명				
	private String prodStd; 		// 상품규격			
	private String ordIpsu; 		// 발주입수			
	private String ordUnit; 		// 발주단위			
	private String ordQty; 			// 발주수량			
	private String ordCfmQty; 		// 확정발주수량		
	private String ordTotQtySum; 	// 발주총수량			
	private String ordTotPrcSum; 	// 발주금액			
	private String regStsCd; 		// 등록상태코드		
	private String mdModCd; 		// MD조정구분코드		
	private String regStsNm; 		// 등록상태명			
	private String mdModNm; 		// MD조정구분명		
 	private String strCnt; 			// 발주총수량합계			
 	private String prodCnt; 		// 발주총수량합계			
	private String ordTotAllQtySum; // 충수량EA합계		
	private String workGb;			// 작업구분
	private String regStsfg;		// 등록상태  [1.전체, 2.정상, 3.오류, 4.미전송,오류]
	private String mdModFg;			// 조정승인 [1.전체, 2.조정, 3.삭제]
	private String areaCd;			// 권역코드
	
	
	
	public String getProdCd() {
		return prodCd;
	}
	public void setProdCd(String prodCd) {
		this.prodCd = prodCd;
	}
	public String getOrdDy() {
		return ordDy;
	}
	public void setOrdDy(String ordDy) {
		this.ordDy = ordDy;
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
	public String getVenCd() {
		return venCd;
	}
	public void setVenCd(String venCd) {
		this.venCd = venCd;
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
	public String getOrdQty() {
		return ordQty;
	}
	public void setOrdQty(String ordQty) {
		this.ordQty = ordQty;
	}
	public String getOrdCfmQty() {
		return ordCfmQty;
	}
	public void setOrdCfmQty(String ordCfmQty) {
		this.ordCfmQty = ordCfmQty;
	}
	public String getOrdTotQtySum() {
		return ordTotQtySum;
	}
	public void setOrdTotQtySum(String ordTotQtySum) {
		this.ordTotQtySum = ordTotQtySum;
	}
	public String getOrdTotPrcSum() {
		return ordTotPrcSum;
	}
	public void setOrdTotPrcSum(String ordTotPrcSum) {
		this.ordTotPrcSum = ordTotPrcSum;
	}
	public String getRegStsCd() {
		return regStsCd;
	}
	public void setRegStsCd(String regStsCd) {
		this.regStsCd = regStsCd;
	}
	public String getMdModCd() {
		return mdModCd;
	}
	public void setMdModCd(String mdModCd) {
		this.mdModCd = mdModCd;
	}
	public String getRegStsNm() {
		return regStsNm;
	}
	public void setRegStsNm(String regStsNm) {
		this.regStsNm = regStsNm;
	}
	public String getMdModNm() {
		return mdModNm;
	}
	public void setMdModNm(String mdModNm) {
		this.mdModNm = mdModNm;
	}
	public String getStrCnt() {
		return strCnt;
	}
	public void setStrCnt(String strCnt) {
		this.strCnt = strCnt;
	}
	public String getProdCnt() {
		return prodCnt;
	}
	public void setProdCnt(String prodCnt) {
		this.prodCnt = prodCnt;
	}
	public String getOrdTotAllQtySum() {
		return ordTotAllQtySum;
	}
	public void setOrdTotAllQtySum(String ordTotAllQtySum) {
		this.ordTotAllQtySum = ordTotAllQtySum;
	}
	public String getWorkGb() {
		return workGb;
	}
	public void setWorkGb(String workGb) {
		this.workGb = workGb;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
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
	public String getAreaCd() {
		return areaCd;
	}
	public void setAreaCd(String areaCd) {
		this.areaCd = areaCd;
	}
	
	
	
	
}
