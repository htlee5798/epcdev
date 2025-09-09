package com.lottemart.epc.edi.product.model;

import java.io.Serializable;

/**
 * @Class Name : NEDMPRO0044VO
 * @Description : 임시보관함 상세정보 온라인전용 단품정보 VO
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>O
 *   
 *   수정일      			수정자           		수정내용
 *  -------    		--------    	---------------------------
 * 	2015.12.07 	SONG MIN KYO	최초생성
 * </pre>
 */
public class NEDMPRO0045VO implements Serializable {
	
	private static final long serialVersionUID = 3187029770079914152L;
	
	/** 단품번호 */
	private String itemCd;
	/** 판매코드 */
	private String sellCd;
	/** 옵션설명 */
	private String optnDesc;
	/** 재고관리여부 */
	private String stkMgrYn;
	/** 예약재고수량 */
	private String rservStkQty;
	
	
	public String getItemCd() {
		return itemCd;
	}
	public void setItemCd(String itemCd) {
		this.itemCd = itemCd;
	}
	public String getSellCd() {
		return sellCd;
	}
	public void setSellCd(String sellCd) {
		this.sellCd = sellCd;
	}
	public String getOptnDesc() {
		return optnDesc;
	}
	public void setOptnDesc(String optnDesc) {
		this.optnDesc = optnDesc;
	}
	public String getStkMgrYn() {
		return stkMgrYn;
	}
	public void setStkMgrYn(String stkMgrYn) {
		this.stkMgrYn = stkMgrYn;
	}
	public String getRservStkQty() {
		return rservStkQty;
	}
	public void setRservStkQty(String rservStkQty) {
		this.rservStkQty = rservStkQty;
	}
}
