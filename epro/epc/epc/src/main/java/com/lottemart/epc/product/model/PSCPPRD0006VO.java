package com.lottemart.epc.product.model;


import java.io.Serializable;

/**
 * @Class Name : PSCPPRD0006VO.java
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
public class PSCPPRD0006VO implements Serializable {
	private static final long serialVersionUID = -7227574059487470346L;

	private String prodCd = "";
	private String itemCd = "";
	private String mdSrcmkCd = "";
	private String prodImagePath = "";
	private String title = "";
	private String colorNm = "";
	private String sizeNm = "";
	private String maxRow = "";
	private String rowCnt = "";
	private String deleteRow = "";
	private String img60 = "";
	private String img75 = "";
	private String img100 = "";
	private String img160 = "";
	private String img250 = "";
	private String img500 = "";
	private String img720 = "";
	private String regId = "";
	private String modId = "";
	private String imgQty = "";
	private String videoUrl = "";
	private String seq = "";
	

	//<<< 2015.12.01 by kmlee 상품 속성체계 변경으로 변수 추가
	private String classNm = "";
	private String attrsNm = "";
	//>>>

	
	public String getProdCd() {
		return prodCd;
	}
	public void setProdCd(String prodCd) {
		this.prodCd = prodCd;
	}
	public String getItemCd() {
		return itemCd;
	}
	public void setItemCd(String itemCd) {
		this.itemCd = itemCd;
	}
	public String getMdSrcmkCd() {
		return mdSrcmkCd;
	}
	public void setMdSrcmkCd(String mdSrcmkCd) {
		this.mdSrcmkCd = mdSrcmkCd;
	}
	public String getProdImagePath() {
		return prodImagePath;
	}
	public void setProdImagePath(String prodImagePath) {
		this.prodImagePath = prodImagePath;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getColorNm() {
		return colorNm;
	}
	public void setColorNm(String colorNm) {
		this.colorNm = colorNm;
	}
	public String getSizeNm() {
		return sizeNm;
	}
	public void setSizeNm(String sizeNm) {
		this.sizeNm = sizeNm;
	}
	public String getMaxRow() {
		return maxRow;
	}
	public void setMaxRow(String maxRow) {
		this.maxRow = maxRow;
	}
	public String getRowCnt() {
		return rowCnt;
	}
	public void setRowCnt(String rowCnt) {
		this.rowCnt = rowCnt;
	}
	public String getDeleteRow() {
		return deleteRow;
	}
	public void setDeleteRow(String deleteRow) {
		this.deleteRow = deleteRow;
	}
	public String getImg60() {
		return img60;
	}
	public void setImg60(String img60) {
		this.img60 = img60;
	}
	public String getImg75() {
		return img75;
	}
	public void setImg75(String img75) {
		this.img75 = img75;
	}
	public String getImg100() {
		return img100;
	}
	public void setImg100(String img100) {
		this.img100 = img100;
	}
	public String getImg160() {
		return img160;
	}
	public void setImg160(String img160) {
		this.img160 = img160;
	}
	public String getImg250() {
		return img250;
	}
	public void setImg250(String img250) {
		this.img250 = img250;
	}
	public String getImg500() {
		return img500;
	}
	public void setImg500(String img500) {
		this.img500 = img500;
	}
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	public String getModId() {
		return modId;
	}
	public void setModId(String modId) {
		this.modId = modId;
	}
	public String getImgQty() {
		return imgQty;
	}
	public void setImgQty(String imgQty) {
		this.imgQty = imgQty;
	}
	public String getVideoUrl() {
		return videoUrl;
	}
	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
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
	
	public String getImg720() {
		return img720;
	}
	public void setImg720(String img720) {
		this.img720 = img720;
	}
	//>>>

}
