package com.lottemart.epc.product.model;


import java.io.Serializable;

/**
 * @Class Name : PSCMPRD0001VO.java
 * @Description :
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 20. 오후 02:02:02 mjChoi
 * 
 * @Copyright (C) 2000 ~ 2011 롯데정보통신(주) All right reserved.
 * </pre>
 */
public class PSCMPRD0001VO implements Serializable 
{
	private static final long serialVersionUID = 4225080112161502320L;

	private String num = "";

	private String prodCd = "";
	private String prodNm = "";
	private String mdProdCd = "";
	private String mdSrcmkCd = "";	
	private String catNm = "";
	private String aprvYn = "";
	private String aprvDate = "";
	private String regDate = "";
	private String absenceYn = "";
	private String dispYn = "";
	private String haveImg = "";	
	private String buyPrc = "";
	private String currSellPrc = "";
	private String approvalChk = "";

	private String strCd = "";	
	private String itemCd = "";
	private String typeCd = "";
	private String statusFg = "";
	private String regId = "";

	private String strNm = "";
	private String cnt = "";	
	private String fedayMallProdDivnCd = "";
	private String auUseYn = "";
	private String itemCnt = "";	
	private String delivCnt = "";
	private String prodDivnCd = "";
	private String mdRecentSellDy = "";
	
	private String currentPage = "";//페이지수
	private String startDt = "";//조회조건 시작일
	private String endDt = "";//조회조건 종료일
	private String chkVal = "";//조회조건 체크박스값
	
	
	private String prodCommerce = ""; //전상법
	private String prodCommerceApprove = "";//전상법
	
	private String profitRate = "";
	
	private String mallDivnCd = "";
	
	private String onlineProdTypeCd = "";
	private String onlineProdTypeNm = "";

	private int startRow = 1;
	private int endRow = 10;
	
	 private String ecLinkYn;
     public String getEcLinkYn() {
           return ecLinkYn;
     }
     public void setEcLinkYn(String ecLinkYn) {
           this.ecLinkYn = ecLinkYn;
     }
     
	
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}

	public String getProdCd() {
		return prodCd;
	}
	public void setProdCd(String prodCd) {
		this.prodCd = prodCd;
	}

	public String getMdProdCd() {
		return mdProdCd;
	}
	public void setMdProdCd(String mdProdCd) {
		this.mdProdCd = mdProdCd;
	}

	public String getMdSrcmkCd() {
		return mdSrcmkCd;
	}
	public void setMdSrcmkCd(String mdSrcmkCd) {
		this.mdSrcmkCd =mdSrcmkCd;
	}

	public String getProdNm() {
		return prodNm;
	}
	public void setProdNm(String prodNm) {
		this.prodNm = prodNm;
	}

	public String getCatNm() {
		return catNm;
	}
	public void setCatNm(String catNm) {
		this.catNm = catNm;
	}

	public String getAprvYn() {
		return aprvYn;
	}
	public void setAprvYn(String aprvYn) {
		this.aprvYn = aprvYn;
	}

	public String getAprvDate() {
		return aprvDate;
	}
	public void setAprvDate(String aprvDate) {
		this.aprvDate = aprvDate;
	}	
	
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}	
	
	public String getAbsenceYn() {
		return absenceYn;
	}
	public void setAbsenceYn(String absenceYn) {
		this.absenceYn = absenceYn;
	}	
	
	public String getDispYn() {
		return dispYn;
	}
	public void setDispYn(String dispYn) {
		this.dispYn = dispYn;
	}	

	public String getHaveImg() {
		return haveImg;
	}
	public void setHaveImg(String haveImg) {
		this.haveImg = haveImg;
	}	
	
	public String getBuyPrc() {
		return buyPrc;
	}
	public void setBuyPrc(String buyPrc) {
		this.buyPrc = buyPrc;
	}	

	public String getCurrSellPrc() {
		return currSellPrc;
	}
	public void setCurrSellPrc(String currSellPrc) {
		this.currSellPrc = currSellPrc;
	}	
	
	public String getApprovalChk() {
		return approvalChk;
	}
	public void setApprovalChk(String approvalChk) {
		this.approvalChk = approvalChk;
	}	



	public String getStrCd() {
		return strCd;
	}
	public void setStrCd(String strCd) {
		this.strCd = strCd;
	}

	public String getItemCd() {
		return itemCd;
	}
	public void setItemCd(String itemCd) {
		this.itemCd = itemCd;
	}

	public String getTypeCd() {
		return typeCd;
	}
	public void setTypeCd(String typeCd) {
		this.typeCd = typeCd;
	}

	public String getStatusFg() {
		return statusFg;
	}
	public void setStatusFg(String statusFg) {
		this.statusFg = statusFg;
	}

	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}


	
	public String getStrNm() {
		return strNm;
	}
	public void setStrNm(String strNm) {
		this.strNm = strNm;
	}

	public String getCnt() {
		return cnt;
	}
	public void setCnt(String cnt) {
		this.cnt = cnt;
	}

	public String getFedayMallProdDivnCd() {
		return fedayMallProdDivnCd;
	}
	public void setFedayMallProdDivnCd(String fedayMallProdDivnCd) {
		this.fedayMallProdDivnCd = fedayMallProdDivnCd;
	}

	public String getAuUseYn() {
		return auUseYn;
	}
	public void setAuUseYn(String auUseYn) {
		this.auUseYn = auUseYn;
	}

	public String getItemCnt() {
		return itemCnt;
	}
	public void setItemCnt(String itemCnt) {
		this.itemCnt = itemCnt;
	}

	public String getDelivCnt() {
		return delivCnt;
	}
	public void setDelivCnt(String delivCnt) {
		this.delivCnt = delivCnt;
	}
	
	
	
	
	public int getStartRow() {
		return startRow;
	}
	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}
	public int getEndRow() {
		return endRow;
	}
	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}
	public String getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}
	public String getStartDt() {
		return startDt;
	}
	public void setStartDt(String startDt) {
		this.startDt = startDt;
	}
	public String getEndDt() {
		return endDt;
	}
	public void setEndDt(String endDt) {
		this.endDt = endDt;
	}
	public String getProdDivnCd() {
		return prodDivnCd;
	}
	public void setProdDivnCd(String prodDivnCd) {
		this.prodDivnCd = prodDivnCd;
	}
	public String getMdRecentSellDy() {
		return mdRecentSellDy;
	}
	public void setMdRecentSellDy(String mdRecentSellDy) {
		this.mdRecentSellDy = mdRecentSellDy;
	}
	public String getChkVal() {
		return chkVal;
	}
	public void setChkVal(String chkVal) {
		this.chkVal = chkVal;
	}
	
//전상법
	public String getProdCommerce() {
		return prodCommerce;
	}
	public void setProdCommerce(String prodCommerce) {
		this.prodCommerce = prodCommerce;
	}
	public String getProdCommerceApprove() {
		return prodCommerceApprove;
	}
	public void setProdCommerceApprove(String prodCommerceApprove) {
		this.prodCommerceApprove = prodCommerceApprove;
	}
	
	
	//이익율
	public String getProfitRate() {
		return profitRate;
	}
	public void setProfitRate(String profitRate) {
		this.profitRate = profitRate;
	}
	public String getMallDivnCd() {
		return mallDivnCd;
	}
	public void setMallDivnCd(String mallDivnCd) {
		this.mallDivnCd = mallDivnCd;
	}
	public String getOnlineProdTypeCd() {
		return onlineProdTypeCd;
	}
	public void setOnlineProdTypeCd(String onlineProdTypeCd) {
		this.onlineProdTypeCd = onlineProdTypeCd;
	}
	public String getOnlineProdTypeNm() {
		return onlineProdTypeNm;
	}
	public void setOnlineProdTypeNm(String onlineProdTypeNm) {
		this.onlineProdTypeNm = onlineProdTypeNm;
	}
	

}