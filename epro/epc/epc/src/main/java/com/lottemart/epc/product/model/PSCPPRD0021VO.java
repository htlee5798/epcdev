package com.lottemart.epc.product.model;

import java.io.Serializable;



/**
 * 
 * @Class Name : PSCPPRD0021VO
 * @Description : 관심상품 관리 PSCPPRD0021VO
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 9. 21. 오후 4:04:07 ksjeong
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
public class PSCPPRD0021VO implements Serializable {  

	private String num = "";
	private String modDate = "";
	private String modId = "";
	private String prodCd = "";
	private String memo = "";
	private String regId = "";
	private String regDate = "";
	private String totalCount = "";
	private String statusCd = "";
	private String typeCd = "";
	private String bosUrl = "";
	private String prodImgNo = "";
	private String seq = "";
		
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
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
	public String getProdCd() {
		return prodCd;
	}
	public void setProdCd(String prodCd) {
		this.prodCd = prodCd;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}
	public String getStatusCd() {
		return statusCd;
	}
	public void setStatusCd(String statusCd) {
		this.statusCd = statusCd;
	}
	public String getTypeCd() {
		return typeCd;
	}
	public void setTypeCd(String typeCd) {
		this.typeCd = typeCd;
	}
	public String getBosUrl() {
		return bosUrl;
	}
	public void setBosUrl(String bosUrl) {
		this.bosUrl = bosUrl;
	}
	public String getProdImgNo() {
		return prodImgNo;
	}
	public void setProdImgNo(String prodImgNo) {
		this.prodImgNo = prodImgNo;
	}
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	
}
