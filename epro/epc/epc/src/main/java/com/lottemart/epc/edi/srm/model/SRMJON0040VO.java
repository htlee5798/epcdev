package com.lottemart.epc.edi.srm.model;

import java.io.Serializable;

public class SRMJON0040VO implements Serializable {

	static final long serialVersionUID = -254238134556327798L;

	/** Locale */
	private String locale;
	/** 하우스코드 */
	private String houseCode;
	/** 업체코드 */
	private String sellerCode;
	/** 업체명{한글} */
	private String sellerNameLoc;
	/** 업체명{영문} */
	private String sellerNameEng;
	/** 국가코드{scode_type=M001} */
	private String country;
	/** 사업자등록번호 */
	private String irsNo;
	private String irsNo1;
	private String irsNo2;
	private String irsNo3;
	/** 채널선택{scode_type=SRM053} */
	private String channelCode;
	/** 업체소싱국가구분[M025] */
	private String shipperType;
	/** 임시비밀번호 */
	private String tempPw;
	/** 법인구분{scode_type=M074} */
	private String companyType;
	/**법인번호*/
	private String companyRegNo;
	/** BDC 필드 추가필요 - 대표자명 */
	private String sellerCeoName;
	/** 업체마트 담당자(이름) */
	private String vMainName;
	/** 등록자 유선 전화번호 */
	private String vPhone1;
	/** 모바일(이동전화) */
	private String vMobilePhone;
	/** BDC EMAIL CHECK 필요 */
	private String vEmail;
	/** FAX */
	private String faxFhone;
	/** 우편번호 */
	private String zipcode;
	/** 주소1 */
	private String address1;
	/** 주소2 */
	private String address2;
	/** 업태 */
	private String businessType;
	/** 업종 */
	private String industryType;
	/** 파트너사 사업 유형{scode_type=M076} */
	private String sellerType;
	/** 도시명 */
	private String cityText;
	/**순번*/
	private String reqSeq;
	/**대분류코드*/
	private String catLv1Code;
	private String catLv1CodeNm;
	/**대표자이메일*/
	private String sellerCeoEmail;
	/**담당자 연락처*/
	private String phoneNo;

	/**온라인몰 메일발송 대상*/
	private String onlineMailTarget;
	/**로컬푸드여부*/
	private String localFoodYn;

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

	public String getSellerNameLoc() {
		return sellerNameLoc;
	}

	public void setSellerNameLoc(String sellerNameLoc) {
		this.sellerNameLoc = sellerNameLoc;
	}

	public String getSellerNameEng() {
		return sellerNameEng;
	}

	public void setSellerNameEng(String sellerNameEng) {
		this.sellerNameEng = sellerNameEng;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getIrsNo() {
		return irsNo;
	}

	public void setIrsNo(String irsNo) {
		this.irsNo = irsNo;
	}

	public String getIrsNo1() {
		return irsNo1;
	}

	public void setIrsNo1(String irsNo1) {
		this.irsNo1 = irsNo1;
	}

	public String getIrsNo2() {
		return irsNo2;
	}

	public void setIrsNo2(String irsNo2) {
		this.irsNo2 = irsNo2;
	}

	public String getIrsNo3() {
		return irsNo3;
	}

	public void setIrsNo3(String irsNo3) {
		this.irsNo3 = irsNo3;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public String getShipperType() {
		return shipperType;
	}

	public void setShipperType(String shipperType) {
		this.shipperType = shipperType;
	}

	public String getTempPw() {
		return tempPw;
	}

	public void setTempPw(String tempPw) {
		this.tempPw = tempPw;
	}

	public String getCompanyType() {
		return companyType;
	}

	public void setCompanyType(String companyType) {
		this.companyType = companyType;
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

	public String getCityText() {
		return cityText;
	}

	public void setCityText(String cityText) {
		this.cityText = cityText;
	}

	public String getReqSeq() {
		return reqSeq;
	}

	public void setReqSeq(String reqSeq) {
		this.reqSeq = reqSeq;
	}

	public String getCatLv1Code() {
		return catLv1Code;
	}

	public void setCatLv1Code(String catLv1Code) {
		this.catLv1Code = catLv1Code;
	}

	public String getSellerCeoEmail() {
		return sellerCeoEmail;
	}

	public void setSellerCeoEmail(String sellerCeoEmail) {
		this.sellerCeoEmail = sellerCeoEmail;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getCatLv1CodeNm() {
		return catLv1CodeNm;
	}

	public void setCatLv1CodeNm(String catLv1CodeNm) {
		this.catLv1CodeNm = catLv1CodeNm;
	}

	public String getLocalFoodYn() {
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

	public String getCompanyRegNo() {
		return companyRegNo;
	}

	public void setCompanyRegNo(String companyRegNo) {
		this.companyRegNo = companyRegNo;
	}
}
