package com.lottemart.epc.edi.order.model;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * @Class Name : NEDMORD0110VO
 * @Description : 발주정보 -> 주문응답서 VO
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 11. 04.  SUN GIL CHOI 		오후 2:32:38 
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */

public class NEDMORD0110VO implements Serializable {

	
	private static final long serialVersionUID = -6918711902569942487L;


	public NEDMORD0110VO () {}
	
	//crud파람
	private NEDMORD0110VO[] arrParam;
	
	
	/** 검색시작일 */
	private String srchStartDate;
	/** 검색종료일 */
	private String srchEndDate;
	/** 협력업체코드 */
	private String searchEntpCd;
	/** 상품선택 */
	private String searchProductVal;
	/** 점포코드(배열) */
	private ArrayList searchStoreAl;
	/** TEXT Data */
	private String textData;
	/** 발주정보 */
	private String searchOrdering;
	
	
	private String[] venCds;
	
	private String ordSlipNo;				//발주전표번호
	private String prodCd;                  //상품코드
	private String ordFg;                   //발주구분
	private String ordDy;                   //발주일
	private String strNm;                   //
	private String totQty;                  //
	private String totPrc;                  //
	private String ctrArrDy;                //센터입하일자
	private String splyTm;                  //납품시간
	private String splyDy;                  //납품일자
	private String userHit;                 //사용자조회
	private String venCd;                   //업체코드
	private String strCd;                   //점포코드
	private String prodPatFg;               //상품패턴구분
	private String logiCatNm;               //
	private String routeFg;                 //루트구분
	private String supplyCd;                //
	private String supplyNm;                //
	private String srcmkCd;                 //판매코드
	private String prodNm;                  //상품명
	private String ordIpsu;                 //발주입수
	private String prodStd;                 //상품규격
	private String ordQty;                  //발주수량
	private String buyPrc;                  //원가
	private String ordUnit;                 //발주단위
	private String splyAbleQty;             //납품가능수량
	private String protectTagFg;            //도난방지태그여부
	private String homeNm;                  //원산지명
	private String taxVat;                  //
	private String buyDan;                  //상품매입단가
	private String evtCd;                   //행사코드
	private String negoBuyPrc;              //협상원가
	private String producer;                //원산지명
	private String hour;                    //시간
	private String min;                     //분


	public String getSearchEntpCd() {
		return searchEntpCd;
	}
	public void setSearchEntpCd(String searchEntpCd) {
		this.searchEntpCd = searchEntpCd;
	}
	public String getSearchProductVal() {
		return searchProductVal;
	}
	public void setSearchProductVal(String searchProductVal) {
		this.searchProductVal = searchProductVal;
	}
	public String getOrdSlipNo() {
		return ordSlipNo;
	}
	public void setOrdSlipNo(String ordSlipNo) {
		this.ordSlipNo = ordSlipNo;
	}
	public String getProdCd() {
		return prodCd;
	}
	public void setProdCd(String prodCd) {
		this.prodCd = prodCd;
	}
	public String getOrdFg() {
		return ordFg;
	}
	public void setOrdFg(String ordFg) {
		this.ordFg = ordFg;
	}
	public String getOrdDy() {
		return ordDy;
	}
	public void setOrdDy(String ordDy) {
		this.ordDy = ordDy;
	}
	public String getStrNm() {
		return strNm;
	}
	public void setStrNm(String strNm) {
		this.strNm = strNm;
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
	public String getCtrArrDy() {
		return ctrArrDy;
	}
	public void setCtrArrDy(String ctrArrDy) {
		this.ctrArrDy = ctrArrDy;
	}
	public String getSplyTm() {
		return splyTm;
	}
	public void setSplyTm(String splyTm) {
		this.splyTm = splyTm;
	}
	public String getSplyDy() {
		return splyDy;
	}
	public void setSplyDy(String splyDy) {
		this.splyDy = splyDy;
	}
	public String getUserHit() {
		return userHit;
	}
	public void setUserHit(String userHit) {
		this.userHit = userHit;
	}
	public String getVenCd() {
		return venCd;
	}
	public void setVenCd(String venCd) {
		this.venCd = venCd;
	}
	public String getStrCd() {
		return strCd;
	}
	public void setStrCd(String strCd) {
		this.strCd = strCd;
	}
	public String getProdPatFg() {
		return prodPatFg;
	}
	public void setProdPatFg(String prodPatFg) {
		this.prodPatFg = prodPatFg;
	}
	public String getLogiCatNm() {
		return logiCatNm;
	}
	public void setLogiCatNm(String logiCatNm) {
		this.logiCatNm = logiCatNm;
	}
	public String getRouteFg() {
		return routeFg;
	}
	public void setRouteFg(String routeFg) {
		this.routeFg = routeFg;
	}
	public String getSupplyCd() {
		return supplyCd;
	}
	public void setSupplyCd(String supplyCd) {
		this.supplyCd = supplyCd;
	}
	public String getSupplyNm() {
		return supplyNm;
	}
	public void setSupplyNm(String supplyNm) {
		this.supplyNm = supplyNm;
	}
	public String getSrcmkCd() {
		return srcmkCd;
	}
	public void setSrcmkCd(String srcmkCd) {
		this.srcmkCd = srcmkCd;
	}
	public String getProdNm() {
		return prodNm;
	}
	public void setProdNm(String prodNm) {
		this.prodNm = prodNm;
	}
	public String getOrdIpsu() {
		return ordIpsu;
	}
	public void setOrdIpsu(String ordIpsu) {
		this.ordIpsu = ordIpsu;
	}
	public String getProdStd() {
		return prodStd;
	}
	public void setProdStd(String prodStd) {
		this.prodStd = prodStd;
	}
	public String getOrdQty() {
		return ordQty;
	}
	public void setOrdQty(String ordQty) {
		this.ordQty = ordQty;
	}
	public String getBuyPrc() {
		return buyPrc;
	}
	public void setBuyPrc(String buyPrc) {
		this.buyPrc = buyPrc;
	}
	public String getOrdUnit() {
		return ordUnit;
	}
	public void setOrdUnit(String ordUnit) {
		this.ordUnit = ordUnit;
	}
	public String getSplyAbleQty() {
		return splyAbleQty;
	}
	public void setSplyAbleQty(String splyAbleQty) {
		this.splyAbleQty = splyAbleQty;
	}
	public String getProtectTagFg() {
		return protectTagFg;
	}
	public void setProtectTagFg(String protectTagFg) {
		this.protectTagFg = protectTagFg;
	}
	public String getHomeNm() {
		return homeNm;
	}
	public void setHomeNm(String homeNm) {
		this.homeNm = homeNm;
	}
	public String getTaxVat() {
		return taxVat;
	}
	public void setTaxVat(String taxVat) {
		this.taxVat = taxVat;
	}
	public String getBuyDan() {
		return buyDan;
	}
	public void setBuyDan(String buyDan) {
		this.buyDan = buyDan;
	}
	public String getEvtCd() {
		return evtCd;
	}
	public void setEvtCd(String evtCd) {
		this.evtCd = evtCd;
	}
	public String getNegoBuyPrc() {
		return negoBuyPrc;
	}
	public void setNegoBuyPrc(String negoBuyPrc) {
		this.negoBuyPrc = negoBuyPrc;
	}
	public String getProducer() {
		return producer;
	}
	public void setProducer(String producer) {
		this.producer = producer;
	}
	public String getHour() {
		return hour;
	}
	public void setHour(String hour) {
		this.hour = hour;
	}
	public String getMin() {
		return min;
	}
	public void setMin(String min) {
		this.min = min;
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
	public ArrayList getSearchStoreAl() {
		if (this.searchStoreAl != null) {
			ArrayList ret = new ArrayList();
			for (int i = 0; i < searchStoreAl.size(); i++) {
				ret.add(i, this.searchStoreAl.get(i));
			}
			
			return ret;
		} else {
			return null;
		}
	}
	public void setSearchStoreAl(ArrayList searchStoreAl) {
		if (searchStoreAl != null) {
			this.searchStoreAl = new ArrayList();
			for (int i = 0; i < searchStoreAl.size();i++) {
				this.searchStoreAl.add(i, searchStoreAl.get(i));
			}
		} else {
			this.searchStoreAl = null;
		}
	}
	public NEDMORD0110VO[] getArrParam() {
		if (this.arrParam != null) {
			NEDMORD0110VO[] ret = new NEDMORD0110VO[arrParam.length];
			for (int i = 0; i < arrParam.length; i++) { 
				ret[i] = this.arrParam[i]; 
			}
			return ret;
		} else {
			return null;
		}
	}
	public void setArrParam(NEDMORD0110VO[] arrParam) {
		if (arrParam != null) {
			 this.arrParam = new NEDMORD0110VO[arrParam.length];			 
			 for (int i = 0; i < arrParam.length; ++i) {
				 this.arrParam[i] = arrParam[i];
			 }
		 } else {
			 this.venCds = null;
		 }
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
	public String getTextData() {
		return textData;
	}
	public void setTextData(String textData) {
		this.textData = textData;
	}
	public String getSearchOrdering() {
		return searchOrdering;
	}
	public void setSearchOrdering(String searchOrdering) {
		this.searchOrdering = searchOrdering;
	}

}
