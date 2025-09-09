package com.lottemart.epc.edi.product.model;

import java.io.Serializable;

import com.lottemart.epc.edi.comm.model.PagingVO;


/**
 * @Class Name : NEDMPRO0300VO
 * @Description : 행사정보 등록내역 VO
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      	수정자           		수정내용
 *  -------    --------    ---------------------------
 * 2025.03.07  PARK JONG GYU 		최초생성
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */

public class NEDMPRO0300VO extends PagingVO  implements Serializable {

	
	private static final long serialVersionUID = -6918711902569942487L;


	public NEDMPRO0300VO () {}
	
	/** 검색시작일 */
	private String srchStartDate;
	/** 검색종료일 */
	private String srchEndDate;
	
	/** 파트너사행사번호 */
	private String reqOfrcd; 	
	
	/** 파트너사코드 */
	private String lifnr;
	
	/** 파트너사명 */
	private String lifnrTxt;
	
	/** 계열사 */   
	private String vkorg;
	
	/** 계열사명 */  
	private String vkorgTxt;
	
	/** 행사명 */   
	private String ofrTxt;
	
	/** 행사목적 */  
	private String reqPurTxt;
	
	/** 행사요청 */  
	private String reqOfr;
	
	/** 행사요청명 */ 
	private String reqOfrTxt;
	
	/** 거래형태 */  
	private String zdeal;
	
	/** 거래형태명 */ 
	private String zdealTxt;
	
	/** 행사유형 */  
	private String reqType;
	
	/** 행사유형명 */ 
	private String reqTypeTxt;
	
	/** 할인유형 */  
	private String reqDisc;
	
	/** 할인유형명 */ 
	private String reqDiscTxt;
	
	/** 비용적용방식 */
	private String costType;
	
	/** 비용적용방식명 */
	private String costTypeTxt;
	
	/** 판촉요청합의서 */
	private String reqContyp;
	
	/** 판촉요청합의서명 */
	private String reqContypTxt;
	
	/** 파트너사분담율 */
	private String hdVenRate;
	
	/** 예상판촉비용 */
	private String ofrCost;
	
	/** 행사시작일 */ 
	private String ofsdt;
	
	/** 행사종료일 */ 
	private String ofedt;
	
	/** 발주시작일 */ 
	private String prsdt;
	
	/** 발주종료일 */ 
	private String predt;
	
	/** 대상점포 */  
	private String werksType;
	
	/** 대상점포명 */ 
	private String werksTypeTxt;
	
	/** 기타점포명 */ 
	private String werksEtcTxt;
	
	/** 행사세부요청사항 */
	private String reqOfrDesc;
	
	/** 전자계약번호 */
	private String docNo1;
	
	/** 전자계약번호 */
	private String docNo2;
	
	/** 전자계약번호 */
	private String docNo3;
	
	/** 팀코드 */   
	private String depCd;
	
	/** 통화  */   
	private String waers;
	
	/** 승인여부 */  
	private String apprStatus;
	
	/** 서식유형 */  
	private String docType;
	
	/** 삭제여부 */  
	private String delYn;
	
	/** 수정일시 */  
	private String modDate;
	
	/** 수정자 */  
	private String modId;
	
	/** 등록일시 */  
	private String regDate;
	
	/** 등록자 */  
	private String regId;

	/** rfc param */
	private String rfcParam;
	
	/** 요청 시작 일자 */
	private String srchFromDt;
	
	/** 요청 종료 일자 */
	private String srchEndDt;
	
	/** 상품 수 */
	private String itmeProdCnt;
	
	/** 승인 상품 수 */
	private String itmeApprProdCnt;
	
	/** IF CD */
	private String ifCd; 	
	
	/** 파일순번 */
	private String fileSeq; 	
	
	/** 계약명 */
	private String docName; 	
	
	/** 계약서 HTML */
	private String docHtml; 	
	
	/** 계약명 */
	private String dcNum; 	
	
	/** 계약차수 */
	private String dcCnt; 	
	
	/** 파트너사코드 list */
	private String[] venCdArr;
	
	public String[] getVenCdArr() {
		return venCdArr;
	}

	public void setVenCdArr(String[] venCdArr) {
		this.venCdArr = venCdArr;
	}

	public String getIfCd() {
		return ifCd;
	}

	public void setIfCd(String ifCd) {
		this.ifCd = ifCd;
	}

	public String getFileSeq() {
		return fileSeq;
	}

	public void setFileSeq(String fileSeq) {
		this.fileSeq = fileSeq;
	}

	public String getDocName() {
		return docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	public String getDocHtml() {
		return docHtml;
	}

	public void setDocHtml(String docHtml) {
		this.docHtml = docHtml;
	}

	public String getDcNum() {
		return dcNum;
	}

	public void setDcNum(String dcNum) {
		this.dcNum = dcNum;
	}

	public String getDcCnt() {
		return dcCnt;
	}

	public void setDcCnt(String dcCnt) {
		this.dcCnt = dcCnt;
	}

	public String getItmeProdCnt() {
		return itmeProdCnt;
	}

	public void setItmeProdCnt(String itmeProdCnt) {
		this.itmeProdCnt = itmeProdCnt;
	}

	public String getItmeApprProdCnt() {
		return itmeApprProdCnt;
	}

	public void setItmeApprProdCnt(String itmeApprProdCnt) {
		this.itmeApprProdCnt = itmeApprProdCnt;
	}

	public String getDocNo1() {
		return docNo1;
	}

	public void setDocNo1(String docNo1) {
		this.docNo1 = docNo1;
	}

	public String getDocNo3() {
		return docNo3;
	}

	public void setDocNo3(String docNo3) {
		this.docNo3 = docNo3;
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

	public String getRfcParam() {
		return rfcParam;
	}

	public void setRfcParam(String rfcParam) {
		this.rfcParam = rfcParam;
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

	public String getReqOfrcd() {
		return reqOfrcd;
	}

	public void setReqOfrcd(String reqOfrcd) {
		this.reqOfrcd = reqOfrcd;
	}

	public String getLifnr() {
		return lifnr;
	}

	public void setLifnr(String lifnr) {
		this.lifnr = lifnr;
	}

	public String getLifnrTxt() {
		return lifnrTxt;
	}

	public void setLifnrTxt(String lifnrTxt) {
		this.lifnrTxt = lifnrTxt;
	}

	public String getVkorg() {
		return vkorg;
	}

	public void setVkorg(String vkorg) {
		this.vkorg = vkorg;
	}

	public String getVkorgTxt() {
		return vkorgTxt;
	}

	public void setVkorgTxt(String vkorgTxt) {
		this.vkorgTxt = vkorgTxt;
	}

	public String getOfrTxt() {
		return ofrTxt;
	}

	public void setOfrTxt(String ofrTxt) {
		this.ofrTxt = ofrTxt;
	}

	public String getReqPurTxt() {
		return reqPurTxt;
	}

	public void setReqPurTxt(String reqPurTxt) {
		this.reqPurTxt = reqPurTxt;
	}

	public String getReqOfr() {
		return reqOfr;
	}

	public void setReqOfr(String reqOfr) {
		this.reqOfr = reqOfr;
	}

	public String getReqOfrTxt() {
		return reqOfrTxt;
	}

	public void setReqOfrTxt(String reqOfrTxt) {
		this.reqOfrTxt = reqOfrTxt;
	}

	public String getZdeal() {
		return zdeal;
	}

	public void setZdeal(String zdeal) {
		this.zdeal = zdeal;
	}

	public String getZdealTxt() {
		return zdealTxt;
	}

	public void setZdealTxt(String zdealTxt) {
		this.zdealTxt = zdealTxt;
	}

	public String getReqType() {
		return reqType;
	}

	public void setReqType(String reqType) {
		this.reqType = reqType;
	}

	public String getReqTypeTxt() {
		return reqTypeTxt;
	}

	public void setReqTypeTxt(String reqTypeTxt) {
		this.reqTypeTxt = reqTypeTxt;
	}

	public String getReqDisc() {
		return reqDisc;
	}

	public void setReqDisc(String reqDisc) {
		this.reqDisc = reqDisc;
	}

	public String getReqDiscTxt() {
		return reqDiscTxt;
	}

	public void setReqDiscTxt(String reqDiscTxt) {
		this.reqDiscTxt = reqDiscTxt;
	}

	public String getCostType() {
		return costType;
	}

	public void setCostType(String costType) {
		this.costType = costType;
	}

	public String getCostTypeTxt() {
		return costTypeTxt;
	}

	public void setCostTypeTxt(String costTypeTxt) {
		this.costTypeTxt = costTypeTxt;
	}

	public String getReqContyp() {
		return reqContyp;
	}

	public void setReqContyp(String reqContyp) {
		this.reqContyp = reqContyp;
	}

	public String getReqContypTxt() {
		return reqContypTxt;
	}

	public void setReqContypTxt(String reqContypTxt) {
		this.reqContypTxt = reqContypTxt;
	}

	public String getHdVenRate() {
		return hdVenRate;
	}

	public void setHdVenRate(String hdVenRate) {
		this.hdVenRate = hdVenRate;
	}

	public String getOfrCost() {
		return ofrCost;
	}

	public void setOfrCost(String ofrCost) {
		this.ofrCost = ofrCost;
	}

	public String getOfsdt() {
		return ofsdt;
	}

	public void setOfsdt(String ofsdt) {
		this.ofsdt = ofsdt;
	}

	public String getOfedt() {
		return ofedt;
	}

	public void setOfedt(String ofedt) {
		this.ofedt = ofedt;
	}

	public String getPrsdt() {
		return prsdt;
	}

	public void setPrsdt(String prsdt) {
		this.prsdt = prsdt;
	}

	public String getPredt() {
		return predt;
	}

	public void setPredt(String predt) {
		this.predt = predt;
	}

	public String getWerksType() {
		return werksType;
	}

	public void setWerksType(String werksType) {
		this.werksType = werksType;
	}

	public String getWerksTypeTxt() {
		return werksTypeTxt;
	}

	public void setWerksTypeTxt(String werksTypeTxt) {
		this.werksTypeTxt = werksTypeTxt;
	}

	public String getWerksEtcTxt() {
		return werksEtcTxt;
	}

	public void setWerksEtcTxt(String werksEtcTxt) {
		this.werksEtcTxt = werksEtcTxt;
	}

	public String getReqOfrDesc() {
		return reqOfrDesc;
	}

	public void setReqOfrDesc(String reqOfrDesc) {
		this.reqOfrDesc = reqOfrDesc;
	}

	public String getDocNo2() {
		return docNo2;
	}

	public void setDocNo2(String docNo2) {
		this.docNo2 = docNo2;
	}

	public String getDepCd() {
		return depCd;
	}

	public void setDepCd(String depCd) {
		this.depCd = depCd;
	}

	public String getWaers() {
		return waers;
	}

	public void setWaers(String waers) {
		this.waers = waers;
	}

	public String getApprStatus() {
		return apprStatus;
	}

	public void setApprStatus(String apprStatus) {
		this.apprStatus = apprStatus;
	}

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public String getDelYn() {
		return delYn;
	}

	public void setDelYn(String delYn) {
		this.delYn = delYn;
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
	
	
	
	
	
	
}
