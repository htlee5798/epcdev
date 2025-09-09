package com.lottemart.epc.edi.product.model;

import java.io.Serializable;



/**
 * @Class Name : OfficialOrder
 * @Description : 신상품 등록에 사용되는상품 부가정보  VO 클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 19. 오후 2:32:38 kks
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */

public class OfficialOrder implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4925106199404425483L;





	public OfficialOrder () {}

	//ISP 상세 설명
	private String ispDetailDescription;
	
	//팀분류
	private String teamCode;
	
	//대분류
	private String lGroupCode;
	
	//세분류
	private String subGroupCode;
	
	//발주입수
	private Integer publishIncrementQuantity;
	
	//발주단위
	private String publishedUnitCode;
	
	//권장금액 구분 코드
	private String recomendedAmountDivnCode;
	
	//신규상품 초도구분 : 미적용(0), 적용(1)
	private String newProductFirstPublishedDivnCode;
	
	//무발주 매입 구분 : 미적용(0), 적용(1)
	private String noPublishedBuyPossibleDivnCode;
	
	//계절구분
	private String seasonDivnCode;
	
	//도난방지TAG사용
	private String protectTagDivnCode;

	//도난방지 TAG유형구분
	private String protectTagTypeCode;
	
	//신상품 등록구분 코드(KOR, EDI, KOR_F)
	private String newProductGeneratedDivnCode;
	
	//거래유형
	private String tradeTypeDivnCode;
	
	//바코드 검증서 유무
	private String barcodeVerfiyDocTypeFlag;
	
	//혼재여부 : 단일상품(0), 혼재상품(1)
	private String mixYn;

	//입고가능일
	private String stageSubmittedDate;
	
	//출고 가능일
	private String pickedDate;

	//온도구분
	private String temperatureDivnCode;
	
	//유통일관리여부
	private String flowDateManageCode;
	
	//수량(0)/ 중량(1) 구분
	private String quantityWeightDivnCode;
	
	//최대보관일수
	private Integer maxKeepDayCount;
	
	//브랜드, 메이커 관련 코드/이름 필드
	private String brandCode;
	private String brandName;
	private String makerCode;
	private String makerName;
	
	//최소 주문 가능량
	private String minimumOrderQuantity;
	
	//최대 주문 가능량
	private String maximumOrderQuantity;
	
	//MD전송구분코드
	private String mdSendDivnCode;
	
	//전수검사여부 : 미대상(0), 대상(1)
	private String totalInspectYn;
	
	//센터 구분코드
	private String centerDivnCode;

	//수량, 중량 구분
	private String countWeightDivnCode;
	
	//신상품장려금구분
	private String newProdPromoFg;
	
	//성과초과장려금구분
	private String overPromoFg;
	
	//모델명
	private String modelName;

	//친환경 인증 여부 : N(0), Y(1)
	private String ecoYn;
	
	//친환경 인증 분류명
	private String ecoNm;
	
	//차등배송비 여부 : N(0), Y(1)
	private String dlvgaYn;
	
	//차등배송비 내용 : 유기농(1), 유기농아님(2)
	private String dlvDt;
	
	//별도 설치비 유무 : N(0), Y(1)
	private String inscoYn;
	
	
	
	public String getCountWeightDivnCode() {
		return countWeightDivnCode;
	}
	public void setCountWeightDivnCode(String countWeightDivnCode) {
		this.countWeightDivnCode = countWeightDivnCode;
	}
	public String getCenterDivnCode() {
		return centerDivnCode;
	}
	public void setCenterDivnCode(String centerDivnCode) {
		this.centerDivnCode = centerDivnCode;
	}
	public String getIspDetailDescription() {
		return ispDetailDescription;
	}
	public void setIspDetailDescription(String ispDetailDescription) {
		this.ispDetailDescription = ispDetailDescription;
	}
	public String getTeamCode() {
		return teamCode;
	}
	public void setTeamCode(String teamCode) {
		this.teamCode = teamCode;
	}
	public String getlGroupCode() {
		return lGroupCode;
	}
	public void setlGroupCode(String lGroupCode) {
		this.lGroupCode = lGroupCode;
	}
	public String getSubGroupCode() {
		return subGroupCode;
	}
	public void setSubGroupCode(String subGroupCode) {
		this.subGroupCode = subGroupCode;
	}
	public Integer getPublishIncrementQuantity() {
		return publishIncrementQuantity;
	}
	public void setPublishIncrementQuantity(Integer publishIncrementQuantity) {
		this.publishIncrementQuantity = publishIncrementQuantity;
	}
	
	public String getPublishedUnitCode() {
		return publishedUnitCode;
	}
	public void setPublishedUnitCode(String publishedUnitCode) {
		this.publishedUnitCode = publishedUnitCode;
	}
	public String getRecomendedAmountDivnCode() {
		return recomendedAmountDivnCode;
	}
	public void setRecomendedAmountDivnCode(String recomendedAmountDivnCode) {
		this.recomendedAmountDivnCode = recomendedAmountDivnCode;
	}
	public String getNewProductFirstPublishedDivnCode() {
		return newProductFirstPublishedDivnCode;
	}
	public void setNewProductFirstPublishedDivnCode(
			String newProductFirstPublishedDivnCode) {
		this.newProductFirstPublishedDivnCode = newProductFirstPublishedDivnCode;
	}
	public String getNoPublishedBuyPossibleDivnCode() {
		return noPublishedBuyPossibleDivnCode;
	}
	public void setNoPublishedBuyPossibleDivnCode(
			String noPublishedBuyPossibleDivnCode) {
		this.noPublishedBuyPossibleDivnCode = noPublishedBuyPossibleDivnCode;
	}
	public String getSeasonDivnCode() {
		return seasonDivnCode;
	}
	public void setSeasonDivnCode(String seasonDivnCode) {
		this.seasonDivnCode = seasonDivnCode;
	}
	public String getProtectTagDivnCode() {
		return protectTagDivnCode;
	}
	public void setProtectTagDivnCode(String protectTagDivnCode) {
		this.protectTagDivnCode = protectTagDivnCode;
	}
	public String getProtectTagTypeCode() {
		return protectTagTypeCode;
	}
	public void setProtectTagTypeCode(String protectTagTypeCode) {
		this.protectTagTypeCode = protectTagTypeCode;
	}
	public String getNewProductGeneratedDivnCode() {
		return newProductGeneratedDivnCode;
	}
	public void setNewProductGeneratedDivnCode(String newProductGeneratedDivnCode) {
		this.newProductGeneratedDivnCode = newProductGeneratedDivnCode;
	}
	public String getTradeTypeDivnCode() {
		return tradeTypeDivnCode;
	}
	public void setTradeTypeDivnCode(String tradeTypeDivnCode) {
		this.tradeTypeDivnCode = tradeTypeDivnCode;
	}
	public String getBarcodeVerfiyDocTypeFlag() {
		return barcodeVerfiyDocTypeFlag;
	}
	public void setBarcodeVerfiyDocTypeFlag(String barcodeVerfiyDocTypeFlag) {
		this.barcodeVerfiyDocTypeFlag = barcodeVerfiyDocTypeFlag;
	}
	public String getMixYn() {
		return mixYn;
	}
	public void setMixYn(String mixYn) {
		this.mixYn = mixYn;
	}
	public String getStageSubmittedDate() {
		return stageSubmittedDate;
	}
	public void setStageSubmittedDate(String stageSubmittedDate) {
		this.stageSubmittedDate = stageSubmittedDate;
	}
	public String getPickedDate() {
		return pickedDate;
	}
	public void setPickedDate(String pickedDate) {
		this.pickedDate = pickedDate;
	}
	public String getTemperatureDivnCode() {
		return temperatureDivnCode;
	}
	public void setTemperatureDivnCode(String temperatureDivnCode) {
		this.temperatureDivnCode = temperatureDivnCode;
	}
	public String getFlowDateManageCode() {
		return flowDateManageCode;
	}
	public void setFlowDateManageCode(String flowDateManageCode) {
		this.flowDateManageCode = flowDateManageCode;
	}
	public String getQuantityWeightDivnCode() {
		return quantityWeightDivnCode;
	}
	public void setQuantityWeightDivnCode(String quantityWeightDivnCode) {
		this.quantityWeightDivnCode = quantityWeightDivnCode;
	}
	public Integer getMaxKeepDayCount() {
		return maxKeepDayCount;
	}
	public void setMaxKeepDayCount(Integer maxKeepDayCount) {
		this.maxKeepDayCount = maxKeepDayCount;
	}
	public String getBrandCode() {
		return brandCode;
	}
	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String getMakerCode() {
		return makerCode;
	}
	public void setMakerCode(String makerCode) {
		this.makerCode = makerCode;
	}
	public String getMakerName() {
		return makerName;
	}
	public void setMakerName(String makerName) {
		this.makerName = makerName;
	}
	public String getMinimumOrderQuantity() {
		return minimumOrderQuantity;
	}
	public void setMinimumOrderQuantity(String minimumOrderQuantity) {
		this.minimumOrderQuantity = minimumOrderQuantity;
	}
	public String getMaximumOrderQuantity() {
		return maximumOrderQuantity;
	}
	public void setMaximumOrderQuantity(String maximumOrderQuantity) {
		this.maximumOrderQuantity = maximumOrderQuantity;
	}
	public String getMdSendDivnCode() {
		return mdSendDivnCode;
	}
	public void setMdSendDivnCode(String mdSendDivnCode) {
		this.mdSendDivnCode = mdSendDivnCode;
	}
	public String getTotalInspectYn() {
		return totalInspectYn;
	}
	public void setTotalInspectYn(String totalInspectYn) {
		this.totalInspectYn = totalInspectYn;
	}
	
	public String getNewProdPromoFg() {
		return newProdPromoFg;
	}
	public void setNewProdPromoFg(String newProdPromoFg) {
		this.newProdPromoFg = newProdPromoFg;
	}

	public String getOverPromoFg() {
		return overPromoFg;
	}
	public void setOverPromoFg(String overPromoFg) {
		this.overPromoFg = overPromoFg;
	}
	
	
	public String getmodelName() {
		return modelName;
	}
	public void setmodelName(String modelName) {
		this.modelName = modelName;
	}
	public String getecoYn() {
		return ecoYn;
	}
	public void setecoYn(String ecoYn) {
		this.ecoYn = ecoYn;
	}
	public String getecoNm() {
		return ecoNm;
	}
	public void setecoNm(String ecoNm) {
		this.ecoNm = ecoNm;
	}
	public String getdlvgaYn() {
		return dlvgaYn;
	}
	public void setdlvgaYn(String dlvgaYn) {
		this.dlvgaYn = dlvgaYn;
	}
	public String getdlvDt() {
		return dlvDt;
	}
	public void setdlvDt(String dlvDt) {
		this.dlvDt = dlvDt;
	}
	public String getinscoYn() {
		return inscoYn;
	}
	public void setinscoYn(String inscoYn) {
		this.inscoYn = inscoYn;
	}


	
	
}
