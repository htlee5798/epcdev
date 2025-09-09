package com.lottemart.epc.edi.product.model;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.List;

import com.lottemart.epc.edi.comm.model.PagingVO;


/**
 * @Class Name : NEDMPRO0310VO
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

public class NEDMPRO0310VO extends PagingVO  implements Serializable {

	
	private static final long serialVersionUID = -6918711902569942487L;


	public NEDMPRO0310VO () {}
	
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
	
	/** 계열사구분 */  
	private String vkorgGbn;
	
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
	
	/** 계열사 분담율 */
	private String vkorgRate;
	
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
	private String docNo;
	
	/** 전자계약번호 */
	private String docNo2;
	
	/** 팀코드 */   
	private String depCd;
	
	/** 팀명 */
	private String depTxt;
	
	/** 화면에 보여지는 팀명 */
	private String srcDepTxt;
	
	/** 통화  */   
	private String waers;
	
	/** 승인여부 */  
	private String apprStatus;
	
	/** 서식유형 */  
	private String docType;
	
	/** 등록일 */   
	private String docDate;

	/** 업무구분 */
	private String taskGbn;
	
	/** 판매바코드(13) */
	private String ean11;
	
	/** 상품코드(18) */
	private String matnr;
	
	/** 상품명 */
	private String maktx;
	
	/** 행사기준 */
	private String ofrStd;
	
	/** 증정/사은 금액 */
	private String giftAmt;
	
	/** 기준1 */
	private String ofrStd1;
	
	/** 할인1 */
	private String ofrDisc1;
	
	/** 기준2 */
	private String ofrStd2;
	
	/** 할인2 */
	private String ofrDisc2;
	
	/** 기준3 */
	private String ofrStd3;
	
	/** 할인3 */
	private String ofrDisc3;
	
	/** M */
	private String ofrM;
	
	/** N */
	private String ofrN;
	
	/** 변경금액(원) */
	private String calPurPrc;
	
	/** 제안납품가(원) */
	private String reqPurPrc;
	
	/** 기존납품가(원) */
	private String purPrc;
	
	/** 변경금액(원) */
	private String calSalesPrc;
	
	/** 변경수수료(%) */
	private String calSalesRate;
	
	/** 제안판매가(원) */
	private String reqSalesPrc;
	
	/** 기존판매가(원) */
	private String salesPrc;
	
	/** 파트너사분담율(%) */
	private String venRate;
	
	/** 변경수수료(%) */
	private String calComRate;
	
	/** 제안수수료(%) */
	private String reqComRate;
	
	/** 기존수수료(%) */
	private String comRate;
	
	/** 행사상품 특이사항 */
	private String reqItemDesc;
	
	/** 작업자 ID */
	private String workId;
	
	/** 행사 item 정보 */
	private List<NEDMPRO0310VO> itemList;
	
	/** RFC Param */
	private String rfcParam;
	
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
	
	/** row 속성 */
	private String rowAttri;
	
	/** 파트너사 행사 신규 여부 */
	private String prodEvntNewYn;
	
	/** 파트너사 행사 번호 순번 */
	private String reqOfrcdSeq;
	
	/** 행번호 */
	private String rowNum;
	
	/** 비고 */
	private String zbigo;
	
	/** ecs 관리코드 대분류 */
	private String ecsDepCd;
	
	/** ecs 관리코드 중분류 */
	private String linCd;
	
	/** ecs 관리코드 계약명 코드 */
	private String dcCd;
	
	/** ecs 관리코드 계약서 코드 */
	private String dcNmCd;
	
	/** ecs 연계 계약 번호 */
	private String contCode;
	
	/** ecs user id */
	private String ecsUserId;
	
	/** 상품수 */
	private String itmeProdCnt;
	
	/** 승인 상품수 */
	private String itmeApprProdCnt;
	
	/** 사업자번호 */
	private String compNum;
	
	/** 사업자명 */
	private String compName;
	
	/** 행사유형 */
	private String reqTyp;

	/** ecs 연계 계약 번호2 */
	private String contCode2;
	
	/** 서브테마번호 */
	private String subNo;
	
	/** 서브테마명 */
	private String subTxt;
	
	/** 행사시작일 */
	private String stDy;
	
	/** 행사종료일 */
	private String enDy;
	
	/** 발주시작일 */
	private String purStDy;
	
	/** 발주종료일 */
	private String purEnDy;
	
	/** 등록종료일 */
	private String endDy;
	
	/** 테마명 검색조건 */
	private String srcSubTxt;
	
	/** 사번 */
	private String empSabun;
	
	/** 팀코드 */
	private String teamCd;
	
	/** 사번 */
	private String empNo;
	
	/** 담당자명 */
	private String empTxt;
	
	
	public String getSrcDepTxt() {
		return srcDepTxt;
	}

	public void setSrcDepTxt(String srcDepTxt) {
		this.srcDepTxt = srcDepTxt;
	}

	public String getEmpTxt() {
		return empTxt;
	}

	public void setEmpTxt(String empTxt) {
		this.empTxt = empTxt;
	}

	public String getEmpNo() {
		return empNo;
	}

	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}

	public String getEmpSabun() {
		return empSabun;
	}

	public void setEmpSabun(String empSabun) {
		this.empSabun = empSabun;
	}

	public String getTeamCd() {
		return teamCd;
	}

	public void setTeamCd(String teamCd) {
		this.teamCd = teamCd;
	}

	public String getDepTxt() {
		return depTxt;
	}

	public void setDepTxt(String depTxt) {
		this.depTxt = depTxt;
	}

	public String getVkorgRate() {
		return vkorgRate;
	}

	public void setVkorgRate(String vkorgRate) {
		this.vkorgRate = vkorgRate;
	}

	public String getSrcSubTxt() {
		return srcSubTxt;
	}

	public void setSrcSubTxt(String srcSubTxt) {
		this.srcSubTxt = srcSubTxt;
	}

	public String getSubNo() {
		return subNo;
	}

	public void setSubNo(String subNo) {
		this.subNo = subNo;
	}

	public String getSubTxt() {
		return subTxt;
	}

	public void setSubTxt(String subTxt) {
		this.subTxt = subTxt;
	}

	public String getStDy() {
		return stDy;
	}

	public void setStDy(String stDy) {
		this.stDy = stDy;
	}

	public String getEnDy() {
		return enDy;
	}

	public void setEnDy(String enDy) {
		this.enDy = enDy;
	}

	public String getPurStDy() {
		return purStDy;
	}

	public void setPurStDy(String purStDy) {
		this.purStDy = purStDy;
	}

	public String getPurEnDy() {
		return purEnDy;
	}

	public void setPurEnDy(String purEnDy) {
		this.purEnDy = purEnDy;
	}

	public String getEndDy() {
		return endDy;
	}

	public void setEndDy(String endDy) {
		this.endDy = endDy;
	}

	public String getVkorgGbn() {
		return vkorgGbn;
	}

	public void setVkorgGbn(String vkorgGbn) {
		this.vkorgGbn = vkorgGbn;
	}

	public String getContCode2() {
		return contCode2;
	}

	public void setContCode2(String contCode2) {
		this.contCode2 = contCode2;
	}

	public String getReqTyp() {
		return reqTyp;
	}

	public void setReqTyp(String reqTyp) {
		this.reqTyp = reqTyp;
	}

	public String getCompNum() {
		return compNum;
	}

	public void setCompNum(String compNum) {
		this.compNum = compNum;
	}

	public String getCompName() {
		return compName;
	}

	public void setCompName(String compName) {
		this.compName = compName;
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

	public String getEcsUserId() {
		return ecsUserId;
	}

	public void setEcsUserId(String ecsUserId) {
		this.ecsUserId = ecsUserId;
	}

	public String getContCode() {
		return contCode;
	}

	public void setContCode(String contCode) {
		this.contCode = contCode;
	}

	public String getEcsDepCd() {
		return ecsDepCd;
	}

	public void setEcsDepCd(String ecsDepCd) {
		this.ecsDepCd = ecsDepCd;
	}

	public String getLinCd() {
		return linCd;
	}

	public void setLinCd(String linCd) {
		this.linCd = linCd;
	}

	public String getDcCd() {
		return dcCd;
	}

	public void setDcCd(String dcCd) {
		this.dcCd = dcCd;
	}

	public String getDcNmCd() {
		return dcNmCd;
	}

	public void setDcNmCd(String dcNmCd) {
		this.dcNmCd = dcNmCd;
	}

	public String getZbigo() {
		return zbigo;
	}

	public void setZbigo(String zbigo) {
		this.zbigo = zbigo;
	}

	public String getRowNum() {
		return rowNum;
	}

	public void setRowNum(String rowNum) {
		this.rowNum = rowNum;
	}

	public String getCalSalesRate() {
		return replaceComma(calSalesRate);
	}

	public void setCalSalesRate(String calSalesRate) {
		this.calSalesRate = calSalesRate;
	}

	public String getReqOfrcdSeq() {
		return reqOfrcdSeq;
	}

	public void setReqOfrcdSeq(String reqOfrcdSeq) {
		this.reqOfrcdSeq = reqOfrcdSeq;
	}

	public String getProdEvntNewYn() {
		return prodEvntNewYn;
	}

	public void setProdEvntNewYn(String prodEvntNewYn) {
		this.prodEvntNewYn = prodEvntNewYn;
	}

	public String getRowAttri() {
		return rowAttri;
	}

	public void setRowAttri(String rowAttri) {
		this.rowAttri = rowAttri;
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

	public String getRfcParam() {
		return rfcParam;
	}

	public void setRfcParam(String rfcParam) {
		this.rfcParam = rfcParam;
	}

	public String getWorkId() {
		return workId;
	}

	public void setWorkId(String workId) {
		this.workId = workId;
	}

	public List<NEDMPRO0310VO> getItemList() {
		return itemList;
	}

	public void setItemList(List<NEDMPRO0310VO> itemList) {
		this.itemList = itemList;
	}

	public String getEan11() {
		return ean11;
	}

	public void setEan11(String ean11) {
		this.ean11 = ean11;
	}

	public String getMatnr() {
		return matnr;
	}

	public void setMatnr(String matnr) {
		this.matnr = matnr;
	}

	public String getMaktx() {
		return maktx;
	}

	public void setMaktx(String maktx) {
		this.maktx = maktx;
	}

	public String getOfrStd() {
		return ofrStd;
	}

	public void setOfrStd(String ofrStd) {
		this.ofrStd = ofrStd;
	}

	public String getGiftAmt() {
		return replaceComma(giftAmt);
	}

	public void setGiftAmt(String giftAmt) {
		this.giftAmt = giftAmt;
	}

	public String getOfrStd1() {
		return ofrStd1;
	}

	public void setOfrStd1(String ofrStd1) {
		this.ofrStd1 = ofrStd1;
	}

	public String getOfrDisc1() {
		return replaceComma(ofrDisc1);
	}

	public void setOfrDisc1(String ofrDisc1) {
		this.ofrDisc1 = ofrDisc1;
	}

	public String getOfrStd2() {
		return ofrStd2;
	}

	public void setOfrStd2(String ofrStd2) {
		this.ofrStd2 = ofrStd2;
	}

	public String getOfrDisc2() {
		return replaceComma(ofrDisc2);
	}

	public void setOfrDisc2(String ofrDisc2) {
		this.ofrDisc2 = ofrDisc2;
	}

	public String getOfrStd3() {
		return ofrStd3;
	}

	public void setOfrStd3(String ofrStd3) {
		this.ofrStd3 = ofrStd3;
	}

	public String getOfrDisc3() {
		return replaceComma(ofrDisc3);
	}

	public void setOfrDisc3(String ofrDisc3) {
		this.ofrDisc3 = ofrDisc3;
	}

	public String getOfrM() {
		return ofrM;
	}

	public void setOfrM(String ofrM) {
		this.ofrM = ofrM;
	}

	public String getOfrN() {
		return ofrN;
	}

	public void setOfrN(String ofrN) {
		this.ofrN = ofrN;
	}

	public String getCalPurPrc() {
		return replaceComma(calPurPrc);
	}

	public void setCalPurPrc(String calPurPrc) {
		this.calPurPrc = calPurPrc;
	}

	public String getReqPurPrc() {
		return replaceComma(reqPurPrc);
	}

	public void setReqPurPrc(String reqPurPrc) {
		this.reqPurPrc = reqPurPrc;
	}

	public String getPurPrc() {
		return replaceComma(purPrc);
	}

	public void setPurPrc(String purPrc) {
		this.purPrc = purPrc;
	}

	public String getCalSalesPrc() {
		return replaceComma(calSalesPrc);
	}

	public void setCalSalesPrc(String calSalesPrc) {
		this.calSalesPrc = calSalesPrc;
	}

	public String getReqSalesPrc() {
		return replaceComma(reqSalesPrc);
	}

	public void setReqSalesPrc(String reqSalesPrc) {
		this.reqSalesPrc = reqSalesPrc;
	}

	public String getSalesPrc() {
		return replaceComma(salesPrc);
	}

	public void setSalesPrc(String salesPrc) {
		this.salesPrc = salesPrc;
	}

	public String getVenRate() {
		return replaceComma(venRate);
	}

	public void setVenRate(String venRate) {
		this.venRate = venRate;
	}

	public String getCalComRate() {
		return replaceComma(calComRate);
	}

	public void setCalComRate(String calComRate) {
		this.calComRate = calComRate;
	}

	public String getReqComRate() {
		return reqComRate;
	}

	public void setReqComRate(String reqComRate) {
		this.reqComRate = reqComRate;
	}

	public String getComRate() {
		return comRate;
	}

	public void setComRate(String comRate) {
		this.comRate = comRate;
	}

	public String getReqItemDesc() {
		return reqItemDesc;
	}

	public void setReqItemDesc(String reqItemDesc) {
		this.reqItemDesc = reqItemDesc;
	}

	public String getTaskGbn() {
		return taskGbn;
	}

	public void setTaskGbn(String taskGbn) {
		this.taskGbn = taskGbn;
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
		return replaceComma(ofrCost);
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

	public String getDocNo() {
		return docNo;
	}

	public void setDocNo(String docNo) {
		this.docNo = docNo;
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

	public String getDocDate() {
		return docDate;
	}

	public void setDocDate(String docDate) {
		this.docDate = docDate;
	}

	/**
	 * 콤마 찍기
	 */
	public static String replaceComma(String data)  {
		if("".equals(data) || data == null) return "";
		String match = "[^0-9]";
		data = data.replaceAll(match, "");
	    long convert = Long.parseLong(data);
	    DecimalFormat df = new DecimalFormat("#,###");

	    String formatNum=(String)df.format(convert);
	    return formatNum;

	}
}
