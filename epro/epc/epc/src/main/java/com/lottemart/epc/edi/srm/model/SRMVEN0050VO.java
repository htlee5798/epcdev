package com.lottemart.epc.edi.srm.model;

import com.lottemart.epc.edi.comm.model.PagingVO;

import java.io.Serializable;

public class SRMVEN0050VO extends PagingVO implements Serializable {
	
	private static final long serialVersionUID = -9182078691197569401L;
	
	// 조회 파라미터들..
	// 대분류
	private String catLv1Code;
	// 중분류
	private String catLv2Code;
	// 판매처
	private String channelCode;
	// 평가년도
	private String countYear;
	// 협력업체코드
	private String venCd;
	// 분기(상반기, 하반기)
	private String yearHalf;	
	
	// 조회 컬럼들..
	private String houseCode;
	private String rnum;
	private String evNo;
	private String evName;
	private String egNo;
	private String egName;
	private String evCtrlUserId;
	private String evCtrlUserName;
	private String evYear;
	private String evYearHalf;
	private String evYearHalfName;
	private String status;
	private String sgNo;
	private String execEvTplNo;
	private String evTplSubject;
	private String evChannelCode;
	private String evChannelName;
	private String sgName2;
	private String sgName1;
	private String vendorCode;
	private String vendorName;
	private String evUserCnt;
	private String evScore;
	private String evalGradeClass;
	private String evGradeClass;
	private String addDate;
	private String startDate;
	private String closeDate;
	private String beforeEvalGradeClass;
	private String amendReason;
	private String sg1;
	private String sg2;
	private String sg3;
	private String sg4;
	private String applyFromDate;
	private String applyToDate;
	
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getCatLv1Code() {
		return catLv1Code;
	}
	public String getCatLv2Code() {
		return catLv2Code;
	}
	public String getChannelCode() {
		return channelCode;
	}
	public String getCountYear() {
		return countYear;
	}
	public String getVenCd() {
		return venCd;
	}
	public String getYearHalf() {
		return yearHalf;
	}
	public String getRnum() {
		return rnum;
	}
	public String getEvNo() {
		return evNo;
	}
	public String getEvName() {
		return evName;
	}
	public String getEgNo() {
		return egNo;
	}
	public String getEgName() {
		return egName;
	}
	public String getEvCtrlUserId() {
		return evCtrlUserId;
	}
	public String getEvCtrlUserName() {
		return evCtrlUserName;
	}
	public String getEvYear() {
		return evYear;
	}
	public String getEvYearHalf() {
		return evYearHalf;
	}
	public String getStatus() {
		return status;
	}
	public String getSgNo() {
		return sgNo;
	}
	public String getExecEvTplNo() {
		return execEvTplNo;
	}
	public String getEvTplSubject() {
		return evTplSubject;
	}
	public String getEvChannelCode() {
		return evChannelCode;
	}
	public String getEvChannelName() {
		return evChannelName;
	}
	public String getSgName2() {
		return sgName2;
	}
	public String getSgName1() {
		return sgName1;
	}
	public String getVendorCode() {
		return vendorCode;
	}
	public String getVendorName() {
		return vendorName;
	}
	public String getEvUserCnt() {
		return evUserCnt;
	}
	public String getEvScore() {
		return evScore;
	}
	public String getEvalGradeClass() {
		return evalGradeClass;
	}
	public String getEvGradeClass() {
		return evGradeClass;
	}
	public String getAddDate() {
		return addDate;
	}
	public String getStartDate() {
		return startDate;
	}
	public String getCloseDate() {
		return closeDate;
	}
	public String getBeforeEvalGradeClass() {
		return beforeEvalGradeClass;
	}
	public String getAmendReason() {
		return amendReason;
	}
	public String getSg1() {
		return sg1;
	}
	public String getSg2() {
		return sg2;
	}
	public String getSg3() {
		return sg3;
	}
	public String getSg4() {
		return sg4;
	}
	public String getApplyFromDate() {
		return applyFromDate;
	}
	public String getApplyToDate() {
		return applyToDate;
	}
	public void setCatLv1Code(String catLv1Code) {
		this.catLv1Code = catLv1Code;
	}
	public void setCatLv2Code(String catLv2Code) {
		this.catLv2Code = catLv2Code;
	}
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	public void setCountYear(String countYear) {
		this.countYear = countYear;
	}
	public void setVenCd(String venCd) {
		this.venCd = venCd;
	}
	public void setYearHalf(String yearHalf) {
		this.yearHalf = yearHalf;
	}
	public void setRnum(String rnum) {
		this.rnum = rnum;
	}
	public void setEvNo(String evNo) {
		this.evNo = evNo;
	}
	public void setEvName(String evName) {
		this.evName = evName;
	}
	public void setEgNo(String egNo) {
		this.egNo = egNo;
	}
	public void setEgName(String egName) {
		this.egName = egName;
	}
	public void setEvCtrlUserId(String evCtrlUserId) {
		this.evCtrlUserId = evCtrlUserId;
	}
	public void setEvCtrlUserName(String evCtrlUserName) {
		this.evCtrlUserName = evCtrlUserName;
	}
	public void setEvYear(String evYear) {
		this.evYear = evYear;
	}
	public void setEvYearHalf(String evYearHalf) {
		this.evYearHalf = evYearHalf;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setSgNo(String sgNo) {
		this.sgNo = sgNo;
	}
	public void setExecEvTplNo(String execEvTplNo) {
		this.execEvTplNo = execEvTplNo;
	}
	public void setEvTplSubject(String evTplSubject) {
		this.evTplSubject = evTplSubject;
	}
	public void setEvChannelCode(String evChannelCode) {
		this.evChannelCode = evChannelCode;
	}
	public void setEvChannelName(String evChannelName) {
		this.evChannelName = evChannelName;
	}
	public void setSgName2(String sgName2) {
		this.sgName2 = sgName2;
	}
	public void setSgName1(String sgName1) {
		this.sgName1 = sgName1;
	}
	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	public void setEvUserCnt(String evUserCnt) {
		this.evUserCnt = evUserCnt;
	}
	public void setEvScore(String evScore) {
		this.evScore = evScore;
	}
	public void setEvalGradeClass(String evalGradeClass) {
		this.evalGradeClass = evalGradeClass;
	}
	public void setEvGradeClass(String evGradeClass) {
		this.evGradeClass = evGradeClass;
	}
	public void setAddDate(String addDate) {
		this.addDate = addDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public void setCloseDate(String closeDate) {
		this.closeDate = closeDate;
	}
	public void setBeforeEvalGradeClass(String beforeEvalGradeClass) {
		this.beforeEvalGradeClass = beforeEvalGradeClass;
	}
	public void setAmendReason(String amendReason) {
		this.amendReason = amendReason;
	}
	public void setSg1(String sg1) {
		this.sg1 = sg1;
	}
	public void setSg2(String sg2) {
		this.sg2 = sg2;
	}
	public void setSg3(String sg3) {
		this.sg3 = sg3;
	}
	public void setSg4(String sg4) {
		this.sg4 = sg4;
	}
	public void setApplyFromDate(String applyFromDate) {
		this.applyFromDate = applyFromDate;
	}
	public void setApplyToDate(String applyToDate) {
		this.applyToDate = applyToDate;
	}
	public String getHouseCode() {
		return houseCode;
	}
	public void setHouseCode(String houseCode) {
		this.houseCode = houseCode;
	}
	public String getEvYearHalfName() {
		return evYearHalfName;
	}
	public void setEvYearHalfName(String evYearHalfName) {
		this.evYearHalfName = evYearHalfName;
	}	
}
