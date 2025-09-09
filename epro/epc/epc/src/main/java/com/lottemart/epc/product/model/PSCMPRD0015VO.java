package com.lottemart.epc.product.model;

import java.io.Serializable;

/**
 * 
 * @author cwj
 * @Description : 상품관리 - 대표상품코드관리
 * @Class : com.lottemart.epc.product.model
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 
 * @version : 
 * </pre>
 */
public class PSCMPRD0015VO implements Serializable{

	/** 
	 * @see 
	 * @Description : 
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -9834076763987858L;

	private String num						="";
	private String cnt 						="";
	private String prodNm				="";
	private String buyPrc					="";
	private String regDate				="";
	private String applyYn				="";
	private String promoCnt				="";
	private String l1Nm					="";	
	private String strCd					="";
	private String strNm					="";
	private String chgbBuyPrc			="";
	private String chgbSellPrc			="";
	private String chgbCurrSellPrc		="";
	private String reqDate				="";
	private String aprvYn					="";
	private String profitRateS			="";	
	private String prodCd 				= "";	/* 인터넷상품코드 */
	private String repProdCd 			= "";	/* 대표판매코드 */
	private String applyStartDy 		= "";	/* 적용시작일자 */
	private String applyEndDy 			= "";	/* 적용시작일자 */	
	private String sellPrc 					= "";	/* 매가 */
	private String currSellPrc 			= "";	/* 판매가 */
	private String profitRate 			= "";	/* 이익율 */
	private String taxatDivnCd 			= "";	/* 과세구분코드 */	
	private String regId 					= "";	/* 등록자 */
	private String modId 					= "";	/* 수정자 */	
	private String applyStartDy_old 	= ""; /* update 할 대상의 적용시작일자 */	
	private String vendorId				= "";
	
	private String itemCd;
	private String optnProdPrcMgrYn;
	private String reqReasonContent;
	private String retnReason;
	private String admYn;
	private String repProdCdNm;
	private String seqNo;
	
	
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getCnt() {
		return cnt;
	}
	public void setCnt(String cnt) {
		this.cnt = cnt;
	}
	public String getProdNm() {
		return prodNm;
	}
	public void setProdNm(String prodNm) {
		this.prodNm = prodNm;
	}
	public String getBuyPrc() {
		return buyPrc;
	}
	public void setBuyPrc(String buyPrc) {
		this.buyPrc = buyPrc;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getApplyYn() {
		return applyYn;
	}
	public void setApplyYn(String applyYn) {
		this.applyYn = applyYn;
	}
	public String getPromoCnt() {
		return promoCnt;
	}
	public void setPromoCnt(String promoCnt) {
		this.promoCnt = promoCnt;
	}
	public String getL1Nm() {
		return l1Nm;
	}
	public void setL1Nm(String l1Nm) {
		this.l1Nm = l1Nm;
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
	public String getChgbBuyPrc() {
		return chgbBuyPrc;
	}
	public void setChgbBuyPrc(String chgbBuyPrc) {
		this.chgbBuyPrc = chgbBuyPrc;
	}
	public String getChgbSellPrc() {
		return chgbSellPrc;
	}
	public void setChgbSellPrc(String chgbSellPrc) {
		this.chgbSellPrc = chgbSellPrc;
	}
	public String getChgbCurrSellPrc() {
		return chgbCurrSellPrc;
	}
	public void setChgbCurrSellPrc(String chgbCurrSellPrc) {
		this.chgbCurrSellPrc = chgbCurrSellPrc;
	}
	public String getReqDate() {
		return reqDate;
	}
	public void setReqDate(String reqDate) {
		this.reqDate = reqDate;
	}
	public String getAprvYn() {
		return aprvYn;
	}
	public void setAprvYn(String aprvYn) {
		this.aprvYn = aprvYn;
	}
	public String getProfitRateS() {
		return profitRateS;
	}
	public void setProfitRateS(String profitRateS) {
		this.profitRateS = profitRateS;
	}
	
	public String getProdCd() {
		return prodCd;
	}
	public void setProdCd(String prodCd) {
		this.prodCd = prodCd;
	}
	public String getRepProdCd() {
		return repProdCd;
	}
	public void setRepProdCd(String repProdCd) {
		this.repProdCd = repProdCd;
	}
	public String getApplyStartDy() {
		return applyStartDy;
	}
	public void setApplyStartDy(String applyStartDy) {
		this.applyStartDy = applyStartDy;
	}
	
	public String getApplyEndDy() {
		return applyEndDy;
	}
	public void setApplyEndDy(String applyEndDy) {
		this.applyEndDy = applyEndDy;
	}
	public String getSellPrc() {
		return sellPrc;
	}
	public void setSellPrc(String sellPrc) {
		this.sellPrc = sellPrc;
	}
	public String getCurrSellPrc() {
		return currSellPrc;
	}
	public void setCurrSellPrc(String currSellPrc) {
		this.currSellPrc = currSellPrc;
	}
	public String getProfitRate() {
		return profitRate;
	}
	public void setProfitRate(String profitRate) {
		this.profitRate = profitRate;
	}
	public String getTaxatDivnCd() {
		return taxatDivnCd;
	}
	public void setTaxatDivnCd(String taxatDivnCd) {
		this.taxatDivnCd = taxatDivnCd;
	}
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	public String getModId() {
		return modId;
	}
	public void setModId(String modId) {
		this.modId = modId;
	}
	public String getApplyStartDy_old() {
		return applyStartDy_old;
	}
	public void setApplyStartDy_old(String applyStartDy_old) {
		this.applyStartDy_old = applyStartDy_old;
	}
	public String getVendorId() {
		return vendorId;
	}
	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}

	public String getItemCd() {
		return itemCd;
	}
	public void setItemCd(String itemCd) {
		this.itemCd = itemCd;
	}
	public String getOptnProdPrcMgrYn() {
		return optnProdPrcMgrYn;
	}
	public void setOptnProdPrcMgrYn(String optnProdPrcMgrYn) {
		this.optnProdPrcMgrYn = optnProdPrcMgrYn;
	}
	public String getReqReasonContent() {
		return reqReasonContent;
	}
	public void setReqReasonContent(String reqReasonContent) {
		this.reqReasonContent = reqReasonContent;
	}	
	public String getRetnReason() {
		return retnReason;
	}
	public void setRetnReason(String retnReason) {
		this.retnReason = retnReason;
	}
	public String getAdmYn() {
		return admYn;
	}
	public void setAdmYn(String admYn) {
		this.admYn = admYn;
	}
	public String getRepProdCdNm() {
		return repProdCdNm;
	}
	public void setRepProdCdNm(String repProdCdNm) {
		this.repProdCdNm = repProdCdNm;
	}
	public String getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}		
	
	
}
