package com.lottemart.epc.edi.product.model;

import java.io.Serializable;

import com.lottemart.epc.edi.comm.model.PagingVO;

public class NEDMPRO0420VO extends PagingVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5675985509511520736L;

	/** 협력업체 */
	private String srchEntpCd;
	
	/**팀코드*/
	private String srchTeamCd;
	
	/**대분류코드*/
	private String srchL1Cd;
	
	/**중분류코드*/
	private String srchL2Cd;
	
	/**진행상태코드*/
	private String srchStatus;
	
	/**조회 시작날짜*/
	private String srchFromDt;
	
	/**조회 종료날짜*/
	private String srchEndDt;
	
	/**사업구분*/
	private String srchBusinessGb;
	
	/**점포코드*/
	private String srchStoreCd;
	
	/**판매코드*/
	private String srchSellCd;
	
	/**신선구분코드*/
	private String srchFreshCd;
	
	public String getSrchFreshCd() {
		return srchFreshCd;
	}

	public void setSrchFreshCd(String srchFreshCd) {
		this.srchFreshCd = srchFreshCd;
	}

	public String getSrchOrderCd() {
		return srchOrderCd;
	}

	public void setSrchOrderCd(String srchOrderCd) {
		this.srchOrderCd = srchOrderCd;
	}

	public String getSrchProdCd() {
		return srchProdCd;
	}

	public void setSrchProdCd(String srchProdCd) {
		this.srchProdCd = srchProdCd;
	}

	/**발주가능여부*/
	private String srchOrderCd;
	
	/**상품코드*/
	private String srchProdCd;
	
	/**순번*/
	private String rnum;
	
	/**점포코드*/
	private String strCd;

	/**상품코드*/
	private String prodCd;
	
	/**상품명*/
	private String prodNm;
	
	/**등록자*/
	private String regId;
	
	/**등록일시*/
	private String regDt;
	
	/**수정자*/
	private String modId;
	
	/**수정일시*/
	private String modDt;
	
	/** 변경요청번호 */
	private String reqNo;
	
	/**before 마스터 상태*/
	private String beMstStat;
	
	/**before 점포 상태*/
	private String beStrStat;
	
	/**after 마스터 상태*/
	private String afMstStat;
	
	/**after 점포 상태*/
	private String afStrStat;
	
	/**진행상태*/
	private String prStat;
	
	/**반려사유*/
	private String rtnRsn;
	
	/**파트너사 코드*/
	private String entpCd;
	
	/**사업구분*/
	private String businessGb;
	
	/**체크*/
	private String chkProdStat;
	
	/**적용일자*/
	private String applyDt;
	
	/**변경사유*/
	private String chgReason;
	
	/**인터페이스명*/
	private String proxyNm;
	
	/**인터페이스*/
	private String gbn;
	
	/**삭제유무*/
	private String delFg;
	
	/**팀코드*/
	private String teamCd;
	
	/**대분류코드*/
	private String l1Cd;
	
	/**중분류코드*/
	private String l2Cd;
	
	/**소분류코드*/
	private String l3Cd;
	
	/**판매코드*/
	private String srcmkCd;
	
	/**상품변경 상태 이력 테이블을 위한 시퀀스 */
	private int seq;
	
	

	//점포코드 팝업 
	private String strNm;
	
	private String bmanNo;
	
	private String comNm;
	
	
	
	private String  srchFresh;
	private String  srchOrderPossible;

	

	public String getSrchFresh() {
		return srchFresh;
	}

	public void setSrchFresh(String srchFresh) {
		this.srchFresh = srchFresh;
	}

	public String getSrchOrderPossible() {
		return srchOrderPossible;
	}

	public void setSrchOrderPossible(String srchOrderPossible) {
		this.srchOrderPossible = srchOrderPossible;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getL1Cd() {
		return l1Cd;
	}

	public void setL1Cd(String l1Cd) {
		this.l1Cd = l1Cd;
	}

	public String getL2Cd() {
		return l2Cd;
	}

	public void setL2Cd(String l2Cd) {
		this.l2Cd = l2Cd;
	}

	public String getL3Cd() {
		return l3Cd;
	}

	public void setL3Cd(String l3Cd) {
		this.l3Cd = l3Cd;
	}

	public String getSrcmkCd() {
		return srcmkCd;
	}

	public void setSrcmkCd(String srcmkCd) {
		this.srcmkCd = srcmkCd;
	}

	public String getTeamCd() {
		return teamCd;
	}

	public void setTeamCd(String teamCd) {
		this.teamCd = teamCd;
	}

	public String getDelFg() {
		return delFg;
	}

	public void setDelFg(String delFg) {
		this.delFg = delFg;
	}

	public String getProxyNm() {
		return proxyNm;
	}

	public void setProxyNm(String proxyNm) {
		this.proxyNm = proxyNm;
	}

	public String getGbn() {
		return gbn;
	}

	public void setGbn(String gbn) {
		this.gbn = gbn;
	}

	public String getApplyDt() {
		return applyDt;
	}

	public void setApplyDt(String applyDt) {
		this.applyDt = applyDt;
	}

	public String getChgReason() {
		return chgReason;
	}

	public void setChgReason(String chgReason) {
		this.chgReason = chgReason;
	}


	public String getChkProdStat() {
		return chkProdStat;
	}

	public void setChkProdStat(String chkProdStat) {
		this.chkProdStat = chkProdStat;
	}

	public String getEntpCd() {
		return entpCd;
	}

	public void setEntpCd(String entpCd) {
		this.entpCd = entpCd;
	}

	public String getBusinessGb() {
		return businessGb;
	}

	public void setBusinessGb(String businessGb) {
		this.businessGb = businessGb;
	}

	public String getStrNm() {
		return strNm;
	}

	public void setStrNm(String strNm) {
		this.strNm = strNm;
	}

	public String getBmanNo() {
		return bmanNo;
	}

	public void setBmanNo(String bmanNo) {
		this.bmanNo = bmanNo;
	}

	public String getComNm() {
		return comNm;
	}

	public void setComNm(String comNm) {
		this.comNm = comNm;
	}

	public String getReqNo() {
		return reqNo;
	}

	public void setReqNo(String reqNo) {
		this.reqNo = reqNo;
	}

	public String getBeMstStat() {
		return beMstStat;
	}

	public void setBeMstStat(String beMstStat) {
		this.beMstStat = beMstStat;
	}

	public String getBeStrStat() {
		return beStrStat;
	}

	public void setBeStrStat(String beStrStat) {
		this.beStrStat = beStrStat;
	}

	public String getAfMstStat() {
		return afMstStat;
	}

	public void setAfMstStat(String afMstStat) {
		this.afMstStat = afMstStat;
	}

	public String getAfStrStat() {
		return afStrStat;
	}

	public void setAfStrStat(String afStrStat) {
		this.afStrStat = afStrStat;
	}

	public String getPrStat() {
		return prStat;
	}

	public void setPrStat(String prStat) {
		this.prStat = prStat;
	}

	public String getRtnRsn() {
		return rtnRsn;
	}

	public void setRtnRsn(String rtnRsn) {
		this.rtnRsn = rtnRsn;
	}

	public String getRnum() {
		return rnum;
	}

	public void setRnum(String rnum) {
		this.rnum = rnum;
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

	public String getProdNm() {
		return prodNm;
	}

	public void setProdNm(String prodNm) {
		this.prodNm = prodNm;
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

	public String getSrchEntpCd() {
		return srchEntpCd;
	}

	public void setSrchEntpCd(String srchEntpCd) {
		this.srchEntpCd = srchEntpCd;
	}

	public String getSrchTeamCd() {
		return srchTeamCd;
	}

	public void setSrchTeamCd(String srchTeamCd) {
		this.srchTeamCd = srchTeamCd;
	}

	public String getSrchL1Cd() {
		return srchL1Cd;
	}

	public void setSrchL1Cd(String srchL1Cd) {
		this.srchL1Cd = srchL1Cd;
	}

	public String getSrchL2Cd() {
		return srchL2Cd;
	}

	public void setSrchL2Cd(String srchL2Cd) {
		this.srchL2Cd = srchL2Cd;
	}

	public String getSrchStatus() {
		return srchStatus;
	}

	public void setSrchStatus(String srchStatus) {
		this.srchStatus = srchStatus;
	}

	public String getSrchFromDt() {
		return srchFromDt;
	}

	public void setSrchFromDt(String srchFromDt) {
		this.srchFromDt = srchFromDt;
	}

	public String getSrchEndDt() {
		return srchEndDt;
	}

	public void setSrchEndDt(String srchEndDt) {
		this.srchEndDt = srchEndDt;
	}

	public String getSrchBusinessGb() {
		return srchBusinessGb;
	}

	public void setSrchBusinessGb(String srchBusinessGb) {
		this.srchBusinessGb = srchBusinessGb;
	}

	public String getSrchStoreCd() {
		return srchStoreCd;
	}

	public void setSrchStoreCd(String srchStoreCd) {
		this.srchStoreCd = srchStoreCd;
	}

	public String getSrchSellCd() {
		return srchSellCd;
	}

	public void setSrchSellCd(String srchSellCd) {
		this.srchSellCd = srchSellCd;
	}
	
	
}
