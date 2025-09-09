package com.lottemart.epc.edi.product.model;

import java.io.Serializable;

/**
 * @Class Name : NEDMPRO0220VO
 * @Description : 물류바코드 등록 VO
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>O
 *   
 *   수정일      			수정자           		수정내용
 *  -------    		--------    	---------------------------
 * 	2015.21.01 	SONG MIN KYO	최초생성
 * </pre>
 */
public class NEDMPRO0220VO implements Serializable {
				
	private static final long serialVersionUID = 6441295875306997915L;
	
	/** 프로그램 ID */
	private String pgmId;
	/** 순번 */
	private String seq;
	/** 물류바코드 */
	private String logiBcd;	
	/** 변형구분 */
	private String variant;
	/** 혼재여부 */
	private String mixProdFg;
	/**  */
	private String logiBoxIpsu;
	/** 총중량 */
	private String wg;
	/** 가로 */
	private String width;
	/** 세로 */
	private String length;
	/** 높이 */
	private String height;
	/** 사이즈단위 */
	private String sizeUnit;
	/** 무게단위 */
	private String wgUnit;
	/** 소터에러 구분 */
	private String conveyFg;
	/** 소터구분 */
	private String sorterFg;
	/** 가로박스 */
	private String innerIpsu;
	/** 세로박스 */
	private String pltLayerQty;
	/** 높이박스 수 */
	private String pltHeightQty;
	/** 크로스독구분 */
	private String crsdkFg;
	/** 빅마켓 취급구분 */
	private String wUseFg;
	/** 빅마켓 박스수 */
	private String wInnerIpsu;
	/** 사용구분 */
	private String useFg;
	/** 상품코드 */
	private String prodCd;
	/** 대분류코드 */
	private String l1Cd;
	/** 협력업체코드 */
	private String venCd;
	/** 등록일시 */
	private String regDate;
	/** 등록자 */
	private String regId;
	/** 수정일시 */
	private String modDate;
	/** 수정자 */
	private String modId;
	/** 물류바코드 확정상태 [00:심사중, 02:심사중, 09:완료]*/
	private String logiCfmFg;
	/** 총박스 수 */
	private String totalBox;
	/** 물류바코드 등룩/수정 구분[I:등록, U:수정] */
	private String cfgFg;
	
	 /* 확정처리 - RFC Call Proxy명 */    
	private String[] arrProxyNm;				//RFC CALL proxy명
	
	/** PROXY Name */
	private String proxyNm;
	
	/** 판매코드 */
	private String srcmkCd;

	
	/** 상품명 */
	private String prodNm;
	
	/** 상품등록일자 */
	private String regDt;
	
	/** 발주입수수량 */
	private String ordIpsu;
	/** 기본단위 */
	private String meins;
	
		
	public String getPgmId() {
		return pgmId;
	}
	public void setPgmId(String pgmId) {
		this.pgmId = pgmId;
	}
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getLogiBcd() {
		return logiBcd;
	}
	public void setLogiBcd(String logiBcd) {
		this.logiBcd = logiBcd;
	}
	public String getVariant() {
		return variant;
	}
	public void setVariant(String variant) {
		this.variant = variant;
	}
	public String getMixProdFg() {
		return mixProdFg;
	}
	public void setMixProdFg(String mixProdFg) {
		this.mixProdFg = mixProdFg;
	}
	public String getLogiBoxIpsu() {
		return logiBoxIpsu;
	}
	public void setLogiBoxIpsu(String logiBoxIpsu) {
		this.logiBoxIpsu = logiBoxIpsu;
	}
	public String getWg() {
		return wg;
	}
	public void setWg(String wg) {
		this.wg = wg;
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
	public String getSizeUnit() {
		return sizeUnit;
	}
	public void setSizeUnit(String sizeUnit) {
		this.sizeUnit = sizeUnit;
	}
	public String getWgUnit() {
		return wgUnit;
	}
	public void setWgUnit(String wgUnit) {
		this.wgUnit = wgUnit;
	}
	public String getConveyFg() {
		return conveyFg;
	}
	public void setConveyFg(String conveyFg) {
		this.conveyFg = conveyFg;
	}
	public String getSorterFg() {
		return sorterFg;
	}
	public void setSorterFg(String sorterFg) {
		this.sorterFg = sorterFg;
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
	public String getCrsdkFg() {
		return crsdkFg;
	}
	public void setCrsdkFg(String crsdkFg) {
		this.crsdkFg = crsdkFg;
	}
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
	public String getUseFg() {
		return useFg;
	}
	public void setUseFg(String useFg) {
		this.useFg = useFg;
	}
	public String getProdCd() {
		return prodCd;
	}
	public void setProdCd(String prodCd) {
		this.prodCd = prodCd;
	}
	public String getL1Cd() {
		return l1Cd;
	}
	public void setL1Cd(String l1Cd) {
		this.l1Cd = l1Cd;
	}
	public String getVenCd() {
		return venCd;
	}
	public void setVenCd(String venCd) {
		this.venCd = venCd;
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
	public String getLogiCfmFg() {
		return logiCfmFg;
	}
	public void setLogiCfmFg(String logiCfmFg) {
		this.logiCfmFg = logiCfmFg;
	}
	public String getTotalBox() {
		return totalBox;
	}
	public void setTotalBox(String totalBox) {
		this.totalBox = totalBox;
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
	public String getCfgFg() {
		return cfgFg;
	}
	public void setCfgFg(String cfgFg) {
		this.cfgFg = cfgFg;
	}
	public String getProxyNm() {
		return proxyNm;
	}
	public void setProxyNm(String proxyNm) {
		this.proxyNm = proxyNm;
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
	public String getRegDt() {
		return regDt;
	}
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}
	public String getOrdIpsu() {
		return ordIpsu;
	}
	public void setOrdIpsu(String ordIpsu) {
		this.ordIpsu = ordIpsu;
	}
	public String getMeins() {
		return meins;
	}
	public void setMeins(String meins) {
		this.meins = meins;
	}
	
}
