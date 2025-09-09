package com.lottemart.epc.edi.product.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class PEDMPRO0099VO implements Serializable {

	private static final long serialVersionUID = 6766182773315305924L;

	public PEDMPRO0099VO() {}

	private String logiBarcodeStatus;
	private String logiBarcode;
	private Integer logiBoxIpsu;
	private String regDate;
	private String useFlag;
	private Integer width;
	private Integer length;
	private Integer height;
	private Double weight;
	private String mixProductFlag;
	private String conveyFlag;
	private String sorterFlag;
	private Integer innerIpsu;
	private Integer plateLayerQty;
	private Integer plateHeightQty;
	private Integer totalBoxCount;
	
	private String newProductCode;
	private String seq;
	private String entpCode;
	private String l1GroupCode;
	private String sellCode;
	
	private String codeFlag;
	
	private String productDay;
	private String productName;
	
	private String barcodeDescription;
	
	
	private String imageName;
	private String confirmFlag;
	private String imageSeq;
	private String confirmReasonFlag;
	
	private String regWidth;
	private String regLength;
	private String regHeight;
	
	
	
	private String categroyName;
	private String sizeName;
	private String colorName;
	
	
	
	private String szCatCode;
	private String szCode;
	private String colorCode;
	
	private String wUseFlag;
	
	private String l4Cd;
	private String venCd;
	
	private String sapL3Cd;
	private String sapL3Nm;
	
	private String codeId;
	private String codeNm;
	
	private String chk;
	private String attrValTxt;
	
	private String rn;
	
	private String sapL3CdCnt;
	private String totCnt;
	private String inputCnt;
	private String cfmFg;
	
	private String ran;
	
	private String grpAttTotCnt;
	private String martAttTotCnt;
	
	/** 2016.03.07 추가 by song min kyo */
	private String srchProdGbn;		//상품구분 검색조건
	private String hvFg;			//상품구분[List에서 보여질 txt]
	private String grpCd;			//그룹소분류
	private String x00816Cnt;		//'해당사항없음'이라고 입력된 속성 카운트
	private String grpCdNm;			//그룹소분류 명칭
	
	public String getSapL3Cd() {
		return sapL3Cd;
	}
	public void setSapL3Cd(String sapL3Cd) {
		this.sapL3Cd = sapL3Cd;
	}
	public String getSapL3Nm() {
		return sapL3Nm;
	}
	public void setSapL3Nm(String sapL3Nm) {
		this.sapL3Nm = sapL3Nm;
	}
	public String getVenCd() {
		return venCd;
	}
	public void setVenCd(String venCd) {
		this.venCd = venCd;
	}
	public String getSzCatCode() {
		return szCatCode;
	}
	public void setSzCatCode(String szCatCode) {
		this.szCatCode = szCatCode;
	}
	public String getSzCode() {
		return szCode;
	}
	public void setSzCode(String szCode) {
		this.szCode = szCode;
	}
	public String getColorCode() {
		return colorCode;
	}
	public void setColorCode(String colorCode) {
		this.colorCode = colorCode;
	}
	public String getCategroyName() {
		return categroyName;
	}
	public void setCategroyName(String categroyName) {
		this.categroyName = categroyName;
	}
	public String getSizeName() {
		return sizeName;
	}
	public void setSizeName(String sizeName) {
		this.sizeName = sizeName;
	}
	public String getColorName() {
		return colorName;
	}
	public void setColorName(String colorName) {
		this.colorName = colorName;
	}
	public String getRegWidth() {
		return regWidth;
	}
	public void setRegWidth(String regWidth) {
		this.regWidth = regWidth;
	}
	public String getRegLength() {
		return regLength;
	}
	public void setRegLength(String regLength) {
		this.regLength = regLength;
	}
	public String getRegHeight() {
		return regHeight;
	}
	public void setRegHeight(String regHeight) {
		this.regHeight = regHeight;
	}
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	public String getConfirmFlag() {
		return confirmFlag;
	}
	public void setConfirmFlag(String confirmFlag) {
		this.confirmFlag = confirmFlag;
	}
	public String getImageSeq() {
		return imageSeq;
	}
	public void setImageSeq(String imageSeq) {
		this.imageSeq = imageSeq;
	}
	public String getConfirmReasonFlag() {
		return confirmReasonFlag;
	}
	public void setConfirmReasonFlag(String confirmReasonFlag) {
		this.confirmReasonFlag = confirmReasonFlag;
	}
	public String getBarcodeDescription() {
		return barcodeDescription;
	}
	public void setBarcodeDescription(String barcodeDescription) {
		this.barcodeDescription = barcodeDescription;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductDay() {
		return productDay;
	}
	public void setProductDay(String productDay) {
		this.productDay = productDay;
	}
	public String getCodeFlag() {
		return StringUtils.defaultIfEmpty(codeFlag, "0");
	}
	public void setCodeFlag(String codeFlag) {
		this.codeFlag = codeFlag;
	}
	public String getSellCode() {
		return sellCode;
	}
	public void setSellCode(String sellCode) {
		this.sellCode = sellCode;
	}
	public String getLogiBarcodeStatus() {
		return logiBarcodeStatus;
	}
	public void setLogiBarcodeStatus(String logiBarcodeStatus) {
		this.logiBarcodeStatus = logiBarcodeStatus;
	}
	public String getLogiBarcode() {
		return logiBarcode;
	}
	public void setLogiBarcode(String logiBarcode) {
		this.logiBarcode = logiBarcode;
	}
	public Integer getLogiBoxIpsu() {
		return logiBoxIpsu;
	}
	public void setLogiBoxIpsu(Integer logiBoxIpsu) {
		this.logiBoxIpsu = logiBoxIpsu;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getUseFlag() {
		return useFlag;
	}
	public void setUseFlag(String useFlag) {
		this.useFlag = useFlag;
	}
	public Integer getWidth() {
		return width;
	}
	public void setWidth(Integer width) {
		this.width = width;
	}
	public Integer getLength() {
		return length;
	}
	public void setLength(Integer length) {
		this.length = length;
	}
	public Integer getHeight() {
		return height;
	}
	public void setHeight(Integer height) {
		this.height = height;
	}
	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	public String getMixProductFlag() {
		return mixProductFlag;
	}
	public void setMixProductFlag(String mixProductFlag) {
		this.mixProductFlag = mixProductFlag;
	}
	public String getConveyFlag() {
		return conveyFlag;
	}
	public void setConveyFlag(String conveyFlag) {
		this.conveyFlag = conveyFlag;
	}
	public String getSorterFlag() {
		return sorterFlag;
	}
	public void setSorterFlag(String sorterFlag) {
		this.sorterFlag = sorterFlag;
	}
	public Integer getInnerIpsu() {
		return innerIpsu;
	}
	public void setInnerIpsu(Integer innerIpsu) {
		this.innerIpsu = innerIpsu;
	}
	public Integer getPlateLayerQty() {
		return plateLayerQty;
	}
	public void setPlateLayerQty(Integer plateLayerQty) {
		this.plateLayerQty = plateLayerQty;
	}
	public Integer getPlateHeightQty() {
		return plateHeightQty;
	}
	public void setPlateHeightQty(Integer plateHeightQty) {
		this.plateHeightQty = plateHeightQty;
	}
	public Integer getTotalBoxCount() {
		return totalBoxCount;
	}
	public void setTotalBoxCount(Integer totalBoxCount) {
		this.totalBoxCount = totalBoxCount;
	}
	public String getNewProductCode() {
		return newProductCode;
	}
	public void setNewProductCode(String newProductCode) {
		this.newProductCode = newProductCode;
	}
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getEntpCode() {
		return entpCode;
	}
	public void setEntpCode(String entpCode) {
		this.entpCode = entpCode;
	}
	public String getL1GroupCode() {
		return l1GroupCode;
	}
	public void setL1GroupCode(String l1GroupCode) {
		this.l1GroupCode = l1GroupCode;
	}
	
	
	public String getwUseFlag() {
		return wUseFlag;
	}
	public void setwUseFlag(String wUseFlag) {
		this.wUseFlag = wUseFlag;
	}
	public String getL4Cd() {
		return l4Cd;
	}
	public void setL4Cd(String l4Cd) {
		this.l4Cd = l4Cd;
	}
	public String getCodeId() {
		return codeId;
	}
	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}
	public String getCodeNm() {
		return codeNm;
	}
	public void setCodeNm(String codeNm) {
		this.codeNm = codeNm;
	}
	public String getChk() {
		return chk;
	}
	public void setChk(String chk) {
		this.chk = chk;
	}
	public String getAttrValTxt() {
		return attrValTxt;
	}
	public void setAttrValTxt(String attrValTxt) {
		this.attrValTxt = attrValTxt;
	}
	public String getRn() {
		return rn;
	}
	public void setRn(String rn) {
		this.rn = rn;
	}
	public String getSapL3CdCnt() {
		return sapL3CdCnt;
	}
	public void setSapL3CdCnt(String sapL3CdCnt) {
		this.sapL3CdCnt = sapL3CdCnt;
	}
	public String getTotCnt() {
		return totCnt;
	}
	public void setTotCnt(String totCnt) {
		this.totCnt = totCnt;
	}
	public String getInputCnt() {
		return inputCnt;
	}
	public void setInputCnt(String inputCnt) {
		this.inputCnt = inputCnt;
	}
	public String getCfmFg() {
		return cfmFg;
	}
	public void setCfmFg(String cfmFg) {
		this.cfmFg = cfmFg;
	}
	public String getRan() {
		return ran;
	}
	public void setRan(String ran) {
		this.ran = ran;
	}
	public String getGrpAttTotCnt() {
		return grpAttTotCnt;
	}
	public void setGrpAttTotCnt(String grpAttTotCnt) {
		this.grpAttTotCnt = grpAttTotCnt;
	}
	public String getMartAttTotCnt() {
		return martAttTotCnt;
	}
	public void setMartAttTotCnt(String martAttTotCnt) {
		this.martAttTotCnt = martAttTotCnt;
	}
	public String getSrchProdGbn() {
		return srchProdGbn;
	}
	public void setSrchProdGbn(String srchProdGbn) {
		this.srchProdGbn = srchProdGbn;
	}
	public String getHvFg() {
		return hvFg;
	}
	public void setHvFg(String hvFg) {
		this.hvFg = hvFg;
	}
	public String getGrpCd() {
		return grpCd;
	}
	public void setGrpCd(String grpCd) {
		this.grpCd = grpCd;
	}
	public String getX00816Cnt() {
		return x00816Cnt;
	}
	public void setX00816Cnt(String x00816Cnt) {
		this.x00816Cnt = x00816Cnt;
	}
	public String getGrpCdNm() {
		return grpCdNm;
	}
	public void setGrpCdNm(String grpCdNm) {
		this.grpCdNm = grpCdNm;
	}
	
}
