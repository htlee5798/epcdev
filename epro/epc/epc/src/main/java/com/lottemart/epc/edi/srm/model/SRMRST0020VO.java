package com.lottemart.epc.edi.srm.model;

import java.io.Serializable;
import java.util.ArrayList;

import com.lottemart.epc.edi.comm.model.PagingVO;

public class SRMRST0020VO extends PagingVO implements Serializable {
	
	private static final long serialVersionUID = -6217746816874955600L;
	
	/** Locale */
	private String locale;
	/** RowSpan */
	private String rowSpan;
	/** 순번 */
	private String rnum;
	/** 하우스코드 */
	private String houseCode;
	/** 업체코드 */
	private String sellerCode;
	/** 업체명{한글} */
	private String sellerNameLoc;
	/** 업체명{영문} */
	private String sellerNameEng;
	/** 사업자등록번호 */
	private String irsNo;
	/** 업종 */
	private String industryType;
	/** 업태 */
	private String businessType;
	/** BDC 필드 추가필요 - 대표자명 */
	private String sellerCeoName;
	/** 업체마트 담당자(이름) */
	private String vMainName;
	/** BDC EMAIL CHECK 필요 */
	private String vEmail;
	/** 등록자 유선 전화번호 */
	private String vPhone1;
	/** 모바일(이동전화) */
	private String vMobilePhone;
	/** 주소1 */
	private String address1;
	/** 주소2 */
	private String address2;
	/** 우편번호 */
	private String zipcode;
	/** 순번 */
	private String seq;
	/** 대분류코드 */
	private String catLv1Code;
	/** 대분류코드 이름 */
	private String catLv1CodeNm;
	/** 채널코드 */
	private String channelCode;
	/** 채널코드 이름 */
	private String channelCodeNm;
	/** 신청일자 */
	private String receiptDate;
	/** 진행사유 */
	private String remark;
	/** 상태 */
	private String status;
	/** 진행상태 */
	private String processStatus;
	/** 진행상태 이름 */
	private String processStatusNm;
	/** 평가 A상태 ( 상담요청 ) */
	private String evalAStatus;
	/** 평가 A상태 이름 ( 상담요청 ) */
	private String evalAStatusNm;
	/** 평가 B상태 ( 품평회요청 ) */
	private String evalBStatus;
	/** 평가 B상태 이름 ( 품평회요청 ) */
	private String evalBStatusNm;
	/** 평가 C상태 ( 이행보증보험 ) */
	private String evalCStatus;
	/** 평가 C상태 이름 ( 이행보증보험 ) */
	private String evalCStatusNm;
	/** 평가 D상태 ( 품질경영평가 ) */
	private String evalDStatus;
	/** 평가 D상태 이름 ( 품질경영평가 ) */
	private String evalDStatusNm;
	/** 삭제여부 */
	private String delFlag;
	/** 접수일자 */
	private String requestDate;
	/** 담당MD */
	private String ownerMd;
	/** 삭제할 입점신청 seq */
	private ArrayList seqList;
	/** 시정조치 완료 수 */
	private String impCnt;
	/** 시정조치 전체 수 */
	private String totImpCnt;
	/**평가결과번호*/
	private String evalNoResult;
	/**평가기관*/
	private String evalSellerCode;
	/**평가요청순번*/
	private String visitSeq;
	/**담당자명*/
	private String userName;
	
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
	public String getRnum() {
		return rnum;
	}
	public void setRnum(String rnum) {
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
	public String getIrsNo() {
		return irsNo;
	}
	public void setIrsNo(String irsNo) {
		this.irsNo = irsNo;
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
	public String getvEmail() {
		return vEmail;
	}
	public void setvEmail(String vEmail) {
		this.vEmail = vEmail;
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
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
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
	public String getReceiptDate() {
		return receiptDate;
	}
	public void setReceiptDate(String receiptDate) {
		this.receiptDate = receiptDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getProcessStatus() {
		return processStatus;
	}
	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}
	public String getProcessStatusNm() {
		return processStatusNm;
	}
	public void setProcessStatusNm(String processStatusNm) {
		this.processStatusNm = processStatusNm;
	}
	public String getEvalAStatus() {
		return evalAStatus;
	}
	public void setEvalAStatus(String evalAStatus) {
		this.evalAStatus = evalAStatus;
	}
	public String getEvalAStatusNm() {
		return evalAStatusNm;
	}
	public void setEvalAStatusNm(String evalAStatusNm) {
		this.evalAStatusNm = evalAStatusNm;
	}
	public String getEvalBStatus() {
		return evalBStatus;
	}
	public void setEvalBStatus(String evalBStatus) {
		this.evalBStatus = evalBStatus;
	}
	public String getEvalBStatusNm() {
		return evalBStatusNm;
	}
	public void setEvalBStatusNm(String evalBStatusNm) {
		this.evalBStatusNm = evalBStatusNm;
	}
	public String getEvalCStatus() {
		return evalCStatus;
	}
	public void setEvalCStatus(String evalCStatus) {
		this.evalCStatus = evalCStatus;
	}
	public String getEvalCStatusNm() {
		return evalCStatusNm;
	}
	public void setEvalCStatusNm(String evalCStatusNm) {
		this.evalCStatusNm = evalCStatusNm;
	}
	public String getEvalDStatus() {
		return evalDStatus;
	}
	public void setEvalDStatus(String evalDStatus) {
		this.evalDStatus = evalDStatus;
	}
	public String getEvalDStatusNm() {
		return evalDStatusNm;
	}
	public void setEvalDStatusNm(String evalDStatusNm) {
		this.evalDStatusNm = evalDStatusNm;
	}
	public String getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	public String getRequestDate() {
		return requestDate;
	}
	public void setRequestDate(String requestDate) {
		this.requestDate = requestDate;
	}
	public String getOwnerMd() {
		return ownerMd;
	}
	public void setOwnerMd(String ownerMd) {
		this.ownerMd = ownerMd;
	}
	public ArrayList getSeqList() {
		if (this.seqList != null) {
			ArrayList ret = new ArrayList();
			for (int i = 0; i < seqList.size(); i++) {
				ret.add(i, this.seqList.get(i));
			}
			return ret;
		} else {
			return null;
		}
	}
	public void setSeqList(ArrayList seqList) {
		if (seqList != null) {
			this.seqList = new ArrayList();
			for (int i = 0; i < seqList.size();i++) {
				this.seqList.add(i, seqList.get(i));
			}
		} else {
			this.seqList = null;
		}
	}

	public String getImpCnt() {
		return impCnt;
	}

	public void setImpCnt(String impCnt) {
		this.impCnt = impCnt;
	}

	public String getTotImpCnt() {
		return totImpCnt;
	}

	public void setTotImpCnt(String totImpCnt) {
		this.totImpCnt = totImpCnt;
	}

	public String getEvalNoResult() {
		return evalNoResult;
	}

	public void setEvalNoResult(String evalNoResult) {
		this.evalNoResult = evalNoResult;
	}

	public String getEvalSellerCode() {
		return evalSellerCode;
	}

	public void setEvalSellerCode(String evalSellerCode) {
		this.evalSellerCode = evalSellerCode;
	}

	public String getVisitSeq() {
		return visitSeq;
	}

	public void setVisitSeq(String visitSeq) {
		this.visitSeq = visitSeq;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
