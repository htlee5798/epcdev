package com.lottemart.epc.edi.inventory.model;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * @Class Name : NEDMINV0030VO
 * @Description : 재고정보 센터 점출입 VO Class
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


public class NEDMINV0040VO implements Serializable {


	private static final long serialVersionUID = 6766182773315305941L;

	public NEDMINV0040VO() {}

	/* 검색 조건 */
	private String srchStartDate;			// 조회기간 from
	private String srchEndDate;				// 조회기간 to
	private ArrayList srchStoreVal;			// 점포선택
	private String srchEntpCode;			// 협력업체코드
	private String srchProductVal;			// 상품코드
	private String srchStkMm;
	   
	/* query select column */				
	private String srcmkCd;                 // 판매코드  
	private String prodCd;                  // 상품코드  
	private String prodNm;                  // 상품명  
	private String ctrCd;                   // 센터코드  
	private String ctrNm;                   // 샌터명   
	private String strCd;                   // 점포코드  
	private String strNm;                   // 점포명   
	private String stroQty;                	// 점출수량  
	private String stroSaleAmt;           	// 점출금액  
	private String striQty;                	// 점입수량  
	private String striSaleAmt;           	// 점입금액  
	private String strioQty;                // 점출입수량 
	private String strioSaleAmt;            // 점출입금액 
	

    
    private String isForwarding;			// 포워딩flag
    
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

	public String getSrcmkCd() {
		return srcmkCd;
	}

	public void setSrcmkCd(String srcmkCd) {
		this.srcmkCd = srcmkCd;
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

	public String getCtrCd() {
		return ctrCd;
	}

	public void setCtrCd(String ctrCd) {
		this.ctrCd = ctrCd;
	}

	public String getCtrNm() {
		return ctrNm;
	}

	public void setCtrNm(String ctrNm) {
		this.ctrNm = ctrNm;
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

	public String getStroQty() {
		return stroQty;
	}

	public void setStroQty(String stroQty) {
		this.stroQty = stroQty;
	}

	public String getStroSaleAmt() {
		return stroSaleAmt;
	}

	public void setStroSaleAmt(String stroSaleAmt) {
		this.stroSaleAmt = stroSaleAmt;
	}

	public String getStriQty() {
		return striQty;
	}

	public void setStriQty(String striQty) {
		this.striQty = striQty;
	}

	public String getStriSaleAmt() {
		return striSaleAmt;
	}

	public void setStriSaleAmt(String striSaleAmt) {
		this.striSaleAmt = striSaleAmt;
	}

	public String getStrioQty() {
		return strioQty;
	}

	public void setStrioQty(String strioQty) {
		this.strioQty = strioQty;
	}

	public String getStrioSaleAmt() {
		return strioSaleAmt;
	}

	public void setStrioSaleAmt(String strioSaleAmt) {
		this.strioSaleAmt = strioSaleAmt;
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

	public String getIsForwarding() {
		return isForwarding;
	}

	public void setIsForwarding(String isForwarding) {
		this.isForwarding = isForwarding;
	}
    
	
	
}
