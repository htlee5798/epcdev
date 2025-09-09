package com.lottemart.epc.edi.srm.model;

import lcn.module.common.util.StringUtil;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

public class SRMJON0042VO implements Serializable {

	static final long serialVersionUID = -254238134556327798L;
	
	/** Locale */
	private String locale;
	/** 하우스코드 */
	private String houseCode;
	/** 업체코드 */
	private String sellerCode;
	/**순번*/
	private String reqSeq;
	/** 신용평가번호 */
	private String creditNo;
	/** 신용평가사 코드{SCODE_TYPE=SRM039} */
	private String creditCompanyCode;
	/** 신용평가사 이름 */
	private String creditCompanyName;
	/** 신용평가등급 */
	private String creditRating;
	/** 신용평가 기준일자 */
	private String creditBasicDate;
	private String creditBasicDateHypen;
	/**신용평가 기준일자 종료일*/
	private String creditBasicEndDate;
	/** 신용평가서 첨부 */
	private String creditAttachNo;
	/***/
	private String creditAttachNoFileName;
	/***/
	private MultipartFile creditAttachNoFile;
	/***/
	private String url;
	/**업체소싱국가구분[M025]*/
	private String shipperType;
	/***/
	private String creditAttachNoName;
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

	private String companyType;
	private String companyRegNo;

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

	public String getCreditCompanyName() {
		return creditCompanyName;
	}

	public void setCreditCompanyName(String creditCompanyName) {
		this.creditCompanyName = creditCompanyName;
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

	public String getCreditAttachNoFileName() {
		return creditAttachNoFileName;
	}

	public void setCreditAttachNoFileName(String creditAttachNoFileName) {
		this.creditAttachNoFileName = creditAttachNoFileName;
	}

	public MultipartFile getCreditAttachNoFile() {
		return creditAttachNoFile;
	}

	public void setCreditAttachNoFile(MultipartFile creditAttachNoFile) {
		this.creditAttachNoFile = creditAttachNoFile;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getShipperType() {
		return shipperType;
	}

	public void setShipperType(String shipperType) {
		this.shipperType = shipperType;
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

	public String getCreditAttachNoName() {
		return creditAttachNoName;
	}

	public void setCreditAttachNoName(String creditAttachNoName) {
		this.creditAttachNoName = creditAttachNoName;
	}

	public String getZzqcFg1() {
		if (StringUtil.isEmpty(zzqcFg1)) {
			return "";
		} else {
			return "X";
		}
	}

	public void setZzqcFg1(String zzqcFg1) {
		this.zzqcFg1 = zzqcFg1;
	}

	public String getZzqcFg2() {
		if (StringUtil.isEmpty(zzqcFg2)) {
			return "";
		} else {
			return "X";
		}
	}

	public void setZzqcFg2(String zzqcFg2) {
		this.zzqcFg2 = zzqcFg2;
	}

	public String getZzqcFg3() {
		if (StringUtil.isEmpty(zzqcFg3)) {
			return "";
		} else {
			return "X";
		}
	}

	public void setZzqcFg3(String zzqcFg3) {
		this.zzqcFg3 = zzqcFg3;
	}

	public String getZzqcFg4() {
		if (StringUtil.isEmpty(zzqcFg4)) {
			return "";
		} else {
			return "X";
		}
	}

	public void setZzqcFg4(String zzqcFg4) {
		this.zzqcFg4 = zzqcFg4;
	}

	public String getZzqcFg5() {
		if (StringUtil.isEmpty(zzqcFg5)) {
			return "";
		} else {
			return "X";
		}
	}

	public void setZzqcFg5(String zzqcFg5) {
		this.zzqcFg5 = zzqcFg5;
	}

	public String getZzqcFg6() {
		if (StringUtil.isEmpty(zzqcFg6)) {
			return "";
		} else {
			return "X";
		}
	}

	public void setZzqcFg6(String zzqcFg6) {
		this.zzqcFg6 = zzqcFg6;
	}

	public String getZzqcFg7() {
		if (StringUtil.isEmpty(zzqcFg7)) {
			return "";
		} else {
			return "X";
		}
	}

	public void setZzqcFg7(String zzqcFg7) {
		this.zzqcFg7 = zzqcFg7;
	}

	public String getZzqcFg8() {
		if (StringUtil.isEmpty(zzqcFg8)) {
			return "";
		} else {
			return "X";
		}
	}

	public void setZzqcFg8(String zzqcFg8) {
		this.zzqcFg8 = zzqcFg8;
	}

	public String getZzqcFg9() {
		if (StringUtil.isEmpty(zzqcFg9)) {
			return "";
		} else {
			return "X";
		}
	}

	public void setZzqcFg9(String zzqcFg9) {
		this.zzqcFg9 = zzqcFg9;
	}

	public String getZzqcFg10() {
		if (StringUtil.isEmpty(zzqcFg10)) {
			return "";
		} else {
			return "X";
		}
	}

	public void setZzqcFg10(String zzqcFg10) {
		this.zzqcFg10 = zzqcFg10;
	}

	public String getZzqcFg11() {
		if (StringUtil.isEmpty(zzqcFg11)) {
			return "";
		} else {
			return "X";
		}
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

	public String getCreditBasicEndDate() {
		return creditBasicEndDate;
	}

	public void setCreditBasicEndDate(String creditBasicEndDate) {
		this.creditBasicEndDate = creditBasicEndDate;
	}


	public String getCompanyRegNo() {
		return companyRegNo;
	}

	public void setCompanyRegNo(String companyRegNo) {
		this.companyRegNo = companyRegNo;
	}

	public String getCompanyType() {
		return companyType;
	}

	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}
}
