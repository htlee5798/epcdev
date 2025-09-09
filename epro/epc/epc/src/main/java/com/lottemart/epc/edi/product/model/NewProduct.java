package com.lottemart.epc.edi.product.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.lottemart.epc.edi.comm.model.Constants;


/**
 * @Class Name : NewProduct
 * @Description : 신상품 등록  NewProduct 클래스
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

public class NewProduct implements Serializable {

	private static final long serialVersionUID = -6918711902569942487L;

	public NewProduct() {
	}

	private OfficialOrder officialOrder;

	private String newProductCode; // 가등록 상품코드(primary key 값)

	private String productName; // 관리용 상품명

	private String productShortName; // 쇼카드 상품명

	private String onOffDivnCode; // 온/오프(0), 온라인전용(1), 소셜(2) 상품구분

	private String entpCode; // 협력업체 코드

	private String sellCode; // 판매(88)코드

	private String productDivnCode; // 상품속성 : 규격(1), 패션(5)

	private String productStandardName; // 상품규격명

	private Integer displayTotalQuantity = 0; // 표시 총량

	private Integer displayBaseQuantity = 0; // 표시기준수량

	private String normalProductSalePrice; // 정상매가

	private String normalProductCost; // 정상원가(var 제외)

	private String vicnormalProductSalePrice; // VIC 정상매가

	private String vicnormalProductCost;// VIC 정상원가(var 제외)

	private String sellPrice; // 판매가

	private String displayUnitCode; // 표시단위

	private String eventSellPrice; // 행사판매가

	private String taxatDivCode; // 면과세구분 : 면세(1) , 과세(2), 영세(3)

	private String productTypeDivnCode; // 상품유형(NB, PB,기타....)

	private String centerTypeDivnCode; // 센터유형 코드

	private String priceIssueDivnCode; // 가격발급구분코드

	private String productHorizontalLength; // 상품 사이즈 : 가로

	private String productVerticalLength; // 상품 사이즈 : 세로

	private String productHeight; // 상품 사이즈 : 높이

	private String productDescription;// 상품 상세

	private String productDay; // 발행일

	private String productImageId; // 오프라인 pog image id

	private String productCountryCode; // 생산국가코드

	private Date regDate; // 등록일

	private String regId; // 등록자

	private Date modDate; // 수정일

	private String modId; // 수정자

	private String matCd; // VIC 대상여부

	private String profitRate; // 이익율

	private String vicprofitRate; // VIC 이익율

	// deprecated field. 2011.12.05
	private Integer saleAmount = 0;
	private String productDivnName;

	private Integer orderIpsu = 0; // 발주 입수 : NEW_PROD_EDI@DL_MD_MARTNIS.ORD_IPSU 칼럼에 매핑됨.

	private String onlineProductCode; // 온라인 대표 상품코드

	private String onlineProductName; // 온라인 상품 명

	private String onlineDisplayCategoryCode; // 온라인 전시 카테고리 값.

	private String onlineDisplayCategoryName; // 온라인 전시 카테고리 명

	private String socialProductDeliveryCode; // 소셜 상품 배송구분 코드

	private String storeWarehouseDay; // 입고가능일

	private String pickupWarehouseDay; // 출고가능일

	private String l4CodeName; // 세분류 명

	private String promoAmtStr; // MD장려금구분

	private String colorSizeState;

	private String nfomlVariationDesc;

	private String totalInspectYn;

	private String venCd;

	private Integer imgNcnt = 0; // 온라인이미지카운트

	private String lGrpCd; // 대분류코드

	private String subgrpCd; // 세분류코드

	private String imgNcntYn; // 대분류코드

	private String wUseFlag; // 물류바코드 vic사용여부

	private String onlineRepProdCd; // 온라인대표상품코드

	// 전자상거래 데이터
	private String infoGrpCd;

	private String prodAddMasterCd;
	private String prodAddCd;
	private String prodAddNm = "";
	private String prodcommerce = "";

	private String md_vali_sellCode= ""; // 온라인전용 md판매코드검증용

	private String newProdPromoFg; // 신상품장려금구분

	private String newProdStDy; // 신상품출시일자

	private String overPromoFg; // 성과초과장려금구분

	private String infoGrpCd2; // 전자상거래 데이터

	// KC 인증마크
	private String prodCertMasterCd;
	private String prodCertCd;
	private String prodCertNm= "";

	private String modelName; // 모델명

	private String ecoYn; // 친환경 인증 여부

	private String ecoNm; //친환경 인증 분류명

	private String dlvgaYn; //차등배송비 여부

	private String dlvDt; //차등배송비 내용

	private String inscoYn; //별도 설치비 유무

	private String brandCode;
	private String brandName;
	private String makerCode;
	private String makerName;

	private String sesnYearCd; // 계절구분(년도)

	private String sesnDivnCd; // 계절구분(계절)

	private boolean exprProdYn; // 체험형 상품 여부 : N(0), Y(1)

	private String sellerRecomm; // 판매자 추천평

	private String regType; // 등록타입(온라인신상품등록)

	private String adlYn; // 성인상품여부

	private String themaYn; // 테마사용 여부

	private String dealViewCd; // 테마 보기방식
	
	private String videoUrl; // 동영상
	
	private String scDpYn; //검색노출여부


	public String getRegType() {
		return regType;
	}

	public void setRegType(String regType) {
		this.regType = regType;
	}

	public String getAdlYn() {
		return adlYn;
	}

	public void setAdlYn(String adlYn) {
		this.adlYn = adlYn;
	}

	public String getThemaYn() {
		return themaYn;
	}

	public void setThemaYn(String themaYn) {
		this.themaYn = themaYn;
	}

	public String getDealViewCd() {
		return dealViewCd;
	}

	public void setDealViewCd(String dealViewCd) {
		this.dealViewCd = dealViewCd;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}
	
	public String getScDpYn() {
		return scDpYn;
	}
	
	public void setScDpYn(String scDpYn) {
		this.scDpYn = scDpYn;
	}

	public boolean getExprProdYn() {
		return exprProdYn;
	}

	public void setExprProdYn(boolean exprProdYn) {
		this.exprProdYn = exprProdYn;
	}

	public String getSellerRecomm() {
		return sellerRecomm;
	}

	public void setSellerRecomm(String sellerRecomm) {
		this.sellerRecomm = sellerRecomm;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
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

	public String getDlvgaYn() {
		return dlvgaYn;
	}

	public void setDlvgaYn(String dlvgaYn) {
		this.dlvgaYn = dlvgaYn;
	}

	public String getDlvDt() {
		return dlvDt;
	}

	public void setDlvDt(String dlvDt) {
		this.dlvDt = dlvDt;
	}

	public String getInscoYn() {
		return inscoYn;
	}

	public void setInscoYn(String inscoYn) {
		this.inscoYn = inscoYn;
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

	public String getInfoGrpCd() {
		return infoGrpCd;
	}

	public void setInfoGrpCd(String infoGrpCd) {
		this.infoGrpCd = infoGrpCd;
	}

	public String getVenCd() {
		return venCd;
	}

	public void setVenCd(String venCd) {
		this.venCd = venCd;
	}

	public String getNfomlVariationDesc() {
		return nfomlVariationDesc;
	}

	public void setNfomlVariationDesc(String nfomlVariationDesc) {
		this.nfomlVariationDesc = nfomlVariationDesc;
	}

	public String getColorSizeState() {
		return colorSizeState;
	}

	public void setColorSizeState(String colorSizeState) {
		this.colorSizeState = colorSizeState;
	}

	public String getPromoAmtStr() {
		return promoAmtStr;
	}

	public void setPromoAmtStr(String promoAmtStr) {
		this.promoAmtStr = promoAmtStr;
	}

	public String getL4CodeName() {
		return l4CodeName;
	}

	public void setL4CodeName(String l4CodeName) {
		this.l4CodeName = l4CodeName;
	}

	public String getStoreWarehouseDay() {
		return storeWarehouseDay;
	}

	public void setStoreWarehouseDay(String storeWarehouseDay) {
		this.storeWarehouseDay = storeWarehouseDay;
	}

	public String getPickupWarehouseDay() {
		return pickupWarehouseDay;
	}

	public void setPickupWarehouseDay(String pickupWarehouseDay) {
		this.pickupWarehouseDay = pickupWarehouseDay;
	}

	public String getSocialProductDeliveryCode() {
		return socialProductDeliveryCode;
	}

	public void setSocialProductDeliveryCode(String socialProductDeliveryCode) {
		this.socialProductDeliveryCode = socialProductDeliveryCode;
	}

	public String getOnlineDisplayCategoryCode() {
		return onlineDisplayCategoryCode;
	}

	public void setOnlineDisplayCategoryCode(String onlineDisplayCategoryCode) {
		this.onlineDisplayCategoryCode = onlineDisplayCategoryCode;
	}

	public String getOnlineDisplayCategoryName() {
		return onlineDisplayCategoryName;
	}

	public void setOnlineDisplayCategoryName(String onlineDisplayCategoryName) {
		this.onlineDisplayCategoryName = onlineDisplayCategoryName;
	}

	public String getSubgrpCd() {
		return subgrpCd;
	}

	public void setSubgrpCd(String subgrpCd) {
		this.subgrpCd = subgrpCd;
	}

	private String tradeTypeCode; // 거래형태

	private String promotionAmountFlag; // 권장금액구분 코드값.

	// 코리안넷으로 상품등록시 사용됨. 시작
	// 박스체적
	private String width; // 가로
	private String length; // 세로
	private String height; // 높이
	private String wg; // 총중량

	private String conveyFg; //소터에러사유

	private String wUseFg; //VIC 물류 사용여부

	private String mixProdFg; //혼재여부

	private String sorterFg; //소터 구분

	private String crsdkFg; //클로즈덕 구분

	private String useFg; //사용여부

	private String logiBcd; //물류바코드
	
	private String logiBoxIpsu; //물류박스 입수

	private String innerIpsu; // 팔레트(가로박스수)
	private String pltLayerQty; // 팔레트(세로박스수)
	private String pltHeightQty; // 팔레트 (높이박스수)
	private String regDt; // 수정일자

	private String wInnerIpsu; // VIC 박스수

	//################ 2016.04.12 2차 개발 추가건 ####################//
	private String teamCd; // 팀코드
	private String l1Cd; // 대분류
	private String l2Cd; // 중분류
	private String l3Cd; // 소분류
	private String entpInProdCd; // 협력사 내부 상품코드
	private String psbtStartDy; // 예약주문가능 시작일
	private String psbtEndDy; // 예약주문가능 종료일
	private String pickIdctDy; // 예약상품 출고지시일
	private String onlineProdTypeCd; // 상품유형
	private String deliDueDate; // 배송예정일
	private String hopeDeliPsbtDd; // 희망배송가능일
	private String setQty; // 골라담기세트수량
	private String optnLoadContent; // 골라담기옵션내용
	private String staffSellYn; // 임직원 전용몰 판매 가능 여부
	private String staffDcAmt; // 임직원 할인가
	private String prodPrcMgrYn; // 단품정보 옵션상품가격관리여부
	private String norProdSaleCurr; // 정상매가 단위
	private String sizeUnit; // 상품사이즈단위
	private String ctpdYn; // 구성품여부
	private String dealRepProdYn; // 딜대표상품여부
	private String sellStartDy; // 판매시작일
	private String sellEndDy; // 판매종료일
	private String dealProdDivnCode; // 딜상품구분
	private String prodOptionDesc; // 상품옵션설명 (설치상품,주문제작상품)


	public String getwUseFg() {
		return wUseFg;
	}

	public void setwUseFg(String wUseFg) {
		this.wUseFg = wUseFg;
	}

	public String getwInnerIpsu() {
		return wInnerIpsu;
	}

	public void setwInnerIpsu(String wInnerIpsu) {
		this.wInnerIpsu = wInnerIpsu;
	}

	private String barCodeFlag; // 바코드 검증서
	private String boxFlag; // 물류바코드검증서
	private String totalBoxCount; // 전체 박스수

	public String getTotalBoxCount() {
		return totalBoxCount;
	}

	public void setTotalBoxCount(String totalBoxCount) {
		this.totalBoxCount = totalBoxCount;
	}

	public String getBarCodeFlag() {
		return barCodeFlag;
	}

	public void setBarCodeFlag(String barCodeFlag) {
		this.barCodeFlag = barCodeFlag;
	}

	public String getBoxFlag() {
		return boxFlag;
	}

	public void setBoxFlag(String boxFlag) {
		this.boxFlag = boxFlag;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getWg() {
		return wg;
	}

	public void setWg(String wg) {
		this.wg = wg;
	}

	public String getConveyFg() {
		return conveyFg;
	}

	public void setConveyFg(String conveyFg) {
		this.conveyFg = conveyFg;
	}

	public String getMixProdFg() {
		return mixProdFg;
	}

	public void setMixProdFg(String mixProdFg) {
		this.mixProdFg = mixProdFg;
	}

	public String getSorterFg() {
		return sorterFg;
	}

	public void setSorterFg(String sorterFg) {
		this.sorterFg = sorterFg;
	}

	public String getCrsdkFg() {
		return crsdkFg;
	}

	public void setCrsdkFg(String crsdkFg) {
		this.crsdkFg = crsdkFg;
	}

	public String getUseFg() {
		return useFg;
	}

	public void setUseFg(String useFg) {
		this.useFg = useFg;
	}

	public String getLogiBcd() {
		return logiBcd;
	}

	public void setLogiBcd(String logiBcd) {
		this.logiBcd = logiBcd;
	}

	public String getLogiBoxIpsu() {
		return logiBoxIpsu;
	}

	public void setLogiBoxIpsu(String logiBoxIpsu) {
		this.logiBoxIpsu = logiBoxIpsu;
	}

	public String getInnerIpsu() {
		return innerIpsu;
	}

	public void setInnerIpsu(String innerIpsu) {
		this.innerIpsu = innerIpsu;
	}

	public String getPltLayerQty() {
		return pltLayerQty;
	}

	public void setPltLayerQty(String pltLayerQty) {
		this.pltLayerQty = pltLayerQty;
	}

	public String getPltHeightQty() {
		return pltHeightQty;
	}

	public void setPltHeightQty(String pltHeightQty) {
		this.pltHeightQty = pltHeightQty;
	}

	public String getRegDt() {
		return regDt;
	}

	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}
	// 코리안넷으로 상품등록시 사용됨. 끝

	public String getPromotionAmountFlag() {
		return promotionAmountFlag;
	}

	public void setPromotionAmountFlag(String promotionAmountFlag) {
		this.promotionAmountFlag = promotionAmountFlag;
	}

	public String getTradeTypeCode() {
		return tradeTypeCode;
	}

	public void setTradeTypeCode(String tradeTypeCode) {
		this.tradeTypeCode = tradeTypeCode;
	}

	public String getDisplayProductName() {
		if (Constants.ONLINE_OFFLINE_PRODUCT_CD.equals(getOnOffDivnCode())) {
			return this.productName;
		} else {
			return this.onlineProductName;
		}
	}

	public String getOnlineProductCode() {
		return onlineProductCode;
	}

	public void setOnlineProductCode(String onlineProductCode) {
		this.onlineProductCode = onlineProductCode;
	}

	public String getOnlineProductName() {
		return onlineProductName;
	}

	public void setOnlineProductName(String onlineProductName) {
		this.onlineProductName = onlineProductName;
	}
	//패션 상품 입력시 사용자가 입력한 필드값
	//실제 db에 저장되진  않고, 값 전달 파라미터 역할만 함.
	private String[] colorCode;
	private String[] sizeCategory;
	private String[] size1;
	private String[] size2;
	private String[] size3;
	private String[] size4;
	private String[] size5;

	private String[] sellCode1;
	private String[] sellCode2;
	private String[] sellCode3;
	private String[] sellCode4;
	private String[] sellCode5;

	private String[] sellFlag;


	private String confirmFlag;
	private String adjustFlag;
	private String productStand;
	private String programId;
	private String imageName;
	private String imageConfirmFlag;
	private String logiConfirmFlag;


	private String colorCodeCd;
	private String sizeCategoryCodeCd;
	private String sizeCodeCd;


	private String colorName;
	private String sizeName;
	private String colorCount;


	public String getColorCount() {
		return colorCount;
	}

	public void setColorCount(String colorCount) {
		this.colorCount = colorCount;
	}

	public String getColorName() {
		return colorName;
	}

	public void setColorName(String colorName) {
		this.colorName = colorName;
	}

	public String getSizeName() {
		return sizeName;
	}

	public void setSizeName(String sizeName) {
		this.sizeName = sizeName;
	}

	public String getColorCodeCd() {
		return colorCodeCd;
	}

	public void setColorCodeCd(String colorCodeCd) {
		this.colorCodeCd = colorCodeCd;
	}

	public String getSizeCategoryCodeCd() {
		return sizeCategoryCodeCd;
	}

	public void setSizeCategoryCodeCd(String sizeCategoryCodeCd) {
		this.sizeCategoryCodeCd = sizeCategoryCodeCd;
	}

	public String getSizeCodeCd() {
		return sizeCodeCd;
	}

	public void setSizeCodeCd(String sizeCodeCd) {
		this.sizeCodeCd = sizeCodeCd;
	}

	public String getConfirmFlag() {
		return confirmFlag;
	}

	public void setConfirmFlag(String confirmFlag) {
		this.confirmFlag = confirmFlag;
	}

	public String getAdjustFlag() {
		return adjustFlag;
	}

	public void setAdjustFlag(String adjustFlag) {
		this.adjustFlag = adjustFlag;
	}

	public String getProductStand() {
		return productStand;
	}

	public void setProductStand(String productStand) {
		this.productStand = productStand;
	}

	public String getProgramId() {
		return programId;
	}

	public void setProgramId(String programId) {
		this.programId = programId;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getImageConfirmFlag() {
		return imageConfirmFlag;
	}

	public void setImageConfirmFlag(String imageConfirmFlag) {
		this.imageConfirmFlag = imageConfirmFlag;
	}

	public String getLogiConfirmFlag() {
		return logiConfirmFlag;
	}

	public void setLogiConfirmFlag(String logiConfirmFlag) {
		this.logiConfirmFlag = logiConfirmFlag;
	}

	public Integer getOrderIpsu() {
		return orderIpsu;
	}

	public void setOrderIpsu(Integer orderIpsu) {
		this.orderIpsu = orderIpsu;
	}

	public String getProductDivnName() {
		return productDivnName;
	}

	public void setProductDivnName(String productDivnName) {
		this.productDivnName = productDivnName;
	}

	private ArrayList<ColorSize> colorSizeList = new ArrayList<ColorSize>();

	private ArrayList<EcomAddInfo> ecomAddInfoList = new ArrayList<EcomAddInfo>();

	public void makeColorSizeObject() {

		int arrayLength = getColorCode().length;
		for (int i = 0; i < arrayLength; i++) {
			int seqValue = i * 5;
			ColorSize tmpColorSize1 = new ColorSize();
			tmpColorSize1.setNewProductCode(getNewProductCode());

			if (getSizeCategory().length == 0)
				continue;

			tmpColorSize1.setSizeCategoryCode(getSizeCategory()[0]);
			tmpColorSize1.setProductName(getProductName());
			tmpColorSize1.setEnterpriseCode(getEntpCode());
			tmpColorSize1.setColorCode(getColorCode()[i]);

			if (getSize1().length > 0 && StringUtils.isNotEmpty(getSize1()[i])) {

				String sellCode1 = "";
				if (getSellCode1().length > 0) {
					sellCode1 = getSellCode1()[i];
				}
				int tmpSeq = seqValue + 1;
				String seqString = StringUtils.leftPad(String.valueOf(tmpSeq), 3, '0');
				tmpColorSize1.setItemCode(seqString);
				tmpColorSize1.setSizeCode(getSize1()[i]);
				tmpColorSize1.setSellCode(sellCode1);
				colorSizeList.add(tmpColorSize1);

			}

			if (getSize2().length > 0 && StringUtils.isNotEmpty(getSize2()[i])) {
				String sellCode2 = "";
				if (getSellCode2().length > 0) {
					sellCode2 = getSellCode2()[i];
				}
				int tmpSeq = seqValue + 2;
				String seqString = StringUtils.leftPad(String.valueOf(tmpSeq), 3, '0');
				ColorSize tmpColorSize2 = tmpColorSize1.copyField(seqString, getSize2()[i], sellCode2);
				colorSizeList.add(tmpColorSize2);

			}
			if (getSize3().length > 0 && StringUtils.isNotEmpty(getSize3()[i])) {

				String sellCode3 = "";
				if (getSellCode3().length > 0) {
					sellCode3 = getSellCode3()[i];
				}

				int tmpSeq = seqValue + 3;
				String seqString = StringUtils.leftPad(String.valueOf(tmpSeq), 3, '0');
				ColorSize tmpColorSize3 = tmpColorSize1.copyField(seqString, getSize3()[i], sellCode3);
				colorSizeList.add(tmpColorSize3);

			}
			if (getSize4().length > 0 && StringUtils.isNotEmpty(getSize4()[i])) {

				String sellCode4 = "";
				if (getSellCode4().length > 0) {
					sellCode4 = getSellCode4()[i];
				}

				int tmpSeq = seqValue + 4;
				String seqString = StringUtils.leftPad(String.valueOf(tmpSeq), 3, '0');
				ColorSize tmpColorSize4 = tmpColorSize1.copyField(seqString, getSize4()[i], sellCode4);
				colorSizeList.add(tmpColorSize4);

			}
			if (getSize5().length > 0 && StringUtils.isNotEmpty(getSize5()[i])) {

				String sellCode5 = "";
				if (getSellCode5().length > 0) {
					sellCode5 = getSellCode5()[i];
				}
				int tmpSeq = seqValue + 5;
				String seqString = StringUtils.leftPad(String.valueOf(tmpSeq), 3, '0');
				ColorSize tmpColorSize5 = tmpColorSize1.copyField(seqString, getSize5()[i], sellCode5);
				colorSizeList.add(tmpColorSize5);

			}

		}

		setColorSizeList(colorSizeList);
	}

	public String[] getSellCode1() {
		return sellCode1;
	}

	public void setSellCode1(String[] sellCode1) {
		this.sellCode1 = sellCode1;
	}

	public String[] getSellCode2() {
		return sellCode2;
	}

	public void setSellCode2(String[] sellCode2) {
		this.sellCode2 = sellCode2;
	}

	public String[] getSellCode3() {
		return sellCode3;
	}

	public void setSellCode3(String[] sellCode3) {
		this.sellCode3 = sellCode3;
	}

	public String[] getSellCode4() {
		return sellCode4;
	}

	public void setSellCode4(String[] sellCode4) {
		this.sellCode4 = sellCode4;
	}

	public String[] getSellCode5() {
		return sellCode5;
	}

	public void setSellCode5(String[] sellCode5) {
		this.sellCode5 = sellCode5;
	}

	public ArrayList<ColorSize> getColorSizeList() {
		return colorSizeList;
	}

	public void setColorSizeList(ArrayList<ColorSize> colorSizeList) {
		this.colorSizeList = colorSizeList;
	}

	public ArrayList<EcomAddInfo> getEcomAddInfoList() {
		return ecomAddInfoList;
	}

	public void setEcomAddInfoList(ArrayList<EcomAddInfo> ecomAddInfoList) {
		this.ecomAddInfoList = ecomAddInfoList;
	}


	public String[] getColorCode() {
		return colorCode;
	}
	public void setColorCode(String[] colorCode) {
		this.colorCode = colorCode;
	}
	public String[] getSizeCategory() {
		return sizeCategory;
	}
	public void setSizeCategory(String[] sizeCategory) {
		this.sizeCategory = sizeCategory;
	}
	public String[] getSize1() {
		return size1;
	}
	public void setSize1(String[] size1) {
		this.size1 = size1;
	}
	public String[] getSize2() {
		return size2;
	}
	public void setSize2(String[] size2) {
		this.size2 = size2;
	}
	public String[] getSize3() {
		return size3;
	}
	public void setSize3(String[] size3) {
		this.size3 = size3;
	}
	public String[] getSize4() {
		return size4;
	}
	public void setSize4(String[] size4) {
		this.size4 = size4;
	}
	public String[] getSize5() {
		return size5;
	}
	public void setSize5(String[] size5) {
		this.size5 = size5;
	}
	public String[] getSellFlag() {
		return sellFlag;
	}
	public void setSellFlag(String[] sellFlag) {
		this.sellFlag = sellFlag;
	}
	public Integer getSaleAmount() {
		return saleAmount;
	}
	public void setSaleAmount(Integer saleAmount) {
		this.saleAmount = saleAmount;
	}
	public String getSellPrice() {
		return sellPrice;
	}
	public void setSellPrice(String sellPrice) {
		this.sellPrice = sellPrice;
	}
	public String getProductCountryCode() {
		return productCountryCode;
	}
	public void setProductCountryCode(String productCountryCode) {
		this.productCountryCode = productCountryCode;
	}
	public String getProfitRate() {
		return profitRate;
	}

	public String getVicprofitRate() {
		return vicprofitRate;
	}

	public String getMatCd() {
		return matCd;
	}

	public void setProfitRate(String profitRate) {
		this.profitRate = profitRate;
	}

	public void setVicprofitRate(String vicprofitRate) {
		this.vicprofitRate = vicprofitRate;
	}

	public void setMatCd(String matCd) {
		this.matCd = matCd;
	}

	public OfficialOrder getOfficialOrder() {
		return officialOrder;
	}
	public void setOfficialOrder(OfficialOrder officialOrder) {
		this.officialOrder = officialOrder;
	}
	public String getNewProductCode() {
		return newProductCode;
	}
	public void setNewProductCode(String newProductCode) {
		this.newProductCode = newProductCode;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductShortName() {
		return productShortName;
	}
	public void setProductShortName(String productShortName) {
		this.productShortName = productShortName;
	}
	public String getOnOffDivnCode() {
		return onOffDivnCode;
	}
	public void setOnOffDivnCode(String onOffDivnCode) {
		this.onOffDivnCode = onOffDivnCode;
	}
	public String getEntpCode() {
		return entpCode;
	}
	public void setEntpCode(String entpCode) {
		this.entpCode = entpCode;
	}
	public String getSellCode() {
		return sellCode;
	}
	public void setSellCode(String sellCode) {
		this.sellCode = sellCode;
	}
	public String getProductDivnCode() {
		return productDivnCode;
	}
	public void setProductDivnCode(String productDivnCode) {
		this.productDivnCode = productDivnCode;
	}
	public String getProductStandardName() {
		return productStandardName;
	}
	public void setProductStandardName(String productStandardName) {
		this.productStandardName = productStandardName;
	}
	public Integer getDisplayTotalQuantity() {
		return displayTotalQuantity;
	}
	public void setDisplayTotalQuantity(Integer displayTotalQuantity) {
		this.displayTotalQuantity = displayTotalQuantity;
	}
	public Integer getDisplayBaseQuantity() {
		return displayBaseQuantity;
	}
	public void setDisplayBaseQuantity(Integer displayBaseQuantity) {
		this.displayBaseQuantity = displayBaseQuantity;
	}
	public String getNormalProductCost() {
		return normalProductCost;
	}

	public void setNormalProductCost(String normalProductCost) {
		this.normalProductCost = normalProductCost;
	}

	public void setVicnormalProductCost(String vicnormalProductCost) {
		this.vicnormalProductCost = vicnormalProductCost;
	}

	public String getVicnormalProductCost() {
		return vicnormalProductCost;
	}

	public String getNormalProductSalePrice() {
		return normalProductSalePrice;
	}
	public String getVicnormalProductSalePrice() {
		return vicnormalProductSalePrice;
	}

	public void setNormalProductSalePrice(String normalProductSalePrice) {
		this.normalProductSalePrice = normalProductSalePrice;
	}

	public void setVicnormalProductSalePrice(String vicnormalProductSalePrice) {
		this.vicnormalProductSalePrice = vicnormalProductSalePrice;
	}

	public String getDisplayUnitCode() {
		return displayUnitCode;
	}
	public void setDisplayUnitCode(String displayUnitCode) {
		this.displayUnitCode = displayUnitCode;
	}
	public String getEventSellPrice() {
		return eventSellPrice;
	}
	public void setEventSellPrice(String eventSellPrice) {
		this.eventSellPrice = eventSellPrice;
	}
	public String getTaxatDivCode() {
		return taxatDivCode;
	}
	public void setTaxatDivCode(String taxatDivCode) {
		this.taxatDivCode = taxatDivCode;
	}
	public String getProductTypeDivnCode() {
		return productTypeDivnCode;
	}
	public void setProductTypeDivnCode(String productTypeDivnCode) {
		this.productTypeDivnCode = productTypeDivnCode;
	}
	public String getCenterTypeDivnCode() {
		return centerTypeDivnCode;
	}
	public void setCenterTypeDivnCode(String centerTypeDivnCode) {
		this.centerTypeDivnCode = centerTypeDivnCode;
	}
	public String getPriceIssueDivnCode() {
		return priceIssueDivnCode;
	}
	public void setPriceIssueDivnCode(String priceIssueDivnCode) {
		this.priceIssueDivnCode = priceIssueDivnCode;
	}
	public String getProductHorizontalLength() {
		return productHorizontalLength;
	}
	public void setProductHorizontalLength(String productHorizontalLength) {
		this.productHorizontalLength = productHorizontalLength;
	}
	public String getProductVerticalLength() {
		return productVerticalLength;
	}
	public void setProductVerticalLength(String productVerticalLength) {
		this.productVerticalLength = productVerticalLength;
	}
	public String getProductHeight() {
		return productHeight;
	}
	public void setProductHeight(String productHeight) {
		this.productHeight = productHeight;
	}
	public String getProductDescription() {
		return productDescription;
	}
	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}
	public String getProductDay() {
		return productDay;
	}
	public void setProductDay(String productDay) {
		this.productDay = productDay;
	}
	public String getProductImageId() {
		return productImageId;
	}
	public void setProductImageId(String productImageId) {
		this.productImageId = productImageId;
	}
	public Date getRegDate() {
		return regDate;
	}
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	public Date getModDate() {
		return modDate;
	}
	public void setModDate(Date modDate) {
		this.modDate = modDate;
	}
	public String getModId() {
		return modId;
	}
	public void setModId(String modId) {
		this.modId = modId;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public String getTotalInspectYn() {
		return totalInspectYn;
	}

	public void setTotalInspectYn(String totalInspectYn) {
		this.totalInspectYn = totalInspectYn;
	}

	public Integer getImgNcnt() {
		return imgNcnt;
	}

	public void setImgNcnt(Integer imgNcnt) {
		this.imgNcnt = imgNcnt;
	}

	public String getlGrpCd() {
		return lGrpCd;
	}

	public void setlGrpCd(String lGrpCd) {
		this.lGrpCd = lGrpCd;
	}

	public String getImgNcntYn() {
		return imgNcntYn;
	}

	public void setImgNcntYn(String imgNcntYn) {
		this.imgNcntYn = imgNcntYn;
	}
	public String getwUseFlag() {
		return wUseFlag;
	}

	public void setwUseFlag(String wUseFlag) {
		this.wUseFlag = wUseFlag;
	}

	public String getOnlineRepProdCd() {
		return onlineRepProdCd;
	}

	public void setOnlineRepProdCd(String onlineRepProdCd) {
		this.onlineRepProdCd = onlineRepProdCd;
	}

	public String getProdAddMasterCd() {
		return prodAddMasterCd;
	}

	public void setProdAddMasterCd(String prodAddMasterCd) {
		this.prodAddMasterCd = prodAddMasterCd;
	}

	public String getProdAddCd() {
		return prodAddCd;
	}

	public void setProdAddCd(String prodAddCd) {
		this.prodAddCd = prodAddCd;
	}

	public String getProdAddNm() {
		return prodAddNm;
	}

	public void setProdAddNm(String prodAddNm) {
		this.prodAddNm = prodAddNm;
	}

	public String getProdcommerce() {
		return prodcommerce;
	}

	public void setProdcommerce(String prodcommerce) {
		this.prodcommerce = prodcommerce;
	}

	public String getMd_vali_sellCode() {
		return md_vali_sellCode;
	}

	public void setMd_vali_sellCode(String md_vali_sellCode) {
		this.md_vali_sellCode = md_vali_sellCode;
	}

	private String[] itemCd;
	private String[] optnDesc;
	private String[] stkMgrYn;
	private int[] rservStkQty;
	private String[] optnAmt; //단품 가격
	
	private String prodDirectYn; // 단일형상품  상품속성 직접입력 여부
	
	private String optnDirectYn; // 단품 옵션형 상품 옵셥값 직접입력 여부
	
	private String[] ecProductAttrId;

	private ArrayList<ColorSize> itemList = new ArrayList<ColorSize>();

	public void makeItemObject() {

		if (getItemCd() != null && getItemCd().length > 0) {
			int arrayLength = getItemCd().length;
			for (int i = 0; i < arrayLength; i++) {
				ColorSize tmpItem = new ColorSize();
				tmpItem.setNewProductCode(getNewProductCode());

				tmpItem.setProductName(getProductName());
				tmpItem.setEnterpriseCode(getEntpCode());
				tmpItem.setSellCode(getSellCode());

				tmpItem.setItemCode(getItemCd()[i]);
				if (getOptnDesc() != null && getOptnDesc().length > 0) {
					tmpItem.setOptnDesc(getOptnDesc()[i]);
				}
				if (getStkMgrYn() != null && getStkMgrYn().length > 0) {
					tmpItem.setStkMgrYn(getStkMgrYn()[i]);
				}
				if (getRservStkQty() != null && getRservStkQty().length > 0) {
					tmpItem.setRservStkQty(getRservStkQty()[i]);
				}

				if (getOptnAmt().length > 0) {
					if (getOptnAmt()[i] != null && !"".equals(getOptnAmt()[i])) {
						tmpItem.setOptnAmt(Integer.parseInt(getOptnAmt()[i]));
					}
				}

				itemList.add(tmpItem);

			}
		}

		setItemList(itemList);
	}

	public ArrayList<ColorSize> getItemList() {
		return itemList;
	}

	public void setItemList(ArrayList<ColorSize> itemList) {
		this.itemList = itemList;
	}

	public String[] getItemCd() {
		return itemCd;
	}

	public void setItemCd(String[] itemCd) {
		this.itemCd = itemCd;
	}

	public String[] getOptnDesc() {
		return optnDesc;
	}

	public void setOptnDesc(String[] optnDesc) {
		this.optnDesc = optnDesc;
	}

	public String[] getStkMgrYn() {
		return stkMgrYn;
	}

	public void setStkMgrYn(String[] stkMgrYn) {
		this.stkMgrYn = stkMgrYn;
	}

	public int[] getRservStkQty() {
		return rservStkQty;
	}

	public void setRservStkQty(int[] rservStkQty) {
		this.rservStkQty = rservStkQty;
	}


	public String getNewProdPromoFg() {
		return newProdPromoFg;
	}

	public void setNewProdPromoFg(String newProdPromoFg) {
		this.newProdPromoFg = newProdPromoFg;
	}

	public String getNewProdStDy() {
		return newProdStDy;
	}

	public void setNewProdStDy(String newProdStDy) {
		this.newProdStDy = newProdStDy;
	}

	public String getOverPromoFg() {
		return overPromoFg;
	}

	public void setOverPromoFg(String overPromoFg) {
		this.overPromoFg = overPromoFg;
	}

	public String getProdCertMasterCd() {
		return prodCertMasterCd;
	}

	public void setProdCertMasterCd(String prodCertMasterCd) {
		this.prodCertMasterCd = prodCertMasterCd;
	}

	public String getProdCertCd() {
		return prodCertCd;
	}

	public void setProdCertCd(String prodCertCd) {
		this.prodCertCd = prodCertCd;
	}

	public String getProdCertNm() {
		return prodCertNm;
	}

	public void setProdCertNm(String prodCertNm) {
		this.prodCertNm = prodCertNm;
	}

	public String getInfoGrpCd2() {
		return infoGrpCd2;
	}

	public void setInfoGrpCd2(String infoGrpCd2) {
		this.infoGrpCd2 = infoGrpCd2;
	}

	public String getSesnYearCd() {
		return sesnYearCd;
	}

	public void setSesnYearCd(String sesnYearCd) {
		this.sesnYearCd = sesnYearCd;
	}

	public String getSesnDivnCd() {
		return sesnDivnCd;
	}

	public void setSesnDivnCd(String sesnDivnCd) {
		this.sesnDivnCd = sesnDivnCd;
	}

	public String getTeamCd() {
		return teamCd;
	}

	public void setTeamCd(String teamCd) {
		this.teamCd = teamCd;
	}

	public String getL1Cd() {
		return l1Cd;
	}

	public void setL1Cd(String l1Cd) {
		this.l1Cd = l1Cd;
	}

	public String getL2Cd() {
		return l2Cd;
	}

	public void setL2Cd(String l2Cd) {
		this.l2Cd = l2Cd;
	}

	public String getL3Cd() {
		return l3Cd;
	}

	public void setL3Cd(String l3Cd) {
		this.l3Cd = l3Cd;
	}

	public String getEntpInProdCd() {
		return entpInProdCd;
	}

	public void setEntpInProdCd(String entpInProdCd) {
		this.entpInProdCd = entpInProdCd;
	}

	public String getPsbtStartDy() {
		return psbtStartDy;
	}

	public void setPsbtStartDy(String psbtStartDy) {
		this.psbtStartDy = psbtStartDy;
	}

	public String getPsbtEndDy() {
		return psbtEndDy;
	}

	public void setPsbtEndDy(String psbtEndDy) {
		this.psbtEndDy = psbtEndDy;
	}

	public String getPickIdctDy() {
		return pickIdctDy;
	}

	public void setPickIdctDy(String pickIdctDy) {
		this.pickIdctDy = pickIdctDy;
	}

	public String getOnlineProdTypeCd() {
		return onlineProdTypeCd;
	}

	public void setOnlineProdTypeCd(String onlineProdTypeCd) {
		this.onlineProdTypeCd = onlineProdTypeCd;
	}

	public String getSetQty() {
		return setQty;
	}

	public void setSetQty(String setQty) {
		this.setQty = setQty;
	}

	public String getStaffSellYn() {
		return staffSellYn;
	}

	public void setStaffSellYn(String staffSellYn) {
		this.staffSellYn = staffSellYn;
	}

	public String getStaffDcAmt() {
		return staffDcAmt;
	}

	public void setStaffDcAmt(String staffDcAmt) {
		this.staffDcAmt = staffDcAmt;
	}

	public String[] getOptnAmt() {
		return optnAmt;
	}

	public void setOptnAmt(String[] optnAmt) {
		this.optnAmt = optnAmt;
	}

	public String getProdDirectYn() {
		return prodDirectYn;
	}

	public void setProdDirectYn(String prodDirectYn) {
		this.prodDirectYn = prodDirectYn;
	}

	public String getOptnDirectYn() {
		return optnDirectYn;
	}

	public void setOptnDirectYn(String optnDirectYn) {
		this.optnDirectYn = optnDirectYn;
	}

	public String[] getEcProductAttrId() {
		return ecProductAttrId;
	}

	public void setEcProductAttrId(String[] ecProductAttrId) {
		this.ecProductAttrId = ecProductAttrId;
	}

	public String getOptnLoadContent() {
		return optnLoadContent;
	}

	public void setOptnLoadContent(String optnLoadContent) {
		this.optnLoadContent = optnLoadContent;
	}

	public String getProdPrcMgrYn() {
		return prodPrcMgrYn;
	}

	public void setProdPrcMgrYn(String prodPrcMgrYn) {
		this.prodPrcMgrYn = prodPrcMgrYn;
	}

	public String getNorProdSaleCurr() {
		return norProdSaleCurr;
	}

	public void setNorProdSaleCurr(String norProdSaleCurr) {
		this.norProdSaleCurr = norProdSaleCurr;
	}

	public String getSizeUnit() {
		return sizeUnit;
	}

	public void setSizeUnit(String sizeUnit) {
		this.sizeUnit = sizeUnit;
	}

	public String getCtpdYn() {
		return ctpdYn;
	}

	public void setCtpdYn(String ctpdYn) {
		this.ctpdYn = ctpdYn;
	}

	public String getDealRepProdYn() {
		return dealRepProdYn;
	}

	public void setDealRepProdYn(String dealRepProdYn) {
		this.dealRepProdYn = dealRepProdYn;
	}

	public String getSellStartDy() {
		return sellStartDy;
	}

	public void setSellStartDy(String sellStartDy) {
		this.sellStartDy = sellStartDy;
	}

	public String getSellEndDy() {
		return sellEndDy;
	}

	public void setSellEndDy(String sellEndDy) {
		this.sellEndDy = sellEndDy;
	}

	public String getHopeDeliPsbtDd() {
		return hopeDeliPsbtDd;
	}

	public void setHopeDeliPsbtDd(String hopeDeliPsbtDd) {
		this.hopeDeliPsbtDd = hopeDeliPsbtDd;
	}

	public String getDealProdDivnCode() {
		return dealProdDivnCode;
	}

	public void setDealProdDivnCode(String dealProdDivnCode) {
		this.dealProdDivnCode = dealProdDivnCode;
	}

	public String getProdOptionDesc() {
		return prodOptionDesc;
	}

	public void setProdOptionDesc(String prodOptionDesc) {
		this.prodOptionDesc = prodOptionDesc;
	}

	public String getDeliDueDate() {
		return deliDueDate;
	}

	public void setDeliDueDate(String deliDueDate) {
		this.deliDueDate = deliDueDate;
	}

	//20180904 - 상품키워드 입력 기능 추가
	private String[] searchKywrd;
	private String[] seq;
	private ArrayList<PEDMPRO0011VO> keywordList = new ArrayList<PEDMPRO0011VO>();

	public String[] getSearchKywrd() {
		return searchKywrd;
	}

	public void setSearchKywrd(String[] searchKywrd) {
		this.searchKywrd = searchKywrd;
	}

	public String[] getSeq() {
		return seq;
	}

	public void setSeq(String[] seq) {
		this.seq = seq;
	}

	public ArrayList<PEDMPRO0011VO> getKeywordList() {
		return keywordList;
	}

	public void setKeywordList(ArrayList<PEDMPRO0011VO> keywordList) {
		this.keywordList = keywordList;
	}

	public void makeKeywordObject() {

		if (getSearchKywrd() != null && getSearchKywrd().length > 0) {
			int arrayLength = getSearchKywrd().length;

			for (int i = 0; i < arrayLength; i++) {
				PEDMPRO0011VO tmpKeyword = new PEDMPRO0011VO();

				tmpKeyword.setPgmId(getNewProductCode());
				tmpKeyword.setSeq(getSeq()[i]);
				tmpKeyword.setSearchKywrd(getSearchKywrd()[i]);
//				tmpKeyword.setRegId(getEntpCode());
				tmpKeyword.setRegId(getRegId());

				keywordList.add(tmpKeyword);
			}
		}

		setKeywordList(keywordList);
	}

	/*
	 *	20200204 EC 표준, 전시 카테고리 추가
	 * 	20200204 EC 상품속성 추가
	 */
	private String stdCatCd;		//EC 표준 카테고리
	private String dispCatCd;		//EC 전시 카테고리
	private String attrPiType;		//EC 상품속성타입
	private String[] ecProductAttr;	//EC 상품단품별 속성

	private ArrayList<EcProductAttribute> attrbuteList = new ArrayList<EcProductAttribute>();

	public String getStdCatCd() {
		return stdCatCd;
	}

	public void setStdCatCd(String stdCatCd) {
		this.stdCatCd = stdCatCd;
	}

	public String getDispCatCd() {
		return dispCatCd;
	}

	public void setDispCatCd(String dispCatCd) {
		this.dispCatCd = dispCatCd;
	}

	public String getAttrPiType() {
		return attrPiType;
	}

	public void setAttrPiType(String attrPiType) {
		this.attrPiType = attrPiType;
	}

	public String[] getEcProductAttr() {
		return ecProductAttr;
	}

	public void setEcProductAttr(String[] ecProductAttr) {
		this.ecProductAttr = ecProductAttr;
	}

	public ArrayList<EcProductAttribute> getAttrbuteList() {
		return attrbuteList;
	}

	public void setAttrbuteList(ArrayList<EcProductAttribute> attrbuteList) {
		this.attrbuteList = attrbuteList;
	}

	public void makeEcAttrbuteObject() {

		if (getAttrPiType() != null && getEcProductAttr() != null) {
			if ("P".equals(getAttrPiType())) {
				int attrIdLength = getEcProductAttr().length;

				for (int i = 0; i < attrIdLength; i++) {
					if (!StringUtils.isEmpty(getEcProductAttr()[i])) {
						EcProductAttribute tmpAttrbute = new EcProductAttribute();
						
						tmpAttrbute.setProdCd(getNewProductCode());
						tmpAttrbute.setItemCd("001");
						tmpAttrbute.setStdCatCd(getStdCatCd());

						if ("Y".equals(getProdDirectYn())) { // 직접입력
							tmpAttrbute.setAttrId(getEcProductAttrId()[i]);
							tmpAttrbute.setAttrVal(getEcProductAttr()[i]);
						} else { // 선택입력
							String[] attrId = getEcProductAttr()[i].split("_"); // attrId(속성유형번호), attrValId(속성값번호), 속성값이 etc 인경우 직접입력
							tmpAttrbute.setAttrId(attrId[0]);
							tmpAttrbute.setAttrValId(attrId[1]);
						}

//						tmpAttrbute.setRegId(getEntpCode());
						tmpAttrbute.setRegId(getRegId());
						attrbuteList.add(tmpAttrbute);
					}
				}
			}

			if ("I".equals(getAttrPiType())) {
				int itemCdLength = getItemCd().length;
				int attrIdLength = getEcProductAttr().length;

				for (int i = 0; i < attrIdLength; i++) {
					if (!StringUtils.isEmpty(getEcProductAttr()[i])) {
						EcProductAttribute tmpAttrbute = new EcProductAttribute();
						
						tmpAttrbute.setProdCd(getNewProductCode());
						tmpAttrbute.setItemCd(getItemCd()[i / (attrIdLength / itemCdLength)]);
						tmpAttrbute.setStdCatCd(getStdCatCd());
						
						if ("Y".equals(getOptnDirectYn())) { // 직접입력
							tmpAttrbute.setAttrId(getEcProductAttrId()[i]);
							tmpAttrbute.setAttrVal(getEcProductAttr()[i]);
						} else { // 선택입력
							String[] attrId = getEcProductAttr()[i].split("_"); // attrId(속성유형번호), attrValId(속성값번호), 속성값이 etc 인경우 직접입력
							tmpAttrbute.setAttrId(attrId[0]);
							tmpAttrbute.setAttrValId(attrId[1]);
						}
						
//						tmpAttrbute.setRegId(getEntpCode());
						tmpAttrbute.setRegId(getRegId());
						attrbuteList.add(tmpAttrbute);
					}
				}
			}
			setAttrbuteList(attrbuteList);
		}
	}

}
