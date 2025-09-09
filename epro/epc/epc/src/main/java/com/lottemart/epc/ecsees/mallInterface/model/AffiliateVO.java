package com.lottemart.epc.ecsees.mallInterface.model;

import java.io.Serializable;

public class AffiliateVO implements Serializable{

	private static final long serialVersionUID = 7791002370395459365L;

	private String affiliate_id;
	private String affiliate_nm;
	private String upper_id;
	private String depth;
	private String reg_id;
	private String reg_date;
	private String upd_id;
	private String upd_date;
	
	public String getAffiliate_id() {
		return affiliate_id;
	}
	public void setAffiliate_id(String affiliate_id) {
		this.affiliate_id = affiliate_id;
	}
	public String getAffiliate_nm() {
		return affiliate_nm;
	}
	public void setAffiliate_nm(String affiliate_nm) {
		this.affiliate_nm = affiliate_nm;
	}
	public String getUpper_id() {
		return upper_id;
	}
	public void setUpper_id(String upper_id) {
		this.upper_id = upper_id;
	}
	public String getReg_id() {
		return reg_id;
	}
	public void setReg_id(String reg_id) {
		this.reg_id = reg_id;
	}
	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	public String getUpd_id() {
		return upd_id;
	}
	public void setUpd_id(String upd_id) {
		this.upd_id = upd_id;
	}
	public String getUpd_date() {
		return upd_date;
	}
	public void setUpd_date(String upd_date) {
		this.upd_date = upd_date;
	}
	public String getDepth() {
		return depth;
	}
	public void setDepth(String depth) {
		this.depth = depth;
	}
}
