package com.lottemart.epc.edi.weborder.model;

import java.io.Serializable;


/**
 * @author jyLee
 * @Description : EXT_발주지원정보
 * @Class : TedOrdSuppInfoVO
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      		수정자 		          수정내용
 *  -------    --------    ---------------------------
 *  2014.08.07	jyLee	최초작성
 * </pre>
 * @version : 1.0
 */

public class TedOrdSuppInfoVO extends PagingVO implements Serializable {



	/**
	 *
	 */
	private static final long serialVersionUID = -8773447691986798470L;
	/** 점포코드 */
	private String strCd;
	/** 발주일자 */
	private String ordDy;
	/** 상품코드 */
	private String prodCd;
	/** 상품명 */
	private String prodNm;
	/** 단축명 */
	private String shortNm;
	/** 협력업체코드 */
	private String venCd;
	/** 협력업체명 */
	private String venNm;
	/** 판매코드 */
	private String srcmkCd;
	/** 행사코드 */
	private String evtCd;
	/** 상품패턴구분 */
	private String prodPatFg;
	/** 상품규격 */
	private String prodStd;
	/** 환산치 */
	private String convRt;
	/** 발주입수 */
	private String ordIpsu;
	/** 발주원가 */
	private String ordBuyPrc;
	/** 발주매가 */
	private String ordSalePrc;
	/** 발주단위 */
	private String ordUnit;
	/** 권고발주량 */
	private String rcmdOrdQty;
	/** 거래형태구분 */
	private String trdTypFg;
	/** 루트구분 */
	private String routeFg;
	/** 센터입하일자 */
	private String ctrArrDy;
	/** 점포납품일자 */
	private String strSplyDy;

	public String getStrCd() {
		return strCd;
	}
	public void setStrCd(String strCd) {
		this.strCd = strCd;
	}
	public String getOrdDy() {
		return ordDy;
	}
	public void setOrdDy(String ordDy) {
		this.ordDy = ordDy;
	}
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
	public String getShortNm() {
		return shortNm;
	}
	public void setShortNm(String shortNm) {
		this.shortNm = shortNm;
	}
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
	public String getSrcmkCd() {
		return srcmkCd;
	}
	public void setSrcmkCd(String srcmkCd) {
		this.srcmkCd = srcmkCd;
	}
	public String getEvtCd() {
		return evtCd;
	}
	public void setEvtCd(String evtCd) {
		this.evtCd = evtCd;
	}
	public String getProdPatFg() {
		return prodPatFg;
	}
	public void setProdPatFg(String prodPatFg) {
		this.prodPatFg = prodPatFg;
	}
	public String getProdStd() {
		return prodStd;
	}
	public void setProdStd(String prodStd) {
		this.prodStd = prodStd;
	}
	public String getConvRt() {
		return convRt;
	}
	public void setConvRt(String convRt) {
		this.convRt = convRt;
	}
	public String getOrdIpsu() {
		return ordIpsu;
	}
	public void setOrdIpsu(String ordIpsu) {
		this.ordIpsu = ordIpsu;
	}
	public String getOrdBuyPrc() {
		return ordBuyPrc;
	}
	public void setOrdBuyPrc(String ordBuyPrc) {
		this.ordBuyPrc = ordBuyPrc;
	}
	public String getOrdSalePrc() {
		return ordSalePrc;
	}
	public void setOrdSalePrc(String ordSalePrc) {
		this.ordSalePrc = ordSalePrc;
	}
	public String getOrdUnit() {
		return ordUnit;
	}
	public void setOrdUnit(String ordUnit) {
		this.ordUnit = ordUnit;
	}
	public String getRcmdOrdQty() {
		return rcmdOrdQty;
	}
	public void setRcmdOrdQty(String rcmdOrdQty) {
		this.rcmdOrdQty = rcmdOrdQty;
	}
	public String getTrdTypFg() {
		return trdTypFg;
	}
	public void setTrdTypFg(String trdTypFg) {
		this.trdTypFg = trdTypFg;
	}
	public String getRouteFg() {
		return routeFg;
	}
	public void setRouteFg(String routeFg) {
		this.routeFg = routeFg;
	}
	public String getCtrArrDy() {
		return ctrArrDy;
	}
	public void setCtrArrDy(String ctrArrDy) {
		this.ctrArrDy = ctrArrDy;
	}
	public String getStrSplyDy() {
		return strSplyDy;
	}
	public void setStrSplyDy(String strSplyDy) {
		this.strSplyDy = strSplyDy;
	}


}
