package com.lottemart.epc.edi.srm.model;

import com.lottemart.epc.edi.comm.model.PagingVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.ArrayList;

public class SRMMNT002001VO extends PagingVO implements Serializable {
	
	private static final long serialVersionUID = -9182078691197569401L;

	/***/
	private String countYear;
	/***/
	private String countMonth;
	/***/
	private String vendorCode;
	/***/
	private String vendorName;
	/***/
	private String catLv2Code;
	/***/
	private String evTplSubject;

	/***/
	private String houseCode;
	/***/
	private String egNo;
	/***/
	private String evIemNo;
	/***/
	private String evItemNo;
	/***/
	private String evIeSeq;
	/***/
	private String evTplNo;
	/***/
	private String purchaseOrg;
	/***/
	private String channelCode;
	/***/
	private String purchaseType;
	/***/
	private String catLv1Code;
	/***/
	private String evItemType1Name;
	/***/
	private String evItemType2Name;
	/***/
	private String sgNo;
	/***/
	private String elName;
	/***/
	private String elContents;
	/***/
	private String elUnit;
	/***/
	private String elPriceUnit;
	/***/
	private String evScore;
	/***/
	private String resultValue;
	private String resultValueVendor;
	private String resultValueAvg;
	/***/
	private String delFlag;
	/***/
	private String sortSeq;
	/***/
	private String displayFlag;
	/***/
	private String resultValueSum;
	/***/
	private String resultValueCnt;
	/***/
	private String improveCount;
	/***/
	private String evItemType1Code;
	/***/
	private String evItemType2Code;

	private String catLv1Name;
	private String catLv2Name;
	private String itemGrade;
	private String resultEtc;
	private String evTplScore;
	private String spYn;
	private String remarkYn;

	private String evScoreAvg;
	private String totEvScoreAvg;
	private String vendorYn;

	private String changeDate;
	private String changeUserId;
	private String outDisplayFlag;
	private String remark1;
	private String remark2;
	private String displaySortSeq;
	private String grade;
	private String evIdContents;

	private String evalGradeClass;
	private String evScoreSum;

	private String skuNumber;
	private String skuName;
	private String plcGrade;

	private String order;
	private String orderValue;

	/*불량등록건수 팝업*/
	private String docno;
	private String stats;
	private String statsText;
	private String route;
	private String routeText;
	private String typ01;
	private String typ01Text;
	private String typ02;
	private String typ02Text;
	private String prown;
	private String prownText;
	private String ldesc;
	private String expdt;
	private String usrid;
	private String regdt;
	private String cpdat;
	private String materialClass3;

	private String locale;

	/*개선조치*/
	private String evEiNo;
	private String impReqMemo;
	private String impReqUserId;
	private String evCtrlUserName;
	private String impPlanDueDate;
	private String impPlanDate;
	private String impPlanUserId;
	private String impPlanMemo;
	private String deptMd;
	private String deptUser;
	private String attachNo;
	private String attachCount;
	private String progressCode;
	private String progressName;
	private String impResult;
	private String improveReqDate;
	private String addUserId;
	private String evChannelCode;
	private String evChannelName;
	private String egName;
	private String impReqContent;
	private String tempYn;

	private String rtnContent;

	private String evGradeClass;

	/**첨부파일*/
	ArrayList<MultipartFile> attachFileNoFile;
	/**첨부문서*/
	private ArrayList docNo;
	/**첨부문서 순번*/
	private ArrayList docSeq;

	public String getCountYear() {
		return countYear;
	}

	public void setCountYear(String countYear) {
		this.countYear = countYear;
	}

	public String getCountMonth() {
		return countMonth;
	}

	public void setCountMonth(String countMonth) {
		this.countMonth = countMonth;
	}

	public String getVendorCode() {
		return vendorCode;
	}

	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}

	public String getCatLv2Code() {
		return catLv2Code;
	}

	public void setCatLv2Code(String catLv2Code) {
		this.catLv2Code = catLv2Code;
	}

	public String getHouseCode() {
		return houseCode;
	}

	public void setHouseCode(String houseCode) {
		this.houseCode = houseCode;
	}

	public String getEvIemNo() {
		return evIemNo;
	}

	public void setEvIemNo(String evIemNo) {
		this.evIemNo = evIemNo;
	}

	public String getEvItemNo() {
		return evItemNo;
	}

	public void setEvItemNo(String evItemNo) {
		this.evItemNo = evItemNo;
	}

	public String getEvIeSeq() {
		return evIeSeq;
	}

	public void setEvIeSeq(String evIeSeq) {
		this.evIeSeq = evIeSeq;
	}

	public String getEvTplNo() {
		return evTplNo;
	}

	public void setEvTplNo(String evTplNo) {
		this.evTplNo = evTplNo;
	}

	public String getPurchaseOrg() {
		return purchaseOrg;
	}

	public void setPurchaseOrg(String purchaseOrg) {
		this.purchaseOrg = purchaseOrg;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public String getPurchaseType() {
		return purchaseType;
	}

	public void setPurchaseType(String purchaseType) {
		this.purchaseType = purchaseType;
	}

	public String getCatLv1Code() {
		return catLv1Code;
	}

	public void setCatLv1Code(String catLv1Code) {
		this.catLv1Code = catLv1Code;
	}

	public String getEvItemType1Name() {
		return evItemType1Name;
	}

	public void setEvItemType1Name(String evItemType1Name) {
		this.evItemType1Name = evItemType1Name;
	}

	public String getEvItemType2Name() {
		return evItemType2Name;
	}

	public void setEvItemType2Name(String evItemType2Name) {
		this.evItemType2Name = evItemType2Name;
	}

	public String getSgNo() {
		return sgNo;
	}

	public void setSgNo(String sgNo) {
		this.sgNo = sgNo;
	}

	public String getElName() {
		return elName;
	}

	public void setElName(String elName) {
		this.elName = elName;
	}

	public String getElContents() {
		return elContents;
	}

	public void setElContents(String elContents) {
		this.elContents = elContents;
	}

	public String getElUnit() {
		return elUnit;
	}

	public void setElUnit(String elUnit) {
		this.elUnit = elUnit;
	}

	public String getElPriceUnit() {
		return elPriceUnit;
	}

	public void setElPriceUnit(String elPriceUnit) {
		this.elPriceUnit = elPriceUnit;
	}

	public String getEvScore() {
		return evScore;
	}

	public void setEvScore(String evScore) {
		this.evScore = evScore;
	}

	public String getResultValue() {
		return resultValue;
	}

	public void setResultValue(String resultValue) {
		this.resultValue = resultValue;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public String getSortSeq() {
		return sortSeq;
	}

	public void setSortSeq(String sortSeq) {
		this.sortSeq = sortSeq;
	}

	public String getDisplayFlag() {
		return displayFlag;
	}

	public void setDisplayFlag(String displayFlag) {
		this.displayFlag = displayFlag;
	}

	public String getResultValueSum() {
		return resultValueSum;
	}

	public void setResultValueSum(String resultValueSum) {
		this.resultValueSum = resultValueSum;
	}

	public String getResultValueCnt() {
		return resultValueCnt;
	}

	public void setResultValueCnt(String resultValueCnt) {
		this.resultValueCnt = resultValueCnt;
	}

	public String getImproveCount() {
		return improveCount;
	}

	public void setImproveCount(String improveCount) {
		this.improveCount = improveCount;
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

	public String getEgNo() {
		return egNo;
	}

	public void setEgNo(String egNo) {
		this.egNo = egNo;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getEvTplSubject() {
		return evTplSubject;
	}

	public void setEvTplSubject(String evTplSubject) {
		this.evTplSubject = evTplSubject;
	}

	public String getCatLv1Name() {
		return catLv1Name;
	}

	public void setCatLv1Name(String catLv1Name) {
		this.catLv1Name = catLv1Name;
	}

	public String getCatLv2Name() {
		return catLv2Name;
	}

	public void setCatLv2Name(String catLv2Name) {
		this.catLv2Name = catLv2Name;
	}

	public String getItemGrade() {
		return itemGrade;
	}

	public void setItemGrade(String itemGrade) {
		this.itemGrade = itemGrade;
	}

	public String getResultEtc() {
		return resultEtc;
	}

	public void setResultEtc(String resultEtc) {
		this.resultEtc = resultEtc;
	}

	public String getEvTplScore() {
		return evTplScore;
	}

	public void setEvTplScore(String evTplScore) {
		this.evTplScore = evTplScore;
	}

	public String getSpYn() {
		return spYn;
	}

	public void setSpYn(String spYn) {
		this.spYn = spYn;
	}

	public String getRemarkYn() {
		return remarkYn;
	}

	public void setRemarkYn(String remarkYn) {
		this.remarkYn = remarkYn;
	}

	public String getEvScoreAvg() {
		return evScoreAvg;
	}

	public void setEvScoreAvg(String evScoreAvg) {
		this.evScoreAvg = evScoreAvg;
	}

	public String getTotEvScoreAvg() {
		return totEvScoreAvg;
	}

	public void setTotEvScoreAvg(String totEvScoreAvg) {
		this.totEvScoreAvg = totEvScoreAvg;
	}

	public String getVendorYn() {
		return vendorYn;
	}

	public void setVendorYn(String vendorYn) {
		this.vendorYn = vendorYn;
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

	public String getOutDisplayFlag() {
		return outDisplayFlag;
	}

	public void setOutDisplayFlag(String outDisplayFlag) {
		this.outDisplayFlag = outDisplayFlag;
	}

	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}

	public String getRemark2() {
		return remark2;
	}

	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}

	public String getDisplaySortSeq() {
		return displaySortSeq;
	}

	public void setDisplaySortSeq(String displaySortSeq) {
		this.displaySortSeq = displaySortSeq;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getEvIdContents() {
		return evIdContents;
	}

	public void setEvIdContents(String evIdContents) {
		this.evIdContents = evIdContents;
	}

	public String getEvalGradeClass() {
		return evalGradeClass;
	}

	public void setEvalGradeClass(String evalGradeClass) {
		this.evalGradeClass = evalGradeClass;
	}

	public String getEvScoreSum() {
		return evScoreSum;
	}

	public void setEvScoreSum(String evScoreSum) {
		this.evScoreSum = evScoreSum;
	}

	public String getResultValueVendor() {
		return resultValueVendor;
	}

	public void setResultValueVendor(String resultValueVendor) {
		this.resultValueVendor = resultValueVendor;
	}

	public String getResultValueAvg() {
		return resultValueAvg;
	}

	public void setResultValueAvg(String resultValueAvg) {
		this.resultValueAvg = resultValueAvg;
	}

	public String getSkuNumber() {
		return skuNumber;
	}

	public void setSkuNumber(String skuNumber) {
		this.skuNumber = skuNumber;
	}

	public String getSkuName() {
		return skuName;
	}

	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}

	public String getPlcGrade() {
		return plcGrade;
	}

	public void setPlcGrade(String plcGrade) {
		this.plcGrade = plcGrade;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getOrderValue() {
		return orderValue;
	}

	public void setOrderValue(String orderValue) {
		this.orderValue = orderValue;
	}

	public String getDocno() {
		return docno;
	}

	public void setDocno(String docno) {
		this.docno = docno;
	}

	public String getStats() {
		return stats;
	}

	public void setStats(String stats) {
		this.stats = stats;
	}

	public String getStatsText() {
		return statsText;
	}

	public void setStatsText(String statsText) {
		this.statsText = statsText;
	}

	public String getRoute() {
		return route;
	}

	public void setRoute(String route) {
		this.route = route;
	}

	public String getRouteText() {
		return routeText;
	}

	public void setRouteText(String routeText) {
		this.routeText = routeText;
	}

	public String getTyp01() {
		return typ01;
	}

	public void setTyp01(String typ01) {
		this.typ01 = typ01;
	}

	public String getTyp01Text() {
		return typ01Text;
	}

	public void setTyp01Text(String typ01Text) {
		this.typ01Text = typ01Text;
	}

	public String getTyp02() {
		return typ02;
	}

	public void setTyp02(String typ02) {
		this.typ02 = typ02;
	}

	public String getTyp02Text() {
		return typ02Text;
	}

	public void setTyp02Text(String typ02Text) {
		this.typ02Text = typ02Text;
	}

	public String getPrown() {
		return prown;
	}

	public void setPrown(String prown) {
		this.prown = prown;
	}

	public String getPrownText() {
		return prownText;
	}

	public void setPrownText(String prownText) {
		this.prownText = prownText;
	}

	public String getLdesc() {
		return ldesc;
	}

	public void setLdesc(String ldesc) {
		this.ldesc = ldesc;
	}

	public String getExpdt() {
		return expdt;
	}

	public void setExpdt(String expdt) {
		this.expdt = expdt;
	}

	public String getUsrid() {
		return usrid;
	}

	public void setUsrid(String usrid) {
		this.usrid = usrid;
	}

	public String getRegdt() {
		return regdt;
	}

	public void setRegdt(String regdt) {
		this.regdt = regdt;
	}

	public String getCpdat() {
		return cpdat;
	}

	public void setCpdat(String cpdat) {
		this.cpdat = cpdat;
	}

	public String getMaterialClass3() {
		return materialClass3;
	}

	public void setMaterialClass3(String materialClass3) {
		this.materialClass3 = materialClass3;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getEvEiNo() {
		return evEiNo;
	}

	public void setEvEiNo(String evEiNo) {
		this.evEiNo = evEiNo;
	}

	public String getImpReqMemo() {
		return impReqMemo;
	}

	public void setImpReqMemo(String impReqMemo) {
		this.impReqMemo = impReqMemo;
	}

	public String getImpReqUserId() {
		return impReqUserId;
	}

	public void setImpReqUserId(String impReqUserId) {
		this.impReqUserId = impReqUserId;
	}

	public String getEvCtrlUserName() {
		return evCtrlUserName;
	}

	public void setEvCtrlUserName(String evCtrlUserName) {
		this.evCtrlUserName = evCtrlUserName;
	}

	public String getImpPlanDate() {
		return impPlanDate;
	}

	public void setImpPlanDate(String impPlanDate) {
		this.impPlanDate = impPlanDate;
	}

	public String getImpPlanUserId() {
		return impPlanUserId;
	}

	public void setImpPlanUserId(String impPlanUserId) {
		this.impPlanUserId = impPlanUserId;
	}

	public String getImpPlanMemo() {
		return impPlanMemo;
	}

	public void setImpPlanMemo(String impPlanMemo) {
		this.impPlanMemo = impPlanMemo;
	}

	public String getDeptMd() {
		return deptMd;
	}

	public void setDeptMd(String deptMd) {
		this.deptMd = deptMd;
	}

	public String getDeptUser() {
		return deptUser;
	}

	public void setDeptUser(String deptUser) {
		this.deptUser = deptUser;
	}

	public String getAttachNo() {
		return attachNo;
	}

	public void setAttachNo(String attachNo) {
		this.attachNo = attachNo;
	}

	public String getAttachCount() {
		return attachCount;
	}

	public void setAttachCount(String attachCount) {
		this.attachCount = attachCount;
	}

	public String getProgressCode() {
		return progressCode;
	}

	public void setProgressCode(String progressCode) {
		this.progressCode = progressCode;
	}

	public String getProgressName() {
		return progressName;
	}

	public void setProgressName(String progressName) {
		this.progressName = progressName;
	}

	public String getImpResult() {
		return impResult;
	}

	public void setImpResult(String impResult) {
		this.impResult = impResult;
	}

	public String getImproveReqDate() {
		return improveReqDate;
	}

	public void setImproveReqDate(String improveReqDate) {
		this.improveReqDate = improveReqDate;
	}

	public String getAddUserId() {
		return addUserId;
	}

	public void setAddUserId(String addUserId) {
		this.addUserId = addUserId;
	}

	public String getEvChannelCode() {
		return evChannelCode;
	}

	public void setEvChannelCode(String evChannelCode) {
		this.evChannelCode = evChannelCode;
	}

	public String getEvChannelName() {
		return evChannelName;
	}

	public void setEvChannelName(String evChannelName) {
		this.evChannelName = evChannelName;
	}

	public String getEgName() {
		return egName;
	}

	public void setEgName(String egName) {
		this.egName = egName;
	}

	public String getImpReqContent() {
		return impReqContent;
	}

	public void setImpReqContent(String impReqContent) {
		this.impReqContent = impReqContent;
	}

	public String getImpPlanDueDate() {
		return impPlanDueDate;
	}

	public void setImpPlanDueDate(String impPlanDueDate) {
		this.impPlanDueDate = impPlanDueDate;
	}

	public String getTempYn() {
		return tempYn;
	}

	public void setTempYn(String tempYn) {
		this.tempYn = tempYn;
	}

	public String getRtnContent() {
		return rtnContent;
	}

	public void setRtnContent(String rtnContent) {
		this.rtnContent = rtnContent;
	}

	public ArrayList getDocNo() {
		if (this.docNo != null) {
			ArrayList ret = new ArrayList();
			for (int i = 0; i < docNo.size(); i++) {
				ret.add(i, this.docNo.get(i));
			}

			return ret;
		} else {
			return null;
		}
	}

	public void setDocNo(ArrayList docNo) {
		if (docNo != null) {
			this.docNo = new ArrayList();
			for (int i = 0; i < docNo.size();i++) {
				this.docNo.add(i, docNo.get(i));
			}
		} else {
			this.docNo = null;
		}
		this.docNo = docNo;
	}

	public ArrayList getDocSeq() {
		if (this.docSeq != null) {
			ArrayList ret = new ArrayList();
			for (int i = 0; i < docSeq.size(); i++) {
				ret.add(i, this.docSeq.get(i));
			}

			return ret;
		} else {
			return null;
		}
	}

	public void setDocSeq(ArrayList docSeq) {
		if (docSeq != null) {
			this.docSeq = new ArrayList();
			for (int i = 0; i < docSeq.size();i++) {
				this.docSeq.add(i, docSeq.get(i));
			}
		} else {
			this.docSeq = null;
		}
		this.docSeq = docSeq;
	}

	public ArrayList<MultipartFile> getAttachFileNoFile() {
		if (this.attachFileNoFile != null) {
			ArrayList ret = new ArrayList();
			for (int i = 0; i < attachFileNoFile.size(); i++) {
				ret.add(i, this.attachFileNoFile.get(i));
			}

			return ret;
		} else {
			return null;
		}
	}

	public void setAttachFileNoFile(ArrayList<MultipartFile> attachFileNoFile) {
		if (attachFileNoFile != null) {
			this.attachFileNoFile = new ArrayList();
			for (int i = 0; i < attachFileNoFile.size();i++) {
				this.attachFileNoFile.add(i, attachFileNoFile.get(i));
			}
		} else {
			this.attachFileNoFile = null;
		}
		this.attachFileNoFile = attachFileNoFile;
	}

	public String getEvGradeClass() {
		return evGradeClass;
	}

	public void setEvGradeClass(String evGradeClass) {
		this.evGradeClass = evGradeClass;
	}
}