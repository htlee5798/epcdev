package com.lottemart.epc.edi.srm.model;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

public class SRMEVL0040VO implements Serializable {

	static final long serialVersionUID = -254238134556327798L;
	
	/** Locale */
	private String locale;
	/** row span */
	private String rowSpan;
	/**subRowSpan*/
	private String subRowSpan;

	/** active Tab */
	private String activeTab;
	
	/**  - (House Code) */
	private String houseCode;
	/**  - [Evaluation Template No] */
	private String evTplNo;
	/**  - (Registration Date) */
	private String addDate;
	/** ID - (Registerer ID) */
	private String addUserId;
	/**  - (Update  Date) */
	private String changeDate;
	/** ID - (Updater  ID) */
	private String changeUserId;
	/**  - (Check whether it is deleted or not) */
	private String delFlag;
	/**  - [Evaluation Template Type Code] SIRA : SI/RA, SCRE : Screening, SITE : Site, EXEC : Execution */
	private String evTplTypeCode;
	/**  - [Template Name] */
	private String evTplSubject;
	/** 과락(상품) - 입점 */
	private String failScore010;
	/** 과락(기) - 입점 */
	private String failScore020;
	/** 통과점 */
	private String failScoreTotal;
	
	/**  - [Evaluation Item No] */
	private String evItemNo;
	/**  - [Weight] */
	private String weight;
	/** 쉬트별점 */
	private String evTplScore;
	
	/**  - [Evaluation Item Type Code] SIRA : SI/RA, SCRE : Screening, SITE : Site, EXEC : Execution */
	private String evItemDivCode;
	/** SI/RA  - [SI/RA Type] */
	private String siRaType;
	/** X/Y  - [Axis X/Y] */
	private String axisCode;
	/**  - (Evaluation Type Code) */
	private String evItemType3Code;
	/**  - (Evaluation Type Code Name) */
	private String evItemType3CodeName;
	/**  - [Evaluation Item Subject] */
	private String evItemSubject;
	/**   - [Evaluation Item Content] */
	private String evItemContents;
	/**  - [Evaluation Kind Code] */
	private String evItemKindCode;
	/** </> - [Evaluation Method <Qualitative/Quantitative>] */
	private String evItemMethodCode;
	/** Scale Type */
	private String scaleTypeCode;
	/** INPUT TYPE(SRM026) */
	private String inputTypeCode;
	/** 지표종류(SRM025) */
	private String kpiKindCode;
	/** 평가관점 */
	private String evItemType1Code;
	/** 평가관점 Name */
	private String evItemType1CodeName;
	/** 평가영역 */
	private String evItemType2Code;
	/** 평가영역Name */
	private String evItemType2CodeName;
	/** 필수통과여 */
	private String evCriticalPassYn;
	/** 모니터링여부 */
	private String monitoringFlag;
	
	/**  - [Indicators Seq Automatically] */
	private String evIdSeq;
	/**  - (Registration Date) */
	/**  - [Indicators Order Manually] */
	private String evIdOrder;
	/**  - [Indicators Content] */
	private String evIdContents;
	/**  - [Score] */
	private String evIdScore;
	/**평가점*/
	private String evIdScoreVal;
	/** /  - [Pass/Fail Code] */
	private String passFailCode;
	/** / - [Qualitative/Quantitative] */
	private String evIdMethod;
	/** From Value */
	private String fromValue;
	/** From Condition Code */
	private String fromConditionCode;
	/** To Value */
	private String toValue;
	/** To Condition Code */
	private String toConditionCode;

	/**소싱그룹번호(대분류)*/
	private String sgNo;
	/**평가번호*/
	private String evNo;
	/**공급업체코드*/
	private String vendorCode;
	/**평가자ID*/
	private String evUserId;
	/**진행상태명*/
	private String statusNm;
	/**파트너사명*/
	private String sellerNameLoc;
	/**의뢰번호*/
	private String evalNoResult;
	/**평가요청 순번*/
	private String visitSeq;
	/**순번*/
	private String seq;

	private String evalFlag;

	/**엑셀파일*/
	private MultipartFile excelFile;
	private String attachFileNo;

	private String progressCode;

	public String getLocale() {
		return locale;
	}
	public void setLocale(String locale) {
		this.locale = locale;
	}
	public String getRowSpan() {
		return rowSpan;
	}
	public void setRowSpan(String rowSpan) {
		this.rowSpan = rowSpan;
	}
	public String getActiveTab() {
		return activeTab;
	}
	public void setActiveTab(String activeTab) {
		this.activeTab = activeTab;
	}
	public String getHouseCode() {
		return houseCode;
	}
	public void setHouseCode(String houseCode) {
		this.houseCode = houseCode;
	}
	public String getEvTplNo() {
		return evTplNo;
	}
	public void setEvTplNo(String evTplNo) {
		this.evTplNo = evTplNo;
	}
	public String getAddDate() {
		return addDate;
	}
	public void setAddDate(String addDate) {
		this.addDate = addDate;
	}
	public String getAddUserId() {
		return addUserId;
	}
	public void setAddUserId(String addUserId) {
		this.addUserId = addUserId;
	}
	public String getChangeDate() {
		return changeDate;
	}
	public void setChangeDate(String changeDate) {
		this.changeDate = changeDate;
	}
	public String getChangeUserId() {
		return changeUserId;
	}
	public void setChangeUserId(String changeUserId) {
		this.changeUserId = changeUserId;
	}
	public String getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	public String getEvTplTypeCode() {
		return evTplTypeCode;
	}
	public void setEvTplTypeCode(String evTplTypeCode) {
		this.evTplTypeCode = evTplTypeCode;
	}
	public String getEvTplSubject() {
		return evTplSubject;
	}
	public void setEvTplSubject(String evTplSubject) {
		this.evTplSubject = evTplSubject;
	}
	public String getFailScore010() {
		return failScore010;
	}
	public void setFailScore010(String failScore010) {
		this.failScore010 = failScore010;
	}
	public String getFailScore020() {
		return failScore020;
	}
	public void setFailScore020(String failScore020) {
		this.failScore020 = failScore020;
	}
	public String getFailScoreTotal() {
		return failScoreTotal;
	}
	public void setFailScoreTotal(String failScoreTotal) {
		this.failScoreTotal = failScoreTotal;
	}
	public String getEvItemNo() {
		return evItemNo;
	}
	public void setEvItemNo(String evItemNo) {
		this.evItemNo = evItemNo;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getEvTplScore() {
		return evTplScore;
	}
	public void setEvTplScore(String evTplScore) {
		this.evTplScore = evTplScore;
	}
	public String getEvItemDivCode() {
		return evItemDivCode;
	}
	public void setEvItemDivCode(String evItemDivCode) {
		this.evItemDivCode = evItemDivCode;
	}
	public String getSiRaType() {
		return siRaType;
	}
	public void setSiRaType(String siRaType) {
		this.siRaType = siRaType;
	}
	public String getAxisCode() {
		return axisCode;
	}
	public void setAxisCode(String axisCode) {
		this.axisCode = axisCode;
	}
	public String getEvItemType3Code() {
		return evItemType3Code;
	}
	public void setEvItemType3Code(String evItemType3Code) {
		this.evItemType3Code = evItemType3Code;
	}
	public String getEvItemSubject() {
		return evItemSubject;
	}
	public void setEvItemSubject(String evItemSubject) {
		this.evItemSubject = evItemSubject;
	}
	public String getEvItemContents() {
		return evItemContents;
	}
	public void setEvItemContents(String evItemContents) {
		this.evItemContents = evItemContents;
	}
	public String getEvItemKindCode() {
		return evItemKindCode;
	}
	public void setEvItemKindCode(String evItemKindCode) {
		this.evItemKindCode = evItemKindCode;
	}
	public String getEvItemMethodCode() {
		return evItemMethodCode;
	}
	public void setEvItemMethodCode(String evItemMethodCode) {
		this.evItemMethodCode = evItemMethodCode;
	}
	public String getScaleTypeCode() {
		return scaleTypeCode;
	}
	public void setScaleTypeCode(String scaleTypeCode) {
		this.scaleTypeCode = scaleTypeCode;
	}
	public String getInputTypeCode() {
		return inputTypeCode;
	}
	public void setInputTypeCode(String inputTypeCode) {
		this.inputTypeCode = inputTypeCode;
	}
	public String getKpiKindCode() {
		return kpiKindCode;
	}
	public void setKpiKindCode(String kpiKindCode) {
		this.kpiKindCode = kpiKindCode;
	}
	public String getEvItemType1Code() {
		return evItemType1Code;
	}
	public void setEvItemType1Code(String evItemType1Code) {
		this.evItemType1Code = evItemType1Code;
	}
	public String getEvItemType2Code() {
		return evItemType2Code;
	}
	public void setEvItemType2Code(String evItemType2Code) {
		this.evItemType2Code = evItemType2Code;
	}
	public String getEvCriticalPassYn() {
		return evCriticalPassYn;
	}
	public void setEvCriticalPassYn(String evCriticalPassYn) {
		this.evCriticalPassYn = evCriticalPassYn;
	}
	public String getMonitoringFlag() {
		return monitoringFlag;
	}
	public void setMonitoringFlag(String monitoringFlag) {
		this.monitoringFlag = monitoringFlag;
	}
	public String getEvIdSeq() {
		return evIdSeq;
	}
	public void setEvIdSeq(String evIdSeq) {
		this.evIdSeq = evIdSeq;
	}
	public String getEvIdOrder() {
		return evIdOrder;
	}
	public void setEvIdOrder(String evIdOrder) {
		this.evIdOrder = evIdOrder;
	}
	public String getEvIdContents() {
		return evIdContents;
	}
	public void setEvIdContents(String evIdContents) {
		this.evIdContents = evIdContents;
	}
	public String getEvIdScore() {
		return evIdScore;
	}
	public void setEvIdScore(String evIdScore) {
		this.evIdScore = evIdScore;
	}
	public String getPassFailCode() {
		return passFailCode;
	}
	public void setPassFailCode(String passFailCode) {
		this.passFailCode = passFailCode;
	}
	public String getEvIdMethod() {
		return evIdMethod;
	}
	public void setEvIdMethod(String evIdMethod) {
		this.evIdMethod = evIdMethod;
	}
	public String getFromValue() {
		return fromValue;
	}
	public void setFromValue(String fromValue) {
		this.fromValue = fromValue;
	}
	public String getFromConditionCode() {
		return fromConditionCode;
	}
	public void setFromConditionCode(String fromConditionCode) {
		this.fromConditionCode = fromConditionCode;
	}
	public String getToValue() {
		return toValue;
	}
	public void setToValue(String toValue) {
		this.toValue = toValue;
	}
	public String getToConditionCode() {
		return toConditionCode;
	}
	public void setToConditionCode(String toConditionCode) {
		this.toConditionCode = toConditionCode;
	}
	public String getEvItemType3CodeName() {
		return evItemType3CodeName;
	}
	public void setEvItemType3CodeName(String evItemType3CodeName) {
		this.evItemType3CodeName = evItemType3CodeName;
	}
	public String getEvItemType1CodeName() {
		return evItemType1CodeName;
	}
	public void setEvItemType1CodeName(String evItemType1CodeName) {
		this.evItemType1CodeName = evItemType1CodeName;
	}
	public String getEvItemType2CodeName() {
		return evItemType2CodeName;
	}
	public void setEvItemType2CodeName(String evItemType2CodeName) {
		this.evItemType2CodeName = evItemType2CodeName;
	}

	public String getSgNo() {
		return sgNo;
	}

	public void setSgNo(String sgNo) {
		this.sgNo = sgNo;
	}

	public String getEvNo() {
		return evNo;
	}

	public void setEvNo(String evNo) {
		this.evNo = evNo;
	}

	public String getVendorCode() {
		return vendorCode;
	}

	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}

	public String getEvUserId() {
		return evUserId;
	}

	public void setEvUserId(String evUserId) {
		this.evUserId = evUserId;
	}

	public String getStatusNm() {
		return statusNm;
	}

	public void setStatusNm(String statusNm) {
		this.statusNm = statusNm;
	}

	public String getSellerNameLoc() {
		return sellerNameLoc;
	}

	public void setSellerNameLoc(String sellerNameLoc) {
		this.sellerNameLoc = sellerNameLoc;
	}

	public String getEvalNoResult() {
		return evalNoResult;
	}

	public void setEvalNoResult(String evalNoResult) {
		this.evalNoResult = evalNoResult;
	}

	public String getEvIdScoreVal() {
		return evIdScoreVal;
	}

	public void setEvIdScoreVal(String evIdScoreVal) {
		this.evIdScoreVal = evIdScoreVal;
	}

	public String getVisitSeq() {
		return visitSeq;
	}

	public void setVisitSeq(String visitSeq) {
		this.visitSeq = visitSeq;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getSubRowSpan() {
		return subRowSpan;
	}

	public void setSubRowSpan(String subRowSpan) {
		this.subRowSpan = subRowSpan;
	}

	public MultipartFile getExcelFile() {
		return excelFile;
	}

	public void setExcelFile(MultipartFile excelFile) {
		this.excelFile = excelFile;
	}

	public String getAttachFileNo() {
		return attachFileNo;
	}

	public void setAttachFileNo(String attachFileNo) {
		this.attachFileNo = attachFileNo;
	}

	public String getEvalFlag() {
		return evalFlag;
	}

	public void setEvalFlag(String evalFlag) {
		this.evalFlag = evalFlag;
	}

	public String getProgressCode() {
		return progressCode;
	}

	public void setProgressCode(String progressCode) {
		this.progressCode = progressCode;
	}
}
