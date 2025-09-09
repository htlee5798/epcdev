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

public class NEDMPAY0061VO implements Serializable {

	
	private static final long serialVersionUID = -6918711902569942487L;


	public NEDMPAY0061VO () {}
	
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
	
	private String strCd; 				
	private String strNm; 				
	private String totBuyAmt; 			
	private String newProdPromo; 		
	private String standPromoAmtAuto; 	
	private String overPromoAmtAuto; 	
	private String newProdPromoAmtAuto; 	
	private String newProdPromoAmtMan; 	
	private String standPromoAmtMan; 	
	private String overPromoAmtMan; 		
	private String newProdPrmoAmtMan; 	
	private String sum;				 	
	

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
	public String getTotBuyAmt() {
		return totBuyAmt;
	}
	public void setTotBuyAmt(String totBuyAmt) {
		this.totBuyAmt = totBuyAmt;
	}
	public String getNewProdPromo() {
		return newProdPromo;
	}
	public void setNewProdPromo(String newProdPromo) {
		this.newProdPromo = newProdPromo;
	}
	public String getStandPromoAmtAuto() {
		return standPromoAmtAuto;
	}
	public void setStandPromoAmtAuto(String standPromoAmtAuto) {
		this.standPromoAmtAuto = standPromoAmtAuto;
	}
	public String getOverPromoAmtAuto() {
		return overPromoAmtAuto;
	}
	public void setOverPromoAmtAuto(String overPromoAmtAuto) {
		this.overPromoAmtAuto = overPromoAmtAuto;
	}
	public String getNewProdPromoAmtAuto() {
		return newProdPromoAmtAuto;
	}
	public void setNewProdPromoAmtAuto(String newProdPromoAmtAuto) {
		this.newProdPromoAmtAuto = newProdPromoAmtAuto;
	}
	public String getNewProdPromoAmtMan() {
		return newProdPromoAmtMan;
	}
	public void setNewProdPromoAmtMan(String newProdPromoAmtMan) {
		this.newProdPromoAmtMan = newProdPromoAmtMan;
	}
	public String getStandPromoAmtMan() {
		return standPromoAmtMan;
	}
	public void setStandPromoAmtMan(String standPromoAmtMan) {
		this.standPromoAmtMan = standPromoAmtMan;
	}
	public String getOverPromoAmtMan() {
		return overPromoAmtMan;
	}
	public void setOverPromoAmtMan(String overPromoAmtMan) {
		this.overPromoAmtMan = overPromoAmtMan;
	}
	public String getNewProdPrmoAmtMan() {
		return newProdPrmoAmtMan;
	}
	public void setNewProdPrmoAmtMan(String newProdPrmoAmtMan) {
		this.newProdPrmoAmtMan = newProdPrmoAmtMan;
	}
	public String getSum() {
		return sum;
	}
	public void setSum(String sum) {
		this.sum = sum;
	}
	
}
