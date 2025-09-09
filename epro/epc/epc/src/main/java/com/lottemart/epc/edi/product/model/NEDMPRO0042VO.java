package com.lottemart.epc.edi.product.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

/**
 * @Class Name : NEDMPRO0042VO
 * @Description : 임시보관함 판매코드 조회[패션상품] VO
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>O
 *   
 *   수정일      			수정자           		수정내용
 *  -------    		--------    	---------------------------
 * 	2015.12.01 	SONG MIN KYO	최초생성
 * </pre>
 */
public class NEDMPRO0042VO implements Serializable {
	
	private static final long serialVersionUID = 4683759744758821131L;

	private String pgmId;		//상품코드
	private String variant;		//변형구분
	private String sellCd;		//판매코드
	private String attNm;		//속성
	
	/*신상품 오프라인 이미지 등록에 사용하기 위해 추가 2015.12.16 SONG MIN KYO*/
	private String prodImgId;	//속성별 이미지 아이디
		
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
	public String getSellCd() {
		return sellCd;
	}
	public void setSellCd(String sellCd) {
		this.sellCd = sellCd;
	}
	public String getAttNm() {
		return attNm;
	}
	public void setAttNm(String attNm) {
		this.attNm = attNm;
	}
	public String getProdImgId() {
		return prodImgId;
	}
	public void setProdImgId(String prodImgId) {
		this.prodImgId = prodImgId;
	}
	
}
