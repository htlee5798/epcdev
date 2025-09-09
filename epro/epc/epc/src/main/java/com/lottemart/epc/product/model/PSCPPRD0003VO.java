package com.lottemart.epc.product.model;


import java.io.Serializable;

/**
 * @Class Name : PSCPPRD0003VO.java
 * @Description :
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 20. 오후 02:02:02 mjChoi
 * 
 * @Copyright (C) 2000 ~ 2011 롯데정보통신(주) All right reserved.
 * </pre>
 */
public class PSCPPRD0003VO implements Serializable 
{
	private static final long serialVersionUID = 1048342099037342476L;
	
	private String num = "";
	private String itemCd = "";
	private String optnDesc = "";
	private String itemInfo = "";
	private String prodCd = "";
	private String colorCd = "";
	private String colorNm = "";
	private String szCatCd = "";
	private String szCatNm = "";
	private String szCd = "";
	private String szNm = "";	
	private String regId = "";
	private String strGubun = "";
	//private String asProdCd = "";
	private String mdProdCd = "";
	private String mdSrcmkCd = "";
	private String repCd = "";
	private String prodDivnCd = "";
	private String prodDivnType = "";
	private String rservStkQty = "";
	private String newItemCd = "";

	//<<< 2015.12.01 by kmlee 상품 속성체계 변경으로 변수 추가
	private String classNm = "";
	private String attrsNm = "";
	//>>>
	
	private String attrNm = "";		//속성유형명
	private String attrValNm = "";	//속형값


	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getItemCd() {
		return itemCd;
	}
	public void setItemCd(String itemCd) {
		this.itemCd = itemCd;
	}
	public String getItemInfo() {
		return itemInfo;
	}
	public void setItemInfo(String itemInfo) {
		this.itemInfo = itemInfo;
	}
	public String getProdCd() {
		return prodCd;
	}
	public void setProdCd(String prodCd) {
		this.prodCd = prodCd;
	}
	
	public String getColorCd() {
		return colorCd;
	}
	public void setColorCd(String colorCd) {
		this.colorCd = colorCd;
	}
	public String getColorNm() {
		return colorNm;
	}
	public void setColorNm(String colorNm) {
		this.colorNm = colorNm;
	}
	public String getSzCatCd() {
		return szCatCd;
	}
	public void setSzCatCd(String szCatCd) {
		this.szCatCd = szCatCd;
	}
	public String getSzCatNm() {
		return szCatNm;
	}
	public void setSzCatNm(String szCatNm) {
		this.szCatNm = szCatNm;
	}
	public String getSzCd() {
		return szCd;
	}
	public void setSzCd(String szCd) {
		this.szCd = szCd;
	}
	public String getSzNm() {
		return szNm;
	}
	public void setSzNm(String szNm) {
		this.szNm = szNm;
	}
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	public String getStrGubun() {
		return strGubun;
	}
	public void setStrGubun(String strGubun) {
		this.strGubun = strGubun;
	}
	public String getMdProdCd() {
		return mdProdCd;
	}
	public void setMdProdCd(String mdProdCd) {
		this.mdProdCd = mdProdCd;
	}
	public String getMdSrcmkCd() {
		return mdSrcmkCd;
	}
	public void setMdSrcmkCd(String mdSrcmkCd) {
		this.mdSrcmkCd = mdSrcmkCd;
	}
	public String getRepCd() {
		return repCd;
	}
	public void setRepCd(String repCd) {
		this.repCd = repCd;
	}
	public String getProdDivnCd() {
		return prodDivnCd;
	}
	public void setProdDivnCd(String prodDivnCd) {
		this.prodDivnCd = prodDivnCd;
	}
	public String getProdDivnType() {
		return prodDivnType;
	}
	public void setProdDivnType(String prodDivnType) {
		this.prodDivnType = prodDivnType;
	}
	public String getOptnDesc() {
		return optnDesc;
	}
	public void setOptnDesc(String optnDesc) {
		this.optnDesc = optnDesc;
	}
	public String getRservStkQty() {
		return rservStkQty;
	}
	public void setRservStkQty(String rservStkQty) {
		this.rservStkQty = rservStkQty;
	}
	public String getNewItemCd() {
		return newItemCd;
	}
	public void setNewItemCd(String newItemCd) {
		this.newItemCd = newItemCd;
	}
	
	//<<< 2015.12.01 by kmlee 상품 속성체계 변경으로 변수 추가
	/**
	 * @return the classNm
	 */
	public String getClassNm() {
		return classNm;
	}
	/**
	 * @param classNm the classNm to set
	 */
	public void setClassNm(String classNm) {
		this.classNm = classNm;
	}
	/**
	 * @return the attrsNm
	 */
	public String getAttrsNm() {
		return attrsNm;
	}
	/**
	 * @param attrsNm the attrsNm to set
	 */
	public void setAttrsNm(String attrsNm) {
		this.attrsNm = attrsNm;
	}
	//>>>
	public String getAttrNm() {
		return attrNm;
	}
	public void setAttrNm(String attrNm) {
		this.attrNm = attrNm;
	}
	public String getAttrValNm() {
		return attrValNm;
	}
	public void setAttrValNm(String attrValNm) {
		this.attrValNm = attrValNm;
	}
	
	
}
