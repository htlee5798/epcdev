package com.lottemart.epc.edi.buy.model;

import java.io.Serializable;
/**
 * 신규상품등록 조회 VO
 * @author SONG MIN KYO
 * @since 2015.11.04
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      			수정자           		수정내용
 *  -----------    	------------    ---------------------------
 *   2015.11.04  	SONG MIN KYO    최초 생성
 *
 * </pre>
 */
public class PEDMBUY0000VO implements Serializable {

	private static final long serialVersionUID = 6338083734002146495L;		//필수 [각 VO Class 마다 동일하게 생성 금지]
	
	/* 검색 조건 */
	private String searchStartDate;			// 조회기간 from
	private String searchEndDate;			// 조회기간 to
	private String searchStoreVal[];		// 점포선택
	private String searchEntpCode;			// 협력업체코드
	private String searchProductVal;		// 상품코드
	private String searchBuying;			// 매입구분		// 상품상태[ex: 1:반품, 2:매입, 3:반품정정, 4:매입정정]
	
	/* query select column */
	
	private String buyDy; 					// 매입일
	private String totQty; 	                // 발주수량   
	private String totPrc;	                // 원가    
	private String buyBoxQty;               // 매입박스수량 
	private String buyQty; 	                // 매입수량   
	private String buyBuyPrc;               // 매입원가   
	private String ansQty; 	                // 납품가능수량 
	private String ansPrc; 	                // 납품가능금액
	
	private String[] venCds;
	
	

	public String getSearchStartDate() {
		return searchStartDate;
	}

	public void setSearchStartDate(String searchStartDate) {
		this.searchStartDate = searchStartDate;
	}

	public String getSearchEndDate() {
		return searchEndDate;
	}

	public void setSearchEndDate(String searchEndDate) {
		this.searchEndDate = searchEndDate;
	}

	public String[] getSearchStoreVal() {
		return searchStoreVal;
	}

	public void setSearchStoreVal(String[] searchStoreVal) {
		this.searchStoreVal = searchStoreVal;
	}

	public String getSearchEntpCode() {
		return searchEntpCode;
	}

	public void setSearchEntpCode(String searchEntpCode) {
		this.searchEntpCode = searchEntpCode;
	}

	public String getSearchProductVal() {
		return searchProductVal;
	}

	public void setSearchProductVal(String searchProductVal) {
		this.searchProductVal = searchProductVal;
	}

	public String getSearchBuying() {
		return searchBuying;
	}

	public void setSearchBuying(String searchBuying) {
		this.searchBuying = searchBuying;
	}

	public String getBuyDy() {
		return buyDy;
	}

	public void setBuyDy(String buyDy) {
		this.buyDy = buyDy;
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

	public String getBuyBoxQty() {
		return buyBoxQty;
	}

	public void setBuyBoxQty(String buyBoxQty) {
		this.buyBoxQty = buyBoxQty;
	}

	public String getBuyQty() {
		return buyQty;
	}

	public void setBuyQty(String buyQty) {
		this.buyQty = buyQty;
	}

	public String getBuyBuyPrc() {
		return buyBuyPrc;
	}

	public void setBuyBuyPrc(String buyBuyPrc) {
		this.buyBuyPrc = buyBuyPrc;
	}

	public String getAnsQty() {
		return ansQty;
	}

	public void setAnsQty(String ansQty) {
		this.ansQty = ansQty;
	}

	public String getAnsPrc() {
		return ansPrc;
	}

	public void setAnsPrc(String ansPrc) {
		this.ansPrc = ansPrc;
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
	
	
	
	
	
}

