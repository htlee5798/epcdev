package com.lottemart.epc.edi.inventory.model;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * @Class Name : NEDMINV0010VO
 * @Description : 재고정보 현재고(점포) VO Class
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      		수정자          		수정내용
 *  -------    --------    ---------------------------
 * 2015.11.18	O YEUN KWON	  최초생성
 * 
 * @Copyright (C) 2000 ~ 2015 lottemart All right reserved.
 * </pre>
 */


public class NEDMINV0010VO implements Serializable {


	private static final long serialVersionUID = 6766182773315305941L;

	public NEDMINV0010VO() {}

	/* 검색 조건 */
	private String srchStartDate;			// 조회기간 from
	private String srchEndDate;				// 조회기간 to
	private ArrayList srchStoreVal;			// 점포선택
	private String srchEntpCode;			// 협력업체코드
	private String srchProductVal;			// 상품코드
	private String srchStkMm;
	
	/* query select column */
 	private String strCd;            		// 점포코드
    private String strNm;            		// 점포명
    private String buySaleAmt;       		// 매입금액
    private String rtnSaleAmt;       		// 반품금액
    private String strioSaleAmt;     		// 점출입금액
    private String salePrcUpdownAmt; 		// 매가인상하금액
    private String saleSaleAmt;      		// 매출금액
    private String buyQty;           		// 매입수량
    private String rtnQty;           		// 반품수량
    private String strioQty;         		// 점출입수량
    private String saleQty;          		// 매출수량
    private String stkAdjSaleAmt;    
    private String stkAdjQty;        		
    private String bookFwdSale;      		// 기초재고금액
    private String bookFwdQty;       		// 기초재고 수량
    private String finalAmt;
    private String finalQty;
    
    private String textData;
    private String[] venCds;
	
    
	
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

	public String getSrchEntpCode() {
		return srchEntpCode;
	}

	public void setSrchEntpCode(String srchEntpCode) {
		this.srchEntpCode = srchEntpCode;
	}

	public String getSrchProductVal() {
		return srchProductVal;
	}

	public void setSrchProductVal(String srchProductVal) {
		this.srchProductVal = srchProductVal;
	}

	public String getSrchStkMm() {
		return srchStkMm;
	}

	public void setSrchStkMm(String srchStkMm) {
		this.srchStkMm = srchStkMm;
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

	public String getBuySaleAmt() {
		return buySaleAmt;
	}

	public void setBuySaleAmt(String buySaleAmt) {
		this.buySaleAmt = buySaleAmt;
	}

	public String getRtnSaleAmt() {
		return rtnSaleAmt;
	}

	public void setRtnSaleAmt(String rtnSaleAmt) {
		this.rtnSaleAmt = rtnSaleAmt;
	}

	public String getStrioSaleAmt() {
		return strioSaleAmt;
	}

	public void setStrioSaleAmt(String strioSaleAmt) {
		this.strioSaleAmt = strioSaleAmt;
	}

	public String getSalePrcUpdownAmt() {
		return salePrcUpdownAmt;
	}

	public void setSalePrcUpdownAmt(String salePrcUpdownAmt) {
		this.salePrcUpdownAmt = salePrcUpdownAmt;
	}

	public String getSaleSaleAmt() {
		return saleSaleAmt;
	}

	public void setSaleSaleAmt(String saleSaleAmt) {
		this.saleSaleAmt = saleSaleAmt;
	}

	public String getBuyQty() {
		return buyQty;
	}

	public void setBuyQty(String buyQty) {
		this.buyQty = buyQty;
	}

	public String getRtnQty() {
		return rtnQty;
	}

	public void setRtnQty(String rtnQty) {
		this.rtnQty = rtnQty;
	}

	public String getStrioQty() {
		return strioQty;
	}

	public void setStrioQty(String strioQty) {
		this.strioQty = strioQty;
	}

	public String getSaleQty() {
		return saleQty;
	}

	public void setSaleQty(String saleQty) {
		this.saleQty = saleQty;
	}

	public String getStkAdjSaleAmt() {
		return stkAdjSaleAmt;
	}

	public void setStkAdjSaleAmt(String stkAdjSaleAmt) {
		this.stkAdjSaleAmt = stkAdjSaleAmt;
	}

	public String getStkAdjQty() {
		return stkAdjQty;
	}

	public void setStkAdjQty(String stkAdjQty) {
		this.stkAdjQty = stkAdjQty;
	}

	public String getBookFwdSale() {
		return bookFwdSale;
	}

	public void setBookFwdSale(String bookFwdSale) {
		this.bookFwdSale = bookFwdSale;
	}

	public String getBookFwdQty() {
		return bookFwdQty;
	}

	public void setBookFwdQty(String bookFwdQty) {
		this.bookFwdQty = bookFwdQty;
	}

	public ArrayList getSrchStoreVal() {
		if (this.srchStoreVal != null) {
			ArrayList ret = new ArrayList();
			for (int i = 0; i < srchStoreVal.size(); i++) {
				ret.add(i, this.srchStoreVal.get(i));
			}
			
			return ret;
		} else {
			return null;
		}
	}

	public void setSrchStoreVal(ArrayList srchStoreVal) {
		if (srchStoreVal != null) {
			this.srchStoreVal = new ArrayList();
			for (int i = 0; i < srchStoreVal.size();i++) {
				this.srchStoreVal.add(i, srchStoreVal.get(i));
			}
		} else {
			this.srchStoreVal = null;
		}
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

	public String getTextData() {
		return textData;
	}

	public void setTextData(String textData) {
		this.textData = textData;
	}

	public String getFinalAmt() {
		return finalAmt;
	}

	public void setFinalAmt(String finalAmt) {
		this.finalAmt = finalAmt;
	}

	public String getFinalQty() {
		return finalQty;
	}

	public void setFinalQty(String finalQty) {
		this.finalQty = finalQty;
	}
    
	
	
}
