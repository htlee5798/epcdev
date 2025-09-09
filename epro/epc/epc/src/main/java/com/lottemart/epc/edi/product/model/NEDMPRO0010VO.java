package com.lottemart.epc.edi.product.model;

import java.io.Serializable;

/**
 * @Class Name : NEDMPRO0010VO
 * @Description : 신상품등록 현황 조회 VO
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      			수정자           		수정내용
 *  -------    		--------    	---------------------------
 * 	2015.12.17 	SONG MIN KYO	최초생성
 * </pre>
 */
public class NEDMPRO0010VO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8373894471374111954L;
	
	/** 신규상품코드 */
	private String pgmId;
	/**  상품명 */
	private String prodNm;
	/** 상품 규격 */
	private String prodStandardNm;
	/**  판매코드 */
	private String srcmkCd;
	/** 업체코드 */
	private String entpCd;
	/** 발주입수수량 */
	private String purInrcntQty;
	/** 정상상품매출가격 */
	private String norProdSalePrc;
	/** 정상상품원가 */
	private String norProdPcost;
	/** 이익율 */
	private String prftRate;
	/** 등록 일시 */
	private String regDate;
	/** CFM_FG */
	private String cfmFg;
	/**  */
	private String attNm;
	/** 이미지 확정구분 */
	private String zzimageCfgFg;
	/** 상태값 */
	private String logiCfmFg;
	/** 빅마켓 취급구분 */
	private String wUseFg;
	/** 변형속성 순번 */
	private String variant;
	/** 상품 이미지 아이디 */
	private String prodImgId;
	/** 상품범주[00:단일, 01:묶음] */
	private String prodAttTypFg;
	
	/*********************** 온라인 전용으로 추가 **************************/
	/** 조정구분 */
	private String adjFg;
	/** 온라인대표상품코드 */
	private String onlineRepProdCd;	
	/**  인터넷상품코드 */
	private String prodCd;
	/**  딜상품여부 */
	private String dealRepProdYn; 
	/**  구성품여부 */
	private String ctpdYn; 
	/****************************************************************/
	
	/** 상품상태명 */
	private String cfmFgTxt;
	/** 상품구분 */
	private String onOffDivnCd;
	/** MD전송구분 */
	private String mdSendDivnCd;
	/** ROWSPAN 할 수 */
	private String ran;
	/** 거래형태[1:직매입, 2:특약1, 4:특약2] */
	private String trdTypeDivnCd;
	/** REG_SAP 테이블의 판매코드값 [상품으로 결정된 판매코드] */
	private String fixSrcmkCd;
	/** 상품구분코드[1:규격, 5:패션] */
	private String prodDivnCd;
	/** 혼재여부 */
	private String mixYn;
	/** 대분류 */
	private String l1Cd;
	/** 신상품 반려 및 거절사유 */
	private String cfmFgReason;
	
	private String wnorProdPcost;		//정상상품원가 (MAXX)
	private String wnorProdSalePrc;		//정상상품매가 (MAXX)
	private String wprftRate;			//이익률 (MAXX)
	private String snorProdPcost;		//정상상품원가 (슈퍼)
	private String snorProdSalePrc;		//정상상품매가 (슈퍼)
	private String sprftRate;			//이익률 (슈퍼)
	private String onorProdPcost;		//정상상품원가 (오카도)
	private String onorProdSalePrc;		//정상상품매가 (오카도)
	private String oprftRate;			//이익률 (오카도)
	private String chanCd;				//채널코드
	
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
	public String getProdStandardNm() {
		return prodStandardNm;
	}
	public void setProdStandardNm(String prodStandardNm) {
		this.prodStandardNm = prodStandardNm;
	}
	public String getSrcmkCd() {
		return srcmkCd;
	}
	public void setSrcmkCd(String srcmkCd) {
		this.srcmkCd = srcmkCd;
	}
	public String getEntpCd() {
		return entpCd;
	}
	public void setEntpCd(String entpCd) {
		this.entpCd = entpCd;
	}
	public String getPurInrcntQty() {
		return purInrcntQty;
	}
	public void setPurInrcntQty(String purInrcntQty) {
		this.purInrcntQty = purInrcntQty;
	}
	public String getNorProdSalePrc() {
		return norProdSalePrc;
	}
	public void setNorProdSalePrc(String norProdSalePrc) {
		this.norProdSalePrc = norProdSalePrc;
	}
	public String getNorProdPcost() {
		return norProdPcost;
	}
	public void setNorProdPcost(String norProdPcost) {
		this.norProdPcost = norProdPcost;
	}
	public String getPrftRate() {
		return prftRate;
	}
	public void setPrftRate(String prftRate) {
		this.prftRate = prftRate;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getCfmFg() {
		return cfmFg;
	}
	public void setCfmFg(String cfmFg) {
		this.cfmFg = cfmFg;
	}
	public String getAttNm() {
		return attNm;
	}
	public void setAttNm(String attNm) {
		this.attNm = attNm;
	}
	public String getZzimageCfgFg() {
		return zzimageCfgFg;
	}
	public void setZzimageCfgFg(String zzimageCfgFg) {
		this.zzimageCfgFg = zzimageCfgFg;
	}
	public String getLogiCfmFg() {
		return logiCfmFg;
	}
	public void setLogiCfmFg(String logiCfmFg) {
		this.logiCfmFg = logiCfmFg;
	}
	public String getwUseFg() {
		return wUseFg;
	}
	public void setwUseFg(String wUseFg) {
		this.wUseFg = wUseFg;
	}
	public String getVariant() {
		return variant;
	}
	public void setVariant(String variant) {
		this.variant = variant;
	}
	public String getProdImgId() {
		return prodImgId;
	}
	public void setProdImgId(String prodImgId) {
		this.prodImgId = prodImgId;
	}
	public String getAdjFg() {
		return adjFg;
	}
	public void setAdjFg(String adjFg) {
		this.adjFg = adjFg;
	}
	public String getOnlineRepProdCd() {
		return onlineRepProdCd;
	}
	public void setOnlineRepProdCd(String onlineRepProdCd) {
		this.onlineRepProdCd = onlineRepProdCd;
	}
	public String getProdCd() {
		return prodCd;
	}
	public void setProdCd(String prodCd) {
		this.prodCd = prodCd;
	}
	public String getCfmFgTxt() {
		return cfmFgTxt;
	}
	public void setCfmFgTxt(String cfmFgTxt) {
		this.cfmFgTxt = cfmFgTxt;
	}
	public String getOnOffDivnCd() {
		return onOffDivnCd;
	}
	public void setOnOffDivnCd(String onOffDivnCd) {
		this.onOffDivnCd = onOffDivnCd;
	}
	public String getMdSendDivnCd() {
		return mdSendDivnCd;
	}
	public void setMdSendDivnCd(String mdSendDivnCd) {
		this.mdSendDivnCd = mdSendDivnCd;
	}
	public String getRan() {
		return ran;
	}
	public void setRan(String ran) {
		this.ran = ran;
	}
	public String getTrdTypeDivnCd() {
		return trdTypeDivnCd;
	}
	public void setTrdTypeDivnCd(String trdTypeDivnCd) {
		this.trdTypeDivnCd = trdTypeDivnCd;
	}
	public String getProdAttTypFg() {
		return prodAttTypFg;
	}
	public void setProdAttTypFg(String prodAttTypFg) {
		this.prodAttTypFg = prodAttTypFg;
	}
	public String getFixSrcmkCd() {
		return fixSrcmkCd;
	}
	public void setFixSrcmkCd(String fixSrcmkCd) {
		this.fixSrcmkCd = fixSrcmkCd;
	}
	public String getProdDivnCd() {
		return prodDivnCd;
	}
	public void setProdDivnCd(String prodDivnCd) {
		this.prodDivnCd = prodDivnCd;
	}
	public String getMixYn() {
		return mixYn;
	}
	public void setMixYn(String mixYn) {
		this.mixYn = mixYn;
	}
	public String getL1Cd() {
		return l1Cd;
	}
	public void setL1Cd(String l1Cd) {
		this.l1Cd = l1Cd;
	}
	public String getCfmFgReason() {
		return cfmFgReason;
	}
	public void setCfmFgReason(String cfmFgReason) {
		this.cfmFgReason = cfmFgReason;
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
	public String getChanCd() {
		return chanCd;
	}
	public void setChanCd(String chanCd) {
		this.chanCd = chanCd;
	}
	
	
}
