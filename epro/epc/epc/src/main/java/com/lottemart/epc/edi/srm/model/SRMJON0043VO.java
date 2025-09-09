package com.lottemart.epc.edi.srm.model;

import lcn.module.common.util.StringUtil;

import java.io.Serializable;

public class SRMJON0043VO implements Serializable {

	static final long serialVersionUID = -254238134556327798L;

	/** Locale */
	private String locale;
	/** 하우스코드 */
	private String houseCode;
	/** 업체코드 */
	private String sellerCode;
	/**순번*/
	private String reqSeq;
	/**채널선택{scode_type=SRM053}*/
	private String channelCode;
	private String channelCodeNm;
	/**업체소싱국가구분[M025]*/
	private String shipperType;
	private String shipperTypeNm;
	/**대분류*/
	private String catLv1Code;
	private String catLv1CodeNm;
	/**사업자등록번호*/
	private String irsNo;
	/**임시비밀번호*/
	private String tempPw;
	/**업체명{한글}*/
	private String sellerNameLoc;
	private String sellerNameEng;

	/**법인구분{scode_type=M074}*/
	private String companyType;
	private String companyTypeNm;
	/**법인번호*/
	private String companyRegNo;
	/**BDC 필드 추가필요 - 대표자명*/
	private String sellerCeoName;
	/**업체마트 담당자(이름)*/
	private String vMainName;
	/**등록자 유선 전화번호*/
	private String vPhone1;
	/**모바일(이동전화)*/
	private String vMobilePhone;
	/**BDC EMAIL CHECK 필요*/
	private String vEmail;
	/**대표자 이메일*/
	private String sellerCeoEmail;
	/**FAX*/
	private String faxFhone;
	/**우편번호*/
	private String zipcode;
	/**주소1*/
	private String address1;
	/**주소2*/
	private String address2;
	/**업태*/
	private String businessType;
	/**업종*/
	private String industryType;
	/**파트너사 사업 유형{scode_type=M076}*/
	private String sellerType;
	private String sellerTypeNm;
	/**국가코드{scode_type=M001}*/
	private String country;
	private String countryNm;
	/**도시명*/
	private String cityText;
	private String cityTextNm;
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
	private String plantRoleType;
	private String plantRoleTypeNm;
	/**주요거래처*/
	private String mainCustomer;
	/**롯데마트 旣 진출 채널*/
	private String aboardChannelText;
	private String aboardChannelTextNm;
	/**롯데마트 旣 진출 국가*/
	private String aboardCountryText;
	/**상품명*/
	private String productName;
	/**납품가*/
	private String productPrice;
	/**통화*/
	private String cur;
	private String curNm;
	/**상품이미지*/
	private String productImgPath;
	private String productImgPathName;
	/**제품소개서 첨부파일*/
	private String productIntroAttachNo;
	private String productIntroAttachNoName;
	/**회사소개서 첨부파일*/
	private String companyIntroAttachNo;
	private String companyIntroAttachNoName;
	/**상품정보*/
	private String mainProduct;
	/**주사용 브랜드*/
	private String dealingBrandProduct;
	/**신용평가번호*/
	private String creditNo;
	/**신용평가사 코드{SCODE_TYPE=SRM039}*/
	private String creditCompanyCode;
	private String creditCompanyCodeNm;
	/**신용평가등급*/
	private String creditRating;
	/**신용평가 기준일자*/
	private String creditBasicDate;
	private String creditBasicDateHypen;

	/**신용평가서 첨부*/
	private String creditAttachNo;
	private String creditAttachNoName;
	/**상태값*/
	private String status;

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

	private String scKind;


	/*******EMAIL*******/
	/**메일제목*/
	private String msgTitle;
	/**메일본문*/
	private String msgContents;
	/**MSG_CD*/
	private String msgCd;
	/**담당MD명*/
	private String userNameLoc;
	/**담당MD Email*/
	private String email;
	/***/
	private String phoneNo;

	/**온라인몰 메일발송 대상*/
	private String onlineMailTarget;
	private String onlineMailTargetNm;
	/**로컬푸드여부*/
	private String localFoodYn;
	
	/**중소기업여부*/
	private String smFlag;
	/**중소기업확인증 첨부*/
	private String smAttachNo;
	private String smAttachNoName;


	public String getLocale() {
		return locale;
	}
	public void setLocale(String locale) {
		this.locale = locale;
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

	public String getReqSeq() {
		return reqSeq;
	}

	public void setReqSeq(String reqSeq) {
		this.reqSeq = reqSeq;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public String getChannelCodeNm() {
		return channelCodeNm;
	}

	public void setChannelCodeNm(String channelCodeNm) {
		this.channelCodeNm = channelCodeNm;
	}

	public String getShipperType() {
		return shipperType;
	}

	public void setShipperType(String shipperType) {
		this.shipperType = shipperType;
	}

	public String getShipperTypeNm() {
		return shipperTypeNm;
	}

	public void setShipperTypeNm(String shipperTypeNm) {
		this.shipperTypeNm = shipperTypeNm;
	}

	public String getCatLv1Code() {
		return catLv1Code;
	}

	public void setCatLv1Code(String catLv1Code) {
		this.catLv1Code = catLv1Code;
	}

	public String getCatLv1CodeNm() {
		return catLv1CodeNm;
	}

	public void setCatLv1CodeNm(String catLv1CodeNm) {
		this.catLv1CodeNm = catLv1CodeNm;
	}

	public String getIrsNo() {
		return irsNo;
	}

	public void setIrsNo(String irsNo) {
		this.irsNo = irsNo;
	}

	public String getTempPw() {
		return tempPw;
	}

	public void setTempPw(String tempPw) {
		this.tempPw = tempPw;
	}

	public String getSellerNameLoc() {
		return sellerNameLoc;
	}

	public void setSellerNameLoc(String sellerNameLoc) {
		this.sellerNameLoc = sellerNameLoc;
	}

	public String getCompanyType() {
		return companyType;
	}

	public void setCompanyType(String companyType) {
		this.companyType = companyType;
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

	public String getvMainName() {
		return vMainName;
	}

	public void setvMainName(String vMainName) {
		this.vMainName = vMainName;
	}

	public String getvPhone1() {
		return vPhone1;
	}

	public void setvPhone1(String vPhone1) {
		this.vPhone1 = vPhone1;
	}

	public String getvMobilePhone() {
		return vMobilePhone;
	}

	public void setvMobilePhone(String vMobilePhone) {
		this.vMobilePhone = vMobilePhone;
	}

	public String getvEmail() {
		return vEmail;
	}

	public void setvEmail(String vEmail) {
		this.vEmail = vEmail;
	}

	public String getSellerCeoEmail() {
		return sellerCeoEmail;
	}

	public void setSellerCeoEmail(String sellerCeoEmail) {
		this.sellerCeoEmail = sellerCeoEmail;
	}

	public String getFaxFhone() {
		return faxFhone;
	}

	public void setFaxFhone(String faxFhone) {
		this.faxFhone = faxFhone;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getIndustryType() {
		return industryType;
	}

	public void setIndustryType(String industryType) {
		this.industryType = industryType;
	}

	public String getSellerType() {
		return sellerType;
	}

	public void setSellerType(String sellerType) {
		this.sellerType = sellerType;
	}

	public String getSellerTypeNm() {
		return sellerTypeNm;
	}

	public void setSellerTypeNm(String sellerTypeNm) {
		this.sellerTypeNm = sellerTypeNm;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
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

	public String getPlantRoleType() {
		return plantRoleType;
	}

	public void setPlantRoleType(String plantRoleType) {
		this.plantRoleType = plantRoleType;
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

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(String productPrice) {
		this.productPrice = productPrice;
	}

	public String getCur() {
		return cur;
	}

	public void setCur(String cur) {
		this.cur = cur;
	}

	public String getCurNm() {
		return curNm;
	}

	public void setCurNm(String curNm) {
		this.curNm = curNm;
	}

	public String getProductImgPath() {
		return productImgPath;
	}

	public void setProductImgPath(String productImgPath) {
		this.productImgPath = productImgPath;
	}

	public String getProductImgPathName() {
		return productImgPathName;
	}

	public void setProductImgPathName(String productImgPathName) {
		this.productImgPathName = productImgPathName;
	}

	public String getProductIntroAttachNo() {
		return productIntroAttachNo;
	}

	public void setProductIntroAttachNo(String productIntroAttachNo) {
		this.productIntroAttachNo = productIntroAttachNo;
	}

	public String getProductIntroAttachNoName() {
		return productIntroAttachNoName;
	}

	public void setProductIntroAttachNoName(String productIntroAttachNoName) {
		this.productIntroAttachNoName = productIntroAttachNoName;
	}

	public String getCompanyIntroAttachNo() {
		return companyIntroAttachNo;
	}

	public void setCompanyIntroAttachNo(String companyIntroAttachNo) {
		this.companyIntroAttachNo = companyIntroAttachNo;
	}

	public String getCompanyIntroAttachNoName() {
		return companyIntroAttachNoName;
	}

	public void setCompanyIntroAttachNoName(String companyIntroAttachNoName) {
		this.companyIntroAttachNoName = companyIntroAttachNoName;
	}

	public String getMainProduct() {
		return mainProduct;
	}

	public void setMainProduct(String mainProduct) {
		this.mainProduct = mainProduct;
	}

	public String getDealingBrandProduct() {
		return dealingBrandProduct;
	}

	public void setDealingBrandProduct(String dealingBrandProduct) {
		this.dealingBrandProduct = dealingBrandProduct;
	}

	public String getCreditNo() {
		return creditNo;
	}

	public void setCreditNo(String creditNo) {
		this.creditNo = creditNo;
	}

	public String getCreditCompanyCode() {
		return creditCompanyCode;
	}

	public void setCreditCompanyCode(String creditCompanyCode) {
		this.creditCompanyCode = creditCompanyCode;
	}

	public String getCreditCompanyCodeNm() {
		return creditCompanyCodeNm;
	}

	public void setCreditCompanyCodeNm(String creditCompanyCodeNm) {
		this.creditCompanyCodeNm = creditCompanyCodeNm;
	}

	public String getCreditRating() {
		return creditRating;
	}

	public void setCreditRating(String creditRating) {
		this.creditRating = creditRating;
	}

	public String getCreditBasicDate() {
		return creditBasicDate;
	}

	public void setCreditBasicDate(String creditBasicDate) {
		this.creditBasicDate = creditBasicDate;
	}

	public String getCreditAttachNo() {
		return creditAttachNo;
	}

	public void setCreditAttachNo(String creditAttachNo) {
		this.creditAttachNo = creditAttachNo;
	}

	public String getCreditAttachNoName() {
		return creditAttachNoName;
	}

	public void setCreditAttachNoName(String creditAttachNoName) {
		this.creditAttachNoName = creditAttachNoName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getUserNameLoc() {
		return userNameLoc;
	}

	public void setUserNameLoc(String userNameLoc) {
		this.userNameLoc = userNameLoc;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMsgTitle() {
		return msgTitle;
	}

	public void setMsgTitle(String msgTitle) {
		this.msgTitle = msgTitle;
	}

	public String getMsgContents() {
		return msgContents;
	}

	public void setMsgContents(String msgContents) {
		this.msgContents = msgContents;
	}

	public String getMsgCd() {
		return msgCd;
	}

	public void setMsgCd(String msgCd) {
		this.msgCd = msgCd;
	}

	public String getSellerNameEng() {
		return sellerNameEng;
	}

	public void setSellerNameEng(String sellerNameEng) {
		this.sellerNameEng = sellerNameEng;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getScKind() {
		return scKind;
	}

	public void setScKind(String scKind) {
		this.scKind = scKind;
	}

	public String getAboardChannelTextNm() {
		return aboardChannelTextNm;
	}

	public void setAboardChannelTextNm(String aboardChannelTextNm) {
		this.aboardChannelTextNm = aboardChannelTextNm;
	}
	public String getCreditBasicDateHypen() {
		if (StringUtil.isEmpty(creditBasicDate)) {
			return "";
		} else {
			creditBasicDateHypen = creditBasicDate.substring(0,4) + "-" + creditBasicDate.substring(4,6) + "-" + creditBasicDate.substring(6,8);
		}
		return creditBasicDateHypen;
	}

	public void setCreditBasicDateHypen(String creditBasicDateHypen) {
		this.creditBasicDateHypen = creditBasicDateHypen;
	}

	public String getCityTextNm() {
		return cityTextNm;
	}

	public void setCityTextNm(String cityTextNm) {
		this.cityTextNm = cityTextNm;
	}

	public String getLocalFoodYn() {
		if(StringUtil.isEmpty(this.localFoodYn)){
			this.localFoodYn = "N";
		}
		return localFoodYn;
	}

	public void setLocalFoodYn(String localFoodYn) {
		this.localFoodYn = localFoodYn;
	}

	public String getOnlineMailTarget() {
		return onlineMailTarget;
	}

	public void setOnlineMailTarget(String onlineMailTarget) {
		this.onlineMailTarget = onlineMailTarget;
	}

	public String getOnlineMailTargetNm() {
		return onlineMailTargetNm;
	}

	public void setOnlineMailTargetNm(String onlineMailTargetNm) {
		this.onlineMailTargetNm = onlineMailTargetNm;
	}

	public String getCompanyRegNo() {
		return companyRegNo;
	}

	public void setCompanyRegNo(String companyRegNo) {
		this.companyRegNo = companyRegNo;
	}
	
	public String getsmFlag() {
		return smFlag;
	}

	public void setsmFlag(String smFlag) {
		this.smFlag = smFlag;
	}
	
	public String getsmAttachNo() {
		return smAttachNo;
	}

	public void setsmAttachNo(String smAttachNo) {
		this.smAttachNo = smAttachNo;
	}

	public String getsmAttachNoName() {
		return smAttachNoName;
	}

	public void setsmAttachNoName(String smAttachNoName) {
		this.smAttachNoName = smAttachNoName;
	}
}
