package com.lottemart.epc.edi.weborder.model;



public class NEDMWEB0110VO {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8359592782520196292L;

	public NEDMWEB0110VO() {
		// TODO Auto-generated constructor stub
	}
	
	//crud파람
	private NEDMWEB0110VO[] insertParam;
	private String venCd; // '협력업체코드';
	private String venNm; // '협력업체명';
	private String strCd; // '점포코드';
	private String strNm; //점포명
	private String rrlDy; // '반품일자';
	private String rrlVseq; // '반품순번';
	private String rrlTotQty; // '반품총수량';
	private String rrlTotProdQty; // '반품상품수';
	private String rrlTotPrc; // '반품총금액';
	private String rrlTotStkQty; // '반품일재고총수량';
	private String rrlPrgsCd; // '반품진행상태코드';
	private String procEmpNo; // '처리사번';
	private String regDt; // '등록일시';
	private String regId; // '등록자아이디';
	private String modDt; // '수정일시';
	private String modId; // '수정자아이디';
	private String smsFg; // 'sms구분';
	
	private String rrlReqNo; // '반품등록번호';            
	private String prodCd; // '상품코드';                   
	private String srcmkCd; // '판매코드';                  
	private String prodNm; // '상품명';                     
	private String shortNm; // '단축명';                    
	private String l4Cd; // '세분류코드';                   
	private String prodStd; // '상품규격';                  
	private String ordIpsu; // '발주입수';                  
	private String ordUnit; // '발주단위';                  
	private String stdSalePrc; // '표준매가';              
	private String stdBuyPrc; // '표준원가';               
	private String rrlQty; // '반품수량';                   
	private String rrlCfmQty; // '반품확정수량';           
	private String rrlStkQty; // '반품일재고수량';         
	private String regStsCd; // '등록상태코드[wpo09]';     
	private String mdModCd; // 'md조정구분코드';           

	
	
	public String getVenCd() {
		return venCd;
	}
	public void setVenCd(String venCd) {
		this.venCd = venCd;
	}
	public String getVenNm() {
		return venNm;
	}
	public void setVenNm(String venNm) {
		this.venNm = venNm;
	}
	public String getStrCd() {
		return strCd;
	}
	public void setStrCd(String strCd) {
		this.strCd = strCd;
	}
	public String getRrlDy() {
		return rrlDy;
	}
	public void setRrlDy(String rrlDy) {
		this.rrlDy = rrlDy;
	}
	public String getRrlVseq() {
		return rrlVseq;
	}
	public void setRrlVseq(String rrlVseq) {
		this.rrlVseq = rrlVseq;
	}
	public String getRegDt() {
		return regDt;
	}
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	public String getModDt() {
		return modDt;
	}
	public void setModDt(String modDt) {
		this.modDt = modDt;
	}
	public String getModId() {
		return modId;
	}
	public void setModId(String modId) {
		this.modId = modId;
	}
	public String getSmsFg() {
		return smsFg;
	}
	public void setSmsFg(String smsFg) {
		this.smsFg = smsFg;
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
	public String getShortNm() {
		return shortNm;
	}
	public void setShortNm(String shortNm) {
		this.shortNm = shortNm;
	}
	public String getL4Cd() {
		return l4Cd;
	}
	public void setL4Cd(String l4Cd) {
		this.l4Cd = l4Cd;
	}
	public String getProdStd() {
		return prodStd;
	}
	public void setProdStd(String prodStd) {
		this.prodStd = prodStd;
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
	public String getStdSalePrc() {
		return stdSalePrc;
	}
	public void setStdSalePrc(String stdSalePrc) {
		this.stdSalePrc = stdSalePrc;
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
	public String getRrlCfmQty() {
		return rrlCfmQty;
	}
	public void setRrlCfmQty(String rrlCfmQty) {
		this.rrlCfmQty = rrlCfmQty;
	}
	public String getRrlStkQty() {
		return rrlStkQty;
	}
	public void setRrlStkQty(String rrlStkQty) {
		this.rrlStkQty = rrlStkQty;
	}
	public String getRegStsCd() {
		return regStsCd;
	}
	public void setRegStsCd(String regStsCd) {
		this.regStsCd = regStsCd;
	}
	public String getMdModCd() {
		return mdModCd;
	}
	public void setMdModCd(String mdModCd) {
		this.mdModCd = mdModCd;
	}
	public String getRrlTotQty() {
		return rrlTotQty;
	}
	public void setRrlTotQty(String rrlTotQty) {
		this.rrlTotQty = rrlTotQty;
	}
	public String getRrlTotProdQty() {
		return rrlTotProdQty;
	}
	public void setRrlTotProdQty(String rrlTotProdQty) {
		this.rrlTotProdQty = rrlTotProdQty;
	}
	public String getRrlTotPrc() {
		return rrlTotPrc;
	}
	public void setRrlTotPrc(String rrlTotPrc) {
		this.rrlTotPrc = rrlTotPrc;
	}
	public String getRrlTotStkQty() {
		return rrlTotStkQty;
	}
	public void setRrlTotStkQty(String rrlTotStkQty) {
		this.rrlTotStkQty = rrlTotStkQty;
	}
	public String getRrlPrgsCd() {
		return rrlPrgsCd;
	}
	public void setRrlPrgsCd(String rrlPrgsCd) {
		this.rrlPrgsCd = rrlPrgsCd;
	}
	public String getProcEmpNo() {
		return procEmpNo;
	}
	public void setProcEmpNo(String procEmpNo) {
		this.procEmpNo = procEmpNo;
	}
	public String getStrNm() {
		return strNm;
	}
	public void setStrNm(String strNm) {
		this.strNm = strNm;
	}
	public String getRrlReqNo() {
		return rrlReqNo;
	}
	public void setRrlReqNo(String rrlReqNo) {
		this.rrlReqNo = rrlReqNo;
	}
	public NEDMWEB0110VO[] getInsertParam() {
		return insertParam;
	}
	public void setInsertParam(NEDMWEB0110VO[] insertParam) {
		this.insertParam = insertParam;
	}
	
	
}
