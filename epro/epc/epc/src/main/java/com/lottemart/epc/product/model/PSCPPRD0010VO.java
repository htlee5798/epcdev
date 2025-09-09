/**
 * @prjectName  : lottemart 프로젝트   
 * @since       : 2011. 10. 18. 오후 2:33:50
 * @author      : yskim 
 * @Copyright(c) 2000 ~ 2011 lottemart
 *  All rights reserved.
 */

package com.lottemart.epc.product.model;

import java.io.Serializable;
import java.util.List;

/**
 * @Class Name : PSCMPRD0009VO
 * @Description : 상품가격변경요청리스트 VO 클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 18. 오후 4:43:29 yskim
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
public class PSCPPRD0010VO implements Serializable {

	private static long serialVersionUID = 6787258195500161597L;

	/** 페이징 관련 페이지당 목록 수 */
    private int rowsPerPage;
    
    /** 페이지 관련 현재 페이지 */
    private int currentPage;
    
    /** 저장시 등록과 수정을 구분하는 플래그 */
	private String crud = "";
	
	/** 생성할 데이터인지 수정할 데이터인지 구분하는 플래그 */
	private String isUpd = "";
    
	/** 인터넷상품코드 */
	private String prodCd = "";
	
	/** 인터넷상품코드 */
	private String prodNm = "";
	
	/** 단품코드 */
	private String itemCd = "";
	
	/** 점포코드 */
	private String strCd = "";
	
	/** 점포코드 */
	private String strNm = "";
	
	/** 요청순번 */
	private String reqSeq = "";
	
	/** 변경전원가 */
	private long chgbBuyPrc = 0;
	
	/** 변경전매가 */
	private long chgbSellPrc = 0;
	
	/** 변경전판매가 */
	private long chgbCurrSellPrc = 0;
	
	/** 이익률 */
	private long profitRate = 0;
	/** 면과세 */
	private String taxatDivnCd = "";
	
	/** 변경후원가 */
	private long chgaBuyPrc = 0;
	
	/** 변경후매가 */
	private long chgaSellPrc = 0;
	
	/** 변경후판매가 */
	private long chgaCurrSellPrc = 0;
	
	/** 요청일시 */
	private String reqDate = "";
	
	/** 요청자 */
	private String reqPsn = "";
	
	/** 승인여부 */
	private String aprvYn = "";
	
	/** 승인일시 */
	private String aprvDate = "";
	
	/** 승인자 */
	private String aprvPsn = "";
	
	/** 등록일시 */
	private String regDate = "";
	
	/** 등록자 */
	private String regId = "";
	
	/** 수정일시 */
	private String modDate = "";
	
	/** 수정자 */
	private String modId = "";

	/** 협력업체코드 */
	private String vendorId = "";
	
	/** 변경요청사유 */
	private String chgReqReasonContent = "";
	
	/** 선택된 인터넷상품코드 리스트 */
	private List<String> selectedProdCd;
	
	/** 조회시작일 */
    private String startDate = "";
    /** 조회종료일 */
    private String endDate = "";
	private String searchCondition = "";
	private String searchWord = "";
	private String profitRateS = "";
	private int totalCount = 0;

	private String mdProdCd = "";
	private String dispYn = "";
	private String mdSrcmkCd = "";
	private String categoryNm = "";
	private String absenceYn = "";
	private String cullSellPrc = "";
	
    /** 협력업체코드 */
    private List<String> vendorList;

    
    public long getProfitRate() {
		return profitRate;
	}
	public void setProfitRate(long profitRate) {
		this.profitRate = profitRate;
	}
	
	public String getTaxatDivnCd() {
		return taxatDivnCd;
	}
	public void setTaxatDivnCd(String taxatDivnCd) {
		this.taxatDivnCd = taxatDivnCd;
	}
	
	
	
	public List<String> getVendorList() {
		return vendorList;
	}
	public void setVendorList(List<String> vendorList) {
		this.vendorList = vendorList;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getSearchCondition() {
		return searchCondition;
	}
	public void setSearchCondition(String searchCondition) {
		this.searchCondition = searchCondition;
	}
	public String getSearchWord() {
		return searchWord;
	}
	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}
	public int getRowsPerPage() {
		return rowsPerPage;
	}
	public void setRowsPerPage(int rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public String getCrud() {
		return crud;
	}
	public void setCrud(String crud) {
		this.crud = crud;
	}
	public String getIsUpd() {
		return isUpd;
	}
	public void setIsUpd(String isUpd) {
		this.isUpd = isUpd;
	}
	public String getProdCd() {
		return prodCd;
	}
	public void setProdCd(String prodCd) {
		this.prodCd = prodCd;
	}
	public String getItemCd() {
		return itemCd;
	}
	public void setItemCd(String itemCd) {
		this.itemCd = itemCd;
	}
	public String getStrCd() {
		return strCd;
	}
	public void setStrCd(String strCd) {
		this.strCd = strCd;
	}
	public String getReqSeq() {
		return reqSeq;
	}
	public void setReqSeq(String reqSeq) {
		this.reqSeq = reqSeq;
	}
	public long getChgbBuyPrc() {
		return chgbBuyPrc;
	}
	public void setChgbBuyPrc(long chgbBuyPrc) {
		this.chgbBuyPrc = chgbBuyPrc;
	}
	public long getChgbSellPrc() {
		return chgbSellPrc;
	}
	public void setChgbSellPrc(long chgbSellPrc) {
		this.chgbSellPrc = chgbSellPrc;
	}
	public long getChgbCurrSellPrc() {
		return chgbCurrSellPrc;
	}
	public void setChgbCurrSellPrc(long chgbCurrSellPrc) {
		this.chgbCurrSellPrc = chgbCurrSellPrc;
	}
	public long getChgaBuyPrc() {
		return chgaBuyPrc;
	}
	public void setChgaBuyPrc(long chgaBuyPrc) {
		this.chgaBuyPrc = chgaBuyPrc;
	}
	public long getChgaSellPrc() {
		return chgaSellPrc;
	}
	public void setChgaSellPrc(long chgaSellPrc) {
		this.chgaSellPrc = chgaSellPrc;
	}
	public long getChgaCurrSellPrc() {
		return chgaCurrSellPrc;
	}
	public void setChgaCurrSellPrc(long chgaCurrSellPrc) {
		this.chgaCurrSellPrc = chgaCurrSellPrc;
	}
	public String getReqDate() {
		return reqDate;
	}
	public void setReqDate(String reqDate) {
		this.reqDate = reqDate;
	}
	public String getReqPsn() {
		return reqPsn;
	}
	public void setReqPsn(String reqPsn) {
		this.reqPsn = reqPsn;
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
	public String getAprvPsn() {
		return aprvPsn;
	}
	public void setAprvPsn(String aprvPsn) {
		this.aprvPsn = aprvPsn;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	public String getModDate() {
		return modDate;
	}
	public void setModDate(String modDate) {
		this.modDate = modDate;
	}
	public String getModId() {
		return modId;
	}
	public void setModId(String modId) {
		this.modId = modId;
	}
	public String getVendorId() {
		return vendorId;
	}
	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}
	public String getChgReqReasonContent() {
		return chgReqReasonContent;
	}
	public void setChgReqReasonContent(String chgReqReasonContent) {
		this.chgReqReasonContent = chgReqReasonContent;
	}
	public List<String> getSelectedProdCd() {
		return selectedProdCd;
	}
	public void setSelectedProdCd(List<String> selectedProdCd) {
		this.selectedProdCd = selectedProdCd;
	}

	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("\n rowsPerPage::").append(rowsPerPage);
		sb.append("\n currentPage::").append(currentPage);
		sb.append("\n crud::").append(crud);
		sb.append("\n prodCd::").append(prodCd);
		sb.append("\n prodNm::").append(prodNm);
		sb.append("\n itemCd::").append(itemCd);
		sb.append("\n strCd::").append(strCd);
		sb.append("\n strNm::").append(strNm);
		sb.append("\n reqSeq::").append(reqSeq);
		sb.append("\n chgbBuyPrc::").append(chgbBuyPrc);
		sb.append("\n chgbSellPrc::").append(chgbSellPrc);
		sb.append("\n chgbCurrSellPrc::").append(chgbCurrSellPrc);
		sb.append("\n chgaBuyPrc::").append(chgaBuyPrc);
		sb.append("\n profitRate::").append(profitRate);
		sb.append("\n taxatDivnCd::").append(taxatDivnCd);
		sb.append("\n chgaSellPrc::").append(chgaSellPrc);
		sb.append("\n chgaCurrSellPrc::").append(chgaCurrSellPrc);
		sb.append("\n reqDate::").append(reqDate);
		sb.append("\n reqPsn::").append(reqPsn);
		sb.append("\n aprvYn::").append(aprvYn);
		sb.append("\n aprvDate::").append(aprvDate);
		sb.append("\n aprvPsn::").append(aprvPsn);
		sb.append("\n regDate::").append(regDate);
		sb.append("\n regId::").append(regId);
		sb.append("\n modDate::").append(modDate);
		sb.append("\n modId::").append(modId);
		sb.append("\n vendorId::").append(vendorId);
		sb.append("\n mdProdCd::").append(mdProdCd);
		sb.append("\n mdSrcmkCd::").append(mdSrcmkCd);
		sb.append("\n categoryNm::").append(categoryNm);
		sb.append("\n absenceYn::").append(absenceYn);
		sb.append("\n cullSellPrc::").append(cullSellPrc);
		sb.append("\n dispYn::").append(dispYn);
		sb.append("\n");
		return sb.toString();
	}
	public String getProfitRateS() {
		return profitRateS;
	}
	public void setProfitRateS(String profitRateS) {
		this.profitRateS = profitRateS;
	}
	public String getProdNm() {
		return prodNm;
	}
	public void setProdNm(String prodNm) {
		this.prodNm = prodNm;
	}
	public String getStrNm() {
		return strNm;
	}
	public void setStrNm(String strNm) {
		this.strNm = strNm;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public static void setSerialversionuid(long serialversionuid) {
		serialVersionUID = serialversionuid;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
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
		this.mdSrcmkCd = mdSrcmkCd;
	}
	public String getCategoryNm() {
		return categoryNm;
	}
	public void setCategoryNm(String categoryNm) {
		this.categoryNm = categoryNm;
	}
	public String getAbsenceYn() {
		return absenceYn;
	}
	public void setAbsenceYn(String absenceYn) {
		this.absenceYn = absenceYn;
	}
	public String getCullSellPrc() {
		return cullSellPrc;
	}
	public void setCullSellPrc(String cullSellPrc) {
		this.cullSellPrc = cullSellPrc;
	}
	public String getDispYn() {
		return dispYn;
	}
	public void setDispYn(String dispYn) {
		this.dispYn = dispYn;
	}


	


}
