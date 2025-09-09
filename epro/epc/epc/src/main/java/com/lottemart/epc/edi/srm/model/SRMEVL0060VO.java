package com.lottemart.epc.edi.srm.model;


import com.lottemart.epc.edi.comm.model.PagingVO;

import java.io.Serializable;

public class SRMEVL0060VO extends PagingVO implements Serializable {

	static final long serialVersionUID = -254238134556327798L;
	
	/** Locale */
	private String locale;
	/***/
	private int rnum;
	/**하우스코드*/
	private String houseCode;
	/** 업체코드*/
	private String sellerCode;
	/**순번*/
	private String seq;
	/**평가요청 순번*/
	private String visitSeq;
	/**의뢰번호*/
	private String evalNoResult;
	/**파트너사명*/
	private String sellerNameLoc;
	/**대분류코드*/
	private String catLv1Code;
	/**대분류명*/
	private String catLv1CodeName;
	/**신청일자*/
	private String reqDate;
	/**접수일자*/
	private String receiptDate;
	/**상태변경일자*/
	private String changeDate;
	/**진행상태*/
	private String status;
	/**진행상태명*/
	private String statusNm;
	/**평가시트번호*/
	private String evTplNo;

	/**평가업체코드*/
	private String evalSellerCode;

	/**파트너사 주소*/
	private String sellerAddress;
	/**파트너사 담당자명*/
	private String vMainName;
	/**파트너사 담당자 이메일*/
	private String vEmail;
	/**파트너사 담당자 휴대전화번호*/
	private String vMobilePhone;
	private String vMobilePhone1;
	private String vMobilePhone2;
	private String vMobilePhone3;
	/**비고*/
	private String remark;
	/**현장점검일*/
	private String checkDate;
	private String checkTime;
	private String evUserId;
	private String evNo;
	private String sgNo;

	/**채널선택{scode_type=SRM053}*/
	private String channelCodeNm;
	/**업체소싱국가구분[M025]*/
	private String shipperType;
	private String shipperTypeName;
	/**사업자등록번호*/
	private String irsNo;
	/**법인구분{scode_type=M074}*/
	private String companyTypeNm;
	/**BDC 필드 추가필요 - 대표자명*/
	private String sellerCeoName;
	/**등록자 유선 전화번호*/
	private String vPhone1;
	/**FAX*/
	private String faxFhone;
	/**업종*/
	private String industryType;
	/**업태*/
	private String businessType;
	/**파트너사 사업 유형{scode_type=M076}*/
	private String sellerTypeNm;
	/**국가코드{scode_type=M001}*/
	private String countryNm;
	/**도시명*/
	private String cityText;
	/**설립일자*/
	private String foundationDate;
	/**자본금*/
	private String basicAmt;
	/**연간 매출액(최근 3년)*/
	private String salesAmt;
	/**종업원 수(정규직)*/
	private String empCount;
	/**공장소유구분*/
	private String plantOwnType;
	/**공장 운영 형태{scode_type=M792}*/
	private String plantRoleTypeNm;
	/**주요거래처*/
	private String mainCustomer;
	/**롯데마트 旣 진출 채널*/
	private String aboardChannelText;
	/** 롯데마트 旣 진출 국가*/
	private String aboardCountryText;
	/***/
	private String progressCode;

	/** HACCP */
	private String zzqcFg1;
	/** FSSC 22000 */
	private String zzqcFg2;
	/** ISO 22000 */
	private String zzqcFg3;
	/** GMP인증 */
	private String zzqcFg4;
	/** KS인증 */
	private String zzqcFg5;
	/** 우수농산물(GAP)인증 */
	private String zzqcFg6;
	/** 유기가공식품인증 */
	private String zzqcFg7;
	/** 전통식품품질인증 */
	private String zzqcFg8;
	/** ISO 9001 */
	private String zzqcFg9;
	/** 수산물품질인증 */
	private String zzqcFg10;
	/** PAS 220 */
	private String zzqcFg11;
	/** 기타 품질인증 텍스트 */
	private String zzqcFg12;
	/***/
	private String evalFlag;

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public int getRnum() {
		return rnum;
	}

	public void setRnum(int rnum) {
		this.rnum = rnum;
	}

	public String getHouseCode() {
		return houseCode;
	}

	public void setHouseCode(String houseCode) {
		this.houseCode = houseCode;
	}

	public String getSellerCode() {
		return sellerCode;
	}

	public void setSellerCode(String sellerCode) {
		this.sellerCode = sellerCode;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getVisitSeq() {
		return visitSeq;
	}

	public void setVisitSeq(String visitSeq) {
		this.visitSeq = visitSeq;
	}

	public String getEvalNoResult() {
		return evalNoResult;
	}

	public void setEvalNoResult(String evalNoResult) {
		this.evalNoResult = evalNoResult;
	}

	public String getSellerNameLoc() {
		return sellerNameLoc;
	}

	public void setSellerNameLoc(String sellerNameLoc) {
		this.sellerNameLoc = sellerNameLoc;
	}

	public String getCatLv1Code() {
		return catLv1Code;
	}

	public void setCatLv1Code(String catLv1Code) {
		this.catLv1Code = catLv1Code;
	}

	public String getCatLv1CodeName() {
		return catLv1CodeName;
	}

	public void setCatLv1CodeName(String catLv1CodeName) {
		this.catLv1CodeName = catLv1CodeName;
	}

	public String getReqDate() {
		return reqDate;
	}

	public void setReqDate(String reqDate) {
		this.reqDate = reqDate;
	}

	public String getReceiptDate() {
		return receiptDate;
	}

	public void setReceiptDate(String receiptDate) {
		this.receiptDate = receiptDate;
	}

	public String getChangeDate() {
		return changeDate;
	}

	public void setChangeDate(String changeDate) {
		this.changeDate = changeDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusNm() {
		return statusNm;
	}

	public void setStatusNm(String statusNm) {
		this.statusNm = statusNm;
	}

	public String getEvTplNo() {
		return evTplNo;
	}

	public void setEvTplNo(String evTplNo) {
		this.evTplNo = evTplNo;
	}

	public String getEvalSellerCode() {
		return evalSellerCode;
	}

	public void setEvalSellerCode(String evalSellerCode) {
		this.evalSellerCode = evalSellerCode;
	}

	public String getSellerAddress() {
		return sellerAddress;
	}

	public void setSellerAddress(String sellerAddress) {
		this.sellerAddress = sellerAddress;
	}

	public String getvMainName() {
		return vMainName;
	}

	public void setvMainName(String vMainName) {
		this.vMainName = vMainName;
	}

	public String getvEmail() {
		return vEmail;
	}

	public void setvEmail(String vEmail) {
		this.vEmail = vEmail;
	}

	public String getvMobilePhone() {
		return vMobilePhone;
	}

	public void setvMobilePhone(String vMobilePhone) {
		this.vMobilePhone = vMobilePhone;
	}

	public String getvMobilePhone1() {
		return vMobilePhone1;
	}

	public void setvMobilePhone1(String vMobilePhone1) {
		this.vMobilePhone1 = vMobilePhone1;
	}

	public String getvMobilePhone2() {
		return vMobilePhone2;
	}

	public void setvMobilePhone2(String vMobilePhone2) {
		this.vMobilePhone2 = vMobilePhone2;
	}

	public String getvMobilePhone3() {
		return vMobilePhone3;
	}

	public void setvMobilePhone3(String vMobilePhone3) {
		this.vMobilePhone3 = vMobilePhone3;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}

	public String getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(String checkTime) {
		this.checkTime = checkTime;
	}

	public String getEvUserId() {
		return evUserId;
	}

	public void setEvUserId(String evUserId) {
		this.evUserId = evUserId;
	}

	public String getEvNo() {
		return evNo;
	}

	public void setEvNo(String evNo) {
		this.evNo = evNo;
	}

	public String getSgNo() {
		return sgNo;
	}

	public void setSgNo(String sgNo) {
		this.sgNo = sgNo;
	}

	public String getChannelCodeNm() {
		return channelCodeNm;
	}

	public void setChannelCodeNm(String channelCodeNm) {
		this.channelCodeNm = channelCodeNm;
	}

	public String getShipperTypeName() {
		return shipperTypeName;
	}

	public void setShipperTypeName(String shipperTypeName) {
		this.shipperTypeName = shipperTypeName;
	}

	public String getIrsNo() {
		return irsNo;
	}

	public void setIrsNo(String irsNo) {
		this.irsNo = irsNo;
	}

	public String getCompanyTypeNm() {
		return companyTypeNm;
	}

	public void setCompanyTypeNm(String companyTypeNm) {
		this.companyTypeNm = companyTypeNm;
	}

	public String getSellerCeoName() {
		return sellerCeoName;
	}

	public void setSellerCeoName(String sellerCeoName) {
		this.sellerCeoName = sellerCeoName;
	}

	public String getvPhone1() {
		return vPhone1;
	}

	public void setvPhone1(String vPhone1) {
		this.vPhone1 = vPhone1;
	}

	public String getFaxFhone() {
		return faxFhone;
	}

	public void setFaxFhone(String faxFhone) {
		this.faxFhone = faxFhone;
	}

	public String getIndustryType() {
		return industryType;
	}

	public void setIndustryType(String industryType) {
		this.industryType = industryType;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getSellerTypeNm() {
		return sellerTypeNm;
	}

	public void setSellerTypeNm(String sellerTypeNm) {
		this.sellerTypeNm = sellerTypeNm;
	}

	public String getCountryNm() {
		return countryNm;
	}

	public void setCountryNm(String countryNm) {
		this.countryNm = countryNm;
	}

	public String getCityText() {
		return cityText;
	}

	public void setCityText(String cityText) {
		this.cityText = cityText;
	}

	public String getFoundationDate() {
		return foundationDate;
	}

	public void setFoundationDate(String foundationDate) {
		this.foundationDate = foundationDate;
	}

	public String getBasicAmt() {
		return basicAmt;
	}

	public void setBasicAmt(String basicAmt) {
		this.basicAmt = basicAmt;
	}

	public String getSalesAmt() {
		return salesAmt;
	}

	public void setSalesAmt(String salesAmt) {
		this.salesAmt = salesAmt;
	}

	public String getEmpCount() {
		return empCount;
	}

	public void setEmpCount(String empCount) {
		this.empCount = empCount;
	}

	public String getPlantOwnType() {
		return plantOwnType;
	}

	public void setPlantOwnType(String plantOwnType) {
		this.plantOwnType = plantOwnType;
	}

	public String getPlantRoleTypeNm() {
		return plantRoleTypeNm;
	}

	public void setPlantRoleTypeNm(String plantRoleTypeNm) {
		this.plantRoleTypeNm = plantRoleTypeNm;
	}

	public String getMainCustomer() {
		return mainCustomer;
	}

	public void setMainCustomer(String mainCustomer) {
		this.mainCustomer = mainCustomer;
	}

	public String getAboardChannelText() {
		return aboardChannelText;
	}

	public void setAboardChannelText(String aboardChannelText) {
		this.aboardChannelText = aboardChannelText;
	}

	public String getAboardCountryText() {
		return aboardCountryText;
	}

	public void setAboardCountryText(String aboardCountryText) {
		this.aboardCountryText = aboardCountryText;
	}

	public String getProgressCode() {
		return progressCode;
	}

	public void setProgressCode(String progressCode) {
		this.progressCode = progressCode;
	}

	public String getShipperType() {
		return shipperType;
	}

	public void setShipperType(String shipperType) {
		this.shipperType = shipperType;
	}

	public String getZzqcFg1() {
		return zzqcFg1;
	}

	public void setZzqcFg1(String zzqcFg1) {
		this.zzqcFg1 = zzqcFg1;
	}

	public String getZzqcFg2() {
		return zzqcFg2;
	}

	public void setZzqcFg2(String zzqcFg2) {
		this.zzqcFg2 = zzqcFg2;
	}

	public String getZzqcFg3() {
		return zzqcFg3;
	}

	public void setZzqcFg3(String zzqcFg3) {
		this.zzqcFg3 = zzqcFg3;
	}

	public String getZzqcFg4() {
		return zzqcFg4;
	}

	public void setZzqcFg4(String zzqcFg4) {
		this.zzqcFg4 = zzqcFg4;
	}

	public String getZzqcFg5() {
		return zzqcFg5;
	}

	public void setZzqcFg5(String zzqcFg5) {
		this.zzqcFg5 = zzqcFg5;
	}

	public String getZzqcFg6() {
		return zzqcFg6;
	}

	public void setZzqcFg6(String zzqcFg6) {
		this.zzqcFg6 = zzqcFg6;
	}

	public String getZzqcFg7() {
		return zzqcFg7;
	}

	public void setZzqcFg7(String zzqcFg7) {
		this.zzqcFg7 = zzqcFg7;
	}

	public String getZzqcFg8() {
		return zzqcFg8;
	}

	public void setZzqcFg8(String zzqcFg8) {
		this.zzqcFg8 = zzqcFg8;
	}

	public String getZzqcFg9() {
		return zzqcFg9;
	}

	public void setZzqcFg9(String zzqcFg9) {
		this.zzqcFg9 = zzqcFg9;
	}

	public String getZzqcFg10() {
		return zzqcFg10;
	}

	public void setZzqcFg10(String zzqcFg10) {
		this.zzqcFg10 = zzqcFg10;
	}

	public String getZzqcFg11() {
		return zzqcFg11;
	}

	public void setZzqcFg11(String zzqcFg11) {
		this.zzqcFg11 = zzqcFg11;
	}

	public String getZzqcFg12() {
		return zzqcFg12;
	}

	public void setZzqcFg12(String zzqcFg12) {
		this.zzqcFg12 = zzqcFg12;
	}

	public String getEvalFlag() {
		return evalFlag;
	}

	public void setEvalFlag(String evalFlag) {
		this.evalFlag = evalFlag;
	}
}