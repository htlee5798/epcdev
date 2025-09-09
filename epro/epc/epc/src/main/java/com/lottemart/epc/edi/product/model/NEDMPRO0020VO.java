package com.lottemart.epc.edi.product.model;

import java.io.Serializable;
import java.util.ArrayList;

import com.lcnjf.util.DateUtil;

/**
 * @Class Name : NEDMPRO0020VO
 * @Description : 신규상품 마스터 정보 VO
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>O
 *   
 *   수정일      			수정자           		수정내용
 *  -------    		--------    	---------------------------
 * 	2015.12.10 	SONG MIN KYO	최초생성
 * </pre>
 */
public class NEDMPRO0020VO implements Serializable {
	
	private static final long serialVersionUID = 7020707785271096493L;
	
	/** 신규, 수정 구분 */
	private String iuGbn;
	/** 신규상품코드 */
	private String pgmId;
	/** 팀코드 */
	private String teamCd;
	/** 대분류코드 */
	private String l1Cd;
	/** 소분류코드 */
	private String l3Cd;	
	/** 그룹분석코드 20160803 */
	private String grpCd;		
	/** 상품명 */
	private String prodNm;
	/** 상품명(영문) */
	private String prodEnNm;
	/** 상품단축명 */
	private String prodShortNm;
	/** 온오프구분코드 */
	private String onoffDivnCd;
	/** 업체코드 */
	private String entpCd;
	/** 판매코드 */
	private String sellCd;
	/** 온라인상품명 */
	private String onlineProdNm;
	/** 온라인대표상품코드 */
	private String onlineRepProdCd;
	/** 상품구분코드 */
	private String prodDivnCd;
	/** 상품규격명 */
	private String prodStandardNm;
	/** 표시총수량 */
	private String dpTotQty;
	/** 표시기준수량 */
	private String dpBaseQty;
	/** 정상상품원가 */
	private String norProdPcost;
	/** 통화단위(정상상품원가) */
	private String norProdCurr;
	/** 정상상품매출가격 */
	private String norProdSalePrc;
	/** 통화단위(정상상품매출가격) */
	private String norProdSaleCurr;
	/** 표시단위코드 */
	private String dpUnitCd;
	/** 행사상품판매가격 */
	private String evtProdSellPrc;
	/** 통화단위(행사상품판매가격) */
	private String evtProdSellCurr;
	/**  */
	private String sellPrc;
	/**  */
	private String sellCurr;
	/** 과세구분코드 */
	private String taxatDivnCd;
	/** 상품유형구분코드 */
	private String prodTypeDivnCd;
	/** 센터유형구분코드 */
	private String ctrTypeDivnCd;
	/** 가격발급구분코드 */
	private String prcIssueDivnCd;
	/** 상품가로길이 */
	private String prodHrznLeng;
	/** 상품세로길이 */
	private String prodVtclLeng;
	/** 상품높이 */
	private String prodHigt;
	/** 상품설명 */
	private String prodDesc;
	/** ISP상세설명 */
	private String ispDtlDesc;
	/** 원산지코드 */
	private String homeCd;
	/** 발주입수수량 */
	private String purInrcntQty;
	/** 이익율 */
	private String prftRate;
	/** 발주단위코드 */
	private String purUnitCd;
	/** 권장금액구분코드 */
	private String promoAmtDivnCd;
	/** 새상품최초발주구분코드 */
	private String newProdFirstPurDivnCd;
	/** 무발주매입가능구분코드 */
	private String npurBuyPsbtDivnCd;
	/** 년도 */
	private String sesnYearCd;
	/** 계절구분코드 */
	private String sesnDivnCd;
	/** 도난방지태그구분코드 */
	private String protectTagDivnCd;
	/** 도난방지태그유형코드 */
	private String protectTagTypeCd;
	/** 새상품생성구분코드 */
	private String newProdGenDivnCd;
	/** 거래유형구분코드 */
	private String trdTypeDivnCd;
	/** 바코드검증서유무 */
	private String barcdVerifyDocTf;
	/** 혼재여부 */
	private String mixYn;
	/** 입고가능일 */
	private String stgePsbtDd;
	/** 출고가능일 */
	private String pickPsbtDd;
	/** 온도구분코드 */
	private String tmprtDivnCd;
	/** 유통일관리코드 */
	private String flowDdMgrCd;
	/** 수량무게구분코드 */
	private String qtyWegtDivnCd;
	/** 최대보관일수 */
	private String maxKeepDdCnt;
	/** 브랜드코드 */
	private String brandCode;
	/** 브랜드명 */
	private String brandName;
	/** 메이커코드 */
	private String makerCode;
	/** 메이커명 */
	private String makerName;
	/** 최소주문가능량 */
	private String minOrdPsbtQty;
	/** 최대주문가능량 */
	private String maxOrdPsbtQty;
	/** MD전송구분코드 */
	private String mdSendDivnCd;
	/** 전수검사여부 */
	private String totInspYn;
	/** 생산일자 */
	private String productDy;
	/** 상품이미지아이디 */
	private String prodImgId;
	/** 비정규베리에이션설명 */
	private String nfomlVariationDesc;
	/** 등록일자 */
	private String regDate;
	/** 등록자 */
	private String regId;
	/** 수정일자 */
	private String modDate;
	/** 수정자 */
	private String modId;
	/** 온라인전시카테고리코드 */
	private String categoryId;
	/** 소셜상품배송코드 */
	private String socialProdDeliCd;
	/** KAN 상품분류 */
	private String kanProdCd;
	/** UNSPSC코드 */
	private String unspscCd;
	/** GTIN 코드 */
	private String gtinCd;
	/** 온라인이미지개수 */
	private String imgNcnt;
	/** 와이드 온라인이미지개수 */
	private String wideImgNcnt;
	/** 와이드 온라인이미지 존재유무 */
	private String wideImgNcntYn;
	/** W정상상품원가 */
	private String wnorProdPcost;
	/** 통화단위(W정상상품원가) */
	private String wnorProdCurr;
	/** W정상상품매출가격 */
	private String wnorProdSalePrc;
	/** 통화단위(W정상상품매출가격) */
	private String wnorProdSaleCurr;
	/** W이익율 */
	private String wprftRate;
	/** 소재코드->VIC마켓상품구분:PRD41 */
	private String matCd;
	/** MD유효판매코드 */
	private String mdValiSellCd;
	/** vic전용 온라인여부 */
	private String vicOnlineCd;
	
	/** 신상품장려금구분 */
	private String newProdPromoFg;
	/** 신상품출시일자 */
	private String newProdStDy;
	/** 성과초과장려금구분 */
	private String overPromoFg;
	/** 모델명 */
	private String modelNm;
	/** 친환경인증여부 */
	private String ecoYn;
	/** 친환경인증분류명 */
	private String ecoNm;
	/** 차등배송비여부 */
	private String dlvGa;
	/** 별도설치비내용 */
	private String dlvDt;
	/** 별도설치비유무 */
	private String insCo;
	/** 사이즈단위 */
	private String sizeUnit;
	/** 중분류코드 */
	private String l2Cd;
	/** 상품유형(비상품구분) */
	private String noprodFg;
	/** 상품범주(단일상품, 묶음상품) */
	private String prodAttTypFg;
	/** 총중량 */
	private String totWeight;
	/** 총중량 단위 */
	private String weightUnit;
	/** 순중량 */
	private String netWeight;	
	/** 낱개단위 */
	//private String pieceQty;
	/** 낱개단위 */
	//private String pieceUnit;
	/** 폐기물유형 */
	private String wasteFg;
	/** 플라스틱무게 */
	private String plasticWeight;
	/** 재활용유형1 */
	private String recycleFg1;
	/** 재활용유형2 */
	private String recycleFg2;
	/** 재활용유형3 */
	private String recycleFg3;
	/** 재활용무게1 */
	private String recycleWeight1;
	/** 재활용무게2 */
	private String recycleWeight2;
	/** 재활용무게3 */
	private String recycleWeight3;
	/** 유통일 */
	private String flowDd;
	/** 신상풍구분[1:순수신상품, 2:시즌상품, 3:행사상품, 4:라인확장] */
	private String zzNewProdFg;
	/*관리자여부 1:은 관리자 null or 0은 업체 */
	private String admFg;
	
	/* 상세보기에 사용하기 위해 추가 */
	private String infoGrpCd;
	private String infoGrpCd2;
	private String infoGrpCd3;
	
	/* 전상법, KC인증마크 요소별 입력값, 요소코드 */
	private String[] arrAddInfoVal;
	private String[] arrCertInfoVal;
	private String[] arrAddInfoCd;
	private String[] arrCertInfoCd;
	private String prodAddMasterCd;
	private String prodCertMasterCd;
	private String prodCertDtlCd;
	private String prodAddCd;
	private String prodCertCd;
	private String prodAddVal;
	private String prodCertVal;
		
	private String tradeType;
	private String onOffDivnCd;
	
	private String categoryNm;
	
	/* 코리안넷에서 넘어온 사업자 등록번호[코리안넷을 통해 상품등록시 사업자번호에 해당하는 협력사 리스트 구하기 위해 사용] */
	private String bmanNo;
	
	/** 상품 수정시 속성정보와 이미지 삭제 여부 */
	private String delGbn;
	
	/** 상품 복사시 변형/분석속성 정보, 이미지 정보까지 모두 복사할지 여부 */
	private String copyGbn;
	
	/** 상품 복사시 등록되어 있는 PGM_ID를 이용해서 복사할 대상 상품의 정보를 조회하기 위해 사용*/
	private String oldPgmId;
	
	/** 상품수정시 협력업체 코드의 변경유무 */
	private String upEntpGbn;
	
	
	
	private String imgNcntyn;		//온라인 이미지 첨부여부
	private String prodcommerce;	//전상법 첨부 여부
	private String ecAttrRegYn;		//EC속성 등록 유무
	private String nutAttrRegYn;			//양영성분속성 등록 유무
	private String canRegNutAttrYn;			//등록가능한 영양성분속성 존재 유무
	
	/* 코리안넷 */
	private String bman_no;
	private String[] arrBmanNo;
	
	/** 상품확정구분 추가 2016.03.17 by song min kyo */
	private String cfmFg;
	
	private String inputGrpAttrCnt;
	private String grpAttrCnt;
	
	private String varAttCnt;
	private String inputVarAttCnt;
	
	//################ 2016.04.12 2차 개발 추가건 ####################//
	private String entpInProdCd;            //협력사 내부 상품코드
	private String psbtStartDy;              //예약주문가능 시작일
	private String psbtEndDy;               //예약주문가능 종료일
	private String pickIdctDy;                //예약상품 출고지시일
	private String onlineProdTypeCd;    //상품유형
	private String hopeDeliPsbtDd;        //희망배송일 가능 여부
	private String setQty;                     //골라담기세트수량
	private String optnLoadContent;     //골라담기옵션내용
	private String staffSellYn;               //임직원 전용몰 판매 가능 여부
	private String staffDcAmt;              //임직원 할인가
	private String prodPrcMgrYn;		 //단품정보 옵션상품가격관리여부
	private String psbtRegnCd;		     //배송가능지역코드
	private String condUseYn;		         //업체상품조건부배송여부
	private String bdlDeliYn;		         //묶음배송여부
	private String sellStartDy;		         //판매시작일
	private String sellEndDy;		         //판매종료일
	private String dealProdDivnCode;	 //딜상품구분
	private String prodOptionDesc;	 	 //상품옵션설명 (설치상품,주문제작상품)
	
	//##### 2017.07.01 수입관련필드 추가 ####//
	private String hscd;
	private String decno;
	private String tarrate;
	
	//체험형 상품 여부 : N(0), Y(1)
	private boolean exprProdYn;
	//판매자 추천평
	private String sellerRecomm;
	// 수기등록 여부
	private String mnlProdReg;	

	private String newPromoVenFg; 
	
	
	private String ownStokFg;			// 총량약정구분
	private String planRecvQty;			// 총량약정량
	private String ordDeadline; 		// 발주마감기한 
	private String sType;				// 소싱유형
	private String adlYn;				// 성인상품여부

	private String themaYn; // 테마사용 여부
	private String dealViewCd; // 테마 보기방식
	private String videoUrl; // 동영상
	private String scDpYn; //검색노출여부

	//EC 상품속성타입
	private String attrPiType;
	
	//EC 상품단품별 속성
	private String[] ecProductAttr;
	
	private String[] itemCd;		// 단품 번호
	private String[] stkMgrYn;   	// 단품 재고여부
	private int[] rservStkQty;		// 단품 재고수량
	private String[] optnAmt; 		// 단품 가격
	
	private String prodDirectYn; // 단일형상품  상품속성 직접입력 여부
	private String optnDirectYn; // 단품 옵션형 상품 옵셥값 직접입력 여부
	private String[] ecProductAttrId;
	
	//영양속성
	private String[] nutCd;
	private String[] nutAmt;
	private String delNutAmtYn;
	
	/** 상품 복사시 EC 속성 저장, 복사 유무  */
	private String ecCategoryKeepYn;
	
	/** EC표준카테고리코드 */
	private String stdCatCd;
	/** EC전시카테고리코드 */
	private String dispCatCd;

	/** 24NBM_OSP_CAT OSP카테고리 */
	private String ospDispCatCd;
	
	
	// 20250327 마트차세대추가
	private String showCardNm;		//쇼카드명
	private String chanCd;			//채널코드
	
	private String snorProdPcost;	//슈퍼 정상상품원가
	private String snorProdCurr;	//슈퍼 통화단위(정상상품원가)
	private String snorProdSalePrc;	//슈퍼 정상상품매출가격
	private String snorProdSaleCurr;//슈퍼 통화단위(정상상품매출가격)
	private String sprftRate;		//슈퍼 이익률
	
	private String onorProdPcost;	//오카도 정상상품원가
	private String onorProdCurr;	//오카도 통화단위(정상상품원가)
	private String onorProdSalePrc;	//오카도 정상상품매출가격
	private String onorProdSaleCurr;//오카도 통화단위(정상상품매출가격)
	private String oprftRate;		//오카도 이익률
	
	private String esgYn;			//ESG상품구분
	private String esgEarth;		//ESG RE:EARTH 로고 사용
	
	private String sNewProdPromoFg;	//(슈퍼) 신상품장려금구분
	private String sNewProdStDy;	//(슈퍼) 신상품출시일자
	private String sOverPromoFg;	//(슈퍼) 성과초과장려금구분
	
	private String regDy;			//등록일자 (yyyymmdd)
	
	public String getsType() {
		return sType;
	}

	public void setsType(String sType) {
		this.sType = sType;
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

	public String getBman_no() {
		return bman_no;
	}

	public void setBman_no(String bman_no) {
		this.bman_no = bman_no;
	}


	public String getProdAddCd() {
		return prodAddCd;
	}

	public void setProdAddCd(String prodAddCd) {
		this.prodAddCd = prodAddCd;
	}

	public String getProdCertCd() {
		return prodCertCd;
	}

	public void setProdCertCd(String prodCertCd) {
		this.prodCertCd = prodCertCd;
	}

	public String getProdAddVal() {
		return prodAddVal;
	}

	public void setProdAddVal(String prodAddVal) {
		this.prodAddVal = prodAddVal;
	}

	public String getProdCertVal() {
		return prodCertVal;
	}

	public void setProdCertVal(String prodCertVal) {
		this.prodCertVal = prodCertVal;
	}

	public String getOnOffDivnCd() {
		return onOffDivnCd;
	}

	public void setOnOffDivnCd(String onOffDivnCd) {
		this.onOffDivnCd = onOffDivnCd;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public String getProdAddMasterCd() {
		return prodAddMasterCd;
	}

	public void setProdAddMasterCd(String prodAddMasterCd) {
		this.prodAddMasterCd = prodAddMasterCd;
	}

	

	public String getProdCertMasterCd() {
		return prodCertMasterCd;
	}
	
	public void setProdCertMasterCd(String prodCertMasterCd) {
		this.prodCertMasterCd = prodCertMasterCd;
	}
	
	public String getProdCertDtlCd() {
		return prodCertDtlCd;
	}
	
	public void setProdCertDtlCd(String prodCertDtlCd) {
		this.prodCertDtlCd = prodCertDtlCd;
	}

	public String[] getArrAddInfoVal() {
		if (this.arrAddInfoVal != null) {
			String[] ret = new String[arrAddInfoVal.length];
			for (int i = 0; i < arrAddInfoVal.length; i++) { 
				ret[i] = this.arrAddInfoVal[i]; 
			}
			return ret;
		} else {
			return null;
		}		
	}
	
	public void setArrAddInfoVal(String[] arrAddInfoVal) {
		 if (arrAddInfoVal != null) {
			 this.arrAddInfoVal = new String[arrAddInfoVal.length];			 
			 for (int i = 0; i < arrAddInfoVal.length; ++i) {
				 this.arrAddInfoVal[i] = arrAddInfoVal[i];
			 }
		 } else {
			 this.arrAddInfoVal = null;
		 }

	}
	
	
	public String[] getArrCertInfoVal() {
		if (this.arrCertInfoVal != null) {
			String[] ret = new String[arrCertInfoVal.length];
			for (int i = 0; i < arrCertInfoVal.length; i++) { 
				ret[i] = this.arrCertInfoVal[i]; 
			}
			return ret;
		} else {
			return null;
		}		
	}
	
	public void setArrCertInfoVal(String[] arrCertInfoVal) {
		 if (arrCertInfoVal != null) {
			 this.arrCertInfoVal = new String[arrCertInfoVal.length];			 
			 for (int i = 0; i < arrCertInfoVal.length; ++i) {
				 this.arrCertInfoVal[i] = arrCertInfoVal[i];
			 }
		 } else {
			 this.arrCertInfoVal = null;
		 }

	}
	
	
	public String[] getArrAddInfoCd() {
		if (this.arrAddInfoCd != null) {
			String[] ret = new String[arrAddInfoCd.length];
			for (int i = 0; i < arrAddInfoCd.length; i++) { 
				ret[i] = this.arrAddInfoCd[i]; 
			}
			return ret;
		} else {
			return null;
		}		
	}
	
	public void setArrAddInfoCd(String[] arrAddInfoCd) {
		 if (arrAddInfoCd != null) {
			 this.arrAddInfoCd = new String[arrAddInfoCd.length];			 
			 for (int i = 0; i < arrAddInfoCd.length; ++i) {
				 this.arrAddInfoCd[i] = arrAddInfoCd[i];
			 }
		 } else {
			 this.arrAddInfoCd = null;
		 }

	}	
	
	public String[] getArrCertInfoCd() {
		if (this.arrCertInfoCd != null) {
			String[] ret = new String[arrCertInfoCd.length];
			for (int i = 0; i < arrCertInfoCd.length; i++) { 
				ret[i] = this.arrCertInfoCd[i]; 
			}
			return ret;
		} else {
			return null;
		}		
	}
	
	public void setArrCertInfoCd(String[] arrCertInfoCd) {
		 if (arrCertInfoCd != null) {
			 this.arrCertInfoCd = new String[arrCertInfoCd.length];			 
			 for (int i = 0; i < arrCertInfoCd.length; ++i) {
				 this.arrCertInfoCd[i] = arrCertInfoCd[i];
			 }
		 } else {
			 this.arrCertInfoCd = null;
		 }

	}
	
	public String getIuGbn() {
		return iuGbn;
	}
	public void setIuGbn(String iuGbn) {
		this.iuGbn = iuGbn;
	}
	public String getPgmId() {
		return pgmId;
	}
	public void setPgmId(String pgmId) {
		this.pgmId = pgmId;
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
	public String getGrpCd() {
		return grpCd;
	}
	public void setGrpCd(String grpCd) {
		this.grpCd = grpCd;
	}
	public String getProdNm() {
		return prodNm;
	}
	public void setProdNm(String prodNm) {
		this.prodNm = prodNm;
	}
	public String getProdEnNm() {
		return prodEnNm;
	}
	public void setProdEnNm(String prodEnNm) {
		this.prodEnNm = prodEnNm;
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
	public String getOnlineProdNm() {
		return onlineProdNm;
	}
	public void setOnlineProdNm(String onlineProdNm) {
		this.onlineProdNm = onlineProdNm;
	}
	public String getOnlineRepProdCd() {
		return onlineRepProdCd;
	}
	public void setOnlineRepProdCd(String onlineRepProdCd) {
		this.onlineRepProdCd = onlineRepProdCd;
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
	public String getNorProdCurr() {
		return norProdCurr;
	}
	public void setNorProdCurr(String norProdCurr) {
		this.norProdCurr = norProdCurr;
	}
	public String getNorProdSalePrc() {
		return norProdSalePrc;
	}
	public void setNorProdSalePrc(String norProdSalePrc) {
		this.norProdSalePrc = norProdSalePrc;
	}
	public String getNorProdSaleCurr() {
		return norProdSaleCurr;
	}
	public void setNorProdSaleCurr(String norProdSaleCurr) {
		this.norProdSaleCurr = norProdSaleCurr;
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
	public String getEvtProdSellCurr() {
		return evtProdSellCurr;
	}
	public void setEvtProdSellCurr(String evtProdSellCurr) {
		this.evtProdSellCurr = evtProdSellCurr;
	}
	public String getSellPrc() {
		return sellPrc;
	}
	public void setSellPrc(String sellPrc) {
		this.sellPrc = sellPrc;
	}
	public String getSellCurr() {
		return sellCurr;
	}
	public void setSellCurr(String sellCurr) {
		this.sellCurr = sellCurr;
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
	public String getHomeCd() {
		return homeCd;
	}
	public void setHomeCd(String homeCd) {
		this.homeCd = homeCd;
	}
	public String getPurInrcntQty() {
		return purInrcntQty;
	}
	public void setPurInrcntQty(String purInrcntQty) {
		this.purInrcntQty = purInrcntQty;
	}
	public String getPrftRate() {
		return prftRate;
	}
	public void setPrftRate(String prftRate) {
		this.prftRate = prftRate;
	}
	public String getPurUnitCd() {
		return purUnitCd;
	}
	public void setPurUnitCd(String purUnitCd) {
		this.purUnitCd = purUnitCd;
	}
	public String getPromoAmtDivnCd() {
		return promoAmtDivnCd;
	}
	public void setPromoAmtDivnCd(String promoAmtDivnCd) {
		this.promoAmtDivnCd = promoAmtDivnCd;
	}
	public String getNewProdFirstPurDivnCd() {
		return newProdFirstPurDivnCd;
	}
	public void setNewProdFirstPurDivnCd(String newProdFirstPurDivnCd) {
		this.newProdFirstPurDivnCd = newProdFirstPurDivnCd;
	}
	public String getNpurBuyPsbtDivnCd() {
		return npurBuyPsbtDivnCd;
	}
	public void setNpurBuyPsbtDivnCd(String npurBuyPsbtDivnCd) {
		this.npurBuyPsbtDivnCd = npurBuyPsbtDivnCd;
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
	public String getNewProdGenDivnCd() {
		return newProdGenDivnCd;
	}
	public void setNewProdGenDivnCd(String newProdGenDivnCd) {
		this.newProdGenDivnCd = newProdGenDivnCd;
	}
	public String getTrdTypeDivnCd() {
		return trdTypeDivnCd;
	}
	public void setTrdTypeDivnCd(String trdTypeDivnCd) {
		this.trdTypeDivnCd = trdTypeDivnCd;
	}
	public String getBarcdVerifyDocTf() {
		return barcdVerifyDocTf;
	}
	public void setBarcdVerifyDocTf(String barcdVerifyDocTf) {
		this.barcdVerifyDocTf = barcdVerifyDocTf;
	}
	public String getMixYn() {
		return mixYn;
	}
	public void setMixYn(String mixYn) {
		this.mixYn = mixYn;
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
	public String getTmprtDivnCd() {
		return tmprtDivnCd;
	}
	public void setTmprtDivnCd(String tmprtDivnCd) {
		this.tmprtDivnCd = tmprtDivnCd;
	}
	public String getFlowDdMgrCd() {
		return flowDdMgrCd;
	}
	public void setFlowDdMgrCd(String flowDdMgrCd) {
		this.flowDdMgrCd = flowDdMgrCd;
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
	public String getMdSendDivnCd() {
		return mdSendDivnCd;
	}
	public void setMdSendDivnCd(String mdSendDivnCd) {
		this.mdSendDivnCd = mdSendDivnCd;
	}
	public String getTotInspYn() {
		return totInspYn;
	}
	public void setTotInspYn(String totInspYn) {
		this.totInspYn = totInspYn;
	}
	public String getProductDy() {
		return productDy;
	}
	public void setProductDy(String productDy) {
		this.productDy = productDy;
	}
	public String getProdImgId() {
		return prodImgId;
	}
	public void setProdImgId(String prodImgId) {
		this.prodImgId = prodImgId;
	}
	public String getNfomlVariationDesc() {
		return nfomlVariationDesc;
	}
	public void setNfomlVariationDesc(String nfomlVariationDesc) {
		this.nfomlVariationDesc = nfomlVariationDesc;
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
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getSocialProdDeliCd() {
		return socialProdDeliCd;
	}
	public void setSocialProdDeliCd(String socialProdDeliCd) {
		this.socialProdDeliCd = socialProdDeliCd;
	}
	public String getKanProdCd() {
		return kanProdCd;
	}
	public void setKanProdCd(String kanProdCd) {
		this.kanProdCd = kanProdCd;
	}
	public String getUnspscCd() {
		return unspscCd;
	}
	public void setUnspscCd(String unspscCd) {
		this.unspscCd = unspscCd;
	}
	public String getGtinCd() {
		return gtinCd;
	}
	public void setGtinCd(String gtinCd) {
		this.gtinCd = gtinCd;
	}
	public String getImgNcnt() {
		return imgNcnt;
	}
	public void setImgNcnt(String imgNcnt) {
		this.imgNcnt = imgNcnt;
	}
	public String getWideImgNcnt() {
		return wideImgNcnt;
	}
	public void setWideImgNcnt(String wideImgNcnt) {
		this.wideImgNcnt = wideImgNcnt;
	}
	public String getWideImgNcntYn() {
		return wideImgNcntYn;
	}
	public void setWideImgNcntYn(String wideImgNcntYn) {
		this.wideImgNcntYn = wideImgNcntYn;
	}
	public String getWnorProdPcost() {
		return wnorProdPcost;
	}
	public void setWnorProdPcost(String wnorProdPcost) {
		this.wnorProdPcost = wnorProdPcost;
	}
	public String getWnorProdCurr() {
		return wnorProdCurr;
	}
	public void setWnorProdCurr(String wnorProdCurr) {
		this.wnorProdCurr = wnorProdCurr;
	}
	public String getWnorProdSalePrc() {
		return wnorProdSalePrc;
	}
	public void setWnorProdSalePrc(String wnorProdSalePrc) {
		this.wnorProdSalePrc = wnorProdSalePrc;
	}
	public String getWnorProdSaleCurr() {
		return wnorProdSaleCurr;
	}
	public void setWnorProdSaleCurr(String wnorProdSaleCurr) {
		this.wnorProdSaleCurr = wnorProdSaleCurr;
	}
	public String getWprftRate() {
		return wprftRate;
	}
	public void setWprftRate(String wprftRate) {
		this.wprftRate = wprftRate;
	}
	public String getMatCd() {
		return matCd;
	}
	public void setMatCd(String matCd) {
		this.matCd = matCd;
	}
	public String getMdValiSellCd() {
		return mdValiSellCd;
	}
	public void setMdValiSellCd(String mdValiSellCd) {
		this.mdValiSellCd = mdValiSellCd;
	}
	public String getVicOnlineCd() {
		return vicOnlineCd;
	}
	public void setVicOnlineCd(String vicOnlineCd) {
		this.vicOnlineCd = vicOnlineCd;
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
//	public String getPieceQty() {
//		return pieceQty;
//	}
//	public void setPieceQty(String pieceQty) {
//		this.pieceQty = pieceQty;
//	}
//	public String getPieceUnit() {
//		return pieceUnit;
//	}
//	public void setPieceUnit(String pieceUnit) {
//		this.pieceUnit = pieceUnit;
//	}
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
	
	public String getInfoGrpCd3() {
		return infoGrpCd3;
	}

	public void setInfoGrpCd3(String infoGrpCd3) {
		this.infoGrpCd3 = infoGrpCd3;
	}

	public String getCategoryNm() {
		return categoryNm;
	}

	public void setCategoryNm(String categoryNm) {
		this.categoryNm = categoryNm;
	}

	public String getDelGbn() {
		return delGbn;
	}

	public void setDelGbn(String delGbn) {
		this.delGbn = delGbn;
	}

	public String getCopyGbn() {
		return copyGbn;
	}

	public void setCopyGbn(String copyGbn) {
		this.copyGbn = copyGbn;
	}

	public String getOldPgmId() {
		return oldPgmId;
	}

	public void setOldPgmId(String oldPgmId) {
		this.oldPgmId = oldPgmId;
	}

	public String getUpEntpGbn() {
		return upEntpGbn;
	}

	public void setUpEntpGbn(String upEntpGbn) {
		this.upEntpGbn = upEntpGbn;
	}

	public String getImgNcntyn() {
		return imgNcntyn;
	}

	public void setImgNcntyn(String imgNcntyn) {
		this.imgNcntyn = imgNcntyn;
	}

	public String getProdcommerce() {
		return prodcommerce;
	}

	public void setProdcommerce(String prodcommerce) {
		this.prodcommerce = prodcommerce;
	}

	public String getEcAttrRegYn() {
		return ecAttrRegYn;
	}
	
	public void setEcAttrRegYn(String ecAttrRegYn) {
		this.ecAttrRegYn = ecAttrRegYn;
	}
	
	public String getNutAttrRegYn() {
		return nutAttrRegYn;
	}
	
	public void setNutAttrRegYn(String nutAttrRegYn) {
		this.nutAttrRegYn = nutAttrRegYn;
	}
	
	public String getCanRegNutAttrYn() {
		return canRegNutAttrYn;
	}
	
	public void setCanRegNutAttrYn(String canRegNutAttrYn) {
		this.canRegNutAttrYn = canRegNutAttrYn;
	}
	
	public String getZzNewProdFg() {
		return zzNewProdFg;
	}

	public void setZzNewProdFg(String zzNewProdFg) {
		this.zzNewProdFg = zzNewProdFg;
	}

	public String getCfmFg() {
		return cfmFg;
	}

	public void setCfmFg(String cfmFg) {
		this.cfmFg = cfmFg;
	}
	
	public String getBmanNo() {
		return bmanNo;
	}

	public void setBmanNo(String bmanNo) {
		this.bmanNo = bmanNo;
	}

	public String[] getArrBmanNo() {
		if (this.arrBmanNo != null) {
			String[] ret = new String[arrBmanNo.length];
			for (int i = 0; i < arrBmanNo.length; i++) { 
				ret[i] = this.arrBmanNo[i]; 
			}
			return ret;
		} else {
			return null;
		}		
	}

	public void setArrBmanNo(String[] arrBmanNo) {
		 if (arrBmanNo != null) {
			 this.arrBmanNo = new String[arrBmanNo.length];			 
			 for (int i = 0; i < arrBmanNo.length; ++i) {
				 this.arrBmanNo[i] = arrBmanNo[i];
			 }
		 } else {
			 this.arrBmanNo = null;
		 }
	}

	public String getInputGrpAttrCnt() {
		return inputGrpAttrCnt;
	}

	public void setInputGrpAttrCnt(String inputGrpAttrCnt) {
		this.inputGrpAttrCnt = inputGrpAttrCnt;
	}

	public String getGrpAttrCnt() {
		return grpAttrCnt;
	}

	public void setGrpAttrCnt(String grpAttrCnt) {
		this.grpAttrCnt = grpAttrCnt;
	}

	public String getVarAttCnt() {
		return varAttCnt;
	}

	public void setVarAttCnt(String varAttCnt) {
		this.varAttCnt = varAttCnt;
	}

	public String getInputVarAttCnt() {
		return inputVarAttCnt;
	}

	public void setInputVarAttCnt(String inputVarAttCnt) {
		this.inputVarAttCnt = inputVarAttCnt;
	}
	
	public String getAdmFg() {
		return admFg;
	}
	public void setAdmFg(String admFg) {
		this.admFg = admFg;
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

	public String getOptnLoadContent() {
		return optnLoadContent;
	}

	public void setOptnLoadContent(String optnLoadContent) {
		this.optnLoadContent = optnLoadContent;
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

	public String getProdPrcMgrYn() {
		return prodPrcMgrYn;
	}

	public void setProdPrcMgrYn(String prodPrcMgrYn) {
		this.prodPrcMgrYn = prodPrcMgrYn;
	}

	public String getPsbtRegnCd() {
		return psbtRegnCd;
	}

	public void setPsbtRegnCd(String psbtRegnCd) {
		this.psbtRegnCd = psbtRegnCd;
	}

	public String getCondUseYn() {
		return condUseYn;
	}

	public void setCondUseYn(String condUseYn) {
		this.condUseYn = condUseYn;
	}

	public String getBdlDeliYn() {
		return bdlDeliYn;
	}

	public void setBdlDeliYn(String bdlDeliYn) {
		this.bdlDeliYn = bdlDeliYn;
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

	public String getHscd() {
		return hscd;
	}

	public void setHscd(String hscd) {
		this.hscd = hscd;
	}

	public String getDecno() {
		return decno;
	}

	public void setDecno(String decno) {
		this.decno = decno;
	}

	public String getTarrate() {
		return tarrate;
	}

	public void setTarrate(String tarrate) {
		this.tarrate = tarrate;
	}	
	
	public String getNetWeight() {
		return netWeight;
	}

	public void setNetWeight(String netWeight) {
		this.netWeight = netWeight;
	}
	
	                     
	/* ****************************** */
	//20180821 - 상품키워드 입력 기능 추가
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
		
		if(getSearchKywrd() != null && getSearchKywrd().length > 0){
			int arrayLength = getSearchKywrd().length;
			
			for(int i=0; i < arrayLength; i++) {
				PEDMPRO0011VO tmpKeyword = new PEDMPRO0011VO();
				
				// 전산정보팀 이상구 수정 2018-08-16 
				//tmpKeyword.setPgmId(getNewProductCode());
				tmpKeyword.setPgmId(getPgmId());				
				tmpKeyword.setSeq(getSeq()[i]);
				tmpKeyword.setSearchKywrd(getSearchKywrd()[i]);				
				// 전산정보팀 이상구 수정 2018-08-16 				
				//tmpKeyword.setRegId(getEntpCode());
				tmpKeyword.setRegId(getEntpCd());				
				keywordList.add(tmpKeyword);
			}
		}
		
		setKeywordList(keywordList);
	}
	//20180821 - 상품키워드 입력 기능 추가
	/* ****************************** */

	public String getOwnStokFg() {
		return ownStokFg;
	}

	public void setOwnStokFg(String ownStokFg) {
		this.ownStokFg = ownStokFg;
	}

	public String getPlanRecvQty() {
		return planRecvQty;
	}

	public void setPlanRecvQty(String planRecvQty) {
		this.planRecvQty = planRecvQty;
	}

	public String getOrdDeadline() {
		return ordDeadline;
	}

	public void setOrdDeadline(String ordDeadline) {
		this.ordDeadline = ordDeadline;
	}

	public String getMnlProdReg() {
		return mnlProdReg;
	}

	public void setMnlProdReg(String mnlProdReg) {
		this.mnlProdReg = mnlProdReg;
	}

	public String getNewPromoVenFg() {
		return newPromoVenFg;
	}

	public void setNewPromoVenFg(String newPromoVenFg) {
		this.newPromoVenFg = newPromoVenFg;
	}

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

	public String getOspDispCatCd() {
		return ospDispCatCd;
	}

	public void setOspDispCatCd(String ospDispCatCd) {
		this.ospDispCatCd = ospDispCatCd;
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
	
	public String[] getItemCd() {
		return itemCd;
	}
	
	public void setItemCd(String[] itemCd) {
		this.itemCd = itemCd;
	}
	/*
	 * private String[] stkMgrYn; // 단품 재고여부 private String[] rservStkQty; // 단품
	 * 재고수량 private String[] optnAmtID; // 단품 가격
	 */
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
	
	public String[] getOptnAmt() {
		return optnAmt;
	}
	
	public void setOptnAmt(String[] optnAmt) {
		this.optnAmt = optnAmt;
	}
	
	public String[] getNutCd() {
		return nutCd;
	}
	
	public void setNutCd(String[] nutCd) {
		this.nutCd = nutCd;
	}
	
	public String[] getNutAmt() {
		return nutAmt;
	}
	
	public void setNutAmt(String[] nutAmt) {
		this.nutAmt = nutAmt;
	}
	
	public String getEcCategoryKeepYn() {
		return ecCategoryKeepYn;
	}
	
	public void setEcCategoryKeepYn(String ecCategoryKeepYn) {
		this.ecCategoryKeepYn = ecCategoryKeepYn;
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
	
	
	public String getDelNutAmtYn() {
		return delNutAmtYn;
	}

	public void setDelNutAmtYn(String delNutAmtYn) {
		this.delNutAmtYn = delNutAmtYn;
	}

	public String getShowCardNm() {
		return showCardNm;
	}

	public void setShowCardNm(String showCardNm) {
		this.showCardNm = showCardNm;
	}

	public String getChanCd() {
		return chanCd;
	}

	public void setChanCd(String chanCd) {
		this.chanCd = chanCd;
	}

	public String getSnorProdPcost() {
		return snorProdPcost;
	}

	public void setSnorProdPcost(String snorProdPcost) {
		this.snorProdPcost = snorProdPcost;
	}

	public String getSnorProdCurr() {
		return snorProdCurr;
	}

	public void setSnorProdCurr(String snorProdCurr) {
		this.snorProdCurr = snorProdCurr;
	}

	public String getSnorProdSalePrc() {
		return snorProdSalePrc;
	}

	public void setSnorProdSalePrc(String snorProdSalePrc) {
		this.snorProdSalePrc = snorProdSalePrc;
	}

	public String getSnorProdSaleCurr() {
		return snorProdSaleCurr;
	}

	public void setSnorProdSaleCurr(String snorProdSaleCurr) {
		this.snorProdSaleCurr = snorProdSaleCurr;
	}

	public String getSprftRate() {
		return sprftRate;
	}

	public void setSprftRate(String sprftRate) {
		this.sprftRate = sprftRate;
	}

	public String getOnorProdPcost() {
		return onorProdPcost;
	}

	public void setOnorProdPcost(String onorProdPcost) {
		this.onorProdPcost = onorProdPcost;
	}

	public String getOnorProdCurr() {
		return onorProdCurr;
	}

	public void setOnorProdCurr(String onorProdCurr) {
		this.onorProdCurr = onorProdCurr;
	}

	public String getOnorProdSalePrc() {
		return onorProdSalePrc;
	}

	public void setOnorProdSalePrc(String onorProdSalePrc) {
		this.onorProdSalePrc = onorProdSalePrc;
	}

	public String getOnorProdSaleCurr() {
		return onorProdSaleCurr;
	}

	public void setOnorProdSaleCurr(String onorProdSaleCurr) {
		this.onorProdSaleCurr = onorProdSaleCurr;
	}

	public String getOprftRate() {
		return oprftRate;
	}

	public void setOprftRate(String oprftRate) {
		this.oprftRate = oprftRate;
	}

	public String getEsgYn() {
		return esgYn;
	}

	public void setEsgYn(String esgYn) {
		this.esgYn = esgYn;
	}

	public String getEsgEarth() {
		return esgEarth;
	}

	public void setEsgEarth(String esgEarth) {
		this.esgEarth = esgEarth;
	}

	public String getsNewProdPromoFg() {
		return sNewProdPromoFg;
	}

	public void setsNewProdPromoFg(String sNewProdPromoFg) {
		this.sNewProdPromoFg = sNewProdPromoFg;
	}

	public String getsNewProdStDy() {
		return sNewProdStDy;
	}

	public void setsNewProdStDy(String sNewProdStDy) {
		this.sNewProdStDy = sNewProdStDy;
	}

	public String getsOverPromoFg() {
		return sOverPromoFg;
	}

	public void setsOverPromoFg(String sOverPromoFg) {
		this.sOverPromoFg = sOverPromoFg;
	}

	public String getRegDy() {
		return regDy;
	}

	public void setRegDy(String regDy) {
		this.regDy = regDy;
	}
	
	public String getRegDyFmt() {
		if(regDy != null && !"".equals(regDy) && DateUtil.isDate(regDy, "yyyyMMdd")) {
			return DateUtil.string2String(regDy, "yyyyMMdd", DateUtil.DATE_PATTERN);
		}
		return "";
	}
}
