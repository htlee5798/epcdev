package com.lottemart.epc.edi.product.model;

import java.io.Serializable;

/**
 * @Class Name : NEDMPRO0022VO
 * @Description : 전자상거래법 VO
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>O
 *   
 *   수정일      			수정자           		수정내용
 *  -------    		--------    	---------------------------
 * 	2015.12.10 	SONG MIN KYO	최초생성
 * </pre>
 */
public class NEDMPRO0022VO implements Serializable {
	
	private static final long serialVersionUID = -1662705009377284936L;
	
	/** 정보그룹코드 */
	private String infoGrpCd;
	/** 정보컬럼코드 */
	private String infoColCd;
	/** 상품코드 */
	private String prodCd;
	/** 신규상품코드 */
	private String newProdCd;
	/** 컬럼값 */
	private String colVal;
	/** 전시여부 */
	private String dispYn;
	/** 등록일자 */
	private String regDate;
	/** 등록자 */
	private String regId;
	/** 수정일자 */
	private String modDate;
	/** 수정자 */
	private String modId;
	public String getInfoGrpCd() {
		return infoGrpCd;
	}
	public void setInfoGrpCd(String infoGrpCd) {
		this.infoGrpCd = infoGrpCd;
	}
	public String getInfoColCd() {
		return infoColCd;
	}
	public void setInfoColCd(String infoColCd) {
		this.infoColCd = infoColCd;
	}
	public String getProdCd() {
		return prodCd;
	}
	public void setProdCd(String prodCd) {
		this.prodCd = prodCd;
	}
	public String getNewProdCd() {
		return newProdCd;
	}
	public void setNewProdCd(String newProdCd) {
		this.newProdCd = newProdCd;
	}
	public String getColVal() {
		return colVal;
	}
	public void setColVal(String colVal) {
		this.colVal = colVal;
	}
	public String getDispYn() {
		return dispYn;
	}
	public void setDispYn(String dispYn) {
		this.dispYn = dispYn;
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
}
