package com.lottemart.epc.edi.srm.model;

import com.lottemart.epc.edi.comm.model.PagingVO;

import java.io.Serializable;
import java.util.ArrayList;

public class SRMMNT0020VO extends PagingVO implements Serializable {
	
	private static final long serialVersionUID = -9182078691197569401L;
	
	/** 분류코드 depth */
	private String  depth;
	/** 상위 분류코드 */
	private String parentSgNo;

	/***/
	private String irsNo;
	
	/***/
	private String rnum;
	/***/
	private String houseCode;
	/***/
	private String evIemNo;
	/***/
	private String evItemNo;
	/***/
	private String evIeSeq;
	/***/
	private String vendorCode;
	/***/
	private String egNo;
	/***/
	private String egName;
	/***/
	private String evTplNo;
	/***/
	private String countYear;
	/***/
	private String countMonth;
	/***/
	private String purchaseOrg;
	/***/
	private String channelCode;
	/***/
	private String purchaseType;
	/***/
	private String catLv1Code;
	/***/
	private String catLv2Code;
	/***/
	private String evTplSubject;
	/***/
	private String evItemType1Name;
	/***/
	private String evItemType2Name;
	/***/
	private String evChannelName;
	/***/
	private String evTypeName;
	/***/
	private String sgName1;
	/***/
	private String sgName2;
	/***/
	private String vendorName;
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

	private String venCd;
	private ArrayList venCds;

	private String evScoreSum;
	private String evGradeClass;
	private String impCnt;


	public String getDepth() {
		return depth;
	}

	public void setDepth(String depth) {
		this.depth = depth;
	}

	public String getParentSgNo() {
		return parentSgNo;
	}

	public void setParentSgNo(String parentSgNo) {
		this.parentSgNo = parentSgNo;
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

	public String getVendorCode() {
		return vendorCode;
	}

	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}

	public String getEvTplNo() {
		return evTplNo;
	}

	public void setEvTplNo(String evTplNo) {
		this.evTplNo = evTplNo;
	}

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

	public String getCatLv2Code() {
		return catLv2Code;
	}

	public void setCatLv2Code(String catLv2Code) {
		this.catLv2Code = catLv2Code;
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

	public String getEvChannelName() {
		return evChannelName;
	}

	public void setEvChannelName(String evChannelName) {
		this.evChannelName = evChannelName;
	}

	public String getEvTypeName() {
		return evTypeName;
	}

	public void setEvTypeName(String evTypeName) {
		this.evTypeName = evTypeName;
	}

	public String getSgName1() {
		return sgName1;
	}

	public void setSgName1(String sgName1) {
		this.sgName1 = sgName1;
	}

	public String getSgName2() {
		return sgName2;
	}

	public void setSgName2(String sgName2) {
		this.sgName2 = sgName2;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
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

	public String getVenCd() {
		return venCd;
	}

	public void setVenCd(String venCd) {
		this.venCd = venCd;
	}

	public String getRnum() {
		return rnum;
	}

	public void setRnum(String rnum) {
		this.rnum = rnum;
	}

	public String getEgNo() {
		return egNo;
	}

	public void setEgNo(String egNo) {
		this.egNo = egNo;
	}

	public String getIrsNo() {
		return irsNo;
	}

	public void setIrsNo(String irsNo) {
		this.irsNo = irsNo;
	}

	public String getEgName() {
		return egName;
	}

	public void setEgName(String egName) {
		this.egName = egName;
	}

	public String getEvTplSubject() {
		return evTplSubject;
	}

	public void setEvTplSubject(String evTplSubject) {
		this.evTplSubject = evTplSubject;
	}

	public String getEvScoreSum() {
		return evScoreSum;
	}

	public void setEvScoreSum(String evScoreSum) {
		this.evScoreSum = evScoreSum;
	}

	public String getEvGradeClass() {
		return evGradeClass;
	}

	public void setEvGradeClass(String evGradeClass) {
		this.evGradeClass = evGradeClass;
	}

	public String getImpCnt() {
		return impCnt;
	}

	public void setImpCnt(String impCnt) {
		this.impCnt = impCnt;
	}

	public ArrayList getVenCds() {
		if (this.venCds != null) {
			ArrayList ret = new ArrayList();
			for (int i = 0; i < venCds.size(); i++) {
				ret.add(i, this.venCds.get(i));
			}
			return ret;
		} else {
			return null;
		}
	}
	public void setVenCds(ArrayList venCds) {
		if (venCds != null) {
			this.venCds = new ArrayList();
			for (int i = 0; i < venCds.size();i++) {
				this.venCds.add(i, venCds.get(i));
			}
		} else {
			this.venCds = null;
		}
	}
}
