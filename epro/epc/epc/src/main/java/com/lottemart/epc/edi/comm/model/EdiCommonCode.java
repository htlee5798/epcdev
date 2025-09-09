package com.lottemart.epc.edi.comm.model;

import java.io.Serializable;

public class EdiCommonCode implements Serializable {

	private String groupCode;
	private String detailCode;
	private String detailName;
	
	private String l1Cd;
	private String l1Nm;
	
	
	
	
	private String teamCode;
	private String teamName;
	
	private String orgCode;
	private String orgName;
	private String orgDesc;
	private String colVal;
	
	
	


	private String orgSubName;
	
	private String categoryCode;
	private String categoryName;
	//private String l1CategoryCode;
	               
	
	
	private String count;
	
	
	
	public EdiCommonCode() {}
	
	
	
	public String getCategoryCode() {
		return categoryCode;
	}
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
//	public String getL1CategoryCode() {
//		return l1CategoryCode;
//	}
//	public void setL1CategoryCode(String l1CategoryCode) {
//		this.l1CategoryCode = l1CategoryCode;
//	}
	
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getOrgDesc() {
		return orgDesc;
	}public void setOrgDesc(String orgDesc) {
		this.orgDesc = orgDesc;
	}
	public String getOrgSubName() {
		return orgSubName;
	}
	public void setOrgSubName(String orgSubName) {
		this.orgSubName = orgSubName;
	}
	public String getColVal() {
		return colVal;
	}
	public void setColVal(String colVal) {
		this.colVal = colVal;
	}
	
	public String getTeamCode() {
		return teamCode;
	}
	public void setTeamCode(String teamCode) {
		this.teamCode = teamCode;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public String getGroupCode() {
		return groupCode;
	}
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	public String getDetailCode() {
		return detailCode;
	}
	public void setDetailCode(String detailCode) {
		this.detailCode = detailCode;
	}
	public String getDetailName() {
		return detailName;
	}
	public void setDetailName(String detailName) {
		this.detailName = detailName;
	} 
	public String getL1Cd() {
		return l1Cd;
	}

	public void setL1Cd(String l1Cd) {
		this.l1Cd = l1Cd;
	}

	public String getL1Nm() {
		return l1Nm;
	}

	public void setL1Nm(String l1Nm) {
		this.l1Nm = l1Nm;
	}
	
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	} 
	
	
	
}
