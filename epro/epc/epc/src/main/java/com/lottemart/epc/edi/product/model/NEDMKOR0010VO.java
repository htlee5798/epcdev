package com.lottemart.epc.edi.product.model;

import java.io.Serializable;

public class NEDMKOR0010VO implements Serializable {
	
	static final long serialVersionUID = 1140226108515522303L;
	
	/** 상품고유번호 */
	private String pgmId;
	/** 사업자번호 */
	private String bmanNo;
	/** 판매코드 */
	private String srcmkCd;
	/** 인증코드 */
	private String authToken;
	/** 실이미지경로 */
	private String realImgPath;
	/** 변셩순번 */
	private String variant;
	/** 협력업체코드 */
	private String entpCd;
	/** 이미지명 */
	private String imgNm;
	
	public String getPgmId() {
		return pgmId;
	}
	public void setPgmId(String pgmId) {
		this.pgmId = pgmId;
	}
	public String getBmanNo() {
		return bmanNo;
	}
	public void setBmanNo(String bmanNo) {
		this.bmanNo = bmanNo;
	}
	public String getSrcmkCd() {
		return srcmkCd;
	}
	public void setSrcmkCd(String srcmkCd) {
		this.srcmkCd = srcmkCd;
	}
	public String getAuthToken() {
		return authToken;
	}
	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}
	public String getRealImgPath() {
		return realImgPath;
	}
	public void setRealImgPath(String realImgPath) {
		this.realImgPath = realImgPath;
	}
	public String getVariant() {
		return variant;
	}
	public void setVariant(String variant) {
		this.variant = variant;
	}
	public String getEntpCd() {
		return entpCd;
	}
	public void setEntpCd(String entpCd) {
		this.entpCd = entpCd;
	}
	public String getImgNm() {
		return imgNm;
	}
	public void setImgNm(String imgNm) {
		this.imgNm = imgNm;
	}
	
}
