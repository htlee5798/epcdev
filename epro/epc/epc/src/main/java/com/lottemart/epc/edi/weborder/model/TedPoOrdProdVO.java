package com.lottemart.epc.edi.weborder.model;

import java.io.Serializable;

/**
 * @author jyLee
 * @Description : 업체발주요청상품정보
 * @Class : TedPoOrdProdVO
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      		수정자 		          수정내용
 *  -------    --------    ---------------------------
 *  2014.08.07	jyLee	최초작성
 * </pre>
 * @version : 1.0
 */

public class TedPoOrdProdVO extends PagingVO implements Serializable {



	/**
	 *
	 */
	private static final long serialVersionUID = -2675752255729600423L;
	/** 발주등록번호 */
	private String ordReqNo;
	/** 상품코드 */
	private String prodCd;
	/** 발주일자 */
	private String ordDy;
	/** 점포코드 */
	private String strCd;
	/** 협력업체코드 */
	private String venCd;
	/** 판매코드 */
	private String srcmkCd;
	/** 상품명 */
	private String prodNm;
	/** 단축명 */
	private String shortNm;
	/** 세분류코드 */
	private String l4Cd;
	/** 상품규격 */
	private String prodStd;
	/** 발주입수 */
	private String ordIpsu;
	/** 발주단위 */
	private String ordUnit;
	/** 발주매가  */
	private String ordSalePrc;
	/** 발주원가 */
	private String ordBuyPrc;
	/** 발주수량 */
	private String ordQty;
	/** 발주확정수량 */
	private String ordCfmQty;
	/** 발주일재고수량 */
	private String ordStkQty;
	/** 등록상태코드 */
	private String regStsCd;
	/** MD조정구분코드 */
	private String mdModCd;
	/** 등록자아이디 */
	private String regId;
	/** 등록일시 */
	private String regDt;
	/** 수정자아이디 */
	private String modId;
	/** 수정일시 */
	private String modDt;

	public String getOrdReqNo() {
		return ordReqNo;
	}
	public void setOrdReqNo(String ordReqNo) {
		this.ordReqNo = ordReqNo;
	}
	public String getProdCd() {
		return prodCd;
	}
	public void setProdCd(String prodCd) {
		this.prodCd = prodCd;
	}
	public String getOrdDy() {
		return ordDy;
	}
	public void setOrdDy(String ordDy) {
		this.ordDy = ordDy;
	}
	public String getStrCd() {
		return strCd;
	}
	public void setStrCd(String strCd) {
		this.strCd = strCd;
	}
	public String getVenCd() {
		return venCd;
	}
	public void setVenCd(String venCd) {
		this.venCd = venCd;
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
	public String getOrdSalePrc() {
		return ordSalePrc;
	}
	public void setOrdSalePrc(String ordSalePrc) {
		this.ordSalePrc = ordSalePrc;
	}
	public String getOrdBuyPrc() {
		return ordBuyPrc;
	}
	public void setOrdBuyPrc(String ordBuyPrc) {
		this.ordBuyPrc = ordBuyPrc;
	}
	public String getOrdQty() {
		return ordQty;
	}
	public void setOrdQty(String ordQty) {
		this.ordQty = ordQty;
	}
	public String getOrdCfmQty() {
		return ordCfmQty;
	}
	public void setOrdCfmQty(String ordCfmQty) {
		this.ordCfmQty = ordCfmQty;
	}
	public String getOrdStkQty() {
		return ordStkQty;
	}
	public void setOrdStkQty(String ordStkQty) {
		this.ordStkQty = ordStkQty;
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
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	public String getRegDt() {
		return regDt;
	}
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}
	public String getModId() {
		return modId;
	}
	public void setModId(String modId) {
		this.modId = modId;
	}
	public String getModDt() {
		return modDt;
	}
	public void setModDt(String modDt) {
		this.modDt = modDt;
	}



}
