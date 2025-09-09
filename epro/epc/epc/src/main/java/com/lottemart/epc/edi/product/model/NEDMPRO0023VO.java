package com.lottemart.epc.edi.product.model;

import java.io.Serializable;

/**
 * @Class Name : NEDMPRO0023VO
 * @Description : 상품상세설명  VO
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>O
 *   
 *   수정일      			수정자           		수정내용
 *  -------    		--------    	---------------------------
 * 	2015.12.10 	SONG MIN KYO	최초생성
 * </pre>
 */
public class NEDMPRO0023VO implements Serializable {
	
	private static final long serialVersionUID = 8817461414775383997L;
	
	/** 신규상품코드 */
	private String pgmId;
	/** 순번 */
	private String seq;
	/** 제목 */
	private String title;
	/** 추가설명 */
	private String addDesc;
	/** 등록일자 */
	private String regDate;
	/** 등록자 */
	private String regId;
	/** 수정일자 */
	private String modDate;
	/** 수정자 */
	private String modId;
	public String getPgmId() {
		return pgmId;
	}
	public void setPgmId(String pgmId) {
		this.pgmId = pgmId;
	}
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAddDesc() {
		return addDesc;
	}
	public void setAddDesc(String addDesc) {
		this.addDesc = addDesc;
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
