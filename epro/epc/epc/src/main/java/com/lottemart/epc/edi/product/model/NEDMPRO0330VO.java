package com.lottemart.epc.edi.product.model;

import java.io.Serializable;

import com.lottemart.epc.edi.comm.model.PagingVO;


/**
 * @Class Name : NEDMPRO0320VO
 * @Description : 반품 제안 등록 VO
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      	수정자           		수정내용
 *  -------    --------    ---------------------------
 * 2025.03.18  PARK JONG GYU 		최초생성
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */

public class NEDMPRO0330VO extends PagingVO  implements Serializable {

	
	private static final long serialVersionUID = -6918711902569942487L;


	public NEDMPRO0330VO () {}
	
	/** 검색조건-생성일 FROM */
	private String iRgdatFrom;
	
	/** 검색조건-생성일 TO */
	private String iRgdatTo;
	
	/** 공급업체 계정 번호 */
	private String iLifnr;
	
	/** 계약진행상태 */
	private String iDcStat;
	
	/** 부서코드 */
	private String iDepCd;

	/** 작업자 id */
	private String workId;
	
	/** I/F 연계계약번호  */
	private String conNo;
	
	/** ECS계약번호 */
	private String dcNum;
	
	/** 계약진행상태 */
	private String dcStat;
	
	/** 계약진행상태명 */
	private String dcStatTxt;
	
	/** 공급업체 계정 번호 */
	private String lifnr;
	
	/** 부서코드 */
	private String depCd;
	
	/** 구매조직 */
	private String ekorg;
	
	/** 전자결재 상태 */
	private String apprFg;
	
	/** 전자결재 상태명 */
	private String apprFgNm;
	
	/** SEQ */
	private String seq;
	
	/** 판매코드 */
	private String srcmkCd;
	
	/** 상품코드 */
	private String prodCd;
	
	/** 상품명 */
	private String prodNm;
	
	/** 국제물품번호(EAN/UPC) */
	private String ean11;
	
	/** 규격 */
	private String zzprodStd;
	
	/** 단가 */
	private String netpr;
	
	/** 수량 */
	private String menge;
	
	/** 금액 */
	private String dmbtr;
	
	/** 자재순번 */
	private String matSeq;
	
	/** 플랜트 */
	private String werks;
	
	/** 반품사유 */
	private String zretyp;
	
	/** 반품상세사유 */
	private String zretypD;
	
	/** 반품장소 */
	private String zreloc;
	
	/** 수정일시 */
	private String modDate;
	
	/** 수정자 */
	private String modId;
	
	/** 등록일시 */
	private String regDate;
	
	/** 등록자 */
	private String regId;
	
	/** 반품사유 */
	private String zretypNm;
	
	/** 반품상세사유 */
	private String zretypDNm;
	
	/** 반품장소 */
	private String zrelocNm;
	
	/** 공급업체명 */
	private String venNm;
	
	/** 팀명 */
	private String teamNm;
	
	/** MD 협의요청일 */
	private String zrtdate;
	
	public String getZrtdate() {
		return zrtdate;
	}

	public void setZrtdate(String zrtdate) {
		this.zrtdate = zrtdate;
	}

	public String getVenNm() {
		return venNm;
	}

	public void setVenNm(String venNm) {
		this.venNm = venNm;
	}

	public String getTeamNm() {
		return teamNm;
	}

	public void setTeamNm(String teamNm) {
		this.teamNm = teamNm;
	}

	public String getZretypNm() {
		return zretypNm;
	}

	public void setZretypNm(String zretypNm) {
		this.zretypNm = zretypNm;
	}

	public String getZretypDNm() {
		return zretypDNm;
	}

	public void setZretypDNm(String zretypDNm) {
		this.zretypDNm = zretypDNm;
	}

	public String getZrelocNm() {
		return zrelocNm;
	}

	public void setZrelocNm(String zrelocNm) {
		this.zrelocNm = zrelocNm;
	}

	public String getProdNm() {
		return prodNm;
	}

	public void setProdNm(String prodNm) {
		this.prodNm = prodNm;
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

	public String getConNo() {
		return conNo;
	}

	public void setConNo(String conNo) {
		this.conNo = conNo;
	}

	public String getDcNum() {
		return dcNum;
	}

	public void setDcNum(String dcNum) {
		this.dcNum = dcNum;
	}

	public String getDcStat() {
		return dcStat;
	}

	public void setDcStat(String dcStat) {
		this.dcStat = dcStat;
	}

	public String getDcStatTxt() {
		return dcStatTxt;
	}

	public void setDcStatTxt(String dcStatTxt) {
		this.dcStatTxt = dcStatTxt;
	}

	public String getLifnr() {
		return lifnr;
	}

	public void setLifnr(String lifnr) {
		this.lifnr = lifnr;
	}

	public String getDepCd() {
		return depCd;
	}

	public void setDepCd(String depCd) {
		this.depCd = depCd;
	}

	public String getEkorg() {
		return ekorg;
	}

	public void setEkorg(String ekorg) {
		this.ekorg = ekorg;
	}

	public String getApprFg() {
		return apprFg;
	}

	public void setApprFg(String apprFg) {
		this.apprFg = apprFg;
	}

	public String getApprFgNm() {
		return apprFgNm;
	}

	public void setApprFgNm(String apprFgNm) {
		this.apprFgNm = apprFgNm;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getSrcmkCd() {
		return srcmkCd;
	}

	public void setSrcmkCd(String srcmkCd) {
		this.srcmkCd = srcmkCd;
	}

	public String getProdCd() {
		return prodCd;
	}

	public void setProdCd(String prodCd) {
		this.prodCd = prodCd;
	}

	public String getEan11() {
		return ean11;
	}

	public void setEan11(String ean11) {
		this.ean11 = ean11;
	}

	public String getZzprodStd() {
		return zzprodStd;
	}

	public void setZzprodStd(String zzprodStd) {
		this.zzprodStd = zzprodStd;
	}

	public String getNetpr() {
		return netpr;
	}

	public void setNetpr(String netpr) {
		this.netpr = netpr;
	}

	public String getMenge() {
		return menge;
	}

	public void setMenge(String menge) {
		this.menge = menge;
	}

	public String getDmbtr() {
		return dmbtr;
	}

	public void setDmbtr(String dmbtr) {
		this.dmbtr = dmbtr;
	}

	public String getMatSeq() {
		return matSeq;
	}

	public void setMatSeq(String matSeq) {
		this.matSeq = matSeq;
	}

	public String getWerks() {
		return werks;
	}

	public void setWerks(String werks) {
		this.werks = werks;
	}

	public String getZretyp() {
		return zretyp;
	}

	public void setZretyp(String zretyp) {
		this.zretyp = zretyp;
	}

	public String getZretypD() {
		return zretypD;
	}

	public void setZretypD(String zretypD) {
		this.zretypD = zretypD;
	}

	public String getZreloc() {
		return zreloc;
	}

	public void setZreloc(String zreloc) {
		this.zreloc = zreloc;
	}

	public String getWorkId() {
		return workId;
	}

	public void setWorkId(String workId) {
		this.workId = workId;
	}

	public String getiRgdatFrom() {
		return iRgdatFrom;
	}

	public void setiRgdatFrom(String iRgdatFrom) {
		this.iRgdatFrom = iRgdatFrom;
	}

	public String getiRgdatTo() {
		return iRgdatTo;
	}

	public void setiRgdatTo(String iRgdatTo) {
		this.iRgdatTo = iRgdatTo;
	}

	public String getiLifnr() {
		return iLifnr;
	}

	public void setiLifnr(String iLifnr) {
		this.iLifnr = iLifnr;
	}

	public String getiDcStat() {
		return iDcStat;
	}

	public void setiDcStat(String iDcStat) {
		this.iDcStat = iDcStat;
	}

	public String getiDepCd() {
		return iDepCd;
	}

	public void setiDepCd(String iDepCd) {
		this.iDepCd = iDepCd;
	}
	
	
	
}
