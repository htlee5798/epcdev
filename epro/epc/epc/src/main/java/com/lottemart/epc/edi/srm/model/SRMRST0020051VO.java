package com.lottemart.epc.edi.srm.model;

import java.io.Serializable;

public class SRMRST0020051VO implements Serializable {
	
	private static final long serialVersionUID = -897615395126813700L;

	
	/**담당MD명*/
	private String userNameLoc;
	/**담당MD Email*/
	private String email;
	/**담당MD 전화번호*/
	private String mobile;
	/**업체명*/
	private String sellerNameLoc;
		
	
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
	public String getSellerNameLoc() {
		return sellerNameLoc;
	}
	public void setSellerNameLoc(String sellerNameLoc) {
		this.sellerNameLoc = sellerNameLoc;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}	
	
}
