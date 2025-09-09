package com.lottemart.epc.edi.product.model;

import java.io.Serializable;

/**
 * @Class Name : NEDMPRO0044VO
 * @Description : 임시보관함 상세정보 온라인전용 88코드, 이익률 VO
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>O
 *   
 *   수정일      			수정자           		수정내용
 *  -------    		--------    	---------------------------
 * 	2015.12.07 	SONG MIN KYO	최초생성
 * </pre>
 */
public class NEDMPRO0044VO implements Serializable {
	
	private static final long serialVersionUID = 3187029770079914152L;
	
	
	private String prodCd;		/** 상품코드 */	
	private String prodNm;		/** 상품명 */
	private String srcmkCd;		/** 판매코드 */
	private String profitRt;	/** 이익률 */
	private String l4Nm;		/** 세분류명 */
	
	public String getProdCd() {
		return prodCd;
	}
	public void setProdCd(String prodCd) {
		this.prodCd = prodCd;
	}
	public String getProdNm() {
		return prodNm;
	}
	public void setProdNm(String prodNm) {
		this.prodNm = prodNm;
	}
	public String getSrcmkCd() {
		return srcmkCd;
	}
	public void setSrcmkCd(String srcmkCd) {
		this.srcmkCd = srcmkCd;
	}
	public String getProfitRt() {
		return profitRt;
	}
	public void setProfitRt(String profitRt) {
		this.profitRt = profitRt;
	}
	public String getL4Nm() {
		return l4Nm;
	}
	public void setL4Nm(String l4Nm) {
		this.l4Nm = l4Nm;
	}
}
