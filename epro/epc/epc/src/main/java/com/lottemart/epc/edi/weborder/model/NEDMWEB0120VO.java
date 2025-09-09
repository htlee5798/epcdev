package com.lottemart.epc.edi.weborder.model;



public class NEDMWEB0120VO {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8359592782520196292L;

	public NEDMWEB0120VO() {
		// TODO Auto-generated constructor stub
	}
	
	//crud파람
	private NEDMWEB0120VO[] insertParam;

	private String rrlReqNo;			//반품등록번호
	private String rrlDy;               //반품일자
	private String prodCd;              //상품코드
	private String srcmkCd;             //판매코드
	private String prodNm;              //상품명
	private String venCd;               //협력업체코드
	private String areaCd;				// 지역코드
	private String strCd;               //점포코드
	private String strNm;               //점포명
	private String ordIpsu;             //발주입수
	private String ordUnit;             //발주단위
	private String stdBuyPrc;           //표준원가
	private String rrlQty;              //반품수량
	private String regStsCd;            //등록상태코드
	private String regStsCdNm;          //등록상태 이름
	private String rrlStkQty;           //반품일재고수량
	private String rrlTotPrc;           //반품일재고 총 수량
	
	public NEDMWEB0120VO[] getInsertParam() {
		return insertParam;
	}
	public void setInsertParam(NEDMWEB0120VO[] insertParam) {
		this.insertParam = insertParam;
	}
	public String getRrlReqNo() {
		return rrlReqNo;
	}
	public void setRrlReqNo(String rrlReqNo) {
		this.rrlReqNo = rrlReqNo;
	}
	public String getRrlDy() {
		return rrlDy;
	}
	public void setRrlDy(String rrlDy) {
		this.rrlDy = rrlDy;
	}
	public String getProdCd() {
		return prodCd;
	}
	public void setProdCd(String prodCd) {
		this.prodCd = prodCd;
	}
	public String getSrcmkCd() {
		return srcmkCd;
	}
	public void setSrcmkCd(String srcmkCd) {
		this.srcmkCd = srcmkCd;
	}
	public String getProdNm() {
		return prodNm;
	}
	public void setProdNm(String prodNm) {
		this.prodNm = prodNm;
	}
	public String getVenCd() {
		return venCd;
	}
	public void setVenCd(String venCd) {
		this.venCd = venCd;
	}
	public String getAreaCd() {
		return areaCd;
	}
	public void setAreaCd(String areaCd) {
		this.areaCd = areaCd;
	}
	public String getStrCd() {
		return strCd;
	}
	public void setStrCd(String strCd) {
		this.strCd = strCd;
	}
	public String getStrNm() {
		return strNm;
	}
	public void setStrNm(String strNm) {
		this.strNm = strNm;
	}
	public String getOrdIpsu() {
		return ordIpsu;
	}
	public void setOrdIpsu(String ordIpsu) {
		this.ordIpsu = ordIpsu;
	}
	public String getOrdUnit() {
		return ordUnit;
	}
	public void setOrdUnit(String ordUnit) {
		this.ordUnit = ordUnit;
	}
	public String getStdBuyPrc() {
		return stdBuyPrc;
	}
	public void setStdBuyPrc(String stdBuyPrc) {
		this.stdBuyPrc = stdBuyPrc;
	}
	public String getRrlQty() {
		return rrlQty;
	}
	public void setRrlQty(String rrlQty) {
		this.rrlQty = rrlQty;
	}
	public String getRegStsCd() {
		return regStsCd;
	}
	public void setRegStsCd(String regStsCd) {
		this.regStsCd = regStsCd;
	}
	public String getRegStsCdNm() {
		return regStsCdNm;
	}
	public void setRegStsCdNm(String regStsCdNm) {
		this.regStsCdNm = regStsCdNm;
	}
	public String getRrlStkQty() {
		return rrlStkQty;
	}
	public void setRrlStkQty(String rrlStkQty) {
		this.rrlStkQty = rrlStkQty;
	}
	public String getRrlTotPrc() {
		return rrlTotPrc;
	}
	public void setRrlTotPrc(String rrlTotPrc) {
		this.rrlTotPrc = rrlTotPrc;
	}
	
}
