package com.lottemart.epc.edi.product.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.StringUtils;


public class PEDMPRO0009VO implements Serializable {

	
	private static final long serialVersionUID = -8457955703294092619L;

	public PEDMPRO0009VO() {}

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
	private String seq;
	private String entpCode;
	private String l1GroupCode;	
	private String codeFlag;	
	private String productDay;		
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
	
	private String sellCode;	
	private String newProductCode;
	private String productName;
	

	private String rnum;
	public String getRnum() {
		return rnum;
	}
	public void setRnum(String rnum) {
		this.rnum = rnum;
	}

	private String strCd;
	private String strNm;
	private String codeStaus;	
	private String venCd;
	private String regDt;

	

	

	
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
	public String getStrCd() {
		return strCd;
	}
	public void setStrCd(String strCd) {
		this.strCd = strCd;
	}
	public String getStrNm() {
		return strNm;
	}
	public void setStrNm(String strNm) {
		this.strNm = strNm;
	}
	public String getCodeStaus() {
		return codeStaus;
	}
	public void setCodeStaus(String codeStaus) {
		this.codeStaus = codeStaus;
	}
	public String getVenCd() {
		return venCd;
	}
	public void setVenCd(String venCd) {
		this.venCd = venCd;
	}
	public String getRegDt() {
		return regDt;
	}
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}
}