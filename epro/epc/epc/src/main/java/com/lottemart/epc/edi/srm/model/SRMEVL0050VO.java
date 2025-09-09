package com.lottemart.epc.edi.srm.model;

import lcn.module.common.util.StringUtil;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.ArrayList;

public class SRMEVL0050VO implements Serializable {

	static final long serialVersionUID = -254238134556327798L;

	/** Locale */
	private String locale;
	/**하우스코드*/
	private String houseCode;
	/**분류*/
	private String sgNo;
	/**평가결과번호*/
	private String evNo;
	/**공급업체코드*/
	private String vendorCode;
	/**평가자ID*/
	private String evUserId;
	/**평가번호*/
	private String evTplNo;
	/**평가요청 순번*/
	private String visitSeq;
	/**순번*/
	private String seq;

	/*************점검요약***************/
	/** 업체명 */
	private String sellerNameLoc;
	/** 업체 주소 */
	private String sellerAddress;
	/**평가기관 업체명*/
	private String evalSellerNameLoc;
	/** 감사자명 */
	private String evCtrlName;
	/** 감사자 연락처 */
	private String evCtrlPhone;
//	private String evCtrlPhone1;
//	private String evCtrlPhone2;
//	private String evCtrlPhone3;
	/** 감사자 이메일 */
	private String evCtrlEmail;
	/** 감사일 */
	private String evCtrlDate;
	/** 획득가능한 총점 */
	private String evTotScore;
	/** 획득점수 */
	private String evScore;
	/***/
	private String EvScorePer;
	/** 평가등급 */
	private String evGrade;
	/** 중요부적합사항 */
	private String evInconRemark;
	/** 심사총평 */
	private String evGenView;
	/** 진행상태 */
	private String status;
	/** 진행상태명[SRM900] */
	private String statusNm;
	/** 진행상태 */
	private String progressCode;
	/** 대분류 */
	private String catLv1CodeName;
	/** 업종 */
	private String industryType;
	/** 주요품목 */
	private String mainProduct;

	/************************************/

	/*************참석자***************/
	/** 참석자명 */
	private String evPartName;
	/** 참석자 전화번호 */
	private String evPartPhone;
//	private String evPartPhone1;
//	private String evPartPhone2;
//	private String evPartPhone3;
	/** 참석자 이메일 */
	private String evPartEmail;
	/** 참석자 부서 */
	private String evPartDept;
	/** 참석자 직위 */
	private String evPartPostion;

	/** 참석자명 */
	private ArrayList evPartNames;
	/** 참석자 전화번호 */
	private ArrayList evPartPhones;
	/** 참석자 이메일 */
	private ArrayList evPartEmails;
	/** 참석자 부서 */
	private ArrayList evPartDepts;
	/** 참석자 직위 */
	private ArrayList evPartPostions;
	/************************************/


	/*************시정조치내역***************/

	/** 폎가관점 */
	private String evItemType1Code;
	/** 시정조치 요청내용 */
	private String impReqRemark;


	/** 시정조치 요청항목 */
	private ArrayList evItemType1Codes;
	/** 시정조치 요청내용 */
	private ArrayList impReqRemarks;
	/**조치내역 상태*/
	private String impStatus;
	/************************************/


	/*************첨부문서***************/
	/**첨부문서*/
	private String attachFileNo;

	/**첨부문서*/
	private ArrayList docNo;
	/**첨부문서 순번*/
	private ArrayList docSeq;
	/**첨부문서*/
	private ArrayList<MultipartFile> attachFile;
	/************************************/


	/*************상세보기 팝업***************/
	/**점검분류1*/
	private String evItemType1CodeName;
	/**점검분류2*/
	private String evItemType2CodeName;
	/**rowspan*/
	private String rowSpan;
	/**획득 가능한 점수*/
	private String evIdScore;
	/**획득한점수*/
	private String evIdScoreVal;
	/********************************/
	/**임시저장 여부*/
	private String tempYn;
	/**부적합수*/
	private String incongruityCnt;
	/**항목수*/
	private String rowCnt;

	private String userNameLoc;
	private String email;

	private String evalFlag;

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

	public String getEvTplNo() {
		return evTplNo;
	}

	public void setEvTplNo(String evTplNo) {
		this.evTplNo = evTplNo;
	}

	public String getVisitSeq() {
		return visitSeq;
	}

	public void setVisitSeq(String visitSeq) {
		this.visitSeq = visitSeq;
	}

	public String getEvCtrlName() {
		return evCtrlName;
	}

	public void setEvCtrlName(String evCtrlName) {
		this.evCtrlName = evCtrlName;
	}

	public String getEvCtrlPhone() {
		return evCtrlPhone;
	}

	public void setEvCtrlPhone(String evCtrlPhone) {
		this.evCtrlPhone = evCtrlPhone;
	}

	public String getEvCtrlEmail() {
		return evCtrlEmail;
	}

	public void setEvCtrlEmail(String evCtrlEmail) {
		this.evCtrlEmail = evCtrlEmail;
	}

	public String getEvCtrlDate() {
		return evCtrlDate;
	}

	public void setEvCtrlDate(String evCtrlDate) {
		this.evCtrlDate = evCtrlDate;
	}

	public String getEvTotScore() {
		return evTotScore;
	}

	public void setEvTotScore(String evTotScore) {
		this.evTotScore = evTotScore;
	}

	public String getEvScore() {
		return evScore;
	}

	public void setEvScore(String evScore) {
		this.evScore = evScore;
	}

	public String getEvGrade() {
		return evGrade;
	}

	public void setEvGrade(String evGrade) {
		this.evGrade = evGrade;
	}

	public String getEvInconRemark() {
		return evInconRemark;
	}

	public void setEvInconRemark(String evInconRemark) {
		this.evInconRemark = evInconRemark;
	}

	public String getEvGenView() {
		return evGenView;
	}

	public void setEvGenView(String evGenView) {
		this.evGenView = evGenView;
	}

	public String getEvPartName() {
		return evPartName;
	}

	public void setEvPartName(String evPartName) {
		this.evPartName = evPartName;
	}

	public String getEvPartPhone() {
		return evPartPhone;
	}

	public void setEvPartPhone(String evPartPhone) {
		this.evPartPhone = evPartPhone;
	}

	public String getEvPartEmail() {
		return evPartEmail;
	}

	public void setEvPartEmail(String evPartEmail) {
		this.evPartEmail = evPartEmail;
	}

	public String getEvPartDept() {
		return evPartDept;
	}

	public void setEvPartDept(String evPartDept) {
		this.evPartDept = evPartDept;
	}

	public String getEvPartPostion() {
		return evPartPostion;
	}

	public void setEvPartPostion(String evPartPostion) {
		this.evPartPostion = evPartPostion;
	}

	public ArrayList getEvPartNames() {
		if (this.evPartNames != null) {
			ArrayList arrayList = new ArrayList();
			for (int i = 0; i < evPartNames.size(); i++) {
				arrayList.add(i, this.evPartNames.get(i));
			}
			return arrayList;
		} else {
			return null;
		}
	}

	public void setEvPartNames(ArrayList evPartNames) {
		if (evPartNames != null) {
			this.evPartNames = new ArrayList();
			for (int i = 0; i < evPartNames.size();i++) {
				this.evPartNames.add(i, evPartNames.get(i));
			}
		} else {
			this.evPartNames = null;
		}
	}

	public ArrayList getEvPartPhones() {
		if (this.evPartPhones != null) {
			ArrayList arrayList = new ArrayList();
			for (int i = 0; i < evPartPhones.size(); i++) {
				arrayList.add(i, this.evPartPhones.get(i));
			}
			return arrayList;
		} else {
			return null;
		}
	}

	public void setEvPartPhones(ArrayList evPartPhones) {
		if (evPartPhones != null) {
			this.evPartPhones = new ArrayList();
			for (int i = 0; i < evPartPhones.size();i++) {
				this.evPartPhones.add(i, evPartPhones.get(i));
			}
		} else {
			this.evPartPhones = null;
		}
	}

	public ArrayList getEvPartEmails() {
		if (this.evPartEmails != null) {
			ArrayList arrayList = new ArrayList();
			for (int i = 0; i < evPartEmails.size(); i++) {
				arrayList.add(i, this.evPartEmails.get(i));
			}
			return arrayList;
		} else {
			return null;
		}
	}

	public void setEvPartEmails(ArrayList evPartEmails) {
		if (evPartEmails != null) {
			this.evPartEmails = new ArrayList();
			for (int i = 0; i < evPartEmails.size();i++) {
				this.evPartEmails.add(i, evPartEmails.get(i));
			}
		} else {
			this.evPartEmails = null;
		}
	}

	public ArrayList getEvPartDepts() {
		if (this.evPartDepts != null) {
			ArrayList arrayList = new ArrayList();
			for (int i = 0; i < evPartDepts.size(); i++) {
				arrayList.add(i, this.evPartDepts.get(i));
			}
			return arrayList;
		} else {
			return null;
		}
	}

	public void setEvPartDepts(ArrayList evPartDepts) {
		if (evPartDepts != null) {
			this.evPartDepts = new ArrayList();
			for (int i = 0; i < evPartDepts.size();i++) {
				this.evPartDepts.add(i, evPartDepts.get(i));
			}
		} else {
			this.evPartDepts = null;
		}
	}

	public ArrayList getEvPartPostions() {
		if (this.evPartPostions != null) {
			ArrayList arrayList = new ArrayList();
			for (int i = 0; i < evPartPostions.size(); i++) {
				arrayList.add(i, this.evPartPostions.get(i));
			}
			return arrayList;
		} else {
			return null;
		}
	}

	public void setEvPartPostions(ArrayList evPartPostions) {
		if (evPartPostions != null) {
			this.evPartPostions = new ArrayList();
			for (int i = 0; i < evPartPostions.size();i++) {
				this.evPartPostions.add(i, evPartPostions.get(i));
			}
		} else {
			this.evPartPostions = null;
		}
	}

	public String getEvItemType1Code() {
		return evItemType1Code;
	}

	public void setEvItemType1Code(String evItemType1Code) {
		this.evItemType1Code = evItemType1Code;
	}

	public String getImpReqRemark() {
		return impReqRemark;
	}

	public void setImpReqRemark(String impReqRemark) {
		this.impReqRemark = impReqRemark;
	}

	public ArrayList getEvItemType1Codes() {
		if (this.evItemType1Codes != null) {
			ArrayList arrayList = new ArrayList();
			for (int i = 0; i < evItemType1Codes.size(); i++) {
				arrayList.add(i, this.evItemType1Codes.get(i));
			}
			return arrayList;
		} else {
			return null;
		}
	}

	public void setEvItemType1Codes(ArrayList evItemType1Codes) {
		if (evItemType1Codes != null) {
			this.evItemType1Codes = new ArrayList();
			for (int i = 0; i < evItemType1Codes.size();i++) {
				this.evItemType1Codes.add(i, evItemType1Codes.get(i));
			}
		} else {
			this.evItemType1Codes = null;
		}
	}

	public ArrayList getImpReqRemarks() {
		if (this.impReqRemarks != null) {
			ArrayList arrayList = new ArrayList();
			for (int i = 0; i < impReqRemarks.size(); i++) {
				arrayList.add(i, this.impReqRemarks.get(i));
			}
			return arrayList;
		} else {
			return null;
		}
	}

	public void setImpReqRemarks(ArrayList impReqRemarks) {
		if (impReqRemarks != null) {
			this.impReqRemarks = new ArrayList();
			for (int i = 0; i < impReqRemarks.size();i++) {
				this.impReqRemarks.add(i, impReqRemarks.get(i));
			}
		} else {
			this.impReqRemarks = null;
		}
	}

	public String getAttachFileNo() {
		return attachFileNo;
	}

	public void setAttachFileNo(String attachFileNo) {
		this.attachFileNo = attachFileNo;
	}

	public ArrayList getDocNo() {
		if (this.docNo != null) {
			ArrayList arrayList = new ArrayList();
			for (int i = 0; i < docNo.size(); i++) {
				arrayList.add(i, this.docNo.get(i));
			}
			return arrayList;
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
	}

	public ArrayList getDocSeq() {
		if (this.docSeq != null) {
			ArrayList arrayList = new ArrayList();
			for (int i = 0; i < docSeq.size(); i++) {
				arrayList.add(i, this.docSeq.get(i));
			}
			return arrayList;
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
	}

	public ArrayList<MultipartFile> getAttachFile() {
		if (this.attachFile != null) {
			ArrayList arrayList = new ArrayList();
			for (int i = 0; i < attachFile.size(); i++) {
				arrayList.add(i, this.attachFile.get(i));
			}
			return arrayList;
		} else {
			return null;
		}
	}

	public void setAttachFile(ArrayList<MultipartFile> attachFile) {
		if (attachFile != null) {
			this.attachFile = new ArrayList();
			for (int i = 0; i < attachFile.size();i++) {
				this.attachFile.add(i, attachFile.get(i));
			}
		} else {
			this.attachFile = null;
		}
	}

	public String getEvScorePer() {
		return EvScorePer;
	}

	public void setEvScorePer(String evScorePer) {
		EvScorePer = evScorePer;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
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

	public String getRowSpan() {
		return rowSpan;
	}

	public void setRowSpan(String rowSpan) {
		this.rowSpan = rowSpan;
	}

	public String getEvIdScore() {
		return evIdScore;
	}

	public void setEvIdScore(String evIdScore) {
		this.evIdScore = evIdScore;
	}

	public String getEvIdScoreVal() {
		return evIdScoreVal;
	}

	public void setEvIdScoreVal(String evIdScoreVal) {
		this.evIdScoreVal = evIdScoreVal;
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

	public String getProgressCode() {
		return progressCode;
	}

	public void setProgressCode(String progressCode) {
		this.progressCode = progressCode;
	}

	public String getSellerNameLoc() {
		return sellerNameLoc;
	}

	public void setSellerNameLoc(String sellerNameLoc) {
		this.sellerNameLoc = sellerNameLoc;
	}

	public String getSellerAddress() {
		return sellerAddress;
	}

	public void setSellerAddress(String sellerAddress) {
		this.sellerAddress = sellerAddress;
	}

	public String getCatLv1CodeName() {
		return catLv1CodeName;
	}

	public void setCatLv1CodeName(String catLv1CodeName) {
		this.catLv1CodeName = catLv1CodeName;
	}

	public String getIndustryType() {
		return industryType;
	}

	public void setIndustryType(String industryType) {
		this.industryType = industryType;
	}

	public String getMainProduct() {
		return mainProduct;
	}

	public void setMainProduct(String mainProduct) {
		this.mainProduct = mainProduct;
	}

	public String getEvCtrlPhone1() {
		return phoneNumberHypen(evCtrlPhone,0);
	}

//	public void setEvCtrlPhone1(String evCtrlPhone1) {
//		this.evCtrlPhone1 = evCtrlPhone1;
//	}

	public String getEvCtrlPhone2() {
		return phoneNumberHypen(evCtrlPhone,1);
	}

//	public void setEvCtrlPhone2(String evCtrlPhone2) {
//		this.evCtrlPhone2 = evCtrlPhone2;
//	}

	public String getEvCtrlPhone3() {
		return phoneNumberHypen(evCtrlPhone,2);
	}

//	public void setEvCtrlPhone3(String evCtrlPhone3) {
//		this.evCtrlPhone3 = evCtrlPhone3;
//	}

	public String getEvPartPhone1() {
		return phoneNumberHypen(evPartPhone,0);
	}

//	public void setEvPartPhone1(String evPartPhone1) {
//		this.evPartPhone1 = evPartPhone1;
//	}

	public String getEvPartPhone2() {
		return phoneNumberHypen(evPartPhone,1);
	}

//	public void setEvPartPhone2(String evPartPhone2) {
//		this.evPartPhone2 = evPartPhone2;
//	}

	public String getEvPartPhone3() {
		return phoneNumberHypen(evPartPhone,2);
	}

//	public void setEvPartPhone3(String evPartPhone3) {
//		this.evPartPhone3 = evPartPhone3;
//	}

	public String getTempYn() {
		return tempYn;
	}

	public void setTempYn(String tempYn) {
		this.tempYn = tempYn;
	}

	public String getImpStatus() {
		return impStatus;
	}

	public void setImpStatus(String impStatus) {
		this.impStatus = impStatus;
	}

	public String getIncongruityCnt() {
		return incongruityCnt;
	}

	public void setIncongruityCnt(String incongruityCnt) {
		this.incongruityCnt = incongruityCnt;
	}

	public String getRowCnt() {
		return rowCnt;
	}

	public void setRowCnt(String rowCnt) {
		this.rowCnt = rowCnt;
	}

	public String getEvalSellerNameLoc() {
		return evalSellerNameLoc;
	}

	public void setEvalSellerNameLoc(String evalSellerNameLoc) {
		this.evalSellerNameLoc = evalSellerNameLoc;
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

	public String getEvalFlag() {
		return evalFlag;
	}

	public void setEvalFlag(String evalFlag) {
		this.evalFlag = evalFlag;
	}

	//전화번호 자르기
	public String phoneNumberHypen(String pNumber, int arry) {
		String number = "";
		if (StringUtil.isEmpty(pNumber)) return "";

		if (pNumber.trim().length() == 9) {
			number = pNumber.trim().substring(0, 2) + "-" + pNumber.trim().substring(2, 5) + "-" + pNumber.trim().substring(pNumber.trim().length() - 4);

			//----- 전화번호가 10자리 일 경우
		} else if (pNumber.trim().length() == 10) {

			//-----지역번호가 서울일 경우[ex) 0212345678]
			if (pNumber.trim().substring(0, 2).equals("02")) {
				number = pNumber.trim().substring(0, 2) + "-" + pNumber.trim().substring(2, 6) + "-" + pNumber.trim().substring(pNumber.trim().length() - 4);

				//-----휴대폰번호이거나 서울지역번호가 아닐경우 [ex) 01112345678, 0541234567]
			} else {
				number = pNumber.trim().substring(0, 3) + "-" + pNumber.trim().substring(3, 6) + "-" + pNumber.trim().substring(pNumber.trim().length() - 4);
			}

			//----- 전화번호가 11자리 일 경우
		} else if (pNumber.trim().length() == 11) {
			number = pNumber.trim().substring(0, 3) + "-" + pNumber.trim().substring(3, 7) + "-" + pNumber.trim().substring(pNumber.trim().length() - 4);

			//----- 전화번호가 9, 10, 11자리가 아닐경우
		} else {
			return "";
		}
		return number.split("-")[arry];
	}
}
