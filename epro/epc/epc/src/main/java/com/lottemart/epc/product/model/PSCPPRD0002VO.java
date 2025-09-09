package com.lottemart.epc.product.model;


import java.io.Serializable;

/**
 * @Class Name : PSCPPRD0002VO.java
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
public class PSCPPRD0002VO implements Serializable 
{
	private static long serialVersionUID = 726729771081532552L;

	private String prodCd = "";
	private String prodNm = "";
	private String categoryId = "";
	private String categoryNm = "";
	private String templateId = "";
	private String templateTitleNm = "";
	private String homeCd = "";
	private String homeNm = "";
	private String brandNm = "";
	private String makerNm = "";
	private String mainMtrlNm = "";
	private String vendorId = "";
	private String vendorNm = "";
	private String minOrdPsbtQty = "";
	private String maxOrdPsbtQty = "";
	private String frshProdSellUnitNm = "";
	private String useYn = "";
	private String soutYn = "";
	private String variationYn = "";
	private String nfomlVariationDesc = "";
	private String auUseYn = "";
	private String fedayMallSellPsbtYn = "";
	private String dispYn = "";
	private String cmbnMallSellPsbtYn = "";
	private String aprvYn = "";
	private String categoryCd = "";
	private String modId = "";	
	private String regId = "";
	private String itemCnt = "";
	private String delivCnt = "";
	private String siteRegYn = "";
	private String homeChgYn = "";
	private String siteCd = "";
	private String fedayMallUseYn = "";
	private String typeCd = "";
	private String statusFg = "";
	private String presentNm = "";
	private String dpTotamt = "";
	private String frshConvQty = "";
	private String bighandBigentpDivnCd = "";
	private String productNationCd = "";
	private String fedayMallProdDivnCd = "";
	private String majorCd = "";
	private String minorCd = "";
	private String cdNm = "";
	private String linkTp = "";
	private String linkSts = "";
	private String regDate = "";
	private String siteProdCd = "";	
	private String siteNm = "";	
	private String fedayMallProdDivnNm = "";	
	private String catCd = "";
	private String num = "";
	private String SnglBkYn = "";

	private String prodPatenDivnCd = "";
	private String prodStandardNm = "";
	private String dpUnitCd = "";
	private String repProdCd = "";
	private String taxatDivnCd = "";
	private String prodTypeDivnCd = "";
	private String ctrTypeDivnCd = "";
	private String prcIssueDivnCd = "";
	private String prodDesc = "";
	private String qtyWegtDivnCd = "";
	private String maxKeepDdCnt = "";
	private String auSellYn = "";
	private String profitRate = "";
	private String ProdDivnCd = "";

	private String dpTotQty = "";
	private String dpBaseQty = "";
	private String dispReason = "";
	
	private String prodUrl = "";
	
	private String modelNm = ""; /* 모델명 */
	private String ecoYn = ""; /* 친환경인증여부 */
	private String ecoNm = ""; /* 친환경인증분류명 */
	private String dlvGa = ""; /* 차등배송비여부 */
	private String dlvDt = ""; /* 별도설치비내용 */	 
	private String insCo = ""; /* 별도설치비유무 */
	
	/* 2차 추가항목 2016.06.02*/
	private String onlineProdTypeCd = "";   		/* 온라인상품유형 */
	private String optnLoadSetQty = "";      		/* 골라담기세트수량 */
	private String optnLoadContent = "";			/* 골라담기내용 */
	private String rservOrdPsbtStartDy = "";		/* 예약주문가능시작일자 */
	private String rservOrdPsbtEndDy = "";      /* 예약주문가능종료일자 */
	private String rservProdPickIdctDy = "";      /* 예약상품출고지시일 */
	private String hopeDeliPsbtDd = "";            /* 희망배송가능일 */
	private String prodOptionDesc = "";
	private boolean exprTypeCd;
	private String sellerRecomm = "";

	public boolean getExprTypeCd() {
		return exprTypeCd;
	}
	public void setExprTypeCd(boolean exprTypeCd) {
		this.exprTypeCd = exprTypeCd;
	}
	public String getSellerRecomm() {
		return sellerRecomm;
	}
	public void setSellerRecomm(String sellerRecomm) {
		this.sellerRecomm = sellerRecomm;
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
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryNm() {
		return categoryNm;
	}
	public void setCategoryNm(String categoryNm) {
		this.categoryNm = categoryNm;
	}
	public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	public String getTemplateTitleNm() {
		return templateTitleNm;
	}
	public void setTemplateTitleNm(String templateTitleNm) {
		this.templateTitleNm = templateTitleNm;
	}
	public String getHomeCd() {
		return homeCd;
	}
	public void setHomeCd(String homeCd) {
		this.homeCd = homeCd;
	}
	public String getHomeNm() {
		return homeNm;
	}
	public void setHomeNm(String homeNm) {
		this.homeNm = homeNm;
	}
	public String getBrandNm() {
		return brandNm;
	}
	public void setBrandNm(String brandNm) {
		this.brandNm = brandNm;
	}
	public String getMakerNm() {
		return makerNm;
	}
	public void setMakerNm(String makerNm) {
		this.makerNm = makerNm;
	}
	public String getMainMtrlNm() {
		return mainMtrlNm;
	}
	public void setMainMtrlNm(String mainMtrlNm) {
		this.mainMtrlNm = mainMtrlNm;
	}
	public String getVendorId() {
		return vendorId;
	}
	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}
	public String getVendorNm() {
		return vendorNm;
	}
	public void setVendorNm(String vendorNm) {
		this.vendorNm = vendorNm;
	}
	public String getMinOrdPsbtQty() {
		return minOrdPsbtQty;
	}
	public void setMinOrdPsbtQty(String minOrdPsbtQty) {
		this.minOrdPsbtQty = minOrdPsbtQty;
	}
	public String getMaxOrdPsbtQty() {
		return maxOrdPsbtQty;
	}
	public void setMaxOrdPsbtQty(String maxOrdPsbtQty) {
		this.maxOrdPsbtQty = maxOrdPsbtQty;
	}
	public String getFrshProdSellUnitNm() {
		return frshProdSellUnitNm;
	}
	public void setFrshProdSellUnitNm(String frshProdSellUnitNm) {
		this.frshProdSellUnitNm = frshProdSellUnitNm;
	}
	public String getUseYn() {
		return useYn;
	}
	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}
	public String getSoutYn() {
		return soutYn;
	}
	public void setSoutYn(String soutYn) {
		this.soutYn = soutYn;
	}
	public String getVariationYn() {
		return variationYn;
	}
	public void setVariationYn(String variationYn) {
		this.variationYn = variationYn;
	}
	public String getNfomlVariationDesc() {
		return nfomlVariationDesc;
	}
	public void setNfomlVariationDesc(String nfomlVariationDesc) {
		this.nfomlVariationDesc = nfomlVariationDesc;
	}
	public String getAuUseYn() {
		return auUseYn;
	}
	public void setAuUseYn(String auUseYn) {
		this.auUseYn = auUseYn;
	}
	public String getFedayMallSellPsbtYn() {
		return fedayMallSellPsbtYn;
	}
	public void setFedayMallSellPsbtYn(String fedayMallSellPsbtYn) {
		this.fedayMallSellPsbtYn = fedayMallSellPsbtYn;
	}
	public String getDispYn() {
		return dispYn;
	}
	public void setDispYn(String dispYn) {
		this.dispYn = dispYn;
	}
	public String getCmbnMallSellPsbtYn() {
		return cmbnMallSellPsbtYn;
	}
	public void setCmbnMallSellPsbtYn(String cmbnMallSellPsbtYn) {
		this.cmbnMallSellPsbtYn = cmbnMallSellPsbtYn;
	}
	public String getAprvYn() {
		return aprvYn;
	}
	public void setAprvYn(String aprvYn) {
		this.aprvYn = aprvYn;
	}
	public String getCategoryCd() {
		return categoryCd;
	}
	public void setCategoryCd(String categoryCd) {
		this.categoryCd = categoryCd;
	}
	public String getModId() {
		return modId;
	}
	public void setModId(String modId) {
		this.modId = modId;
	}
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
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
	public String getSiteRegYn() {
		return siteRegYn;
	}
	public void setSiteRegYn(String siteRegYn) {
		this.siteRegYn = siteRegYn;
	}
	public String getHomeChgYn() {
		return homeChgYn;
	}
	public void setHomeChgYn(String homeChgYn) {
		this.homeChgYn = homeChgYn;
	}
	public String getSiteCd() {
		return siteCd;
	}
	public void setSiteCd(String siteCd) {
		this.siteCd = siteCd;
	}
	public String getFedayMallUseYn() {
		return fedayMallUseYn;
	}
	public void setFedayMallUseYn(String fedayMallUseYn) {
		this.fedayMallUseYn = fedayMallUseYn;
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
	public String getPresentNm() {
		return presentNm;
	}
	public void setPresentNm(String presentNm) {
		this.presentNm = presentNm;
	}
	public String getDpTotamt() {
		return dpTotamt;
	}
	public void setDpTotamt(String dpTotamt) {
		this.dpTotamt = dpTotamt;
	}
	public String getFrshConvQty() {
		return frshConvQty;
	}
	public void setFrshConvQty(String frshConvQty) {
		this.frshConvQty = frshConvQty;
	}
	public String getBighandBigentpDivnCd() {
		return bighandBigentpDivnCd;
	}
	public void setBighandBigentpDivnCd(String bighandBigentpDivnCd) {
		this.bighandBigentpDivnCd = bighandBigentpDivnCd;
	}
	public String getProductNationCd() {
		return productNationCd;
	}
	public void setProductNationCd(String productNationCd) {
		this.productNationCd = productNationCd;
	}
	public String getFedayMallProdDivnCd() {
		return fedayMallProdDivnCd;
	}
	public void setFedayMallProdDivnCd(String fedayMallProdDivnCd) {
		this.fedayMallProdDivnCd = fedayMallProdDivnCd;
	}
	public String getMajorCd() {
		return majorCd;
	}
	public void setMajorCd(String majorCd) {
		this.majorCd = majorCd;
	}
	public String getMinorCd() {
		return minorCd;
	}
	public void setMinorCd(String minorCd) {
		this.minorCd = minorCd;
	}
	public String getCdNm() {
		return cdNm;
	}
	public void setCdNm(String cdNm) {
		this.cdNm = cdNm;
	}
	public String getLinkTp() {
		return linkTp;
	}
	public void setLinkTp(String linkTp) {
		this.linkTp = linkTp;
	}
	public String getLinkSts() {
		return linkSts;
	}
	public void setLinkSts(String linkSts) {
		this.linkSts = linkSts;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getSiteProdCd() {
		return siteProdCd;
	}
	public void setSiteProdCd(String siteProdCd) {
		this.siteProdCd = siteProdCd;
	}
	public String getSiteNm() {
		return siteNm;
	}
	public void setSiteNm(String siteNm) {
		this.siteNm = siteNm;
	}
	public String getFedayMallProdDivnNm() {
		return fedayMallProdDivnNm;
	}
	public void setFedayMallProdDivnNm(String fedayMallProdDivnNm) {
		this.fedayMallProdDivnNm = fedayMallProdDivnNm;
	}
	public String getCatCd() {
		return catCd;
	}
	public void setCatCd(String catCd) {
		this.catCd = catCd;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getSnglBkYn() {
		return SnglBkYn;
	}
	public void setSnglBkYn(String snglBkYn) {
		SnglBkYn = snglBkYn;
	}
	
	public String getProdPatenDivnCd() {
		return prodPatenDivnCd;
	}
	public void setProdPatenDivnCd(String prodPatenDivnCd) {
		this.prodPatenDivnCd = prodPatenDivnCd;
	}
	public String getProdStandardNm() {
		return prodStandardNm;
	}
	public void setProdStandardNm(String prodStandardNm) {
		this.prodStandardNm = prodStandardNm;
	}
	public String getDpUnitCd() {
		return dpUnitCd;
	}
	public void setDpUnitCd(String dpUnitCd) {
		this.dpUnitCd = dpUnitCd;
	}
	public String getRepProdCd() {
		return repProdCd;
	}
	public void setRepProdCd(String repProdCd) {
		this.repProdCd = repProdCd;
	}
	public String getTaxatDivnCd() {
		return taxatDivnCd;
	}
	public void setTaxatDivnCd(String taxatDivnCd) {
		this.taxatDivnCd = taxatDivnCd;
	}
	public String getProdTypeDivnCd() {
		return prodTypeDivnCd;
	}
	public void setProdTypeDivnCd(String prodTypeDivnCd) {
		this.prodTypeDivnCd = prodTypeDivnCd;
	}
	public String getCtrTypeDivnCd() {
		return ctrTypeDivnCd;
	}
	public void setCtrTypeDivnCd(String ctrTypeDivnCd) {
		this.ctrTypeDivnCd = ctrTypeDivnCd;
	}
	public String getPrcIssueDivnCd() {
		return prcIssueDivnCd;
	}
	public void setPrcIssueDivnCd(String prcIssueDivnCd) {
		this.prcIssueDivnCd = prcIssueDivnCd;
	}
	public String getProdDesc() {
		return prodDesc;
	}
	public void setProdDesc(String prodDesc) {
		this.prodDesc = prodDesc;
	}
	public String getQtyWegtDivnCd() {
		return qtyWegtDivnCd;
	}
	public void setQtyWegtDivnCd(String qtyWegtDivnCd) {
		this.qtyWegtDivnCd = qtyWegtDivnCd;
	}
	public String getMaxKeepDdCnt() {
		return maxKeepDdCnt;
	}
	public void setMaxKeepDdCnt(String maxKeepDdCnt) {
		this.maxKeepDdCnt = maxKeepDdCnt;
	}
	public String getAuSellYn() {
		return auSellYn;
	}
	public void setAuSellYn(String auSellYn) {
		this.auSellYn = auSellYn;
	}
	public String getProfitRate() {
		return profitRate;
	}
	public void setProfitRate(String profitRate) {
		this.profitRate = profitRate;
	}
	public String getProdDivnCd() {
		return ProdDivnCd;
	}
	public void setProdDivnCd(String prodDivnCd) {
		ProdDivnCd = prodDivnCd;
	}
	
	public String getDpTotQty() {
		return dpTotQty;
	}
	public void setDpTotQty(String dpTotQty) {
		this.dpTotQty = dpTotQty;
	}
	public String getDpBaseQty() {
		return dpBaseQty;
	}
	public void setDpBaseQty(String dpBaseQty) {
		this.dpBaseQty = dpBaseQty;
	}
	public String getDispReason() {
		return dispReason;
	}
	public void setDispReason(String dispReason) {
		this.dispReason = dispReason;
	}
	public String getProdUrl() {
		return prodUrl;
	}
	public void setProdUrl(String prodUrl) {
		this.prodUrl = prodUrl;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public static void setSerialversionuid(long serialversionuid) {
		serialVersionUID = serialversionuid;
	}
	public String getModelNm() {
		return modelNm;
	}
	public void setModelNm(String modelNm) {
		this.modelNm = modelNm;
	}
	public String getEcoYn() {
		return ecoYn;
	}
	public void setEcoYn(String ecoYn) {
		this.ecoYn = ecoYn;
	}
	public String getEcoNm() {
		return ecoNm;
	}
	public void setEcoNm(String ecoNm) {
		this.ecoNm = ecoNm;
	}
	public String getDlvGa() {
		return dlvGa;
	}
	public void setDlvGa(String dlvGa) {
		this.dlvGa = dlvGa;
	}
	public String getDlvDt() {
		return dlvDt;
	}
	public void setDlvDt(String dlvDt) {
		this.dlvDt = dlvDt;
	}
	public String getInsCo() {
		return insCo;
	}
	public void setInsCo(String insCo) {
		this.insCo = insCo;
	}
	public String getOnlineProdTypeCd() {
		return onlineProdTypeCd;
	}
	public void setOnlineProdTypeCd(String onlineProdTypeCd) {
		this.onlineProdTypeCd = onlineProdTypeCd;
	}
	public String getOptnLoadSetQty() {
		return optnLoadSetQty;
	}
	public void setOptnLoadSetQty(String optnLoadSetQty) {
		this.optnLoadSetQty = optnLoadSetQty;
	}
	public String getRservOrdPsbtStartDy() {
		return rservOrdPsbtStartDy;
	}
	public void setRservOrdPsbtStartDy(String rservOrdPsbtStartDy) {
		this.rservOrdPsbtStartDy = rservOrdPsbtStartDy;
	}
	public String getRservOrdPsbtEndDy() {
		return rservOrdPsbtEndDy;
	}
	public void setRservOrdPsbtEndDy(String rservOrdPsbtEndDy) {
		this.rservOrdPsbtEndDy = rservOrdPsbtEndDy;
	}
	public String getRservProdPickIdctDy() {
		return rservProdPickIdctDy;
	}
	public void setRservProdPickIdctDy(String rservProdPickIdctDy) {
		this.rservProdPickIdctDy = rservProdPickIdctDy;
	}
	public String getHopeDeliPsbtDd() {
		return hopeDeliPsbtDd;
	}
	public void setHopeDeliPsbtDd(String hopeDeliPsbtDd) {
		this.hopeDeliPsbtDd = hopeDeliPsbtDd;
	}
	public String getOptnLoadContent() {
		return optnLoadContent;
	}
	public void setOptnLoadContent(String optnLoadContent) {
		this.optnLoadContent = optnLoadContent;
	}
	public String getProdOptionDesc() {
		return prodOptionDesc;
	}
	public void setProdOptionDesc(String prodOptionDesc) {
		this.prodOptionDesc = prodOptionDesc;
	}
}
