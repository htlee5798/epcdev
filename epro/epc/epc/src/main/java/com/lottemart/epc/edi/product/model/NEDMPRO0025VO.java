package com.lottemart.epc.edi.product.model;

import java.io.Serializable;

/**
 * @Class Name : NEDMPRO0025VO
 * @Description : 상품 이미지 정보  VO
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>O
 *   
 *   수정일      			수정자           		수정내용
 *  -------    		--------    	---------------------------
 * 	2016.01.14 	SONG MIN KYO	최초생성
 * </pre>
 */
public class NEDMPRO0025VO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9190478721942310069L;
	
	/** 프로그램 ID */
	private String pgmId;
	/** 변형구분 */
	private String variant;
	/** 이미지명 */
	private String imgNm;
	/** 등록일시 */
	private String regDate;
	/** 등록자 */
	private String regId;
	
	public String getPgmId() {
		return pgmId;
	}
	public void setPgmId(String pgmId) {
		this.pgmId = pgmId;
	}
	public String getVariant() {
		return variant;
	}
	public void setVariant(String variant) {
		this.variant = variant;
	}
	public String getImgNm() {
		return imgNm;
	}
	public void setImgNm(String imgNm) {
		this.imgNm = imgNm;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
}
