package com.lottemart.epc.edi.product.model;

import com.lottemart.epc.common.model.PSCMCOM0004VO;

public class OnlineDisplayCategory extends PSCMCOM0004VO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1372494173309406360L;

	private String displayFlag;

	private String depth;
	
	//대분류 카테고리
	private String catCd1 ="";
	//중분류 카테고리
	private String catCd2 ="";
	//소분류 카테고리
	private String catCd3 ="";
	//소속팀코드
	private String teamCd ="";
	
	public String getDepth() {
		return depth;
	}

	public void setDepth(String depth) {
		this.depth = depth;
	}

	public String getDisplayFlag() {
		return displayFlag;
	}

	public void setDisplayFlag(String displayFlag) {
		this.displayFlag = displayFlag;
	}

	public String getCatCd1() {
		return catCd1;
	}

	public void setCatCd1(String catCd1) {
		this.catCd1 = catCd1;
	}

	public String getCatCd2() {
		return catCd2;
	}

	public void setCatCd2(String catCd2) {
		this.catCd2 = catCd2;
	}

	public String getCatCd3() {
		return catCd3;
	}

	public void setCatCd3(String catCd3) {
		this.catCd3 = catCd3;
	}

	public String getTeamCd() {
		return teamCd;
	}

	public void setTeamCd(String teamCd) {
		this.teamCd = teamCd;
	}
	
	
	
}
