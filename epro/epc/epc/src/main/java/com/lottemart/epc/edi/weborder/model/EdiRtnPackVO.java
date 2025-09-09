package com.lottemart.epc.edi.weborder.model;

import java.io.Serializable;

/**
 * @Class Name : EdiRtnPackVO
 * @Description : 업체반품요청 일괄등록 정보 조회
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2014. 08. 021. 오후 2:32:38 jyLee
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */

public class EdiRtnPackVO extends PagingVO implements Serializable {

	

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -207863988104734970L;
	/** 파일구분코드 */
	private String packDivnCd;
	/** 협력업체코드 */
	private String venCd;
	/** 발주일자 */
	private String rrlDy;
	/** 점포코드 */
	private String strCd;
	/** 상품코드 */
	private String prodCd;
	/** 발주등록번호 */
	private String rrlReqNo;
	/** 일괄등록파일명 */
	private String packFileNm;
	/** 판매코드 */
	private String srcmkCd;
	/** 상품명 */
	private String prodNm;
	/** 단축명 */
	private String shortNm;
	/** 상품규격 */
	private String prodStd;
	/** 발주입수 */
	private String ordIpsu;
	/** 발주단위 */
	private String ordUnit;
	/** 발주매가 */
	private String stdSalePrc;
	/** 발주원가 */
	private String stdBuyPrc;
	/** 발주수량 */
	private String rrlQty;
	/** 발주일재고수량 */
	private String rrlStkQty;
	/** 등록상태코드 */
	private String regStsCd;
	/** 등록자아이디 */
	private String regId;
	/** 등록일시 */
	private String regDt;
	/** 수정자아이디 */
	private String modId;
	/** 수정일시 */
	private String modDt;
	/** 파일 그룹 코드*/
	private String fileGrpCd;
	
	/** 점포 갯수 */
	private String strCnt;
	/** 상품 갯수 */
	private String prodCnt;
	/** 발주총수량합계 */
	private String ordTotQtySum;
	/** 발주총금액합계 */
	private String ordTotPrcSum;
	/** 충수량EA합계 */
	private String ordTotAllQtySum;
	/** 점포코드 */
	private String strNm;
	
	
	public String getPackDivnCd() {
		return packDivnCd;
	}
	public void setPackDivnCd(String packDivnCd) {
		this.packDivnCd = packDivnCd;
	}
	public String getVenCd() {
		return venCd;
	}
	public void setVenCd(String venCd) {
		this.venCd = venCd;
	}
	public String getRrlDy() {
		return rrlDy;
	}
	public void setRrlDy(String rrlDy) {
		this.rrlDy = rrlDy;
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
	public String getRrlReqNo() {
		return rrlReqNo;
	}
	public void setRrlReqNo(String rrlReqNo) {
		this.rrlReqNo = rrlReqNo;
	}
	public String getPackFileNm() {
		return packFileNm;
	}
	public void setPackFileNm(String packFileNm) {
		this.packFileNm = packFileNm;
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
	public String getFileGrpCd() {
		return fileGrpCd;
	}
	public void setFileGrpCd(String fileGrpCd) {
		this.fileGrpCd = fileGrpCd;
	}
	public String getStrCnt() {
		return strCnt;
	}
	public void setStrCnt(String strCnt) {
		this.strCnt = strCnt;
	}
	public String getProdCnt() {
		return prodCnt;
	}
	public void setProdCnt(String prodCnt) {
		this.prodCnt = prodCnt;
	}
	public String getOrdTotQtySum() {
		return ordTotQtySum;
	}
	public void setOrdTotQtySum(String ordTotQtySum) {
		this.ordTotQtySum = ordTotQtySum;
	}
	public String getOrdTotPrcSum() {
		return ordTotPrcSum;
	}
	public void setOrdTotPrcSum(String ordTotPrcSum) {
		this.ordTotPrcSum = ordTotPrcSum;
	}
	public String getOrdTotAllQtySum() {
		return ordTotAllQtySum;
	}
	public void setOrdTotAllQtySum(String ordTotAllQtySum) {
		this.ordTotAllQtySum = ordTotAllQtySum;
	}
	public String getStrNm() {
		return strNm;
	}
	public void setStrNm(String strNm) {
		this.strNm = strNm;
	}
	
	
}
