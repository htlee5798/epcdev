package com.lottemart.epc.edi.product.model;

import java.io.Serializable;

/**
 * @Class Name : NEDMPRO0041VO
 * @Description : 상품상세정보 VO
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>O
 *   
 *   수정일      			수정자           		수정내용
 *  -------    		--------    	---------------------------
 * 	2015.12.04 	SONG MIN KYO	최초생성
 * </pre>
 */
public class NEDMPRO0041VO implements Serializable {

	
	private static final long serialVersionUID = -8849105587473246889L;
			
	private String pgmId;					//신규상품코드
	private String prodNm;					//상품명
	private String prodShortNm;				//상품단축명
	private String onoffDivnCd;	  	 		//온오프구분[온오프:0, 온라인:1, 소셜:2]
	private String entpCd;					//협력업체코드
	private String sellCd;					//판매코드
	private String prodDivnCd;				//상품구분[규격:1, 패션:5]
	private String prodStandardNm; 			//상품규격명
	private String dpTotQty;				//표시총수량
	private String dpBaseQty;				//표시기준수량
	private String norProdPcost;			//정상상품원가
	private String norProdSalePrc;			//성상상품매출가격
	private String dpUnitCd;				//표시단위코드
	private String evtProdSellPrc;			//행사상품판매가격
	private String sellPrc;					//판매가격??
	private String taxatDivnCd;				//과세구분코드
	private String prodTypeDivnCd;			//상품유형구분코드
	private String ctrTypeDivnCd;			//센터유형구분코드
	private String prcIssueDivnCd;			//가격발급구분코드
	private String prodHrznLeng;			//상품가로길이 			
	private String prodVtclLeng;			//상품세로길이
	private String prodHigt;				//상품높이
	private String prodDesc;				//상품설명
	private String ispDtlDesc;				//ISP상세설명
	private String teamCd;					//팀코드
	private String l1Cd;					//대분류
	private String l3Cd;					//소분류
	private String onlineProdNm;			//온라인상품명
	private String homeCd;					//원산지
	private String onlineRepProdCd;			//온라인대표상품코드
	private String purInrcntQty;			//발주입수수량
	private String purUnitCd;				//발주단위코드
	private String prftRate;				//이익율
	private String flowDdMgrCd;				//유통일관리여부
	private String npurBuyPsbtDivnCd;		//무발주매입가능구분코드
	private String maxKeepDdCnt;			//최대보관일수
	private String newProdFirstPurDivnCd;	//새상품최초발주구분코드
	private String mixYn;					//혼재여부
	private String totInspYn;				//전수검사여부
	private String protectTagDivnCd;		//도난방지태그구분
	private String protectTagTypeCd;		//도난방지태그유형코드
	private String tmprtDivnCd;				//온도구분코드
	private String qtyWegtDivnCd;			//수량무게구분코드
	private String sesnDivnCd;				//계절구분코드
	private String minOrdPsbtQty;			//최소주문가능량
	private String maxOrdPsbtQty;			//치대주문가능량
	private String brandCd;					//브랜드코드
	private String brandNm;					//브랜드명
	private String makerCd;					//메이커코드
	private String makerNm;					//메이커명
	private String trdTypeDivnCd;			//거래유형구분코드
	private String regDate;					//등록일자
	private String prodImgId;				//상품이미지아이디
	private String productDy;				//생산일자
	private String categoryId; 				//온라인전시카테고리아이디
	private String categoryNm;				//온라인전시카테고리명
	private String socialProdDeliCd;		//소셜상품배송코드
	private String stgePsbtDd;				//입고가능일
	private String pickPsbtDd;				//출고가능일
	private String newProdGenDivnCd;		//새상품생성구분코드
	private String nfomlVariationDesc;		//비정규베리에이션설명
	private String imgNcnt;					//온라인이미지개수
	private String matCd;					//소재코드->VIC마켓상품구분:PRD41
	private String wnorProdPcost;			//W정상상품원가
	private String wnorProdSalePrc;			//W정상상품매출가격
	private String wprftRate;				//W이익율
	private String infoGrpCd;	   	 	 	//전상법		
	private String infoGrpCd2; 	 			//KC인증
	private String mdValiSellCd; 			//MD유효판매코드
	private String newProdPromoFg;			//신상품장려금구분
	private String newProdStDy;				//신상품출시일자
	private String overPromoFg;				//성과초과장려금구분
	private String modelNm;					//모델명
	private String ecoYn;					//친환경인증여부
	private String ecoNm;					//친환경인증분류명
	private String dlvGa;					//차등배송비여부
	private String dlvDt;			  		//별도설치비내용		
	private String insCo;					//별도설치비유무
	private String sizeUnit;				//사이즈단위
	private String l2Cd;					//중분류코드 	
	private String noprodFg;				//상품유형(비상품구분)
	private String prodAttTypFg;			//상품범주(단일상품, 묶음상품) 	
	private String totWeight;				//총중량 	
	private String weightUnit;				//총중량 단위 	
	private String pieceQty;				//낱개단위 	
	private String pieceUnit;				//낱개단위 	
	private String wasteFg;					//폐기물유형 	
	private String plasticWeight;			//플라스틱무게 	
	private String recycleFg1;				//재활용유형1 	
	private String recycleFg2;				//재활용유형2 	
	private String recycleFg3;				//재활용유형3 	
	private String recycleWeight1;			//재활용무게1 	
	private String recycleWeight2;			//재활용무게2 	
	private String recycleWeight3;			//재활용무게3 	
	private String flowDd;					//유통일 
	
	
	/* 상품 변형 속성 */
	private String variant;					// 변형구분 	
	private String varAttClass;				// 상품변경플레그 ("I", "U", "D")
	private String attCd;					// 속성구분 
	private String attValue;				// 속성값 
	private String varAttSellCd;			// 판매코드 
	private String delTf;					// 삭제유무 
	private String varAttProdImgId;			// 상품이미지아이디 
	private String optnDesc;				// 옵션설명 
		
	public String getPgmId() {
		return pgmId;
	}
	public void setPgmId(String pgmId) {
		this.pgmId = pgmId;
	}
	public String getProdNm() {
		return prodNm;
	}
	public void setProdNm(String prodNm) {
		this.prodNm = prodNm;
	}
	public String getProdShortNm() {
		return prodShortNm;
	}
	public void setProdShortNm(String prodShortNm) {
		this.prodShortNm = prodShortNm;
	}
	public String getOnoffDivnCd() {
		return onoffDivnCd;
	}
	public void setOnoffDivnCd(String onoffDivnCd) {
		this.onoffDivnCd = onoffDivnCd;
	}
	public String getEntpCd() {
		return entpCd;
	}
	public void setEntpCd(String entpCd) {
		this.entpCd = entpCd;
	}
	public String getSellCd() {
		return sellCd;
	}
	public void setSellCd(String sellCd) {
		this.sellCd = sellCd;
	}
	public String getProdDivnCd() {
		return prodDivnCd;
	}
	public void setProdDivnCd(String prodDivnCd) {
		this.prodDivnCd = prodDivnCd;
	}
	public String getProdStandardNm() {
		return prodStandardNm;
	}
	public void setProdStandardNm(String prodStandardNm) {
		this.prodStandardNm = prodStandardNm;
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
	public String getNorProdPcost() {
		return norProdPcost;
	}
	public void setNorProdPcost(String norProdPcost) {
		this.norProdPcost = norProdPcost;
	}
	public String getNorProdSalePrc() {
		return norProdSalePrc;
	}
	public void setNorProdSalePrc(String norProdSalePrc) {
		this.norProdSalePrc = norProdSalePrc;
	}
	public String getDpUnitCd() {
		return dpUnitCd;
	}
	public void setDpUnitCd(String dpUnitCd) {
		this.dpUnitCd = dpUnitCd;
	}
	public String getEvtProdSellPrc() {
		return evtProdSellPrc;
	}
	public void setEvtProdSellPrc(String evtProdSellPrc) {
		this.evtProdSellPrc = evtProdSellPrc;
	}
	public String getSellPrc() {
		return sellPrc;
	}
	public void setSellPrc(String sellPrc) {
		this.sellPrc = sellPrc;
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
	public String getProdHrznLeng() {
		return prodHrznLeng;
	}
	public void setProdHrznLeng(String prodHrznLeng) {
		this.prodHrznLeng = prodHrznLeng;
	}
	public String getProdVtclLeng() {
		return prodVtclLeng;
	}
	public void setProdVtclLeng(String prodVtclLeng) {
		this.prodVtclLeng = prodVtclLeng;
	}
	public String getProdHigt() {
		return prodHigt;
	}
	public void setProdHigt(String prodHigt) {
		this.prodHigt = prodHigt;
	}
	public String getProdDesc() {
		return prodDesc;
	}
	public void setProdDesc(String prodDesc) {
		this.prodDesc = prodDesc;
	}
	public String getIspDtlDesc() {
		return ispDtlDesc;
	}
	public void setIspDtlDesc(String ispDtlDesc) {
		this.ispDtlDesc = ispDtlDesc;
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
	public String getL3Cd() {
		return l3Cd;
	}
	public void setL3Cd(String l3Cd) {
		this.l3Cd = l3Cd;
	}
	public String getOnlineProdNm() {
		return onlineProdNm;
	}
	public void setOnlineProdNm(String onlineProdNm) {
		this.onlineProdNm = onlineProdNm;
	}
	public String getHomeCd() {
		return homeCd;
	}
	public void setHomeCd(String homeCd) {
		this.homeCd = homeCd;
	}
	public String getOnlineRepProdCd() {
		return onlineRepProdCd;
	}
	public void setOnlineRepProdCd(String onlineRepProdCd) {
		this.onlineRepProdCd = onlineRepProdCd;
	}
	public String getPurInrcntQty() {
		return purInrcntQty;
	}
	public void setPurInrcntQty(String purInrcntQty) {
		this.purInrcntQty = purInrcntQty;
	}
	public String getPurUnitCd() {
		return purUnitCd;
	}
	public void setPurUnitCd(String purUnitCd) {
		this.purUnitCd = purUnitCd;
	}
	public String getPrftRate() {
		return prftRate;
	}
	public void setPrftRate(String prftRate) {
		this.prftRate = prftRate;
	}
	public String getFlowDdMgrCd() {
		return flowDdMgrCd;
	}
	public void setFlowDdMgrCd(String flowDdMgrCd) {
		this.flowDdMgrCd = flowDdMgrCd;
	}
	public String getNpurBuyPsbtDivnCd() {
		return npurBuyPsbtDivnCd;
	}
	public void setNpurBuyPsbtDivnCd(String npurBuyPsbtDivnCd) {
		this.npurBuyPsbtDivnCd = npurBuyPsbtDivnCd;
	}
	public String getMaxKeepDdCnt() {
		return maxKeepDdCnt;
	}
	public void setMaxKeepDdCnt(String maxKeepDdCnt) {
		this.maxKeepDdCnt = maxKeepDdCnt;
	}
	public String getNewProdFirstPurDivnCd() {
		return newProdFirstPurDivnCd;
	}
	public void setNewProdFirstPurDivnCd(String newProdFirstPurDivnCd) {
		this.newProdFirstPurDivnCd = newProdFirstPurDivnCd;
	}
	public String getMixYn() {
		return mixYn;
	}
	public void setMixYn(String mixYn) {
		this.mixYn = mixYn;
	}
	public String getTotInspYn() {
		return totInspYn;
	}
	public void setTotInspYn(String totInspYn) {
		this.totInspYn = totInspYn;
	}
	public String getProtectTagDivnCd() {
		return protectTagDivnCd;
	}
	public void setProtectTagDivnCd(String protectTagDivnCd) {
		this.protectTagDivnCd = protectTagDivnCd;
	}
	public String getProtectTagTypeCd() {
		return protectTagTypeCd;
	}
	public void setProtectTagTypeCd(String protectTagTypeCd) {
		this.protectTagTypeCd = protectTagTypeCd;
	}
	public String getTmprtDivnCd() {
		return tmprtDivnCd;
	}
	public void setTmprtDivnCd(String tmprtDivnCd) {
		this.tmprtDivnCd = tmprtDivnCd;
	}
	public String getQtyWegtDivnCd() {
		return qtyWegtDivnCd;
	}
	public void setQtyWegtDivnCd(String qtyWegtDivnCd) {
		this.qtyWegtDivnCd = qtyWegtDivnCd;
	}
	public String getSesnDivnCd() {
		return sesnDivnCd;
	}
	public void setSesnDivnCd(String sesnDivnCd) {
		this.sesnDivnCd = sesnDivnCd;
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
	public String getBrandCd() {
		return brandCd;
	}
	public void setBrandCd(String brandCd) {
		this.brandCd = brandCd;
	}
	public String getBrandNm() {
		return brandNm;
	}
	public void setBrandNm(String brandNm) {
		this.brandNm = brandNm;
	}
	public String getMakerCd() {
		return makerCd;
	}
	public void setMakerCd(String makerCd) {
		this.makerCd = makerCd;
	}
	public String getMakerNm() {
		return makerNm;
	}
	public void setMakerNm(String makerNm) {
		this.makerNm = makerNm;
	}
	public String getTrdTypeDivnCd() {
		return trdTypeDivnCd;
	}
	public void setTrdTypeDivnCd(String trdTypeDivnCd) {
		this.trdTypeDivnCd = trdTypeDivnCd;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getProdImgId() {
		return prodImgId;
	}
	public void setProdImgId(String prodImgId) {
		this.prodImgId = prodImgId;
	}
	public String getProductDy() {
		return productDy;
	}
	public void setProductDy(String productDy) {
		this.productDy = productDy;
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
	public String getSocialProdDeliCd() {
		return socialProdDeliCd;
	}
	public void setSocialProdDeliCd(String socialProdDeliCd) {
		this.socialProdDeliCd = socialProdDeliCd;
	}
	public String getStgePsbtDd() {
		return stgePsbtDd;
	}
	public void setStgePsbtDd(String stgePsbtDd) {
		this.stgePsbtDd = stgePsbtDd;
	}
	public String getPickPsbtDd() {
		return pickPsbtDd;
	}
	public void setPickPsbtDd(String pickPsbtDd) {
		this.pickPsbtDd = pickPsbtDd;
	}
	public String getNewProdGenDivnCd() {
		return newProdGenDivnCd;
	}
	public void setNewProdGenDivnCd(String newProdGenDivnCd) {
		this.newProdGenDivnCd = newProdGenDivnCd;
	}
	public String getNfomlVariationDesc() {
		return nfomlVariationDesc;
	}
	public void setNfomlVariationDesc(String nfomlVariationDesc) {
		this.nfomlVariationDesc = nfomlVariationDesc;
	}
	public String getImgNcnt() {
		return imgNcnt;
	}
	public void setImgNcnt(String imgNcnt) {
		this.imgNcnt = imgNcnt;
	}
	public String getMatCd() {
		return matCd;
	}
	public void setMatCd(String matCd) {
		this.matCd = matCd;
	}
	public String getWnorProdPcost() {
		return wnorProdPcost;
	}
	public void setWnorProdPcost(String wnorProdPcost) {
		this.wnorProdPcost = wnorProdPcost;
	}
	public String getWnorProdSalePrc() {
		return wnorProdSalePrc;
	}
	public void setWnorProdSalePrc(String wnorProdSalePrc) {
		this.wnorProdSalePrc = wnorProdSalePrc;
	}
	public String getWprftRate() {
		return wprftRate;
	}
	public void setWprftRate(String wprftRate) {
		this.wprftRate = wprftRate;
	}
	public String getInfoGrpCd() {
		return infoGrpCd;
	}
	public void setInfoGrpCd(String infoGrpCd) {
		this.infoGrpCd = infoGrpCd;
	}
	public String getInfoGrpCd2() {
		return infoGrpCd2;
	}
	public void setInfoGrpCd2(String infoGrpCd2) {
		this.infoGrpCd2 = infoGrpCd2;
	}
	public String getMdValiSellCd() {
		return mdValiSellCd;
	}
	public void setMdValiSellCd(String mdValiSellCd) {
		this.mdValiSellCd = mdValiSellCd;
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
	public String getSizeUnit() {
		return sizeUnit;
	}
	public void setSizeUnit(String sizeUnit) {
		this.sizeUnit = sizeUnit;
	}
	public String getVariant() {
		return variant;
	}
	public void setVariant(String variant) {
		this.variant = variant;
	}
	
	public String getVarAttClass() {
		return varAttClass;
	}
	public void setVarAttClass(String varAttClass) {
		this.varAttClass = varAttClass;
	}
	public String getAttCd() {
		return attCd;
	}
	public void setAttCd(String attCd) {
		this.attCd = attCd;
	}
	public String getAttValue() {
		return attValue;
	}
	public void setAttValue(String attValue) {
		this.attValue = attValue;
	}
	public String getVarAttSellCd() {
		return varAttSellCd;
	}
	public void setVarAttSellCd(String varAttSellCd) {
		this.varAttSellCd = varAttSellCd;
	}
	public String getDelTf() {
		return delTf;
	}
	public void setDelTf(String delTf) {
		this.delTf = delTf;
	}
	
	public String getVarAttProdImgId() {
		return varAttProdImgId;
	}
	public void setVarAttProdImgId(String varAttProdImgId) {
		this.varAttProdImgId = varAttProdImgId;
	}
	public String getOptnDesc() {
		return optnDesc;
	}
	public void setOptnDesc(String optnDesc) {
		this.optnDesc = optnDesc;
	}
	public String getL2Cd() {
		return l2Cd;
	}
	public void setL2Cd(String l2Cd) {
		this.l2Cd = l2Cd;
	}
	public String getNoprodFg() {
		return noprodFg;
	}
	public void setNoprodFg(String noprodFg) {
		this.noprodFg = noprodFg;
	}
	public String getProdAttTypFg() {
		return prodAttTypFg;
	}
	public void setProdAttTypFg(String prodAttTypFg) {
		this.prodAttTypFg = prodAttTypFg;
	}
	public String getTotWeight() {
		return totWeight;
	}
	public void setTotWeight(String totWeight) {
		this.totWeight = totWeight;
	}
	public String getWeightUnit() {
		return weightUnit;
	}
	public void setWeightUnit(String weightUnit) {
		this.weightUnit = weightUnit;
	}
	public String getPieceQty() {
		return pieceQty;
	}
	public void setPieceQty(String pieceQty) {
		this.pieceQty = pieceQty;
	}
	public String getPieceUnit() {
		return pieceUnit;
	}
	public void setPieceUnit(String pieceUnit) {
		this.pieceUnit = pieceUnit;
	}
	public String getWasteFg() {
		return wasteFg;
	}
	public void setWasteFg(String wasteFg) {
		this.wasteFg = wasteFg;
	}
	public String getPlasticWeight() {
		return plasticWeight;
	}
	public void setPlasticWeight(String plasticWeight) {
		this.plasticWeight = plasticWeight;
	}
	public String getRecycleFg1() {
		return recycleFg1;
	}
	public void setRecycleFg1(String recycleFg1) {
		this.recycleFg1 = recycleFg1;
	}
	public String getRecycleFg2() {
		return recycleFg2;
	}
	public void setRecycleFg2(String recycleFg2) {
		this.recycleFg2 = recycleFg2;
	}
	public String getRecycleFg3() {
		return recycleFg3;
	}
	public void setRecycleFg3(String recycleFg3) {
		this.recycleFg3 = recycleFg3;
	}
	public String getRecycleWeight1() {
		return recycleWeight1;
	}
	public void setRecycleWeight1(String recycleWeight1) {
		this.recycleWeight1 = recycleWeight1;
	}
	public String getRecycleWeight2() {
		return recycleWeight2;
	}
	public void setRecycleWeight2(String recycleWeight2) {
		this.recycleWeight2 = recycleWeight2;
	}
	public String getRecycleWeight3() {
		return recycleWeight3;
	}
	public void setRecycleWeight3(String recycleWeight3) {
		this.recycleWeight3 = recycleWeight3;
	}
	public String getFlowDd() {
		return flowDd;
	}
	public void setFlowDd(String flowDd) {
		this.flowDd = flowDd;
	}	
	
}
