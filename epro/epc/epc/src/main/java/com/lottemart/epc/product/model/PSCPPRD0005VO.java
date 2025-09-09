package com.lottemart.epc.product.model;

import java.io.Serializable;

/**
 * @Class Name : PSCPPRD0005VO.java
 * @Description :
 * @Modification Information
 * 
 *               <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 20. 오후 02:02:02 mjChoi
 * 
 * &#64;Copyright (C) 2000 ~ 2011 롯데정보통신(주) All right reserved.
 *               </pre>
 */
public class PSCPPRD0005VO implements Serializable {
	private static final long serialVersionUID = 4263711195151963690L;

	private String prodCd = "";
	private String seq = "";
	private String title = "";
	private String addDesc = "";
	private String imgSubPath = "";
	private String regId = "";
	private String mdSrcmkCd = "";
	private String aprvCd = "";
	private String typeCd = "";
	private String memo = "";
	private String vendorId = "";
	private String aprvId = "";
	private String modId = "";
	private String aprvDate = "";
	private String regDate = "";
	private String modDate = "";
	private String dispTypeCd = ""; // 모바일 상품상세 표시 설정정보 추가 20180523

	public String getProdCd() {
		return prodCd;
	}

	public void setProdCd(String prodCd) {
		this.prodCd = prodCd;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAddDesc() {
		return addDesc;
	}

	public void setAddDesc(String addDesc) {
		this.addDesc = addDesc;
	}

	public String getImgSubPath() {
		return imgSubPath;
	}

	public void setImgSubPath(String imgSubPath) {
		this.imgSubPath = imgSubPath;
	}

	public String getRegId() {
		return regId;
	}

	public void setRegId(String regId) {
		this.regId = regId;
	}

	public String getMdSrcmkCd() {
		return mdSrcmkCd;
	}

	public void setMdSrcmkCd(String mdSrcmkCd) {
		this.mdSrcmkCd = mdSrcmkCd;
	}

	public String getAprvCd() {
		return aprvCd;
	}

	public void setAprvCd(String aprvCd) {
		this.aprvCd = aprvCd;
	}

	public String getTypeCd() {
		return typeCd;
	}

	public void setTypeCd(String typeCd) {
		this.typeCd = typeCd;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getVendorId() {
		return vendorId;
	}

	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}

	public String getAprvId() {
		return aprvId;
	}

	public void setAprvId(String aprvId) {
		this.aprvId = aprvId;
	}

	public String getModId() {
		return modId;
	}

	public void setModId(String modId) {
		this.modId = modId;
	}

	public String getAprvDate() {
		return aprvDate;
	}

	public void setAprvDate(String aprvDate) {
		this.aprvDate = aprvDate;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public String getModDate() {
		return modDate;
	}

	public void setModDate(String modDate) {
		this.modDate = modDate;
	}

	public String getDispTypeCd() {
		return dispTypeCd;
	}

	public void setDispTypeCd(String dispTypeCd) {
		this.dispTypeCd = dispTypeCd;
	}

}
