package com.lottemart.epc.edi.srm.model;


import java.io.Serializable;

import com.lottemart.epc.edi.comm.model.PagingVO;

public class SRMEVL0010VO extends PagingVO implements Serializable {
	
	private static final long serialVersionUID = -2141820938143316371L;
	
	/** 하우스코드 */
	private String houseCode;
	/** 사용자 id */
	private String userId;
	/** 비밀번호 */
	private String password;
	/** 사용자타입(20자리로 변경) */
	private String userType;
	/** 업체코드 */
	private String sellerCode;
	/** 업체명 */
	private String sellerNameLoc;
	/** 01 일반잠재업체,02 외부평가업체 */
	private String inoutKind;
	/** 비밀번호체크횟수 */
	private int passCheckCnt;
	
	public String getHouseCode() {
		return houseCode;
	}
	public void setHouseCode(String houseCode) {
		this.houseCode = houseCode;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
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
	public String getInoutKind() {
		return inoutKind;
	}
	public void setInoutKind(String inoutKind) {
		this.inoutKind = inoutKind;
	}
	public int getPassCheckCnt() {
		return passCheckCnt;
	}
	public void setPassCheckCnt(int passCheckCnt) {
		this.passCheckCnt = passCheckCnt;
	}
	
}