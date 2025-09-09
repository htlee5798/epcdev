package com.lottemart.epc.edi.order.model;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * @Class Name : PEDMORD0002VO
 * @Description : 발주정보 -> 기간정보 VO
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 11. 04. 오후 2:32:38 
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */

public class NEDMORD0020VO implements Serializable {

	
	private static final long serialVersionUID = -6918711902569942487L;


	public NEDMORD0020VO () {}
	
	/** 검색시작일 */
	private String srchStartDate;
	/** 검색종료일 */
	private String srchEndDate;
	/** 협력업체코드 */
	private String searchEntpCd;
	/** 상품선택 */
	private String searchProductVal;
	/** 점포코드(배열) */
	private ArrayList searchStoreAl;
	/** 발주구분 */
	private String searchOrdering;
	
	/** TEXT Data */
	private String textData;
	
	private String ordSlipNo;		// 발주전표번호
	private String ordDy;	        // 발주일
	private String strNm;	        // 점포명
	private String orgFg;	        // 발주구분
	private String ordFgNm;	        // 발주구분명
	private String totQty;	        // 발주총수량
	private String totPrc;	        // 발주총금액
	private String ctrArrDy;	    // 센터입하일자
	private String splyTm;	        // 납품시간
	private String splyDy;	        // 납품일자
	private String userHit;	        // 사용자조회
	private String venCd;	        // 업체코드
	private String strCd;	        // 점포코드
	private String prodPatFg;       // 상품패턴구분
	private String logiCatNm;       //
	private String routeFg;	        // 루트구분
	private String supplyCd;	    // 통합발주납품처코드
	private String supllyNm;        // 통합발주납품처코드명
	private String[] venCds;        // 협력업체 코드 배열
	//


	public String getSearchEntpCd() {
		return searchEntpCd;
	}
	public void setSearchEntpCd(String searchEntpCd) {
		this.searchEntpCd = searchEntpCd;
	}
	public String getOrdSlipNo() {
		return ordSlipNo;
	}
	public void setOrdSlipNo(String ordSlipNo) {
		this.ordSlipNo = ordSlipNo;
	}
	public String getOrdDy() {
		return ordDy;
	}
	public void setOrdDy(String ordDy) {
		this.ordDy = ordDy;
	}
	public String getStrNm() {
		return strNm;
	}
	public void setStrNm(String strNm) {
		this.strNm = strNm;
	}
	public String getOrgFg() {
		return orgFg;
	}
	public void setOrgFg(String orgFg) {
		this.orgFg = orgFg;
	}
	public String getOrdFgNm() {
		return ordFgNm;
	}
	public void setOrdFgNm(String ordFgNm) {
		this.ordFgNm = ordFgNm;
	}
	public String getTotQty() {
		return totQty;
	}
	public void setTotQty(String totQty) {
		this.totQty = totQty;
	}
	public String getTotPrc() {
		return totPrc;
	}
	public void setTotPrc(String totPrc) {
		this.totPrc = totPrc;
	}
	public String getCtrArrDy() {
		return ctrArrDy;
	}
	public void setCtrArrDy(String ctrArrDy) {
		this.ctrArrDy = ctrArrDy;
	}
	public String getSplyTm() {
		return splyTm;
	}
	public void setSplyTm(String splyTm) {
		this.splyTm = splyTm;
	}
	public String getSplyDy() {
		return splyDy;
	}
	public void setSplyDy(String splyDy) {
		this.splyDy = splyDy;
	}
	public String getUserHit() {
		return userHit;
	}
	public void setUserHit(String userHit) {
		this.userHit = userHit;
	}
	public String getVenCd() {
		return venCd;
	}
	public void setVenCd(String venCd) {
		this.venCd = venCd;
	}
	public String getStrCd() {
		return strCd;
	}
	public void setStrCd(String strCd) {
		this.strCd = strCd;
	}
	public String getProdPatFg() {
		return prodPatFg;
	}
	public void setProdPatFg(String prodPatFg) {
		this.prodPatFg = prodPatFg;
	}
	public String getLogiCatNm() {
		return logiCatNm;
	}
	public void setLogiCatNm(String logiCatNm) {
		this.logiCatNm = logiCatNm;
	}
	public String getRouteFg() {
		return routeFg;
	}
	public void setRouteFg(String routeFg) {
		this.routeFg = routeFg;
	}
	public String getSupplyCd() {
		return supplyCd;
	}
	public void setSupplyCd(String supplyCd) {
		this.supplyCd = supplyCd;
	}
	public String getSupllyNm() {
		return supllyNm;
	}
	public void setSupllyNm(String supllyNm) {
		this.supllyNm = supllyNm;
	}
	
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
	public ArrayList getSearchStoreAl() {
		if (this.searchStoreAl != null) {
			ArrayList ret = new ArrayList();
			for (int i = 0; i < searchStoreAl.size(); i++) {
				ret.add(i, this.searchStoreAl.get(i));
			}
			
			return ret;
		} else {
			return null;
		}
	}
	public void setSearchStoreAl(ArrayList searchStoreAl) {
		if (searchStoreAl != null) {
			this.searchStoreAl = new ArrayList();
			for (int i = 0; i < searchStoreAl.size();i++) {
				this.searchStoreAl.add(i, searchStoreAl.get(i));
			}
		} else {
			this.searchStoreAl = null;
		}
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
	public String getSearchProductVal() {
		return searchProductVal;
	}
	public void setSearchProductVal(String searchProductVal) {
		this.searchProductVal = searchProductVal;
	}
	public String getTextData() {
		return textData;
	}
	public void setTextData(String textData) {
		this.textData = textData;
	}
	public String getSearchOrdering() {
		return searchOrdering;
	}
	public void setSearchOrdering(String searchOrdering) {
		this.searchOrdering = searchOrdering;
	}
	
}
