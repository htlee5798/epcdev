package com.lottemart.epc.edi.weborder.model;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @Class Name : SearchWebOrder
 * @Description : MARTNIS 반품상품 조회
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2014. 08. 07. 오후 2:32:38 DADA
 *
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */

public class EdiRtnProdVO extends PagingVO implements Serializable {

	private static final Logger logger = LoggerFactory.getLogger(EdiRtnProdVO.class);


	/**
	 *
	 */
	private static final long serialVersionUID = -8873085335583745633L;

	public EdiRtnProdVO() {}

	/**조회상태정보 0:정상, 1:중복, -1:exception*/
	private String state		;
	/**상태 메세지*/
	private String message      ;
	/**작업자아이디*/
	private String workUser		;
	/**반품등록번호*/
	private String rrlReqNo		;
	/**반품수량*/
	private String rrlQty		;

	/**반품등록번호*/
	private String rrlReqNos[]		;
	/**상품코드*/
	private String prodCds[]		;

	/**SP OUT*/
	private String o_ret			;
	private String o_proc_cmt		;


	/**지역코드*/
	private String areaCd		;
	/**점포코드*/
	private String strCd		;
	/**상품코드*/
	private String prodCd		;
	/**판매코드*/
	private String srcmkCd		;
	/**상품명*/
	private String prodNm		;
	/**상품규격*/
	private String prodStd		;
	/**업체코드*/
	private String venCd		;
	/**업체코드명*/
	private String venNm		;
	/**센터유형구분*/
	private String ctrTypFg	  	;
	/**루트구분*/
	private String routeFg		;
	/**발주입수*/
	private String ordIpsu		;
	/**원가-MARTNIS*/
	private String buyPrc		;
	/**매가-MARTNIS*/
	private String salePrc		;


	/**점포카운트*/
	private String strCdCnt			   ;
	/**업체전체상품수*/
	private String rrlTotProdQtySum    ;
	/**반품수량전체*/
	private String rrlTotQtySum        ;
	/**반품금액전체*/
	private String rrlTotPrcSum        ;
	/**점포명*/
	private String strNm               ;
	/**단위*/
	private String ordUnit             ;
	/**매가-EDI*/
	private String stdSalePrc          ;
	/**원가-EID*/
	private String stdBuyPrc           ;
	/**반품환산금액(수량*매가)*/
	private String stdProdPrc          ;
	/**일괄등록상태코드*/
	private String regStsCd            ;
	/**등록상태명칭*/
	private String regStsCdNm          ;
	/**등록상태상세내용*/
	private String regStsCdDetail       ;

	/**반품일자*/
	private String rrlDy			   ;
	/** 재고 수량*/
	private String rrlStkQty;
	private String rrlStkQty2;

	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getWorkUser() {
		return workUser;
	}
	public void setWorkUser(String workUser) {
		this.workUser = workUser;
	}
	public String getRrlReqNo() {
		return rrlReqNo;
	}
	public void setRrlReqNo(String rrlReqNo) {
		this.rrlReqNo = rrlReqNo;
	}
	public String getRrlQty() {
		return rrlQty;
	}
	public void setRrlQty(String rrlQty) {
		this.rrlQty = rrlQty;
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
	public String getProdCd() {
		return prodCd;
	}
	public void setProdCd(String prodCd) {
		this.prodCd = prodCd;
	}
	public String getSrcmkCd() {
		return srcmkCd;
	}

	public Integer getRrlQtyNum() {
		int rtnNum = 0;
		try{
			rtnNum = Integer.parseInt(rrlQty);
		}catch (Exception e) {
			logger.error(e.getMessage());
		}
		return rtnNum;
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
	public String getProdStd() {
		return prodStd;
	}
	public void setProdStd(String prodStd) {
		this.prodStd = prodStd;
	}
	public String getVenCd() {
		return venCd;
	}
	public void setVenCd(String venCd) {
		this.venCd = venCd;
	}
	public String getCtrTypFg() {
		return ctrTypFg;
	}
	public void setCtrTypFg(String ctrTypFg) {
		this.ctrTypFg = ctrTypFg;
	}
	public String getRouteFg() {
		return routeFg;
	}
	public void setRouteFg(String routeFg) {
		this.routeFg = routeFg;
	}
	public String getOrdIpsu() {
		return ordIpsu;
	}
	public void setOrdIpsu(String ordIpsu) {
		this.ordIpsu = ordIpsu;
	}
	public String getBuyPrc() {
		return buyPrc;
	}
	public void setBuyPrc(String buyPrc) {
		this.buyPrc = buyPrc;
	}
	public String getSalePrc() {
		return salePrc;
	}
	public void setSalePrc(String salePrc) {
		this.salePrc = salePrc;
	}
	public String getStrCdCnt() {
		return strCdCnt;
	}
	public void setStrCdCnt(String strCdCnt) {
		this.strCdCnt = strCdCnt;
	}
	public String getRrlTotProdQtySum() {
		return rrlTotProdQtySum;
	}
	public void setRrlTotProdQtySum(String rrlTotProdQtySum) {
		this.rrlTotProdQtySum = rrlTotProdQtySum;
	}
	public String getRrlTotQtySum() {
		return rrlTotQtySum;
	}
	public void setRrlTotQtySum(String rrlTotQtySum) {
		this.rrlTotQtySum = rrlTotQtySum;
	}
	public String getRrlTotPrcSum() {
		return rrlTotPrcSum;
	}
	public void setRrlTotPrcSum(String rrlTotPrcSum) {
		this.rrlTotPrcSum = rrlTotPrcSum;
	}
	public String getStrNm() {
		return strNm;
	}
	public void setStrNm(String strNm) {
		this.strNm = strNm;
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
	public String getStdProdPrc() {
		return stdProdPrc;
	}
	public void setStdProdPrc(String stdProdPrc) {
		this.stdProdPrc = stdProdPrc;
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
	public String getRegStsCdDetail() {
		return regStsCdDetail;
	}
	public void setRegStsCdDetail(String regStsCdDetail) {
		this.regStsCdDetail = regStsCdDetail;
	}
	public String[] getRrlReqNos() {
		return rrlReqNos;
	}
	public void setRrlReqNos(String[] rrlReqNos) {
		this.rrlReqNos = rrlReqNos;
	}
	public String[] getProdCds() {
		return prodCds;
	}
	public void setProdCds(String[] prodCds) {
		this.prodCds = prodCds;
	}
	public String getRrlDy() {
		return rrlDy;
	}
	public void setRrlDy(String rrlDy) {
		this.rrlDy = rrlDy;
	}
	public String getO_ret() {
		return o_ret;
	}
	public void setO_ret(String o_ret) {
		this.o_ret = o_ret;
	}
	public String getO_proc_cmt() {
		return o_proc_cmt;
	}
	public void setO_proc_cmt(String o_proc_cmt) {
		this.o_proc_cmt = o_proc_cmt;
	}
	public String getVenNm() {
		return venNm;
	}
	public void setVenNm(String venNm) {
		this.venNm = venNm;
	}
	public String getRrlStkQty() {
		return rrlStkQty;
	}
	public void setRrlStkQty(String rrlStkQty) {
		this.rrlStkQty = rrlStkQty;
	}
	public String getRrlStkQty2() {
		return rrlStkQty2;
	}
	public void setRrlStkQty2(String rrlStkQty2) {
		this.rrlStkQty2 = rrlStkQty2;
	}











}
