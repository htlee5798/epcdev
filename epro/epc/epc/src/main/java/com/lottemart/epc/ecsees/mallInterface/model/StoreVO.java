package com.lottemart.epc.ecsees.mallInterface.model;

import java.io.Serializable;

public class StoreVO implements Serializable{
	private static final long serialVersionUID = 145487968324772707L;
	private String str_cd;
	private String str_nm;
	private String online_yn;
	private String feday_mall_yn;
	private String str_recp_yn;
	private String reg_id;
	private String reg_date;
	private String upd_id;
	private String upd_date;
	
	public String getStr_cd() {
		return str_cd;
	}
	public void setStr_cd(String str_cd) {
		this.str_cd = str_cd;
	}
	public String getStr_nm() {
		return str_nm;
	}
	public void setStr_nm(String str_nm) {
		this.str_nm = str_nm;
	}
	public String getOnline_yn() {
		return online_yn;
	}
	public void setOnline_yn(String online_yn) {
		this.online_yn = online_yn;
	}
	public String getFeday_mall_yn() {
		return feday_mall_yn;
	}
	public void setFeday_mall_yn(String feday_mall_yn) {
		this.feday_mall_yn = feday_mall_yn;
	}
	public String getStr_recp_yn() {
		return str_recp_yn;
	}
	public void setStr_recp_yn(String str_recp_yn) {
		this.str_recp_yn = str_recp_yn;
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
}
