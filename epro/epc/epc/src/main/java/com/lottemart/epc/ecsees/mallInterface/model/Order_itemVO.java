package com.lottemart.epc.ecsees.mallInterface.model;

import java.io.Serializable;

public class Order_itemVO implements Serializable{
	private static final long serialVersionUID = 6099634273731312299L;
	private String order_id;
	private String first_order_id;
	private String ord_sts_cd;
	private String ord_sts_nm;
	private String order_date;
	private String tot_order_qty;
	private String tot_order_amt;
	private String order_dtl_seq;
	private String first_order_item_seq;
	private String product_id;
	private String item_id;
	private String order_qty;
	private String order_amt;
	private String str_cd;
	private String reg_id;
	private String reg_date;
	private String upd_id;
	private String upd_date;
	private String team_id;
	private String category_id;
	private String channel_cd;
	private String affiliate_id; 
	
	public String getOrder_date() {
		return order_date;
	}
	public void setOrder_date(String order_date) {
		this.order_date = order_date;
	}
	public String getTot_order_qty() {
		return tot_order_qty;
	}
	public void setTot_order_qty(String tot_order_qty) {
		this.tot_order_qty = tot_order_qty;
	}
	public String getTot_order_amt() {
		return tot_order_amt;
	}
	public void setTot_order_amt(String tot_order_amt) {
		this.tot_order_amt = tot_order_amt;
	}
	public String getOrder_dtl_seq() {
		return order_dtl_seq;
	}
	public void setOrder_dtl_seq(String order_dtl_seq) {
		this.order_dtl_seq = order_dtl_seq;
	}
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public String getItem_id() {
		return item_id;
	}
	public void setItem_id(String item_id) {
		this.item_id = item_id;
	}
	public String getOrder_qty() {
		return order_qty;
	}
	public void setOrder_qty(String order_qty) {
		this.order_qty = order_qty;
	}
	public String getOrder_amt() {
		return order_amt;
	}
	public void setOrder_amt(String order_amt) {
		this.order_amt = order_amt;
	}
	public String getStr_cd() {
		return str_cd;
	}
	public void setStr_cd(String str_cd) {
		this.str_cd = str_cd;
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
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getTeam_id() {
		return team_id;
	}
	public void setTeam_id(String team_id) {
		this.team_id = team_id;
	}
	public String getCategory_id() {
		return category_id;
	}
	public void setCategory_id(String category_id) {
		this.category_id = category_id;
	}
	public String getChannel_cd() {
		return channel_cd;
	}
	public void setChannel_cd(String channel_cd) {
		this.channel_cd = channel_cd;
	}
	public String getAffiliate_id() {
		return affiliate_id;
	}
	public void setAffiliate_id(String affiliate_id) {
		this.affiliate_id = affiliate_id;
	}
	public String getFirst_order_id() {
		return first_order_id;
	}
	public void setFirst_order_id(String first_order_id) {
		this.first_order_id = first_order_id;
	}
	public String getOrd_sts_cd() {
		return ord_sts_cd;
	}
	public void setOrd_sts_cd(String ord_sts_cd) {
		this.ord_sts_cd = ord_sts_cd;
	}
	public String getOrd_sts_nm() {
		return ord_sts_nm;
	}
	public void setOrd_sts_nm(String ord_sts_nm) {
		this.ord_sts_nm = ord_sts_nm;
	}
	public String getFirst_order_item_seq() {
		return first_order_item_seq;
	}
	public void setFirst_order_item_seq(String first_order_item_seq) {
		this.first_order_item_seq = first_order_item_seq;
	}
	
}
