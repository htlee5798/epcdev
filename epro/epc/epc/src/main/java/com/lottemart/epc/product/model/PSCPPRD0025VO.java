package com.lottemart.epc.product.model;

import java.io.Serializable;

/**
 * @Class Name : PSCPPRD00025VO.java
 * @Description :
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2016. 11. 21. 오후 1:29:23 최성웅
 * 
 * @Copyright (C) 2000 ~ 2011 롯데정보통신(주) All right reserved.
 * </pre>
 */
public class PSCPPRD0025VO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6328533835286239379L;

	private String prodCd		= "";		/* 인터넷상품코드 */
	private String keyword		= "";		/* 키워드 */
	private String keywordString= "";		/* 키워드String */
	private String regId		= "";		/* 등록자 */
	private String modId		= "";		/* 수정자 */
	
	public String getProdCd() {
		return prodCd;
	}
	public void setProdCd(String prodCd) {
		this.prodCd = prodCd;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getKeywordString() {
		return keywordString;
	}
	public void setKeywordString(String keywordString) {
		this.keywordString = keywordString;
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
}
