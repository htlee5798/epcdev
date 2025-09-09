package com.lottemart.epc.edi.main.model;

import java.io.Serializable;

import com.lcnjf.util.DateUtil;
import com.lottemart.epc.edi.comm.model.PagingVO;


/**
 * @Class Name : MainDashBoardVO
 * @Description : Main 화면 VO
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      	수정자           		수정내용
 *  -------    --------    ---------------------------
 * 2025.04.23  PARK JONG GYU 		최초생성
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */

public class MainDashBoardVO extends PagingVO  implements Serializable {

	
	private static final long serialVersionUID = -6918711902569942487L;


	public MainDashBoardVO () {}
	
	
	/** 일자 */
	private String dy;
	
	/** 파트너사코드 */
	private String venCd;
	
	/** 상품코드 */
	private String prodCd;
	
	/** 상품명 */
	private String prodNm;
	
	/** 채널코드 */
	private String chanCd;
	
	/** 팀코드 */
	private String teamCd;
	
	/** 대분류 */
	private String l1Cd;
	
	/** 중분류 */
	private String l2Cd;
	
	/** 소분류 */
	private String l3Cd;
	
	/** 순매출금액 */
	private String salesAmt;
	
	/** 상품이익 */
	private String prodProfit;
	
	/** 이익율 */
	private String profitRate;
	
	/** 판매량 */
	private String salesQty;
	
	/** 수정일시 */
	private String modDate;
	
	/** 수정자 */
	private String modId;
	
	/** 등록일시 */
	private String regDate;
	
	/** 등록자 */
	private String regId;
	
	/** 판매코드 */
	private String sellCd;
	
	/** 매입금액 */
	private String buyAmt;
	
	/** 매입량 */
	private String buyQty;
	
	/** 총매출금액 */
	private String totSalesAmt;
	
	/** 매출신장율 */
	private String salesRate;
	
	/** 매입신장율 */
	private String buyRate;
	
	/** 행번호 */
	private String rownum;
	
	/** 검색조건 기간 시작일 */
	private String startDy;
	
	/** 검색조건 기간 종료일 */
	private String endDy;
	
	/** 검색조건 판매코드 */
	private String srcmkCd;
	
	/** 진행상태 */
	private String prdtStatus;
	
	/** 팀명 */
	private String teamNm;
	
	/** 대분류 */
	private String l1Nm;
	
	/** 규격 */
	private String prodStandardNm;
	
	/** 원가 */
	private String norProdPcost;
	
	/** 판매가 */
	private String prodSellPrc;
	
	private String purDept;		//구매조직
	private String seq;			//순번
	private String orgCost;		//기존원가
	private String chgReqCost;	//변경원가
	private String prStat;		//원가변경요청 진행상태
	private String prStatNm;	//원가변경요청 진행상태명
	private String reqDt;		//요청일시
	
	private String[] venCds;	//협력업체코드 array
	
	/** 중분류명 */
	private String l2Nm;
	
	/** 소분류명 */
	private String l3Nm;
	
	private String ordNo;		//발주번호
	private String rcvDt;		//입고예정일
	private String nonPayRsrn;	//미납사유
	private String nonPaySt;	//귀책
	private String nonPayAmt;	//미납금액
	
	private String prodGrd;		//PLC 등급
	private String prodSts;		//상품상태
	private String prodStsNm;	//상품상태명
	
	private String isuStore;	//발생지점
	private String isuType;		//발생유형
	
	private String totRowYn;	//총계행여부
	
	private int page;			//현재페이지
	private int rows;			//페이지별 행
	private int startRowNo;		//조회시작번호
	private int endRowNo;		//조회끝번호
	
	
	public String getPrdtStatus() {
		return prdtStatus;
	}

	public void setPrdtStatus(String prdtStatus) {
		this.prdtStatus = prdtStatus;
	}

	public String getTeamNm() {
		return teamNm;
	}

	public void setTeamNm(String teamNm) {
		this.teamNm = teamNm;
	}

	public String getL1Nm() {
		return l1Nm;
	}

	public void setL1Nm(String l1Nm) {
		this.l1Nm = l1Nm;
	}

	public String getProdStandardNm() {
		return prodStandardNm;
	}

	public void setProdStandardNm(String prodStandardNm) {
		this.prodStandardNm = prodStandardNm;
	}

	public String getNorProdPcost() {
		return norProdPcost;
	}

	public void setNorProdPcost(String norProdPcost) {
		this.norProdPcost = norProdPcost;
	}

	public String getProdSellPrc() {
		return prodSellPrc;
	}

	public void setProdSellPrc(String prodSellPrc) {
		this.prodSellPrc = prodSellPrc;
	}

	public String getSrcmkCd() {
		return srcmkCd;
	}

	public void setSrcmkCd(String srcmkCd) {
		this.srcmkCd = srcmkCd;
	}

	public String getStartDy() {
		return startDy;
	}

	public void setStartDy(String startDy) {
		this.startDy = startDy;
	}

	public String getEndDy() {
		return endDy;
	}

	public void setEndDy(String endDy) {
		this.endDy = endDy;
	}

	public String getProdNm() {
		return prodNm;
	}

	public void setProdNm(String prodNm) {
		this.prodNm = prodNm;
	}

	public String getRownum() {
		return rownum;
	}

	public void setRownum(String rownum) {
		this.rownum = rownum;
	}

	public String getDy() {
		return dy;
	}

	public void setDy(String dy) {
		this.dy = dy;
	}

	public String getVenCd() {
		return venCd;
	}

	public void setVenCd(String venCd) {
		this.venCd = venCd;
	}

	public String getProdCd() {
		return prodCd;
	}

	public void setProdCd(String prodCd) {
		this.prodCd = prodCd;
	}

	public String getChanCd() {
		return chanCd;
	}

	public void setChanCd(String chanCd) {
		this.chanCd = chanCd;
	}

	public String getTeamCd() {
		return teamCd;
	}

	public void setTeamCd(String teamCd) {
		this.teamCd = teamCd;
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

	public String getSalesAmt() {
		return salesAmt;
	}

	public void setSalesAmt(String salesAmt) {
		this.salesAmt = salesAmt;
	}

	public String getProdProfit() {
		return prodProfit;
	}

	public void setProdProfit(String prodProfit) {
		this.prodProfit = prodProfit;
	}

	public String getProfitRate() {
		return profitRate;
	}

	public void setProfitRate(String profitRate) {
		this.profitRate = profitRate;
	}

	public String getSalesQty() {
		return salesQty;
	}

	public void setSalesQty(String salesQty) {
		this.salesQty = salesQty;
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

	public String getSellCd() {
		return sellCd;
	}

	public void setSellCd(String sellCd) {
		this.sellCd = sellCd;
	}

	public String getBuyAmt() {
		return buyAmt;
	}

	public void setBuyAmt(String buyAmt) {
		this.buyAmt = buyAmt;
	}

	public String getBuyQty() {
		return buyQty;
	}

	public void setBuyQty(String buyQty) {
		this.buyQty = buyQty;
	}

	public String getTotSalesAmt() {
		return totSalesAmt;
	}

	public void setTotSalesAmt(String totSalesAmt) {
		this.totSalesAmt = totSalesAmt;
	}

	public String getSalesRate() {
		return salesRate;
	}

	public void setSalesRate(String salesRate) {
		this.salesRate = salesRate;
	}

	public String getBuyRate() {
		return buyRate;
	}

	public void setBuyRate(String buyRate) {
		this.buyRate = buyRate;
	}

	public String getPurDept() {
		return purDept;
	}

	public void setPurDept(String purDept) {
		this.purDept = purDept;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getOrgCost() {
		return orgCost;
	}

	public void setOrgCost(String orgCost) {
		this.orgCost = orgCost;
	}

	public String getChgReqCost() {
		return chgReqCost;
	}

	public void setChgReqCost(String chgReqCost) {
		this.chgReqCost = chgReqCost;
	}

	public String getPrStat() {
		return prStat;
	}

	public void setPrStat(String prStat) {
		this.prStat = prStat;
	}

	public String getPrStatNm() {
		return prStatNm;
	}

	public void setPrStatNm(String prStatNm) {
		this.prStatNm = prStatNm;
	}

	public String getReqDt() {
		return reqDt;
	}

	public void setReqDt(String reqDt) {
		this.reqDt = reqDt;
	}

	public String[] getVenCds() {
		return venCds;
	}

	public void setVenCds(String[] venCds) {
		this.venCds = venCds;
	}

	public String getL2Nm() {
		return l2Nm;
	}

	public void setL2Nm(String l2Nm) {
		this.l2Nm = l2Nm;
	}

	public String getL3Nm() {
		return l3Nm;
	}

	public void setL3Nm(String l3Nm) {
		this.l3Nm = l3Nm;
	}

	public String getOrdNo() {
		return ordNo;
	}

	public void setOrdNo(String ordNo) {
		this.ordNo = ordNo;
	}

	public String getRcvDt() {
		return rcvDt;
	}

	public void setRcvDt(String rcvDt) {
		this.rcvDt = rcvDt;
	}

	public String getNonPayRsrn() {
		return nonPayRsrn;
	}

	public void setNonPayRsrn(String nonPayRsrn) {
		this.nonPayRsrn = nonPayRsrn;
	}

	public String getNonPaySt() {
		return nonPaySt;
	}

	public void setNonPaySt(String nonPaySt) {
		this.nonPaySt = nonPaySt;
	}

	public String getNonPayAmt() {
		return nonPayAmt;
	}

	public void setNonPayAmt(String nonPayAmt) {
		this.nonPayAmt = nonPayAmt;
	}

	public String getProdGrd() {
		return prodGrd;
	}

	public void setProdGrd(String prodGrd) {
		this.prodGrd = prodGrd;
	}

	public String getProdSts() {
		return prodSts;
	}

	public void setProdSts(String prodSts) {
		this.prodSts = prodSts;
	}

	public String getProdStsNm() {
		return prodStsNm;
	}

	public void setProdStsNm(String prodStsNm) {
		this.prodStsNm = prodStsNm;
	}

	public String getIsuStore() {
		return isuStore;
	}

	public void setIsuStore(String isuStore) {
		this.isuStore = isuStore;
	}

	public String getIsuType() {
		return isuType;
	}

	public void setIsuType(String isuType) {
		this.isuType = isuType;
	}
	
	public String getRcvDtFmt() {
		if(rcvDt != null && !"".equals(rcvDt) && DateUtil.isDate(rcvDt, "yyyyMMdd")) {
			return DateUtil.string2String(rcvDt, "yyyyMMdd", DateUtil.DATE_PATTERN);
		}
		return "";
	}
	
	public String getDyFmt() {
		if(dy != null && !"".equals(dy) && DateUtil.isDate(dy, "yyyyMMdd")) {
			return DateUtil.string2String(dy, "yyyyMMdd", DateUtil.DATE_PATTERN);
		}
		return "";
	}

	public String getTotRowYn() {
		return totRowYn;
	}

	public void setTotRowYn(String totRowYn) {
		this.totRowYn = totRowYn;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getStartRowNo() {
		return startRowNo;
	}

	public void setStartRowNo(int startRowNo) {
		this.startRowNo = startRowNo;
	}

	public int getEndRowNo() {
		return endRowNo;
	}

	public void setEndRowNo(int endRowNo) {
		this.endRowNo = endRowNo;
	}
	
	
}
