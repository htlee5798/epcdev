package com.lottemart.epc.edi.product.model;

import java.io.Serializable;
import java.util.List;

/**
 * @Class Name : NEDMPRO0040VO
 * @Description : 임시보관함 리스트 VO
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>O
 *   
 *   수정일      			수정자           		수정내용
 *  -------    		--------    	---------------------------
 * 	2015.11.27 	SONG MIN KYO	최초생성
 * </pre>
 */
public class NEDMPRO0040VO implements Serializable {
			
	private static final long serialVersionUID = -4439443437647514819L;
	
	/* 검색 PARAMETER */
	private String srchProductDivnCode;	//상품구분[1:규격private String  5:패션]
	private String srchEntpCode;		//협력업체
	private String srchOnOffDivnCode;	//온오프구분[1:온라인private String  0:온오프]
	private String srchStartDt;			//검색시작일자
	private String srchEndDt;			//검색종료일자
	private String admFg;				//관리자여부	
	private String uAdmFg;				//화면 관리자여부	
	
	
	private String[] venCds;			//협력업체코드 리스트
	
	/* LIST column */
	private String entpCd;					//업체코드
	private String regDate;					//등록일자
	private String sellCd;						//판매코드
	private String prodNm;					//상품명
	private String prodDivnCd;				//상품구분코드[1:규격private String  5:패션]
	private String purInrcntQty;			//발주입수수량
	private String prodStandardNm;		//상품규격명
	private String onlineProdNm;			//온라인상품명
	private String norProdPcost;			//정상상품원가
	private String norProdSalePrc;			//정상상품매출가격
	private String prftRate;					//이익율
	private String logiPgmId;				//물류바코드 테이블 상품코드
	private String pgmId;						//상품테이블 상품코드
	private String onoffDivnCd;			//온오프구분코드 [1:온라인private String  0:온오프]
	private String brandCd;					//브랜드코드
	private String brandNm;					//브랜드명
	private String makerCd;					//메이커코드
	private String makerNm;					//메이커명
	private String prodImgId;				//상품 이미지 아이디
	private String imgNcnt;					//온라인이미지개수
    private String imgNcntyn;				//온라인이미지여부
	private String wideImgNcnt;					//와이드 온라인이미지개수
	private String wideImgNcntyn;				//와이드 온라인이미지여부
    private String l1Cd;						//대분류
    private String l3Cd;						//소분류	
    private String prodcommerce;			//전상법 상태??  
    private String colorSizeState;			//단품코드
    private String prodAttTypFg;			//상품범주(00:단일상품, 01:묶음상품)
    private String varAttCnt;				//소분류에 매핑되어 있는 변형속성 카운트
    private String inputVarAttCnt;			//입력된 변형속성 카운트
	private String inputVarAttCnt001;	//입력된 001변형속성 카운트 2016.08.29
	private String grpCd;						//그룹분류	 2016.08.29
    private String mixYn;						//상품 추가정보의 혼재여부
    private String dealRepProdYn; 		//딜상품여부
    private String ctpdYn;					//구성품여부
    private String staffSellYn;				//임직원몰판매가능여부
    private String matCd;						//vic여부
    private String vicOnlineCd;				//vic전용 온라인여부
    private String prodDescYn;				//상품대표이미지 여부
    private String nochDeliYn;				//배송설정 여부    
	private String onlineProdTypeCd; 		//온라인상품유형코드
	private String ecAttrRegYn;				//EC속성 등록 유무
	private String nutAttrRegYn;			//양영성분속성 등록 유무
	private String canRegNutAttrYn;			//등록가능한 영양성분속성 존재 유무
	private String ctrTypeDivnCd;			//센터유형
	private String tmprtDivnCd;			    //온도구분
	private String pbVenFg;			        //PB 파트너사 유무
	private String prodTypeDivnCd;			//상품 유형
	
	/* 확정처리 - 선택된 상품 상품코드private String  온오프구분private String  이미지아이디 */
    private String[] arrPgmId;			//상품코드
    private String[] arrOnOffGubun;		//온오프구분
    private String[] arrImgId;			//이미지아이디
    private String[] arrTrdTypeDivnCd;	//거래형태구분[1:직매입, 2:특약1, 4:특약2]
    private String[] arrStaffSellYn;   //임직원몰판매가능여부
    private String[] arrMatCd;   		//vic전용 온라인여부    
	private String[] arrVicOnlineCd;   //vic전용 온라인여부
	private String[] arrAdmFg;   //vic전용 온라인여부 
 
	
	
	/* 확정처리 - RFC Call Proxy명 */    
	private String[] arrProxyNm;				//RFC CALL proxy명
	
	/* RFC 콜 요청구분 */
	private String gbn;
	
	/* 2016.05.09 추가 by song min kyo [임시보관함에서 거래유형이 직매입, 특약1 일 경우는 분석속성 입력여부를 확인하고 확정이 되게끔 처리하기 위해 사용] */
	private String trdTypeDivnCd;
	private String inputGrpAttrCnt;
    private String grpAttrCnt;
    
    //20180626 - EDI 임시보관함 상품 확정 시 배송정책을 설정하지 않을 경우 배송정책 설정하도록 유효성 추가
    private String entpCondUseYn;			//업체조건사용여부
    
    private String pogImgYn;			//오프라인 POG 이미지 등록여부체크
    private String esgYn;				//ESG 상품구분
    private String esgItmRegYn;			//ESG 적용 시, 인증정보 등록여부체크
    private String esgDtFgChk;			//ESG인증기간 필수등록여부체크
    private String esgDtValidChk;		//ESG인증기간 유효성체크
    
    private String chanCd;				//채널코드
    private String[] arrMaxxOnlyYn;  	//MAXX 단일채널여부
    
    private String maxxOnlyYn;			//MAXX 단일채널여부
    private String superOnlyYn;			//SUPER 단일채널여부
    private List<NEDMPRO0040VO> prodArr;
    private List<NEDMPRO0040VO> bosProdList;	//BOS 전송대상상품리스트
    private List<NEDMPRO0040VO> erpProdList;	//ERP 전송대상상품리스트
    private String sendGbn;	//전송대상구분
    
    private String bosSendDy;	//BOS 발송일자
    private String autoSendFg;	//ERP 자동발송여부
    private String bosCfrmEndDy;//BOS 승인마감일
    private String bosCfrmYn;	//BOS 승인여부
    private String bosCfrmExpYn;//BOS 승인마감일 만료여부
    
    private String batchYn;		//스케쥴러로 동작하는지 여부
    
    
	private String wnorProdPcost;		//정상상품원가 (MAXX)
	private String wnorProdSalePrc;		//정상상품매가 (MAXX)
	private String wprftRate;			//이익률 (MAXX)
	private String snorProdPcost;		//정상상품원가 (슈퍼)
	private String snorProdSalePrc;		//정상상품매가 (슈퍼)
	private String sprftRate;			//이익률 (슈퍼)
	private String onorProdPcost;		//정상상품원가 (오카도)
	private String onorProdSalePrc;		//정상상품매가 (오카도)
	private String oprftRate;			//이익률 (오카도)
	
	private String sNewProdPromoFg;	//(슈퍼) 신상품장려금구분
	private String sNewProdStDy;	//(슈퍼) 신상품출시일자
	private String sOverPromoFg;	//(슈퍼) 성과초과장려금구분
	
	private String bosTgYn;			//BOS 승인대상여부
	
	//파일 SFTP 전송정보
	private String sendYn;			//전송여부
	private String sendDate;		//전송일시
	private String sendPath;		//전송경로
	
    
	public String getSrchProductDivnCode() {
		return srchProductDivnCode;
	}
	public void setSrchProductDivnCode(String srchProductDivnCode) {
		this.srchProductDivnCode = srchProductDivnCode;
	}
	public String getSrchEntpCode() {
		return srchEntpCode;
	}
	public void setSrchEntpCode(String srchEntpCode) {
		this.srchEntpCode = srchEntpCode;
	}
	public String getSrchOnOffDivnCode() {
		return srchOnOffDivnCode;
	}
	public void setSrchOnOffDivnCode(String srchOnOffDivnCode) {
		this.srchOnOffDivnCode = srchOnOffDivnCode;
	}
	public String getSrchStartDt() {
		return srchStartDt;
	}
	public void setSrchStartDt(String srchStartDt) {
		this.srchStartDt = srchStartDt;
	}
	public String getSrchEndDt() {
		return srchEndDt;
	}
	public void setSrchEndDt(String srchEndDt) {
		this.srchEndDt = srchEndDt;
	}
	public String getEntpCd() {
		return entpCd;
	}
	public void setEntpCd(String entpCd) {
		this.entpCd = entpCd;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getSellCd() {
		return sellCd;
	}
	public void setSellCd(String sellCd) {
		this.sellCd = sellCd;
	}
	public String getProdNm() {
		return prodNm;
	}
	public void setProdNm(String prodNm) {
		this.prodNm = prodNm;
	}
	public String getProdDivnCd() {
		return prodDivnCd;
	}
	public void setProdDivnCd(String prodDivnCd) {
		this.prodDivnCd = prodDivnCd;
	}
	public String getPurInrcntQty() {
		return purInrcntQty;
	}
	public void setPurInrcntQty(String purInrcntQty) {
		this.purInrcntQty = purInrcntQty;
	}
	public String getProdStandardNm() {
		return prodStandardNm;
	}
	public void setProdStandardNm(String prodStandardNm) {
		this.prodStandardNm = prodStandardNm;
	}
	public String getOnlineProdNm() {
		return onlineProdNm;
	}
	public void setOnlineProdNm(String onlineProdNm) {
		this.onlineProdNm = onlineProdNm;
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
	public String getPrftRate() {
		return prftRate;
	}
	public void setPrftRate(String prftRate) {
		this.prftRate = prftRate;
	}
	public String getLogiPgmId() {
		return logiPgmId;
	}
	public void setLogiPgmId(String logiPgmId) {
		this.logiPgmId = logiPgmId;
	}
	public String getPgmId() {
		return pgmId;
	}
	public void setPgmId(String pgmId) {
		this.pgmId = pgmId;
	}
	public String getOnoffDivnCd() {
		return onoffDivnCd;
	}
	public void setOnoffDivnCd(String onoffDivnCd) {
		this.onoffDivnCd = onoffDivnCd;
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
	public String getProdImgId() {
		return prodImgId;
	}
	public void setProdImgId(String prodImgId) {
		this.prodImgId = prodImgId;
	}
	public String getImgNcnt() {
		return imgNcnt;
	}
	public void setImgNcnt(String imgNcnt) {
		this.imgNcnt = imgNcnt;
	}
	public String getImgNcntyn() {
		return imgNcntyn;
	}
	public void setImgNcntyn(String imgNcntyn) {
		this.imgNcntyn = imgNcntyn;
	}
	public String getWideImgNcnt() {
		return wideImgNcnt;
	}
	public void setWideImgNcnt(String wideImgNcnt) {
		this.wideImgNcnt = wideImgNcnt;
	}
	public String getWideImgNcntyn() {
		return wideImgNcntyn;
	}
	public void setWideImgNcntyn(String wideImgNcntyn) {
		this.wideImgNcntyn = wideImgNcntyn;
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
	public String getProdcommerce() {
		return prodcommerce;
	}
	public void setProdcommerce(String prodcommerce) {
		this.prodcommerce = prodcommerce;
	}	
	public String getNochDeliYn() {
		return nochDeliYn;
	}
	public void setNochDeliYn(String nochDeliYn) {
		this.nochDeliYn = nochDeliYn;
	}
	public String getOnlineProdTypeCd() {
		return onlineProdTypeCd;
	}
	public void setOnlineProdTypeCd(String onlineProdTypeCd) {
		this.onlineProdTypeCd = onlineProdTypeCd;
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
	public String getCtrTypeDivnCd() { return ctrTypeDivnCd;}
	public void setCtrTypeDivnCd(String ctrTypeDivnCd) {
		this.ctrTypeDivnCd = ctrTypeDivnCd;
	}
	public String getTmprtDivnCd() { return tmprtDivnCd;}
	public void setTmprtDivnCd(String tmprtDivnCd) {
		this.tmprtDivnCd = tmprtDivnCd;
	}
	public String getPbVenFg() { return pbVenFg;}
	public void setPbVenFg(String pbVenFg) {
		this.pbVenFg = pbVenFg;
	}
	public String getProdTypeDivnCd() { return prodTypeDivnCd;}
	public void setProdTypeDivnCd(String prodTypeDivnCd) {
		this.prodTypeDivnCd = prodTypeDivnCd;
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
	public String[] getArrPgmId() {
		if (this.arrPgmId != null) {
			String[] ret = new String[arrPgmId.length];
			for (int i = 0; i < arrPgmId.length; i++) { 
				ret[i] = this.arrPgmId[i]; 
			}
			return ret;
		} else {
			return null;
		}		
	}
	public void setArrPgmId(String[] arrPgmId) {
		if (arrPgmId != null) {
			 this.arrPgmId = new String[arrPgmId.length];			 
			 for (int i = 0; i < arrPgmId.length; ++i) {
				 this.arrPgmId[i] = arrPgmId[i];
			 }
		 } else {
			 this.arrPgmId = null;
		 }
	}
	
	
	public String[] getArrImgId() {
		if (this.arrImgId != null) {
			String[] ret = new String[arrImgId.length];
			for (int i = 0; i < arrImgId.length; i++) { 
				ret[i] = this.arrImgId[i]; 
			}
			return ret;
		} else {
			return null;
		}	
	}
	public void setArrImgId(String[] arrImgId) {
		if (arrImgId != null) {
			 this.arrImgId = new String[arrImgId.length];			 
			 for (int i = 0; i < arrImgId.length; ++i) {
				 this.arrImgId[i] = arrImgId[i];
			 }
		 } else {
			 this.arrImgId = null;
		 }
	}
	
	public String[] getArrOnOffGubun() {
		if (this.arrOnOffGubun != null) {
			String[] ret = new String[arrOnOffGubun.length];
			for (int i = 0; i < arrOnOffGubun.length; i++) { 
				ret[i] = this.arrOnOffGubun[i]; 
			}
			return ret;
		} else {
			return null;
		}	
	}
	public void setArrOnOffGubun(String[] arrOnOffGubun) {
		if (arrOnOffGubun != null) {
			 this.arrOnOffGubun = new String[arrOnOffGubun.length];			 
			 for (int i = 0; i < arrOnOffGubun.length; ++i) {
				 this.arrOnOffGubun[i] = arrOnOffGubun[i];
			 }
		 } else {
			 this.arrOnOffGubun = null;
		 }
	}
	
	public String[] getArrProxyNm() {
		if (this.arrProxyNm != null) {
			String[] ret = new String[arrProxyNm.length];
			for (int i = 0; i < arrProxyNm.length; i++) { 
				ret[i] = this.arrProxyNm[i]; 
			}
			return ret;
		} else {
			return null;
		}	
	}
	public void setArrProxyNm(String[] arrProxyNm) {
		if (arrProxyNm != null) {
			 this.arrProxyNm = new String[arrProxyNm.length];			 
			 for (int i = 0; i < arrProxyNm.length; ++i) {
				 this.arrProxyNm[i] = arrProxyNm[i];
			 }
		 } else {
			 this.arrProxyNm = null;
		 }
	}
	
	public String[] getArrStaffSellYn() {
		if (this.arrStaffSellYn != null) {
			String[] ret = new String[arrStaffSellYn.length];
			for (int i = 0; i < arrStaffSellYn.length; i++) { 
				ret[i] = this.arrStaffSellYn[i]; 
			}
			return ret;
		} else {
			return null;
		}		
	}
	public void setArrStaffSellYn(String[] arrStaffSellYn) {
		if (arrStaffSellYn != null) {
			 this.arrStaffSellYn = new String[arrStaffSellYn.length];			 
			 for (int i = 0; i < arrStaffSellYn.length; ++i) {
				 this.arrStaffSellYn[i] = arrStaffSellYn[i];
			 }
		 } else {
			 this.arrStaffSellYn = null;
		 }
	}
	
	public String[] getArrAdmFg() {
		
		if (this.arrAdmFg != null) {
			String[] ret = new String[arrAdmFg.length];
			for (int i = 0; i < arrAdmFg.length; i++) { 
				ret[i] = this.arrAdmFg[i]; 
			}
			return ret;
		} else {
			return null;
		}			
	}
	public void setArrAdmFg(String[] arrAdmFg) {
		
		if (arrAdmFg != null) {
			 this.arrAdmFg = new String[arrAdmFg.length];			 
			 for (int i = 0; i < arrAdmFg.length; ++i) {
				 this.arrAdmFg[i] = arrAdmFg[i];
			 }
		 } else {
			 this.arrAdmFg = null;
		 }		
	}
	
	
public String[] getArrMatCd() {		
		if (this.arrMatCd != null) {
			String[] ret = new String[arrMatCd.length];
			for (int i = 0; i < arrMatCd.length; i++) { 
				ret[i] = this.arrMatCd[i]; 
			}
			return ret;
		} else {
			return null;
		}			
	}
	public void setArrMatCd(String[] arrMatCd) {
		if (arrMatCd != null) {
			 this.arrMatCd = new String[arrMatCd.length];			 
			 for (int i = 0; i < arrMatCd.length; ++i) {
				 this.arrMatCd[i] = arrMatCd[i];
			 }
		 } else {
			 this.arrMatCd  = null;
		 }
	}	
	
	public String[] getArrVicOnlineCd() {			
		if (this.arrVicOnlineCd != null) {
		String[] ret = new String[arrVicOnlineCd.length];
		for (int i = 0; i < arrVicOnlineCd.length; i++) { 
			ret[i] = this.arrVicOnlineCd[i]; 
			}
			return ret;
		} else {
			return null;
		}				
	}
	    
	public void setArrVicOnlineCd(String[] arrVicOnlineCd) {				
		if (arrVicOnlineCd != null) {
			 this.arrVicOnlineCd = new String[arrVicOnlineCd.length];			 
			 for (int i = 0; i < arrVicOnlineCd.length; ++i) {
				 this.arrVicOnlineCd[i] = arrVicOnlineCd[i];
			 }
		 } else {
			 this.arrVicOnlineCd = null;
		 }			
	}
		
		
	public String getColorSizeState() {
		return colorSizeState;
	}
	public void setColorSizeState(String colorSizeState) {
		this.colorSizeState = colorSizeState;
	}
	public String getProdAttTypFg() {
		return prodAttTypFg;
	}
	public void setProdAttTypFg(String prodAttTypFg) {
		this.prodAttTypFg = prodAttTypFg;
	}
	public String getVarAttCnt() {
		return varAttCnt;
	}
	public void setVarAttCnt(String varAttCnt) {
		this.varAttCnt = varAttCnt;
	}
	
	// 2016.08.29
    public String getInputVarAttCnt001() {
		return inputVarAttCnt001;
	}
	public void setInputVarAttCnt001(String inputVarAttCnt001) {
		this.inputVarAttCnt001 = inputVarAttCnt001;
	}
	// 2016.08.29
	public String getGrpCd() {
		return grpCd;
	}
	public void setGrpCd(String grpCd) {
		this.grpCd = grpCd;
	}
	public String getInputVarAttCnt() {
		return inputVarAttCnt;
	}
	public void setInputVarAttCnt(String inputVarAttCnt) {
		this.inputVarAttCnt = inputVarAttCnt;
	}	
	public String[] getArrTrdTypeDivnCd() {
		if (this.arrTrdTypeDivnCd != null) {
			String[] ret = new String[arrTrdTypeDivnCd.length];
			for (int i = 0; i < arrTrdTypeDivnCd.length; i++) { 
				ret[i] = this.arrTrdTypeDivnCd[i]; 
			}
			return ret;
		} else {
			return null;
		}	
	}
	public void setArrTrdTypeDivnCd(String[] arrTrdTypeDivnCd) {
		if (arrTrdTypeDivnCd != null) {
			 this.arrTrdTypeDivnCd = new String[arrTrdTypeDivnCd.length];			 
			 for (int i = 0; i < arrTrdTypeDivnCd.length; ++i) {
				 this.arrTrdTypeDivnCd[i] = arrTrdTypeDivnCd[i];
			 }
		 } else {
			 this.arrTrdTypeDivnCd = null;
		 }
	}
	public String getMixYn() {
		return mixYn;
	}
	public void setMixYn(String mixYn) {
		this.mixYn = mixYn;
	}
	public String getGbn() {
		return gbn;
	}
	public void setGbn(String gbn) {
		this.gbn = gbn;
	}
	public String getDealRepProdYn() {
		return dealRepProdYn;
	}
	public void setDealRepProdYn(String dealRepProdYn) {
		this.dealRepProdYn = dealRepProdYn;
	}
	public String getCtpdYn() {
		return ctpdYn;
	}
	public void setCtpdYn(String ctpdYn) {
		this.ctpdYn = ctpdYn;
	}
	public String getTrdTypeDivnCd() {
		return trdTypeDivnCd;
	}
	public void setTrdTypeDivnCd(String trdTypeDivnCd) {
		this.trdTypeDivnCd = trdTypeDivnCd;
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
	public String getStaffSellYn() {
		return staffSellYn;
	}
	public void setStaffSellYn(String staffSellYn) {
		this.staffSellYn = staffSellYn;
	}
	public String getAdmFg() {
		return admFg;
	}
	public void setAdmFg(String admFg) {
		this.admFg = admFg;
	}	
	public String getuAdmFg() {
		return uAdmFg;
	}
	public void setuAdmFg(String uAdmFg) {
		this.uAdmFg = uAdmFg;
	}
	
	public String getMatCd() {
		return matCd;
	}
	public void setMatCd(String matCd) {
		this.matCd = matCd;
	}
	public String getVicOnlineCd() {
		return vicOnlineCd;
	}
	public void setVicOnlineCd(String vicOnlineCd) {
		this.vicOnlineCd = vicOnlineCd;
	}
	
	//20180626 - EDI 임시보관함 상품 확정 시 배송정책을 설정하지 않을 경우 배송정책 설정하도록 유효성 추가
	public String getEntpCondUseYn() {
		return entpCondUseYn;
	}
	public void setEntpCondUseYn(String entpCondUseYn) {
		this.entpCondUseYn = entpCondUseYn;
	}
	
	//20180904 상품키워드 입력 기능 추가
    private String keywordYn;

	public String getKeywordYn() {
		return keywordYn;
	}
	public void setKeywordYn(String keywordYn) {
		this.keywordYn = keywordYn;
	}
	
	//20190215 상품대표 이미지 여부 추가
	public String getProdDescYn() {
		return prodDescYn;
	}
	public void setProdDescYn(String prodDescYn) {
		this.prodDescYn = prodDescYn;
	}
	public String getPogImgYn() {
		return pogImgYn;
	}
	public void setPogImgYn(String pogImgYn) {
		this.pogImgYn = pogImgYn;
	}
	public String getEsgYn() {
		return esgYn;
	}
	public void setEsgYn(String esgYn) {
		this.esgYn = esgYn;
	}
	public String getEsgItmRegYn() {
		return esgItmRegYn;
	}
	public void setEsgItmRegYn(String esgItmRegYn) {
		this.esgItmRegYn = esgItmRegYn;
	}
	public String getEsgDtFgChk() {
		return esgDtFgChk;
	}
	public void setEsgDtFgChk(String esgDtFgChk) {
		this.esgDtFgChk = esgDtFgChk;
	}
	public String getEsgDtValidChk() {
		return esgDtValidChk;
	}
	public void setEsgDtValidChk(String esgDtValidChk) {
		this.esgDtValidChk = esgDtValidChk;
	}
	public String getChanCd() {
		return chanCd;
	}
	public void setChanCd(String chanCd) {
		this.chanCd = chanCd;
	}
	
	public String[] getArrMaxxOnlyYn() {
		if (this.arrMaxxOnlyYn != null) {
			String[] ret = new String[arrMaxxOnlyYn.length];
			for (int i = 0; i < arrMaxxOnlyYn.length; i++) { 
				ret[i] = this.arrMaxxOnlyYn[i]; 
			}
			return ret;
		} else {
			return null;
		}	
	}
	public void setArrMaxxOnlyYn(String[] arrMaxxOnlyYn) {
		if (arrMaxxOnlyYn != null) {
			 this.arrMaxxOnlyYn = new String[arrMaxxOnlyYn.length];			 
			 for (int i = 0; i < arrMaxxOnlyYn.length; ++i) {
				 this.arrMaxxOnlyYn[i] = arrMaxxOnlyYn[i];
			 }
		 } else {
			 this.arrMaxxOnlyYn = null;
		 }
	}
	public String getMaxxOnlyYn() {
		return maxxOnlyYn;
	}
	public void setMaxxOnlyYn(String maxxOnlyYn) {
		this.maxxOnlyYn = maxxOnlyYn;
	}
	public List<NEDMPRO0040VO> getProdArr() {
		return prodArr;
	}
	public void setProdArr(List<NEDMPRO0040VO> prodArr) {
		this.prodArr = prodArr;
	}
	public List<NEDMPRO0040VO> getBosProdList() {
		return bosProdList;
	}
	public void setBosProdList(List<NEDMPRO0040VO> bosProdList) {
		this.bosProdList = bosProdList;
	}
	public List<NEDMPRO0040VO> getErpProdList() {
		return erpProdList;
	}
	public void setErpProdList(List<NEDMPRO0040VO> erpProdList) {
		this.erpProdList = erpProdList;
	}
	public String getSendGbn() {
		return sendGbn;
	}
	public void setSendGbn(String sendGbn) {
		this.sendGbn = sendGbn;
	}
	public String getBosSendDy() {
		return bosSendDy;
	}
	public void setBosSendDy(String bosSendDy) {
		this.bosSendDy = bosSendDy;
	}
	public String getAutoSendFg() {
		return autoSendFg;
	}
	public void setAutoSendFg(String autoSendFg) {
		this.autoSendFg = autoSendFg;
	}
	public String getBosCfrmEndDy() {
		return bosCfrmEndDy;
	}
	public void setBosCfrmEndDy(String bosCfrmEndDy) {
		this.bosCfrmEndDy = bosCfrmEndDy;
	}
	public String getBosCfrmYn() {
		return bosCfrmYn;
	}
	public void setBosCfrmYn(String bosCfrmYn) {
		this.bosCfrmYn = bosCfrmYn;
	}
	public String getBosCfrmExpYn() {
		return bosCfrmExpYn;
	}
	public void setBosCfrmExpYn(String bosCfrmExpYn) {
		this.bosCfrmExpYn = bosCfrmExpYn;
	}
	public String getBatchYn() {
		return batchYn;
	}
	public void setBatchYn(String batchYn) {
		this.batchYn = batchYn;
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
	public String getSnorProdPcost() {
		return snorProdPcost;
	}
	public void setSnorProdPcost(String snorProdPcost) {
		this.snorProdPcost = snorProdPcost;
	}
	public String getSnorProdSalePrc() {
		return snorProdSalePrc;
	}
	public void setSnorProdSalePrc(String snorProdSalePrc) {
		this.snorProdSalePrc = snorProdSalePrc;
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
	public String getOnorProdSalePrc() {
		return onorProdSalePrc;
	}
	public void setOnorProdSalePrc(String onorProdSalePrc) {
		this.onorProdSalePrc = onorProdSalePrc;
	}
	public String getOprftRate() {
		return oprftRate;
	}
	public void setOprftRate(String oprftRate) {
		this.oprftRate = oprftRate;
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
	public String getSuperOnlyYn() {
		return superOnlyYn;
	}
	public void setSuperOnlyYn(String superOnlyYn) {
		this.superOnlyYn = superOnlyYn;
	}
	public String getBosTgYn() {
		return bosTgYn;
	}
	public void setBosTgYn(String bosTgYn) {
		this.bosTgYn = bosTgYn;
	}
	public String getSendYn() {
		return sendYn;
	}
	public void setSendYn(String sendYn) {
		this.sendYn = sendYn;
	}
	public String getSendDate() {
		return sendDate;
	}
	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
	}
	public String getSendPath() {
		return sendPath;
	}
	public void setSendPath(String sendPath) {
		this.sendPath = sendPath;
	}
	
}
