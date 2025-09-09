package com.lottemart.epc.edi.product.model;

import java.io.Serializable;

/**
 * @Class Name : NEDMPRO0026VO
 * @Description : 소분류별 이미지 등록제한 관리정보 VO
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>O
 *   
 *   수정일      			수정자           		수정내용
 *  -------    		--------    	---------------------------
 * 	2016.04.19 	SONG MIN KYO	최초생성
 * </pre>
 */
public class NEDMPRO0026VO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6696796372653463865L;
		
	private String l3Cd;			//소분류
	private String l1Cd;			//대분류
	private String l2Cd;			//중분류
	private String onlineImgFg;		//온라인 이미지 필수여부[x:필수, NULL:미필수]
	private String pogImgFg;		//pog 이미지 필수여부[X:필수, NULL:미필수]
	private String dpBaseQty;		//표시기준수량
	private String dpUnitCd;		//표시기준단위
	private String delFg ;			//삭제여부(X:삭제, NULL:정상)
	
	public String getL3Cd() {
		return l3Cd;
	}
	public void setL3Cd(String l3Cd) {
		this.l3Cd = l3Cd;
	}
	public String getL1Cd() {
		return l1Cd;
	}
	public void setL1Cd(String l1Cd) {
		this.l1Cd = l1Cd;
	}
	public String getL2Cd() {
		return l2Cd;
	}
	public void setL2Cd(String l2Cd) {
		this.l2Cd = l2Cd;
	}
	public String getOnlineImgFg() {
		return onlineImgFg;
	}
	public void setOnlineImgFg(String onlineImgFg) {
		this.onlineImgFg = onlineImgFg;
	}
	public String getPogImgFg() {
		return pogImgFg;
	}
	public void setPogImgFg(String pogImgFg) {
		this.pogImgFg = pogImgFg;
	}
	public String getDpBaseQty() {
		return dpBaseQty;
	}
	public void setDpBaseQty(String dpBaseQty) {
		this.dpBaseQty = dpBaseQty;
	}
	public String getDpUnitCd() {
		return dpUnitCd;
	}
	public void setDpUnitCd(String dpUnitCd) {
		this.dpUnitCd = dpUnitCd;
	}
	public String getDelFg() {
		return delFg;
	}
	public void setDelFg(String delFg) {
		this.delFg = delFg;
	}
}
