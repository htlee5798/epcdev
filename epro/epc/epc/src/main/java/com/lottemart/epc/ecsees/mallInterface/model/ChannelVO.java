package com.lottemart.epc.ecsees.mallInterface.model;

import java.io.Serializable;

public class ChannelVO implements Serializable{
	private static final long serialVersionUID = -8187915443884377246L;
	private String channel_id;
	private String channel_nm;
	private String channel_type_cd;
	private String channel_type_nm;
	private String param_id;
	private String param_val;
	private String partner_co_nm;
	private String reg_id;
	private String reg_date;
	private String upd_id;
	private String upd_date;
	
	public String getChannel_id() {
		return channel_id;
	}
	public void setChannel_id(String channel_id) {
		this.channel_id = channel_id;
	}
	public String getChannel_nm() {
		return channel_nm;
	}
	public void setChannel_nm(String channel_nm) {
		this.channel_nm = channel_nm;
	}
	public String getChannel_type_cd() {
		return channel_type_cd;
	}
	public void setChannel_type_cd(String channel_type_cd) {
		this.channel_type_cd = channel_type_cd;
	}
	public String getChannel_type_nm() {
		return channel_type_nm;
	}
	public void setChannel_type_nm(String channel_type_nm) {
		this.channel_type_nm = channel_type_nm;
	}
	public String getParam_id() {
		return param_id;
	}
	public void setParam_id(String param_id) {
		this.param_id = param_id;
	}
	public String getParam_val() {
		return param_val;
	}
	public void setParam_val(String param_val) {
		this.param_val = param_val;
	}
	public String getPartner_co_nm() {
		return partner_co_nm;
	}
	public void setPartner_co_nm(String partner_co_nm) {
		this.partner_co_nm = partner_co_nm;
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
