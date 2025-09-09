package com.lottemart.epc.board.model;

import java.io.Serializable;


/**
 * @Class Name : PSCMBRD0013VO.java
 * @Description : 상품평 게시판 PSCMBRD0013VO
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 9. 16. 오후 1:16:40 ksjeong
 * 2014. 9. 22. 박지혜       수정
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
public class PSCMBRD0013VO implements Serializable{  
	private static final long serialVersionUID = -4437035278047081003L;
	private String num = "";
	private String recommSeq = "";
	private String prodCd = "";
	private String prodName = "";
	private String title = "";
	private String delYN = "";
	private String startDt = "";
	private String endDt = "";
	private String regName = "";
	private String regId = "";
	private String modId = "";
	private String regDate = "";
	private String ntceDy = "";
	private String memberName = "";
	private String memberLoginId = "";
	private String viewCnt = "";
	private String recommPnt = "";
	private String item1 = "";
	private String item2 = "";
	private String item3 = "";
	private String item4 = "";
	private String recommContent = "";
	private String adminMemo = "";
	private String memberNo = "";
	private String drvNo = "";
	private String pickerNm = "";
	private String orderId = "";
	
	private String STR_NM = ""; //2012-04-09 임재유 추가
	
	private String mallDivnCd = ""; //2014.09.22 박지혜 추가
	private String atchFileId = ""; //2014.09.30 박지혜 추가
	private String mainDpSeq = ""; //2014.10.29 박지혜 추가
	private String atchFileYN = ""; //2014.11.22 박지혜 추가
	private String exlnSltYn = "";  //2016.05.31 추가
	private String vendorNm = "";
	private String recommDivnCd = "";
	private String fileUrl = "";
	
	public String getMemberLoginId() {
		return memberLoginId;
	}
	public void setMemberLoginId(String memberLoginId) {
		this.memberLoginId = memberLoginId;
	}
	public String getViewCnt() {
		return viewCnt;
	}
	public void setViewCnt(String viewCnt) {
		this.viewCnt = viewCnt;
	}
	public String getRecommPnt() {
		return recommPnt;
	}
	public void setRecommPnt(String recommPnt) {
		this.recommPnt = recommPnt;
	}
	public String getItem1() {
		return item1;
	}
	public void setItem1(String item1) {
		this.item1 = item1;
	}
	public String getItem2() {
		return item2;
	}
	public void setItem2(String item2) {
		this.item2 = item2;
	}
	public String getItem3() {
		return item3;
	}
	public void setItem3(String item3) {
		this.item3 = item3;
	}
	public String getItem4() {
		return item4;
	}
	public void setItem4(String item4) {
		this.item4 = item4;
	}
	public String getRecommContent() {
		return recommContent;
	}
	public void setRecommContent(String recommContent) {
		this.recommContent = recommContent;
	}
	public String getAdminMemo() {
		return adminMemo;
	}
	public void setAdminMemo(String adminMemo) {
		this.adminMemo = adminMemo;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getNtceDy() {
		return ntceDy;
	}
	public void setNtceDy(String ntceDy) {
		this.ntceDy = ntceDy;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getRecommSeq() {
		return recommSeq;
	}
	public void setRecommSeq(String recommSeq) {
		this.recommSeq = recommSeq;
	}
	public String getProdCd() {
		return prodCd;
	}
	public void setProdCd(String prodCd) {
		this.prodCd = prodCd;
	}
	public String getProdName() {
		return prodName;
	}
	public void setProdName(String prodName) {
		this.prodName = prodName;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDelYN() {
		return delYN;
	}
	public void setDelYN(String delYN) {
		this.delYN = delYN;
	}
	public String getStartDt() {
		return startDt;
	}
	public void setStartDt(String startDt) {
		this.startDt = startDt;
	}
	public String getEndDt() {
		return endDt;
	}
	public void setEndDt(String endDt) {
		this.endDt = endDt;
	}
	public String getRegName() {
		return regName;
	}
	public void setRegName(String regName) {
		this.regName = regName;
	}
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	public String getModId() {
		return modId;
	}
	public void setModId(String modId) {
		this.modId = modId;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getMemberNo() {
		return memberNo;
	}
	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}
	public String getSTR_NM() {
		return STR_NM;
	}
	public void setSTR_NM(String sTR_NM) {
		STR_NM = sTR_NM;
	}
	public String getDrvNo() {
		return drvNo;
	}
	public void setDrvNo(String drvNo) {
		this.drvNo = drvNo;
	}
	public String getPickerNm() {
		return pickerNm;
	}
	public void setPickerNm(String pickerNm) {
		this.pickerNm = pickerNm;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getMallDivnCd() {
		return mallDivnCd;
	}
	public void setMallDivnCd(String mallDivnCd) {
		this.mallDivnCd = mallDivnCd;
	}
	public String getAtchFileId() {
		return atchFileId;
	}
	public void setAtchFileId(String atchFileId) {
		this.atchFileId = atchFileId;
	}
	public String getMainDpSeq() {
		return mainDpSeq;
	}
	public void setMainDpSeq(String mainDpSeq) {
		this.mainDpSeq = mainDpSeq;
	}
	public String getAtchFileYN() {
		return atchFileYN;
	}
	public void setAtchFileYN(String atchFileYN) {
		this.atchFileYN = atchFileYN;
	}
	public String getExlnSltYn() {
		return exlnSltYn;
	}
	public void setExlnSltYn(String exlnSltYn) {
		this.exlnSltYn = exlnSltYn;
	}
	public String getVendorNm() {
		return vendorNm;
	}
	public void setVendorNm(String vendorNm) {
		this.vendorNm = vendorNm;
	}
	public String getRecommDivnCd() {
		return recommDivnCd;
	}
	public void setRecommDivnCd(String recommDivnCd) {
		this.recommDivnCd = recommDivnCd;
	}
	public String getFileUrl() {
		return fileUrl;
	}
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	
	
}
