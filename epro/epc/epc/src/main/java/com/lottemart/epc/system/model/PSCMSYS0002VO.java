package com.lottemart.epc.system.model;


import java.io.Serializable;

/**
 * @Class Name : PSCMSYS0002VO.java
 * @Description :
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 20. 오후 02:02:02 mjChoi
 * 
 * @Copyright (C) 2000 ~ 2011 롯데정보통신(주) All right reserved.
 * </pre>
 */
public class PSCMSYS0002VO implements Serializable
{  
	private static final long serialVersionUID = -3740160135792638303L;
	//-----------------------------------------------------	
	// 쿼리 조건
	//-----------------------------------------------------
	private String vendorId		= "";	//협력업체번호
	private String vendorSeq		= "";	//
	
	//-----------------------------------------------------
	// 사용자 정보
	//-----------------------------------------------------
	private String userId			= "";	//
	
	//-----------------------------------------------------
	// column 관련
	//-----------------------------------------------------
	private String vendorUserGubun = "";	//구분
	private String utakNm 			= "";	//성명
	private String utakType 		= "";	//담당자구분코드
	private String utakDeptNm 		= "";	//부서
	private String utakPositionNm 	= "";	//직위
	private String utakTelNo1 		= "";	//전화번호1
	private String utakTelNo2 		= "";	//전화번호2(정)
	private String utakTelNo3 		= "";	//전화번호3
	private String utakCellNo1 	= "";	//휴대폰번호1
	private String utakCellNo2 	= "";	//휴대폰번호2
	private String utakCellNo3 	= "";	//휴대폰번호3
	private String utakFaxNo1 		= "";	//팩스번호1
	private String utakFaxNo2 		= "";	//팩스번호2
	private String utakFaxNo3 		= "";	//팩스번호3
	private String valiYN 			= "";	//유효여부
	
	private String utakTelNo		= "";	//전화번호1 	+ 전화번호2	+ 전화번호3
	private String utakCellNo		= "";	//휴대폰번호1	+ 휴대폰번호2	+ 휴대폰번호3
	private String utakFaxNo		= "";	//팩스번호1  	+ 팩스번호2 	+ 팩스번호3
	
	private String addr1Nm			= "";   //주소1
	private String addr2Nm			= "";   //주소2

	private String zipCd			= "";   //우편번호
	private String email			= "";   //메일
	private String smsRecvYn		= "";   //SMS수신여부
	private String rmk  			= "";   //비고
	private String regDate  		= "";   //등록일
	private String regId  			= "";   //등록자id
	private String modId  			= "";   //수정자id
	private String num             = "";
	
	//-----------------------------------------------------
	// 쿼리 조건
	//-----------------------------------------------------
	public String getVendorId() {
		return vendorId;
	}
	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}
	//-----------------------------------------------------
	public String getVendorSeq() {
		return vendorSeq;
	}
	public void setVendorSeq(String vendorSeq) {
		this.vendorSeq = vendorSeq;
	}
	//-----------------------------------------------------
	// 사용자 정보
	//-----------------------------------------------------
	public String getUserId(){
		return userId;
	}
	public void setUserId(String userId){
		this.userId = userId;
	}
	//-----------------------------------------------------
	// column 관련
	//-----------------------------------------------------	
	public String getVendorUserGubun() {
		return vendorUserGubun;
	}
	public void setVendorUserGubun(String vendorUserGubun) {
		this.vendorUserGubun = vendorUserGubun;
	}
	//-----------------------------------------------------
	public String getUtakNm() {
		return utakNm;
	}
	public void setUtakNm(String utakNm) {
		this.utakNm = utakNm;
	}
	//-----------------------------------------------------
	public String getUtakDeptNm() {
		return utakDeptNm;
	}
	public void setUtakDeptNm(String utakDeptNm) {
		this.utakDeptNm = utakDeptNm;
	}
	//-----------------------------------------------------
	public String getUtakPositionNm() {
		return utakPositionNm;
	}
	public void setUtakPositionNm(String utakPositionNm) {
		this.utakPositionNm = utakPositionNm;
	}
	//-----------------------------------------------------
	public String getUtakTelNo1() {
		return utakTelNo1;
	}
	public void setUtakTelNo1(String utakTelNo1) {
		this.utakTelNo1 = utakTelNo1;
	}
	//-----------------------------------------------------
	public String getUtakTelNo2() {
		return utakTelNo2;
	}
	public void setUtakTelNo2(String utakTelNo2) {
		this.utakTelNo2 = utakTelNo2;
	}
	//-----------------------------------------------------
	public String getUtakTelNo3() {
		return utakTelNo3;
	}
	public void setUtakTelNo3(String utakTelNo3) {
		this.utakTelNo3 = utakTelNo3;
	}
	//-----------------------------------------------------
	public String getUtakCellNo1() {
		return utakCellNo1;
	}
	public void setUtakCellNo1(String utakCellNo1) {
		this.utakCellNo1 = utakCellNo1;
	}
	//-----------------------------------------------------
	public String getUtakCellNo2() {
		return utakCellNo2;
	}
	public void setUtakCellNo2(String utakCellNo2) {
		this.utakCellNo2 = utakCellNo2;
	}
	//-----------------------------------------------------
	public String getUtakCellNo3() {
		return utakCellNo3;
	}
	public void setUtakCellNo3(String utakCellNo3) {
		this.utakCellNo3 = utakCellNo3;
	}
	//-----------------------------------------------------
	public String getUtakFaxNo1() {
		return utakFaxNo1;
	}
	public void setUtakFaxNo1(String utakFaxNo1) {
		this.utakFaxNo1 = utakFaxNo1;
	}
	//-----------------------------------------------------
	public String getUtakFaxNo2() {
		return utakFaxNo2;
	}
	public void setUtakFaxNo2(String utakFaxNo2) {
		this.utakFaxNo2 = utakFaxNo2;
	}
	//-----------------------------------------------------
	public String getUtakFaxNo3() {
		return utakFaxNo3;
	}
	public void setUtakFaxNo3(String utakFaxNo3) {
		this.utakFaxNo3 = utakFaxNo3;
	}
	//-----------------------------------------------------
	public String getValiYN() {
		return valiYN;
	}
	public void setValiYN(String valiYN) {
		this.valiYN = valiYN;
	}
	//-----------------------------------------------------
	
	public String getUtakTelNo() {
		return utakTelNo;
	}
	public void setUtakTelNo(String utakTelNo) {
		this.utakTelNo = utakTelNo;
	}
	//-----------------------------------------------------
	public String getUtakCellNo() {
		return utakCellNo;
	}
	public void setUtakCellNo(String utakCellNo) {
		this.utakCellNo = utakCellNo;
	}
	//-----------------------------------------------------
	public String getUtakFaxNo() {
		return utakFaxNo;
	}
	public void setUtakFaxNo(String utakFaxNo) {
		this.utakFaxNo = utakFaxNo;
	}
	//-----------------------------------------------------
	public String getAddr1Nm() {
		return addr1Nm;
	}
	public void setAddr1Nm(String addr1Nm) {
		this.addr1Nm = addr1Nm;
	}
	public String getAddr2Nm() {
		return addr2Nm;
	}
	public void setAddr2Nm(String addr2Nm) {
		this.addr2Nm = addr2Nm;
	}

	public String getZipCd() {
		return zipCd;
	}
	public void setZipCd(String zipCd) {
		this.zipCd = zipCd;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSmsRecvYn() {
		return smsRecvYn;
	}
	public void setSmsRecvYn(String smsRecvYn) {
		this.smsRecvYn = smsRecvYn;
	}
	public String getRmk() {
		return rmk;
	}
	public void setRmk(String rmk) {
		this.rmk = rmk;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
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
	public String getUtakType() {
		return utakType;
	}
	public void setUtakType(String utakType) {
		this.utakType = utakType;
	}
	
}
