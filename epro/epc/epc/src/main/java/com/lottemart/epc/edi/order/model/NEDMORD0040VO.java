package com.lottemart.epc.edi.order.model;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * @Class Name : PEDMORD0004VO
 * @Description : 발주정보 -> 기간정보 VO
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

public class NEDMORD0040VO implements Serializable {

	
	private static final long serialVersionUID = -6918711902569942487L;


	public NEDMORD0040VO () {}
	
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
	
	private String[] venCds;
	
	
	private String prodCd;				//상품코드
	private String srcmkCd;		        //판매코드
	private String prodNm;		        //상품명
	private String prodStd;		        //상품규격
	private String ordUnit;		        //발주단위
	private String ordIpsu;		        //발주입수
	private String buyDan;		        //상품매입단가
	private String strNm;		        //
	private String ordQty;		        //발주수량
	private String buyPrc;		        //원가
	private String negoBuyPrc;	        //협상원가
	private String homeNm;		        //원산지명
	private String ordSlipNo;	        //발주전표번호
	private String splyAbleQty;         //납품가능수량
	private String strCd;		        //점포코드
	private String producer;	        //원산지명
	private String purUnitCdNm;         //발주단위


	public String getSearchEntpCd() {
		return searchEntpCd;
	}
	public void setSearchEntpCd(String searchEntpCd) {
		this.searchEntpCd = searchEntpCd;
	}
	public String getProdCd() {
		return prodCd;
	}
	public void setProdCd(String prodCd) {
		this.prodCd = prodCd;
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
	public String getProdStd() {
		return prodStd;
	}
	public void setProdStd(String prodStd) {
		this.prodStd = prodStd;
	}
	public String getOrdUnit() {
		return ordUnit;
	}
	public void setOrdUnit(String ordUnit) {
		this.ordUnit = ordUnit;
	}
	public String getOrdIpsu() {
		return ordIpsu;
	}
	public void setOrdIpsu(String ordIpsu) {
		this.ordIpsu = ordIpsu;
	}
	public String getBuyDan() {
		return buyDan;
	}
	public void setBuyDan(String buyDan) {
		this.buyDan = buyDan;
	}
	public String getStrNm() {
		return strNm;
	}
	public void setStrNm(String strNm) {
		this.strNm = strNm;
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
	public String getNegoBuyPrc() {
		return negoBuyPrc;
	}
	public void setNegoBuyPrc(String negoBuyPrc) {
		this.negoBuyPrc = negoBuyPrc;
	}
	public String getHomeNm() {
		return homeNm;
	}
	public void setHomeNm(String homeNm) {
		this.homeNm = homeNm;
	}
	public String getOrdSlipNo() {
		return ordSlipNo;
	}
	public void setOrdSlipNo(String ordSlipNo) {
		this.ordSlipNo = ordSlipNo;
	}
	public String getSplyAbleQty() {
		return splyAbleQty;
	}
	public void setSplyAbleQty(String splyAbleQty) {
		this.splyAbleQty = splyAbleQty;
	}
	public String getStrCd() {
		return strCd;
	}
	public void setStrCd(String strCd) {
		this.strCd = strCd;
	}
	public String getProducer() {
		return producer;
	}
	public void setProducer(String producer) {
		this.producer = producer;
	}
	public String getPurUnitCdNm() {
		return purUnitCdNm;
	}
	public void setPurUnitCdNm(String purUnitCdNm) {
		this.purUnitCdNm = purUnitCdNm;
	}
	public String getSearchProductVal() {
		return searchProductVal;
	}
	public void setSearchProductVal(String searchProductVal) {
		this.searchProductVal = searchProductVal;
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

	
}
