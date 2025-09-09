package com.lottemart.epc.ecsees.mallInterface.model;

import java.io.Serializable;

public class CategoryVO implements Serializable {
	private static final long serialVersionUID = 4902955058530827202L;
	private String category_id;
	private String category_nm;
	private String upper_id;
	private String leaf_yn;
	private String category_typr_cd;
	private String disp_seq;
	private String disp_yn;
	private String lvl;
	private String reg_id;
	private String reg_date;
	private String upd_id;
	private String upd_date;
	public String getCategory_id() {
		return category_id;
	}
	public void setCategory_id(String category_id) {
		this.category_id = category_id;
	}
	public String getCategory_nm() {
		return category_nm;
	}
	public void setCategory_nm(String category_nm) {
		this.category_nm = category_nm;
	}
	public String getUpper_id() {
		return upper_id;
	}
	public void setUpper_id(String upper_id) {
		this.upper_id = upper_id;
	}
	public String getLeaf_yn() {
		return leaf_yn;
	}
	public void setLeaf_yn(String leaf_yn) {
		this.leaf_yn = leaf_yn;
	}
	public String getCategory_typr_cd() {
		return category_typr_cd;
	}
	public void setCategory_typr_cd(String category_typr_cd) {
		this.category_typr_cd = category_typr_cd;
	}
	public String getDisp_seq() {
		return disp_seq;
	}
	public void setDisp_seq(String disp_seq) {
		this.disp_seq = disp_seq;
	}
	public String getDisp_yn() {
		return disp_yn;
	}
	public void setDisp_yn(String disp_yn) {
		this.disp_yn = disp_yn;
	}
	public String getLvl() {
		return lvl;
	}
	public void setLvl(String lvl) {
		this.lvl = lvl;
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
