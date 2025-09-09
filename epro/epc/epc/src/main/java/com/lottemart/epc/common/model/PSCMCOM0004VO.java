package com.lottemart.epc.common.model;


import java.io.Serializable;

/**
 * @Class Name : PSCMCOM0004VO.java
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
public class PSCMCOM0004VO implements Serializable 
{
	private static final long serialVersionUID = -456096558933092504L;
	
	private String num = ""; 			// 순번
	private String categoryId = ""; 	// 카테고리ID
	private String categoryNm = ""; 	// 카테고리명 
	private String fullCategoryNm = ""; 	// 전체카테고리명
	private String stdistExuseYN = ""; 	// 근거리전용여부
	private String startDate = "";		//
	private String endDate = "";		//
	
	private String categoryTypeCd = "";

	// -----------------------------------------------------
	// column 관련
	// -----------------------------------------------------
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	// -----------------------------------------------------
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	// -----------------------------------------------------
	public String getCategoryNm() {
		return categoryNm;
	}
	public void setCategoryNm(String categoryNm) {
		this.categoryNm = categoryNm;
	}
	// -----------------------------------------------------
	public String getStdistExuseYN() {
		return stdistExuseYN;
	}
	public void setStdistExuseYN(String stdistExuseYN) {
		this.stdistExuseYN = stdistExuseYN;
	}
	// -----------------------------------------------------
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	// -----------------------------------------------------
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	// -----------------------------------------------------
	public String getCategoryTypeCd() {
		return categoryTypeCd;
	}
	public void setCategoryTypeCd(String categoryTypeCd) {
		this.categoryTypeCd = categoryTypeCd;
	}
	public String getFullCategoryNm() {
		return fullCategoryNm;
	}
	public void setFullCategoryNm(String fullCategoryNm) {
		this.fullCategoryNm = fullCategoryNm;
	}

}
