package com.lottemart.epc.edi.product.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class NEDMPRO0221VO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8881235348369905947L;



	public NEDMPRO0221VO() {}

	//--조회조건 -------------------------------
	private String searchVenCd	  ;    // 협력업체코드		
	private String searchSrcmkCd  ;	   // 판매(88)코드
	
	private String newProdCd      ;	    // 신규상품번호
	
	private String venCd	      ;		// 협력업체코드
	private String l1Cd	          ;		// 대분류코드
	private String srcmkCd	      ;		// 판매(88)코드
	private String prodCd		  ;		// 상품코드
	private String regId	  ;		// 수정자 
	private String orgLogiBcd;
	
	private String[] pgmId    	  ; 	// PGM ID  (KEY)
	private String[] seq	      ;  	// 순번 (KEY
	private String[] logiBcd	  ;		// 물류바코드
	private String[] logiCfmFg	  ;		// 상태
	private String[] useFg	      ;		// 사용여부
	private String[] mixProdFg	  ;		// 혼재여부 0:비혼재, 1:혼재
	private String[] wg	          ;		// 총중량
	private String[] width	      ;		// 박스체적 (가로)  
	private String[] length	      ;		// 박스체적 (세로)
	private String[] height	      ;		// 박스체적 (높이) 
	private String[] conveyFg	  ;		// 소터에러사유
	private String[] sorterFg	  ;		// 소터구분
	private String[] innerIpsu	  ;		// 팔레트(가로박스수)
	private String[] pltLayerQty  ;		// 팔레트(세로박스수)
	private String[] pltHeightQty ;		// 팔레트 (높이박스수)
	private String[] logiBoxIpsu  ;		// 물류박스 입수
	private String[] chgFg        ;		// 변경구분 (I:등록, U:수정)
	private String[] crsdkFg      ;		// 클로스덕 물류바코드 flag 순서/값  0/1
	private String[] regDt		  ;     // 수정일자
	private String[] sizeUnit		  ;     // 사이즈
	private String[] wgUnit		  ;     // 무게
	
	private String new_prod_id	  ;		//물류바코드 new_prod_id
	
	private String[] wUseFg	      ;		// WIC 사용여부
	

	private String[] wInnerIpsu	      ;		// WIC 박수수
	
	
	
	

	public String getOrgLogiBcd() {
		return orgLogiBcd;
	}
	public void setOrgLogiBcd(String orgLogiBcd) {
		this.orgLogiBcd = orgLogiBcd;
	}
	public String getNew_prod_id() {
		return new_prod_id;
	}
	public void setNew_prod_id(String new_prod_id) {
		this.new_prod_id = new_prod_id;
	}
	public String getSearchVenCd() {
		return searchVenCd;
	}
	public void setSearchVenCd(String searchVenCd) {
		this.searchVenCd = searchVenCd;
	}
	public String getSearchSrcmkCd() {
		return searchSrcmkCd;
	}
	public void setSearchSrcmkCd(String searchSrcmkCd) {
		this.searchSrcmkCd = searchSrcmkCd;
	}
	
	public String getNewProdCd() {
		return newProdCd;
	}
	public void setNewProdCd(String newProdCd) {
		this.newProdCd = newProdCd;
	}
	
	public String getVenCd() {
		return venCd;
	}
	public void setVenCd(String venCd) {
		this.venCd = venCd;
	}
	public String getL1Cd() {
		return l1Cd;
	}
	public void setL1Cd(String l1Cd) {
		this.l1Cd = l1Cd;
	}
	public String getSrcmkCd() {
		return srcmkCd;
	}
	public void setSrcmkCd(String srcmkCd) {
		this.srcmkCd = srcmkCd;
	}
	public String getProdCd() {
		return prodCd;
	}
	public void setProdCd(String prodCd) {
		this.prodCd = prodCd;
	}
	public String[] getPgmId() {
		return pgmId;
	}
	
	
	public void setPgmId(String[] pgmId) {
		this.pgmId = pgmId;
	}
	public String[] getSeq() {
		return seq;
	}
	public void setSeq(String[] seq) {
		this.seq = seq;
	}
	public String[] getLogiBcd() {
		return logiBcd;
	}
	public void setLogiBcd(String[] logiBcd) {
		this.logiBcd = logiBcd;
	}
	public String[] getLogiCfmFg() {
		return logiCfmFg;
	}
	public void setLogiCfmFg(String[] logiCfmFg) {
		this.logiCfmFg = logiCfmFg;
	}
	public String[] getUseFg() {
		return useFg;
	}
	public String[] getwUseFg() {
		return wUseFg;
	}
	
	public void setUseFg(String[] useFg) {
		this.useFg = useFg;
	}
	public void setwUseFg(String[] wUseFg) {
		this.wUseFg = wUseFg;
	}
	public String[] getMixProdFg() {
		return mixProdFg;
	}
	public void setMixProdFg(String[] mixProdFg) {
		this.mixProdFg = mixProdFg;
	}
	public String[] getWg() {
		return wg;
	}
	public void setWg(String[] wg) {
		this.wg = wg;
	}
	public String[] getWidth() {
		return width;
	}
	public void setWidth(String[] width) {
		this.width = width;
	}
	public String[] getLength() {
		return length;
	}
	public void setLength(String[] length) {
		this.length = length;
	}
	public String[] getHeight() {
		return height;
	}
	public void setHeight(String[] height) {
		this.height = height;
	}
	public String[] getConveyFg() {
		return conveyFg;
	}
	public void setConveyFg(String[] conveyFg) {
		this.conveyFg = conveyFg;
	}
	public String[] getSorterFg() {
		return sorterFg;
	}
	public void setSorterFg(String[] sorterFg) {
		this.sorterFg = sorterFg;
	}
	public String[] getInnerIpsu() {
		return innerIpsu;
	}

	public String[] getwInnerIpsu() {
		return wInnerIpsu;
	}

	public void setInnerIpsu(String[] innerIpsu) {
		this.innerIpsu = innerIpsu;
	}
	public void setwInnerIpsu(String[] wInnerIpsu) {
		this.wInnerIpsu = wInnerIpsu;
	}

	public String[] getPltLayerQty() {
		return pltLayerQty;
	}
	public void setPltLayerQty(String[] pltLayerQty) {
		this.pltLayerQty = pltLayerQty;
	}
	public String[] getPltHeightQty() {
		return pltHeightQty;
	}
	public void setPltHeightQty(String[] pltHeightQty) {
		this.pltHeightQty = pltHeightQty;
	}
	public String[] getLogiBoxIpsu() {
		return logiBoxIpsu;
	}
	public void setLogiBoxIpsu(String[] logiBoxIpsu) {
		this.logiBoxIpsu = logiBoxIpsu;
	}
	public String[] getChgFg() {
		return chgFg;
	}
	public void setChgFg(String[] chgFg) {
		this.chgFg = chgFg;
	}
	public String[] getCrsdkFg() {
		return crsdkFg;
	}
	public void setCrsdkFg(String[] crsdkFg) {
		this.crsdkFg = crsdkFg;
	}
	public String[] getRegDt() {
		return regDt;
	}
	public void setRegDt(String[] regDt) {
		this.regDt = regDt;
	}
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	public String[] getSizeUnit() {
		return sizeUnit;
	}
	public void setSizeUnit(String[] sizeUnit) {
		this.sizeUnit = sizeUnit;
	}
	public String[] getWgUnit() {
		return wgUnit;
	}
	public void setWgUnit(String[] wgUnit) {
		this.wgUnit = wgUnit;
	}
	
	
	
	
	
	
	
}
