package com.lottemart.epc.edi.ems.model;

import java.io.Serializable;

public class NEDMEMS0010VO implements Serializable {

	static final long serialVersionUID = -4106073758078484593L;
	
	/** 발주일 */
	private String ordDy;
	/** EXE_협력업체코드 */
	private String venCd;
	/** 발주전표수량 */
	private String ordSlipCnt;
	/** 발주SKU */
	private String ordSku;
	/** 발주수량 */
	private String ordQty;
	/** 구매금액 */
	private String ordAmt;
	/** 매입수량 */
	private String buyQty;
	/** (재고)매입금액 */
	private String stkBuyAmt;
	/** 매출량 */
	private String saleQty;
	/** 매가금액 */
	private String saleAmt;
	/** 누계매출수량 */
	private String sumSaleQty;
	/** 누계매출집계 */
	private String sumSaleAmt;
	/** 재고량 */
	private String stkQty;
	/** 재고금액 */
	private String stkAmt;
	/** 매입전표수량 */
	private String buySlipCnt;
	/**  */
	private String buyAmt;
	/** 매입반품전표수량 */
	private String buyRtnSlipCnt;
	/** 매입반품금액 */
	private String buyRtnAmt;
	/** 매입정정전표수량 */
	private String buyChgSlipCnt;
	/** 매입정정금액 */
	private String buyChgAmt;
	/** 미납전표수량 */
	private String usplySlipCnt;
	/** 미납SKU */
	private String usplySku;
	/** 미납수량 */
	private String usplyQty;
	/** 미납금액 */
	private String usplyAmt;
	/** 상호명 */
	private String comNm;
	
	public String getOrdDy() {
		return ordDy;
	}
	public void setOrdDy(String ordDy) {
		this.ordDy = ordDy;
	}
	public String getVenCd() {
		return venCd;
	}
	public void setVenCd(String venCd) {
		this.venCd = venCd;
	}
	public String getOrdSlipCnt() {
		return ordSlipCnt;
	}
	public void setOrdSlipCnt(String ordSlipCnt) {
		this.ordSlipCnt = ordSlipCnt;
	}
	public String getOrdSku() {
		return ordSku;
	}
	public void setOrdSku(String ordSku) {
		this.ordSku = ordSku;
	}
	public String getOrdQty() {
		return ordQty;
	}
	public void setOrdQty(String ordQty) {
		this.ordQty = ordQty;
	}
	public String getOrdAmt() {
		return ordAmt;
	}
	public void setOrdAmt(String ordAmt) {
		this.ordAmt = ordAmt;
	}
	public String getBuyQty() {
		return buyQty;
	}
	public void setBuyQty(String buyQty) {
		this.buyQty = buyQty;
	}
	public String getStkBuyAmt() {
		return stkBuyAmt;
	}
	public void setStkBuyAmt(String stkBuyAmt) {
		this.stkBuyAmt = stkBuyAmt;
	}
	public String getSaleQty() {
		return saleQty;
	}
	public void setSaleQty(String saleQty) {
		this.saleQty = saleQty;
	}
	public String getSaleAmt() {
		return saleAmt;
	}
	public void setSaleAmt(String saleAmt) {
		this.saleAmt = saleAmt;
	}
	public String getSumSaleQty() {
		return sumSaleQty;
	}
	public void setSumSaleQty(String sumSaleQty) {
		this.sumSaleQty = sumSaleQty;
	}
	public String getSumSaleAmt() {
		return sumSaleAmt;
	}
	public void setSumSaleAmt(String sumSaleAmt) {
		this.sumSaleAmt = sumSaleAmt;
	}
	public String getStkQty() {
		return stkQty;
	}
	public void setStkQty(String stkQty) {
		this.stkQty = stkQty;
	}
	public String getStkAmt() {
		return stkAmt;
	}
	public void setStkAmt(String stkAmt) {
		this.stkAmt = stkAmt;
	}
	public String getBuySlipCnt() {
		return buySlipCnt;
	}
	public void setBuySlipCnt(String buySlipCnt) {
		this.buySlipCnt = buySlipCnt;
	}
	public String getBuyAmt() {
		return buyAmt;
	}
	public void setBuyAmt(String buyAmt) {
		this.buyAmt = buyAmt;
	}
	public String getBuyRtnSlipCnt() {
		return buyRtnSlipCnt;
	}
	public void setBuyRtnSlipCnt(String buyRtnSlipCnt) {
		this.buyRtnSlipCnt = buyRtnSlipCnt;
	}
	public String getBuyRtnAmt() {
		return buyRtnAmt;
	}
	public void setBuyRtnAmt(String buyRtnAmt) {
		this.buyRtnAmt = buyRtnAmt;
	}
	public String getBuyChgSlipCnt() {
		return buyChgSlipCnt;
	}
	public void setBuyChgSlipCnt(String buyChgSlipCnt) {
		this.buyChgSlipCnt = buyChgSlipCnt;
	}
	public String getBuyChgAmt() {
		return buyChgAmt;
	}
	public void setBuyChgAmt(String buyChgAmt) {
		this.buyChgAmt = buyChgAmt;
	}
	public String getUsplySlipCnt() {
		return usplySlipCnt;
	}
	public void setUsplySlipCnt(String usplySlipCnt) {
		this.usplySlipCnt = usplySlipCnt;
	}
	public String getUsplySku() {
		return usplySku;
	}
	public void setUsplySku(String usplySku) {
		this.usplySku = usplySku;
	}
	public String getUsplyQty() {
		return usplyQty;
	}
	public void setUsplyQty(String usplyQty) {
		this.usplyQty = usplyQty;
	}
	public String getUsplyAmt() {
		return usplyAmt;
	}
	public void setUsplyAmt(String usplyAmt) {
		this.usplyAmt = usplyAmt;
	}
	public String getComNm() {
		return comNm;
	}
	public void setComNm(String comNm) {
		this.comNm = comNm;
	}

}
