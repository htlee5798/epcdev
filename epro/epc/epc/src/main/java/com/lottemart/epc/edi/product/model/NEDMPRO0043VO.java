package com.lottemart.epc.edi.product.model;

import java.io.Serializable;

/**
 * @Class Name : NEDMPRO0043VO
 * @Description : 임시보관함 상세정보 변형속성 VO
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>O
 *   
 *   수정일      			수정자           		수정내용
 *  -------    		--------    	---------------------------
 * 	2015.12.07 	SONG MIN KYO	최초생성
 * </pre>
 */
public class NEDMPRO0043VO implements Serializable {
	
	private static final long serialVersionUID = 3187029770079914152L;
	
	private String pgmId;			/** 상품코드 */	
	private String variant;			/** 변형구분 */
	private String varAttClass;		/** 특성프로파일(클래스) */
	private String attCd;			/** 속성구분 */
	private String attValue;		/** 속성값 */
	private String sellCd;			/** 판매코드 */
	private String stsDivnCd;		/** 상태구분코드 */
	private String delTf;			/** 삭제유무 */
	private String prodImgId;		/** 상품이미지아이디 */
	private String optnDesc;		/** 옵션설명 */
		
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
	public String getVarAttClass() {
		return varAttClass;
	}
	public void setVarAttClass(String varAttClass) {
		this.varAttClass = varAttClass;
	}
	public String getAttCd() {
		return attCd;
	}
	public void setAttCd(String attCd) {
		this.attCd = attCd;
	}
	public String getAttValue() {
		return attValue;
	}
	public void setAttValue(String attValue) {
		this.attValue = attValue;
	}
	public String getSellCd() {
		return sellCd;
	}
	public void setSellCd(String sellCd) {
		this.sellCd = sellCd;
	}
	public String getStsDivnCd() {
		return stsDivnCd;
	}
	public void setStsDivnCd(String stsDivnCd) {
		this.stsDivnCd = stsDivnCd;
	}
	public String getDelTf() {
		return delTf;
	}
	public void setDelTf(String delTf) {
		this.delTf = delTf;
	}
	public String getProdImgId() {
		return prodImgId;
	}
	public void setProdImgId(String prodImgId) {
		this.prodImgId = prodImgId;
	}
	public String getOptnDesc() {
		return optnDesc;
	}
	public void setOptnDesc(String optnDesc) {
		this.optnDesc = optnDesc;
	}
}
