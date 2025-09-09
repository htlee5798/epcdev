package com.lottemart.epc.edi.buy.model;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * @Class Name : NEDMBUY0040VO
 * @Description : 매입정보 전표별 조회 VO Class
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      		수정자          		수정내용
 *  -------    --------    ---------------------------
 * 2015.11.17	O YEUN KWON	  최초생성
 * 
 * @Copyright (C) 2000 ~ 2015 lottemart All right reserved.
 * </pre>
 */
public class NEDMBUY0040VO implements Serializable {

	private static final long serialVersionUID = 6338083734002146494L;		//필수 [각 VO Class 마다 동일하게 생성 금지]
	
	/* 검색 조건 */
	private String srchStartDate;			// 조회기간 from
	private String srchEndDate;				// 조회기간 to
	private ArrayList srchStoreVal;			// 점포선택
	private String srchEntpCode;			// 협력업체코드
	private String srchProductVal;			// 상품코드
	private String srchBuying;				// 매입구분[ex: 1:반품, 2:매입, 3:반품정정, 4:매입정정]
	
	private String isForwarding;			// 포워딩flag
	/* query select column */

 	private String buyDy; 					// 매입일
	private String strCd; 	                // 점포코드   
	private String strNm; 	                // 점포명   
	private String buySlipNo;				// 매입전표번호
	private String buyRtnNm;				// 납품.매입(1:반품,2:매입,3:반품정정,4:매입정정)
	private String ordDy;					// 발주전표번호
	private String totQty; 	                // 발주수량   
	private String totPrc;	                // 원가    
	private String buyBoxQty;               // 매입박스수량 
	private String buyQty; 	                // 매입수량   
	private String buyBuyPrc;               // 매입원가  

	
	private String[] venCds;
	
	private String textData;			
	
	

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

	public String getBuySlipNo() {
		return buySlipNo;
	}

	public void setBuySlipNo(String buySlipNo) {
		this.buySlipNo = buySlipNo;
	}

	public String getOrdDy() {
		return ordDy;
	}

	public void setOrdDy(String ordDy) {
		this.ordDy = ordDy;
	}

	public String getBuyRtnNm() {
		return buyRtnNm;
	}

	public void setBuyRtnNm(String buyRtnNm) {
		this.buyRtnNm = buyRtnNm;
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

	public String getSrchBuying() {
		return srchBuying;
	}

	public void setSrchBuying(String srchBuying) {
		this.srchBuying = srchBuying;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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

	public String getIsForwarding() {
		return isForwarding;
	}

	public void setIsForwarding(String isForwarding) {
		this.isForwarding = isForwarding;
	}
	
	public String getTextData() {
		return textData;
	}
	
	public void setTextData(String textData) {
		this.textData = textData;
	}
	
	
	
}

