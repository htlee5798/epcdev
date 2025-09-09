package com.lottemart.epc.edi.payment.model;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * @Class Name : NEDMORD0010VO
 * @Description : 발주정보 -> 기간정보 VO
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 11. 04.  SUN GIL CHOI 		오후 2:32:38 
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */

public class NEDMPAY0120VO implements Serializable {

	
	private static final long serialVersionUID = -6918711902569942487L;


	public NEDMPAY0120VO () {}
	
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
	/** TEXT Data */
	private String textData;
	/** 협력업체 코드 */
	private String[] venCds;
	
	private String buyYm ;
	private String strCd ;
	private String strNm ;
	private String bmanNo ;
	private String transBuyAmt; 
	private String transVat ;
	private String buyAmt ;
	private String vat ;
	private String payBuyAmt; 
	private String payVat ;
	private String remBuyAmt; 
	private String remVat ;
	private String remSum ;
	private String boru ;
	

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
	public String getSearchEntpCd() {
		return searchEntpCd;
	}
	public void setSearchEntpCd(String searchEntpCd) {
		this.searchEntpCd = searchEntpCd;
	}
	public String getSearchProductVal() {
		return searchProductVal;
	}
	public void setSearchProductVal(String searchProductVal) {
		this.searchProductVal = searchProductVal;
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
	public String getTextData() {
		return textData;
	}
	public void setTextData(String textData) {
		this.textData = textData;
	}
	public String getStrNm() {
		return strNm;
	}
	public void setStrNm(String strNm) {
		this.strNm = strNm;
	}
	public String getStrCd() {
		return strCd;
	}
	public void setStrCd(String strCd) {
		this.strCd = strCd;
	}
	public String getBmanNo() {
		return bmanNo;
	}
	public void setBmanNo(String bmanNo) {
		this.bmanNo = bmanNo;
	}
	public String getTransBuyAmt() {
		return transBuyAmt;
	}
	public void setTransBuyAmt(String transBuyAmt) {
		this.transBuyAmt = transBuyAmt;
	}
	public String getTransVat() {
		return transVat;
	}
	public void setTransVat(String transVat) {
		this.transVat = transVat;
	}
	public String getBuyAmt() {
		return buyAmt;
	}
	public void setBuyAmt(String buyAmt) {
		this.buyAmt = buyAmt;
	}
	public String getVat() {
		return vat;
	}
	public void setVat(String vat) {
		this.vat = vat;
	}
	public String getPayBuyAmt() {
		return payBuyAmt;
	}
	public void setPayBuyAmt(String payBuyAmt) {
		this.payBuyAmt = payBuyAmt;
	}
	public String getPayVat() {
		return payVat;
	}
	public void setPayVat(String payVat) {
		this.payVat = payVat;
	}
	public String getRemBuyAmt() {
		return remBuyAmt;
	}
	public void setRemBuyAmt(String remBuyAmt) {
		this.remBuyAmt = remBuyAmt;
	}
	public String getRemVat() {
		return remVat;
	}
	public void setRemVat(String remVat) {
		this.remVat = remVat;
	}
	public String getRemSum() {
		return remSum;
	}
	public void setRemSum(String remSum) {
		this.remSum = remSum;
	}
	public String getBoru() {
		return boru;
	}
	public void setBoru(String boru) {
		this.boru = boru;
	}
	public String getBuyYm() {
		return buyYm;
	}
	public void setBuyYm(String buyYm) {
		this.buyYm = buyYm;
	}
	
}
