package com.lottemart.epc.edi.product.model;

import java.io.Serializable;
import java.util.List;

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

public class NEDMPRO0320VO extends PagingVO  implements Serializable {

	
	private static final long serialVersionUID = -6918711902569942487L;


	public NEDMPRO0320VO () {}
	
	/** 검색시작일 */
	private String srchStartDate;
	
	/** 검색종료일 */
	private String srchEndDate;
	
	/** 반품요청번호 */
	private String prodRtnNo;
	
	/** 순번 */
	private String seq;
	
	/** 파트너사코드 */
	private String venCd;
	
	/** 파트너사코드 list */
	private String[] venCdArr;
	
	/** 상품팀코드 */
	private String teamCd;
	
	/** 팀명	*/
	private String teamNm;
	
	/** 판매코드 */
	private String srcmkCd;
	
	/** 상품코드 */
	private String prodCd;
	
	/** 반품마감일 */
	private String rtnClsDy;
	
	/** MD협의요청일 */
	private String reqMdDy;
	
	/** 수량 */
	private String prodQty;
	
	/** 반품사유 */
	private String rtnResn;
	
	/** 반품상세사유 */
	private String rtnResnDtl;
	
	/** 반품장소 */
	private String rtnResnPlce;
	
	/** 진행상태 */
	private String prdtStatus;
	
	/** 진행상태명 */
	private String prdtStatusNm;
	
	/** 점포코드 */
	private String strCd;
	
	/** ERP 인터페이스 송신 일시 */
	private String ifReqDt;
	
	/** ERP 인터페이스 수신 일시 */
	private String ifRcvDt;
	
	/** 계약번호(공문번호) */
	private String dcNum;
	
	/** 계약(공문)진행상태 */
	private String dcStat;
	
	/** 등록아이디 */
	private String modDate;
	
	/** 등록일시 */
	private String modId;
	
	/** 수정아이디 */
	private String regDate;
	
	/** 수정일시 */
	private String regId;

	/** 작업자 id */
	private String workId;
	
	/** 반품 item 정보 */
	private List<NEDMPRO0320VO> itemList;
	
	/** 삭제 여부 */
	private String delYn;
	
	/** 반품 등록일 */
	private String rtnRegDy;
	
	/** rfc param */
	private String rfcParam;
	
	/** 검색조건 시작일 */
	private String srchFromDt;
	
	/** 검색조건 종료일 */
	private String srchEndDt;
	
	/** 검색조건 - 파트너사코드 */
	private String srcVenCd;
	
	/** 검색조건 - 팀코드 */
	private String srcTeamCd;
	
	/** 검색조건 - 진행상태 */
	private String srcStatus;
	
	/** 검색조건 - 상품명 */
	private String srcmkNm;

	/** row 속성 */
	private String rowAttri;
	
	/** 회사코드 */
	private String bukrs;
	
	/** PB유형구분 */
	private String prodTypFg;
	
	/** PB유형구분명 */
	private String prodTypFgNm;
	
	/** 반품사유명 */
	private String rtnResnNm;
	
	/** 반품상세사유명 */
	private String rtnResnDtlNm;
	
	/** 반품장소명 */
	private String rtnResnPlceNm;
	
	/** MD협의 요청시간 */
	private String reqMdTim;
	
	/** MD협의 요청인 */
	private String reqMdNm;
	
	/** 반품등록시간 */
	private String regDtm;
	
	/** 등록인 */
	private String regUser;
	
	/** 상품명	*/
	private String prodNm;
	
	public String getProdNm() {
		return prodNm;
	}

	public void setProdNm(String prodNm) {
		this.prodNm = prodNm;
	}

	public String getTeamNm() {
		return teamNm;
	}

	public void setTeamNm(String teamNm) {
		this.teamNm = teamNm;
	}

	public String getProdTypFg() {
		return prodTypFg;
	}

	public void setProdTypFg(String prodTypFg) {
		this.prodTypFg = prodTypFg;
	}

	public String getProdTypFgNm() {
		return prodTypFgNm;
	}

	public void setProdTypFgNm(String prodTypFgNm) {
		this.prodTypFgNm = prodTypFgNm;
	}

	public String getRtnResnNm() {
		return rtnResnNm;
	}

	public void setRtnResnNm(String rtnResnNm) {
		this.rtnResnNm = rtnResnNm;
	}

	public String getRtnResnDtlNm() {
		return rtnResnDtlNm;
	}

	public void setRtnResnDtlNm(String rtnResnDtlNm) {
		this.rtnResnDtlNm = rtnResnDtlNm;
	}

	public String getRtnResnPlceNm() {
		return rtnResnPlceNm;
	}

	public void setRtnResnPlceNm(String rtnResnPlceNm) {
		this.rtnResnPlceNm = rtnResnPlceNm;
	}

	public String getReqMdTim() {
		return reqMdTim;
	}

	public void setReqMdTim(String reqMdTim) {
		this.reqMdTim = reqMdTim;
	}

	public String getReqMdNm() {
		return reqMdNm;
	}

	public void setReqMdNm(String reqMdNm) {
		this.reqMdNm = reqMdNm;
	}

	public String getRegDtm() {
		return regDtm;
	}

	public void setRegDtm(String regDtm) {
		this.regDtm = regDtm;
	}

	public String getRegUser() {
		return regUser;
	}

	public void setRegUser(String regUser) {
		this.regUser = regUser;
	}

	public String getBukrs() {
		return bukrs;
	}

	public void setBukrs(String bukrs) {
		this.bukrs = bukrs;
	}

	public String getRowAttri() {
		return rowAttri;
	}

	public void setRowAttri(String rowAttri) {
		this.rowAttri = rowAttri;
	}

	public String getSrcmkNm() {
		return srcmkNm;
	}

	public void setSrcmkNm(String srcmkNm) {
		this.srcmkNm = srcmkNm;
	}

	public String getSrchStartDate() {
		return srchStartDate;
	}

	public void setSrchStartDate(String srchStartDate) {
		this.srchStartDate = srchStartDate;
	}

	public String getSrchEndDate() {
		return srchEndDate;
	}

	public void setSrchEndDate(String srchEndDate) {
		this.srchEndDate = srchEndDate;
	}

	public String getProdRtnNo() {
		return prodRtnNo;
	}

	public void setProdRtnNo(String prodRtnNo) {
		this.prodRtnNo = prodRtnNo;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getVenCd() {
		return venCd;
	}

	public void setVenCd(String venCd) {
		this.venCd = venCd;
	}

	public String getTeamCd() {
		return teamCd;
	}

	public void setTeamCd(String teamCd) {
		this.teamCd = teamCd;
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

	public String getRtnClsDy() {
		return rtnClsDy;
	}

	public void setRtnClsDy(String rtnClsDy) {
		this.rtnClsDy = rtnClsDy;
	}

	public String getReqMdDy() {
		return reqMdDy;
	}

	public void setReqMdDy(String reqMdDy) {
		this.reqMdDy = reqMdDy;
	}

	public String getProdQty() {
		return prodQty;
	}

	public void setProdQty(String prodQty) {
		this.prodQty = prodQty;
	}

	public String getRtnResn() {
		return rtnResn;
	}

	public void setRtnResn(String rtnResn) {
		this.rtnResn = rtnResn;
	}

	public String getRtnResnDtl() {
		return rtnResnDtl;
	}

	public void setRtnResnDtl(String rtnResnDtl) {
		this.rtnResnDtl = rtnResnDtl;
	}

	public String getRtnResnPlce() {
		return rtnResnPlce;
	}

	public void setRtnResnPlce(String rtnResnPlce) {
		this.rtnResnPlce = rtnResnPlce;
	}

	public String getPrdtStatus() {
		return prdtStatus;
	}

	public void setPrdtStatus(String prdtStatus) {
		this.prdtStatus = prdtStatus;
	}

	public String getPrdtStatusNm() {
		return prdtStatusNm;
	}

	public void setPrdtStatusNm(String prdtStatusNm) {
		this.prdtStatusNm = prdtStatusNm;
	}

	public String getStrCd() {
		return strCd;
	}

	public void setStrCd(String strCd) {
		this.strCd = strCd;
	}

	public String getIfReqDt() {
		return ifReqDt;
	}

	public void setIfReqDt(String ifReqDt) {
		this.ifReqDt = ifReqDt;
	}

	public String getIfRcvDt() {
		return ifRcvDt;
	}

	public void setIfRcvDt(String ifRcvDt) {
		this.ifRcvDt = ifRcvDt;
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

	public String getWorkId() {
		return workId;
	}

	public void setWorkId(String workId) {
		this.workId = workId;
	}

	public List<NEDMPRO0320VO> getItemList() {
		return itemList;
	}

	public void setItemList(List<NEDMPRO0320VO> itemList) {
		this.itemList = itemList;
	}

	public String getDelYn() {
		return delYn;
	}

	public void setDelYn(String delYn) {
		this.delYn = delYn;
	}

	public String getRtnRegDy() {
		return rtnRegDy;
	}

	public void setRtnRegDy(String rtnRegDy) {
		this.rtnRegDy = rtnRegDy;
	}

	public String getRfcParam() {
		return rfcParam;
	}

	public void setRfcParam(String rfcParam) {
		this.rfcParam = rfcParam;
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

	public String getSrcVenCd() {
		return srcVenCd;
	}

	public void setSrcVenCd(String srcVenCd) {
		this.srcVenCd = srcVenCd;
	}

	public String getSrcTeamCd() {
		return srcTeamCd;
	}

	public void setSrcTeamCd(String srcTeamCd) {
		this.srcTeamCd = srcTeamCd;
	}

	public String getSrcStatus() {
		return srcStatus;
	}

	public void setSrcStatus(String srcStatus) {
		this.srcStatus = srcStatus;
	}

	public String[] getVenCdArr() {
		return venCdArr;
	}

	public void setVenCdArr(String[] venCdArr) {
		this.venCdArr = venCdArr;
	}
	
	
	
}
