package com.lottemart.epc.edi.srm.model;

import java.io.Serializable;

import com.lottemart.epc.edi.comm.model.PagingVO;

public class SRMJON0020VO extends PagingVO implements Serializable {

	static final long serialVersionUID = -254238134556327798L;
	
	/** Locale */
	private String locale;
	/** 순서 */
	private int rnum;
	/** 하우스코드 */
	private String houseCode;
	/** 업체코드 */
	private String sellerCode;
	/** 해외업체구분 */
	private String shipperType;
	/** 상호명 */
	private String sellerNameLoc;
	/** 요청일자 */
	private String receiptDate;
	/** 사업자등록번호 */
	private String irsNo;
	/** 순번 */
	private String reqSeq;
	/** 업체생성상태{P:최초생성-승인대기,R:반려,E:승인} */
	private String jobStatus;
	/** 상품명 */
	private String productName;
	/** 임시비밀번호 */
	private String tempPw;
	/** 업체코드 */
	private String vendorCode;
	/** 채널 */
	private String channelCode;
	/** 채널명 */
	private String channelCodeNm;
	/** 상담신청일시 */
	private String requestDate;
	/** 대분류코드 */
	private String catLv1Code;
	/** 대분류코드 이름 */
	private String catLv1CodeNm;
	/** 상태 */
	private String status;
	/** 상태명 */
	private String statusNm;
	/** 삭제여부 */	
	private String delFlag;
	/** 국가 */
	private String country;
	/** IP주소 */
	private String ipAddress;
	/** 등록일 */
	private String addDate;
	/** 비밀번호 오류 횟수 카운트 컬럼 */
	private int passCheckCnt;

	/** 약관동의 파일 버전 */
	private String agreeFileVer;
	
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
	public String getShipperType() {
		return shipperType;
	}
	public void setShipperType(String shipperType) {
		this.shipperType = shipperType;
	}
	public String getSellerNameLoc() {
		return sellerNameLoc;
	}
	public void setSellerNameLoc(String sellerNameLoc) {
		this.sellerNameLoc = sellerNameLoc;
	}
	public String getReceiptDate() {
		return receiptDate;
	}
	public void setReceiptDate(String receiptDate) {
		this.receiptDate = receiptDate;
	}
	public String getIrsNo() {
		return irsNo;
	}
	public void setIrsNo(String irsNo) {
		this.irsNo = irsNo;
	}
	public String getReqSeq() {
		return reqSeq;
	}
	public void setReqSeq(String reqSeq) {
		this.reqSeq = reqSeq;
	}
	public String getJobStatus() {
		return jobStatus;
	}
	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getTempPw() {
		return tempPw;
	}
	public void setTempPw(String tempPw) {
		this.tempPw = tempPw;
	}
	public String getVendorCode() {
		return vendorCode;
	}
	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
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
	public String getRequestDate() {
		return requestDate;
	}
	public void setRequestDate(String requestDate) {
		this.requestDate = requestDate;
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
	public String getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getAddDate() {
		return addDate;
	}
	public void setAddDate(String addDate) {
		this.addDate = addDate;
	}
	public String getAgreeFileVer() {
		return agreeFileVer;
	}
	public void setAgreeFileVer(String agreeFileVer) {
		this.agreeFileVer = agreeFileVer;
	}
	public int getPassCheckCnt() {
		return passCheckCnt;
	}
	public void setPassCheckCnt(int passCheckCnt) {
		this.passCheckCnt = passCheckCnt;
	}
	
}
