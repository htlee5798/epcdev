package com.lottemart.epc.ecsees.mallInterface.model;

import java.io.Serializable;

public class ProductVO implements Serializable{
	private static final long serialVersionUID = -1171464142065751147L;
	private String product_id;
	private String product_nm;
	private String vendor_id;
	private String brand_id;
	private String team_id;
	private String reg_id;
	private String reg_date;
	private String upd_id;
	private String upd_date;
	private String md_srcmk_cd;
	
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public String getProduct_nm() {
		return product_nm;
	}
	public void setProduct_nm(String product_nm) {
		this.product_nm = product_nm;
	}
	public String getVendor_id() {
		return vendor_id;
	}
	public void setVendor_id(String vendor_id) {
		this.vendor_id = vendor_id;
	}
	public String getBrand_id() {
		return brand_id;
	}
	public void setBrand_id(String brand_id) {
		this.brand_id = brand_id;
	}
	public String getTeam_id() {
		return team_id;
	}
	public void setTeam_id(String team_id) {
		this.team_id = team_id;
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
	public String getMd_srcmk_cd() {
		return md_srcmk_cd;
	}
	public void setMd_srcmk_cd(String md_srcmk_cd) {
		this.md_srcmk_cd = md_srcmk_cd;
	}
}
