package com.lottemart.epc.board.model;

import java.io.Serializable;

/**
 * @Class Name : PSCMBRD0013VO.java
 * @Description : 상품평 게시판 PSCMBRD0013VO
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2018. 4. 26. mz_kjs       
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */

public class PSCMBRD0015VO implements Serializable {
	private static final long serialVersionUID = 7515696104594344707L;
	
	private String num;
	private String reviewId;
	private String prodCd;
	private String productNm;
	private String reviewContents;
	private String fileUrl;
	private int grade;
	private String useYn;
	private String memberNo;
	private String atchFileId;
	private String userNm;
	private String regId;
	private String modId;
	private String regDt;
	private String modDt;
	private String isImgFile;
	private String mallDivnCd;
	private String loginId;

	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getReviewId() {
		return reviewId;
	}
	public void setReviewId(String reviewId) {
		this.reviewId = reviewId;
	}
	public String getProdCd() {
		return prodCd;
	}
	public void setProdCd(String prodCd) {
		this.prodCd = prodCd;
	}
	public String getProductNm() {
		return productNm;
	}
	public void setProductNm(String productNm) {
		this.productNm = productNm;
	}
	public String getReviewContents() {
		return reviewContents;
	}
	public void setReviewContents(String reviewContents) {
		this.reviewContents = reviewContents;
	}
	public String getFileUrl() {
		return fileUrl;
	}
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	public String getUseYn() {
		return useYn;
	}
	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}
	public String getMemberNo() {
		return memberNo;
	}
	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}
	public String getAtchFileId() {
		return atchFileId;
	}
	public void setAtchFileId(String atchFileId) {
		this.atchFileId = atchFileId;
	}
	public String getUserNm() {
		return userNm;
	}
	public void setUserNm(String userNm) {
		this.userNm = userNm;
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
	public String getRegDt() {
		return regDt;
	}
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}
	public String getModDt() {
		return modDt;
	}
	public void setModDt(String modDt) {
		this.modDt = modDt;
	}
	public String getIsImgFile() {
		return isImgFile;
	}
	public void setIsImgFile(String isImgFile) {
		this.isImgFile = isImgFile;
	}
	public String getMallDivnCd() {
		return mallDivnCd;
	}
	public void setMallDivnCd(String mallDivnCd) {
		this.mallDivnCd = mallDivnCd;
	}
	
}
